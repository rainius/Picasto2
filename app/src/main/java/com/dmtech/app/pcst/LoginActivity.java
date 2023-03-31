package com.dmtech.app.pcst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dmtech.app.pcst.entity.UserResponse;
import com.dmtech.app.pcst.util.HttpHelper;
import com.dmtech.app.pcst.util.TypefaceUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity
        implements View.OnClickListener {

    //用户名和密码输入
    private EditText mUsernameEdit, mPasswordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获取界面的编辑框对象
        mUsernameEdit = findViewById(R.id.edit_login_user);
        mPasswordEdit = findViewById(R.id.edit_login_password);

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
        //读取登录状态，为空标明没有登录，否则标明已经登录
        String session = readSession();
        if (TextUtils.isEmpty(session)) {
            //null or ""
            return false;
        }
        Log.d("Login", "已经登录：" + session);
        return true;
    }

    private String readSession() {
        //读取已存储的用户名
        SharedPreferences prefs =
                getSharedPreferences("user_session",
                        MODE_PRIVATE);
        return prefs.getString("session", "");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            //删除旧代码：
                //navigateTo(MainActivity.class);
                //finish();
            //从编辑框读取用户登录信息
            String username = mUsernameEdit.getText().toString();
            String password = mPasswordEdit.getText().toString();
            Log.d("Picasto", "user: " + username + ", pwd: " + password);
            // 发起登录
            startLogin(username, password);
        } else if (v.getId() == R.id.btn_go_register) {
            //登录过，转入MainActivity
            navigateTo(RegisterActivity.class);
        }
    }

    private void startLogin(String username, String password) {
        OkHttpClient client = new OkHttpClient();
        //创建表单数据为内容的请求报文body，并填入表单字段
        //先配置消息体构建器
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        bodyBuilder.add("username", username);
        bodyBuilder.add("pwd", password);
        //构建body对象
        FormBody body = bodyBuilder.build();
        //配置请求参数
        Request.Builder requestBuilder = new Request.Builder();
        //提交LoginServlet的URL
        requestBuilder.url(HttpHelper.getLoginServiceUrl())
                      //采用POST方式提交用户信息
                      .post(body);
        //生成请求对象
        Request request = requestBuilder.build();
        //发起异步请求:将请求装入处理队列
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                //访问失败
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //处理服务器端响应
                //从响应中提取服务端返回的信息
                String result = response.body().string();
                Log.d("Picasto", "Login result: " + result);
                //通过GSON把收到的JSON结果转为UserResponse对象
                Gson gson = new Gson();
                UserResponse userResponse = gson.fromJson(result, UserResponse.class);
                Log.d("Picasto", "user response: " + userResponse);
                //发生错误
                if (userResponse == null) {
                    //仅仅提示用户后中断执行
                    Toast.makeText(LoginActivity.this,
                            "网络出错了",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                //在用户界面线程处理登录结果
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (userResponse.getCode() >= 0) {
                            //登录成功
                            //保存登录状态
                            saveSession(username);
                            //登录成功，跳转到主界面
                            navigateTo(MainActivity.class);
                            //关闭登录页
                            finish();
                        } else {
                            //登录失败，提示用户原因
                            Toast.makeText(LoginActivity.this,
                                    userResponse.getMsg(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void saveSession(String username) {
        //长久保存用户名作为该用户与服务会话的凭证
        //创建数据保存：提供名字，和保存模式（私有）
        SharedPreferences prefs =
                getSharedPreferences("user_session", MODE_PRIVATE);
        //进入编辑模式并按名-值方式存入当前登录的用户名
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("session", username);
        //提交数据修改
        editor.commit();
    }

    private void navigateTo(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}