package crm.servlet.back;

import com.sun.deploy.services.DefaultService;
import crm.service.back.IDefaultServiceBack;
import crm.service.back.impl.DefaultServiceBackImpl;
import util.factory.ServiceFactory;

import javax.servlet.annotation.WebServlet;
import java.util.Map;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/8/7 下午12:09
 */
@WebServlet(urlPatterns = "/pages/back/DefaultServletBack/*")
public class DefaultServletBack extends AbstractCRMServlet {
    public String show() {
        IDefaultServiceBack defaultServiceBack = (IDefaultServiceBack) ServiceFactory.getInstance(DefaultServiceBackImpl.class);
        try {
            Map<String, Object> map = defaultServiceBack.stat(super.getMid());
            super.request.setAttribute("allClients", map.get("allClients"));
            super.request.setAttribute("allTasks", map.get("allTasks"));
            super.request.setAttribute("clientCount", map.get("clientCount"));
            super.request.setAttribute("unFinishCount", map.get("unFinishCount"));
            super.request.setAttribute("waitFinishCount", map.get("waitFinishCount"));
            super.request.setAttribute("allNews", map.get("allNews"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "default.page";
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
        return null;
    }

    @Override
    public String getColumntData() {
        return null;
    }
}
