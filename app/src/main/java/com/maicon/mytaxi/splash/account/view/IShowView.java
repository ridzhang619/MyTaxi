package com.maicon.mytaxi.splash.account.view;

/**
 * Created by Maicon on 2018/4/23 0023.
 */

public interface IShowView {

    /**
     * 显示Loading
     */
    void showLoading();

    /**
     * 显示错误
     */
    void showError(int code,String msg);
}
