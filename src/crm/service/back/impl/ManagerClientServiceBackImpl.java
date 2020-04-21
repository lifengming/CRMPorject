package crm.service.back.impl;

import crm.dao.impl.ActionDAOImpl;
import crm.dao.impl.ClientDAOImpl;
import crm.dao.impl.MemberDAOImpl;
import crm.service.back.AbstractCRMServiceBack;
import crm.service.back.IManagerClientServiceBack;
import crm.vo.Client;
import crm.vo.Member;
import util.factory.DAOFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/28 下午3:13
 */
public class ManagerClientServiceBackImpl extends AbstractCRMServiceBack implements IManagerClientServiceBack {
    @Override
    public Client show(String mid, Integer cid) throws Exception {
        if (super.isAction(mid, 27)) {
          return DAOFactory.getInstance(ClientDAOImpl.class).findById(cid);
        }
        return null;
    }

    @Override
    public Map<String, Object> listByType(String mid,Integer type, String keyWord, String column, Integer currentPage, Integer lineSize) throws Exception {
        if (super.isAction(mid,26))
        {
            Map<String, Object> map = new HashMap<>();
            map.put("allClients", DAOFactory.getInstance(ClientDAOImpl.class).findAllSplitByType( type, keyWord, column, currentPage, lineSize));
            map.put("clientCount", DAOFactory.getInstance(ClientDAOImpl.class).getAllCountByType( type,keyWord, column));
            return map;
        }
        return null;
    }
}
