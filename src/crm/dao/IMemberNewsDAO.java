package crm.dao;

import crm.vo.MemberNews;
import util.dao.IDAO;

import java.util.Map;
import java.util.Set;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/8/12 下午9:53
 */
public interface IMemberNewsDAO extends IDAO<MemberNews, MemberNews> {
    /**
     * 通过member_news表，获得未读消息的编号，保存至Map集合中，本程序执行如下操作:<br>
     * @param ids
     * @param mid
     * @return 本操作返回如下结果:<br>
     * <li>key=,value=</li>
     * <li>key=,value=</li>
     * <li>key=,value=</li>
     * @throws Exception
     */
    public Map<Integer, Boolean> findAllNotId(String mid,Set<Integer> ids) throws Exception;
    /**
     * 统计指定用户未读取消息数量
     * @param mid 用户ID
     * @return 未读消息数量
     * @throws Exception
     */
    public Integer getAllCountUnread(String mid) throws Exception;
    /**
     * 指定用户对指定公告的阅读记录数
     * @param mid
     * @param nid
     * @return
     * @throws Exception
     */
    public Integer getAllCountByMemberAndNews(String mid, Integer nid) throws Exception;
}
