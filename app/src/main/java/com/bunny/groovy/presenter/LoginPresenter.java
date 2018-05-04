package com.bunny.groovy.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.bunny.groovy.api.SubscriberCallBack;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.listener.VerifyEvent;
import com.bunny.groovy.model.GlobalModel;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.model.ResultResponse;
import com.bunny.groovy.ui.login.BindAccountFragment;
import com.bunny.groovy.ui.login.VenueRegister1Activity;
import com.bunny.groovy.ui.setfile.SetFile1Activity;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.utils.PatternUtils;
import com.bunny.groovy.utils.SharedPreferencesUtils;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.utils.Utils;
import com.bunny.groovy.view.ILoginView;

import org.greenrobot.eventbus.EventBus;

/****************************************
 * 功能说明:  登录控制器
 *
 * Author: Created by bayin on 2017/12/13.
 ****************************************/

public class LoginPresenter extends BasePresenter<ILoginView> {
    public LoginPresenter(ILoginView view) {
        super(view);
    }

    public void login(final String account, final String password, final int type) {
        addSubscription(apiService.performerLogin(account, password, String.valueOf(type), Utils.getTimeZone()),
                new SubscriberCallBack<PerformerUserModel>(mView.get()) {
                    /**
                     * @param response
                     */
                    @Override
                    protected void onSuccess(PerformerUserModel response) {
                        //保存用户账号密码，用于切换时自动登录
                        SharedPreferencesUtils.setAppParam(mView.get(), AppConstants.KEY_HISTORY_ACCOUNT_BY_TYPE + type, account);
                        SharedPreferencesUtils.setUserParam(mView.get(), AppConstants.KEY_ACCOUNT + response.getUserID(), account);
                        SharedPreferencesUtils.setUserParam(mView.get(), AppConstants.KEY_PASSWORD + response.getUserID(), password);
                        //坑爹服务器，登录之后类型还是原来的，只能自己转
                        response.setUserType(String.valueOf(type));
                        //缓存到本地
                        Utils.initLoginData(mView.get(), response);
                        if (response != null) {
                            //获取全局参数
                            getGlobParam();
                            //判断资料是否完善
                            int userType = Utils.parseInt(response.getUserType());
                            //需要完善信息
                            if (userType == AppConstants.USER_TYPE_MUSICIAN
                                    && TextUtils.isEmpty(response.getZipCode())) {
                                mView.get().startActivityForResult(new Intent(mView.get(), SetFile1Activity.class), AppConstants.REQUESTCODE_SETFILE);
                            } else if (userType == AppConstants.USER_TYPE_VENUE
                                    && TextUtils.isEmpty(response.getVenueAddress())) {
                                mView.get().startActivityForResult(new Intent(mView.get(), VenueRegister1Activity.class), AppConstants.REQUESTCODE_SETFILE);
                            } else {
                                //进入主页
                                mView.launchMainPage(userType);
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

    /**
     * 检查是否绑定该id
     *
     * @param loginType 类型
     * @param uid       账户
     */
    public void checkHadBindUid(final String loginType, final String uid, final String username, final String userType) {
        addSubscription(apiService.checkUidNotLogin(loginType, userType, uid, Utils.getTimeZone()), new SubscriberCallBack<PerformerUserModel>(mView.get()) {
            @Override
            protected void onSuccess(PerformerUserModel response) {
                //已经绑定，直接登录
                if (response != null) {
                    response.setUserType(userType);
                    Utils.initLoginData(mView.get(), response);
                    //获取全局参数
                    getGlobParam();
                    //判断资料是否完善
                    int type = Utils.parseInt(userType);
                    if (type == AppConstants.USER_TYPE_MUSICIAN
                            && TextUtils.isEmpty(response.getZipCode())) {
                        mView.get().startActivityForResult(new Intent(mView.get(), SetFile1Activity.class), AppConstants.REQUESTCODE_SETFILE);
                    } else if (type == AppConstants.USER_TYPE_VENUE
                            && TextUtils.isEmpty(response.getVenueAddress())) {
                        mView.get().startActivityForResult(new Intent(mView.get(), VenueRegister1Activity.class), AppConstants.REQUESTCODE_SETFILE);
                    } else {
                        //进入主页
                        mView.launchMainPage(type);
                    }
                } else {
                    //未绑定任何账户，跳转到绑定账户页面
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    bundle.putString("logintype", loginType);
                    bundle.putString("uid", uid);
                    bundle.putString("userType", userType);
                    BindAccountFragment.launch(mView.get(), bundle);
                }
            }

            @Override
            protected void onFailure(ResultResponse response) {
                //已绑定其他类型账户
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                bundle.putString("logintype", loginType);
                bundle.putString("uid", uid);
                bundle.putString("userType", userType);
                BindAccountFragment.launch(mView.get(), bundle);
            }

            @Override
            protected boolean isShowProgress() {
                return true;
            }
        });
    }

    /**
     * 发送邮件验证码
     *
     * @param email
     */
    public void socialSendEmailCode(String email) {
        addSubscription(apiService.socialSendEmailCode(email), new SubscriberCallBack(mView.get()) {
            @Override
            protected void onSuccess(Object response) {
                UIUtils.showBaseToast("Send Success.");
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

    /**
     * loginType
     * uid
     * userName
     * userAccount
     * userType
     * userZone
     */
    public void socialLogin(String logintype, String uid, String username, String useraccount, final String userType) {
        addSubscription(apiService.socialAccountLogin(logintype, uid, username,
                useraccount, userType, Utils.getTimeZone()), new SubscriberCallBack<PerformerUserModel>(mView.get()) {
            @Override
            protected boolean isShowProgress() {
                return true;
            }

            @Override
            protected void onSuccess(PerformerUserModel response) {
                //登录，判断是否完善了资料
                response.setUserType(userType);
                Utils.initLoginData(mView.get(), response);
                int type = Utils.parseInt(userType);
                if (type == AppConstants.USER_TYPE_MUSICIAN
                        && TextUtils.isEmpty(response.getZipCode())) {
                    mView.get().startActivityForResult(new Intent(mView.get(), SetFile1Activity.class), AppConstants.REQUESTCODE_SETFILE);
                } else if (type == AppConstants.USER_TYPE_VENUE
                        && TextUtils.isEmpty(response.getVenueAddress())) {
                    mView.get().startActivityForResult(new Intent(mView.get(), VenueRegister1Activity.class), AppConstants.REQUESTCODE_SETFILE);
                } else {
                    //进入主页
                    mView.launchMainPage(type);
                }
            }

            @Override
            protected void onFailure(ResultResponse response) {

            }
        });
    }

    /**
     * 检查账户是否可用，和类型
     */
    public void checkAccount(final String account) {
        if (PatternUtils.isUSphonenumber(account) || PatternUtils.isCNPhone(account)) {
            //手机号
            VerifyEvent.initSinch(mView.get(), account);
        } else if (PatternUtils.isValidEmail(account)) {
            //邮箱
            addSubscription(apiService.getEmailCheckCode(account, String.valueOf(AppConstants.USER_TYPE_MUSICIAN)),
                    new SubscriberCallBack<ResultResponse>(mView.get()) {
                        @Override
                        protected void onSuccess(ResultResponse response) {
                            UIUtils.showBaseToast("Code send to your E-mail successfully.");
                        }

                        @Override
                        protected void onFailure(ResultResponse response) {

                        }

                        @Override
                        protected boolean isShowProgress() {
                            return true;
                        }
                    });
        } else {
            //不合法，提示用户
            UIUtils.showBaseToast("Invalid phone or email!");
            return;
        }
    }

    /**
     * 检验邮箱验证码
     *
     * @param code
     */
    public void checkEmailCode(String code, final String userAccount) {
        addSubscription(apiService.chekEmailCodeRegister(code, userAccount), new SubscriberCallBack<ResultResponse>(mView.get()) {
            @Override
            protected void onSuccess(ResultResponse response) {
                EventBus.getDefault().post(AppConstants.Code_Verify_Correct);
            }

            @Override
            protected void onFailure(ResultResponse response) {
                EventBus.getDefault().post(AppConstants.Code_Verify_Invalid);
            }
        });
    }


}
