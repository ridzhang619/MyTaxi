package com.maicon.mytaxi.splash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.maicon.mytaxi.R;
import com.maicon.mytaxi.splash.account.PhoneInputDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkLoginState();

    }

    /**
     * 检查用户是否登录
     */
    private void checkLoginState() {

        // todo:获取本地登录信息

        // 登录是否过期
        boolean tokenValid = false;

        //todo:检查token是否过期

       if(!tokenValid){
            showPhoneInputDialog();//token过期
       }else{
           // todo:请求网络,完成自动登录
       }

    }

    /**
     * 显示手机输入框
     */
    private void showPhoneInputDialog() {
        PhoneInputDialog dialog = new PhoneInputDialog(MainActivity.this);
        dialog.show();

    }
}
