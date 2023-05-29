package com.dmtech.app.pcst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.dmtech.app.pcst.databinding.ActivityPostComposeBinding;

public class PostComposeActivity extends AppCompatActivity {

    private ActivityPostComposeBinding mBinding;

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