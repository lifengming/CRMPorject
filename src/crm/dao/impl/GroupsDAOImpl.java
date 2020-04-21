package crm.dao.impl;

import crm.dao.IGroupsDAO;
import crm.dao.abs.AbstractDAOImpl;
import crm.vo.Groups;

import java.sql.ResultSet;
import java.util.*;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/22 上午1:40
 */
public class GroupsDAOImpl extends AbstractDAOImpl implements IGroupsDAO {
    @Override
    public Map<String, List<Groups>> findAllByType() throws Exception {
        Map<String,List<Groups>> map = new HashMap<>();
        String sql = " SELECT gid,title,img,type FROM groups ";
        super.pstmt = super.conn.prepareStatement(sql);
        ResultSet rs = super.pstmt.executeQuery();
        while  (rs.next()) {
            Groups vo = new Groups();
            vo.setGid(rs.getInt(1));
            vo.setTitle(rs.getString(2));
            vo.setImg(rs.getString(3));
            vo.setType(rs.getString(4));
            if (map.containsKey(vo.getType())) {
                List<Groups> temp = map.get(vo.getType());
                temp.add(vo);
                map.put(vo.getType(), temp);
            } else {
                List<Groups> temp = new ArrayList<>();
                temp.add(vo);
                map.put(vo.getType(), temp);
            }
        }
        return map;
    }

    @Override
    public List<Groups> findAllByRole(Integer rid) throws Exception {
        List<Groups> all = new ArrayList<>();
        String sql = " SELECT gid,title,img,type FROM groups " +
                " WHERE gid IN( " +
                " SELECT gid FROM role_groups WHERE rid=?) ";
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setInt(1, rid);
        ResultSet rs = super.pstmt.executeQuery();
        while  (rs.next()) {
            Groups vo = new Groups();
            vo.setGid(rs.getInt(1));
            vo.setTitle(rs.getString(2));
            vo.setImg(rs.getString(3));
            vo.setType(rs.getString(4));
            all.add(vo);
        }
        return all;
    }

    @Override
    public boolean doCreate(Groups vo) throws Exception {
        return false;
    }

    @Override
    public boolean doUpdate(Groups vo) throws Exception {
        return false;
    }

    @Override
    public boolean doRemove(Set<Integer> ids) throws Exception {
        return false;
    }

    @Override
    public List<Groups> findAll() throws Exception {
        List<Groups> all = new ArrayList<>();
        String sql = " SELECT gid,title,img,type FROM groups ";
        super.pstmt = super.conn.prepareStatement(sql);
        ResultSet rs = super.pstmt.executeQuery();
        while  (rs.next()) {
            Groups vo = new Groups();
            vo.setGid(rs.getInt(1));
            vo.setTitle(rs.getString(2));
            vo.setImg(rs.getString(3));
            vo.setType(rs.getString(4));
            all.add(vo);
        }
        return all;
    }

    @Override
    public Groups findById(Integer id) throws Exception {
        Groups vo = null;
        String sql = " SELECT gid,title,img,type FROM groups WHERE gid=? ";
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setInt(1, id);
        ResultSet rs = super.pstmt.executeQuery();
        if (rs.next()) {
            vo = new Groups();
            vo.setGid(rs.getInt(1));
            vo.setTitle(rs.getString(2));
            vo.setImg(rs.getString(3));
            vo.setType(rs.getString(4));
        }
        return vo;
    }

    @Override
    public List<Groups> findAllSplit(String column, String keyWord, Integer currentPage, Integer lineSize) throws Exception {
        return null;
    }

    @Override
    public Integer getAllCount(String column, String keyWord) throws Exception {
        return null;
    }
}
