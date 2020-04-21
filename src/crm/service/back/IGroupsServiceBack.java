package crm.service.back;

import crm.vo.Groups;

import java.util.List;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/8/13 下午10:57
 */
public interface IGroupsServiceBack {
    /**
     * 实现权限组信息的列表操作（无分页）
     * @param mid
     * @return
     * @throws Exception
     */
    public List<Groups> list(String mid) throws Exception;

    /**
     * 根据权限组编号查看权限组详情以及对应权限信息，包含如下操作:<br>
     * <li>调用IGroupsDAO.findById()查询权限组的详细信息</li>
     * <li>调用IActionDAO.findAllByGroups(Integer gid)查询权限组编号对应的权限信息</li>
     * @param mid
     * @param gid
     * @return
     * @throws Exception
     */
    public Groups show(String mid, Integer gid) throws Exception;
}
