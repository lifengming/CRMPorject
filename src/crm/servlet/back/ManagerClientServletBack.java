package crm.servlet.back;

import crm.service.back.IManagerClientServiceBack;
import crm.service.back.impl.ManagerClientServiceBackImpl;
import crm.vo.Client;
import util.factory.ServiceFactory;
import util.split.SplitPageUtils;

import javax.servlet.annotation.WebServlet;
import java.util.Map;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/28 下午3:30
 */
@WebServlet(urlPatterns = "/pages/back/mclient/ManagerClientServletBack/*")
public class ManagerClientServletBack extends AbstractCRMServlet {
    private Client client = new Client();

    public Client getClient() {
        return client;
    }
    IManagerClientServiceBack managerClientServiceBack = (IManagerClientServiceBack) ServiceFactory.getInstance(ManagerClientServiceBackImpl.class);

    public String show() {
        try {
            super.request.setAttribute("client", managerClientServiceBack.show(super.getMid(), this.client.getCid()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "mclient.show.page";
    }
    public String listSplit() {
        if (super.isAction(26)) {
            String urlKey = "mclient.list.servlet";
            SplitPageUtils spu = super.handleSplitParam();
            try {
                Map<String, Object> map = managerClientServiceBack.listByType(super.getMid(), super.getTypes(), spu.getKeyWord(), spu.getColumn(), spu.getCurrentPage(), spu.getLineSize());
                // List<Client> all = (List<Client>) map.get("allClients");
                super.request.setAttribute("type", super.getTypes());
                int clientCount = (Integer) map.get("clientCount");
                super.request.setAttribute("allClients", map.get("allClients"));
                super.request.setAttribute("paramName", "type");
                super.request.setAttribute("paramValue", String.valueOf(super.getTypes()));
                super.setSplitPage(urlKey, clientCount);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "mclient.list.page";
        } else {
            return "error.page";
        }
    }
    @Override
    public String getDefaultColumn() {
        return "name";
    }

    @Override
    public String getUploadDir() {
        return null;
    }

    @Override
    public String getType() {
        return "客户";
    }

    @Override
    public String getColumntData() {
        return "顾客姓名:name|联系邮箱:email|联系电话:tel|联系QQ:qq";
    }
}
