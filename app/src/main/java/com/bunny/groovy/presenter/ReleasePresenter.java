package com.bunny.groovy.presenter;

import com.bunny.groovy.api.SubscriberCallBack;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.ResultResponse;
import com.bunny.groovy.model.StyleModel;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.view.ISetFileView;

import java.util.List;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/12/19.
 ****************************************/

public class ReleasePresenter extends BasePresenter<ISetFileView> {
    public ReleasePresenter(ISetFileView view) {
        super(view);
    }

    /**
     * 获取表演style
     */
    public void requestStyle() {
        addSubscription(apiService.getPerformStyle(), new SubscriberCallBack<List<StyleModel>>(mView.get()) {
            @Override
            protected void onSuccess(List<StyleModel> response) {
                if (response != null && response.size() > 0) {
                    mView.showStylePop(response);
                } else {
                    UIUtils.showBaseToast("获取style失败，稍后再试");
                }
            }

            @Override
            protected void onFailure(ResultResponse response) {
            }
        });
    }
}
