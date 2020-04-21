package crm.filter;

import util.BasepathUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/23 上午1:36
 */
@WebFilter("/pages/back/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        if (session.getAttribute("mid") != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {//应该将请求交给"forward.jsp"进行信息提示和路径的跳转
            servletRequest.setAttribute("msg", "您还未登录，请先登录");
            servletRequest.setAttribute("url",  "/login.jsp");
            servletRequest.getRequestDispatcher("/pages/forward.jsp").forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
