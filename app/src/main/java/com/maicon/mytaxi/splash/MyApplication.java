package com.maicon.mytaxi.splash;

import android.app.Application;

/**
 * Created by Maicon on 2018/4/18 0018.
 */

public class MyApplication extends Application{

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance  = this;
    }

    public static MyApplication getInstance(){
        return mInstance;
    }

}
