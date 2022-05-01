package com.dmtech.app.pcst.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dmtech.app.pcst.R;
import com.dmtech.app.pcst.data.Post;
import com.dmtech.app.pcst.databinding.PostItemLayoutBinding;
import com.dmtech.app.pcst.ui.DateTimeUtil;
import com.dmtech.app.pcst.util.HttpHelper;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private LiveData<List<Post>> posts;

    public PostAdapter(LiveData<List<Post>>posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.post_item_layout,
                parent,
                false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.getValue().get(position);
        holder.binding.tvPostAuthor.setText(post.getAuthor());
        //绑定用户头像
        Glide.with(holder.binding.ivAuthorHead)
                .load(HttpHelper.getImageUrl(post.getAuthorHead()))
                .placeholder(R.drawable.logo_placeholder)
                .into(holder.binding.ivAuthorHead);
        //为VP2关联图集
        holder.binding.vp2Album.setAdapter(new AlbumAdapter(post));
        //处理点赞相关操作
        holder.binding.ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Posts", "点赞");
                post.setLike(!post.isLike());
                notifyDataSetChanged();
                //联网同步状态
            }
        });
        //根据帖子点赞状态设定图标外观
        if (post.isLike()) {
            holder.binding.ivLike.setImageResource(R.drawable.ic_like_fill);
        } else {
            holder.binding.ivLike.setImageResource(R.drawable.ic_like_line);
        }

        //点赞数
        holder.binding.tvNumLike.setText(String.valueOf(post.getNumLike()));
        //评论数
        holder.binding.tvNumComment.setText(String.valueOf(post.getNumComment()));
        //正文
        holder.binding.tvText.setText(String.valueOf(post.getText()));
        holder.binding.tvLocation.setText(String.valueOf(post.getLocation()));
        long timestamp = post.getTimestamp();
        holder.binding.tvDatetime.setText(DateTimeUtil.getDateTime(timestamp));
    }

    @Override
    public int getItemCount() {
        return posts.getValue().size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        PostItemLayoutBinding binding;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = PostItemLayoutBinding.bind(itemView);
        }
    }
}
