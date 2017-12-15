package com.bunny.groovy.view;

import com.bunny.groovy.model.NextShowModel;

/****************************************
 * 功能说明:  首页接口
 *
 * Author: Created by bayin on 2017/12/15.
 ****************************************/

public interface IOverView {
    void initNextView(NextShowModel nextShowModel);
    void showEmptyNextShow();
}
