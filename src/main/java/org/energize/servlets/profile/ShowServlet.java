package org.energize.servlets.profile;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.energize.domain.User;
import org.energize.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(value = "/profile")
public class ShowServlet extends HttpServlet {

    private static final String SECRET = "something_interesting_in_this_case";

    private final String TEMPLATE_DIR = "/templates/profile/show.html";

    private String token;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        this.token = (String) session.getAttribute("token");

        if (this.token == null){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }else{
            try {
                Jws<Claims> jwsClaims = Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)))
                        .build()
                        .parseClaimsJws(this.token);

                UserService userService = new UserService();
                User user = userService.getById(Long.parseLong(String.valueOf(jwsClaims.getBody().get("id"))));

                request.getRequestDispatcher(TEMPLATE_DIR).include(request, response);

            }catch (ExpiredJwtException exp){
                request.getRequestDispatcher("/templates/expire/show.html").include(request, response);
                session.invalidate();
                return;
            }catch (JwtException exp){
                exp.printStackTrace();
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }


    }
}
