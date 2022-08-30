package org.energize.filter;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.energize.domain.Credential;
import org.energize.domain.User;
import org.energize.service.AuthService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;


@WebFilter(filterName = "AuthFilter", urlPatterns = "/auth")
public class AuthFilter extends HttpFilter {


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
                        .setIssuer("user")
                        .claim("email", credentials[0])
                        .setExpiration(new Date(new Date().getTime() + (1000 * 60 * 2)))
                        .signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)))
                        .compact();

                response.addHeader("X-Auth-Token", this.jws);
                chain.doFilter(req, res);

            }else{
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

        }else{
            try {
                Jws<Claims> jwsClaims = Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)))
                        .requireIssuer("user")
                        .build()
                        .parseClaimsJws(this.token);

                chain.doFilter(req, res);

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
}