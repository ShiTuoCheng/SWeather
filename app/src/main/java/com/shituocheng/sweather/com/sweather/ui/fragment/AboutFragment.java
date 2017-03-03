package com.shituocheng.sweather.com.sweather.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.shituocheng.sweather.com.sweather.R;

public class AboutFragment extends Fragment {

    private ImageView authorImageView;
    private ImageView designerImageView;
    private ImageView githubImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(bindLayout(), container, false);

        initView(v);

        Glide.with(getActivity().getApplicationContext()).load(R.drawable.author_avatar).diskCacheStrategy(DiskCacheStrategy.NONE).into(authorImageView);
        Glide.with(getActivity().getApplicationContext()).load(R.drawable.designer_icon).diskCacheStrategy(DiskCacheStrategy.NONE).into(designerImageView);
        Glide.with(getActivity().getApplicationContext()).load(R.drawable.github).diskCacheStrategy(DiskCacheStrategy.NONE).into(githubImageView);

        return v;
    }

    public void initView(View view) {

        authorImageView = (ImageView)view.findViewById(R.id.authorImageView);
        designerImageView = (ImageView)view.findViewById(R.id.designerImageView);
        githubImageView = (ImageView)view.findViewById(R.id.githubImageView);

    }

    public int bindLayout() {
        return R.layout.fragment_about;
    }

}
