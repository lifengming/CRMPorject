package crm.service.back.impl;

import crm.dao.IActionDAO;
import crm.dao.IGroupsDAO;
import crm.dao.impl.ActionDAOImpl;
import crm.dao.impl.GroupsDAOImpl;
import crm.service.back.AbstractCRMServiceBack;
import crm.service.back.IGroupsServiceBack;
import crm.vo.Groups;
import util.factory.DAOFactory;

import java.util.List;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/8/13 下午11:00
 */
public class GroupsServiceBackImpl extends AbstractCRMServiceBack implements IGroupsServiceBack {
    @Override
    public Groups show(String mid, Integer gid) throws Exception {
        IGroupsDAO groupsDAO = DAOFactory.getInstance(GroupsDAOImpl.class);
        IActionDAO actionDAO = DAOFactory.getInstance(ActionDAOImpl.class);
        if (super.isAction(mid, 24)) {
            Groups gup = ((GroupsDAOImpl) groupsDAO).findById(gid);
            if (gup != null) {
                gup.setActions(actionDAO.findAllByGroups(gid));
            }
            return gup;
        }
        return null;
    }

    @Override
    public List<Groups> list(String mid) throws Exception {
        IGroupsDAO groupsDAO = DAOFactory.getInstance(GroupsDAOImpl.class);
        if (super.isAction(mid, 23)) {
            return groupsDAO.findAll();
        }
        return null;
    }
}
