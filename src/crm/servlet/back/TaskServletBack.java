package crm.servlet.back;

import crm.service.back.ITaskServiceBack;
import crm.service.back.impl.TaskServiceBackImpl;
import crm.vo.Task;
import util.factory.ServiceFactory;
import util.split.SplitPageUtils;

import javax.servlet.annotation.WebServlet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/29 下午11:19
 */
@WebServlet(urlPatterns = "/pages/back/task/TaskServletBack/*")
public class TaskServletBack extends AbstractCRMServlet {
    private Task task = new Task();
    public Task getTask() {
        return task;
    }

    public String rm() {
        if (super.isAction(10)) {
            ITaskServiceBack taskServiceBack = (ITaskServiceBack) ServiceFactory.getInstance(TaskServiceBackImpl.class);
            try {
                String ids = super.request.getParameter("ids");
                Set<Integer> tids = new HashSet<>();
                String result[] = ids.split("\\-");
                for (int x = 0; x < result.length; x++) {
                    tids.add(Integer.parseInt(result[x]));
                }
                if (taskServiceBack.rmByMember(super.getMid(), tids)) {
                    super.setUrlAndMsg("task.list.servlet", "task.rm.success");
                } else {
                    super.setUrlAndMsg("task.list.servlet", "task.rm.failure");
                }
            } catch (Exception e) {
                e.printStackTrace();
                super.setUrlAndMsg("task.list.servlet", "task.rm.failure");
            }
        } else {
            return "error.page";
        }
        return "forward.page";
    }

    public String over() {
        if (super.isAction(9)) {
            ITaskServiceBack taskServiceBack = (ITaskServiceBack) ServiceFactory.getInstance(TaskServiceBackImpl.class);
            int tid = Integer.parseInt(super.request.getParameter("tid"));
            try {
                if (taskServiceBack.editOver(super.getMid(), tid)) {
                    super.setUrlAndMsg("task.list.servlet", "task.over.success");
                } else {
                    super.setUrlAndMsg("task.list.servlet", "task.over.failure");
                }
            } catch (Exception e) {
                e.printStackTrace();
                super.setUrlAndMsg("task.list.servlet", "task.over.failure");

            }
        } else {
            return "error.page";
        }
        return "forward.page";
    }
    public String finish() {
        if (super.isAction(8)) {
            ITaskServiceBack taskServiceBack = (ITaskServiceBack) ServiceFactory.getInstance(TaskServiceBackImpl.class);
            int tid = Integer.parseInt(super.request.getParameter("tid"));
            try {
                if (taskServiceBack.editFinish(super.getMid(), tid)) {
                    super.setUrlAndMsg("task.list.servlet", "task.finish.success");
                } else {
                    super.setUrlAndMsg("task.list.servlet", "task.finish.failure");
                }
            } catch (Exception e) {
                e.printStackTrace();
                super.setUrlAndMsg("task.list.servlet", "task.finish.failure");

            }
        } else {
            return "error.page";
        }
        return "forward.page";
    }

    public String editPre() {
        if (super.isAction(7)) {
            ITaskServiceBack taskServiceBack = (ITaskServiceBack) ServiceFactory.getInstance(TaskServiceBackImpl.class);
            try {
                int tid = Integer.parseInt(super.request.getParameter("tid"));
                super.request.setAttribute("task", taskServiceBack.editPre(super.getMid(), tid));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "task.edit.page";
        } else {
            return "error.page";
        }
    }

    public String edit() {
        ITaskServiceBack taskServiceBack = (ITaskServiceBack) ServiceFactory.getInstance(TaskServiceBackImpl.class);
        if (super.isAction(7)) {
            this.task.getMember().setMid(super.getMid());
            try {
                if (taskServiceBack.edit(this.task)) {
                    super.setUrlAndMsg("task.list.servlet", "vo.edit.success.msg");
                } else {
                    super.setUrlAndMsg("task.list.servlet", "vo.edit.failure.msg");
                }
            } catch (Exception e) {
                e.printStackTrace();
                super.setUrlAndMsg("task.list.servlet", "vo.edit.failure.msg");
            }
            return "forward.page";
        } else {
            return "error.page";
        }
    }

    public String listSplit() {
        ITaskServiceBack taskServiceBack = (ITaskServiceBack) ServiceFactory.getInstance(TaskServiceBackImpl.class);
        if (super.isAction(6)) {
            String urlKey = "task.list.servlet";
            SplitPageUtils spu = super.handleSplitParam();
            try {
                Map<String, Object> map = taskServiceBack.listByMember(super.getMid(), super.getVisit(), super.getTypes(), "name",spu.getKeyWord(), spu.getCurrentPage(), spu.getLineSize());
                // List<Client> all = (List<Client>) map.get("allClients");
                if (map != null) {
                    int taskCount = (Integer) map.get("taskCount");
                    super.request.setAttribute("allTasks", map.get("allTasks"));
                    super.request.setAttribute("allClients", map.get("allClients"));

                    super.request.setAttribute("type", super.getTypes());
                    super.request.setAttribute("visit", super.getVisit());
                    super.request.setAttribute("paramName", "type");
                    super.request.setAttribute("paramValue", String.valueOf(super.getTypes()));
                    super.request.setAttribute("paramNameB", "visit");
                    super.request.setAttribute("paramValueB", String.valueOf(super.getVisit()));

                    super.setSplitPage(urlKey, taskCount);
                } else {
                    super.request.setAttribute("type", super.getTypes());
                    super.request.setAttribute("visit", super.getVisit());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return "task.list.page";
        } else {
            return "error.page";
        }

    }
    public String show() {
        ITaskServiceBack taskServiceBack = (ITaskServiceBack) ServiceFactory.getInstance(TaskServiceBackImpl.class);
        if (super.isAction(31)) {
            try {
                int tid = Integer.parseInt(super.request.getParameter("tid"));
                int cid = Integer.parseInt(super.request.getParameter("cid"));
                super.request.setAttribute("task", taskServiceBack.show(super.getMid(),cid, tid));
            } catch (Exception e) {

            }
            return "task.show.page";
        } else {
            return "error.page";
        }
    }

    public String listByClient() {
        ITaskServiceBack taskServiceBack = (ITaskServiceBack) ServiceFactory.getInstance(TaskServiceBackImpl.class);
        if (super.isAction(30)) {
            try {
                int cid = Integer.parseInt(super.request.getParameter("cid"));
                Map<String, Object> map = taskServiceBack.listByMemberAndClient(super.getMid(), cid);
                super.request.setAttribute("allTasks", map.get("allTasks"));
                super.request.setAttribute("client", map.get("client"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "task.client.list.page";
        } else {
            return "error.page";
        }
    }
    public String addPre() {
        if (super.isAction(5)) {//如果该用户已有权限包括5号权限，
            return "task.add.page";
        } else {
            return "error.page";
        }
    }

    public String add() {
        ITaskServiceBack taskServiceBack = (ITaskServiceBack) ServiceFactory.getInstance(TaskServiceBackImpl.class);
        if (super.isAction(5)) {
            this.task.getMember().setMid(super.getMid());
            try {
                if (taskServiceBack.add(this.task)) {
                    super.setUrlAndMsg("task.add.servlet", "vo.add.success.msg");
                } else {
                    super.setUrlAndMsg("task.add.servlet", "vo.add.failure.msg");
                }
            } catch (Exception e) {
                e.printStackTrace();
                super.setUrlAndMsg("task.add.servlet", "vo.add.failure.msg");
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
        return "任务";
    }

    @Override
    public String getColumntData() {
        return null;
    }
}
