package org.energize.servlets;

import org.energize.domain.Credential;
import org.energize.dto.CredentialDTO;
import org.energize.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;



@WebServlet(value = "/profile")
public class ProfileServlet  extends HttpServlet {

    private AuthService authService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.authService = new AuthService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("auth/profile.html").include(request, response);
    }


    private Credential credentialDtoToCredential(CredentialDTO dto){
        Credential credential = new Credential();
        credential.setEmail(dto.getEmail());
        credential.setPassword(dto.getPassword());
        return credential;
    }

}
