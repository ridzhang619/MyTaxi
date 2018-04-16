package com.maicon.mytaxi.splash.account;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.maicon.mytaxi.R;
import com.maicon.mytaxi.splash.common.http.IHttpClient;
import com.maicon.mytaxi.splash.common.http.IRequest;
import com.maicon.mytaxi.splash.common.http.api.API;
import com.maicon.mytaxi.splash.common.http.impl.BaseRequest;
import com.maicon.mytaxi.splash.common.http.impl.OkHttpClientImpl;
import com.maicon.mytaxi.splash.common.utils.DevUtil;

import java.lang.ref.SoftReference;
import java.util.logging.LogRecord;

/**
 * Created by Maicon on 2018/4/16 0016.
 */

public class CreatePasswordDialog extends Dialog{

    private static final String TAG = "CreatePasswordDialog";
    private static final int REGISTER_SUCCESS = 1;
    private static final int SERVER_FAIL = 100;
    private TextView mTitle;
    private TextView mPhone;
    private EditText mPw;
    private EditText mPw1;
    private Button mBtnCofirm;
    private View mLoading;
    private TextView mTips;
    private IHttpClient mHttpClient;
    private String mPhoneStr;
    private MyHandler mHandler;


    static class MyHandler extends Handler {

        SoftReference<CreatePasswordDialog> codeDialogRef;

        public MyHandler(CreatePasswordDialog dialog){
            codeDialogRef = new SoftReference<CreatePasswordDialog>(dialog);
        }


        @Override
        public void handleMessage(Message msg) {

            CreatePasswordDialog dialog = codeDialogRef.get();
            if(dialog == null){
                return;
            }

            switch(msg.what){
                case REGISTER_SUCCESS:

                    break;
                case SERVER_FAIL:
                    Toast.makeText(dialog.getContext(), dialog.getContext().getString(R.string.error_server), Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }




    public CreatePasswordDialog(@NonNull Context context,String phone) {
        this(context,R.style.Dialog);
        mPhoneStr = phone;
        mHttpClient = new OkHttpClientImpl();
        mHandler = new MyHandler(this);

    }

    public CreatePasswordDialog(@NonNull Context context, int themeResId) {
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
        View root = inflater.inflate(R.layout.dialog_create_pw,null);
        setContentView(root);
        initViews();
    }

    private void initViews() {
        mPhone = (TextView)findViewById(R.id.phone);
        mPw = (EditText)findViewById(R.id.pw);
        mPw1 = (EditText)findViewById(R.id.pw1);
        mBtnCofirm = (Button)findViewById(R.id.btn_confirm);
        mLoading = findViewById(R.id.loading);
        mTips = (TextView)findViewById(R.id.tips);
        mTitle = ( TextView)findViewById(R.id.dialog_title);
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mBtnCofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        mPhone.setText(mPhoneStr);
    }

    /**
     * 提交注册
     */
    private void submit() {
        if(checkPassword()){
            final String password = mPw.getText().toString();
            final String phone = mPhoneStr;
            //请求网络,提交注册
            new Thread(){
                @Override
                public void run() {
                    String url = API.Config.getDomain() + API.REGISTER;
                    IRequest request = new BaseRequest(url);
                    request.setBody("phone",phone);
                    request.setBody("password",password);
                    request.setBody("uid", DevUtil.UUID(getContext()));
                }
            }.start();
        }
    }

    /**
     * 检查密码输入
     * @return
     */
    private boolean checkPassword() {
        String password = mPw.getText().toString();
        if(TextUtils.isEmpty(password)){
            mTips.setVisibility(View.VISIBLE);
            mTips.setText(R.string.pw_not_null);
            mTips.setTextColor(getContext().getResources().getColor(R.color.error_red));
            return false;
        }
        if(!password.equals(mPw1.getText().toString())){
            mTips.setText(R.string.pw_confirm_diff);
            mTips.setTextColor(getContext().getResources().getColor(R.color.error_red));
            return false;
        }
        return true;
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
