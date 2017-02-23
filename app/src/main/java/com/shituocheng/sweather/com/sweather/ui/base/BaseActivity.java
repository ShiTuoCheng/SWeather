package com.shituocheng.sweather.com.sweather.ui.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

public abstract class BaseActivity extends AppCompatActivity {

    private View mContentView = null;

    //是否包含fragment
    private boolean isFragmentActivity = false;

    //是否为沉浸式状态栏
    private boolean isStatusBar = false;

    protected final String TAG = this.getClass().getName();

    private String APP_NAME;

    //APP是否为调试状态
    private boolean isDebug;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContentView = LayoutInflater.from(this).inflate(bindLayout(), null);

        if (isStatusBar){
            steepStatusBar();
        }
        setContentView(mContentView);

        initView();
    }

    //绑定layout
    public abstract int bindLayout();

    //初始化view
    public abstract void initView();

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //不携带数据跳转activity
    public void startActivity(Class<?> clz){

        startActivity(clz);
    }

    //重载携带数据跳转的activity
    public void startActivity(Class<?> clz, Bundle bundle){

        Intent intent = new Intent();
        intent.setClass(this, clz);

        if (bundle != null){
            intent.putExtras(bundle);
        }

        startActivity(intent);
    }

    //返回数据
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);

        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    //设置shortcuts
    public void setShortcuts(){
        //判断当前API为25
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1){

        }
    }

    //设置沉浸式状态栏
    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    //打印逻辑
    protected void $Log(String msg) {
        if (isDebug) {
            Log.d(APP_NAME, msg);
        }
    }
}
