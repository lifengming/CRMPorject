package crm.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/21 下午2:03
 */
@SuppressWarnings("serial")
public class Member implements Serializable {
    private String mid;
    private String password;
    private String tel;
    private Date lastdate;
    private String photo;
    private Integer flag;
    private Integer locked;
    private List<Logs> logs;
    private Role role = new Role();
    private List<Client> clients;
    private List<Task> tasks;
    private List<News> news;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(mid, member.mid) &&
                Objects.equals(password, member.password) &&
                Objects.equals(tel, member.tel) &&
                Objects.equals(lastdate, member.lastdate) &&
                Objects.equals(photo, member.photo) &&
                Objects.equals(flag, member.flag) &&
                Objects.equals(locked, member.locked);
    }

    @Override
    public int hashCode() {

        return Objects.hash(mid, password, tel, lastdate, photo, flag, locked);
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Logs> getLogs() {
        return logs;
    }

    public void setLogs(List<Logs> logs) {
        this.logs = logs;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Date getLastdate() {
        return lastdate;
    }

    public void setLastdate(Date lastdate) {
        this.lastdate = lastdate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    @Override
    public String toString() {
        return "Member{" +
                "mid='" + mid + '\'' +
                ", password='" + password + '\'' +
                ", tel='" + tel + '\'' +
                ", lastdate=" + lastdate +
                ", photo='" + photo + '\'' +
                ", flag=" + flag +
                ", locked=" + locked +
                ", logs=" + logs +
                ", role=" + role +
                '}';
    }
}
