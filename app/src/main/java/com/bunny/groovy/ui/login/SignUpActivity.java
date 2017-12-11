package com.bunny.groovy.ui.login;

import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseActivity;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.presenter.SingUpPresenter;
import com.bunny.groovy.view.ISingUpView;
import com.xw.repo.XEditText;

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

    //下一步
    @OnClick(R.id.tv_signup_next)
    void next() {
    }

    //登陆
    @OnClick(R.id.tv_signup_login)
    void login() {
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
        etPhoneEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String account = etPhoneEmail.getText().toString();
                if (!hasFocus&&!TextUtils.isEmpty(account)){
                    //检查账号是否占用
                    mPresenter.checkAccount(account);
                }
            }
        });
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
