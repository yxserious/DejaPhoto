package com.example.jeffphung.dejaphoto;

import com.google.api.services.people.v1.People;

import java.io.IOException;

/**
 * Created by jeffphung on 6/5/17.
 */
import android.content.Context;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.People;
import com.google.api.services.people.v1.PeopleScopes;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PeopleHelper {
    private static final String APPLICATION_NAME = "Peoples App";

    public static List<String> list;


    public static People setUp(Context context, String serverAuthCode) throws IOException {
        HttpTransport httpTransport = new NetHttpTransport();
        JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        list = Arrays.asList(PeopleScopes.CONTACTS, PeopleScopes.USERINFO_EMAIL);


        System.out.println("the code is: " + serverAuthCode);

        // Redirect URL for web based applications.
        // Can be empty too.
        String redirectUrl = "urn:ietf:wg:oauth:2.0:oob";
        String clientId = "233552778796-7fm2m9cd3h8cjforhuo8p8q0v5sse786.apps.googleusercontent.com";
        String clientSecret = "RdNHXJRCQZOFAmAX8V179uUP";

        // Exchange auth code for access token
        GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                httpTransport,
                jsonFactory,
                clientId,
                clientSecret,
                serverAuthCode,
                redirectUrl).setGrantType("authorization_code").execute();

        // Then, create a GoogleCredential object using the tokens from GoogleTokenResponse
        GoogleCredential credential = new GoogleCredential.Builder()
                .setClientSecrets(clientId, clientSecret)
                .setTransport(httpTransport)
                .setJsonFactory(jsonFactory)
                .build();

        credential.setFromTokenResponse(tokenResponse);

        // credential can then be used to access Google services
        return new People.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
