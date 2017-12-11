package com.bunny.groovy.listener;

import com.sinch.verification.CodeInterceptionException;
import com.sinch.verification.IncorrectCodeException;
import com.sinch.verification.InitiationResult;
import com.sinch.verification.InvalidInputException;
import com.sinch.verification.ServiceErrorException;
import com.sinch.verification.VerificationListener;

/****************************************
 * 功能说明:  短信验证码回调
 *
 * Author: Created by bayin on 2017/12/11.
 ****************************************/

public class MyVerificationListener implements VerificationListener {


    @Override
    public void onInitiated(InitiationResult initiationResult) {

    }

    @Override
    public void onInitiationFailed(Exception e) {
        if (e instanceof InvalidInputException) {
            // Incorrect number provided
        } else if (e instanceof ServiceErrorException) {
            // Sinch service error
        } else {
            // Other system error, such as UnknownHostException in case of network error
        }
    }

    @Override
    public void onVerified() {
        // Verification successful
    }

    @Override
    public void onVerificationFailed(Exception e) {
        if (e instanceof InvalidInputException) {
            // Incorrect number or code provided
        } else if (e instanceof CodeInterceptionException) {
            // Intercepting the verification code automatically failed, input the code manually with verify()
        } else if (e instanceof IncorrectCodeException) {
            // The verification code provided was incorrect
        } else if (e instanceof ServiceErrorException) {
            // Sinch service error
        } else {
            // Other system error, such as UnknownHostException in case of network error
        }
    }

    @Override
    public void onVerificationFallback() {

    }
}
