package com.dmtech.app.pcst.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.dmtech.app.pcst.R;
import com.dmtech.app.pcst.data.Post;
import com.dmtech.app.pcst.databinding.PostItemLayoutBinding;

import java.util.List;
//相册页帖子列表适配器
public class PostAdapter
        extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    //帖子列表
    private LiveData<List<Post>> posts;

    public PostAdapter(LiveData<List<Post>> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //根据需要创建列表项
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.post_item_layout,
                parent,
                false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        //将条目数据绑定到视图
        //取当前列表项对应的帖子
        Post post = posts.getValue().get(position);
        //设定作者昵称
        holder.binding.tvPostAuthor.setText(post.getAuthor());
    }

    @Override
    public int getItemCount() {
        return posts.getValue().size();
    }

    //封装列表项中各视图
    public static class PostViewHolder extends RecyclerView.ViewHolder {
        //列表项视图绑定
        PostItemLayoutBinding binding;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = PostItemLayoutBinding.bind(itemView);
        }
    }
}
