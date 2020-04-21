package util.servlet;

import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
import util.BasepathUtil;
import util.bean.BeanValueUtil;
import util.split.SplitPageUtils;
import util.validator.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description: DispatcherProject
 * Created by leizhaoyuan on 19/7/10 下午1:46
 */
//此Servlet没有注解，因为执行路径是根据子类的业务的Servlet执行的！
public abstract class DispatcherServlet extends HttpServlet {
    protected HttpServletRequest request;//定义request对象
    protected HttpServletResponse response;//定义response对象
    private ResourceBundle pageResource;//读取Pages.properties对象
    private ResourceBundle MessageResource;//读取Messages.properties对象
    private ResourceBundle ValidationsResource;//读取Validator.properties对象
    private SmartUpload smart;

    @Override
    public void init() throws ServletException {
        this.pageResource = ResourceBundle.getBundle("Pages", Locale.getDefault());
        this.MessageResource = ResourceBundle.getBundle("Messages", Locale.getDefault());
        this.ValidationsResource = ResourceBundle.getBundle("Validations", Locale.getDefault());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;
        String urlPage = "error.page";//此错误页后期还需要修改设计,错误页不可能固定！
//        String uri = request.getRequestURI();//uri=/EmpDynamicServlet/edit
//        String url = request.getRequestURL().toString();
//        System.out.println(uri + "    " + url);
//        String status = uri.substring(uri.lastIndexOf("/") + 1);
        String status = this.getStatus();
        System.out.println("status=" + status);
        try {
            String contentType = request.getContentType();
            if (contentType != null && contentType.contains("multipart/form-data")) {//表单已进行封装
                //处理SmartUpload对象实例化问题
                try {
                    this.smart = new SmartUpload();
                    this.smart.initialize(super.getServletConfig(), this.request, this.response);
                    this.smart.upload();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (status != null) {//此时根据用户状态码，区分用户请求!
                Map<String, String> errors = Validation.validate(this);
                if (errors.size() == 0) {//验证没有错误
                    this.parameterHandle();//处理所有提交参数
                    try {
                        Method statusMethod = this.getClass().getMethod(status);
                        urlPage = statusMethod.invoke(this).toString();//此时urlPage的实质是Pages.properties中的key!所以下面跳转的时候需要转成value!
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {//回到指定的错误页
                    request.setAttribute("errors", errors);
                    urlPage = this.getClass().getSimpleName()+"."+status+".error.page";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(urlPage);
        request.getRequestDispatcher(this.getPageValue(urlPage)).forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    /**
     * 执行文件的删除处理操作
     * @param fileName
     */
    public void deleteFile(String fileName) {
        File file = new File(super.getServletContext().getRealPath(this.getUploadDir()) + fileName) ;
        if (file.exists()) {
            file.delete() ;
        }
    }

    /**
     * 执行多个文件的删除操作
     * @param files
     */
    public void deleteFile(List<String> files) {
        Iterator<String> iter = files.iterator();
        while (iter.hasNext()) {
            this.deleteFile(iter.next());
        }
    }

    /**
     *  取得当前业务调用对象
     * @return
     */
    public String getStatus() {
        String uri = request.getRequestURI();//uri=/EmpDynamicServlet/edit
        //String url = request.getRequestURL().toString();
        //System.out.println(uri + "    " + url);
        String status = uri.substring(uri.lastIndexOf("/") + 1);
        return status;
    }

    /**
     * 取得指定参数的内容，不关心表单是否被封装!
     * @param paraName
     * @return
     */
    public String getStringParameter(String paraName) {
        String contentType = request.getContentType();
        if (contentType != null && contentType.contains("multipart/form-data")) {
            return this.smart.getRequest().getParameter(paraName);
        } else {
            return this.request.getParameter(paraName);
        }
    }

    /**
     * 取得指定参数，并且将其变为int型数据返回
     * @param paraName
     * @return
     */
    public int getIntParameter(String paraName) {
        return Integer.parseInt(this.getStringParameter(paraName));
    }

    public double getDoubleParameter(String paraName) {
        return Double.parseDouble(this.getStringParameter(paraName));
    }

    public Date getDateParameter(String paraName)  {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(this.getStringParameter(paraName));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Date getDatetimeParameter(String paraName)  {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.getStringParameter(paraName));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 实现所有请求参数与vo类转换的功能
     */
    private void parameterHandle() {
        String contentType = request.getContentType();
        if (contentType != null && contentType.contains("multipart/form-data")) {//表单已进行封装
            try {
                Enumeration<String> enu = this.smart.getRequest().getParameterNames();
                while (enu.hasMoreElements()) {
                    String paramName = enu.nextElement();
                    //System.out.println(paramName + "=" + this.request.getParameter(paramName));
                    String paramValue = this.smart.getRequest().getParameter(paramName);
                    new BeanValueUtil(this, paramName, paramValue).setObjectValue();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
         else {
            //1,需要取得全部的参数名称
            Enumeration<String> enu = this.request.getParameterNames();
            while (enu.hasMoreElements()) {
                String paramName = enu.nextElement();
                //System.out.println(paramName + "=" + this.request.getParameter(paramName));
                String paramValue = this.request.getParameter(paramName);
                new BeanValueUtil(this, paramName, paramValue).setObjectValue();
            }
        }
    }

    /**
     * 设置业务操作完成之后 跳转路径与提示信息的key
     * @param pageKey 对应的Pages.properties资源文件指定的key信息
     * @param messageKey 对应的Messages.properties资源文件指定的key信息
     */
    public void setUrlAndMsg(String pageKey, String messageKey) {
        request.setAttribute("url", this.getPageValue(pageKey));
        if (this.getType() == null || "".equals(this.getType())) {
            request.setAttribute("msg", this.getMessageValue(messageKey));
        } else {//将之前资源文件中设置的每一个Servlet的占位符进行替换！
            request.setAttribute("msg", MessageFormat.format(this.getMessageValue(messageKey), this.getType()));
        }
    }
    /**
     * 取得Pages.properties资源文件指定的key对应的value信息
     * @param pageKey 要读取的资源文件的key信息
     * @return
     */
    public String getPageValue(String pageKey) {
        return this.pageResource.getString(pageKey);
    }
    /**
     * 取得Messages.properties资源文件指定的key对应的value信息
     * @param messageKey 要读取的资源文件的key信息
     * @return
     */
    public String getMessageValue(String messageKey) {
        return this.MessageResource.getString(messageKey);
    }


    /**
     * 判断当前是否有表单的提及处理
     * @return 如果为上传处理返回true，否则返回false
     */
    public boolean isUpload() {
        if (this.request.getContentType() != null) {
            if (this.request.getContentType().contains("multipart/form-data")) {
                return true ;
            }
        }
        return false ;
    }
    /**
     * 判断当前是否存在有上传文件的内容
     * @return 如果存在上传文件则返回true，否则返回false
     */
    public boolean existsUpload() {
        boolean flag = false ;
        if (!this.isUpload()) {
            return false ;
        }
        try {
            flag = this.smart.getFiles().getSize() > 0 ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag ;
    }

    /**
     * 创建新的上传文件名称
     *
     * @return
     */
    public String createSingleFileName() {
        String fileName = null;
        if (!this.isUpload()) {	// 没有文件上传
            return null ;
        }
        if (this.existsUpload()) {
            if (this.smart.getFiles().getFile(0).getSize() > 0) {
                 fileName = UUID.randomUUID() + "." + this.smart.getFiles().getFile(0).getFileExt();
            }
        }
        return fileName;
    }

    /**
     * 得到上传文件的名称，利用UUID生成
     * @return 返回所有的生成的上传文件名称
     */
    public List<String> createUploadFileName(){
        if (!this.isUpload()) {	// 没有文件上传
            return null ;
        }
        List<String> allNames = new ArrayList<String>() ;
        if (this.existsUpload()) {	// 整体有文件上传
            for (int x = 0 ; x < this.smart.getFiles().getCount() ; x ++) {
                if (this.smart.getFiles().getFile(x).getSize() > 0) {
                    String fileName = UUID.randomUUID() + "." + this.smart.getFiles().getFile(x).getFileExt() ;
                    allNames.add(fileName) ;
                }
            }
        }
        return allNames ;
    }

    /**
     * 进行单个上传文件保存
     */
    public boolean saveUploadFile(String fileName) {
        boolean flag = true ;
            if (this.isUpload() && this.existsUpload()) {	// 表单封装并且上传了
                if (this.smart.getFiles().getFile(0).getSize() > 0) {
                    if (this.getUploadDir() == null || "".equals(this.getUploadDir())) {
                        String filePath = super.getServletContext().getRealPath("/") + fileName;
                        try {
                            this.smart.getFiles().getFile(0).saveAs(filePath);
                        } catch (Exception e) {
                            e.printStackTrace();
                            flag = false ;
                        }
                    }
                    else {
                        String filePath = super.getServletContext().getRealPath(
                                this.getUploadDir()) + fileName;
                        File file = new File(filePath) ;
                        if (!file.getParentFile().exists()) {
                            file.mkdirs() ;	// 创建目录
                        }
                        try {
                            this.smart.getFiles().getFile(0).saveAs(filePath);
                        } catch (Exception e) {
                            e.printStackTrace();
                            flag = false ;
                        }
                    }
                }
            }
        return flag ;
    }


    /**
     * 进行一组上传文件保存
     */
    public boolean saveUploadFile(List<String> fileNames) {
        boolean flag = true ;
        if (this.isUpload() && this.existsUpload()) {	// 表单封装并且上传了
            for (int x = 0 ; x < this.smart.getFiles().getCount() ; x ++) {
                if (this.smart.getFiles().getFile(x).getSize() > 0) {
                    String filePath = super.getServletContext().getRealPath(
                            this.getUploadDir())
                            + fileNames.get(x);
                    File file = new File(filePath) ;
                    if (!file.getParentFile().exists()) {
                        file.mkdirs() ;	// 创建目录
                    }
                    try {
                        this.smart.getFiles().getFile(x).saveAs(filePath);
                    } catch (Exception e) {
                        e.printStackTrace();
                        flag = false ;
                    }
                }
            }
        }
        return flag ;
    }

    /**
     * 将分页所需要的参数都是用request进行传递
     * @param urlKey 分页执行要找的url
     * @param allRecorders 总记录数
     */
    public void setSplitPage(String urlKey, int allRecorders) {
        this.request.setAttribute("url", this.getPageValue(urlKey));
        this.request.setAttribute("allRecorders", allRecorders);
    }

    /**
     * 取得HttpSession接口对象
     * @return
     */
    public HttpSession getSession() {
        return this.request.getSession();

    }

    /**
     * 取得分页查询时默认的查询列
     * @return
     */
    public abstract String getDefaultColumn();

    /**
     * 取得上传文件保存目录
     * @return
     */
    public abstract String getUploadDir();
    /**
     * 取得每一个子类的具体操作类型，以作为消息的填充使用
     * @return 返回每一个模块的名称
     */
    public abstract String getType();
    /**
     * 取得默认分页查询时检索的字段名称，结构“显示标签:列名称|显示标签|列名称”
     * @return
     */
    public abstract String getColumntData() ;
    /**
     * 处理分页中的所有参数
     */
    public SplitPageUtils handleSplitParam() {
        SplitPageUtils su = new SplitPageUtils() ;
        su.setCp(request.getParameter("cp"));
        String col = this.request.getParameter("col") ;
        if (col == null || "".equals(col)) {	// 现在没有col参数
            su.setCol(this.getDefaultColumn()); 	// 如果没有分页的列那么使用默认的列
        } else {
            su.setCol(col);
        }
        String kw = this.request.getParameter("kw") ;
        if (kw == null) {
            kw = "" ;
        }
        su.setKw(kw);
        this.request.setAttribute("currentPage", su.getCurrentPage());
        this.request.setAttribute("lineSize", su.getLineSize());
        this.request.setAttribute("keyWord", su.getKeyWord());
        this.request.setAttribute("column", su.getColumn());
        this.request.setAttribute("columnData",this.getColumntData());
        return su ;
    }
}
