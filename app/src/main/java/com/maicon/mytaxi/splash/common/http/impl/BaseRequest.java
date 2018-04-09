package com.maicon.mytaxi.splash.common.http.impl;

import com.maicon.mytaxi.splash.common.http.IRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maicon on 2018/3/21 0021.
 */

public class BaseRequest implements IRequest{

    private String method = POST;
    private String url;
    private Map<String,String> header;
    private Map<String,Object> body;


    public BaseRequest(String url) {
        this.url = url;
        this.header = new HashMap<>();
        this.body = new HashMap<>();
        header.put("Application-Id","myTaxiID");
        header.put("API-Key","myTaxiKey");
    }

    @Override
    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public void setHeader(String key, String value) {
        header.put(key,value);
    }

    @Override
    public void setBody(String key, String value) {
        body.put(key,value);
    }

    @Override
    public String getUrl() {
        if(GET.equals(method)){
            //组装 get 请求参数
            for(String key : body.keySet()){
                url = url.replace("${"+key+"}",body.get(key).toString());
            }
        }
        return url;
    }

    @Override
    public Map<String, String> getHeader() {
        return header;
    }

    @Override
    public Object getBody() {
        if(body != null){
            return new Gson().toJson(this.body,HashMap.class);
        }else{
            return "{}";
        }
    }

}
