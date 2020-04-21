package crm.service.back.impl;

import crm.dao.*;
import crm.dao.impl.*;
import crm.service.back.AbstractCRMServiceBack;
import crm.service.back.IMemberServiceBack;
import crm.vo.*;
import util.factory.DAOFactory;

import java.sql.Connection;
import java.util.*;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/21 下午3:02
 */
public class MemberServiceBackImpl extends AbstractCRMServiceBack implements IMemberServiceBack {
    @Override
    public boolean rm(String mid, Set<String> ids) throws Exception {
        IMemberDAO memberDAO = DAOFactory.getInstance(MemberDAOImpl.class);
        if (ids.size() == 0) {
            return false;
        }
        if (super.isAction(mid, 18)) {
            return memberDAO.doRemove(ids);
        }
        return false;
    }

    @Override
    public boolean editPasswordByAdmin(String mid, Member vo) throws Exception {
        IMemberDAO memberDAO = DAOFactory.getInstance(MemberDAOImpl.class);
        if (super.isAction(mid, 17)) {
            return memberDAO.doUpdatePassword(vo.getMid(), vo.getPassword());
        }
        return false;
    }

    @Override
    public Map<String, Object> editPre(String mid,String umid) throws Exception {
        Map<String, Object> map = new HashMap<>();
        IRoleDAO roleDAO = DAOFactory.getInstance(RoleDAOImpl.class);
        IMemberDAO memberDAO = DAOFactory.getInstance(MemberDAOImpl.class);
        if (super.isAction(mid, 17)) {
            map.put("allRoles", roleDAO.findAll());
            map.put("member", memberDAO.findById(umid));
        }
        return map;
    }

    @Override
    public Boolean edit(String mid, Member vo) throws Exception {
        IMemberDAO memberDAO = DAOFactory.getInstance(MemberDAOImpl.class);
        if (super.isAction(mid, 17)) {
            return memberDAO.doUpdate(vo);
        }
        return false;
    }

    @Override
    public Map<String, Object> list(String mid, String column, String keyword, Integer currentPage, Integer lineSize) throws Exception {
        Map<String, Object> map = new HashMap<>();
        IMemberDAO memberDAO = DAOFactory.getInstance(MemberDAOImpl.class);
        if (super.isAction(mid, 16)) {
            map.put("allMembers", memberDAO.findAllSplit(column, keyword, currentPage, lineSize));
            map.put("memberCount", memberDAO.getAllCount(column, keyword));
        }
        return map;
    }

    @Override
    public boolean add(String mid, Member vo) throws Exception {
        IMemberDAO memberDAO = DAOFactory.getInstance(MemberDAOImpl.class);
        if (super.isAction(mid, 15)) {
            if (memberDAO.findById(vo.getMid()) == null) {
                vo.setLocked(0);
                vo.setFlag(0);//普通用户
                return memberDAO.doCreate(vo);
            }
        }
        return false;
    }

    @Override
    public Map<String, Object> addPre(String mid) throws Exception {
        Map<String, Object> map = new HashMap<>();
        IRoleDAO roleDAO = DAOFactory.getInstance(RoleDAOImpl.class);
        if (super.isAction(mid, 15)) {
            map.put("allRoles", roleDAO.findAll());
        }
        return map;
    }

    @Override
    public boolean editPassword(String mid, String newPas, String oldPas) throws Exception {
        Member vo = new Member();
        vo.setMid(mid);
        vo.setPassword(oldPas);
        IMemberDAO memberDAO = DAOFactory.getInstance(MemberDAOImpl.class);
        if (memberDAO.findLogin(vo)) {
            return memberDAO.doUpdatePassword(mid, newPas);
        }
        return false;
    }

    @Override
    public Map<String, Object> login(Member vo) throws Exception {
        Map<String, Object> map = new HashMap<>();
        boolean flag = false;
        IMemberDAO memberDAO = DAOFactory.getInstance(MemberDAOImpl.class);
        ILogsDAO logsDAO = DAOFactory.getInstance(LogsDAOImpl.class);
        IGroupsDAO groupsDAO = DAOFactory.getInstance(GroupsDAOImpl.class);
        IActionDAO actionDAO = DAOFactory.getInstance(ActionDAOImpl.class);
        IMemberNewsDAO memberNewsDAO = DAOFactory.getInstance(MemberNewsDAOImpl.class);
        if (memberDAO.findLogin(vo)) {//1，登陆成功
            if (memberDAO.doUpdateLastdate(vo.getMid())) {//2，把当前时间设置进数据库
                map.put("Unread", memberNewsDAO.getAllCountUnread(vo.getMid()));
                Logs logs = new Logs();
                logs.getMember().setMid(vo.getMid());
                if (logsDAO.doCreate(logs)) {//3，日志创建成功
                    Set<Integer> gids = new HashSet<>();
                    List<Groups> groupsList = groupsDAO.findAllByRole(vo.getRole().getRid());
                    Iterator<Groups> iter = groupsList.iterator();
                    while (iter.hasNext()) {
                        Groups groups = iter.next();
                        gids.add(groups.getGid());
                        //将权限信息保存在权限组里
                        groups.setActions(actionDAO.findAllByGroups(groups.getGid()));

//                        List<Action> actionList = actionDAO.findAllByGroups(groups.getGid());
//                        Iterator<Action> actionIterator = actionList.iterator();
//                        while (actionIterator.hasNext()) {
//                            Action action = actionIterator.next();
//                        }
                    }
                    //将权限组信息保存在角色里
                    vo.getRole().setGroups(groupsList);
                    flag = true;
                    map.put("allActions", actionDAO.findAllByGroups(gids));
                }
            }
        }
        map.put("flag", flag);
        return map;
    }
}
