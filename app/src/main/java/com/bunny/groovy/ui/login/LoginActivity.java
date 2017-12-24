package com.bunny.groovy.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseActivity;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.presenter.LoginPresenter;
import com.bunny.groovy.ui.MainActivity;
import com.bunny.groovy.ui.setfile.SetFile1Activity;
import com.bunny.groovy.ui.setfile.SetFile2Activity;
import com.bunny.groovy.ui.setfile.SetFile3Activity;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.utils.PatternUtils;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.view.ILoginView;
import com.xw.repo.XEditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 表演者登陆页面
 * <p>
 * Created by Administrator on 2017/12/5.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView {
    @Bind(R.id.login_et_account)
    XEditText etPhoneOrEmail;
    @Bind(R.id.login_et_password)
    XEditText etPassword;

    @OnClick(R.id.tv_sign_up)
    void signUp() {
        startActivityForResult(new Intent(this, SignUpActivity.class), 2);
    }

    @OnClick(R.id.tv_musician_login)
    void login() {
        //拦截非法输入
        if (TextUtils.isEmpty(etPhoneOrEmail.getTrimmedString())) {
            UIUtils.showBaseToast(getString(R.string.account_not_be_null));
            return;
        }
        if (!(PatternUtils.isUSphonenumber(etPhoneOrEmail.getTrimmedString())
                || PatternUtils.isCNPhone(etPhoneOrEmail.getTrimmedString())
                || PatternUtils.isValidEmail(etPhoneOrEmail.getTrimmedString()))) {
            //不是email也不是手机
            UIUtils.showBaseToast(getString(R.string.invalid_account));
            return;
        }
        if (TextUtils.isEmpty(etPassword.getTrimmedString())) {
            UIUtils.showBaseToast(getString(R.string.account_not_be_null));
            return;
        } else if (etPassword.getTrimmedString().length() < 8) {
            UIUtils.showBaseToast(getString(R.string.invalid_password));
            return;
        }

        //通过验证,登录
        mPresenter.login(etPhoneOrEmail.getTrimmedString(), etPassword.getTrimmedString());
    }

    @OnClick(R.id.tv_forget_password)
    void forgetPassword() {

    }

    public static void launch(Context activity) {
        activity.startActivity(new Intent(activity, LoginActivity.class));
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_login_layout;
    }

    @Override
    public void loginSuccess(PerformerUserModel userModel) {

    }

    @Override
    public Activity get() {
        return getCurrentActivity();
    }


    @Override
    public void launchMainPage() {
        //登录成功，进入主页，结束登录页面
        MainActivity.launch(this);
    }

    @Override
    public void launchToSetFile() {
        startActivityForResult(new Intent(this, SetFile1Activity.class), AppConstants.REQUESTCODE_SETFILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //设置资料结束，结束本页面,跳转至首页
        if (requestCode == AppConstants.REQUESTCODE_SETFILE && resultCode == AppConstants.ACTIVITY_FINISH) {
           launchMainPage();
        }
    }
}
