package com.example.proiect.utils;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class SessionManager {
    private static final String PREF_NAME = "user_session";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_ROLE = "role";

    private SharedPreferences sharedPreferences;

    public SessionManager(Context context) {
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            sharedPreferences = EncryptedSharedPreferences.create(
                    PREF_NAME,
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
    }

    public void saveUser(Long id, String email, String password, String role) {
        sharedPreferences.edit()
                .putLong(KEY_USER_ID, id)
                .putString(KEY_EMAIL, email)
                .putString(KEY_PASSWORD, password)
                .putString(KEY_ROLE, role)
                .apply();
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    public String getPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, null);
    }

    public Long getUserId() {
        return sharedPreferences.getLong(KEY_USER_ID, -1L);
    }

    public String getRole() {
        return sharedPreferences.getString(KEY_ROLE, "CLIENT");
    }

    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(getRole());
    }

    public boolean isLoggedIn() {
        return getEmail() != null && getPassword() != null;
    }

    public void logout() {
        sharedPreferences.edit().clear().apply();
    }
}
