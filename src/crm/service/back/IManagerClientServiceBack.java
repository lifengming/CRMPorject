package crm.service.back;

import crm.vo.Client;

import java.util.Map;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/28 下午3:00
 */
public interface IManagerClientServiceBack {
    /**
     *根据客户数据类型，实现数据的查询处理操作，本操作实现如下的调用:<br>
     *<li>调用IClientDAO.findAllSplitByTpye()查询数据</li>
     *<li>调用IClientDAO.getAllCountByTpye()查询数据量</li>
     * @param mid
     * @param type
     * @param keyWord
     * @param column
     * @param currentPage
     * @param lineSize
     * @return 返回包含以下数据:<br>
     *<Li>key=allClients,value=IClientDAO.findAllSplitByTpye()</Li>
     *<Li>key=clientCount,value=IClientDAO.getAllCountByTpye()</Li>
     * @throws Exception
     */
    public Map<String, Object> listByType( String mid,Integer type, String keyWord, String column, Integer currentPage, Integer lineSize) throws Exception;

    /**
     * 根据客户编号查询出客户完整信息
     * @param mid 进行相关的权限检测
     * @param cid 客户编号
     * @return
     * @throws Exception
     */
    public Client show(String mid, Integer cid) throws Exception;
}
