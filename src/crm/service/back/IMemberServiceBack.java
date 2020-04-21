package crm.service.back;

import crm.vo.Member;
import crm.vo.Role;

import java.util.Map;
import java.util.Set;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/21 下午2:53
 */
public interface IMemberServiceBack {
    /**
     * 用户删除操作，不能删除超管
     * @param mid
     * @param ids
     * @return
     * @throws Exception
     */
    public boolean rm(String mid, Set<String> ids) throws Exception;
    /**
     * 管理员直接修改密码操作，不需要原有密码直接操作
     * @param mid 权限验证使用
     * @param vo 包含用户名和密码的信息
     * @return
     * @throws Exception
     */
    public boolean editPasswordByAdmin(String mid,Member vo) throws Exception;

    /**
     * 用户更新前的查询处理操作
     * @param mid
     * @return 本操作完成如下功能:<br>
     * <li>key=member,value=IMemberDAO.findById()查询用户信息,保存类型是Member</li>
     * <li>key=allRoles,value=IRoleDAO.findAll()</li>
     * @throws Exception
     */
    public Map<String, Object> editPre(String mid,String umid) throws Exception;

    /**
     * 更新用户信息
     * @param mid
     * @param vo
     * @return 操作完成如下操作:<br>
     * <Li>IMemberDAO.doUpdate()更新Member</Li>
     * @throws Exception
     */
    public Boolean edit(String mid, Member vo) throws Exception;

    /**
     * 实现用户的列表的分页查询显示处理
     * @param mid
     * @param column      模糊查询列
     * @param keyword     模糊查询关键字
     * @param currentPage 当前所在页
     * @param lineSize    每页显示行数
     * @return 本操作返回Map集合包含如下结果:<br>
     * <LI>key=allMembers,value=IMemberDAO.findAllSplit()</LI>
     * <LI>key=memberCount,value=IMemberDAO.getAllCount()</LI>
     * @throws Exception
     */
    public Map<String, Object> list(String mid, String column, String keyword, Integer currentPage, Integer lineSize) throws Exception;

    /**
     * 用户数据的添加处理，本操作执行如下功能:<br>
     * <li>IMemberDAO.findById()查询指定ID是否存在</li>
     * <li>IMemberDAO.doCreate()保存用户信息</li>
     * @param mid 权限验证使用
     * @param vo 包含了新的用户信息
     * @return
     * @throws Exception
     */
    public boolean add(String mid, Member vo) throws Exception;
    /**
     * 增加前的查询操作处理，
     * @param mid
     * @return 返回如下内容:<br>
     * <li>key=allRoles,value=IRoleDAO.findAll()</li>
     * @throws Exception
     */
    public Map<String, Object> addPre(String mid)throws Exception;
    /**
     * 实现用户的登录操作，需执行如下调用:<br>
     * <li>调用IMemberDAO.findLogin()方法验证用户名和密码，同时取出管理员标记</li>
     * <li>调用IMemberDAO.doUpdateLastdate()方法更新最后一次登录日期时间</li>
     * <li>调用ILogsDAO.doCreate()方法保存日志信息</li>
     * <li>调用ILogsDAO.doCreate()方法保存日志信息</li>
     *
     * @param vo 提交的密码要进行MD5加密处理
     * @return 返回Map集合有两个组成部分<br>
     * <li>key = flag,value = 登陆成功或者失败的结果</li>
     * <li>key = allActions,value = 所有权限组对应的权限信息,此value以Map集合形式作为value!（包含菜单权限和页面权限两部分）</li>
     * @throws Exception
     */
    public Map<String, Object> login(Member vo) throws Exception;

    /**
     * 密码修改操作，本操作要执行如下的调用:<br>
     * <li>调用IMemberDAO.findLogin()方法，判断原始密码是否正确</li>
     * <li>调用ImemberDAO.doUpdatePassword()方法，进行新密码的修改</li>
     * @param mid 用户的id
     * @param newPas 新的密码，需要MD5加密处理
     * @param oldPas 旧的密码，需要MD5加密处理
     * @return 密码修改成功，返回true,失败，返回false
     * @throws Exception
     */
    public boolean editPassword(String mid,String newPas,String oldPas) throws Exception;
}
