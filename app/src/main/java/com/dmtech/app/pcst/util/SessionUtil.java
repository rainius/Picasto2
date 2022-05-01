package com.dmtech.app.pcst.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionUtil {

    public static String getUsername(Context context) {
        SharedPreferences prefs =
                context.getSharedPreferences("user_session", Context.MODE_PRIVATE);
        return prefs.getString("session", "");
    }
}
