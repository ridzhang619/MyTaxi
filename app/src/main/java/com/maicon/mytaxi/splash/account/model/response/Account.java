package com.maicon.mytaxi.splash.account.model.response;

/**
 * Created by Maicon on 2018/4/18 0018.
 */

public class Account {

    private String token;
    private String uid;
    private String account;
    private String expired;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }
}
