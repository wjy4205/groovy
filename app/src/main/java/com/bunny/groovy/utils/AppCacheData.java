package com.bunny.groovy.utils;

import com.bunny.groovy.model.PerformerUserModel;

/**
 * app运行时数据缓存
 * <p>
 * Created by Administrator on 2017/12/13.
 */

public class AppCacheData {
    private static PerformerUserModel userModel = new PerformerUserModel();

    public static PerformerUserModel getPerformerUserModel() {
        return userModel;
    }

    public static void setPerformerUserModel(PerformerUserModel performerUserModel) {
        userModel = performerUserModel;
    }
}
