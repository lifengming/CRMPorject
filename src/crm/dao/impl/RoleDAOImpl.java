package crm.dao.impl;

import crm.dao.IRoleDAO;
import crm.dao.abs.AbstractDAOImpl;
import crm.vo.Groups;
import crm.vo.Role;

import java.sql.ResultSet;
import java.util.*;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/8/14 上午1:29
 */
public class RoleDAOImpl extends AbstractDAOImpl implements IRoleDAO {
    @Override
    public Boolean doRemoveRoleAndGroups(Integer rid) throws Exception {
        String sql = " DELETE  FROM role_groups WHERE rid=? ";
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setInt(1, rid);
        return super.pstmt.executeUpdate() > 0;//角色至少包含一个权限组
    }

    @Override
    public Map<Integer, Boolean> findGroupsByRole(Integer rid) throws Exception {
        Map<Integer, Boolean> map = new HashMap<>();
        String sql = " SELECT gid FROM role_groups WHERE rid=? ";
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setInt(1, rid);
        ResultSet rs = super.pstmt.executeQuery();
        while (rs.next()) {
            map.put(rs.getInt(1), true);
        }
        return map;
    }

    @Override
    public Integer findLastId() throws Exception {
        String sql = " SELECT LAST_INSERT_ID() ";
        super.pstmt = super.conn.prepareStatement(sql);
        ResultSet rs = super.pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    @Override
    public boolean doCreateRoleAndGroups(Role vo) throws Exception {
        String sql = " INSERT INTO role_groups (rid,gid) VALUES (?,?)";
        super.pstmt = super.conn.prepareStatement(sql);
        Iterator<Groups> iter = vo.getGroups().iterator();
        while (iter.hasNext()) {
            super.pstmt.setInt(1, vo.getRid());
            super.pstmt.setInt(2, iter.next().getGid());
            super.pstmt.addBatch();//追加批处理
        }
        int result[] = super.pstmt.executeBatch();//执行批处理的处理模式
        for (int x = 0; x < result.length; x++) {
            if (result[x] == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean doCreate(Role vo) throws Exception {
        String sql = " INSERT INTO role (title) VALUES (?) ";
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setString(1, vo.getTitle());
        return super.pstmt.executeUpdate() > 0;
    }

    @Override
    public boolean doUpdate(Role vo) throws Exception {
        String sql = " UPDATE role  SET title=? WHERE rid=? ";
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setString(1, vo.getTitle());
        super.pstmt.setInt(2, vo.getRid());
        return super.pstmt.executeUpdate() > 0;
    }

    @Override
    public boolean doRemove(Set<Integer> ids) throws Exception {
        return super.handleRemoveNumberType("role", "rid", ids);
    }

    @Override
    public List<Role> findAll() throws Exception {
        List<Role> all = new ArrayList<>();
        String sql = " SELECT rid,title FROM role ";
        super.pstmt = super.conn.prepareStatement(sql);
        ResultSet rs = super.pstmt.executeQuery();
        while (rs.next()) {
            Role vo = new Role();
            vo.setRid(rs.getInt(1));
            vo.setTitle(rs.getString(2));
            all.add(vo);
        }
        return all;
    }

    @Override
    public Role findById(Integer id) throws Exception {
        Role vo = null;
        String sql = " SELECT rid,title FROM role WHERE rid=? ";
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setInt(1, id);
        ResultSet rs = super.pstmt.executeQuery();
        if (rs.next()) {
             vo = new Role();
            vo.setRid(rs.getInt(1));
            vo.setTitle(rs.getString(2));
        }
        return vo;
    }

    @Override
    public List<Role> findAllSplit(String column, String keyWord, Integer currentPage, Integer lineSize) throws Exception {
        return null;
    }

    @Override
    public Integer getAllCount(String column, String keyWord) throws Exception {
        return null;
    }
}
