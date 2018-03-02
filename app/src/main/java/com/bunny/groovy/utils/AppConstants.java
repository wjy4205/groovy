package com.bunny.groovy.utils;

import java.util.Stack;

/****************************************
 * 功能说明:  app常量类
 *
 * Author: Created by bayin on 2017/12/11.
 ****************************************/

public class AppConstants {
    public static String SINCH_APPKEY = "b1673b83-1899-4c52-b3c4-0446cf327ecc";
    public static int ACCOUNT_TYPE_PHONE = 1;//账号类型：手机号
    public static int ACCOUNT_TYPE_EMAIL = 2;//账号类型：邮箱

    public final static int USER_TYPE_NORMAL = 0;//用户类型 普通用户
    public final static int USER_TYPE_MUSICIAN = 1;//用户类型 表演者
    public final static int USER_TYPE_VENUE = 2;//用户类型 演出厅

    public static String KEY_USER_TYPE = "key_user_type";
    public static String USER_TYPE;//用户类型

    public static int ACTIVITY_FINISH = -100;//activity结束标志

    public static String GMT_FORMAT = "GMT%s";//格林尼治时间标志


    public static int REQUESTCODE_SETFILE = 9;//完善资料请求码

    public static String KEY_USERFILE_LEVEL = "key_userfile_level";//用户资料完善级别
    public final static String USERFILE_LEVLE_FULL = "0000";//已经完善
    public final static String USERFILE_LEVLE_NONE = "1000";//初始状态
    public final static String USERFILE_LEVLE_FIRST = "2000";//完善了第一页
    public final static String USERFILE_LEVLE_SECOND = "3000";//完善了第二页

    public static int REQUESTCODE_SELECT_PIC = 6;//选择图片的请求码
    public static int REQUESTCODE_SELECT_MUSIC = 666;//选择音乐的请求码

    public static String KEY_LOGIN = "key_login";//是否登录标识
    public static String KEY_USERID = "key_userid";//已登录的userid
    public static String KEY_PHONE = "key_phone";//已登录的user 电话

    public final static String Code_Send_Success = "0000";//发送成功
    public final static String Code_Send_ServerError = "5000";//sinch服务器出错
    public final static String Code_Send_InvalidPhone = "1000";//填的不对

    public final static String Code_Verify_Correct = "6666";//验证通过
    public final static String Code_Verify_Invalid = "1111";//验证未通过
    public final static String Code_Verify_ServerError = "5555";//验证未通过


    public final static String EVENT_LOGIN_SUCCESS = "logout_success";//eventbus 用户退出标志
    public final static String EVENT_LOGIN_OUT = "logout";//eventbus 用户退出标志

    public final static String Pay_Style_paypal = "0";//余额支付方式
    public final static String Pay_Style_balance = "1";//PayPal支付方式

    public static String KEY_DISCOVER = "key_discover";

}
