package com.bunny.groovy.view;

import android.app.Activity;

import com.bunny.groovy.model.VenueModel;

/****************************************
 * 功能说明:  音乐厅详情页，接口
 *
 * Author: Created by bayin on 2018/1/3.
 ****************************************/

public interface IVenueView {
    Activity get();
    void setView(VenueModel model);
    void cancleFavorite();
    void favorite();
}
