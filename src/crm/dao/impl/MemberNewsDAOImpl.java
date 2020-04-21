package crm.dao.impl;

import crm.dao.IMemberNewsDAO;
import crm.dao.abs.AbstractDAOImpl;
import crm.vo.MemberNews;
import util.DateCompare;

import java.sql.ResultSet;
import java.util.*;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/8/12 下午10:02
 */
public class MemberNewsDAOImpl extends AbstractDAOImpl implements IMemberNewsDAO {
    @Override
    public Map<Integer, Boolean> findAllNotId(String mid,Set<Integer> ids) throws Exception {
        Map<Integer, Boolean> map = new HashMap<>();
        StringBuffer buf = new StringBuffer();
        buf.append(" SELECT nid FROM news WHERE nid NOT IN( ");
        buf.append(" SELECT nid FROM member_news WHERE mid=? )");
        buf.append(" AND nid IN(");
        Iterator<Integer> iter = ids.iterator();
        while (iter.hasNext()) {
            buf.append(iter.next()).append(",");
        }
        buf.delete(buf.length() - 1, buf.length());
        buf.append(" )");
        super.pstmt = super.conn.prepareStatement(buf.toString());
        super.pstmt.setString(1, mid);
        ResultSet rs = super.pstmt.executeQuery();
        while (rs.next()) {
            map.put(rs.getInt(1), true);
        }
        return map;
    }

    @Override
    public Integer getAllCountUnread(String mid) throws Exception {
        String sql = "  SELECT COUNT(*) FROM news " +
                      " WHERE nid NOT IN( " +
                     "  SELECT nid FROM member_news WHERE mid=? ) ";
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setString(1, mid);
        ResultSet rs = super.pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    @Override
    public Integer getAllCountByMemberAndNews(String mid, Integer nid) throws Exception {
        String sql = " SELECT COUNT(*) FROM member_news WHERE mid=? AND nid=? ";
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setString(1, mid);
        super.pstmt.setInt(2, nid);
        ResultSet rs = super.pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    @Override
    public boolean doCreate(MemberNews vo) throws Exception {
        String sql = " INSERT INTO member_news (mid,nid,rdate) VALUES (?,?,?)";
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setString(1, vo.getMember().getMid());
        super.pstmt.setInt(2, vo.getNews().getNid());
        super.pstmt.setTimestamp(3, new java.sql.Timestamp(vo.getRdate().getTime()));
        return super.pstmt.executeUpdate() > 0;
    }

    @Override
    public boolean doUpdate(MemberNews vo) throws Exception {
        return false;
    }

    @Override
    public boolean doRemove(Set<MemberNews> ids) throws Exception {
        return false;
    }

    @Override
    public List<MemberNews> findAll() throws Exception {
        return null;
    }

    @Override
    public MemberNews findById(MemberNews id) throws Exception {
        return null;
    }

    @Override
    public List<MemberNews> findAllSplit(String column, String keyWord, Integer currentPage, Integer lineSize) throws Exception {
        return null;
    }

    @Override
    public Integer getAllCount(String column, String keyWord) throws Exception {
        return null;
    }
}
