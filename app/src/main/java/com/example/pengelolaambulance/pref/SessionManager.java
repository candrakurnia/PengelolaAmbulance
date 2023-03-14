package com.example.pengelolaambulance.pref;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.example.pengelolaambulance.model.User;
import com.google.gson.Gson;

public class SessionManager {

    private final SharedPreferences.Editor editor;

    private static final String SHARE_NAME = "userPref";
    private static final String KEY_ID = "key.id";
    private static final String KEY_USER = "key.user";
    private static final String KEY_IS_LOGIN = "key.isLogin";

    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static SharedPreferences getSp(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getKeyId(Context context) {
        return getSp(context).getString(KEY_ID, null);
    }

    public static void setKeyId(Context context, String id_profile) {
        getSp(context).edit().putString(KEY_ID, id_profile).apply();
    }

    public static User getUser(Context context) {
        String user = getSp(context).getString(KEY_USER, null);
        if (TextUtils.isEmpty(user)) return null;
        return new Gson().fromJson(user, User.class);
    }

    public static void setUser(Context context, User user) {
        Gson gson = new Gson();
        getSp(context).edit().putString(KEY_USER, gson.toJson(user)).apply();
    }

    public static void setIsLogin(Context context, Boolean isLogin) {
        getSp(context).edit().putBoolean(KEY_IS_LOGIN, isLogin).apply();
    }

    public static Boolean getIsLogin(Context context) {
        return getSp(context).getBoolean(KEY_IS_LOGIN, false);
    }

    public void logout(Context context) {
        setKeyId(context, null);
        setIsLogin(context, false);
        editor.clear();
        editor.commit();
    }
}
