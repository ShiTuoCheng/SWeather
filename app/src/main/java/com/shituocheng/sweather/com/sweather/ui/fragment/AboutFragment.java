package com.shituocheng.sweather.com.sweather.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.shituocheng.sweather.com.sweather.R;

public class AboutFragment extends Fragment {

    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(bindLayout(), container, false);

        initView(v);

        return v;
    }

    public void initView(View view) {

    }

    public int bindLayout() {
        return R.layout.fragment_about;
    }

}
