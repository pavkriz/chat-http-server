package com.example.services;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UhkAuthenticator {
    public boolean authenticate(String username, String password) {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
        provider.setCredentials(AuthScope.ANY, credentials);

        HttpClient client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();

        try {
            HttpResponse response = client.execute(
                    new HttpGet("https://stagws.uhk.cz/ws/services/rest2/help/getStagUserListForActualUser"));
            int statusCode = response.getStatusLine().getStatusCode();
            return statusCode == 200;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
