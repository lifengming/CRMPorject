package crm.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/8/7 下午4:15
 */
@SuppressWarnings("serial")
public class News implements Serializable {
    private Integer nid;
    private Member member = new Member();
    private String title;
    private Integer type;
    private Date pubdate;
    private String note;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return Objects.equals(nid, news.nid) &&
                Objects.equals(title, news.title) &&
                Objects.equals(type, news.type) &&
                Objects.equals(pubdate, news.pubdate) &&
                Objects.equals(note, news.note);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nid, title, type, pubdate, note);
    }

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getPubdate() {
        return pubdate;
    }

    public void setPubdate(Date pubdate) {
        this.pubdate = pubdate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
