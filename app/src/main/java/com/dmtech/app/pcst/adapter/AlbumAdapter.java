package com.dmtech.app.pcst.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dmtech.app.pcst.R;
import com.dmtech.app.pcst.data.Post;
import com.dmtech.app.pcst.databinding.PostAlbumLayoutBinding;
import com.dmtech.app.pcst.util.HttpHelper;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumHolder> {

    //样例图片数组
    private static final int[] SAMPLE_PHOTOS = {
            R.drawable.sample0,
            R.drawable.sample1,
            R.drawable.sample2
    };

    private Post post;

    public AlbumAdapter(Post post) {
        this.post = post;
    }

    @NonNull
    @Override
    public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.post_album_layout,
                parent,
                false);
        return new AlbumHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumHolder holder, int position) {
        String photoPath = post.getPhotos().get(position);
        Log.d("Picasto", "photo path: " + photoPath);
        Glide.with(holder.binding.ivAlbumPhoto)
                .load(HttpHelper.getImageUrl(photoPath))
                .into(holder.binding.ivAlbumPhoto);
    }

    @Override
    public int getItemCount() {
        return post.getPhotos().size();
    }

    public static class AlbumHolder extends RecyclerView.ViewHolder {
        PostAlbumLayoutBinding binding;
        public AlbumHolder(@NonNull View itemView) {
            super(itemView);
            binding = PostAlbumLayoutBinding.bind(itemView);
        }
    }
}
