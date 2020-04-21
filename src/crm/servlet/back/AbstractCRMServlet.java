package crm.servlet.back;

import com.sun.org.apache.xml.internal.serializer.ElemDesc;
import crm.vo.Action;
import sun.jvm.hotspot.debugger.posix.elf.ELFSectionHeader;
import util.servlet.DispatcherServlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/25 上午12:47
 */
public abstract class  AbstractCRMServlet extends DispatcherServlet {
    /**
     * 通过session取出所有权限，而后判断此权限是否存在
     * @param actid 要判断的权限ID
     * @return 如果指定权限存在，返回true,否则返回false
     */
    @SuppressWarnings("unchecked")
    public boolean isAction(int actid) {
        Map<String, Action> map =(Map<String, Action>) super.getSession().getAttribute("allActions") ;
        return map.containsKey(String.valueOf(actid));
    }

    /**
     * 通过session取出登录后的mid属性
     * @return
     */
    public String getMid() {
        return super.getSession().getAttribute("mid").toString();
    }

    /**
     * type类型为int,若没有传递参数，则返回-1
     * @return
     */
    public int getTypes() {
        // 判断当前是否有type这个参数，如果没有这个参数就表示查询全部客户信息，设置为-1
        if (super.request.getParameter("type") == null || "".equals(super.request.getParameter("type"))) {
            return -1 ;
        } else {
            return Integer.parseInt(super.request.getParameter("type")) ;
        }

    }

    public int getVisit() {
        // 判断当前是否有type这个参数，如果没有这个参数就表示查询全部客户信息，设置为-1
        if (super.request.getParameter("visit") == null || "".equals(super.request.getParameter("visit"))) {
            return -1 ;
        } else {
            return Integer.parseInt(super.request.getParameter("visit")) ;
        }

    }

}
