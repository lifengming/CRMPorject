package crm.servlet.back;

import crm.service.back.INewsServiceBack;
import crm.service.back.ITaskServiceBack;
import crm.service.back.impl.NewsServiceBackImpl;
import crm.service.back.impl.TaskServiceBackImpl;
import crm.vo.News;
import util.factory.ServiceFactory;
import util.split.SplitPageUtils;

import javax.servlet.annotation.WebServlet;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/8/8 上午7:33
 */
@WebServlet(urlPatterns = "/pages/back/news/NewsServletBack/*")
public class NewsServletBack extends AbstractCRMServlet {
    private News news = new News();

    public News getNews() {
        return news;
    }

    public String rm() {
        if (super.isAction(14)) {
            INewsServiceBack newsServiceBack = (INewsServiceBack) ServiceFactory.getInstance(NewsServiceBackImpl.class);
            try {
                String ids = super.request.getParameter("ids");
                Set<Integer> nids = new HashSet<>();
                String result[] = ids.split("\\-");
                for (int x = 0; x < result.length; x++) {
                    nids.add(Integer.parseInt(result[x]));
                }
                if (newsServiceBack.rm(super.getMid(), nids)) {
                    super.setUrlAndMsg("news.list.servlet", "vo.rm.success.msg");
                } else {
                    super.setUrlAndMsg("news.list.servlet", "vo.rm.failure.msg");
                }
            } catch (Exception e) {
                e.printStackTrace();
                super.setUrlAndMsg("news.list.servlet", "vo.rm.failure.msg");
            }
        } else {
            return "error.page";
        }
        return "forward.page";
    }
    public String editPre() {
        if (super.isAction(13)) {
            INewsServiceBack newsServiceBack = (INewsServiceBack) ServiceFactory.getInstance(NewsServiceBackImpl.class);
            try {
                super.request.setAttribute("news", newsServiceBack.editPre(super.getMid(), this.news.getNid()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "news.edit.page";
        } else {
            return "error.page";
        }
    }

    public String edit() {
        INewsServiceBack newsServiceBack = (INewsServiceBack) ServiceFactory.getInstance(NewsServiceBackImpl.class);
        if (super.isAction(13)) {
            try {
                this.news.getMember().setMid(super.getMid());
                if (newsServiceBack.edit(this.news)) {
                    super.setUrlAndMsg("news.list.servlet", "vo.edit.success.msg");
                } else {
                    super.setUrlAndMsg("news.list.servlet", "vo.edit.failure.msg");
                }
            } catch (Exception e) {
                e.printStackTrace();
                super.setUrlAndMsg("news.list.servlet", "vo.edit.failure.msg");
            }
            return "forward.page";
        } else {
            return "error.page";
        }
    }

    public String show() {
        INewsServiceBack newsServiceBack = (INewsServiceBack) ServiceFactory.getInstance(NewsServiceBackImpl.class);
        if (super.isAction(33)) {

            try {
                super.request.setAttribute("news", newsServiceBack.show(super.getMid(), this.news.getNid()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "news.show.page";
        } else {
            return "error.page";
        }
    }
    public String list() {
        INewsServiceBack newsServiceBack = (INewsServiceBack) ServiceFactory.getInstance(NewsServiceBackImpl.class);
        if (super.isAction(11)) {
            String urlKey = "news.list.servlet";
            SplitPageUtils spu = super.handleSplitParam();
            try {
                Map<String, Object> map = newsServiceBack.listSplit(super.getMid(), spu.getColumn(),spu.getKeyWord(), spu.getCurrentPage(), spu.getLineSize());
                // List<Client> all = (List<Client>) map.get("allClients");
                if (map != null) {
                    int newsCount = (Integer) map.get("newsCount");
                    super.getSession().setAttribute("Unread", 0);
                    super.request.setAttribute("allNews", map.get("allNews"));
                    super.request.setAttribute("unreadMap", map.get("unreadMap"));
                    super.setSplitPage(urlKey, newsCount);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "news.list.page";
        } else {
            return "error.page";
        }
    }

    public String addPre() {
        if (super.isAction(12)) {
            return "news.add.page";
        } else {
            return "error.page";
        }
    }
    public String add() {
        INewsServiceBack newsServiceBack = (INewsServiceBack) ServiceFactory.getInstance(NewsServiceBackImpl.class);
        if (super.isAction(12)) {
            this.news.getMember().setMid(super.getMid());
            try {
                if (newsServiceBack.add(this.news)) {
                    super.setUrlAndMsg("newa.add.servlet", "vo.add.success.msg");
                } else {
                    super.setUrlAndMsg("newa.add.servlet", "vo.add.failure.msg");
                }
            } catch (Exception e) {
                super.setUrlAndMsg("newa.add.servlet", "vo.add.failure.msg");
                e.printStackTrace();
            }
            return "forward.page";
        } else {
            return "error.page";
        }

    }

    @Override
    public String getDefaultColumn() {
        return "title";
    }

    @Override
    public String getUploadDir() {
        return null;
    }

    @Override
    public String getType() {
        return "公告消息";
    }

    @Override
    public String getColumntData() {
        return "公告标题:title|发布者:mid";
    }
}
