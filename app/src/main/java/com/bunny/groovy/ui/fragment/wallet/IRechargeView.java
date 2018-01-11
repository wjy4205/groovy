package com.bunny.groovy.ui.fragment.wallet;

import android.app.Activity;

/****************************************
 * 功能说明:  充值接口
 *
 * Author: Created by bayin on 2018/1/11.
 ****************************************/

public interface IRechargeView {
    Activity get();
    void onTokenGet(String token);
}
