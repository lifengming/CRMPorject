package crm.service.back.impl;

import crm.dao.IActionDAO;
import crm.dao.impl.ActionDAOImpl;
import crm.service.back.AbstractCRMServiceBack;
import crm.service.back.IActionServiceBack;
import crm.vo.Action;
import util.factory.DAOFactory;

import java.util.List;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/8/13 下午9:53
 */
public class ActionServiceBackImpl extends AbstractCRMServiceBack implements IActionServiceBack {
    @Override
    public List<Action> list(String mid) throws Exception {
        IActionDAO actionDAO = DAOFactory.getInstance(ActionDAOImpl.class);
        if (super.isAction(mid, 25)) {
            return actionDAO.findAll();
        }
        return null;
    }
}
