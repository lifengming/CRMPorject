package crm.service.back;

import crm.dao.impl.ActionDAOImpl;
import crm.dao.impl.MemberDAOImpl;
import crm.vo.Member;
import util.factory.DAOFactory;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/28 下午6:08
 */
public abstract class AbstractCRMServiceBack {
    public boolean isAction(String mid, Integer actid) throws Exception {
        Member member = DAOFactory.getInstance(MemberDAOImpl.class).findById(mid);
        if (member.getLocked().equals(1)) {//该用户已经被锁定，非活跃用户
            return false;
        }
        if (member.getFlag().equals(1) || DAOFactory.getInstance(ActionDAOImpl.class).findByRoleAndId(member.getRole().getRid(), actid) != null) {
            return true;
        }
        return false;
    }
}
