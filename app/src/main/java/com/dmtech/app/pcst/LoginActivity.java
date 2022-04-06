package com.dmtech.app.pcst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dmtech.app.pcst.entity.UserResponse;
import com.dmtech.app.pcst.util.TypefaceUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity
        implements View.OnClickListener {

    EditText mUsernameEdit, mPasswordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        if (TextUtils.isEmpty(session)) { //null or ""
            return false;
        }
        Log.d("Login", "已经登录：" + session);
        return true;
    }

    // 保存登录状态
    private void saveSession(String session) {
        SharedPreferences prefs =
                getSharedPreferences("user_session", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("session", session);
        editor.commit();
    }

    // 读取登录状态
    private String readSession() {
        SharedPreferences prefs =
                getSharedPreferences("user_session", MODE_PRIVATE);
        return prefs.getString("session", "");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            //从编辑框读取用户登录信息
            String username = mUsernameEdit.getText().toString();
            String password = mPasswordEdit.getText().toString();
            Log.d("Login", "user: " + username + ", pwd: " + password);
            // 发起登录
            startLogin(username, password);
        } else if (v.getId() == R.id.btn_go_register) {
            //登录过，转入MainActivity
            navigateTo(RegisterActivity.class);
        }
    }

    // 发起登录
    private void startLogin(String username, String password) {
        // 启动异步登录
        LoginTask task = new LoginTask(username, password);
        task.execute();
    }

    // 用户登录任务
    private class LoginTask extends AsyncTask<Void, Void, String> {
        private String username;
        private String password;

        public LoginTask(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected void onPostExecute(String result) {
            //在主线程
            Log.d("LoginTask", "Login result: " + result);
            //通过GSON把收到的JSON结果转为UserResponse对象
            Gson gson = new Gson();
            UserResponse response = gson.fromJson(result, UserResponse.class);
            Log.d("LoginTask", "Response: " + response);
            if (response == null) {
                //没有访问到服务器
                Toast.makeText(LoginActivity.this, R.string.server_error, Toast.LENGTH_SHORT)
                        .show();
            }

            if (response.getCode() >= 0) {
                //保存登录状态
                saveSession(username);
                //登录成功，跳转到主界面
                navigateTo(MainActivity.class);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, response.getMsg(), Toast.LENGTH_SHORT)
                        .show();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            // 执行OkHttp网络访问
            String result = "";
            final String LOGIN_URL =
                    "http://192.168.3.183:8080/PicastoServer/LoginServlet";
            OkHttpClient client = new OkHttpClient();
            //创建表单数据为内容的请求报文body，并填入表单字段
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            bodyBuilder.add("username", username);
            bodyBuilder.add("pwd", password);
            //构建body对象
            FormBody body = bodyBuilder.build();
            //定义本次登录的请求:POST操作，提交表单数据
            Request.Builder requestBuilder = new Request.Builder();
            requestBuilder.url(LOGIN_URL).post(body);
            Request request = requestBuilder.build();
            //用client发送request，获得响应response
            try {
                Response response = client.newCall(request).execute();
                // 读取响应回来的json串
                result = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    private void navigateTo(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}