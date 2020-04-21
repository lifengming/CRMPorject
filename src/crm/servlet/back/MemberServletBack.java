package crm.servlet.back;

import crm.service.back.IMemberServiceBack;
import crm.service.back.INewsServiceBack;
import crm.service.back.IRoleServiceBack;
import crm.service.back.impl.MemberServiceBackImpl;
import crm.service.back.impl.NewsServiceBackImpl;
import crm.service.back.impl.RoleServiceBackImpl;
import crm.vo.Groups;
import crm.vo.Member;
import crm.vo.Role;
import util.MD5Code;
import util.factory.ServiceFactory;
import util.split.SplitPageUtils;

import javax.servlet.annotation.WebServlet;
import java.util.*;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/23 下午1:38
 */
@WebServlet(urlPatterns = "/pages/back/member/MemberServletBack/*")
public class MemberServletBack extends AbstractCRMServlet {
    private Member member = new Member();

    public Member getMember() {
        return member;
    }

    public String rm() {
        IMemberServiceBack memberServiceBack = (IMemberServiceBack) ServiceFactory.getInstance(MemberServiceBackImpl.class);
        if (super.isAction(18)) {
            try {
                String ids = super.request.getParameter("ids");
                //有图片上传操作，此处禁止使用"-"作为拆分分隔符，
                //同时，也禁止在js中，使用"-"符号连接ids,,推荐使用&号ids += obj.value + "&" ;
                String result[] = ids.split("\\&");
                List<String> photos = new ArrayList<>();//保存要删除图片
                Set<String> mids = new HashSet<>();//保存要删除的用户名
                for (int x = 0; x < result.length; x++) {
                    String temp[] = result[x].split("\\:");
                    mids.add(temp[0]);
                    if (!"nophoto.jpg".equals(temp[1])) {
                        photos.add(temp[1]);
                    }
                }
                if (memberServiceBack.rm(super.getMid(),mids)) {
                    super.deleteFile(photos);
                    super.setUrlAndMsg("member.list.servlet", "vo.rm.success.msg");
                } else {
                    super.setUrlAndMsg("member.list.servlet", "vo.rm.failure.msg");
                }
            } catch (Exception e) {
                super.setUrlAndMsg("member.list.servlet", "vo.rm.failure.msg");
                e.printStackTrace();
            }
            return "forward.page";
        } else {
            return "error.page";
        }
    }
    public String editPassPre() {
        if (super.isAction(17)) {
            return "member.edit.password.admin.page";
        } else {
            return "error.page";
        }
    }

    public String editPass() {
        IMemberServiceBack memberServiceBack = (IMemberServiceBack) ServiceFactory.getInstance(MemberServiceBackImpl.class);
        if (super.isAction(17)) {
            try {
                this.member.setPassword(new MD5Code().getMD5ofStr(this.member.getPassword()));
                if (memberServiceBack.editPasswordByAdmin(super.getMid(), this.member)) {
                    super.setUrlAndMsg("member.list.servlet", "member.edit.password.admin.success");
                } else {
                    super.setUrlAndMsg("member.list.servlet", "member.edit.password.admin.failure");
                }
            } catch (Exception e) {
                super.setUrlAndMsg("member.list.servlet", "member.edit.password.admin.failure");
                e.printStackTrace();
            }
            return "forward.page";
        } else {
            return "error.page";
        }
    }
    public String editPre() {
        if (super.isAction(17)) {
            IMemberServiceBack memberServiceBack = (IMemberServiceBack) ServiceFactory.getInstance(MemberServiceBackImpl.class);
            try {
                String umid = super.request.getParameter("mid");
                Map<String, Object> map = memberServiceBack.editPre(super.getMid(), umid);
                super.request.setAttribute("allRoles", map.get("allRoles"));
                super.request.setAttribute("member", map.get("member"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "member.edit.page";
        } else {
            return "error.page";
        }
    }

    public String edit() {
        IMemberServiceBack memberServiceBack = (IMemberServiceBack) ServiceFactory.getInstance(MemberServiceBackImpl.class);
        if (super.isAction(17)) {

            List<String> names = null;
            if (super.existsUpload()) { // 现在有上传图片
                if ("nophoto.jpg".equals(this.member.getPhoto())) { // 原始图片不是 "nophoto.jpg"
                    names = super.createUploadFileName();
                    this.member.setPhoto(names.get(0));
                } else {                                            //// 原始图片是 "nophoto.jpg"
                    names = new ArrayList<String>();
                    names.add(this.member.getPhoto());
                }
            }
            try {
                if (memberServiceBack.edit(super.getMid(),this.member)) {
                    if (super.existsUpload()) {
                        super.saveUploadFile(names); // 保存上传文件
                    }
                    super.setUrlAndMsg("member.list.servlet", "vo.edit.success.msg");
                } else {
                    super.setUrlAndMsg("member.list.servlet", "vo.edit.failure.msg");
                }
            } catch (Exception e) {
                super.setUrlAndMsg("member.list.servlet", "vo.edit.failure.msg");
                e.printStackTrace();
            }
            return "forward.page";
        } else {
            return "error.page";
        }
    }
    public String listSplit() {
        IMemberServiceBack memberServiceBack = (IMemberServiceBack) ServiceFactory.getInstance(MemberServiceBackImpl.class);
        if (super.isAction(16)) {
            String urlKey = "member.list.servlet";
            SplitPageUtils spu = super.handleSplitParam();
            try {
                Map<String, Object> map = memberServiceBack.list(super.getMid(), spu.getColumn(),spu.getKeyWord(), spu.getCurrentPage(), spu.getLineSize());
                // List<Client> all = (List<Client>) map.get("allClients");
                if (map != null) {
                    int memberCount = (Integer) map.get("memberCount");
                    super.request.setAttribute("allMembers", map.get("allMembers"));
                    super.setSplitPage(urlKey, memberCount);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "member.list.page";
        } else {
            return "error.page";
        }
    }

    public String addPre() {
        IMemberServiceBack memberServiceBack = (IMemberServiceBack) ServiceFactory.getInstance(MemberServiceBackImpl.class);
        if (super.isAction(15)) {
            try {
                Map<String, Object> map = memberServiceBack.addPre(super.getMid());
                super.request.setAttribute("allRoles", map.get("allRoles"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "member.add.page";
        } else {
            return "error.page";
        }
    }

    public String add() {
        IMemberServiceBack memberServiceBack = (IMemberServiceBack) ServiceFactory.getInstance(MemberServiceBackImpl.class);
        if (super.isAction(15)) {
            try {
                this.member.setPassword(new MD5Code().getMD5ofStr(this.member.getPassword()));
                List<String> names = null;
                if (super.existsUpload()) { // 现在有上传图片
                    names = super.createUploadFileName();
                    this.member.setPhoto(names.get(0));
                } else {
                    this.member.setPhoto("nophoto.jpg");
                }
                if (memberServiceBack.add(super.getMid(), this.member)) {
                    if (super.existsUpload()) {
                        super.saveUploadFile(names); // 保存上传文件
                    }
                    super.setUrlAndMsg("member.add.servlet", "vo.add.success.msg");
                } else {
                    super.setUrlAndMsg("member.add.servlet", "vo.add.failure.msg");
                }
            } catch (Exception e) {
                super.setUrlAndMsg("member.add.servlet", "vo.add.failure.msg");
                e.printStackTrace();
            }
            return "forward.page";
        } else {
            return "error.page";
        }
    }

    public String editPasswordPre() {//修改前，需要跳转到修改界面
        return "member.password.edit.page";
    }

    public String editPassword() {
        String mid = (String) super.getSession().getAttribute("mid");
        String newpass = new MD5Code().getMD5ofStr(super.request.getParameter("newpass"));
        String oldpass = new MD5Code().getMD5ofStr(super.request.getParameter("oldpass"));
        IMemberServiceBack memberServiceBack = (IMemberServiceBack) ServiceFactory.getInstance(MemberServiceBackImpl.class);
        try {
            if (memberServiceBack.editPassword(mid, newpass, oldpass)) {
                super.setUrlAndMsg("member.logout.servlet", "member.password.edit.success");
            } else {
                super.setUrlAndMsg("member.logout.servlet", "member.password.edit.failure");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward.page";
    }

    @Override
    public String getDefaultColumn() {
        return "mid";
    }

    @Override
    public String getUploadDir() {
        return "/upload/member/";
    }
    @Override
    public String getType() {
        return "用户";
    }
    @Override
    public String getColumntData() {
        return "用户名:mid|联系电话:tel";
    }

}
