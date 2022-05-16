package com.dmtech.app.pcst;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
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

    //访问创作页的请求码
    private static final int REQUEST_CODE_COMPOSE = 1;
    private static final int RESULT_CODE_SENT = 1;
    private static final int RESULT_CODE_CANCEL = 0;

    private ActivityResultLauncher<Intent> mComposeLauncher;

    //视图绑定对象
    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //使用视图绑定以操作布局中各视图元素
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        // 点击发帖按钮
        mBinding.ivAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Compose", "点此去发帖");
                //启动主题创作页
                Intent intent = new Intent(MainActivity.this, PostComposeActivity.class);
                startActivity(intent);
            }
        });

        //定义启动器以启动一个需要返回结果的Activity
//        mComposeLauncher = setupComposeLauncher();
//        Intent intent = new Intent(MainActivity.this, PostComposeActivity.class);
//        mComposeLauncher.launch(intent);
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

    private ActivityResultLauncher<Intent> setupComposeLauncher() {
        return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> { //处理返回结果
                    Log.d("Compose", "Result code: " + result.getResultCode());
                    //TODO: 如果返回结果为1，则重新获取数据，为0则忽略
                });
    }
}