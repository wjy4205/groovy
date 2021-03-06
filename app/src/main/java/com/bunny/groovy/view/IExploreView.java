package com.bunny.groovy.view;

import android.app.Activity;

import com.bunny.groovy.model.OpportunityModel;
import com.bunny.groovy.model.StyleModel;

import java.util.List;

/****************************************
 * 功能说明:申请表演机会接口
 *
 * Author: Created by bayin on 2017/12/25.
 ****************************************/

public interface IExploreView {
    Activity get();
    void setListData(List<OpportunityModel> list);
    void applyResult(boolean success,String msg);
    void showStylePop(List<StyleModel> modelList);
}
