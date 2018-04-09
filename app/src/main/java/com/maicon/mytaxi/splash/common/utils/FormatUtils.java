package com.maicon.mytaxi.splash.common.utils;

import java.util.regex.Pattern;

/**
 * Created by Maicon on 2018/4/9 0009.
 */

public class FormatUtils {

    public static boolean checkMobile(String phone){
        String regex = "(\\+\\d+)?1[3458]\\d{9}$";
        return Pattern.matches(regex,phone);
    }

}
