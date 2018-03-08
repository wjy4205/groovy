package com.bunny.groovy.presenter;

import com.bunny.groovy.api.SubscriberCallBack;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.model.ResultResponse;
import com.bunny.groovy.utils.Utils;
import com.bunny.groovy.view.IMeView;

/**
 * 个人中心 控制器
 * Created by Administrator on 2017/12/21.
 */

public class VenueMePresenter extends BasePresenter<IMeView> {
    public VenueMePresenter(IMeView view) {
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
                        mView.setUserView(response);
                        Utils.initLoginData(mView.get(),response);
                    }

                    @Override
                    protected void onFailure(ResultResponse response) {

                    }
                });
    }

}
