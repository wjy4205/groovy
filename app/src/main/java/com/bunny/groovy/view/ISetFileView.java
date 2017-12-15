package com.bunny.groovy.view;

import android.app.Activity;

import com.bunny.groovy.model.PerformStyleModel;

import java.util.List;

/****************************************
 * 功能说明:  完善资料的控制器
 *
 * Author: Created by bayin on 2017/12/12.
 ****************************************/

public interface ISetFileView{
    Activity get();
    void showStylePop(List<PerformStyleModel> modelList);
}
