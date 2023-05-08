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

import com.dmtech.app.pcst.R;
import com.dmtech.app.pcst.adapter.PostAdapter;
import com.dmtech.app.pcst.data.Post;
import com.dmtech.app.pcst.databinding.FragmentPostsBinding;
import com.dmtech.app.pcst.util.SessionUtil;

public class PostsFragment extends Fragment {

    private PostsViewModel mViewModel;
    // 替代 findViewById
    FragmentPostsBinding mBinding;
    //帖子列表适配器
    private PostAdapter mPostAdapter;

    public static PostsFragment newInstance() {
        return new PostsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_posts, container, false);
        mBinding = FragmentPostsBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();
        asLinear(mBinding.rvPosts);
        return view;
    }
    //将RecyclerView配置成垂直线性
    private void asLinear(RecyclerView rv) {
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(PostsViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //取得本页面关联的视图模型
        mViewModel = new ViewModelProvider(this).get(PostsViewModel.class);
        //用视图模型中加载的数据初始化适配器
        String username = SessionUtil.getUsername(getContext());
        mPostAdapter = new PostAdapter(mViewModel.getPosts(username));
        //绑定适配器
        mBinding.rvPosts.setAdapter(mPostAdapter);
        //在此页面视图已经创建起来，开始对帖子列表数据进行观察
        mViewModel.getPosts(username).observe(
                getViewLifecycleOwner(),
                posts -> {
                    //更新UI
                    mPostAdapter.notifyDataSetChanged();
                    //每次内容改变会触发此函数，并传入帖子数据列表posts
                    Log.d("Picasto", "帖子数：" + posts.size());
                });
        //监听下拉刷新
        mBinding.swipeFresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //执行刷新操作
                mViewModel.addPost(new Post("New one"));
                //等待2秒后执行: android.os.Handler
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //结束刷新状态
                        mBinding.swipeFresh.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }
}





