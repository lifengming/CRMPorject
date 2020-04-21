package crm.dao.impl;

import crm.dao.IClientDAO;
import crm.dao.abs.AbstractDAOImpl;
import crm.vo.Client;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.util.*;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/24 下午8:29
 */
public class ClientDAOImpl extends AbstractDAOImpl implements IClientDAO {
    @Override
    public Integer getAllCountByMemberAndStatus(String mid, Integer status) throws Exception {
        String sql = " SELECT COUNT(*) FROM task WHERE mid=? AND status=? ";
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setString(1, mid);
        super.pstmt.setInt(2, status);
        ResultSet rs = super.pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    @Override
    public Map<Integer, String> findByIds(Set<Integer> id) throws Exception {
        Map<Integer, String> map = new HashMap<>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT cid,name FROM client WHERE cid IN(  ");
        Iterator<Integer> iter = id.iterator();
        while (iter.hasNext()) {
            sql.append(iter.next()).append(",");
        }
        sql.delete(sql.length() - 1, sql.length());
        sql.append(" )");
        super.pstmt = super.conn.prepareStatement(sql.toString());
        ResultSet rs = super.pstmt.executeQuery();
        while (rs.next()) {
            map.put(rs.getInt(1), rs.getString(2));
        }
        return map;
    }

    @Override
    public boolean findExistsByMemberAndCid(String mid, Integer cid) throws Exception {
        String sql = " SELECT name FROM client WHERE mid=? AND cid=? ";
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setString(1, mid);
        super.pstmt.setInt(2, cid);
        ResultSet rs = super.pstmt.executeQuery();
        if (rs.next()) {
            return true;
        }
        return false;
    }

    @Override
    public Integer getAllCountByType(Integer type, String keyWord, String column) throws Exception {
        String sql = null;
        if (type.equals(-1)) {
            sql = " SELECT COUNT(*) FROM client WHERE " + column + "  LIKE ?  ";
        } else {
            sql = " SELECT COUNT(*) FROM client WHERE " + column + "  LIKE ? AND type= "+type;
        }
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setString(1,"%"+keyWord+"%");
        ResultSet rs = super.pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }


    @Override
    public List<Client> findAllSplitByType(Integer type, String keyWord, String column, Integer currentPage, Integer lineSize) throws Exception {
        List<Client> all = new ArrayList<>();
        String sql = null;
        if (type.equals(-1)) {
            sql = " SELECT cid,mid,name,sex,email,tel,qq,type,reg,note FROM client WHERE " + column + " LIKE ?  LIMIT ?,? ";
        } else {
            sql = " SELECT cid,mid,name,sex,email,tel,qq,type,reg,note FROM client WHERE " + column + " LIKE ?  AND type= "+type+"  LIMIT ?,? ";
        }
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setString(1,"%"+keyWord+"%");
        super.pstmt.setInt(2,(currentPage-1)*lineSize);
        super.pstmt.setInt(3,lineSize);
        ResultSet rs = super.pstmt.executeQuery();
        while (rs.next()) {
            Client vo = new Client();
            vo.setCid(rs.getInt(1));
            vo.getMember().setMid(rs.getString(2));
            vo.setName(rs.getString(3));
            vo.setSex(rs.getString(4));
            vo.setEmail(rs.getString(5));
            vo.setTel(rs.getString(6));
            vo.setQq(rs.getString(7));
            vo.setType(rs.getInt(8));
            vo.setReg(rs.getDate(9));
            vo.setNote(rs.getString(10));
            all.add(vo);
        }
        return all;
    }

    @Override
    public boolean doRemoveByMember(String mid, Set<Integer> cids) throws Exception {
        StringBuffer buf = new StringBuffer();
        buf.append(" DELETE FROM client WHERE mid=? AND cid IN( ");
        Iterator<Integer> iter = cids.iterator();
        while (iter.hasNext()) {
            buf.append(iter.next()).append(",");
        }
        buf.delete(buf.length() - 1, buf.length());
        buf.append(" )");
        super.pstmt = super.conn.prepareStatement(buf.toString());
        super.pstmt.setString(1, mid);
        return super.pstmt.executeUpdate() == cids.size();
    }

    @Override
    public boolean doUpdateByMember(Client vo) throws Exception {
        String sql = " UPDATE client SET name=?,sex=?,email=?,tel=?,qq=?,type=?,note=? WHERE mid=? AND cid=? ";
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setString(1, vo.getName());
        super.pstmt.setString(2, vo.getSex());
        super.pstmt.setString(3, vo.getEmail());
        super.pstmt.setString(4, vo.getTel());
        super.pstmt.setString(5, vo.getQq());
        super.pstmt.setInt(6, vo.getType());
        super.pstmt.setString(7, vo.getNote());
        super.pstmt.setString(8, vo.getMember().getMid());
        super.pstmt.setInt(9, vo.getCid());
        return super.pstmt.executeUpdate() > 0;
    }

    @Override
    public Client findByMemberAndId(String mid, Integer id) throws Exception {
        Client vo = null;
        String sql = " SELECT cid,mid,name,sex,email,tel,qq,type,reg,note FROM client WHERE mid=? AND cid=? ";
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setString(1, mid);
        super.pstmt.setInt(2, id);
        ResultSet rs = super.pstmt.executeQuery();
        if (rs.next()) {
            vo = new Client();
            vo.setCid(rs.getInt(1));
            vo.getMember().setMid(rs.getString(2));
            vo.setName(rs.getString(3));
            vo.setSex(rs.getString(4));
            vo.setEmail(rs.getString(5));
            vo.setTel(rs.getString(6));
            vo.setQq(rs.getString(7));
            vo.setType(rs.getInt(8));
            vo.setReg(rs.getDate(9));
            vo.setNote(rs.getString(10));
        }
        return vo;
    }

    @Override
    public Integer getAllCountByMemberAndTpye(String mid,Integer type, String column, String keyWord) throws Exception {
        String sql = null;
        if (type.equals(-1)) {
            sql = " SELECT COUNT(*) FROM client WHERE " + column + " LIKE ? AND mid=? ";
        } else {
            sql = " SELECT COUNT(*) FROM client WHERE " + column + " LIKE ? AND mid=? AND type= "+type;
        }
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setString(1,"%"+keyWord+"%");
        super.pstmt.setString(2,mid);
        ResultSet rs = super.pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    @Override
    public List<Client> findAllSplitByMemberAndTpye(String mid, Integer type,String keyWord, String column,Integer currentPage, Integer lineSize) throws Exception {
        List<Client> all = new ArrayList<>();
        String sql = null;
        if (type.equals(-1)) {
            sql = " SELECT cid,mid,name,sex,email,tel,qq,type,reg,note FROM client WHERE " + column + " LIKE ? AND mid=? LIMIT ?,? ";
        } else {
            sql = " SELECT cid,mid,name,sex,email,tel,qq,type,reg,note FROM client WHERE " + column + " LIKE ? AND mid=? AND type= "+type+"  LIMIT ?,? ";
        }
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setString(1,"%"+keyWord+"%");
        super.pstmt.setString(2,mid);
        super.pstmt.setInt(3,(currentPage-1)*lineSize);
        super.pstmt.setInt(4,lineSize);
        ResultSet rs = super.pstmt.executeQuery();
        while (rs.next()) {
            Client vo = new Client();
            vo.setCid(rs.getInt(1));
            vo.getMember().setMid(rs.getString(2));
            vo.setName(rs.getString(3));
            vo.setSex(rs.getString(4));
            vo.setEmail(rs.getString(5));
            vo.setTel(rs.getString(6));
            vo.setQq(rs.getString(7));
            vo.setType(rs.getInt(8));
            vo.setReg(rs.getDate(9));
            vo.setNote(rs.getString(10));
            all.add(vo);
        }
        return all;
    }

    @Override
    public boolean doCreate(Client vo) throws Exception {
        String sql = " INSERT INTO client(mid,name,sex,email,tel,qq,type,reg,note) " +
                " VALUES (?,?,?,?,?,?,?,?,?) ";
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setString(1, vo.getMember().getMid());
        super.pstmt.setString(2, vo.getName());
        super.pstmt.setString(3, vo.getSex());
        super.pstmt.setString(4, vo.getEmail());
        super.pstmt.setString(5, vo.getTel());
        super.pstmt.setString(6, vo.getQq());
        super.pstmt.setInt(7, vo.getType());
        super.pstmt.setTimestamp(8, new java.sql.Timestamp(vo.getReg().getTime()));
        super.pstmt.setString(9, vo.getNote());
        return super.pstmt.executeUpdate() > 0;
    }


    @Override
    public boolean doUpdate(Client vo) throws Exception {
        return false;
    }

    @Override
    public boolean doRemove(Set<Integer> ids) throws Exception {
        return false;
    }

    @Override
    public List<Client> findAll() throws Exception {
        return null;
    }

    @Override
    public Client findById(Integer id) throws Exception {
        Client vo = null;
        String sql = " SELECT cid,mid,name,sex,email,tel,qq,type,reg,note FROM client WHERE  cid=? ";
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setInt(1, id);
        ResultSet rs = super.pstmt.executeQuery();
        if (rs.next()) {
            vo = new Client();
            vo.setCid(rs.getInt(1));
            vo.getMember().setMid(rs.getString(2));
            vo.setName(rs.getString(3));
            vo.setSex(rs.getString(4));
            vo.setEmail(rs.getString(5));
            vo.setTel(rs.getString(6));
            vo.setQq(rs.getString(7));
            vo.setType(rs.getInt(8));
            vo.setReg(rs.getDate(9));
            vo.setNote(rs.getString(10));
        }
        return vo;
    }

    @Override
    public List<Client> findAllSplit(String column, String keyWord, Integer currentPage, Integer lineSize) throws Exception {
        return null;
    }

    @Override
    public Integer getAllCount(String column, String keyWord) throws Exception {
        return null;
    }
}
