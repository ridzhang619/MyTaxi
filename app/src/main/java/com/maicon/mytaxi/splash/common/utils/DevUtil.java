package com.maicon.mytaxi.splash.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Telephony;
import android.telephony.TelephonyManager;

/**
 * 设备相关工具类
 * Created by Maicon on 2018/4/16 0016.
 */

public class DevUtil {

    public static String UUID(Context context){
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String deviceId = tm.getDeviceId();
        return deviceId+System.currentTimeMillis();
    }

}
