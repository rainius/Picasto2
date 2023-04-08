package com.dmtech.app.pcst;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.dmtech.app.pcst.util.TypefaceUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取导航宿主Fragment
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.fragments_container);
        //获取导航控制器对象
        NavController navController = navHostFragment.getNavController();
        //获取底部导航视图对象
        BottomNavigationView navView = findViewById(R.id.nav_view);
        //实现导航
        NavigationUI.setupWithNavController(navView, navController);
    }
}