package com.maicon.mytaxi.splash.common.http.impl;


import com.maicon.mytaxi.splash.common.http.IHttpClient;
import com.maicon.mytaxi.splash.common.http.IRequest;
import com.maicon.mytaxi.splash.common.http.IResponse;

import java.io.IOException;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Maicon on 2018/3/26 0026.
 */

public class OkHttpClientImpl implements IHttpClient {

    OkHttpClient mOkHttpClient = new OkHttpClient.Builder().build();

    @Override
    public IResponse get(IRequest request, boolean forceCache) {
        //指定请求方式
        request.setMethod(IRequest.GET);

        Map<String, String> header = request.getHeader();

        Request.Builder builder = new Request.Builder();
        for(String key : header.keySet()){
            builder.header(key,header.get(key));
        }
        String url = request.getUrl();
        builder.url(url).get();
        Request okRequest = builder.build();

        return execute(okRequest);
    }



    @Override
    public IResponse post(IRequest request, boolean forceCache) {

        //指定请求方式
        request.setMethod(IRequest.POST);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType,request.getBody().toString());
        Map<String,String> header = request.getHeader();
        Request.Builder builder = new Request.Builder();
        for(String key : header.keySet()){
            header.put(key,header.get(key));
        }
        builder.url(request.getUrl()).post(requestBody);
        Request okRequest = builder.build();
        return execute(okRequest);
    }

    private IResponse execute(Request okRequest) {
        BaseResponse commonResponse = new BaseResponse();
        try {
            Response response = mOkHttpClient.newCall(okRequest).execute();
            //设置状态码
            commonResponse.setCode(response.code());
            //设置相应数据
            commonResponse.setData(response.body().string());

        } catch (IOException e) {
            e.printStackTrace();
            commonResponse.setCode(BaseResponse.STATE_UNKNOWN_ERROR);
            commonResponse.setData(e.getMessage());
        }


        return commonResponse;
    }

}
