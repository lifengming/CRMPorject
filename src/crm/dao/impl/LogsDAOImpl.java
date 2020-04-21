package crm.dao.impl;

import crm.dao.ILogsDAO;
import crm.dao.abs.AbstractDAOImpl;
import crm.vo.Logs;
import crm.vo.Member;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/22 上午12:06
 */
public class LogsDAOImpl extends AbstractDAOImpl implements ILogsDAO {
    @Override
    public boolean doCreate(Logs vo) throws Exception {
        String sql = " INSERT INTO logs (mid,indate) VALUES(?,?) ";
        super.pstmt = super.conn.prepareStatement(sql);
        super.pstmt.setString(1, vo.getMember().getMid());
        super.pstmt.setTimestamp(2,new java.sql.Timestamp(new Date().getTime()));
        return super.pstmt.executeUpdate() > 0;
    }

    @Override
    public boolean doUpdate(Logs vo) throws Exception {
        return false;
    }

    @Override
    public boolean doRemove(Set<Integer> ids) throws Exception {
        return false;
    }

    @Override
    public List<Logs> findAll() throws Exception {
        return null;
    }

    @Override
    public Logs findById(Integer id) throws Exception {
        return null;
    }

    @Override
    public List<Logs> findAllSplit(String column, String keyWord, Integer currentPage, Integer lineSize) throws Exception {
        return null;
    }

    @Override
    public Integer getAllCount(String column, String keyWord) throws Exception {
        return null;
    }
}
