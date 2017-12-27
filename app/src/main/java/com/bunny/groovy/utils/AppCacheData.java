package com.bunny.groovy.utils;

import com.bunny.groovy.model.PerformerUserModel;

import java.util.HashMap;
import java.util.Map;

/**
 * app运行时数据缓存
 * <p>
 * Created by Administrator on 2017/12/13.
 */

public class AppCacheData {
    private static PerformerUserModel userModel;

    public static PerformerUserModel getPerformerUserModel() {
        return userModel;
    }

    public static void setPerformerUserModel(PerformerUserModel performerUserModel) {
        userModel = performerUserModel;
    }

    /**
     * 清空缓存表演者信息
     */
    public static void resetPerformer(){
        userModel = null;
    }

    private static Map<String, String> fileMap;

    public static Map<String, String> getFileMap() {
        if (fileMap == null) fileMap = new HashMap<>();
        return fileMap;
    }
}
