package crm.servlet.back;

import crm.service.back.IMemberServiceBack;
import crm.service.back.impl.MemberServiceBackImpl;
import crm.vo.Member;
import util.MD5Code;
import util.factory.ServiceFactory;
import util.servlet.DispatcherServlet;

import javax.servlet.annotation.WebServlet;
import java.util.Map;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/21 下午3:25
 */
@WebServlet(urlPatterns = "/LoginServletBack/*")
public class LoginServletBack extends DispatcherServlet {
    private Member member = new Member();

    public Member getMember() {
        return member;
    }

    public String login() {
        this.member.setPassword(new MD5Code().getMD5ofStr(this.member.getPassword()));
        IMemberServiceBack memberServiceBack = (IMemberServiceBack) ServiceFactory.getInstance(MemberServiceBackImpl.class);
        try {
            Map<String, Object> map = memberServiceBack.login(this.member);
            boolean loginFlag = (Boolean) map.get("flag");

            if (loginFlag) {
                //System.out.println(this.member);
                super.getSession().setAttribute("mid", this.member.getMid());
                super.getSession().setAttribute("flag", this.member.getFlag());
                super.getSession().setAttribute("groups", this.member.getRole().getGroups());
                super.getSession().setAttribute("allActions", map.get("allActions"));
                super.getSession().setAttribute("Unread", map.get("Unread"));
                super.setUrlAndMsg("index.page", "member.login.success");
            } else {
                super.setUrlAndMsg("member.login.page", "member.login.failure");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward.page";
    }

    public String logout() {
        super.getSession().invalidate();
        super.setUrlAndMsg("member.login.page", "member.logout.success");
        return "forward.page";
    }

    @Override
    public String getDefaultColumn() {
        return null;
    }

    @Override
    public String getUploadDir() {
        return null;
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public String getColumntData() {
        return null;
    }
}
