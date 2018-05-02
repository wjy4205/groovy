package com.bunny.groovy.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseActivity;
import com.bunny.groovy.presenter.SingUpPresenter;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.view.ISingUpView;
import com.xw.repo.XEditText;

import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 表演者注册页面
 * <p>
 * Created by Administrator on 2017/12/9.
 */

public class SignUpActivity extends BaseActivity<SingUpPresenter> implements ISingUpView {
    @Bind(R.id.et_signup_phone_or_email)
    XEditText etPhoneEmail;
    @Bind(R.id.et_signup_password)
    XEditText etPassword;
    @Bind(R.id.et_signup_password_again)
    XEditText etPasswordAgain;

    private int mAccountType = 0;//账号类型
    private WeakReference<Activity> mWeakReference = new WeakReference<Activity>(this);

    //下一步
    @OnClick(R.id.tv_signup_next)
    void next() {
        //检查账户
        String account = etPhoneEmail.getTrimmedString();
        if (TextUtils.isEmpty(account)) {
            UIUtils.showBaseToast("Please input email or phone.");
            return;
        }
        String pwd = etPassword.getTrimmedString();
        String pwdAgain = etPasswordAgain.getTrimmedString();
        if (TextUtils.isEmpty(pwd)) {
            UIUtils.showBaseToast("Please input Password.");
            return;
        } else if (pwd.length() < 8) {
            UIUtils.showBaseToast("The password length less than 8.");
            return;
        } else if (TextUtils.isEmpty(pwdAgain)) {
            UIUtils.showBaseToast("Please input password again.");
            return;
        } else if (!pwd.equals(pwdAgain)) {
            UIUtils.showBaseToast("The password entered twice is not the same.");
            return;
        }

        mPresenter.checkAccount(account, true);

    }

    //登陆
    @OnClick(R.id.tv_signup_login)
    void login() {
        finish();
    }

    @Override
    protected SingUpPresenter createPresenter() {
        return new SingUpPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_register_first_layout;
    }

    @Override
    public void initListener() {
        super.initListener();
        //账户输入框的监听
//        etPhoneEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                String account = etPhoneEmail.getText().toString();
//                if (!hasFocus && !TextUtils.isEmpty(account)) {
//                    //检查是否合法
//                    mPresenter.checkAccount(account, false);
//                }
//            }
//        });
        //event bus
        registerEventBus(this);
    }


    @Subscribe
    public void onRecevieSms(String code) {
        switch (code) {
            case AppConstants.Code_Send_Success://发送成功，跳转到下一个页面
                nextStep();
                break;
            case AppConstants.Code_Send_InvalidPhone://发送失败
                UIUtils.showBaseToast("Phone number invalid.");
                break;
            case AppConstants.Code_Send_ServerError:
                UIUtils.showBaseToast("Server error!");
                break;
            default:
                break;
        }
    }

    @Override
    public void showCheckResult(boolean invalid, int AccountType, String msg) {
        mAccountType = AccountType;
        if (invalid) etPhoneEmail.setCheckStatus(XEditText.CheckStatus.CORRECT);
        else {
            etPhoneEmail.setCheckStatus(XEditText.CheckStatus.INVALID);
            UIUtils.showBaseToast(msg);
        }
    }

    @Override
    public void nextStep() {
        Intent intent = new Intent();
        intent.putExtra(SignUp2Activity.KEY_ACCOUNT, etPhoneEmail.getTrimmedString());
        intent.putExtra(SignUp2Activity.KEY_TYPE, mAccountType);
        intent.putExtra(SignUp2Activity.KEY_PASSWORD, etPassword.getTrimmedString());
        intent.setClass(this, SignUp2Activity.class);
        startActivityForResult(intent, 2);
    }

    @Override
    public Activity get() {
        return mWeakReference.get();
    }

    @Override
    public void registerSuccess() {
        //第二步才用到
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == AppConstants.ACTIVITY_FINISH) {
            finish();
        }
    }
}
