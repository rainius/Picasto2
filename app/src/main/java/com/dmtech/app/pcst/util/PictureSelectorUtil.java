package com.dmtech.app.pcst.util;

import android.content.Context;
import android.net.Uri;

import androidx.fragment.app.Fragment;

import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.engine.CompressEngine;
import com.luck.picture.lib.engine.CompressFileEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.luck.picture.lib.interfaces.OnMediaEditInterceptListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.utils.DateUtils;
import com.luck.pictureselector.GlideEngine;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;

import top.zibin.luban.Luban;
import top.zibin.luban.OnNewCompressListener;
import top.zibin.luban.OnRenameListener;


public class PictureSelectorUtil {
    /**
     * 显示图片选择界面
     * @param context
     * @param maxSelectNum 可以选择几张图片
     * @param listener 选择完成后的处理
     */
    public static void show(Context context,
                            int maxSelectNum,
                            OnResultCallbackListener<LocalMedia> listener) {
        PictureSelector.create(context) //创建对象
                .openGallery(SelectMimeType.ofImage())  //打开相册，筛选图片
                .setImageEngine(GlideEngine.createGlideEngine())   //使用Glide图片加载库
                .setEditMediaInterceptListener( //设定图片编辑器
                        new MeOnMediaEditInterceptListener(getSandboxPath(context),
                                buildOptions(context)))
                .setMaxSelectNum(maxSelectNum)  //设定可选图片数量
                .setCompressEngine(getCompressEngine()) // 图片压缩
                .forResult(listener);   // 启动图片选择页面，并在完成后调用回调
    }

    /**
     * 获取图像压缩引擎
     * @return
     */
    private static CompressFileEngine getCompressEngine() {
        return new ImageFileCompressEngine();
    }


    /**
     * 自定义编辑
     */
    private static class MeOnMediaEditInterceptListener implements OnMediaEditInterceptListener {
        private final String outputCropPath;
        private final UCrop.Options options;

        public MeOnMediaEditInterceptListener(String outputCropPath, UCrop.Options options) {
            this.outputCropPath = outputCropPath;
            this.options = options;
        }

        @Override
        public void onStartMediaEdit(Fragment fragment, LocalMedia currentLocalMedia, int requestCode) {
            String currentEditPath = currentLocalMedia.getAvailablePath();
            Uri inputUri = PictureMimeType.isContent(currentEditPath)
                    ? Uri.parse(currentEditPath) : Uri.fromFile(new File(currentEditPath));
            Uri destinationUri = Uri.fromFile(
                    new File(outputCropPath, DateUtils.getCreateFileName("CROP_") + ".jpeg"));
            UCrop uCrop = UCrop.of(inputUri, destinationUri);
            options.setHideBottomControls(false);
            uCrop.withOptions(options);
            uCrop.startEdit(fragment.getActivity(), fragment, requestCode);
        }
    }

    /**
     * 创建自定义输出目录
     *
     * @return
     */
    private static String getSandboxPath(Context context) {
        File externalFilesDir = context.getExternalFilesDir("");
        File customFile = new File(externalFilesDir.getAbsolutePath(), "Sandbox");
        if (!customFile.exists()) {
            customFile.mkdirs();
        }
        return customFile.getAbsolutePath() + File.separator;
    }

    /**
     * 配制UCrop，可根据需求自我扩展
     *
     * @return
     */
    private static UCrop.Options buildOptions(Context context) {
        UCrop.Options options = new UCrop.Options();
        options.setFreeStyleCropEnabled(true);
        options.setShowCropFrame(true);
        options.setShowCropGrid(true);
        options.setCropOutputPathDir(getSandboxPath(context));
        options.isCropDragSmoothToCenter(false);
        options.isForbidCropGifWebp(true);
        options.isForbidSkipMultipleCrop(false);
        options.setMaxScaleMultiplier(100);
        return options;
    }

    /**
     * 自定义压缩
     */
    private static class ImageFileCompressEngine implements CompressFileEngine {

        @Override
        public void onStartCompress(Context context, ArrayList<Uri> source, OnKeyValueResultCallbackListener call) {
            Luban.with(context).load(source).ignoreBy(100).setRenameListener(new OnRenameListener() {
                @Override
                public String rename(String filePath) {
                    int indexOf = filePath.lastIndexOf(".");
                    String postfix = indexOf != -1 ? filePath.substring(indexOf) : ".jpg";
                    return DateUtils.getCreateFileName("CMP_") + postfix;
                }
            }).setCompressListener(new OnNewCompressListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(String source, File compressFile) {
                    if (call != null) {
                        call.onCallback(source, compressFile.getAbsolutePath());
                    }
                }

                @Override
                public void onError(String source, Throwable e) {
                    if (call != null) {
                        call.onCallback(source, null);
                    }
                }
            }).launch();
        }
    }
}
