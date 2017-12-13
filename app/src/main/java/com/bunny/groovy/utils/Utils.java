package com.bunny.groovy.utils;

import com.socks.library.KLog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/****************************************
 * 功能说明:统一工具类
 *
 * Author: Created by bayin on 2017/12/13.
 ****************************************/

public class Utils {
    /**
     * 获取时区
     *
     * @return 时区
     */
    public static String getTimeZone() {
        DateFormat dateFormat = new SimpleDateFormat("Z");//‘z’小写CST；'Z'大写+0800
        String format = String.format(AppConstants.GMT_FORMAT, dateFormat.format(new Date()));
        KLog.d(format);
        return format;
    }
}
