package crm.servlet.back;

import crm.service.back.AbstractCRMServiceBack;
import crm.service.back.IActionServiceBack;
import crm.service.back.impl.ActionServiceBackImpl;
import crm.vo.Action;
import util.factory.ServiceFactory;

import javax.servlet.annotation.WebServlet;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/8/13 下午10:02
 */
@WebServlet(urlPatterns = "/pages/back/action/ActionServletBack/*")
public class ActionServletBack extends AbstractCRMServlet {
    private Action action = new Action();

    public Action getAction() {
        return action;
    }

    IActionServiceBack actionServiceBack = (IActionServiceBack) ServiceFactory.getInstance(ActionServiceBackImpl.class);

    public String list() {
        if (super.isAction(25)) {
            try {
                super.request.setAttribute("allActions", actionServiceBack.list(super.getMid()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "action.list.page";
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
        return "权限";
    }

    @Override
    public String getColumntData() {
        return null;
    }
}
