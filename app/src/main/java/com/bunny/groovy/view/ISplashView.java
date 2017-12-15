package com.bunny.groovy.view;

import android.app.Activity;

/****************************************
 * 功能说明:  欢迎页的接口
 *
 * Author: Created by bayin on 2017/12/15.
 ****************************************/

public interface ISplashView {
    Activity get();
    void requestFailed();
}
