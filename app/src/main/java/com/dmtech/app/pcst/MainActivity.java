package com.dmtech.app.pcst;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取标题文本视图
        TextView hw = findViewById(R.id.tv_hw);
        //从assets创建字体对象
        Typeface typeface = Typeface.createFromAsset(
                getAssets(), "Lucida Calligraphy.ttf");
        //为文本视图设置字体
        hw.setTypeface(typeface);

    }
}