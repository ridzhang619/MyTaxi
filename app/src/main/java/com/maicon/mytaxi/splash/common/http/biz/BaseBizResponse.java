package com.maicon.mytaxi.splash.common.http.biz;

/**
 * Created by Maicon on 2018/4/11 0011.
 * 返回业务数据的公共格式
 */

public class BaseBizResponse {

    public static final int STATE_OK = 200;
    //用户已经存在
    public static final int STATE_USER_EXIST = 100003;
    //密码错误
    public static final int STATE_PW_ERROR = 100005;
    //token过期
    public static final int STATE_TOKEN_INVALID = 100006;

    //状态码
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
