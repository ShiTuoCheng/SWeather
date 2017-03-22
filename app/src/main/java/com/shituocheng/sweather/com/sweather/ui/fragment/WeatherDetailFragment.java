package com.shituocheng.sweather.com.sweather.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shituocheng.sweather.com.sweather.R;
import com.shituocheng.sweather.com.sweather.bean.ForecastWeather;
import com.shituocheng.sweather.com.sweather.bean.Weather;
import com.shituocheng.sweather.com.sweather.ui.base.BaseFragment;
import com.shituocheng.sweather.com.sweather.utilis.API;
import com.shituocheng.sweather.com.sweather.utilis.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;


public class WeatherDetailFragment extends BaseFragment {


    private int page;
    private static final String INDEX_ID = "0x01";

    private TextView minTmpTextView;
    private TextView maxTmpTextView;
    private TextView dateTmpTextView;
    private ForecastWeather weather;

    private Handler uiHandler;

    public static WeatherDetailFragment newInstance(int index){
        Bundle argus = new Bundle();
        argus.putInt(INDEX_ID, index);
        WeatherDetailFragment weatherDetailFragment = new WeatherDetailFragment();
        weatherDetailFragment.setArguments(argus);
        return weatherDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt(INDEX_ID);

        uiHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(bindLayout(), container, false);

        initView(v);

        fetchData();

        return v;
    }

    @Override
    public void initView(View view) {

        minTmpTextView = (TextView)view.findViewById(R.id.minTemp_textView);
        maxTmpTextView = (TextView)view.findViewById(R.id.maxTemp_textView);
        dateTmpTextView = (TextView)view.findViewById(R.id.date_textView);
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_weather_detail;
    }

    private void fetchData(){

        String ipStr = Utils.getIPAddress(getActivity().getApplicationContext());

        String url = API.baseUrl + "forecast?" + "city=" + ipStr + "&key=" + API.appKey;

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
                    ForecastWeather.Builder builder = new ForecastWeather.Builder();

                    // 解析json

                    try {

                        JSONObject jsonObj = new JSONObject(json);

                        JSONArray heWeather = jsonObj.getJSONArray("HeWeather5");
                        JSONObject weatherObj = heWeather.getJSONObject(0);
                        JSONArray forecastJsonArray = weatherObj.getJSONArray("daily_forecast");
                        JSONObject forecastJsonObj = forecastJsonArray.getJSONObject(page);

                        JSONObject forecastTempObj = forecastJsonObj.getJSONObject("tmp");

                        String tmpMin = forecastTempObj.getString("min");
                        String tmpMax = forecastTempObj.getString("max");
                        String date = forecastJsonObj.getString("date");

                        weather = builder.minTemp(tmpMin).maxTemp(tmpMax).date(date).build();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //Handler切换至主线程
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            minTmpTextView.setText("最低温度: "+weather.getMinTemp());
                            maxTmpTextView.setText("最高温度: "+weather.getMaxTemp());
                            dateTmpTextView.setText(weather.getDate());

                        }
                    });


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
