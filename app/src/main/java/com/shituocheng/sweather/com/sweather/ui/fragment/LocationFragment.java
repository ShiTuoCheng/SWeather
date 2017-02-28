package com.shituocheng.sweather.com.sweather.ui.fragment;


import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.shituocheng.sweather.com.sweather.R;
import com.shituocheng.sweather.com.sweather.adapter.MainViewPagerAdapter;
import com.shituocheng.sweather.com.sweather.bean.Weather;
import com.shituocheng.sweather.com.sweather.ui.base.BaseFragment;
import com.shituocheng.sweather.com.sweather.utilis.API;
import com.shituocheng.sweather.com.sweather.utilis.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class LocationFragment extends BaseFragment {

    private Handler uiHandler;
    private Weather weather;
    private TabLayout tabLayout;
    private ImageView imageView;
    private ViewPager innerViewPager;

    private List<Weather> weathers = new ArrayList<>();

    //ui
    private TextView tempTextView;
    private TextView cityTextView;
    private ImageView weatherStateImageView;

    private RelativeLayout cardViewLayout;
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(bindLayout(), container, false);

        uiHandler = new Handler(Looper.getMainLooper());
        initView(v);

        Glide.with(getActivity().getApplicationContext()).load(R.drawable.sun_index).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);

        if (!Utils.isNetworkAvailable(getActivity().getApplicationContext())){

            AlertDialog alertDialog = Utils.networkError(getActivity());

            alertDialog.show();
        }else {

            fetchData();

            dataTabBar();
        }
        return v;
    }

    @Override
    public void initView(View view) {

        cardViewLayout = (RelativeLayout)view.findViewById(R.id.cardViewLayout);
        tabLayout = (TabLayout)view.findViewById(R.id.tabBarLayout);
        imageView = (ImageView)view.findViewById(R.id.location_imageView);
        innerViewPager = (ViewPager)view.findViewById(R.id.innerViewPager);
        tempTextView = (TextView)view.findViewById(R.id.tempTextView);
        cityTextView = (TextView)view.findViewById(R.id.cityTextView);
        weatherStateImageView = (ImageView)view.findViewById(R.id.weatherStateImageView);

        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getChildFragmentManager());

        mainViewPagerAdapter.addFragment(new WeatherTodayFragment());
        mainViewPagerAdapter.addFragment(new AboutFragment());
        mainViewPagerAdapter.addFragment(new AboutFragment());

        innerViewPager.setAdapter(mainViewPagerAdapter);

        tabLayout.setupWithViewPager(innerViewPager);

        innerViewPager.setOffscreenPageLimit(tabLayout.getTabCount() * 10);

        innerViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                innerViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        innerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state){
                    case ViewPager.SCROLL_STATE_IDLE:
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                    case ViewPager.SCROLL_STATE_SETTLING:
                        break;
                }
            }
        });
        cardViewLayout.setTranslationY(1000);
        imageView.setTranslationY(-1000);
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_location;
    }

    //抓取数据设定tabbar样式
    private void dataTabBar() {

        String ipStr = Utils.getIPAddress(getActivity().getApplicationContext());

        String url = API.baseUrl + "forecast?" + "city=" + ipStr + "&key=" + API.appKey;

        try {

            //主线程
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {

                    Looper.prepare();

                    Utils.networkError(getActivity().getApplicationContext());

                }

                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

                    // 注：该回调是子线程，非主线程
                    String json = response.body().string();

                    try {

                        //Builder模式创造weather 构造器;
                        Weather.Builder builder = new Weather.Builder();

                        JSONObject jsonObject = new JSONObject(json);
                        JSONArray heWeather = jsonObject.getJSONArray("HeWeather5");
                        JSONObject weatherObj = heWeather.getJSONObject(0);
                        JSONArray forecastJsonArray = weatherObj.getJSONArray("daily_forecast");


                        for (int i = 0; i < 3; i++){

                            JSONObject jsonObj = forecastJsonArray.getJSONObject(i);
                            JSONObject condJsonObj = jsonObj.getJSONObject("cond");
                            JSONObject tempJsonObj = jsonObj.getJSONObject("tmp");
                            String code = condJsonObj.getString("code_d");
                            String tempMax = tempJsonObj.getString("max");
                            String minMax = tempJsonObj.getString("min");

                            Weather weather = builder.weatherCode(code).maxTemp(tempMax).minTemp(minMax).build();

                            weathers.add(weather);
                        }

                        //切换到主线程

                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {

                                for (int i = 0; i < 3; i++){

                                    String tempRange = weathers.get(i).getMinTemp() + Utils.tempUnit() + "~" + weathers.get(i).getMaxTemp() + Utils.tempUnit();

                                    tabLayout.getTabAt(i)
                                            .setText(tempRange);
//                                            .setIcon(Utils.compareWeatherBackground(Integer.valueOf(weathers.get(i).getWeatherCode())));
                                }
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //抓去天气数据
    private void fetchData(){

        String ipStr = Utils.getIPAddress(getActivity().getApplicationContext());

        String url = API.baseUrl + "now?" + "city=" + ipStr + "&key=" + API.appKey;

        try {
            //主线程
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {

                    Looper.prepare();

                    Utils.networkError(getActivity().getApplicationContext());

                }

                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException  {
                    // 注：该回调是子线程，非主线程

                    String json = response.body().string();

                    // 工厂模式builder创建
                    Weather.Builder builder = new Weather.Builder();

                    // 解析json

                    try {

                        JSONObject jsonObj = new JSONObject(json);

                        JSONArray heWeather = jsonObj.getJSONArray("HeWeather5");
                        JSONObject weatherObj = heWeather.getJSONObject(0);
                        JSONObject basicJsonObj = weatherObj.getJSONObject("basic");
                        JSONObject nowJsonObj = weatherObj.getJSONObject("now");
                        JSONObject condJsonObj = nowJsonObj.getJSONObject("cond");
                        JSONObject updateJsonObj = basicJsonObj.getJSONObject("update");

                        String city = basicJsonObj.getString("city");
                        String country = basicJsonObj.getString("cnty");
                        String temp = nowJsonObj.getString("tmp");
                        String weatherState = condJsonObj.getString("txt");
                        String weatherCode = condJsonObj.getString("code");
                        String updateTime = updateJsonObj.getString("loc");

                        weather = builder.city(city).country(country).temperature(temp).weatherState(weatherState).weatherUpdate(updateTime).weatherCode(weatherCode).build();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //Handler切换至主线程
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {


                            weatherStateImageView.setImageResource(Utils.compareWeatherBackground(Integer.valueOf(weather.getWeatherCode())));
                            tempTextView.setText(weather.getTemperature());
                            cityTextView.setText(weather.getCity());
                            cardViewAnimation();
                        }
                    });


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void cardViewAnimation(){

        cardViewLayout.setTranslationY(1000);

        cardViewLayout.animate().translationY(0).setDuration(300).setStartDelay(300).setInterpolator(INTERPOLATOR);

        imageView.animate().translationY(0).setDuration(300).setStartDelay(300).setInterpolator(INTERPOLATOR);
    }

}
