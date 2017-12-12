package com.bunny.groovy.presenter;

import com.bunny.groovy.api.SubscriberCallBack;
import com.bunny.groovy.base.BaseApp;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.listener.MyVerificationListener;
import com.bunny.groovy.model.ResultResponse;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.utils.PatternUtils;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.view.ISingUpView;
import com.sinch.verification.Config;
import com.sinch.verification.PhoneNumberUtils;
import com.sinch.verification.SinchVerification;
import com.sinch.verification.Verification;
import com.sinch.verification.VerificationListener;
import com.socks.library.KLog;

import rx.Subscriber;

/**
 * 注册控制器
 * <p>
 * Created by Administrator on 2017/12/9.
 */

public class SingUpPresenter extends BasePresenter<ISingUpView> {

    private int type = 0;
    private VerificationListener mListener;
    private Verification mVerification;

    public SingUpPresenter(ISingUpView view) {
        super(view);
    }

    /**
     * 检查账户是否可用，和类型
     */
    public void checkAccount(final String account, final boolean next) {
        if (PatternUtils.isUSphonenumber(account) || PatternUtils.isCNPhone(account)) {
            //手机号
            type = AppConstants.ACCOUNT_TYPE_PHONE;
        } else if (PatternUtils.isValidEmail(account)) {
            //邮箱
            type = AppConstants.ACCOUNT_TYPE_EMAIL;
        } else {
            //不合法，提示用户
            mView.showCheckResult(false, type, "ACCOUNT INVALID!");
            return;
        }

        addSubscription(apiService.checkAccountUsed(account), new Subscriber<ResultResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                KLog.a(e.toString());
                UIUtils.showBaseToast(e.toString());
            }

            @Override
            public void onNext(ResultResponse result) {
                KLog.a("checkAccount", result.success);
                mView.showCheckResult(result.success, type, result.errorMsg);
                if (result.success && next) {
                    //根据类型发送验证码

                    //邮箱账户
                    if (type == AppConstants.ACCOUNT_TYPE_EMAIL)
                        addSubscription(apiService.getEmailCheckCode(account, AppConstants.USER_TYPE_MUSICIAN),
                                new SubscriberCallBack<ResultResponse>(mView.get()) {
                                    @Override
                                    protected void onSuccess(ResultResponse response) {
                                        UIUtils.showBaseToast("Code send to your E-mail successfully.");
                                        mView.nextStep();
                                    }

                                    @Override
                                    protected void onError() {

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

                        //手机账户
                    else if (type == AppConstants.ACCOUNT_TYPE_PHONE) {
                        sendPhoneCheckCode(account);
                    }
                }
            }
        });
    }

    /**
     * 检验邮箱验证码
     *
     * @param code
     */
    public void checkEmailCode(String code, String userAccount) {
        addSubscription(apiService.chekEmailCodeRegister(code, userAccount), new SubscriberCallBack<ResultResponse>(mView.get()) {
            @Override
            protected void onSuccess(ResultResponse response) {
                // TODO: 2017/12/12 注册账户

            }

            @Override
            protected void onError() {

            }

        });
    }

    //发送手机验证码
    private void sendPhoneCheckCode(String account) {
        if (mVerification == null) {
            Config config = SinchVerification.config().applicationKey(AppConstants.SINCH_APPKEY).context(BaseApp.getContext()).build();
            mListener = new MyVerificationListener();
            String defaultRegion = PhoneNumberUtils.getDefaultCountryIso(BaseApp.getContext());
            String phoneNumberInE164 = PhoneNumberUtils.formatNumberToE164(account, defaultRegion);
            mVerification = SinchVerification.createSmsVerification(config, phoneNumberInE164, mListener);
        }
        mVerification.initiate();
    }

    /**
     * 验证手机验证码
     *
     * @param code
     */
    public void checkPhoneCode(String code) {
        mVerification.verify(code);
    }


    /**
     * 表演者注册
     *
     * @param account
     * @param pwd
     * @param phone
     * @param email
     */
    public void register(String account, String pwd, String phone, String email) {
        addSubscription(apiService.performerRegister(account, pwd, phone, email), new SubscriberCallBack<ResultResponse>(mView.get()) {
            @Override
            protected void onSuccess(ResultResponse response) {

            }

            @Override
            protected void onError() {

            }

            @Override
            public void onNext(ResultResponse o) {

            }
        });
    }
}
