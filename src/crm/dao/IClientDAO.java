package crm.dao;

import crm.vo.Client;
import util.dao.IDAO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/24 下午8:28
 */
public interface IClientDAO extends IDAO<Integer, Client> {
    /**
     * 根据用户名称及任务状态，统计出数据量
     * @param mid
     * @param status
     * @return
     * @throws Exception
     */
    public Integer getAllCountByMemberAndStatus(String mid, Integer status) throws Exception;
    /**
     * 根据一组客户id信息，查询对应的所有客户信息
     * @param id
     * @return
     * @throws Exception
     */
    public Map<Integer, String> findByIds(Set<Integer> id) throws Exception;
    /**
     * 根据客户类型，统计出客户数量
     * @param type
     * @param keyWord
     * @param column
     * @return
     * @throws Exception
     */
    public Integer getAllCountByType(Integer type, String keyWord, String column) throws Exception;
    /**
     * 管理员根据用户类型查看所有用户的客户信息
     * @param type
     * @param keyWord
     * @param column
     * @param currentPage
     * @param lineSize
     * @return
     * @throws Exception
     */
    public List<Client> findAllSplitByType(Integer type, String keyWord, String column, Integer currentPage, Integer lineSize) throws Exception;
    /**
     * 统计一个用户所有客户量
     * @param mid
     * @param type
     * @param column
     * @param keyWord
     * @return
     * @throws Exception
     */
    public Integer getAllCountByMemberAndTpye(String mid, Integer type,String column, String keyWord) throws Exception;

    /**
     * 根据指定用户编号，显示该用户所有客户信息
     *
     * @param mid         指定用户编号
     * @param type
     * @param keyWord     分页关键字
     * @param currentPage 分页当前页
     * @param lineSize    每页显示行数
     * @return
     * @throws Exception
     */
    public List<Client> findAllSplitByMemberAndTpye(String mid, Integer type, String keyWord, String column, Integer currentPage, Integer lineSize) throws Exception;

    /**
     * 根据登录的用户，来更新对应的client信息
     * @param vo 包含有登录客户对应的client信息
     * @return 更新成功返回true,失败返回false
     * @throws Exception
     */
    public boolean doUpdateByMember(Client vo) throws Exception;

    /**
     * 根据指定用户编号和客户编号，查询对应的客户信息
     * @param mid 指定用户编号
     * @param id 添加的客户编号
     * @return 返回对应的客户信息
     * @throws Exception
     */
    public Client findByMemberAndId(String mid, Integer id) throws Exception;

    /**
     * 删除指定用户的客户信息
     * @param mid 用户编号
     * @param cids 需要删除客户信息的客户编号集合
     * @return 客户信息删除成功，返回true,否则返回false
     * @throws Exception
     */
    public boolean doRemoveByMember(String mid, Set<Integer> cids) throws Exception;

    /**
     * 根据指定的用户名和客户编号，判断该客户是否属于指定的用户
     * @param mid 用户编号
     * @param cid 客户编号
     * @return 如果该客户属于指定用户，返回true,否则返回false
     * @throws Exception
     */
    public boolean findExistsByMemberAndCid(String mid, Integer cid) throws Exception;
}


