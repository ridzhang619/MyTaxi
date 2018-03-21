package com.dalimao.mytaxi.splash.common.http.impl;

import com.dalimao.mytaxi.splash.common.http.IRequest;

import java.util.Map;

/**
 * Created by Maicon on 2018/3/21 0021.
 */

public class BaseRequest implements IRequest{

    @Override
    public void setMethod(String method) {

    }

    @Override
    public void setHeader(String key, String value) {

    }

    @Override
    public void setBody(String key, String value) {

    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public Map<String, String> getHeader() {
        return null;
    }

    @Override
    public Object getBody() {
        return null;
    }

}
