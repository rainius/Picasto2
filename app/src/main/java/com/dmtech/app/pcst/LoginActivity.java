package com.dmtech.app.pcst;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.dmtech.app.pcst.util.TypefaceUtil;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView appnameView = findViewById(R.id.tv_appname);
        TypefaceUtil.setTypeface(appnameView, "Lucida Calligraphy.ttf");
    }
}