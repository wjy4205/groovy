package com.bunny.groovy.presenter;

import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.BooleanBean;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.utils.PatternUtils;
import com.bunny.groovy.view.ISingUpView;
import com.socks.library.KLog;

import rx.Subscriber;

/**
 * 注册控制器
 * <p>
 * Created by Administrator on 2017/12/9.
 */

public class SingUpPresenter extends BasePresenter<ISingUpView> {

    private int type = 0;

    public SingUpPresenter(ISingUpView view) {
        super(view);
    }

    /**
     * 检查账户是否可用，和类型
     */
    public void checkAccount(String account, final boolean next) {
        if (PatternUtils.isUSphonenumber(account)) {
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

        addSubscription(apiService.checkAccountUsed(account), new Subscriber<BooleanBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                KLog.a(e.toString());
            }

            @Override
            public void onNext(BooleanBean result) {
                KLog.a("checkAccount", result.isSuccess(), result.getResultData());
                mView.showCheckResult(result.isSuccess(), type, result.getErrorMsg());
                if (result.isSuccess() && next) {
                    mView.nextStep();
                }
            }
        });
    }
}
