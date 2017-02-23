package com.shituocheng.sweather.com.sweather.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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

public class LocationFragment extends BaseFragment {


    private ImageView imageView;

    private Handler uiHandler;

    private RelativeLayout cardViewLayout;
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(bindLayout(), container, false);
        initView(v);
        Glide.with(getActivity().getApplicationContext()).load(R.drawable.sun).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);

        return v;
    }

    @Override
    public void initView(View view) {

        imageView = (ImageView)view.findViewById(R.id.location_imageView);
        cardViewLayout = (RelativeLayout)view.findViewById(R.id.cardViewLayout);

        cardViewLayout.setTranslationY(1000);

        fetchData();
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_location;
    }

    private void fetchData(){

        String ipStr = Utils.getIPAddress(getActivity().getApplicationContext());

        String url = API.baseUrl + "now?" + "city=" + ipStr + "&key=" + API.appKey;

        Log.d("URL", url);

        try {
            //主线程
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {

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

                        String city = basicJsonObj.getString("city");
                        String country = basicJsonObj.getString("cnty");



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    uiHandler = new Handler(Looper.getMainLooper());

                    //Handler切换至主线程
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {

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
    }

}
