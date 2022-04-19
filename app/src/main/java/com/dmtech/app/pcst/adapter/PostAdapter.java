package com.dmtech.app.pcst.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.dmtech.app.pcst.R;
import com.dmtech.app.pcst.data.Post;
import com.dmtech.app.pcst.databinding.PostItemLayoutBinding;

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
