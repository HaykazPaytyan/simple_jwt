package org.energize.filter;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.energize.domain.Credential;
import org.energize.domain.User;
import org.energize.service.AuthService;
import org.energize.utility.MD5;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;


@WebFilter(filterName = "MainFilter", urlPatterns = "/auth")
public class MainFilter extends HttpFilter {


    private static final String SECRET = "something_interesting_in_this_case";
    private String jws;
    private String token;
    private String authorization;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {


        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;


        this.authorization = request.getHeader("Authorization");
        if (this.authorization == null || !(this.authorization.matches("Basic .+") || this.authorization.matches("Bearer .+"))){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        this.token = this.authorization.replaceAll("(Basic)|(Bearer)", "").trim();

        if (this.authorization.matches("Basic .+")){
            String decodedCredentials = new String(Base64.getDecoder().decode(this.token.getBytes()));
            String[] credentials = decodedCredentials.split(":");

            if (credentials.length != 2){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            Credential credential = new Credential();
            credential.setEmail(credentials[0]);
            credential.setPassword(credentials[1]);

            AuthService authService = new AuthService();
            User user = authService.attempt(credential);


            if (credentials[0].equals(user.getEmail()) && credentials[1].equals(user.getPassword())){
                this.jws = Jwts.builder()
                        .claim("id", MD5.getMd5(user.getId().toString()))
                        .setExpiration(new Date(new Date().getTime() + (1000 * 60 * 2)))
                        .signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)))
                        .compact();

                HttpSession session = request.getSession();
                session.setAttribute("token",this.jws);


            }else{
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

        }

        super.doFilter(req, res,chain);
    }
}