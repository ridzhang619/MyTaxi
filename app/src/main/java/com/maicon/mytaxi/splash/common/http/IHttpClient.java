package com.maicon.mytaxi.splash.common.http;


/**
 * Created by Maicon on 2018/3/26 0026.
 */

public interface IHttpClient {

    IResponse get(IRequest request, boolean forceCache);
    IResponse post(IRequest request,boolean forceCache);

}
