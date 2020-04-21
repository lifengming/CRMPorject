package crm.dao;

import crm.vo.Member;
import util.dao.IDAO;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/21 下午2:13
 */
public interface IMemberDAO extends IDAO<String, Member> {
    /**
     * 实现用户的的登录检测，只允许活跃用户登录（locked=0），要求将用户的管理员标记取出
     * 传入的是一个VO类对象，可以利用引用传递的概念，通过vo类对象，取回flag数据
     * @param vo 包含有用户名和密码的vo类
     * @return 登陆成功返回true,否则返回false
     * @throws Exception
     */
    public boolean findLogin(Member vo) throws Exception;

    /**
     * 更新指定ID最后一次登录时间
     * @param id 用户id
     * @return
     * @throws Exception
     */
    public boolean doUpdateLastdate(String id) throws Exception;

    /**
     * 更新用户密码
     * @param mid 要修改密码的用户，用户id
     * @param password 要设置的新密码
     * @return 跟新成功返回true,否则返回false
     * @throws Exception
     */
    public boolean doUpdatePassword(String mid, String password) throws Exception;



}
