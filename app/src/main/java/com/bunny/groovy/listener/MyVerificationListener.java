package com.bunny.groovy.listener;

import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.utils.UIUtils;
import com.sinch.verification.CodeInterceptionException;
import com.sinch.verification.IncorrectCodeException;
import com.sinch.verification.InitiationResult;
import com.sinch.verification.InvalidInputException;
import com.sinch.verification.ServiceErrorException;
import com.sinch.verification.VerificationListener;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

/****************************************
 * 功能说明:  短信验证码回调
 *
 * Author: Created by bayin on 2017/12/11.
 ****************************************/

public class MyVerificationListener implements VerificationListener {

    @Override
    public void onInitiated(InitiationResult initiationResult) {
        UIUtils.showBaseToast("Verification code sent.");
        //跳转到下一页
        EventBus.getDefault().post(AppConstants.Code_Send_Success);
    }

    @Override
    public void onInitiationFailed(Exception e) {
        if (e instanceof InvalidInputException) {
            //手机号输入有误
            EventBus.getDefault().post(AppConstants.Code_Send_InvalidPhone);
        } else if (e instanceof ServiceErrorException) {
            //sinch服务器出错
            EventBus.getDefault().post(AppConstants.Code_Send_ServerError);
        } else {
            // Other system error, such as UnknownHostException in case of network error
            EventBus.getDefault().post(AppConstants.Code_Send_ServerError);
        }
        UIUtils.showBaseToast("Send fail."+e.toString());
        KLog.a(e.toString());
    }

    @Override
    public void onVerified() {
        // Verification successful
        EventBus.getDefault().post(AppConstants.Code_Verify_Correct);
    }

    @Override
    public void onVerificationFailed(Exception e) {
        if (e instanceof InvalidInputException) {
            // Incorrect number or code provided
            //验证码输入不正确
            EventBus.getDefault().post(AppConstants.Code_Verify_Invalid);
        } else if (e instanceof CodeInterceptionException) {
            // Intercepting the verification code automatically failed, input the code manually with verify()
        } else if (e instanceof IncorrectCodeException) {
            // The verification code provided was incorrect
            EventBus.getDefault().post(AppConstants.Code_Verify_Invalid);
        } else if (e instanceof ServiceErrorException) {
            // Sinch service error
            EventBus.getDefault().post(AppConstants.Code_Verify_ServerError);
        } else {
            // Other system error, such as UnknownHostException in case of network error
            EventBus.getDefault().post(AppConstants.Code_Verify_ServerError);
        }
        KLog.a(e.toString());
    }

    @Override
    public void onVerificationFallback() {

    }
}
