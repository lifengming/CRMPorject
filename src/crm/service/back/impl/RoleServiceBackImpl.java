package crm.service.back.impl;

import crm.dao.IGroupsDAO;
import crm.dao.IRoleDAO;
import crm.dao.impl.GroupsDAOImpl;
import crm.dao.impl.RoleDAOImpl;
import crm.service.back.AbstractCRMServiceBack;
import crm.service.back.IRoleServiceBack;
import crm.vo.Groups;
import crm.vo.Role;
import util.factory.DAOFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/8/14 上午2:12
 */
public class RoleServiceBackImpl extends AbstractCRMServiceBack implements IRoleServiceBack {
    @Override
    public boolean rm(String mid, Set<Integer> rid) throws Exception {
        IRoleDAO roleDAO = DAOFactory.getInstance(RoleDAOImpl.class);
        if (rid.size() == 0) {
            return false;
        }
        if (super.isAction(mid, 22)) {
            return roleDAO.doRemove(rid);
        }
        return false;
    }

    @Override
    public Map<String, Object> editPre(String mid, Integer rid) throws Exception {
        Map<String, Object> map = new HashMap<>();
        IRoleDAO roleDAO = DAOFactory.getInstance(RoleDAOImpl.class);
        IGroupsDAO groupsDAO = DAOFactory.getInstance(GroupsDAOImpl.class);
        if (super.isAction(mid, 21)) {
            map.put("role", roleDAO.findById(rid));
            map.put("allGroups", groupsDAO.findAllByType());
            map.put("roleGroups", roleDAO.findGroupsByRole(rid));
        }
        return map;
    }

    @Override
    public Boolean edit(String mid, Role vo) throws Exception {
        IRoleDAO roleDAO = DAOFactory.getInstance(RoleDAOImpl.class);
        if (vo.getGroups() == null || vo.getGroups().size() == 0) {
            return false;
        }
        if (super.isAction(mid, 21)) {
            if (roleDAO.doUpdate(vo)) {
                if (roleDAO.doRemoveRoleAndGroups(vo.getRid())) {
                    return roleDAO.doCreateRoleAndGroups(vo);
                }
            }
        }
        return false;
    }

    @Override
    public Role show(String mid, Integer rid) throws Exception {
        IRoleDAO roleDAO = DAOFactory.getInstance(RoleDAOImpl.class);
        IGroupsDAO groupsDAO = DAOFactory.getInstance(GroupsDAOImpl.class);
        if (super.isAction(mid, 34)) {
            Role role = ((RoleDAOImpl) roleDAO).findById(rid);
            if (role != null) {
                role.setGroups(groupsDAO.findAllByRole(rid));
            }
            return role;
        }
        return null;
    }

    @Override
    public List<Role> list(String mid) throws Exception {
        IRoleDAO roleDAO = DAOFactory.getInstance(RoleDAOImpl.class);
        if (super.isAction(mid, 20)) {
            return roleDAO.findAll();
        }
        return null;
    }

    @Override
    public Map<String, Object> addPre(String mid) throws Exception {
        IGroupsDAO groupsDAO = DAOFactory.getInstance(GroupsDAOImpl.class);
        Map<String, Object> map = new HashMap<>();
        if (super.isAction(mid, 19)) {
            map.put("allGroups", groupsDAO.findAllByType());
        }
        return map;
    }

    @Override
    public boolean add(String mid,Role vo) throws Exception {
        IRoleDAO roleDAO = DAOFactory.getInstance(RoleDAOImpl.class);
        // 角色必须要有对应的权限组信息
        if (vo.getGroups() == null || vo.getGroups().size() == 0) {
            return false;
        }
        if (super.isAction(mid, 19)) {
            if (roleDAO.doCreate(vo)) {//1,向role表中，保存数据，role表中有rid,和title,但此时VO没有rid内容，VO只有title和权限组集合,
                //2,为了向role_groups表中保存数据，所以需要取出之前的role表中的rid数据
                vo.setRid(roleDAO.findLastId());//此时VO有title，rid,权限组集合
                return roleDAO.doCreateRoleAndGroups(vo);
            }
        }
        return false;
    }
}
