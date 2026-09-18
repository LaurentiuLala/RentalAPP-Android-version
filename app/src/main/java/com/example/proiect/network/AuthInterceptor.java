package com.example.proiect.network;

import com.example.proiect.utils.SessionManager;
import java.io.IOException;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private SessionManager sessionManager;

    public AuthInterceptor(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        if (sessionManager.isLoggedIn()) {
            String credentials = Credentials.basic(sessionManager.getEmail(), sessionManager.getPassword());
            Request authenticatedRequest = originalRequest.newBuilder()
                    .header("Authorization", credentials)
                    .build();
            return chain.proceed(authenticatedRequest);
        }

        return chain.proceed(originalRequest);
    }
}
