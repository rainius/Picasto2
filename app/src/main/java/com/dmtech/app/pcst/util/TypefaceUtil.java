package com.dmtech.app.pcst.util;

import android.graphics.Typeface;
import android.widget.TextView;

/**
 * 字体工具类
 */
public class TypefaceUtil {
    public static void setTypeface(TextView tv, String typePath) {
        //创建字体对象
        Typeface typeface = Typeface.createFromAsset(
                tv.getContext().getAssets(), typePath);
        //为文本视图设置字体
        tv.setTypeface(typeface);
    }
}
