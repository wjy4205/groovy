package com.bunny.groovy.presenter;

import com.bunny.groovy.api.SubscriberCallBack;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.ResultResponse;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.view.ISettingView;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/12/22.
 ****************************************/

public class SettingsPresenter extends BasePresenter<ISettingView> {

    public SettingsPresenter(ISettingView view) {
        super(view);
    }

    /**
     * 登出
     */
    public void logout() {
        addSubscription(apiService.loginOut(AppCacheData.getPerformerUserModel().getUserID()),
                new SubscriberCallBack(mView.get()) {

                    @Override
                    protected boolean isShowProgress() {
                        return true;
                    }

                    @Override
                    protected void onSuccess(Object response) {
                        mView.loginOut();
                    }

                    @Override
                    protected void onFailure(ResultResponse response) {

                    }
                });
    }
}
