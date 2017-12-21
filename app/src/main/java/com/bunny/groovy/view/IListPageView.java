package com.bunny.groovy.view;

import android.app.Activity;

/**
 * 列表界面统一接口
 * Created by Administrator on 2017/12/21.
 */

public interface IListPageView<T> {
    Activity get();

    void setView(T t);

    void setNormalView();

    void setNodata();

    void setError();
}
