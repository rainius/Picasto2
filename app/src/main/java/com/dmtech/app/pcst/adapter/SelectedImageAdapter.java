package com.dmtech.app.pcst.adapter;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dmtech.app.pcst.R;
import com.dmtech.app.pcst.databinding.SelectedImageGridBinding;

import java.util.ArrayList;
import java.util.List;

public class SelectedImageAdapter extends RecyclerView.Adapter<SelectedImageAdapter.ImageHolder> {
    //处理添加图片按钮点击事件的接口
    public interface OnAddImageClickListener {
        void OnAddImageClick();
    }

    private OnAddImageClickListener onAddImageClickListener;

    public void setOnAddImageClickListener(OnAddImageClickListener onAddImageClickListener) {
        this.onAddImageClickListener = onAddImageClickListener;
    }

    //长按监听接口
    public interface OnLongPressGridListener {
        void OnLongPressGrid(int position);
    }
    private OnLongPressGridListener onLongPressGridListener;

    public void setOnLongPressGridListener(OnLongPressGridListener onLongPressGridListener) {
        this.onLongPressGridListener = onLongPressGridListener;
    }

    //选中图片在手机上的路径
    private List<String> selectedImages;

    public SelectedImageAdapter() {
        //初始化为空表
        selectedImages = new ArrayList<>();
    }

    //添加照片
    public void addImage(String imagePath) {
        this.selectedImages.add(imagePath);
        notifyDataSetChanged();
    }

    //删除图片
    public void removeImage(int position) {
        if (selectedImages != null && position >= 0 && position < selectedImages.size()) {
            selectedImages.remove(position);
            notifyDataSetChanged();
        }
    }

    //获取选中全部图片
    public List<String> getSelectedImages() {
        return selectedImages;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.selected_image_grid,
                parent, false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        if (position == selectedImages.size()) {
            //添加按钮
            holder.binding.ivSelected.setImageResource(R.drawable.ic_add);
            holder.binding.ivSelected.setOnClickListener(v -> {
                Log.d("Compose", "To select images");
                if (onAddImageClickListener != null) {
                    onAddImageClickListener.OnAddImageClick();
                }
            });
        } else {
            //普通图片
            Uri imageURI = Uri.parse("file://" + selectedImages.get(position));
            holder.binding.ivSelected.setImageURI(imageURI);
            final int pos = position;
            holder.binding.ivSelected.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onLongPressGridListener != null) {
                        onLongPressGridListener.OnLongPressGrid(pos);
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return selectedImages.size() + 1;
    }

    //单元格对应的ViewHolder
    static class ImageHolder extends RecyclerView.ViewHolder {
        SelectedImageGridBinding binding;
        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            binding = SelectedImageGridBinding.bind(itemView);
        }
    }
}
