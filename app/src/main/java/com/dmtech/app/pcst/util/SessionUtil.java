package com.dmtech.app.pcst.util;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionUtil {
    public static String getUsername(Context context) {
        //读取已存储的用户名
        SharedPreferences prefs =
                context.getSharedPreferences("user_session",
                        MODE_PRIVATE);
        return prefs.getString("session", "");
    }
}
