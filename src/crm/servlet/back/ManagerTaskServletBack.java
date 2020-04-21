package crm.servlet.back;

import crm.service.back.ITaskServiceBack;
import crm.service.back.impl.TaskServiceBackImpl;
import crm.vo.Task;
import util.factory.ServiceFactory;
import util.split.SplitPageUtils;

import javax.servlet.annotation.WebServlet;
import java.util.Map;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/29 下午11:19
 */
@WebServlet(urlPatterns = "/pages/back/mtask/ManagerTaskServletBack/*")
public class ManagerTaskServletBack extends AbstractCRMServlet {
    private Task task = new Task();
    public Task getTask() {
        return task;
    }

    public String listByClient() {
        ITaskServiceBack taskServiceBack = (ITaskServiceBack) ServiceFactory.getInstance(TaskServiceBackImpl.class);
        if (super.isAction(29)) {
            try {
                int cid = Integer.parseInt(super.request.getParameter("cid"));
                Map<String, Object> map = taskServiceBack.listByClient(super.getMid(), cid);
                super.request.setAttribute("allTasks", map.get("allTasks"));
                super.request.setAttribute("client", map.get("client"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "mtask.client.list.page";
        } else {
            return "error.page";
        }
    }

    public String listSplit() {
        ITaskServiceBack taskServiceBack = (ITaskServiceBack) ServiceFactory.getInstance(TaskServiceBackImpl.class);
        if (super.isAction(28)) {
            String urlKey = "mtask.list.servlet";
            SplitPageUtils spu = super.handleSplitParam();
            try {
                Map<String, Object> map = taskServiceBack.list(super.getMid(), super.getVisit(), super.getTypes(), "name",spu.getKeyWord(), spu.getCurrentPage(), spu.getLineSize());
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
            return "mtask.list.page";
        } else {
            return "error.page";
        }

    }
    public String show() {
        ITaskServiceBack taskServiceBack = (ITaskServiceBack) ServiceFactory.getInstance(TaskServiceBackImpl.class);
        if (super.isAction(32)) {
            try {
                int tid = Integer.parseInt(super.request.getParameter("tid"));
                super.request.setAttribute("task", taskServiceBack.show(super.getMid(), tid));
            } catch (Exception e) {

            }
            return "mtask.show.page";
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
