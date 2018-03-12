package com.bunny.groovy.presenter;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;

import com.bunny.groovy.api.SubscriberCallBack;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.manager.LoginBlock;
import com.bunny.groovy.model.GlobalModel;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.model.ResultResponse;
import com.bunny.groovy.ui.MainActivity;
import com.bunny.groovy.ui.login.LoginActivity;
import com.bunny.groovy.ui.setfile.SetFile1Activity;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.utils.Utils;
import com.bunny.groovy.view.ISplashView;

import javax.microedition.khronos.opengles.GL;

/****************************************
 * 功能说明: 欢迎页的控制器
 *
 * Author: Created by bayin on 2017/12/15.
 ****************************************/

public class SplashPresenter extends BasePresenter<ISplashView> {
    public SplashPresenter(ISplashView view) {
        super(view);
    }

    private long startRequestTime = 0;
    private long endRequestTime = 0;

    public void requestUserInfo(final int userType) {
        startRequestTime = SystemClock.currentThreadTimeMillis();
        addSubscription(userType == AppConstants.USER_TYPE_MUSICIAN
                        ? apiService.getPerformerInfo() : (userType == AppConstants.USER_TYPE_NORMAL
                        ? apiService.getUserInfo() : apiService.getVenueDetailInfo())
                , new SubscriberCallBack<PerformerUserModel>(mView.get()) {
                    @Override
                    protected void onSuccess(final PerformerUserModel response) {
                        endRequestTime = SystemClock.currentThreadTimeMillis();
                        long delta = endRequestTime - startRequestTime;
                        if (delta / 1000 < 3000)
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //缓存数据
                                    Utils.initLoginData(mView.get(), response);
                                    //判断资料是否完善
                                    if (Utils.parseInt(response.getUserType()) == AppConstants.USER_TYPE_MUSICIAN
                                            && TextUtils.isEmpty(response.getZipCode())) {
                                        //需要完善信息
                                        int type = Integer.parseInt(response.getUserType());
                                        LoginActivity.launch(mView.get(), type);
//                                mView.get().startActivity(new Intent(mView.get(), SetFile1Activity.class));
                                    } else {
                                        LoginBlock.getInstance().handleCheckSuccess(response.getUserType());
                                    }
                                    mView.get().finish();
                                }
                            }, 3000 - (delta / 1000));
                        //获取全局参数
                        getGlobParam();
                    }

                    @Override
                    protected void onFailure(ResultResponse response) {
                        mView.requestFailed();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.requestFailed();
                    }
                });
    }


    public void getGlobParam() {
        //获取全局参数
        addSubscription(apiService.getGlobalParam(), new SubscriberCallBack<GlobalModel>(null) {
            @Override
            protected void onSuccess(GlobalModel response) {
                AppCacheData.setGlobalModel(response);
            }

            @Override
            protected void onFailure(ResultResponse response) {

            }
        });
    }
}
