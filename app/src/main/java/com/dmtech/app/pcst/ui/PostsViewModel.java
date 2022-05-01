package com.dmtech.app.pcst.ui;

import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dmtech.app.pcst.data.Post;
import com.dmtech.app.pcst.entity.PostView;
import com.dmtech.app.pcst.util.HttpHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PostsViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<List<Post>> posts;
    private String username;
    //与UI线程绑定的Handler
    private Handler mainHandler = new Handler();

    public LiveData<List<Post>> getPosts(String username) {
        if (posts == null) {
            this.username = username;
            posts = new MutableLiveData<List<Post>>();
            loadPosts();
        }
        return posts;
    }

    private void loadPosts() {
        posts.setValue(new ArrayList<>());
        //异步加载
        OkHttpClient client = new OkHttpClient();
        //创建表单数据为内容的请求报文body，并填入表单字段
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        bodyBuilder.add("username", username);
        //构建body对象
        FormBody body = bodyBuilder.build();
        //定义本次登录的请求:POST操作，提交表单数据
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(HttpHelper.getGetPostListServiceUrl()).post(body);
        Request request = requestBuilder.build();
        //Response response = client.newCall(request).execute();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                // 读取响应回来的json串
                String result = response.body().string();
                Log.d("Picasto", "Result: " + result);
                //用Gson解析响应中的JSON为Java对象列表
                Gson gson = new Gson();
                //声明要将JSON数据解析成PostView列表
                Type type = new TypeToken<List<PostView>>(){}.getType();
                List<PostView> postViews = gson.fromJson(result, type);
                Log.d("Picasto", "Post views: " + postViews.size());
                //将PostView转换成Post
                List<Post> postList = new ArrayList<>();
                for (PostView pv : postViews) {
                    postList.add(Post.fromPostView(pv));
                }
                Log.d("Picasto", "Posts: " + postList.size());
                //刷新数据
                //posts.setValue(postList);
                //将更新数据操作发布到主线程
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        posts.setValue(postList);
                    }
                });
            }
        });
    }

    public void addPost(Post post) {
//        List<Post> list = posts.getValue();
//        list.add(post);
//        //posts.setValue(list);
//        //将更新数据的操作装入运行于主线程的任务
//        mainHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                posts.setValue(list);
//            }
//        });
    }
}