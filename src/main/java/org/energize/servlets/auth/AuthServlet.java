package org.energize.servlets.auth;



import com.google.gson.Gson;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.energize.domain.Credential;
import org.energize.dto.CredentialDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


@WebServlet(value = "/auth")
public class AuthServlet  extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CredentialDTO credentialDTO = new Gson().fromJson(request.getReader(),CredentialDTO.class);
        Credential credential = this.credentialDTOToCredential(credentialDTO);

        String notEncodedString = credential.getEmail() + ":" + credential.getPassword();


    }


    private Credential credentialDTOToCredential(CredentialDTO credentialDTO){
        Credential credential = new Credential();
        credential.setEmail(credentialDTO.getEmail());
        credential.setPassword(credentialDTO.getPassword());
        return credential;
    }



}
