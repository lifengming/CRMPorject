package crm.service.back.impl;

import crm.dao.IClientDAO;
import crm.dao.INewsDAO;
import crm.dao.ITaskDAO;
import crm.dao.impl.ClientDAOImpl;
import crm.dao.impl.NewsDAOImpl;
import crm.dao.impl.TaskDAOImpl;
import crm.service.back.AbstractCRMServiceBack;
import crm.service.back.IDefaultServiceBack;
import util.DateCompare;
import util.factory.DAOFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/8/7 上午11:34
 */
public class
DefaultServiceBackImpl extends AbstractCRMServiceBack implements IDefaultServiceBack {
    @Override
    public Map<String, Object> stat(String mid) throws Exception {
        Map<String, Object> map = new HashMap<>();
        ITaskDAO taskDAO = DAOFactory.getInstance(TaskDAOImpl.class);
        IClientDAO clientDAO = DAOFactory.getInstance(ClientDAOImpl.class);
        INewsDAO newsDAO = DAOFactory.getInstance(NewsDAOImpl.class);
        map.put("allClients", clientDAO.findAllSplitByMemberAndTpye(mid, -1, "", "name", 1, 6));
        map.put("allTasks", taskDAO.findAllSplitByMember(mid, -1, -1, "title", "", 1, 7));
        map.put("clientCount", clientDAO.getAllCountByMemberAndTpye(mid, -1, "name", ""));
        map.put("unFinishCount", taskDAO.getAllCountByBeforeUnFinish(mid, DateCompare.getCurrentDate()));
        map.put("waitFinishCount", taskDAO.getAllCountByAfterFinish(mid, DateCompare.getCurrentDate()));
        map.put("allNews", newsDAO.findAllSplit("title", "", 1, 9));
        return map;
    }

}
