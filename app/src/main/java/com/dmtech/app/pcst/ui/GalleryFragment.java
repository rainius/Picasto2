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
import com.dmtech.app.pcst.util.TypefaceUtil;

public class GalleryFragment extends Fragment {

    private GalleryViewModel mViewModel;

    public static GalleryFragment newInstance() {
        return new GalleryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gallery_fragment, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar_gallery);
        initToolbar(toolbar);
        return view;
    }

    //为主标题设定字体
    private void initToolbar(Toolbar toolbar) {
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