package com.xxx.ware_house.utils;

import android.util.Log;

/**
 * @Author: ZhangRuixiang
 * Date: 2020/12/22
 * DES:
 */
public class LogUtil {

    private static final String TAG = "WMS_PDA";

    public static void e(String msg){
        Log.e(TAG,msg);
    }

    public static void d(String msg){
        Log.d(TAG,msg);
    }

    public static void i(String msg){
        Log.i(TAG,msg);
    }

    public static void v(String msg){
        Log.v(TAG,msg);
    }

    public static void w(String msg){
        Log.w(TAG,msg);
    }

    public static void wtf(String msg){
        Log.wtf(TAG,msg);
    }

}
