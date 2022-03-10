package com.dmtech.app.pcst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dmtech.app.pcst.util.TypefaceUtil;

public class LoginActivity extends AppCompatActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 设置标题文字字体
        TextView tv = findViewById(R.id.tv_app_name);
        TypefaceUtil.setTypeface(tv, "Lucida Calligraphy.ttf");

        // 检查是否已登录
        checkLogin();

        // 监听登录按钮点击事件
        findViewById(R.id.btn_login).setOnClickListener(this);

        // 监听立即注册按钮点击事件
        findViewById(R.id.btn_go_register).setOnClickListener(this);
    }

    // 检查是否已登录并进行处理
    private void checkLogin() {
        if (hasLogin()) {
            //登录过，转入MainActivity
            navigateTo(MainActivity.class);
            //关闭登录页
            finish();
        }
    }

    // 检查是否已登录
    private boolean hasLogin() {
        //return true;
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            //登录过，转入MainActivity
            navigateTo(MainActivity.class);
            //关闭登录页
            finish();
        } else if (v.getId() == R.id.btn_go_register) {
            //登录过，转入MainActivity
            navigateTo(RegisterActivity.class);
        }
    }

    private void navigateTo(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}