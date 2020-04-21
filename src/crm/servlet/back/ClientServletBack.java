package crm.servlet.back;

import crm.service.back.IClientServiceBack;
import crm.service.back.IMemberServiceBack;
import crm.service.back.impl.ClientServiceBackImpl;
import crm.service.back.impl.MemberServiceBackImpl;
import crm.vo.Client;
import util.factory.ServiceFactory;
import util.split.SplitPageUtils;


import javax.servlet.annotation.WebServlet;
import java.util.*;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/24 下午9:00
 */
@WebServlet(urlPatterns = "/pages/back/client/ClientServletBack/*")
public class ClientServletBack extends AbstractCRMServlet {
    private Client client = new Client();

    public Client getClient() {
        return client;
    }
    IClientServiceBack clientServiceBack = (IClientServiceBack) ServiceFactory.getInstance(ClientServiceBackImpl.class);

    public String rm() {
        String cid = super.request.getParameter("ids");
        String result[] = cid.split("-");
        Set<Integer> set = new HashSet<>();
        for (int x = 0; x < result.length; x++) {
            set.add(Integer.parseInt(result[x]));
        }
        try {
            if (clientServiceBack.rmByMember(super.getMid(), set)) {
                super.setUrlAndMsg("client.list.servlet", "vo.rm.success.msg");
            }else{
                super.setUrlAndMsg("client.list.servlet","vo.rm.failure.msg");
            }
        } catch (Exception e) {
            e.printStackTrace();
            super.setUrlAndMsg("client.list.servlet","vo.rm.failure.msg");
        }
        return "forward.page";

    }
    public String editPre() {
        try {
            super.request.setAttribute("client",clientServiceBack.editPre(super.getMid(),this.client.getCid()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "client.edit.page";
    }
    public String edit() {
        //进行数据更新的时候，一定要将当前用户mid传给client,因为底层更新需要从client获取这个值，
        this.client.getMember().setMid(super.getMid());
        try {
            if (clientServiceBack.edit(this.client)) {
                super.setUrlAndMsg("client.list.servlet", "vo.edit.success.msg");
            } else {
                super.setUrlAndMsg("client.list.servlet","vo.edit.failure.msg");
            }
        } catch (Exception e) {
            e.printStackTrace();
            super.setUrlAndMsg("client.list.servlet","vo.edit.failure.msg");
        }
        return "forward.page";
    }


    public String listSplit() {
        if (super.isAction(2)) {

            String urlKey = "client.list.servlet";
            SplitPageUtils spu = super.handleSplitParam();
            try {
                Map<String, Object> map = clientServiceBack.listByMemberAndType(super.getMid(), super.getTypes(), spu.getKeyWord(), spu.getColumn(), spu.getCurrentPage(), spu.getLineSize());
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
            return "client.list.page";
        } else {
            return "error.page";
        }
    }

    public String addPre() {
        if (super.isAction(1)) {//如果该用户已有权限包括1号权限，
            return "client.add.page";
        } else {
            return "error.page";
        }

    }

    public String add() {
        if (super.isAction(1)) {
            String mid = super.getSession().getAttribute("mid").toString();
            //此处，因为client中其他属性会通过DispatcherServlet自动将表单中的内容赋值到client中，但表单没有mid的内容，
            //即mid内容无法自动设置到client中，所以需要在这里单独设置进该属性的值，否则client信息增加不完整，数据库信息更新不完整！！！
            this.client.getMember().setMid(mid);
            try {
                if (clientServiceBack.add(client)) {
                    super.setUrlAndMsg("client.add.servlet", "vo.add.success.msg");
                } else {
                    super.setUrlAndMsg("client.add.servlet", "vo.add.failure.msg");
                }
            } catch (Exception e) {
                e.printStackTrace();
                super.setUrlAndMsg("client.add.servlet", "vo.add.failure.msg");
            }
            return "forward.page";
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
