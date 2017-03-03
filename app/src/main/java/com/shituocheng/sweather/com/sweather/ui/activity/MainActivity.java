package com.shituocheng.sweather.com.sweather.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.shituocheng.sweather.com.sweather.R;
import com.shituocheng.sweather.com.sweather.adapter.MainViewPagerAdapter;
import com.shituocheng.sweather.com.sweather.bean.Weather;
import com.shituocheng.sweather.com.sweather.ui.base.BaseActivity;
import com.shituocheng.sweather.com.sweather.ui.fragment.AboutFragment;
import com.shituocheng.sweather.com.sweather.ui.fragment.LocationFragment;
import com.shituocheng.sweather.com.sweather.ui.fragment.WeatherDetailFragment;
import com.shituocheng.sweather.com.sweather.ui.fragment.WeatherTodayFragment;
import com.shituocheng.sweather.com.sweather.utilis.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ViewPager viewPager;
    private MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bindLayout());
        initView();
    }

    //绑定布局
    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    //初始化view
    @Override
    public void initView() {

        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        viewPager = (ViewPager)findViewById(R.id.main_viewPager);

        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:

                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.navigation_dashboard:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.navigation_notifications:
                        viewPager.setCurrentItem(2);

                        return true;
                }
                return false;
            }

        };
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                navigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = navigation.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new LocationFragment());
        adapter.addFragment(new WeatherTodayFragment());
        adapter.addFragment(new AboutFragment());
        viewPager.setAdapter(adapter);
    }
}
