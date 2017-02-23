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
import com.shituocheng.sweather.com.sweather.ui.base.BaseActivity;
import com.shituocheng.sweather.com.sweather.ui.fragment.AboutFragment;
import com.shituocheng.sweather.com.sweather.ui.fragment.LocationFragment;
import com.shituocheng.sweather.com.sweather.utilis.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ViewPager viewPager;
    private MenuItem prevMenuItem;
    private List<String> mainTitleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bindLayout());
        initView();

        String ipStr = Utils.getIPAddress(this.getApplicationContext());

        Log.d("IP", ipStr);
    }

    //绑定布局
    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    //初始化view
    @Override
    public void initView() {

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.title_my_location);

        mainTitleList.add(getResources().getString(R.string.title_my_location));
        mainTitleList.add(getResources().getString(R.string.title_places));
        mainTitleList.add(getResources().getString(R.string.title_about));


        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        viewPager = (ViewPager)findViewById(R.id.main_viewPager);

        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:

                        viewPager.setCurrentItem(0);

                        getSupportActionBar().setTitle(R.string.title_my_location);
                        return true;
                    case R.id.navigation_dashboard:
                        viewPager.setCurrentItem(1);

                        getSupportActionBar().setTitle(R.string.title_places);
                        return true;
                    case R.id.navigation_notifications:
                        viewPager.setCurrentItem(2);

                        getSupportActionBar().setTitle(R.string.title_about);

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

                if (position == 0){
                    // TODO: 2017/2/23
                }

                getSupportActionBar().setTitle(mainTitleList.get(position));
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
        adapter.addFragment(new AboutFragment());
        adapter.addFragment(new AboutFragment());
        viewPager.setAdapter(adapter);
    }
}
