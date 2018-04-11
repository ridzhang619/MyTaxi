package com.maicon.mytaxi.splash.common.http.impl;

import com.maicon.mytaxi.splash.common.http.IResponse;

/**
 * Created by Maicon on 2018/3/26 0026.
 * 封装执行的结果
 */

public class BaseResponse implements IResponse{

    public static final int STATE_UNKNOWN_ERROR = 100001;
    public static final int STATE_OK = 200;
    //相应数据
    private String data;
    //状态码
    private int code;

    public void setData(String data) {
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getData() {
        return data;
    }

    @Override
    public int getCode() {
        return code;
    }
}
