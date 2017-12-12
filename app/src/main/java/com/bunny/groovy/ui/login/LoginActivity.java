package com.bunny.groovy.ui.login;

import android.content.Intent;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseActivity;
import com.bunny.groovy.base.BasePresenter;
import com.xw.repo.XEditText;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 表演者登陆页面
 * <p>
 * Created by Administrator on 2017/12/5.
 */

public class LoginActivity extends BaseActivity {
    @Bind(R.id.login_et_account)
    XEditText etPhoneOrEmail;
    @Bind(R.id.login_et_password)
    XEditText etPassword;

    @OnClick(R.id.tv_sign_up)
    void signUp() {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    @OnClick(R.id.tv_musician_login)
    void testSet() {

    }

    @OnClick(R.id.tv_forget_password)
    void testGet() {

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
