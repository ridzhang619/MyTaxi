package com.maicon.mytaxi.splash.account.presenter;

/**
 * Created by Maicon on 2018/4/23 0023.
 */

public interface ICreatePasswordDialogPresenter {

    /**
     * 校验密码输入是否合法
     */
    void checkPw(String pw,String pw1);

    /**
     * 提交注册
     */
    void requestRegister(String phone,String password);

    /**
     * 登录
     */
    void requestLogin(String phone,String pw);



}
