package com.bunny.groovy.presenter;

import android.content.Intent;
import android.text.TextUtils;

import com.bunny.groovy.api.SubscriberCallBack;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.model.ResultResponse;
import com.bunny.groovy.ui.MainActivity;
import com.bunny.groovy.ui.setfile.SetFile1Activity;
import com.bunny.groovy.utils.Utils;
import com.bunny.groovy.view.ISplashView;

/****************************************
 * 功能说明: 欢迎页的控制器
 *
 * Author: Created by bayin on 2017/12/15.
 ****************************************/

public class SplashPresenter extends BasePresenter<ISplashView> {
    public SplashPresenter(ISplashView view) {
        super(view);
    }

    public void requestPerformerInfo(String userID) {
        addSubscription(apiService.getPerformerInfo(userID), new SubscriberCallBack<PerformerUserModel>(mView.get()) {
            @Override
            protected void onSuccess(PerformerUserModel response) {
                //缓存数据
                Utils.initLoginData(mView.get(), response);
                //判断资料是否完善
                if (TextUtils.isEmpty(response.getZipCode())) {
                    //需要完善信息
                    mView.get().startActivity(new Intent(mView.get(), SetFile1Activity.class));
                } else {
                    //进入主页
                    MainActivity.launch(mView.get());
                }
                mView.get().finish();
            }

            @Override
            protected void onFailure(ResultResponse response) {
                super.onFailure(response);
                mView.requestFailed();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.requestFailed();
            }
        });
    }
}
