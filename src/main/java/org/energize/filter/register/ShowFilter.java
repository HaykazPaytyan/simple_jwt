package org.energize.filter.register;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "ShowFilter", urlPatterns = "/register")
public class ShowFilter extends HttpFilter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(0);

        String token = (String) session.getAttribute("token");

        if (token != null){
            response.sendRedirect("/profile");
        }else{
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        super.doFilter(req, res, chain);
    }
}