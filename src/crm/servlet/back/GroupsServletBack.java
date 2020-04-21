package crm.servlet.back;

import crm.service.back.IActionServiceBack;
import crm.service.back.IGroupsServiceBack;
import crm.service.back.impl.ActionServiceBackImpl;
import crm.service.back.impl.GroupsServiceBackImpl;
import crm.vo.Action;
import crm.vo.Groups;
import util.factory.ServiceFactory;

import javax.servlet.annotation.WebServlet;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/8/13 下午10:02
 */
@WebServlet(urlPatterns = "/pages/back/groups/GroupsServletBack/*")
public class GroupsServletBack extends AbstractCRMServlet {
    private Groups groups = new Groups();

    public Groups getGroups() {
        return groups;
    }

    public String show() {
        if (super.isAction(24)) {
            try {
                IGroupsServiceBack groupsServiceBack = (IGroupsServiceBack) ServiceFactory.getInstance(GroupsServiceBackImpl.class);
                int gid = Integer.parseInt(super.request.getParameter("gid"));
                super.request.setAttribute("gup", groupsServiceBack.show(super.getMid(), gid));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "groups.show.page";
        } else {
            return "error.page";
        }
    }



    public String list() {
        if (super.isAction(23)) {
            try {
                IGroupsServiceBack groupsServiceBack = (IGroupsServiceBack) ServiceFactory.getInstance(GroupsServiceBackImpl.class);
                super.request.setAttribute("allGroups", groupsServiceBack.list(super.getMid()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "groups.list.page";
        } else {
            return "error.page";
        }
    }

    @Override
    public String getDefaultColumn() {
        return null;
    }

    @Override
    public String getUploadDir() {
        return null;
    }

    @Override
    public String getType() {
        return "权限组";
    }

    @Override
    public String getColumntData() {
        return null;
    }
}
