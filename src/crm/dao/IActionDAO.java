package crm.dao;

import crm.vo.Action;
import crm.vo.Groups;
import crm.vo.Role;
import util.dao.IDAO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/22 上午1:28
 */
public interface IActionDAO extends IDAO<Integer, Action> {
    /**
     * 根据权限组编号找到对应所有权限信息
     * @param gid 权限组编号
     * @return
     * @throws Exception
     */
    public List<Action> findAllByGroups(Integer gid) throws Exception;

    /**
     * 根据权限组编号集合找到对应所有权限信息
     * @param gid 查询一组gid的信息
     * @return 返回map集合，key为权限编号，value为权限内容,
     * 这样的优势在于避免了在Servlet中进行双层迭代，便于前台查询!!!提高性能！
     * @throws Exception
     */
    public Map<String, Action> findAllByGroups(Set<Integer> gid) throws Exception;

    /**
     * 根据指定角色编号和权限编号，查询该权限内容是否存在
     * @param rid 角色编号
     * @param actid 权限编号
     * @return 若权限内容存在，返回指定权限对象，否则返回null
     * @throws Exception
     */
    public Action findByRoleAndId(Integer rid, Integer actid) throws Exception;
}
