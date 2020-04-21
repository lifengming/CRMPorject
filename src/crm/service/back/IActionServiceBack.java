package crm.service.back;

import crm.vo.Action;

import java.util.List;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/8/13 下午9:50
 */
public interface IActionServiceBack {
    /**
     * 实现权限列表数据处理操作
     * @return
     * @throws Exception
     */
    public List<Action> list(String mid) throws Exception;
}
