package com.dmtech.app.pcst.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dmtech.app.pcst.data.Post;

import java.util.ArrayList;
import java.util.List;

public class PostsViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<List<Post>> posts;

    public LiveData<List<Post>> getPosts() {
        if (posts == null) {
            posts = new MutableLiveData<List<Post>>();
            loadPosts();
        }
        return posts;
    }

    public void addPost(Post post) {
        List<Post> list = posts.getValue();
        list.add(post);
        posts.setValue(list);
    }

    private void loadPosts() {
        //异步加载
        List<Post> postList = new ArrayList<>();
        postList.add(new Post("Tom"));
        postList.add(new Post("Jack"));
        postList.add(new Post("Mickey"));
        postList.add(new Post("Donald"));
        postList.add(new Post("foo"));
        posts.setValue(postList);
    }
}