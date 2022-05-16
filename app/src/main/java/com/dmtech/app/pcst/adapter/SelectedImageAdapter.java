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
    //定义处理添加图片按钮的接口，由Activity实现并注入
    public interface OnAddImageClickListener {
        // 点击时调用
        void OnAddImageClick();
    }
    //接口实例，需要外部注入
    private OnAddImageClickListener onAddImageClickListener;
    //供外部调用注入接口的具体实现
    public void setOnAddImageClickListener(OnAddImageClickListener onAddImageClickListener) {
        this.onAddImageClickListener = onAddImageClickListener;
    }

    //长按网格事件处理接口
    public interface OnLongPressGridListener {
        void OnLongPressGrid(int position);
    }
    // 长按网格事件处理接口对象
    private OnLongPressGridListener onLongPressGridListener;
    //供外部调用注入长按事件监听器
    public void setOnLongPressGridListener(OnLongPressGridListener onLongPressGridListener) {
        this.onLongPressGridListener = onLongPressGridListener;
    }

    //选中的图片在手机上的路径
    //例如：/storage/emulated/0/DCIM/JiuZhai/一凡素材高清照片九寨沟 (2).jpg
    private List<String> selectedImages;

    public SelectedImageAdapter() {
        //初始化为空表
        this.selectedImages = new ArrayList<>();
    }
    // 添加一幅图的路径
    public void addImage(String imagePath) {
        this.selectedImages.add(imagePath);
        //通知数据变更
        notifyDataSetChanged();
    }

    // 移除一幅图
    public void removeImage(int position) {
        if (selectedImages != null && position >= 0 &&
                position < selectedImages.size()) {
            //列表包含position位置的图片时将其移除
            selectedImages.remove(position);
            //通知数据变更
            notifyDataSetChanged();
        }
    }

    // 获取选中图片路径列表
    public List<String> getSelectedImages() {
        return selectedImages;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 从布局生成一个对应的单元格视图对象
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.selected_image_grid,
                parent,
                false);
        //创建持有单元格的ViewHolder对象
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        if (position == selectedImages.size()) {
            //此项是添加按钮
            //显示添加图标
            holder.binding.ivSelected.setImageResource(R.drawable.ic_add);
            //监听点击
            holder.binding.ivSelected.setOnClickListener(v -> {
                Log.d("Compose", "To Select images");
                //显示图片选择页面
                //呼叫外部操作处理点击
                if (onAddImageClickListener != null) {
                    onAddImageClickListener.OnAddImageClick();
                }
            });
        }
        else {
            //此项是普通图片
            //根据图片路径生成Uri对象，并指定给图片视图
            Uri imageURI = Uri.parse("file://" + selectedImages.get(position));
            Log.d("Compose", "Select Uri: " + imageURI.toString());
            holder.binding.ivSelected.setImageURI(imageURI);
            //监听长按事件
            final int pos = position;
            holder.binding.ivSelected.setOnLongClickListener(v -> {
                //处理长按事件：调用外部注入的监听器对象，并告知哪个图片被长按
                if (onLongPressGridListener != null) {
                    onLongPressGridListener.OnLongPressGrid(pos);
                }
                return true;
            });
        }
    }

    @Override
    public int getItemCount() {
        // 列表展示全部图片预览 + 添加图片操作
        return selectedImages.size() + 1;
    }

    static class ImageHolder extends RecyclerView.ViewHolder {
        SelectedImageGridBinding binding;
        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            binding = SelectedImageGridBinding.bind(itemView);
        }
    }
}
