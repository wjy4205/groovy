package com.bunny.groovy.presenter;

import android.content.Intent;
import android.text.TextUtils;

import com.bunny.groovy.R;
import com.bunny.groovy.api.SubscriberCallBack;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.model.ResultResponse;
import com.bunny.groovy.ui.MainActivity;
import com.bunny.groovy.ui.setfile.SetFile1Activity;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.utils.SharedPreferencesUtils;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.utils.Utils;
import com.bunny.groovy.view.ILoginView;

/****************************************
 * 功能说明:  登录控制器
 *
 * Author: Created by bayin on 2017/12/13.
 ****************************************/

public class LoginPresenter extends BasePresenter<ILoginView> {
    public LoginPresenter(ILoginView view) {
        super(view);
    }

    public void login(String account, String password) {
        addSubscription(apiService.performerLogin(account, password, AppConstants.USER_TYPE_MUSICIAN, Utils.getTimeZone()),
                new SubscriberCallBack<PerformerUserModel>(mView.get()) {
                    /**
                     * @param response
                     */
                    @Override
                    protected void onSuccess(PerformerUserModel response) {
                        //缓存到本地
                        Utils.initLoginData(mView.get(), response);
                        if (response != null) {
                            //判断资料是否完善
                            if (TextUtils.isEmpty(response.getZipCode())) {
                                //需要完善信息
                                mView.get().startActivityForResult(new Intent(mView.get(), SetFile1Activity.class),2);
                            } else {
                                //进入主页
                                mView.loginSuccess(response);
                            }
                        }
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
