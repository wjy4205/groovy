package com.bunny.groovy.view;

import android.app.Activity;

import com.bunny.groovy.model.PerformerUserModel;

/**
 * Created by Administrator on 2017/12/21.
 */

public interface IMeView {
    Activity get();
    void setUserView(PerformerUserModel model);
}
