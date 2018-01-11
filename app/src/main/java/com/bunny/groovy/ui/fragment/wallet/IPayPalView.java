package com.bunny.groovy.ui.fragment.wallet;

import android.app.Activity;

import com.bunny.groovy.model.PerformerUserModel;

/****************************************
 * 功能说明:  wallet视图接口
 *
 * Author: Created by bayin on 2018/1/10.
 ****************************************/

public interface IPayPalView {
    Activity get();
    void setView(PerformerUserModel userModel);
}
