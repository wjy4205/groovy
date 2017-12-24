package com.bunny.groovy.view;

import android.app.Activity;

import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.model.StyleModel;

import java.util.List;

/**
 * Created by Administrator on 2017/12/21.
 */

public interface IMeView {
    Activity get();
    void setUserView(PerformerUserModel model);
    void showStylePop(List<StyleModel> modelList);
}
