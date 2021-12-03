package com.example.simplelogin;

import android.content.Context;

public class GlobalVariable {
    private Context context;
    public static String BASE_URL = "http://192.168.1.9";
    public static String TYPE_CONN = "typeConnection";
    public static String RETROFIT = "retrofit";
    public static String VOLLEY = "volley";
    public static String PREFERENCE_NAME = "Lightlance";
    public static String CURRENT_USER_ID = "curentUserID";
    public static String CURRENT_FULLNAME = "currentFullname";
    public static String CURRENT_EMAIL = "currentEmail";
    public static String CURRENT_PASSWORD = "currentPassword";
    public static String UPDATE_USER = "updateUser";
    public GlobalVariable() {

    }
    public void setContext(Context context) {
        this.context = context;
    }
}
