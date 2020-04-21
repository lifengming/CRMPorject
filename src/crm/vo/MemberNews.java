package crm.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/8/12 下午9:33
 */
public class MemberNews implements Serializable {
    private Member member = new Member();
    private News news = new News();
    private Date rdate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberNews that = (MemberNews) o;
        return Objects.equals(member, that.member) &&
                Objects.equals(news, that.news);
    }

    @Override
    public int hashCode() {

        return Objects.hash(member, news);
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public Date getRdate() {
        return rdate;
    }

    public void setRdate(Date rdate) {
        this.rdate = rdate;
    }
}
