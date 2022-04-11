package com.dmtech.app.pcst.ui;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dmtech.app.pcst.R;
import com.dmtech.app.pcst.adapter.PostAdapter;
import com.dmtech.app.pcst.data.Post;
import com.dmtech.app.pcst.databinding.PostsFragmentBinding;

import java.util.ArrayList;

public class PostsFragment extends Fragment {

    private PostsViewModel mViewModel;
    private RecyclerView mPostList;
    private PostAdapter mPostAdapter;
    // 替代 findViewById
    PostsFragmentBinding mBinding;

    public static PostsFragment newInstance() {
        return new PostsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.posts_fragment, container, false);
        mBinding = PostsFragmentBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();
        asLinear(mBinding.rvPosts);
        return view;
    }

    private void asLinear(RecyclerView rv) {
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PostsViewModel.class);
        // TODO: Use the ViewModel
        mPostAdapter = new PostAdapter(mViewModel.getPosts());
        mBinding.rvPosts.setAdapter(mPostAdapter);
        mViewModel.getPosts().observe(getViewLifecycleOwner(), posts -> {
            //更新UI
            mPostAdapter.notifyDataSetChanged();
            Log.d("Posts", "帖子数：" + posts.size());
        });

        mBinding.swipeFresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.swipeFresh.setRefreshing(false);
                    }
                },2000);

                mViewModel.addPost(new Post("New one"));
            }
        });
    }

}