package com.maicon.mytaxi.splash.account;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dalimao.corelibrary.VerificationCodeInput;
import com.maicon.mytaxi.R;

/**
 * Created by Maicon on 2018/4/10 0010.
 * 验证码输入框Dialog
 */

public class SmsCodeDialog extends Dialog{

    private static final String TAG = "SmsCodeDialog";

    private String mPhone;
    private Button mResentBtn;
    private VerificationCodeInput mVerificationCodeInput;
    private View mLoading;
    private View mErrorView;
    private TextView mPhoneTv;

    private CountDownTimer mCountDownTimer = new CountDownTimer(10000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mResentBtn.setEnabled(false);
            mResentBtn.setText(String.format(getContext().getString(R.string.count_format_text)));
        }

        @Override
        public void onFinish() {
            mResentBtn.setEnabled(false);
            mResentBtn.setText("重新发送");
            cancel();
        }
    };


    public SmsCodeDialog(Context context,String phone) {
        this(context, R.style.Dialog);
        mPhone = phone;
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

    private void commit(String code) {
        showLoading();
        // todo:网络请求校验验证码
    }

    private void showLoading() {
        mLoading.setVisibility(View.VISIBLE);
    }

    private void resend(){
        String template = getContext().getString(R.string.template_text);
        mPhoneTv.setText(String.format(template,mPhone));
    }

}
