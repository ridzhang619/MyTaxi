package com.dalimao.mytaxi.splash.common.http;

import java.util.Map;

/**
 * Created by Maicon on 2018/3/21 0021.
 */

public interface IRequest {

    public static final String POST = "POST";
    public static final String GET = "GET";

    /**
     * 指定请求方式
     * @param method
     */
    void setMethod(String method);

    /**
     * 指定请求头
     * @param key
     * @param value
     */
    void setHeader(String key,String value);

    /**
     * 指定请求参数
     * @param key
     * @param value
     */
    void setBody(String key,String value);

    /**
     * 提供给执行库请求行url
     * @return
     */
    String getUrl();
    /**
     * 提供给执行库请求头部
     * @return
     */
    Map<String,String> getHeader();
    /**
     * 提供给执行库请求体,有可能多种类型,所有定义Object
     * @return
     */
    Object getBody();





}
