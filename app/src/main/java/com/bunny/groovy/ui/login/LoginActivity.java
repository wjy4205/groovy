package com.bunny.groovy.ui.login;

import android.content.Intent;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseActivity;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.presenter.SingUpPresenter;
import com.xw.repo.XEditText;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 表演者登陆页面
 *
 * Created by Administrator on 2017/12/5.
 */

public class LoginActivity extends BaseActivity {
    @Bind(R.id.et_phone_or_email)
    XEditText etPhoneOrEmail;
    @Bind(R.id.et_password)
    XEditText etPassword;
    @OnClick(R.id.tv_sign_up)
    void signUp(){
        startActivity(new Intent(this, SignUpActivity.class));
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_login_layout;
    }
}
