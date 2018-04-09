package com.maicon.mytaxi.splash.common.http.impl;

import com.maicon.mytaxi.splash.common.http.IHttpClient;
import com.maicon.mytaxi.splash.common.http.IRequest;
import com.maicon.mytaxi.splash.common.http.IResponse;
import com.maicon.mytaxi.splash.common.http.api.API;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Maicon on 2018/3/26 0026.
 */
public class OkHttpClientImplTest {

    //测试封装的OkHttpClient

    IHttpClient mHttpClient;

    @Before
    public void setUp() throws Exception {

        mHttpClient = new OkHttpClientImpl();

    }

    @Test
    public void get() throws Exception {

        String url = API.Config.getDomain() + API.TEST_GET;
        IRequest request = new BaseRequest(url);
        request.setHeader("testHeader","test header");
        request.setBody("uid","zzc");
        IResponse response = mHttpClient.get(request,false);
        System.out.println("response code is = "+ response.getCode());
        System.out.println("response data is = "+ response.getData());



    }

    @Test
    public void post() throws Exception {
    }

}