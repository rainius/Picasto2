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

    public interface OnAddImageClickListener {
        void OnAddImageClick();
    }

    public interface OnLongPressGridListener {
        void OnLongPressGrid(int position);
    }



    private List<String> selectedImages;
    private OnAddImageClickListener onAddImageClickListener;
    private OnLongPressGridListener onLongPressGridListener;

    public SelectedImageAdapter(List<String> selectedImages) {
        this.selectedImages = new ArrayList<>();
        if (selectedImages != null) {
            this.selectedImages.addAll(selectedImages);
        }
    }

    public void setOnAddImageClickListener(OnAddImageClickListener onAddImageClickListener) {
        this.onAddImageClickListener = onAddImageClickListener;
    }

    public void setOnLongPressGridListener(OnLongPressGridListener onLongPressGridListener) {
        this.onLongPressGridListener = onLongPressGridListener;
    }

    public void addImage(String imagePath) {
        this.selectedImages.add(imagePath);
        notifyDataSetChanged();
    }

    public void removeImage(int position) {
        if (selectedImages != null && position >= 0 && position < selectedImages.size()) {
            selectedImages.remove(position);
            notifyDataSetChanged();
        }
    }

    public List<String> getSelectedImages() {
        return selectedImages;
    }

    public void setSelectedImages(List<String> selectedImages) {
        this.selectedImages = selectedImages;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.selected_image_grid,
                parent,
                false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        final int pos = position;

        holder.binding.ivSelected.setOnClickListener(null);
        holder.binding.ivSelected.setOnLongClickListener(null);

        if (position == selectedImages.size()) {
            //此项是添加按钮
            holder.binding.ivSelected.setImageResource(R.drawable.ic_add);

            holder.binding.ivSelected.setOnClickListener(v -> {
                if (onAddImageClickListener != null) {
                    onAddImageClickListener.OnAddImageClick();
                }
            });
        } else {
            String imagePath = selectedImages.get(pos);
            Uri imageURI = Uri.parse("file://" + imagePath);
            Log.d("Compose", "Select Uri: " + imageURI.toString());
            //此项是选中的图片
            holder.binding.ivSelected.setImageURI(imageURI);
            holder.binding.ivSelected.setOnLongClickListener(v -> {
                if (onLongPressGridListener != null) {
                    onLongPressGridListener.OnLongPressGrid(pos);
                }
                return true;
            });
        }
    }

    @Override
    public int getItemCount() {
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
