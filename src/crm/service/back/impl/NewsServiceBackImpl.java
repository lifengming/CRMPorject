package crm.service.back.impl;

import crm.dao.IMemberNewsDAO;
import crm.dao.INewsDAO;
import crm.dao.impl.MemberNewsDAOImpl;
import crm.dao.impl.NewsDAOImpl;
import crm.service.back.AbstractCRMServiceBack;
import crm.service.back.INewsServiceBack;
import crm.vo.MemberNews;
import crm.vo.News;
import util.factory.DAOFactory;

import java.sql.Timestamp;
import java.util.*;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/8/8 上午7:27
 */
public class NewsServiceBackImpl extends AbstractCRMServiceBack implements INewsServiceBack {
    @Override
    public boolean rm(String mid, Set<Integer> ids) throws Exception {
        INewsDAO newsDAO = DAOFactory.getInstance(NewsDAOImpl.class);
        if (ids.size() == 0) {
            return false;
        }
        if (super.isAction(mid, 14)) {
            return newsDAO.doRemove(ids);
        }
        return false;
    }

    @Override
    public News editPre(String mid, Integer nid) throws Exception {
        INewsDAO newsDAO = DAOFactory.getInstance(NewsDAOImpl.class);
        if (super.isAction(mid, 13)) {
            return ((NewsDAOImpl) newsDAO).findById(nid);
        }
        return null;
    }

    @Override
    public boolean edit(News vo) throws Exception {
        INewsDAO newsDAO = DAOFactory.getInstance(NewsDAOImpl.class);
        if (super.isAction(vo.getMember().getMid(), 13)) {
            return newsDAO.doUpdate(vo);
        }
        return false;
    }

    @Override
    public News show(String mid, Integer nid) throws Exception {
        INewsDAO newsDAO = DAOFactory.getInstance(NewsDAOImpl.class);
        IMemberNewsDAO memberNewsDAO = DAOFactory.getInstance(MemberNewsDAOImpl.class);
        if (super.isAction(mid, 33)) {
            if (memberNewsDAO.getAllCountByMemberAndNews(mid, nid) == 0) {//没有读取过
                MemberNews vo = new MemberNews();
                vo.getMember().setMid(mid);
                vo.getNews().setNid(nid);
                vo.setRdate(new Date());
                memberNewsDAO.doCreate(vo);
            }
            return ((NewsDAOImpl) newsDAO).findById(nid);
        }
        return null;
    }

    @Override
    public Map<String, Object> listSplit(String mid,String column, String keyword, Integer currentPage, Integer lineSize) throws Exception {
        INewsDAO newsDAO = DAOFactory.getInstance(NewsDAOImpl.class);
        IMemberNewsDAO memberNewsDAO = DAOFactory.getInstance(MemberNewsDAOImpl.class);
        Map<String, Object> map = new HashMap<>();
        List<News> all = newsDAO.findAllSplit(column, keyword, currentPage, lineSize);
        if (super.isAction(mid, 11)) {
            if (all.size() > 0) {
                Set<Integer> set = new HashSet<>();
                Iterator<News> iter = all.iterator();
                while (iter.hasNext()) {
                    set.add(iter.next().getNid());
                }
                map.put("unreadMap", memberNewsDAO.findAllNotId(mid, set));
                map.put("allNews", all);
                map.put("newsCount", newsDAO.getAllCount(column, keyword));
                return map;
            }
        }
        return null;
    }

    @Override
    public boolean add(News vo) throws Exception {
        if (super.isAction(vo.getMember().getMid(), 12)) {
            vo.setPubdate(new Date());//将公告追加日期设置为当前日期！
            INewsDAO newsDAO = DAOFactory.getInstance(NewsDAOImpl.class);
            return newsDAO.doCreate(vo);
        }
        return false;
    }
}
