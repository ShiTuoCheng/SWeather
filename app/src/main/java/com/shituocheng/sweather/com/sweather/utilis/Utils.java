package com.shituocheng.sweather.com.sweather.utilis;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

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

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络\

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

        if (code == 100 || code == 101 || code == 102 || code == 103){

            if (getCurrentTime()){

                return R.drawable.moon_index;
            }else {

                return R.drawable.sun_index;
            }
        }

        return 0;

    };

    private static boolean getCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String hour= sdf.format(new Date());
        int k  = Integer.parseInt(hour)  ;
        if ((k>=0 && k<6) ||(k >=18 && k<24)){
            return true;
        } else {
            return false;
        }
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
        }else if (code == 101){

            return R.drawable.cloudy;
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
