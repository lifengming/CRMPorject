package crm.dao;

import crm.vo.Role;
import util.dao.IDAO;

import java.util.Map;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/22 上午1:28
 */
public interface IRoleDAO extends IDAO<Integer, Role> {
    /**
     * 删除role_groups表中指定角色对应的所有权限组关系
     * @param rid 角色编号
     * @return
     * @throws Exception
     */
    public Boolean doRemoveRoleAndGroups(Integer rid) throws Exception;
    /**
     * 查询role_groups表，而后根据此表中角色编号，查询出所有的权限组编号
     * @param rid
     * @return 本操作返回如下内容:<br>
     * <Li>key=gid,value=true</Li>
     * @throws Exception
     */
    public Map<Integer, Boolean> findGroupsByRole(Integer rid) throws Exception;
    /**
     * 取得最后一次增长的rid数据,
     * @return
     * @throws Exception
     */
    public Integer findLastId() throws Exception;
    /**
     * 传递一个role对象，里面一定要包含角色编号和对应的权限组编号
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean doCreateRoleAndGroups(Role vo) throws Exception;
}
