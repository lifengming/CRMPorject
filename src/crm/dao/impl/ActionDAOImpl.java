package crm.dao.impl;

import crm.dao.IActionDAO;
import crm.dao.abs.AbstractDAOImpl;
import crm.vo.Action;
import crm.vo.Groups;

import java.sql.ResultSet;
import java.util.*;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/22 上午2:05
 */
public class ActionDAOImpl extends AbstractDAOImpl implements IActionDAO {
    @Override
    public List<Action> findAll() throws Exception {
        List<Action> all = new ArrayList<>();
        String sql = " SELECT actid,title,menu,url FROM action ";
        super.pstmt = super.conn.prepareStatement(sql);
        ResultSet rs = super.pstmt.executeQuery();
        while (rs.next()) {
            Action vo = new Action();
            vo.setActid(rs.getInt(1));
            vo.setTitle(rs.getString(2));
            vo.setMenu(rs.getInt(3));
            vo.setUrl(rs.getString(4));
            all.add(vo);
        }
        return all;
    }
    @Override
    public Action findByRoleAndId(Integer rid, Integer actid) throws Exception {
        Action vo = null;
        String sql =" SELECT actid,title,menu,url " +
                    " FROM action WHERE actid IN( " +
                        " SELECT actid FROM groups_action " +
                        " WHERE gid IN( " +
                            " SELECT gid FROM role_groups WHERE rid=? )) " +
                    " AND actid=? ";
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setInt(1, rid);
        super.pstmt.setInt(2, actid);
        ResultSet rs = super.pstmt.executeQuery();
        if (rs.next()) {
            vo = new Action();
            vo.setActid(rs.getInt(1));
            vo.setTitle(rs.getString(2));
            vo.setMenu(rs.getInt(3));
            vo.setUrl(rs.getString(4));
        }
        return vo;
    }
    @Override
    public Map<String, Action> findAllByGroups(Set<Integer> gid) throws Exception {
        Map<String, Action> map = new HashMap<>();
        StringBuffer buf = new StringBuffer();
        buf.append(" SELECT actid,title,menu,url FROM action " +
                " WHERE actid IN( " +
                " SELECT actid FROM groups_action WHERE gid IN(  ");
        Iterator<Integer> iter = gid.iterator();
        while (iter.hasNext()) {
            buf.append(iter.next()).append(",");
        }
        buf.delete(buf.length() - 1, buf.length());
        buf.append("))");
        super.pstmt = super.conn.prepareStatement(buf.toString());
        ResultSet rs = super.pstmt.executeQuery();
        while (rs.next()) {
            Action vo = new Action();
            vo.setActid(rs.getInt(1));
            vo.setTitle(rs.getString(2));
            vo.setMenu(rs.getInt(3));
            vo.setUrl(rs.getString(4));
            map.put(String.valueOf(vo.getActid()), vo);
        }
        return map;
    }

    @Override
    public List<Action> findAllByGroups(Integer gid) throws Exception {
        List<Action> all = new ArrayList<>();
        String sql = " SELECT actid,title,menu,url FROM action " +
                " WHERE actid IN( " +
                " SELECT actid FROM groups_action WHERE gid=?) ";
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setInt(1, gid);
        ResultSet rs = super.pstmt.executeQuery();
        while (rs.next()) {
            Action vo = new Action();
            vo.setActid(rs.getInt(1));
            vo.setTitle(rs.getString(2));
            vo.setMenu(rs.getInt(3));
            vo.setUrl(rs.getString(4));
            all.add(vo);
        }
        return all;
    }

    @Override
    public boolean doCreate(Action vo) throws Exception {
        return false;
    }

    @Override
    public boolean doUpdate(Action vo) throws Exception {
        return false;
    }

    @Override
    public boolean doRemove(Set<Integer> ids) throws Exception {
        return false;
    }



    @Override
    public Action findById(Integer id) throws Exception {
        return null;
    }

    @Override
    public List<Action> findAllSplit(String column, String keyWord, Integer currentPage, Integer lineSize) throws Exception {
        return null;
    }

    @Override
    public Integer getAllCount(String column, String keyWord) throws Exception {
        return null;
    }
}
