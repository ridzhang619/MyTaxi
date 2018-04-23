package com.maicon.mytaxi.splash.account.model.response;

import com.maicon.mytaxi.splash.common.http.biz.BaseBizResponse;

/**
 * Created by Maicon on 2018/4/18 0018.
 */

public class LoginResponse extends BaseBizResponse{

    Account data;

    public Account getData() {
        return data;
    }

    public void setData(Account data) {
        this.data = data;
    }
}
