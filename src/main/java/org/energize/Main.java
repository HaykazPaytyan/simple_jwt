package org.energize;


import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

public class Main {

    public static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static void main(String[] args) {

        String email = "paytyan.haykaz@gmail.com";
        String password = "12345678";

        String auth_string = email + ":" + password;

        byte[] encodedAuth = Base64.getEncoder().encode(auth_string.getBytes(StandardCharsets.UTF_8));

        String auth_header = "Basic " + new String(encodedAuth);

        HttpClient client = HttpClients.custom().build();

        HttpUriRequest request = RequestBuilder.get()
                .setUri("http://localhost:8080/profile")
                .addHeader(HttpHeaders.AUTHORIZATION, auth_header)
                .build();

        try {

            HttpResponse response = client.execute(request);

            int statusCode = response.getStatusLine().getStatusCode();

            System.out.println(statusCode);
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            System.out.println("Response body: " + responseBody);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
