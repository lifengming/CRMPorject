package crm.dao;

import crm.vo.Task;
import util.dao.IDAO;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/29 下午9:55
 */
public interface ITaskDAO extends IDAO<Integer, Task> {
    /**
     * 统计出该日期之后待完成的任务量
     * @param mid
     * @param date
     * @return
     * @throws Exception
     */
    public Integer getAllCountByAfterFinish(String mid, Date date) throws Exception;
    /**
     * 统计出该日期之前未完成的任务量
     * @param mid
     * @param date
     * @return
     * @throws Exception
     */
    public Integer getAllCountByBeforeUnFinish(String mid, Date date) throws Exception;
    /**
     * 删除指定用户的任务信息
     * @param mid
     * @param tids
     * @return
     * @throws Exception
     */
    public boolean doRemoveByMember(String mid, Set<Integer> tids) throws Exception;

    /**
     * 根据指定用户编号，更新指定任务的状态值！
     * @param mid 用户编号
     * @param tid 任务编号
     * @param status 状态值 (status=0任务关闭,status=2任务完成)
     * @return
     * @throws Exception
     */
    public boolean doUpdateByStatus(String mid, Integer tid, Integer status) throws Exception;

    /**
     * 表示根据用户编号，任务编号，查询任务的全部信息
     * @param mid
     * @param tid
     * @return 获得该用户的指定客户的指定一个任务的全部信息,任务信息存在，以VO形式返回!
     * @throws Exception
     */
    public Task findById(String mid,  Integer tid) throws Exception;
    /**
     * 更新指定用户的任务信息，更新时候要求同时判断mid与tid
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean doUpdateByMember(Task vo) throws Exception;
    /**
     * 管理员根据客户编号查询指定客户任务列表
     * @param cid 客户编号
     * @return
     * @throws Exception
     */
    public List<Task> findAllByClient( Integer cid) throws Exception;


    /**
     * 根据用户和客户编号，查询出一个客户的所有任务信息，查询时候按照任务完成时间倒序排列
     * @param mid 用户编号
     * @param cid 客户编号
     * @return  当前用户的客户的任务信息，以集合形式返回！
     * @throws Exception
     */
    public List<Task> findAllMemberAndClient(String mid, Integer cid) throws Exception;

    /**
     * 表示根据用户编号，客户编号，任务编号，查询该用户的指定客户的指定任务全部信息
     * @param mid
     * @param cid
     * @param tid
     * @return 获得该用户的指定客户的指定一个任务的全部信息,任务信息存在，以VO形式返回!
     * @throws Exception
     */
    public Task findById(String mid, Integer cid, Integer tid) throws Exception;

    /**
     * 进行指定用户任务列表的操作显示
     * @param mid 用户编号
     * @param visit 回访方式
     * @param type 回访状态
     * @param column 列名称
     * @param keyword 模糊查询关键字
     * @param currentPage
     * @param lineSize
     * @return 以list集合形式，返回部分查询结果
     * @throws Exception
     */
    public List<Task> findAllSplitByMember(String mid, Integer visit, Integer type, String column, String keyword, Integer currentPage, Integer lineSize) throws Exception;

    /**
     * 指定任务类型的数据量统计
     * @param mid
     * @param visit
     * @param type
     * @param column
     * @param keyword
     * @return
     * @throws Exception
     */
    public Integer getAllCountByMember(String mid, Integer visit, Integer type, String column, String keyword) throws Exception;
    /**
     * 进行指定用户任务列表的操作显示
     * @param visit 回访方式
     * @param type 回访状态
     * @param column 列名称
     * @param keyword 模糊查询关键字
     * @param currentPage
     * @param lineSize
     * @return 以list集合形式，返回部分查询结果
     * @throws Exception
     */
    public List<Task> findAllSplit( Integer visit, Integer type, String column, String keyword, Integer currentPage, Integer lineSize) throws Exception;
    /**
     * 指定任务类型的数据量统计
     * @param visit
     * @param type
     * @param column
     * @param keyword
     * @return
     * @throws Exception
     */
    public Integer getAllCount( Integer visit, Integer type, String column, String keyword) throws Exception;

}
