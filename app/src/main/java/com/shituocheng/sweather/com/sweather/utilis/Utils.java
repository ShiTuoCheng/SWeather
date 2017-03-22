package com.shituocheng.sweather.com.sweather.utilis;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.shituocheng.sweather.com.sweather.R;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

/**
 * Created by shituocheng on 2017/2/21.
 */

public class Utils {

    public static String getIPAddress(Context context) {

        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络

                WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();

                return intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
            networkError(context);
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    private static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    //网络出错弹窗

    public static AlertDialog networkError(Context context){

        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);

        return alertBuilder.setTitle("连接出错了哦")
                           .setMessage("网络连接异常")
                           .setPositiveButton("朕知道了", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.dismiss();
                                                    }
                                                })
                           .create();
    }

    public static String tempUnit(){

        return "℃";
    }

    public static int compareWeatherIndex(int code){

        if (code == 100 || code == 200 || code == 201 || code == 202 || code == 203 || code == 204 ) {

            if (getCurrentTime()){

                return R.drawable.moon_index;
            }else {

                return R.drawable.sun_index;
            }
        }else if (code == 103 || code == 101 || code == 102  || code == 205){

            return R.drawable.cloud;
        }else if (code == 104 || code == 206 || code == 207 || code == 208 || code == 209 || code == 210 || code == 211 || code == 212 || code == 213 || code == 300 || code == 301 || code == 302 || code == 303 || code == 304 || code == 305 || code == 306 || code == 307 || code == 308 || code == 309 || code == 310 || code == 311 || code == 312 || code == 313 || code == 400 || code == 401 || code == 402 || code == 403 || code == 404 || code == 405 || code == 406 || code == 407 || code == 500 || code == 501 || code == 502 || code == 503 || code == 504 || code == 507 || code == 508 || code == 900 || code == 901){

            return R.drawable.dark_cloud;
        }

        return 0;
    }

    private static boolean getCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String hour= sdf.format(new Date());
        int k  = Integer.parseInt(hour)  ;
        return (k >= 0 && k < 6) || (k >= 18 && k < 24);
    }

    //加载天气小图标
    public static int compareWeatherBackground(int code){

        if (code == 100){

            return R.drawable.sun;
        }else if (code == 101){

            return R.drawable.cloudy;
        }else if (code == 102){

            return R.drawable.sun;
        }else if (code == 103){

            return R.drawable.cloudy;
        }else if (code == 104){

            return R.drawable.cloudy;
        }else if (code == 200){

            return R.drawable.cloudy;
        }else if (code == 201){

            return R.drawable.cloudy;
        }else if (code == 202){

            return R.drawable.cloudy;
        }else if (code == 203){

            return R.drawable.cloudy;
        }else if (code == 204){

            return R.drawable.cloudy;
        }else if (code == 205){

            return R.drawable.wind;
        }else if (code == 206){

            return R.drawable.wind;
        }else if (code == 207){

            return R.drawable.wind;
        }else if (code == 208){

            return R.drawable.wind;
        }else if (code == 209){

            return R.drawable.wind;
        }else if (code == 210){

            return R.drawable.wind;
        }else if (code == 211){

            return R.drawable.wind;
        }else if (code == 212){

            return R.drawable.wind;
        }else if (code == 213){

            return R.drawable.wind;
        }else if (code == 300){

            return R.drawable.cloudyrain;
        }else if (code == 301){

            return R.drawable.bigrain;
        }else if (code == 302){

            return R.drawable.thunderrain;
        }else if (code == 303){

            return R.drawable.thunderrain;
        }else if (code == 304){

            return R.drawable.rainandsnow;
        }else if (code == 305){

            return R.drawable.smallrain;
        }else if (code == 306){

            return R.drawable.middlerain;
        }else if (code == 307){

            return R.drawable.bigrain;
        }else if (code == 308){

            return R.drawable.giantrain;
        }else if (code == 309){

            return R.drawable.bigrain;
        }else if (code == 310){

            return R.drawable.bigrain;
        }else if (code == 311){

            return R.drawable.bigrain;
        }else if (code == 312){

            return R.drawable.giantrain;
        }else if (code == 313){

            return R.drawable.rainandsnow;
        }

        return 0;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    
}
