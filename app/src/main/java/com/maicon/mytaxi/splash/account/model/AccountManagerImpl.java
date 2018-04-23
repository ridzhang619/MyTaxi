package com.maicon.mytaxi.splash.account.model;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.maicon.mytaxi.splash.MainActivity;
import com.maicon.mytaxi.splash.MyApplication;
import com.maicon.mytaxi.splash.account.model.response.Account;
import com.maicon.mytaxi.splash.account.model.response.LoginResponse;
import com.maicon.mytaxi.splash.common.http.IHttpClient;
import com.maicon.mytaxi.splash.common.http.IRequest;
import com.maicon.mytaxi.splash.common.http.IResponse;
import com.maicon.mytaxi.splash.common.http.api.API;
import com.maicon.mytaxi.splash.common.http.biz.BaseBizResponse;
import com.maicon.mytaxi.splash.common.http.impl.BaseRequest;
import com.maicon.mytaxi.splash.common.http.impl.BaseResponse;
import com.maicon.mytaxi.splash.common.storage.SharedPreferencesDao;
import com.maicon.mytaxi.splash.common.utils.DevUtil;

import java.util.logging.MemoryHandler;

/**
 * Created by Maicon on 2018/4/23 0023.
 */

public class AccountManagerImpl implements IAccountManager{

    private static final String TAG = "AccountManagerImpl";
    //服务器错误
    private static final int SERVER_FAIL = -999;
    //验证码发送成功
    private static final int SMS_SEND_SUCCESS = 1;
    //验证码发送失败
    private static final int SMS_SEND_FAIL = -1;
    //验证码校验成功
    private static final int SMS_CHECK_SUCCESS = 2;
    //验证码错误
    private static final int SMS_CHECK_FAIL = -2;
    //用户已经存在
    private static final int USER_EXIST = 3;
    //用户不存在
    private static final int USER_NOT_EXIST = -3;
    //注册成功
    private static final int REGISTER_SUCCESS = 4;
    //登录成功
    private static final int LOGIN_SUCCESS = 5;
    //登录失效
    private static final int TOKEN_INVALID = -6;

    //网络请求库
    private IHttpClient mHttpClient;
    //数据存储
    private SharedPreferencesDao mSharedPreferencesDao;
    //发送消息
    private Handler mHandler;


    public AccountManagerImpl(IHttpClient mHttpClient, SharedPreferencesDao mSharedPreferencesDao) {
        this.mHttpClient = mHttpClient;
        this.mSharedPreferencesDao = mSharedPreferencesDao;
    }

    @Override
    public void setHandler(Handler handler) {
        mHandler = handler;
    }

    @Override
    public void fetchSmsCode(final String phone) {

        new Thread(){
            @Override
            public void run() {
                String url = API.Config.getDomain() + API.GET_SMS_CODE;
                Log.d(TAG,url);

                IRequest request = new BaseRequest(url);
                request.setBody("phone",phone);
                IResponse response = mHttpClient.get(request, false);
                Log.d(TAG,"response :" + response.getData());

                if(response.getCode() == BaseResponse.STATE_OK){
                    BaseBizResponse bizResponse =
                            new Gson().fromJson(response.getData(), BaseBizResponse.class);
                    if(bizResponse.getCode() == BaseBizResponse.STATE_OK){
                        mHandler.sendEmptyMessage(SMS_SEND_SUCCESS);
                    }else{
                        mHandler.sendEmptyMessage(SMS_SEND_FAIL);
                    }
                }else{
                    mHandler.sendEmptyMessage(SMS_SEND_FAIL);
                }

            }
        }.start();
    }

    @Override
    public void checkSmsCode(final String phone, final String smsCode) {
        new Thread(){

            @Override
            public void run() {
                String url = API.Config.getDomain() + API.CHECK_SMS_CODE;
                Log.d(TAG,url);

                IRequest request = new BaseRequest(url);
                request.setBody("phone",phone);
                request.setBody("code",smsCode);

                IResponse response = mHttpClient.get(request, false);
                Log.d(TAG,"response :" + response.getData());

                if(response.getCode() == BaseResponse.STATE_OK){
                    BaseBizResponse bizResponse =
                            new Gson().fromJson(response.getData(), BaseBizResponse.class);
                    if(bizResponse.getCode() == BaseBizResponse.STATE_OK){
                        mHandler.sendEmptyMessage(SMS_CHECK_SUCCESS);
                    }else{
                        mHandler.sendEmptyMessage(SMS_CHECK_FAIL);
                    }
                }else{
                    mHandler.sendEmptyMessage(SMS_CHECK_FAIL);
                }

            }
        }.start();
    }

    @Override
    public void checkUserExist(final String phone) {
        new Thread(){

            @Override
            public void run() {
                String url = API.Config.getDomain() + API.CHECK_USER_EXIST;
                Log.d(TAG,url);

                IRequest request = new BaseRequest(url);
                request.setBody("phone",phone);
                IResponse response = mHttpClient.get(request, false);
                Log.d(TAG,"response :" + response.getData());

                if(response.getCode() == BaseResponse.STATE_OK){
                    BaseBizResponse bizResponse =
                            new Gson().fromJson(response.getData(), BaseBizResponse.class);
                    if(bizResponse.getCode() == BaseBizResponse.STATE_USER_EXIST){
                        mHandler.sendEmptyMessage(USER_EXIST);
                    }else{
                        mHandler.sendEmptyMessage(USER_NOT_EXIST);
                    }
                }else{
                    mHandler.sendEmptyMessage(SERVER_FAIL);
                }

            }
        }.start();
    }

    @Override
    public void register(final String phone, final String password) {
        new Thread(){
            @Override
            public void run() {
                String url = API.Config.getDomain() + API.REGISTER;
                IRequest request = new BaseRequest(url);
                request.setBody("phone",phone);
                request.setBody("password",password);
                request.setBody("uid", DevUtil.UUID(MyApplication.getInstance()));
                IResponse response = mHttpClient.post(request,false);
                Log.d(TAG,"response is :"+response.getData());
                if(response.getCode() == BaseBizResponse.STATE_OK){
                    BaseBizResponse bizResponse =
                            new Gson().fromJson(response.getData(),BaseBizResponse.class);
                    if(bizResponse.getCode() == BaseBizResponse.STATE_OK){
                        mHandler.sendEmptyMessage(REGISTER_SUCCESS);
                    }else{
                        mHandler.sendEmptyMessage(SERVER_FAIL);
                    }
                }else{
                    mHandler.sendEmptyMessage(SERVER_FAIL);
                }
            }
        }.start();
    }

    @Override
    public void login(final String phone, final String password) {
        new Thread(){
            @Override
            public void run() {
                String url = API.Config.getDomain() + API.LOGIN;
                IRequest request = new BaseRequest(url);
                request.setBody("phone",phone);
                request.setBody("password",password);
                request.setBody("uid", DevUtil.UUID(MyApplication.getInstance()));
                IResponse response = mHttpClient.post(request,false);
                Log.d(TAG,"response is :"+response.getData());
                if(response.getCode() == BaseBizResponse.STATE_OK){
                    LoginResponse bizResponse =
                            new Gson().fromJson(response.getData(),LoginResponse.class);
                    if(bizResponse.getCode() == BaseBizResponse.STATE_OK){
                        Account account = bizResponse.getData();
                        // todo: 加密存储
                        SharedPreferencesDao dao =
                                new SharedPreferencesDao(MyApplication.getInstance(), SharedPreferencesDao.FILE_ACCOUNT);
                        dao.save(SharedPreferencesDao.KEY_ACCOUNT,account);
                        mHandler.sendEmptyMessage(LOGIN_SUCCESS);
                    }
//                    if(bizResponse.getCode() == BaseBizResponse.STATE_PW_ERROR){
//                        mHandler.sendEmptyMessage(PW_ERROR);
//                    }
                    else{
                        mHandler.sendEmptyMessage(SERVER_FAIL);
                    }
                }else{
                    mHandler.sendEmptyMessage(SERVER_FAIL);
                }
            }
        }.start();
    }

    @Override
    public void loginByToken() {
        SharedPreferencesDao dao = new SharedPreferencesDao(MyApplication.getInstance(),SharedPreferencesDao.FILE_ACCOUNT);
        final Account account = (Account) dao.get(SharedPreferencesDao.KEY_ACCOUNT,Account.class);


        // 登录是否过期
        boolean tokenValid = false;

        //todo:检查token是否过期

        if(account != null){
            if(account.getExpired() > System.currentTimeMillis()){
                //token有效
                tokenValid = true;
            }
        }

        if(!tokenValid){
            mHandler.sendEmptyMessage(TOKEN_INVALID);
        }else{
            // todo:请求网络,完成自动登录
            new Thread(){
                @Override
                public void run() {
                    String url = API.Config.getDomain() + API.LOGIN_BY_TOKEN;
                    IRequest request = new BaseRequest(url);
                    request.setBody("token",account.getToken());
                    IResponse response = mHttpClient.post(request,false);
                    Log.d(TAG,"response is :"+response.getData());
                    if(response.getCode() == BaseBizResponse.STATE_OK){
                        LoginResponse bizResponse =
                                new Gson().fromJson(response.getData(),LoginResponse.class);
                        if(bizResponse.getCode() == BaseBizResponse.STATE_OK){
                            Account account = bizResponse.getData();
                            // todo: 加密存储
                            SharedPreferencesDao dao =
                                    new SharedPreferencesDao(MyApplication.getInstance(), SharedPreferencesDao.FILE_ACCOUNT);
                            dao.save(SharedPreferencesDao.KEY_ACCOUNT,account);
                            mHandler.sendEmptyMessage(LOGIN_SUCCESS);
                        }if(bizResponse.getCode() == BaseBizResponse.STATE_TOKEN_INVALID){
                            mHandler.sendEmptyMessage(TOKEN_INVALID);
                        }
                    }else{
                        mHandler.sendEmptyMessage(SERVER_FAIL);

                    }
                }
            }.start();
        }
    }
}
