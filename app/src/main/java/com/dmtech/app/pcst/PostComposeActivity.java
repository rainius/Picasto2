package com.dmtech.app.pcst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.dmtech.app.pcst.adapter.SelectedImageAdapter;
import com.dmtech.app.pcst.databinding.ActivityPostComposeBinding;
import com.dmtech.app.pcst.util.PictureSelectorUtil;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;

import java.util.ArrayList;

public class PostComposeActivity extends AppCompatActivity {

    private ActivityPostComposeBinding mBinding;
    private SelectedImageAdapter mSelectedImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityPostComposeBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        //把工具栏指定为操作栏
        setSupportActionBar(mBinding.toolbarCompose);
        //显示回退按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //配置已选图片网格视图
        setupSelectedImagesView();
    }

    private void setupSelectedImagesView() {
        //定义网格布局
        RecyclerView.LayoutManager gridManager =
                new GridLayoutManager(this, 3);
        mBinding.rvSelectedImages.setLayoutManager(gridManager);
        //适配器初始化并注入所需接口对象
        mSelectedImageAdapter = new SelectedImageAdapter();
        //设定添加新图监听
        mSelectedImageAdapter.setOnAddImageClickListener(() -> {
            onAddImage();
        });
        //设定长按网格单元监听
        mSelectedImageAdapter.setOnLongPressGridListener(position -> {
            onDeleteSelectedImage(position);
        });
        //关联适配器
        mBinding.rvSelectedImages.setAdapter(mSelectedImageAdapter);
    }

    private void onDeleteSelectedImage(int position) {
        Log.d("Compose", "删除图片： " + position);
    }

    private void onAddImage() {
        Log.d("Compose", "添加新图片");
        openPictureSelector(9 - mSelectedImageAdapter.getSelectedImages().size());
    }

    private void openPictureSelector(int canSelectNum) {
        PictureSelectorUtil.show(this, canSelectNum,
                new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(ArrayList<LocalMedia> result) {
                Log.d("Compose", "Selected images: " + result.size());
                for (LocalMedia localMedia : result) {
                    Log.d("Compose", "Selected image: " + localMedia.getRealPath());
                    mSelectedImageAdapter.addImage(localMedia.getRealPath());
                }
            }

            @Override
            public void onCancel() {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.item_finish) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}