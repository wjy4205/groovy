package com.bunny.groovy.presenter;

import com.bunny.groovy.api.SubscriberCallBack;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.model.ResultResponse;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.view.IMeView;

/**
 * 个人中心 控制器
 * Created by Administrator on 2017/12/21.
 */

public class MePresenter extends BasePresenter<IMeView> {
    public MePresenter(IMeView view) {
        super(view);
    }

    /**
     * 获取用户数据
     */
    public void requestUserData() {
        addSubscription(apiService.getPerformerInfo(AppCacheData.getPerformerUserModel().getUserID()),
                new SubscriberCallBack<PerformerUserModel>(mView.get()) {
            @Override
            protected void onSuccess(PerformerUserModel response) {
                mView.setUserView(response);
            }

            @Override
            protected void onFailure(ResultResponse response) {

            }
        });
    }

}
