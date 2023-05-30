package com.dmtech.app.pcst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dmtech.app.pcst.adapter.SelectedImageAdapter;
import com.dmtech.app.pcst.databinding.ActivityPostComposeBinding;
import com.dmtech.app.pcst.util.HttpHelper;
import com.dmtech.app.pcst.util.PictureSelectorUtil;
import com.dmtech.app.pcst.util.SessionUtil;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this, R.style.Base_Theme_AppCompat_Light_Dialog);
        builder.setMessage("确定要去掉这张图片吗？")
                .setPositiveButton("删除", (dialog, which) -> {
                    //用户确认，在此执行删除
                    mSelectedImageAdapter.removeImage(position);
                    dialog.dismiss();
                })
                .setNegativeButton("取消", (dialog, which) -> {
                    //用户放弃删除，取消对话框
                    dialog.dismiss();
                });
        builder.create().show();
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
                    //默认使用原图路径
                    String path = localMedia.getRealPath();
                    //如果有压缩版，使用之
                    String compressPath = localMedia.getCompressPath();
                    File compressFile = new File(compressPath);
                    Log.d("Compose", "压缩文件: " + compressPath + ", size: " + compressFile.length());
                    if (!TextUtils.isEmpty(compressPath)) {
                        path = compressPath;
                    }
                    mSelectedImageAdapter.addImage(path);
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
            mBinding.uploadingContainer.setVisibility(View.VISIBLE);
            onPublishPost();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onPublishPost() {
        //准备数据
        String username = SessionUtil.getUsername(this);
        String textContent = mBinding.editContent.getText().toString();
        String location = "";
        long timestamp = System.currentTimeMillis();
        List<String> imagePaths = mSelectedImageAdapter.getSelectedImages();
        OkHttpClient client = new OkHttpClient();
        //定义多部分的消息体
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM)
                .addFormDataPart("username", username)
                .addFormDataPart("textContent", textContent)
                .addFormDataPart("location", location)
                .addFormDataPart("timestamp", String.valueOf(timestamp));

        //插入图片数据
        for (int i = 0; i < imagePaths.size(); i++) {
            String partName = "pic_" + i;
            File imageFile = new File(imagePaths.get(i));
            builder.addFormDataPart(
                    partName,
                    imageFile.getName(),
                    RequestBody.create(MediaType.get("image/jpeg"), imageFile));
        }
        //生成请求对象
        RequestBody requestBody = builder.build();
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(HttpHelper.getAddPostServiceUrl())
                .post(requestBody);
        Request request = requestBuilder.build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.uploadingContainer.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.uploadingContainer.setVisibility(View.GONE);
                        finish();
                    }
                });
            }
        });
    }
}