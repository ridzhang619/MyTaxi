package com.maicon.mytaxi.splash.account;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Maicon on 2018/4/9 0009.
 */

public class PhoneInputDialog extends Dialog{
    public PhoneInputDialog(@NonNull Context context) {
        super(context);
    }

    public PhoneInputDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PhoneInputDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
