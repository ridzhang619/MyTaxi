package com.maicon.mytaxi.splash.common.http.api;

/**
 * Created by Maicon on 2018/3/22 0022.
 */

public class API {

    public static final String TEST_GET = "/get?uid=${uid}";
    public static final String TEST_POST = "/post";
    public static class Config{
        private static final String TEST_DOMAIN = "http://httpbin.org";
        private static final String RELEASE_DOMAIN = "http://httpbin.org";
        private static String domain = TEST_DOMAIN;
        public static void setDebug(boolean debug){
            domain = debug ? TEST_DOMAIN : RELEASE_DOMAIN;
        }
        public static String getDomain(){
            return domain;
        }
    }
}
