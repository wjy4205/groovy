package com.bunny.groovy.view;

import android.support.v4.app.FragmentActivity;

import com.bunny.groovy.model.ShowModel;
import com.bunny.groovy.model.PerformerUserModel;

/****************************************
 * 功能说明:  首页接口
 *
 * Author: Created by bayin on 2017/12/15.
 ****************************************/

public interface IOverView {
    void initNextView(ShowModel showModel);
    void showEmptyNextShow();
    FragmentActivity get();
    void setView(PerformerUserModel userModel);
}
