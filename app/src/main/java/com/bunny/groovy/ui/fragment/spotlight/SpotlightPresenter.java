package com.bunny.groovy.ui.fragment.spotlight;

import com.bunny.groovy.api.SubscriberCallBack;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.model.ResultResponse;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.utils.Utils;

import java.util.Map;

/****************************************
 * 功能说明:推广控制器
 *
 * Author: Created by bayin on 2018/1/11.
 ****************************************/

public class SpotlightPresenter extends BasePresenter<ISpotLightView> {

    public SpotlightPresenter(ISpotLightView view) {
        super(view);
    }

    public void buySpotLight(Map<String, String> map) {
        addSubscription(apiService.buySpotlight(map), new SubscriberCallBack(mView.get()) {
            @Override
            protected void onSuccess(Object response) {
                UIUtils.showBaseToast("success");
                //更新用户数据
                updateUserData();

            }

            @Override
            protected void onFailure(ResultResponse response) {

            }
        });
    }


    private void updateUserData() {
        addSubscription(Utils.parseInt(AppCacheData.getPerformerUserModel().getUserType()) == AppConstants.USER_TYPE_MUSICIAN
                ? apiService.getPerformerInfo() : apiService.getVenueDetailInfo(), new SubscriberCallBack<PerformerUserModel>(mView.get()) {
            @Override
            protected void onSuccess(PerformerUserModel response) {
                try {
                    AppCacheData.setPerformerUserModel(response);
                    mView.get().finish();
                }catch (Exception e){}

            }

            @Override
            protected void onFailure(ResultResponse response) {

            }

            @Override
            protected boolean isShowProgress() {
                return true;
            }
        });
    }
}
