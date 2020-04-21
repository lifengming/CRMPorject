package crm.service.back.impl;

import crm.dao.IClientDAO;
import crm.dao.IMemberDAO;
import crm.dao.impl.ActionDAOImpl;
import crm.dao.impl.ClientDAOImpl;
import crm.dao.impl.MemberDAOImpl;
import crm.service.back.IClientServiceBack;
import crm.vo.Client;
import crm.vo.Member;
import util.factory.DAOFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/24 下午8:53
 */
public class ClientServiceBackImpl implements IClientServiceBack {
    @Override
    public boolean rmByMember(String mid, Set<Integer> cids) throws Exception {
        if (cids.size() == 0) {
            return false;
        }
        Member member = DAOFactory.getInstance(MemberDAOImpl.class).findById(mid);
        IClientDAO clientDAO = DAOFactory.getInstance(ClientDAOImpl.class);
        if (member.getLocked().equals(1)) {//该用户已经被锁定，非活跃用户
            return false;
        }
        //如果该用户是超级管理员，或者具备"客户修改"的权限，则可以进行客户信息的查询
        if (member.getFlag().equals(1)||DAOFactory.getInstance(ActionDAOImpl.class).findByRoleAndId(member.getRole().getRid(), 4) != null) {
            return clientDAO.doRemoveByMember(mid, cids);
        }
        return false;
    }

    @Override
    public Client editPre(String mid, Integer id) throws Exception {
        Member member = DAOFactory.getInstance(MemberDAOImpl.class).findById(mid);
        IClientDAO clientDAO = DAOFactory.getInstance(ClientDAOImpl.class);
        if (member.getLocked().equals(1)) {//该用户已经被锁定，非活跃用户
            return null;
        }
        //如果该用户是超级管理员，或者具备"客户修改"的权限，则可以进行客户信息的查询
        if (member.getFlag().equals(1)||DAOFactory.getInstance(ActionDAOImpl.class).findByRoleAndId(member.getRole().getRid(), 3) != null) {
            return clientDAO.findByMemberAndId(mid, id);
        }
        return null;
    }

    @Override
    public boolean edit(Client vo) throws Exception {
        Member member = DAOFactory.getInstance(MemberDAOImpl.class).findById(vo.getMember().getMid());
        IClientDAO clientDAO = DAOFactory.getInstance(ClientDAOImpl.class);
        if (member.getLocked().equals(1)) {//该用户已经被锁定，非活跃用户
            return false;
        }
        //如果该用户是超级管理员，或者具备"客户修改"的权限，则可以进行客户信息的查询
        if (member.getFlag().equals(1)||DAOFactory.getInstance(ActionDAOImpl.class).findByRoleAndId(member.getRole().getRid(), 3) != null) {
            return clientDAO.doUpdateByMember(vo);
        }
        return false;

    }

    @Override
    public Map<String, Object> listByMemberAndType(String mid, Integer type, String keyWord, String column, Integer currentPage, Integer lineSize) throws Exception {
        Member member = DAOFactory.getInstance(MemberDAOImpl.class).findById(mid);
        if (member.getFlag().equals(1)) { //超级管理员
            Map<String, Object> map = new HashMap<>();
            map.put("allClients", DAOFactory.getInstance(ClientDAOImpl.class).findAllSplitByMemberAndTpye(mid, type, keyWord, column, currentPage, lineSize));
            map.put("clientCount", DAOFactory.getInstance(ClientDAOImpl.class).getAllCountByMemberAndTpye(mid, type, column, keyWord));
            return map;
        } else {//需要查询用户是否具有指定权限
            if (member.getLocked().equals(1)) {//该用户已经被锁定，非活跃用户
                return null;
            } else {
                //根据指定角色和权限编号，存在对应权限，
                if (DAOFactory.getInstance(ActionDAOImpl.class).findByRoleAndId(member.getRole().getRid(), 2) != null) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("allClients", DAOFactory.getInstance(ClientDAOImpl.class).findAllSplitByMemberAndTpye(mid, type, keyWord, column, currentPage, lineSize));
                    map.put("clientCount", DAOFactory.getInstance(ClientDAOImpl.class).getAllCountByMemberAndTpye(mid, type, column, keyWord));
                    return map;
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public boolean add(Client vo) throws Exception {
        vo.setReg(new Date());//注册日期为当前系统的日期时间
        IClientDAO clientDAO = DAOFactory.getInstance(ClientDAOImpl.class);
        //1 需要查询该用户当前是否可用，是锁定状态还是活跃状态，需要用户locked状态(locked)
        //2 需要查询该用户是否具备有制定权限，
        //3 超级管理员不受限，不用进行权限检查 member.flag==1
        Member member = DAOFactory.getInstance(MemberDAOImpl.class).findById(vo.getMember().getMid());
        if (member.getFlag().equals(1)) { //超级管理员
            return clientDAO.doCreate(vo);
        } else {//需要查询用户是否具有指定权限
            if (member.getLocked().equals(1)) {//该用户已经被锁定，非活跃用户
                return false;
            } else {
                //根据指定角色和权限编号，存在对应权限，
                if (DAOFactory.getInstance(ActionDAOImpl.class).findByRoleAndId(member.getRole().getRid(), 1) != null) {
                    return clientDAO.doCreate(vo);
                } else {
                    return false;
                }
            }
        }
    }
}
