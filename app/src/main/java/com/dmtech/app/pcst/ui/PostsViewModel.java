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

//PostsFragment对应的视图模型
public class PostsViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    //可响应内容变化的帖子数据列表
    private MutableLiveData<List<Post>> posts;

    private String username;
    //android.os.Handler
    private Handler mMainHandler = new Handler();

    //对外提供帖子数据
//    public LiveData<List<Post>> getPosts() {
    public LiveData<List<Post>> getPosts(String username) {
        if (posts == null) {
            //只在初次提供时创建数据结构
            posts = new MutableLiveData<>();
            this.username = username;
            loadPosts();
        }
        return posts;
    }

    public void loadPosts() {
        //加载帖子数据
        //使用测试数据
        List<Post> postList = new ArrayList<>();
        posts.setValue(postList);
        //访问主题列表服务
        OkHttpClient client = new OkHttpClient();
        //先配置消息体构建器
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        bodyBuilder.add("username", username);
        //构建body对象
        FormBody body = bodyBuilder.build();
        //配置请求参数
        Request.Builder requestBuilder = new Request.Builder();
        //提交LoginServlet的URL
        requestBuilder.url(HttpHelper.getGetPostListServiceUrl())
                //采用POST方式提交用户信息
                .post(body);
        //生成请求对象
        Request request = requestBuilder.build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //从响应中提取服务端返回的信息
                String result = response.body().string();
                Log.d("Picasto", "Posts result: " + result);
                Gson gson = new Gson();
                //声明目标类型：PostView的列表
                Type type = (new TypeToken<List<PostView>>() {}).getType();
                List<PostView> postViews = gson.fromJson(result, type);
                //转为Post对象列表，以供界面显示
                List<Post> postList = new ArrayList<>();
                for (PostView pv : postViews) {
                    postList.add(Post.fromPostView(pv));
                }
                mMainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //更新数据，触发界面更新
                        posts.setValue(postList);
                    }
                });
            }
        });

    }

    private List<Post> mockPosts() {
        ArrayList<Post> posts = new ArrayList<>();
        posts.add(new Post("Tom"));
        posts.add(new Post("Jack"));
        posts.add(new Post("Mickey"));
        posts.add(new Post("Donald"));
        posts.add(new Post("foo"));
        return posts;
    }

    //增加新帖子
    public void addPost(Post post) {
//        List<Post> list = posts.getValue();
//        list.add(post);
//        posts.setValue(list);
    }
}