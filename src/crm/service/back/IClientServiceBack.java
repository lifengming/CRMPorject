package crm.service.back;

import crm.vo.Client;

import java.util.Map;
import java.util.Set;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/24 下午8:47
 */
public interface IClientServiceBack {
    /**
     * 实现客户信息的增加处理，本操作实现如下功能:<br>
     * <li>判断当前处理用户是否具备有指定的权限</li>
     * <li>如果权限存在，就要进行数据的保存</li>
     * @param vo 包含客户信息的vo类
     * @return 增加成功返回true,否则返回false
     * @throws Exception
     */
    public boolean add(Client vo) throws Exception;

    /**
     * 根据用户及数据类型，实现数据的查询处理操作，本操作实现如下的调用:<br>
     * <li>调用IClientDAO.findAllSplitByMemberAndTpye()查询数据</li>
     * <li>调用IClientDAO.getAllCountByMemberAndTpye()查询数据量</li>
     * @param mid
     * @param type
     * @param keyWord
     * @param column
     * @param currentPage
     * @param lineSize
     * @return 返回包含以下数据:<br>
     * <Li>key=allClients,value=IClientDAO.findAllSplitByMemberAndTpye()</Li>
     * <Li>key=clientCount,value=IClientDAO.getAllCountByMemberAndTpye()</Li>
     * @throws Exception
     */
    public Map<String, Object> listByMemberAndType(String mid, Integer type,String keyWord, String column,Integer currentPage, Integer lineSize) throws Exception;

    /**
     * 查询属于某一个用户指定的客户信息，在进行查询前，一定要针对于当前用户的权限进行验证处理！
     * @param mid 用户编号
     * @param id 客户编号
     * @return 查询到的一个客户的全部信息
     * @throws Exception
     */
    public Client editPre(String mid, Integer id) throws Exception;

    /**
     * 根据mid和cid,更新指定客户信息,需要进行权限信息的验证处理
     * @param vo 包含mid和cid信息的vo
     * @return
     * @throws Exception
     */
    public boolean edit(Client vo) throws Exception;

    /**
     * 删除指定用户对应的客户信息，删除之前需要进行相关权限的判断
     * @param mid 用户编号
     * @param cids 客户编号集合
     * @return
     * @throws Exception
     */
    public boolean rmByMember(String mid, Set<Integer> cids) throws Exception;









}
