package com.bunny.groovy.ui.login;

import android.content.Intent;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseActivity;
import com.bunny.groovy.presenter.SingUpPresenter;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.view.ISingUpView;
import com.xw.repo.XEditText;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 注册第二步页面
 * <p>
 * Created by Administrator on 2017/12/11.
 */

public class SignUp2Activity extends BaseActivity<SingUpPresenter> implements ISingUpView {
    public static String KEY_ACCOUNT = "key_account";
    private String mAccount;
    public static String KEY_TYPE = "key_type";
    private int mType;

    @Bind(R.id.signup_et_code)
    XEditText etCode;
    @Bind(R.id.signup_et_phone)
    XEditText etPhone;
    @Bind(R.id.signup_et_email)
    XEditText etEmail;
    @OnClick(R.id.bt_sign_up)
    void signUp(){

    }

    @Override
    public void initView() {
        super.initView();
        Intent intent = getIntent();
        if (intent != null) {
            mAccount = intent.getStringExtra(KEY_ACCOUNT);
            mType = intent.getIntExtra(KEY_TYPE,0);
        } else finish();

        //set view
        if (AppConstants.ACCOUNT_TYPE_EMAIL == mType){
            etEmail.setText(mAccount);
            etEmail.setFocusable(false);
        }else if (AppConstants.ACCOUNT_TYPE_PHONE == mType){
            etPhone.setText(mAccount);
            etPhone.setFocusable(false);
        }

    }

    @Override
    protected SingUpPresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_register_second_layout;
    }

    @Override
    public void showCheckResult(boolean invalid, int accountType, String msg) {

    }

    @Override
    public void nextStep() {

    }
}
