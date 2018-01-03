package com.bunny.groovy.view;

import android.app.Activity;

import com.bunny.groovy.model.StyleModel;

import java.util.List;

/****************************************
 * 功能说明:  申请演出厅接口
 *
 * Author: Created by bayin on 2018/1/3.
 ****************************************/

public interface IApplyVenueView {
    Activity get();
    void showStylePop(List<StyleModel> modelList);
}
