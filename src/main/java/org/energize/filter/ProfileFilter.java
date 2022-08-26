package org.energize.filter;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.energize.domain.Credential;
import org.energize.domain.User;
import org.energize.service.AuthService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;


@WebFilter(filterName = "ProfileFilter", urlPatterns = "/profile")
public class ProfileFilter extends HttpFilter {

    private AuthService authService;

    private Credential credential;

    private static final String SECRET = "something_interesting_in_this_case";

    private String jws;

    public ProfileFilter() {
        this.authService = new AuthService();
        this.credential = new Credential();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {


        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;


        String authorization = request.getHeader("Authorization");
        if (authorization == null || !(authorization.matches("Basic .+") || authorization.matches("Bearer .+"))){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }


        String token = authorization.replaceAll("(Basic)|(Bearer)", "").trim();


        if (authorization.matches("Basic .+")){
            String decodedCredentials = new String(Base64.getDecoder().decode(token.getBytes()));
            String[] credentials = decodedCredentials.split(":");


            if (credentials.length != 2){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            this.credential.setEmail(credentials[0]);
            this.credential.setPassword(credentials[1]);

            User user = this.authService.attempt(credential);


            if (credentials[0].equals(user.getEmail()) && credentials[1].equals(user.getPassword())){
                 jws = Jwts.builder()
                        .setIssuer("user")
                        .claim("email", credentials[0])

                        .setExpiration(new Date(new Date().getTime() + (1000 * 60 * 2)))
                        .signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)))
                        .compact();

                response.addHeader("X-Auth-Token", jws);
                chain.doFilter(request,response);

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
                        .parseClaimsJws(token);


                chain.doFilter(request, response);

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