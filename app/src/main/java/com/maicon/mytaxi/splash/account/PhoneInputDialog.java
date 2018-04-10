package com.maicon.mytaxi.splash.account;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.text.style.CharacterStyle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.maicon.mytaxi.R;
import com.maicon.mytaxi.splash.common.utils.FormatUtils;

/**
 * Created by Maicon on 2018/4/9 0009.
 */

public class PhoneInputDialog extends Dialog{

    private View mRoot;

    private Button mButton;

    private EditText mPhone;


    public PhoneInputDialog(@NonNull Context context) {
        this(context, R.style.Dialog);
    }

    public PhoneInputDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRoot = inflater.inflate(R.layout.dialog_phone_input,null);
        setContentView(mRoot);
        initListener();


    }

    private void initListener() {
        mButton = (Button) findViewById(R.id.btn_next);
        mButton.setEnabled(false);
        mPhone = (EditText) findViewById(R.id.phone);
        //手机号输入框注册监听检查手机号输入是否合法
        mPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkPhone();
            }
        });
        //按钮注册监听
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                String phone  =  mPhone.getText().toString();
                // todo:显示输入验证码的输入框
                SmsCodeDialog smsCodeDialog = new SmsCodeDialog(getContext(),phone);
                smsCodeDialog.show();
            }
        });

        // 关闭按钮注册监听
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneInputDialog.this.dismiss();
            }
        });
    }




    private void checkPhone() {
        String phone = mPhone.getText().toString();
        boolean legal = FormatUtils.checkMobile(phone);
        mButton.setEnabled(legal);
    }
}
