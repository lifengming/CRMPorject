package crm.vo;

import sun.plugin2.message.Serializer;

import java.io.Serializable;
import java.util.List;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/22 上午1:09
 */
@SuppressWarnings("serial")
public class Groups implements Serializable {
    private Integer gid;
    private String title;
    private String img;
    private String type;
    private List<Role> roles;
    private List<Action> actions;

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    @Override
    public String toString() {
        return "Groups{" +
                "gid=" + gid +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", type='" + type + '\'' +
                ", actions=" + actions +
                '}';
    }
}
