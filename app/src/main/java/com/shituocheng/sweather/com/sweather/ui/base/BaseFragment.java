package com.shituocheng.sweather.com.sweather.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shituocheng on 2016/11/20.
 */

public abstract class BaseFragment extends Fragment {

    public boolean isSatusBar = true;

    private final String TAG = this.getClass().getName();

    private View mContentView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mContentView = inflater.inflate(bindLayout(), container, false);
        initView(mContentView);

        return mContentView;
    }

    public abstract void initView(View view);

    public abstract int bindLayout();

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
