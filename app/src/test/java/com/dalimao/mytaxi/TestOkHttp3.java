package com.dalimao.mytaxi;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Maicon on 2018/3/21 0021.
 */

public class TestOkHttp3 {
    /**
     * 测试OkHttp Get方法
     */
    @Test
    public void testGet(){
        //创建HttpClient的对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建Request对象
        Request request = new Request.Builder().url("http://httpbin.org/get?id=id").build();
        //OkHttp执行Request
        try {
            Response response = okHttpClient.newCall(request).execute();
            System.out.println("response:"+response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * 测试OkHttp Post方法
     */
    @Test
    public void testPost(){
        //创建HttpClient的对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建Request对象
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        RequestBody body = RequestBody.create(mediaType,"{\"name\":\"maicon\"}");
        Request request = new Request.Builder()
                .url("http://httpbin.org/get?id=id")//请求行
                //.header()请求头
                .post(body) //请求体
                .build();
        //OkHttp执行Request
        try {
            Response response = okHttpClient.newCall(request).execute();
            System.out.println("response:"+response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 测试拦截器
     */
    @Test
    public void testInterceptor(){
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                long startTime = System.currentTimeMillis();
                Request request = chain.request();
                Response response = chain.proceed(request);
                long endTime = System.currentTimeMillis();
                System.out.println("interceptor: cost time = "+(endTime-startTime));
                return response;
            }
        };

        //创建HttpClient的对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        //创建Request对象
        Request request = new Request.Builder().url("http://httpbin.org/get?id=id").build();
        //OkHttp执行Request
        try {
            Response response = okHttpClient.newCall(request).execute();
            System.out.println("response:"+response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 测试缓存
     */
    @Test
    public void testCache(){
        //创建缓存对象
        Cache cache = new Cache(new File("cache.cache"),1024*1024);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();
        //创建Request对象
        Request request = new Request.Builder()
                .url("http://httpbin.org/get?id=id")
                .cacheControl(CacheControl.FORCE_CACHE)//强制使用缓存
                .build();
        //OkHttp执行Request
        try {
            Response response = okHttpClient.newCall(request).execute();
            Response responseCache = response.cacheResponse();
            Response responseNetWork = response.networkResponse();
            System.out.println("response:"+response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}
