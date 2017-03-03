package com.shituocheng.sweather.com.sweather.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shituocheng.sweather.com.sweather.R;
import com.shituocheng.sweather.com.sweather.ui.base.BaseFragment;
import com.shituocheng.sweather.com.sweather.utilis.Utils;

public class WeatherTodayFragment extends BaseFragment {

    private TextView pmTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(bindLayout(), container, false);

        initView(view);

        if (Utils.isNetworkAvailable(getActivity().getApplicationContext())){

            fetchData();
        }else {

            Utils.networkError(getActivity());
        }

        return view;
    }

    @Override
    public void initView(View view) {


    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_weather_today;
    }

    private void fetchData(){


    }
}
