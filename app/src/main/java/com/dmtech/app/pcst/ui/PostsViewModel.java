package com.dmtech.app.pcst.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dmtech.app.pcst.data.Post;

import java.util.ArrayList;
import java.util.List;

//PostsFragment对应的视图模型
public class PostsViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    //可响应内容变化的帖子数据列表
    private MutableLiveData<List<Post>> posts;

    //对外提供帖子数据
    public LiveData<List<Post>> getPosts() {
        if (posts == null) {
            //只在初次提供时创建数据结构
            posts = new MutableLiveData<>();
            loadPosts();
        }
        return posts;
    }

    public void loadPosts() {
        //加载帖子数据
        //使用测试数据
        List<Post> postList = mockPosts();
        posts.setValue(postList);
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
        List<Post> list = posts.getValue();
        list.add(post);
        posts.setValue(list);
    }
}