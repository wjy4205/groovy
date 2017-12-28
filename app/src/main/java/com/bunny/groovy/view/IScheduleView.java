package com.bunny.groovy.view;

import android.app.Activity;

import com.bunny.groovy.model.ScheduleModel;

/**
 * Created by Administrator on 2017/12/28.
 */
public interface IScheduleView {
    Activity get();
    void setFailureView();
    void setSuccessView(ScheduleModel model);
}
