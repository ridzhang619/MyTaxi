package com.maicon.mytaxi.splash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.maicon.mytaxi.R;
import com.maicon.mytaxi.splash.account.view.PhoneInputDialog;
import com.maicon.mytaxi.splash.account.model.response.Account;
import com.maicon.mytaxi.splash.account.model.response.LoginResponse;
import com.maicon.mytaxi.splash.common.http.IHttpClient;
import com.maicon.mytaxi.splash.common.http.IRequest;
import com.maicon.mytaxi.splash.common.http.IResponse;
import com.maicon.mytaxi.splash.common.http.api.API;
import com.maicon.mytaxi.splash.common.http.biz.BaseBizResponse;
import com.maicon.mytaxi.splash.common.http.impl.BaseRequest;
import com.maicon.mytaxi.splash.common.http.impl.OkHttpClientImpl;
import com.maicon.mytaxi.splash.common.storage.SharedPreferencesDao;

/**
 * 1 检查本地记录
 * 2 若用户没有登录则登录
 * 3 登录之前先校验手机号码
 * 4 token有效使用 自动登录
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private IHttpClient mHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHttpClient = new OkHttpClientImpl();
        checkLoginState();

    }

    /**
     * 检查用户是否登录
     */
    private void checkLoginState() {

        // todo:获取本地登录信息



    }

    /**
     * 显示手机输入框
     */
    private void showPhoneInputDialog() {
        PhoneInputDialog dialog = new PhoneInputDialog(MainActivity.this);
        dialog.show();

    }
}
