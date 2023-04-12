package com.dmtech.app.pcst.ui;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmtech.app.pcst.R;
import com.dmtech.app.pcst.databinding.FragmentGalleryBinding;
import com.dmtech.app.pcst.util.TypefaceUtil;

public class GalleryFragment extends Fragment {

    private GalleryViewModel mViewModel;
    //视图绑定
    private FragmentGalleryBinding mBinding;

    public static GalleryFragment newInstance() {
        return new GalleryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_gallery, container, false);
        //生成视图绑定对象
        mBinding = FragmentGalleryBinding.inflate(inflater);
        //抽取页面根视图
        View view = mBinding.getRoot();
        initToolbar(mBinding.toolbarGallery);
        return view;
    }

    //为主标题设定字体
    private void initToolbar(Toolbar toolbar) {
        //工具栏内部第0号子视图就是标题文本
        TextView titleView = (TextView) toolbar.getChildAt(0);
        if (titleView != null) {
            TypefaceUtil.setTypeface(titleView, "Lucida Calligraphy.ttf");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
        // TODO: Use the ViewModel
    }

}