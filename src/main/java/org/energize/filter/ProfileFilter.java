package org.energize.filter;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

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
import java.nio.charset.StandardCharsets;

@WebFilter(filterName = "ProfileFilter", urlPatterns = "/profile")
public class ProfileFilter extends HttpFilter {

    private static final String SECRET = "something_interesting_in_this_case";

    private String token;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession();
        this.token = (String) session.getAttribute("token");

        if (this.token == null){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            Jws<Claims> jwsClaims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(this.token);

            super.doFilter(req, res, chain);

        }catch (ExpiredJwtException exp){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.setContentType("text/html");
            res.getWriter().println("<h1>Token has expired, Please log in again</h1>");
            return;

        }catch (JwtException exp){
            exp.printStackTrace();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

    }
}
