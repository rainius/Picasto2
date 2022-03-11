package com.dmtech.app.pcst;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class RegisterActivity extends AppCompatActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //监听注册按钮
        findViewById(R.id.btn_register).setOnClickListener(this);
        //监听去登录按钮
        findViewById(R.id.btn_go_login).setOnClickListener(this);
        //监听回退图标按钮
        findViewById(R.id.btn_register_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
            case R.id.btn_go_login:
            case R.id.btn_register_back:
                onBackPressed();
                break;
        }
    }
}