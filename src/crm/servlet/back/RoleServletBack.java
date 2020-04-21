package crm.servlet.back;

import crm.service.back.IGroupsServiceBack;
import crm.service.back.INewsServiceBack;
import crm.service.back.IRoleServiceBack;
import crm.service.back.impl.GroupsServiceBackImpl;
import crm.service.back.impl.NewsServiceBackImpl;
import crm.service.back.impl.RoleServiceBackImpl;
import crm.vo.Groups;
import crm.vo.Role;
import util.factory.ServiceFactory;

import javax.servlet.annotation.WebServlet;
import java.util.*;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/8/14 上午2:31
 */
@WebServlet(urlPatterns = "/pages/back/role/RoleServletBack/*")
public class RoleServletBack extends AbstractCRMServlet {
    private Role role = new Role();

    public Role getRole() {
        return role;
    }

    public String rm() {
        IRoleServiceBack roleServiceBack = (IRoleServiceBack) ServiceFactory.getInstance(RoleServiceBackImpl.class);
        if (super.isAction(22)) {
            try {
                String ids = super.request.getParameter("ids");
                Set<Integer> set = new HashSet<>();
                String result[] = ids.split("\\-");
                for (int x = 0; x < result.length; x++) {
                    set.add(Integer.parseInt(result[x]));
                }
                if (roleServiceBack.rm(super.getMid(),set)) {
                    super.setUrlAndMsg("role.list.servlet", "vo.rm.success.msg");
                } else {
                    super.setUrlAndMsg("role.list.servlet", "vo.rm.failure.msg");
                }
            } catch (Exception e) {
                super.setUrlAndMsg("role.list.servlet", "vo.rm.failure.msg");
                e.printStackTrace();
            }
            return "forward.page";
        } else {
            return "error.page";
        }
    }

    public String editPre() {
        if (super.isAction(21)) {
            IRoleServiceBack roleServiceBack = (IRoleServiceBack) ServiceFactory.getInstance(RoleServiceBackImpl.class);
            try {
                int rid = Integer.parseInt(super.request.getParameter("rid"));
                Map<String, Object> map = roleServiceBack.editPre(super.getMid(), rid);
                super.request.setAttribute("role", map.get("role"));
                super.request.setAttribute("allGroups", map.get("allGroups"));
                super.request.setAttribute("roleGroups", map.get("roleGroups"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "role.edit.page";
        } else {
            return "error.page";
        }
    }

    public String edit() {
        IRoleServiceBack roleServiceBack = (IRoleServiceBack) ServiceFactory.getInstance(RoleServiceBackImpl.class);
        if (super.isAction(21)) {
            try {
                List<Groups> all = new ArrayList<>();
                String gids[] = super.request.getParameterValues("gid");
                for (int x = 0; x < gids.length; x++) {
                    Groups g = new Groups();
                    g.setGid(Integer.parseInt(gids[x]));
                    all.add(g);
                }

                this.role.setGroups(all);//表示一个角色对应多个权限组信息

                if (roleServiceBack.edit(super.getMid(), this.role)) {
                    super.setUrlAndMsg("role.list.servlet", "vo.edit.success.msg");
                } else {
                    super.setUrlAndMsg("role.list.servlet", "vo.edit.failure.msg");
                }
            } catch (Exception e) {
                super.setUrlAndMsg("role.list.servlet", "vo.edit.failure.msg");
                e.printStackTrace();
            }
            return "forward.page";
        } else {
            return "error.page";
        }
    }

    public String show() {
        if (super.isAction(34)) {
            try {
                IRoleServiceBack roleServiceBack = (IRoleServiceBack) ServiceFactory.getInstance(RoleServiceBackImpl.class);
                int rid = Integer.parseInt(super.request.getParameter("rid"));
                super.request.setAttribute("role", roleServiceBack.show(super.getMid(), rid));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "role.show.page";
        } else {
            return "error.page";
        }
    }

    public String list() {
        IRoleServiceBack roleServiceBack = (IRoleServiceBack) ServiceFactory.getInstance(RoleServiceBackImpl.class);
        if (super.isAction(20)) {
            try {
                super.request.setAttribute("allRoles", roleServiceBack.list(super.getMid()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "role.list.page";
        } else {
            return "error.page";
        }

    }

    public String addPre() {
        IRoleServiceBack roleServiceBack = (IRoleServiceBack) ServiceFactory.getInstance(RoleServiceBackImpl.class);
        if (super.isAction(19)) {
            try {
                Map<String, Object> map = roleServiceBack.addPre(super.getMid());
                super.request.setAttribute("allGroups", map.get("allGroups"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "role.add.page";
        } else {
            return "error.page";
        }
    }

    public String add() {
        IRoleServiceBack roleServiceBack = (IRoleServiceBack) ServiceFactory.getInstance(RoleServiceBackImpl.class);
        if (super.isAction(19)) {
            try {
                List<Groups> all = new ArrayList<>();
                String gids[] = super.request.getParameterValues("gid");
                for (int x = 0; x < gids.length; x++) {
                    Groups g = new Groups();
                    g.setGid(Integer.parseInt(gids[x]));
                    all.add(g);
                }

                this.role.setGroups(all);//表示一个角色对应多个权限组信息

                if (roleServiceBack.add(super.getMid(), this.role)) {
                    super.setUrlAndMsg("role.add.servlet", "vo.add.success.msg");
                } else {
                    super.setUrlAndMsg("role.add.servlet", "vo.add.failure.msg");
                }
            } catch (Exception e) {
                super.setUrlAndMsg("role.add.servlet", "vo.add.failure.msg");
                e.printStackTrace();
            }
            return "forward.page";
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
        return "角色";
    }

    @Override
    public String getColumntData() {
        return null;
    }
}
