package com.maicon.mytaxi.splash.common.http.biz;

/**
 * Created by Maicon on 2018/4/11 0011.
 * 返回业务数据的公共格式
 */

public class BaseBizResponse {

    public static final int STATE_OK = 200;

    private int code;
    private String msg;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
