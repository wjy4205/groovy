package com.bunny.groovy.presenter;

import com.bunny.groovy.api.SubscriberCallBack;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.NextShowModel;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.model.ResultResponse;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.utils.Utils;
import com.bunny.groovy.view.IOverView;

import java.util.List;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/12/15.
 ****************************************/

public class OverviewPresenter extends BasePresenter<IOverView> {
    public OverviewPresenter(IOverView view) {
        super(view);
    }

    public void requestNextShow() {
        String userID = AppCacheData.getPerformerUserModel().getUserID();
        addSubscription(apiService.getNextShow(userID), new SubscriberCallBack<List<NextShowModel>>(null) {
            @Override
            protected void onSuccess(List<NextShowModel> response) {
                if (response != null && response.size() > 0) {
                    mView.initNextView(response.get(0));
                } else {
                    mView.showEmptyNextShow();
                }
            }

            @Override
            protected void onFailure(ResultResponse response) {
                mView.showEmptyNextShow();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.showEmptyNextShow();
            }
        });
    }

    /**
     * 获取user数据
     */
    public void requestUserData() {
        addSubscription(apiService.getPerformerInfo(AppCacheData.getPerformerUserModel().getUserID()), new SubscriberCallBack<PerformerUserModel>(mView.get()) {
            @Override
            protected void onSuccess(PerformerUserModel response) {
                //缓存到本地
                Utils.initLoginData(mView.get(), response);
                //设置数据
                mView.setView(response);
            }

            @Override
            protected void onFailure(ResultResponse response) {

            }
        });
    }
}
