package crm.dao.impl;

import crm.dao.IMemberDAO;
import crm.dao.abs.AbstractDAOImpl;
import crm.vo.Member;
import crm.vo.News;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.*;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/21 下午2:37
 */
public class MemberDAOImpl extends AbstractDAOImpl implements IMemberDAO {
    @Override
    public boolean doUpdatePassword(String mid, String password) throws Exception {
        String sql = " UPDATE member SET password=? WHERE mid=? ";
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setString(1, password);
        super.pstmt.setString(2, mid);
        return super.pstmt.executeUpdate() > 0;
    }

    @Override
    public boolean doUpdateLastdate(String id) throws Exception {
        Date date = new Date();
        String sql = " UPDATE member SET lastdate=? WHERE mid=? ";
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setTimestamp(1, new java.sql.Timestamp(date.getTime()));
        super.pstmt.setString(2, id);
        return super.pstmt.executeUpdate() > 0;
    }

    @Override
    public boolean findLogin(Member vo) throws Exception {
        Integer flag = null;
        String sql = " SELECT flag,rid FROM member WHERE mid=? AND password=? AND locked=0 ";
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setString(1, vo.getMid());
        super.pstmt.setString(2, vo.getPassword());
        ResultSet rs = super.pstmt.executeQuery();
        if (rs.next()) {
            vo.setFlag(rs.getInt(1));
            vo.getRole().setRid(rs.getInt(2));
            return true;
        }
        return false;
    }

    @Override
    public boolean doCreate(Member vo) throws Exception {
        String sql = " INSERT INTO member(mid,rid,password,tel,photo,locked,flag)VALUES(?,?,?,?,?,?,?) ";
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setString(1, vo.getMid());
        super.pstmt.setInt(2, vo.getRole().getRid());
        super.pstmt.setString(3, vo.getPassword());
        super.pstmt.setString(4, vo.getTel());
        super.pstmt.setString(5, vo.getPhoto());
        super.pstmt.setInt(6, vo.getLocked());
        super.pstmt.setInt(7, vo.getFlag());
        return super.pstmt.executeUpdate() > 0;
    }

    @Override
    public boolean doUpdate(Member vo) throws Exception {
        String sql = " UPDATE member SET rid=?,tel=?,locked=?,photo=? WHERE mid=? ";
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setInt(1, vo.getRole().getRid());
        super.pstmt.setString(2, vo.getTel());
        super.pstmt.setInt(3, vo.getLocked());
        super.pstmt.setString(4, vo.getPhoto());
        super.pstmt.setString(5, vo.getMid());
        return super.pstmt.executeUpdate() > 0;
    }

    @Override
    public boolean doRemove(Set<String> ids) throws Exception {
        StringBuffer buf = new StringBuffer();
        buf.append(" DELETE FROM  ").append("member").append("  WHERE ").append("mid").append("  IN (");
        Iterator<String> iter = ids.iterator();
        while (iter.hasNext()) {
            buf.append("'").append(iter.next()).append("'").append(",");
        }
        buf.delete(buf.length() - 1, buf.length()).append(" )");
        buf.append(" AND flag=0 ");//只能够删除非超级管理员用户
        this.pstmt = this.conn.prepareStatement(buf.toString());
        return this.pstmt.executeUpdate() == ids.size();
    }

    @Override
    public List<Member> findAll() throws Exception {
        return null;
    }

    @Override
    public Member findById(String id) throws Exception {
        String sql = " SELECT mid,rid,tel,lastdate,photo,flag,locked FROM member WHERE mid=? ";
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setString(1, id);
        ResultSet rs = super.pstmt.executeQuery();
        Member vo = null;
        if (rs.next()) {
             vo = new Member();
            vo.setMid(rs.getString(1));
            vo.getRole().setRid(rs.getInt(2));
            vo.setTel(rs.getString(3));
            vo.setLastdate(rs.getDate(4));
            vo.setPhoto(rs.getString(5));
            vo.setFlag(rs.getInt(6));
            vo.setLocked(rs.getInt(7));
        }
        return vo;
    }

    @Override
    public List<Member> findAllSplit(String column, String keyWord, Integer currentPage, Integer lineSize) throws Exception {
        List<Member> all = new ArrayList<>();
        String sql = " SELECT mid,rid,tel,lastdate,photo,flag,locked FROM member WHERE  "+column+"  LIKE ?  LIMIT ?,? ";
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setString(1, "%" + keyWord + "%");
        super.pstmt.setInt(2, (currentPage - 1) * lineSize);
        super.pstmt.setInt(3, lineSize);
        ResultSet rs = super.pstmt.executeQuery();
        while (rs.next()) {
            Member vo = new Member();
            vo.setMid(rs.getString(1));
            vo.getRole().setRid(rs.getInt(2));
            vo.setTel(rs.getString(3));
            vo.setLastdate(rs.getDate(4));
            vo.setPhoto(rs.getString(5));
            vo.setFlag(rs.getInt(6));
            vo.setLocked(rs.getInt(7));
            all.add(vo);
        }
        return all;

    }

    @Override
    public Integer getAllCount(String column, String keyWord) throws Exception {
        return super.handleCount("member", column, keyWord);
    }
}
