package com.dmtech.app.pcst.util;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class TypefaceUtil {

    /**
     * 为文本视图tv设定字体
     * @param tv
     * @param typePath
     */
    public static void setTypeface(TextView tv, String typePath) {
        //从assets创建字体对象
        Typeface typeface = Typeface.createFromAsset(
                tv.getContext().getAssets(), typePath);
        //为文本视图设置字体
        tv.setTypeface(typeface);
    }
}
