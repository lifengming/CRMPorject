package crm.service.back;

import crm.vo.News;

import java.util.Map;
import java.util.Set;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/8/8 上午7:23
 */
public interface INewsServiceBack {
    /**
     * 实现新闻数据的追加操作
     * @param vo 包含有新闻数据的VO对象
     * @return 增加成功返回true,否则返回false
     * @throws Exception
     */
    public boolean add(News vo) throws Exception;

    /**
     * 实现公告列表的分页查询显示处理
     * @param mid
     * @param column      模糊查询列
     * @param keyword     模糊查询关键字
     * @param currentPage 当前所在页
     * @param lineSize    每页显示行数
     * @return 本操作返回Map集合包含如下结果:<br>
     * <LI>key=allNews,value=INewsDAO.findAllSplit()</LI>
     * <LI>key=newsCount,value=INewsDAO.getAllCount()</LI>
     * @throws Exception
     */
    public Map<String, Object> listSplit(String mid, String column, String keyword, Integer currentPage, Integer lineSize) throws Exception;

    /**
     * 查看公告信息详情
     * @param mid 权限验证使用
     * @param nid 公告信息查询使用
     * @return
     * @throws Exception
     */
    public News show(String mid,Integer nid) throws Exception;

    /**
     * 数据更新前的查询处理
     * @param mid
     * @param nid
     * @return
     * @throws Exception
     */
    public News editPre(String mid, Integer nid) throws Exception;

    /**
     * 数据更新处理
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean edit(News vo) throws Exception;

    /**
     * 删除公告信息
     * @param mid
     * @param ids
     * @return
     * @throws Exception
     */
    public boolean rm(String mid, Set<Integer> ids) throws Exception;
}
