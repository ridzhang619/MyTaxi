package com.maicon.mytaxi.splash.account;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dalimao.corelibrary.VerificationCodeInput;
import com.google.gson.Gson;
import com.maicon.mytaxi.R;
import com.maicon.mytaxi.splash.common.http.IHttpClient;
import com.maicon.mytaxi.splash.common.http.IRequest;
import com.maicon.mytaxi.splash.common.http.IResponse;
import com.maicon.mytaxi.splash.common.http.api.API;
import com.maicon.mytaxi.splash.common.http.biz.BaseBizResponse;
import com.maicon.mytaxi.splash.common.http.impl.BaseRequest;
import com.maicon.mytaxi.splash.common.http.impl.BaseResponse;
import com.maicon.mytaxi.splash.common.http.impl.OkHttpClientImpl;

import java.lang.ref.SoftReference;

/**
 * Created by Maicon on 2018/4/10 0010.
 * 验证码输入框Dialog
 */

public class SmsCodeDialog extends Dialog{

    private static final String TAG = "SmsCodeDialog";
    private static final int SMS_SEND_SUCCESS= 1;
    public static final int SMS_SEND_FAIL = -1;
    private static final int SMS_CHECK_SUCCESS = 2;
    private static final int SMS_CHECK_FAIL = -2;


    private String mPhone;
    private Button mResentBtn;
    private VerificationCodeInput mVerificationCodeInput;
    private View mLoading;
    private View mErrorView;
    private TextView mPhoneTv;

    private IHttpClient mHttpClient;

    private SmsCodeHandler mSmsCodeHandler;

    private CountDownTimer mCountDownTimer = new CountDownTimer(60000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mResentBtn.setEnabled(false);
//            mResentBtn.setText(String.format(getContext().getString(R.string.count_format_text)));
            mResentBtn.setText(millisUntilFinished/1000 + getContext().getString(R.string.count_format_text));
        }

        @Override
        public void onFinish() {
            mResentBtn.setEnabled(false);
            mResentBtn.setText("重新发送");
            cancel();
        }
    };


    static class SmsCodeHandler extends Handler {

        SoftReference<SmsCodeDialog> codeDialogRef;

        public SmsCodeHandler(SmsCodeDialog codeDialog){
            codeDialogRef = new SoftReference<SmsCodeDialog>(codeDialog);
        }

        @Override
        public void handleMessage(Message msg) {

            SmsCodeDialog dialog = codeDialogRef.get();

            if(dialog == null){
                return;
            }

            switch (msg.what){
                case SMS_SEND_SUCCESS:
                    //验证码发送成功,打开倒计时
                    dialog.mCountDownTimer.start();
                    break;
                case SMS_SEND_FAIL:
                    Toast.makeText(dialog.getContext(), "发送失败", Toast.LENGTH_SHORT).show();
                    break;
                case SMS_CHECK_SUCCESS:
                    dialog.showVerifyState(true);
                    break;
                case SMS_CHECK_FAIL:
                    dialog.showVerifyState(false);
                    break;


            }


        }
    }


    public SmsCodeDialog(Context context,String phone) {
        this(context, R.style.Dialog);
        mPhone = phone;
        mHttpClient = new OkHttpClientImpl();
        mSmsCodeHandler = new SmsCodeHandler(this);
    }

    public SmsCodeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root =  inflater.inflate(R.layout.dialog_smscode_input,null);
        setContentView(root);
        mPhoneTv = (TextView)findViewById(R.id.phone);
        String template = getContext().getString(R.string.template_text);
        mPhoneTv.setText(String.format(template,mPhone));
        mResentBtn = (Button)findViewById(R.id.btn_resend);
        mVerificationCodeInput = (VerificationCodeInput)findViewById(R.id.verificationCodeInput);
        mLoading = findViewById(R.id.loading);
        mErrorView = findViewById(R.id.error);
        mErrorView.setVisibility(View.GONE);
        initListener();

        // todo:请求下发验证码

        requestSendSmsCode();

    }

    /**
     * 请求下发验证码
     */
    private void requestSendSmsCode() {

        new Thread(){

            @Override
            public void run() {
                String url = API.Config.getDomain() + API.GET_SMS_CODE;
                Log.d(TAG,url);

                IRequest request = new BaseRequest(url);
                request.setBody("phone",mPhone);
                IResponse response = mHttpClient.get(request, false);
                Log.d(TAG,"response :" + response.getData());

                if(response.getCode() == BaseResponse.STATE_OK){
                    BaseBizResponse bizResponse =
                     new Gson().fromJson(response.getData(), BaseBizResponse.class);
                    if(bizResponse.getCode() == BaseBizResponse.STATE_OK){
                            mSmsCodeHandler.sendEmptyMessage(SMS_SEND_SUCCESS);
                    }else{
                        mSmsCodeHandler.sendEmptyMessage(SMS_SEND_FAIL);
                    }
                }else{
                    mSmsCodeHandler.sendEmptyMessage(SMS_SEND_FAIL);
                }

            }
        }.start();

    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mCountDownTimer.cancel();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    private void initListener() {

        // 关闭按钮注册监听器
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // 重发验证码按钮注册监听器
        mResentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resend();
            }
        });

        // 验证码输入完成监听
        mVerificationCodeInput.setOnCompleteListener(new VerificationCodeInput.Listener() {
            @Override
            public void onComplete(String code) {
                commit(code);
            }
        });

    }

    /**
     * 提交验证码
     * @param code
     */
    private void commit(final String code) {
        showLoading();
        // 网络请求校验验证码

        new Thread(){

            @Override
            public void run() {
                String url = API.Config.getDomain() + API.CHECK_SMS_CODE;
                Log.d(TAG,url);

                IRequest request = new BaseRequest(url);
                request.setBody("phone",mPhone);
                request.setBody("code",code);

                IResponse response = mHttpClient.get(request, false);
                Log.d(TAG,"response :" + response.getData());

                if(response.getCode() == BaseResponse.STATE_OK){
                    BaseBizResponse bizResponse =
                            new Gson().fromJson(response.getData(), BaseBizResponse.class);
                    if(bizResponse.getCode() == BaseBizResponse.STATE_OK){
                        mSmsCodeHandler.sendEmptyMessage(SMS_CHECK_SUCCESS);
                    }else{
                        mSmsCodeHandler.sendEmptyMessage(SMS_CHECK_FAIL);
                    }
                }else{
                    mSmsCodeHandler.sendEmptyMessage(SMS_CHECK_FAIL);
                }

            }
        }.start();


    }

    private void showLoading() {
        mLoading.setVisibility(View.VISIBLE);
    }

    private void resend(){
        String template = getContext().getString(R.string.template_text);
        mPhoneTv.setText(String.format(template,mPhone));
    }

    public void showVerifyState(boolean success){
        if(success){
            mErrorView.setVisibility(View.GONE);
            mLoading.setVisibility(View.VISIBLE);

        }else{
            // 提示验证码错误
            mErrorView.setVisibility(View.VISIBLE);
            mVerificationCodeInput.setEnabled(true);
            mLoading.setVisibility(View.GONE);
        }
    }

}
