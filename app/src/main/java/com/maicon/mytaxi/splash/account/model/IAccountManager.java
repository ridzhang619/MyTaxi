package com.maicon.mytaxi.splash.account.model;

import android.os.Handler;

/**
 * Created by Maicon on 2018/4/23 0023.
 */

public interface IAccountManager {

    void setHandler(Handler handler);

    /**
     * 下发验证码
     */
    void fetchSmsCode(String phone);

    /**
     * 校验验证码
     */
    void checkSmsCode(String phone,String smsCode);

    /**
     * 用户是否注册接口
     */
    void checkUserExist(String phone);

    /**
     * 注册
     */
    void register(String phone,String password);

    /**
     * 登录
     */
    void  login(String phone,String password);

    /**
     * token登录
     */
    void loginByToken();


}
