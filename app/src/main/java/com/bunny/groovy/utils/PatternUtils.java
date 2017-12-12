package com.bunny.groovy.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/12/11.
 ****************************************/

public class PatternUtils {
    private static String phonePattern = "^(1\\-)?[0-9]{3}\\-?[0-9]{3}\\-?[0-9]{4}$";

    private static String emailPattern = "^[A-Za-z0-9\\u4e00-\\u9fa5._]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    /**
     * 匹配美国手机号
     *
     * @param phone
     * @return
     */
    public static boolean isUSphonenumber(String phone) {
        Pattern pattern1 = Pattern.compile(phonePattern);
        return pattern1.matcher(phone).find();
    }

    /**
     * 验证邮箱是否合法
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && email.matches(emailPattern);
    }
}
