package com.bunny.groovy.view;

import android.app.Activity;

import com.bunny.groovy.model.PerformerUserModel;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/12/13.
 ****************************************/

public interface ILoginView {
    void loginSuccess(PerformerUserModel userModel);

    Activity get();

    /**
     * 跳转完善资料第一页
     */
    void launchFirstPage();

    /**
     * 跳转至第二页
     */
    void launchSecondPage();

    /**
     * 跳转至第三页
     */
    void launchThirdPage();

    /**
     * 跳转至主页
     */
    void launchMainPage();
}
