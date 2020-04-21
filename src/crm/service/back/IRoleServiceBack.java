package crm.service.back;

import crm.vo.Role;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/8/14 上午2:04
 */
public interface IRoleServiceBack {
    /**
     * 进行角色数据的删除处理操作，角色删除后，由于数据表中有相关级联操作的设置，
     * 所以会自动删除掉该角色对应的member表和role_groups表中的数据，
     * （注意，是删除ON DELETE CASCADE，不是赋值为空 ON DELETE SETNULL）
     * @param mid
     * @param rid
     * @return
     * @throws Exception
     */
    public boolean rm(String mid, Set<Integer> rid) throws Exception;

    /**
     * 角色更新前的查询处理操作
     * @param mid
     * @param rid
     * @return 本操作完成如下功能:<br>
     * <li>key=role,value=IRoleDAO.findById()查询角色信息,保存类型是Role</li>
     * <li>key=allGroups,value=IGroupsDAO.findAllByType()查询全部的权限组信息,,保存类型是Map<String, List<Groups>> </li>
     * <li>key=roleGroups,value=IRoleDAO.findGroupsByRole()，保存类型是Map<Integer,Boolean></li>
     * @throws Exception
     */
    public Map<String, Object> editPre(String mid,Integer rid) throws Exception;

    /**
     * 更新角色信息
     * @param mid
     * @param vo
     * @return 操作完成如下操作:<br>
     * <Li>IRoleDAO.doUpdate()更新role</Li>
     * <Li>IRoleDAO.doRemoveRoleAndGroups()删除role_groups表中，角色-权限组对应关系</Li>
     * <Li>IRoleDAO.doCreateRoleAndGroups()在role_groups表中保存新的角色-权限组对应关系</Li>
     * @throws Exception
     */
    public Boolean edit(String mid, Role vo) throws Exception;

    /**
     * 查看角色详情信息，本操作完成如下步骤:<br>
     * <li>调用IRoleDAO.findById()查询角色信息,</li>
     * <li>调用IGroupsDAO.findByRole()查询角色对应的权限组信息</li>
     * @param mid
     * @param rid
     * @return
     * @throws Exception
     */
    public Role show(String mid, Integer rid) throws Exception;

    /**
     * 实现角色列表（无分页）
     * @param mid
     * @return
     * @throws Exception
     */
    public List<Role> list(String mid) throws Exception;

    /**
     * 角色增加处理，本操作完成如下步骤:<br>
     * <li>使用IRoleDAO.doCreate()保存角色信息</li>
     * <li>使用IRoleDAO.findLastId()取得当前橘色的编号，并且将其设置到VO中</li>
     * <li>使用IRoleDAO.doCreateRoleAndGroups()保存role_groups关系表的信息</li>
     * @param mid
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean add(String mid,Role vo) throws Exception;

    /**
     * 角色增加前的准备操作
     * @param mid
     * @return
     * @throws Exception
     */
    public Map<String, Object> addPre(String mid) throws Exception;
}
