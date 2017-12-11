package com.bunny.groovy.utils;

import java.util.regex.Pattern;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/12/11.
 ****************************************/

public class PatternUtils {
    private static String USphone_1 = "/^(((1(\\s|))|)\\([1-9]{3}\\)(\\s|-|)[1-9]{3}(\\s|-|)[1-9]{4})$/";
    private static String USphone_2 = "/^(((1(\\s)|)|)[1-9]{3}(\\s|-|)[1-9]{3}(\\s|-|)[1-9]{4})$/";


    /**
     * 匹配美国手机号
     *
     * @param phone
     * @return
     */
    public static boolean isUSphonenumber(String phone) {
        Pattern pattern1 = Pattern.compile(USphone_1);
        Pattern pattern2 = Pattern.compile(USphone_2);
        return pattern1.matcher(phone).find()||pattern2.matcher(phone).find();
    }
}
