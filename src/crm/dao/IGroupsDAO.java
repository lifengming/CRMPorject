package crm.dao;

import crm.vo.Groups;
import crm.vo.Role;
import util.dao.IDAO;

import java.util.List;
import java.util.Map;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/22 上午1:28
 */
public interface IGroupsDAO extends IDAO<Integer, Groups> {
    /**
     * 查询权限组的全部信息
     * @return key=type,value=Groups的VO对象
     * @throws Exception
     */
    public Map<String, List<Groups>> findAllByType() throws Exception;
    /**
     * 根据角色编号找到对应权限组信息
     * @param rid
     * @return
     * @throws Exception
     */
    public List<Groups> findAllByRole(Integer rid) throws Exception;
}
