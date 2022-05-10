package com.dmtech.app.pcst;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmtech.app.pcst.databinding.ActivityMainBinding;
import com.dmtech.app.pcst.picsel.GlideEngine;
import com.dmtech.app.pcst.util.SessionUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //视图绑定对象
    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //使用视图绑定以操作布局中各视图元素
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(R.layout.activity_main);
        setContentView(mBinding.getRoot());
        // 点击发帖按钮
        mBinding.ivAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureSelector();
            }
        });

        //获取导航宿主Fragment
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.fragments_container);
        //获取导航控制器对象
        NavController navController = navHostFragment.getNavController();
        //获取底部导航视图
        BottomNavigationView navView = findViewById(R.id.nav_view);
        //实现导航
        NavigationUI.setupWithNavController(navView, navController);
        Log.d("Picasto", "user: " + SessionUtil.getUsername(this));

    }

    private void showPictureSelector() {
        PictureSelector.create(this)
                .openGallery(SelectMimeType.ofImage())
                .setImageEngine(GlideEngine.createGlideEngine())
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(ArrayList<LocalMedia> result) {

                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }
}