package com.bunny.groovy.view;

import android.app.Activity;

/**
 * 打赏接口
 *
 * Created by Administrator on 2017/12/17.
 */

public interface IRewardView {
    Activity get();
    void onTokenGet(String token);
}
