package com.shituocheng.sweather.com.sweather.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shituocheng.sweather.com.sweather.R;
import com.shituocheng.sweather.com.sweather.ui.base.BaseFragment;

public class WeatherTodayFragment extends BaseFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(bindLayout(), container, false);

        initView(view);

        return view;
    }

    @Override
    public void initView(View view) {


    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_weather_today;
    }

}
