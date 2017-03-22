package com.shituocheng.sweather.com.sweather.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.shituocheng.sweather.com.sweather.R;
import com.shituocheng.sweather.com.sweather.bean.Weather;
import com.shituocheng.sweather.com.sweather.ui.base.BaseFragment;
import com.shituocheng.sweather.com.sweather.utilis.API;
import com.shituocheng.sweather.com.sweather.utilis.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class WeatherTodayFragment extends BaseFragment {

    private TextView stateTextView;
    private TextView feelingTextView;
    private TextView windTextView;


    private Weather weather;

    private Handler uiHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(bindLayout(), container, false);

        uiHandler = new Handler(Looper.getMainLooper());

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

        stateTextView = (TextView)view.findViewById(R.id.weatherStateTextView);
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_weather_today;
    }

    private void fetchData(){

        String ipStr = Utils.getIPAddress(getActivity().getApplicationContext());

        String url = API.baseUrl + "now?" + "city=" + ipStr + "&key=" + API.appKey;

        Log.d("api", url);

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

                            stateTextView.setText(weather.getWeatherState());
                        }
                    });


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
