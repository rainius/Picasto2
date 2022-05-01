package com.dmtech.app.pcst;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dmtech.app.pcst.entity.UserResponse;
import com.dmtech.app.pcst.util.HttpHelper;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity
        implements View.OnClickListener {

    EditText mUsernameEdit, mPasswordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUsernameEdit = findViewById(R.id.edit_reg_user);
        mPasswordEdit = findViewById(R.id.edit_reg_password);

        //监听注册按钮
        findViewById(R.id.btn_register).setOnClickListener(this);
        //监听去登录按钮
        findViewById(R.id.btn_go_login).setOnClickListener(this);
        //监听回退图标按钮
        findViewById(R.id.btn_register_back).setOnClickListener(this);
    }

    //当点击提交按钮时
    private void onSubmitRegister() {
        //从编辑框读取用户注册信息
        String username = mUsernameEdit.getText().toString();
        String password = mPasswordEdit.getText().toString();
        Log.d("Register", "注册新用户：" + username + ", " + password);
        RegisterTask regTask = new RegisterTask(username, password);
        regTask.execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                onSubmitRegister();
                break;
            case R.id.btn_go_login:
            case R.id.btn_register_back:
                onBackPressed();
                break;
        }
    }

    private class RegisterTask extends AsyncTask<Void, Void, String> {
        private String username;
        private String password;

        public RegisterTask(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected void onPostExecute(String result) {
            //在主线程
            Log.d("Register", "Register result: " + result);
            //通过GSON把收到的JSON结果转为UserResponse对象
            Gson gson = new Gson();
            UserResponse response = gson.fromJson(result, UserResponse.class);
            Log.d("LoginTask", "Response: " + response);
            if (response == null) {
                //没有访问到服务器
                Toast.makeText(RegisterActivity.this, R.string.server_error, Toast.LENGTH_SHORT)
                        .show();
            }

            Toast.makeText(RegisterActivity.this, response.getMsg(), Toast.LENGTH_SHORT)
                    .show();

            if (response.getCode() == 1) {
                //注册成功，关闭注册界面
                finish();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            // 执行OkHttp网络访问
            String result = "";
//            final String REG_URL =
//                    "http://192.168.101.191:8080/PicastoServer/RegisterServlet";
            OkHttpClient client = new OkHttpClient();
            //创建表单数据为内容的请求报文body，并填入表单字段
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            bodyBuilder.add("username", username);
            bodyBuilder.add("pwd", password);
            //构建body对象
            FormBody body = bodyBuilder.build();
            //定义本次登录的请求:POST操作，提交表单数据
            Request.Builder requestBuilder = new Request.Builder();
            requestBuilder.url(HttpHelper.getRegisterServiceUrl()).post(body);
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

}