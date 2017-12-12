package com.bunny.groovy.view;

import android.app.Activity;

/**
 * Created by Administrator on 2017/12/9.
 */

public interface ISingUpView {
//    void showMessage(String msg);
    void showCheckResult(boolean invalid,int accountType,String msg);
    void nextStep();
    Activity get();
    void registerSuccess();
}
