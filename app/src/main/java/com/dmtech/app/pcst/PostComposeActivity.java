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
    private SelectedImageAdapter mSelectedImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityComposePostBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        setSupportActionBar(mBinding.toolbarCompose);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        RecyclerView.LayoutManager lm = new GridLayoutManager(this, 3);
        mBinding.recyclerView.setLayoutManager(lm);
        mSelectedImageAdapter = new SelectedImageAdapter(null);
        mSelectedImageAdapter.setOnAddImageClickListener(() -> {
            int canSelectNum = 9 - mSelectedImageAdapter.getSelectedImages().size();
            if (canSelectNum >= 1) {
                openPictureSelector(canSelectNum);
            } else {
                Toast.makeText(PostComposeActivity.this, "图片数量已经达到上限。", Toast.LENGTH_SHORT).show();
            }
        });
        mSelectedImageAdapter.setOnLongPressGridListener(position -> {
            final int pos = position;
            AlertDialog.Builder dialogBuilder =
                    new AlertDialog.Builder(PostComposeActivity.this, R.style.Base_Theme_AppCompat_Light_Dialog)
                    .setMessage("确认需要删除已选中的图片吗？")
                    .setPositiveButton("删除", (dialog, which) -> {
                        mSelectedImageAdapter.removeImage(pos);
                        dialog.dismiss();
                    })
                    .setNegativeButton("取消", (dialog, which) -> {
                        dialog.dismiss();
                    });
            dialogBuilder.create().show();
        });

        mBinding.recyclerView.setAdapter(mSelectedImageAdapter);
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
        if (item.getItemId() == R.id.item_finish) {
            setResult(1);
            finish();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}