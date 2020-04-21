package crm.service.back;

import java.util.Map;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/8/7 上午3:19
 */
public interface IDefaultServiceBack {
    /** 用户工作台的统计信息
     * @param mid
     * @return 返回信息包含如下内容：<br>
     * <li>key=allClients,value=IClientDAO.findAllSplitByMemberAndTpye(),设置type为-1</li>
     * <li>key=allTasks,value=ITaskDAO.findAllSplitByMember()</li>
     * <li>key=clientCount,value=IClientDAO.getAllCountByMemberAndTpye(),设置type为-1</li>
     * <li>key=unFinishCount,value=ITaskDAO.getAllCountByBeforeUnFinish()</li>
     * <li>key=waitFinishCount,value=ITaskDAO.getAllCountByAfterFinish()</li>
     * <li></li>
     * @throws Exception
     */
    public Map<String, Object> stat(String mid) throws Exception;
}
