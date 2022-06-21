package org.energize.servlets.auth;

import com.google.gson.Gson;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.energize.domain.Credential;
import org.energize.domain.User;
import org.energize.dto.CredentialDTO;
import org.energize.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;


@WebServlet(value = "/auth")
public class AuthServlet  extends HttpServlet {

    private AuthService authService;

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Override
    public void init() throws ServletException {
        super.init();
        this.authService = new AuthService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CredentialDTO credentialDTO = new Gson().fromJson(request.getReader(),CredentialDTO.class);
        Credential credential = this.credentialDtoToCredential(credentialDTO);
        User user = this.authService.attempt(credential);

        String auth_string = user.getEmail() + ":" + user.getPassword();

        byte[] encodedAuth = Base64.getEncoder().encode(auth_string.getBytes(StandardCharsets.UTF_8));

        String auth_header = "Basic " + new String(encodedAuth);

        HttpClient client = HttpClients.custom().build();
        client.execute(new HttpGet("http://localhost:8080/profile"));

//        HttpUriRequest manualRequest = RequestBuilder.get()
//                .setUri("http://localhost:8080/profile")
//                .addHeader(HttpHeaders.AUTHORIZATION, auth_header)
//                .build();
//
//        try {
//
//            HttpResponse manualResponse = client.execute(manualRequest);
//            client.execute(new HttpGet("http://localhost:8080/profile"));
//            int statusCode = manualResponse.getStatusLine().getStatusCode();
//            System.out.println(statusCode);
//            String responseBody = EntityUtils.toString(manualResponse.getEntity(), StandardCharsets.UTF_8);
//            System.out.println("Response body: " + responseBody);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    }

    private Credential credentialDtoToCredential(CredentialDTO dto){
        Credential credential = new Credential();
        credential.setEmail(dto.getEmail());
        credential.setPassword(dto.getPassword());
        return credential;
    }


}
