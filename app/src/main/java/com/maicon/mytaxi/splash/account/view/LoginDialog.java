package com.maicon.mytaxi.splash.account.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.maicon.mytaxi.R;
import com.maicon.mytaxi.splash.MyApplication;
import com.maicon.mytaxi.splash.account.model.response.Account;
import com.maicon.mytaxi.splash.account.model.response.LoginResponse;
import com.maicon.mytaxi.splash.common.http.IHttpClient;
import com.maicon.mytaxi.splash.common.http.IRequest;
import com.maicon.mytaxi.splash.common.http.IResponse;
import com.maicon.mytaxi.splash.common.http.api.API;
import com.maicon.mytaxi.splash.common.http.biz.BaseBizResponse;
import com.maicon.mytaxi.splash.common.http.impl.BaseRequest;
import com.maicon.mytaxi.splash.common.http.impl.OkHttpClientImpl;
import com.maicon.mytaxi.splash.common.storage.SharedPreferencesDao;
import com.maicon.mytaxi.splash.common.utils.DevUtil;

import java.lang.ref.SoftReference;


/**
 * Created by Maicon on 2018/4/19 0019.
 */

public class LoginDialog extends Dialog{
    private static final String TAG = "LoginDialog";
    private static final int LOGIN_SUCCESS = 1;
    private static final int SERVER_FAIL = 100;
    private static final int PW_ERROR = -1;
    private TextView mPhone;
    private EditText mPw;
    private Button mBtnConfirm;
    private View mLoading;
    private TextView mTips;
    private String mPhoneStr;

    private IHttpClient mHttpClient;
    private MyHandler mHandler;

    static class MyHandler extends Handler {

        SoftReference<LoginDialog> mDialog;

        public MyHandler(LoginDialog dialog){
            mDialog = new SoftReference<LoginDialog>(dialog);
        }

        @Override
        public void handleMessage(Message msg) {
            LoginDialog dialog = mDialog.get();
            if(dialog == null){
                return;
            }
            switch (msg.what){
                case LOGIN_SUCCESS:
                    dialog.showLoginSuccess();
                    break;
                case PW_ERROR:
                    dialog.showPasswordError();
                    break;
                case SERVER_FAIL:
                    dialog.showServerError();
                    break;
            }
        }
    }


    public LoginDialog(@NonNull Context context,String phone) {
        this(context, R.style.Dialog);
        mPhoneStr = phone;
        mHttpClient = new OkHttpClientImpl();
        mHandler = new MyHandler(this);
    }

    public LoginDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.dialog_login_input,null);
        setContentView(root);
        initViews();
    }

    private void initViews() {

        mPhone = (TextView)findViewById(R.id.phone);
        mPw = (EditText)findViewById(R.id.password);
        mBtnConfirm = (Button)findViewById(R.id.btn_confirm);
        mLoading = findViewById(R.id.loading);
        mTips = (TextView)findViewById(R.id.tips);
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        mPhone.setText(mPhoneStr);




    }

    /**
     * 提交登录
     */
    private void submit() {
        final String password = mPw.getText().toString();


    }


    /**
     *
     * @param show
     */
    public void showOrHideLoading(boolean show){
        if(show){
            mLoading.setVisibility(View.VISIBLE);
            mBtnConfirm.setVisibility(View.GONE);
        }else{
            mLoading.setVisibility(View.GONE);
            mBtnConfirm.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 处理登录成功
     */
    public void showLoginSuccess(){
        mLoading.setVisibility(View.GONE);
        mBtnConfirm.setVisibility(View.GONE);
        mTips.setVisibility(View.VISIBLE);
        mTips.setTextColor(getContext().getResources().getColor(R.color.color_text_normal));
        mTips.setText(getContext().getResources().getString(R.string.login_suc));
        Toast.makeText(getContext(), getContext().getResources().getString(R.string.login_suc), Toast.LENGTH_SHORT).show();
        dismiss();
    }

    /**
     * 显示服务器出错
     */
    public void showServerError(){
        showOrHideLoading(false);
        mTips.setVisibility(View.VISIBLE);
        mTips.setTextColor(getContext().getResources().getColor(R.color.error_red));
        mTips.setText(getContext().getResources().getString(R.string.server_busy));

    }

    /**
     * 处理密码错误
     */
    public void showPasswordError(){
        showOrHideLoading(false);
        mTips.setVisibility(View.VISIBLE);
        mTips.setTextColor(getContext().getResources().getColor(R.color.error_red));
        mTips.setText(getContext().getResources().getString(R.string.password_error));
    }

}
