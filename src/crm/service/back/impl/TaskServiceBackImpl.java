package crm.service.back.impl;

import crm.dao.IClientDAO;
import crm.dao.ITaskDAO;
import crm.dao.impl.ClientDAOImpl;
import crm.dao.impl.TaskDAOImpl;
import crm.service.back.AbstractCRMServiceBack;
import crm.service.back.ITaskServiceBack;
import crm.vo.Client;
import crm.vo.Task;
import util.DateCompare;
import util.factory.DAOFactory;

import java.util.*;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/29 下午11:00
 */
public class TaskServiceBackImpl extends AbstractCRMServiceBack implements ITaskServiceBack {
    @Override
    public boolean rmByMember(String mid, Set<Integer> tids) throws Exception {
        ITaskDAO taskDAO = DAOFactory.getInstance(TaskDAOImpl.class);
        if (tids.size() == 0 || tids == null) {
            return false;
        }
        if (super.isAction(mid, 10)) {
            return taskDAO.doRemoveByMember(mid, tids);
        }
        return false;
    }

    @Override
    public boolean editOver(String mid, Integer tid) throws Exception {
        ITaskDAO taskDAO = DAOFactory.getInstance(TaskDAOImpl.class);
        if (super.isAction(mid, 9)) {
            return taskDAO.doUpdateByStatus(mid, tid, 0);
        }
        return false;
    }

    @Override
    public boolean editFinish(String mid, Integer tid) throws Exception {
        ITaskDAO taskDAO = DAOFactory.getInstance(TaskDAOImpl.class);
        if (super.isAction(mid, 8)) {
            return taskDAO.doUpdateByStatus(mid, tid, 2);
        }
        return false;
    }

    @Override
    public boolean edit(Task vo) throws Exception {
        ITaskDAO taskDAO = DAOFactory.getInstance(TaskDAOImpl.class);
        if (!DateCompare.compare(vo.getTdate())) {
            return false;
        }
        if (super.isAction(vo.getMember().getMid(), 7)) {
            return taskDAO.doUpdateByMember(vo);
        }
        return false;
    }

    @Override
    public Task editPre(String mid, Integer tid) throws Exception {
        ITaskDAO taskDAO = DAOFactory.getInstance(TaskDAOImpl.class);
        if (super.isAction(mid, 7)) {
            return taskDAO.findById(mid, tid);
        }
        return null;
    }

    @Override
    public Map<String, Object> listByClient(String mid, Integer cid) throws Exception {
        Map<String, Object> map = new HashMap<>();
        ITaskDAO taskDAO = DAOFactory.getInstance(TaskDAOImpl.class);
        IClientDAO clientDAO = DAOFactory.getInstance(ClientDAOImpl.class);
        if (super.isAction(mid, 29)) {
            map.put("allTasks", taskDAO.findAllByClient(cid));
            map.put("client", clientDAO.findById(cid));
            return map;
        }
        return null;
    }

    @Override
    public Task show(String mid, Integer tid) throws Exception {
        ITaskDAO taskDAO = DAOFactory.getInstance(TaskDAOImpl.class);
        if (super.isAction(mid, 32)) {
            return taskDAO.findById( tid);
        }
        return null;
    }

    @Override
    public Map<String, Object> list(String mid,Integer visit, Integer type, String column, String keyword, Integer currentPage, Integer lineSize) throws Exception {
        Map<String, Object> map = new HashMap<>();
        ITaskDAO taskDAO = DAOFactory.getInstance(TaskDAOImpl.class);
        IClientDAO clientDAO = DAOFactory.getInstance(ClientDAOImpl.class);
        List<Task> allTasks=taskDAO.findAllSplit( visit, type, column, keyword, currentPage, lineSize);
        Set<Integer> cids = new HashSet<>();
        if (allTasks != null) {
            Iterator<Task> iter = allTasks.iterator();
            while (iter.hasNext()) {
                cids.add(iter.next().getClient().getCid());
            }
            if (super.isAction(mid, 28)) {
                map.put("allTasks",allTasks);
                map.put("taskCount", taskDAO.getAllCount( visit, type, column, keyword));
                map.put("allClients", clientDAO.findByIds(cids));
                return map;
            }
        }
        return null;
    }

    @Override
    public Map<String, Object> listByMember(String mid, Integer visit, Integer type, String column, String keyword, Integer currentPage, Integer lineSize) throws Exception {
        Map<String, Object> map = new HashMap<>();
        ITaskDAO taskDAO = DAOFactory.getInstance(TaskDAOImpl.class);
        IClientDAO clientDAO = DAOFactory.getInstance(ClientDAOImpl.class);
        List<Task> allTasks=taskDAO.findAllSplitByMember(mid, visit, type, column, keyword, currentPage, lineSize);
        Set<Integer> cids = new HashSet<>();
        if (allTasks != null) {
            Iterator<Task> iter = allTasks.iterator();
            while (iter.hasNext()) {
                cids.add(iter.next().getClient().getCid());
            }
            if (super.isAction(mid, 6)) {
                map.put("allTasks",allTasks);
                map.put("taskCount", taskDAO.getAllCountByMember(mid, visit, type, column, keyword));
                map.put("allClients", clientDAO.findByIds(cids));
                return map;
            }
        }
        return null;
    }

    @Override
    public Task show(String mid, Integer cid, Integer tid) throws Exception {
        ITaskDAO taskDAO = DAOFactory.getInstance(TaskDAOImpl.class);
        IClientDAO clientDAO = DAOFactory.getInstance(ClientDAOImpl.class);
        if (super.isAction(mid, 31)) {
            return taskDAO.findById(mid, cid, tid);
        }
        return null;
    }

    @Override
    public Map<String, Object> listByMemberAndClient(String mid, Integer cid) throws Exception {
        Map<String, Object> map = new HashMap<>();
        ITaskDAO taskDAO = DAOFactory.getInstance(TaskDAOImpl.class);
        IClientDAO clientDAO = DAOFactory.getInstance(ClientDAOImpl.class);
        if (super.isAction(mid, 30)) {
            map.put("allTasks", taskDAO.findAllMemberAndClient(mid, cid));
            map.put("client", clientDAO.findByMemberAndId(mid, cid));
            return map;
        }
        return null;
    }

    @Override
    public boolean add(Task vo) throws Exception {
        ITaskDAO taskDAO = DAOFactory.getInstance(TaskDAOImpl.class);
        IClientDAO clientDAO = DAOFactory.getInstance(ClientDAOImpl.class);
        if (!DateCompare.compare(vo.getTdate())) {
            return false;
        }
        //1 该用户具备"添加任务"的权限
        if (super.isAction(vo.getMember().getMid(), 5)) {
            //2 根据指定的用户名和客户编号，判断该客户是否属于指定的用户
            if (clientDAO.findExistsByMemberAndCid(vo.getMember().getMid(), vo.getClient().getCid())) {
                vo.setStatus(1);//任务状态设置为"任务进行" 状态
                return taskDAO.doCreate(vo);
            }
        }
        return false;
    }
}
