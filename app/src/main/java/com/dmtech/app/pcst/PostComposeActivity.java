package com.dmtech.app.pcst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dmtech.app.pcst.adapter.SelectedImageAdapter;
import com.dmtech.app.pcst.databinding.ActivityComposePostBinding;
import com.dmtech.app.pcst.picsel.GlideEngine;
import com.dmtech.app.pcst.util.PictureSelectorUtil;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;

import java.util.ArrayList;

public class PostComposeActivity extends AppCompatActivity {

    private ActivityComposePostBinding mBinding;
    //网格视图适配器
    private SelectedImageAdapter mSelectedImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityComposePostBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        //将本页Toolbar设置为Actionbar
        setSupportActionBar(mBinding.toolbarCompose);
        //将回退按钮显示开关开启
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //装配已选图片网格视图
        setupSelectedImagesView();
    }

    private void setupSelectedImagesView() {
        //配置RecyclerView并关联适配器
        //定义网格视图布局管理器，每行3列
        RecyclerView.LayoutManager gridManager =
                new GridLayoutManager(this, 3);
        mBinding.rvSelectedImages.setLayoutManager(gridManager);
        //适配器初始化，并注入所需要的接口对象
        mSelectedImageAdapter = new SelectedImageAdapter();
        //注入点击添加图片按钮监听对象
        mSelectedImageAdapter.setOnAddImageClickListener(() -> {
            onAddImage();
        });
        //注入长按图片单元格监听对象
        mSelectedImageAdapter.setOnLongPressGridListener(position -> {
            onDeleteSelectedImage(position);
        });
        //适配器与RecyclerView关联
        mBinding.rvSelectedImages.setAdapter(mSelectedImageAdapter);
    }

    private void onAddImage() {
        //发起用户添加图片操作
        Log.d("Compose", "添加新图片");
//        openPictureSelector(9 - mSelectedImageAdapter.getSelectedImages().size());
    }

    private void onDeleteSelectedImage(int position) {
        //发起删除第position个已添加图片的操作
        Log.d("Compose", "删除图片" + position);
    }



    private void openPictureSelector(int canSelectNum) {
        PictureSelectorUtil.show(this, canSelectNum, new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(ArrayList<LocalMedia> result) {
                Log.d("Compose", "Selected images: " + result.size());
                for (LocalMedia localMedia : result) {
                    Log.d("Compose", "Selected image: " + localMedia.getRealPath());
                    mSelectedImageAdapter.addImage(localMedia.getRealPath());
                }
            }
            @Override
            public void onCancel() { }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //按下回退按钮，执行与按手机回退键一致的操作：关闭本页面
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.item_finish) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //        mSelectedImageAdapter.setOnLongPressGridListener(position -> {
//            final int pos = position;
//            AlertDialog.Builder dialogBuilder =
//                    new AlertDialog.Builder(PostComposeActivity.this, R.style.Base_Theme_AppCompat_Light_Dialog)
//                    .setMessage("确认需要删除已选中的图片吗？")
//                    .setPositiveButton("删除", (dialog, which) -> {
//                        mSelectedImageAdapter.removeImage(pos);
//                        dialog.dismiss();
//                    })
//                    .setNegativeButton("取消", (dialog, which) -> {
//                        dialog.dismiss();
//                    });
//            dialogBuilder.create().show();
//        });
//    mBinding.rvSelectedImages.setAdapter(mSelectedImageAdapter);

}