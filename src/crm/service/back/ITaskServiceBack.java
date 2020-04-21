package crm.service.back;

import crm.vo.Client;
import crm.vo.Task;

import java.util.Map;
import java.util.Set;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/29 下午10:21
 */
public interface ITaskServiceBack {
    /**
     * 删除指定用户的指定任务,调用ITaskDAO.doRemoveByMember()方法
     * @param mid
     * @param tids
     * @return
     * @throws Exception
     */
    public boolean rmByMember(String mid, Set<Integer> tids) throws Exception;
    /**
     * 任务关闭操作，调用ITaskDAO.UpdateByStatus()方法
     * @param mid
     * @param tid
     * @return
     * @throws Exception
     */
    public boolean editOver(String mid, Integer tid) throws Exception;
    /**
     * 任务完成操作，调用ITaskDAO.UpdateByStatus()方法
     * @param mid
     * @param tid
     * @return
     * @throws Exception
     */
    public boolean editFinish(String mid, Integer tid) throws Exception;
    /**
     * 任务的更新处理操作，依然需要考虑到时间问题,就是在My97Date组件里，选择的时间不能是过去的时间，否则会报错
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean edit(Task vo) throws Exception;
    /**
     * 数据更新前的显示查询，在查询过程中，一定只能够查询指定用户的任务
     * @param mid
     * @param tid
     * @return
     * @throws Exception
     */
    public Task editPre(String mid, Integer tid) throws Exception;
    /**
     * 根据用户编号，查询指定客户所有的回访任务信息，本操作执行如下功能:<br>
     * <LI>调用ITaskDAO.findAllByClient()查询全部的任务信息</LI>
     * <LI>调用IClientDAO.findById()查询对应客户的全部信息</LI>
     * @param mid 权限验证使用
     * @param cid
     * @return 本操作返回以下内容:<br>
     * <li>key=allTasks,value=ITaskDAO.findAllByClient()</li>
     * <li>key=client,value=IClientDAO.findById()</li>
     * @throws Exception
     */
    public Map<String, Object> listByClient(String mid, Integer cid) throws Exception;

    /**
     * 管理员根据任务编号查询指定任务详情
     * @param mid 权限验证使用
     * @param tid 查询任务详情使用
     * @return
     * @throws Exception
     */
    public Task show(String mid, Integer tid) throws Exception;

    /**
     * 进行指定客户的任务创建，需要客户的编号，本操作需要执行如下功能:<br>
     * <li>必须保证创建任务的用户具备此类权限</li>
     * <li>必须判断指定的客户编号是否存在，即保证客户的编号就是该用户的客户</li>
     * <li>创建任务</li>
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean add(Task vo) throws Exception;

    /**
     * 根据用户编号，查询指定客户所有的回访任务信息，本操作执行如下功能:<br>
     * <LI>调用ITaskDAO.findAllMemberAndClient()查询全部的任务信息</LI>
     * <LI>调用IClientDAO.findByMemberAndId()查询对应客户的全部信息</LI>
     * @param mid
     * @param cid
     * @return 本操作返回以下内容:<br>
     * <li>key=allTasks,value=ITaskDAO.findAllMemberAndClient()</li>
     * <li>key=client,value=IClientDAO.findByMemberAndId()</li>
     * @throws Exception
     */
    public Map<String, Object> listByMemberAndClient(String mid, Integer cid) throws Exception;

    /**
     * 根据用户名称，客户名称，任务名称查询一个任务的所有信息
     * @param mid
     * @param cid
     * @param tid
     * @return
     * @throws Exception
     */
    public Task show(String mid, Integer cid, Integer tid) throws Exception;

    /**
     * 查询指定用户的全部任务信息，分页显示
     * @param mid
     * @param visit
     * @param type
     * @param column
     * @param keyword
     * @param currentPage
     * @param lineSize
     * @return 返回数据包括如下信息:<br>
     * <li>key=allTasks,value=ITaskDAO.findAllSplitByMember()</li>
     * <li>key=taskCount,value=ITaskDAO.getAllCountByMember()</li>
     * @throws Exception
     */
    public Map<String, Object> listByMember(String mid, Integer visit, Integer type, String column, String keyword, Integer currentPage, Integer lineSize) throws Exception;


    /**
     * 查询指定用户的全部任务信息，分页显示
     * @param visit
     * @param type
     * @param column
     * @param keyword
     * @param currentPage
     * @param lineSize
     * @return 返回数据包括如下信息:<br>
     * <li>key=allTasks,value=ITaskDAO.findAllSplit()</li>
     * <li>key=taskCount,value=ITaskDAO.getAllCount()</li>
     * @throws Exception
     */
    public Map<String, Object> list(String mid,Integer visit, Integer type, String column, String keyword, Integer currentPage, Integer lineSize) throws Exception;


}
