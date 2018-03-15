package com.bunny.groovy.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.bunny.groovy.api.SubscriberCallBack;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.model.ResultResponse;
import com.bunny.groovy.ui.fragment.spotlight.ISpotLightView;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.utils.Utils;
import com.bunny.groovy.view.IRewardView;

import java.util.Map;

/****************************************
 * 功能说明:打赏控制器
 *
 * Author: Created by bayin on 2018/1/11.
 ****************************************/

public class RewardPresenter extends BasePresenter<IRewardView> {

    public RewardPresenter(IRewardView view) {
        super(view);
    }

    /**
     * 获取用户数据
     */
    public void requestUserData() {
        addSubscription(apiService.getVenueDetailInfo(),
                new SubscriberCallBack<PerformerUserModel>(mView.get()) {
                    @Override
                    protected void onSuccess(PerformerUserModel response) {
                        Utils.initLoginData(mView.get(), response);
                    }

                    @Override
                    protected void onFailure(ResultResponse response) {

                    }
                });
    }

    //用余额打赏
    public void rewardPerformer(Map<String, String> map) {
        addSubscription(apiService.rewardPerformer(map), new SubscriberCallBack(mView.get()) {
            @Override
            protected void onSuccess(Object response) {
                UIUtils.showBaseToast("reward success.");
                mView.get().finish();
            }

            @Override
            protected void onFailure(ResultResponse response) {

            }
        });
    }

    /**
     * （获取token）Paypal
     */
    public void getToken() {
        addSubscription(apiService.getPaypalToken(AppCacheData.getPerformerUserModel().getUserID()), new SubscriberCallBack<String>(mView.get()) {
            @Override
            protected boolean isShowProgress() {
                return true;
            }

            @Override
            protected void onSuccess(String response) {
                if (!TextUtils.isEmpty(response)) {
                    //调用paypal api
                    mView.onTokenGet(response);
                }
            }

            @Override
            protected void onFailure(ResultResponse response) {

            }
        });
    }
}
