package com.bunny.groovy.presenter;

import com.bunny.groovy.R;
import com.bunny.groovy.api.SubscriberCallBack;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.model.ResultResponse;
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
                            String level = (String) SharedPreferencesUtils.getParam(mView.get(),
                                    AppConstants.KEY_USERFILE_LEVEL, AppConstants.USERFILE_LEVLE_NONE);
                            switch (level) {
                                case AppConstants.USERFILE_LEVLE_FULL:
                                    //资料完善进入首页
                                    UIUtils.showBaseToast(UIUtils.getString(R.string.perfect_info));
                                    mView.loginSuccess(response);
                                    break;
                                case AppConstants.USERFILE_LEVLE_FIRST:
                                    UIUtils.showBaseToast(UIUtils.getString(R.string.perfect_info));
                                    mView.launchSecondPage();
                                    break;
                                case AppConstants.USERFILE_LEVLE_SECOND:
                                    UIUtils.showBaseToast(UIUtils.getString(R.string.perfect_info));
                                    mView.launchThirdPage();
                                    break;
                                default:
                                case AppConstants.USERFILE_LEVLE_NONE:
                                    mView.launchFirstPage();
                                    break;
                            }
                        }
                    }

                    @Override
                    protected void onFailure(ResultResponse response) {
                        super.onFailure(response);
                    }

                    @Override
                    protected boolean isShowProgress() {
                        return true;
                    }
                });
    }
}
