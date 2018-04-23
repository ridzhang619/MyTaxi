package com.maicon.mytaxi.splash.common.storage;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

/**
 * SharedPreference访问对象
 * Created by Maicon on 2018/4/18 0018.
 */

public class SharedPreferencesDao {

    private static final String TAG = "SharedPreferencesDao";
    public static final String FILE_ACCOUNT = "FILE_ACCOUNT";
    public static final java.lang.String KEY_ACCOUNT = "KEY_ACCOUNT";
    private SharedPreferences mSharedPreferences;

    /**
     * 初始化
     */
    public SharedPreferencesDao(Application application,String fileName){
        mSharedPreferences = application.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }


    /**
     * 保存k-v
     */
    public void save(String key,String value){
        mSharedPreferences.edit().putString(key,value).commit();
    }

    /**
     * 读取k-v
     */
    public String get(String key){
        return mSharedPreferences.getString(key,null);
    }

    /**
     * 保存对象
     */

    public void save(String key,Object object){
        String value = new Gson().toJson(object);
        save(key,value);
    }

    /**
     * 读取对象
     */
    public Object get(String key,Class cls){

        String value = get(key);

        try{
            if(value != null){
                Object object = new Gson().fromJson(value,cls);
                return object;
            }
        }catch (Exception e){
            Log.d(TAG,e.getMessage());
        }

        return null;

    }

}
