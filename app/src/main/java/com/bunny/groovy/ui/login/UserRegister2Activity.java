package com.bunny.groovy.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseActivity;
import com.bunny.groovy.listener.VerifyEvent;
import com.bunny.groovy.presenter.SingUpPresenter;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.view.ISingUpView;
import com.xw.repo.XEditText;

import org.greenrobot.eventbus.Subscribe;

import java.net.URLEncoder;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 注册第二步页面
 * <p>
 * Created by Administrator on 2017/12/11.
 */

public class UserRegister2Activity extends BaseActivity<SingUpPresenter> implements ISingUpView {
    public final static String KEY_ACCOUNT = "key_account";
    private String mAccount;
    public final static String KEY_TYPE = "key_type";
    private int mType;
    public final static String KEY_PASSWORD = "key_password";
    private String mPassword;
    public final static String KEY_NAME = "key_name";
    private String mName;
    @Bind(R.id.signup_et_code)
    XEditText etCode;

    @OnClick(R.id.bt_sign_up)
    void signUp() {
        //验证码为空
        if (TextUtils.isEmpty(etCode.getTrimmedString())) {
            UIUtils.showBaseToast("Code must not be null.");
            return;
        }

        if (mType == AppConstants.ACCOUNT_TYPE_PHONE) {
            VerifyEvent.verifyCode(etCode.getTrimmedString());
        } else if (mType == AppConstants.ACCOUNT_TYPE_EMAIL) {
            mPresenter.checkEmailCode(etCode.getTrimmedString(), URLEncoder.encode(mAccount));
        }
    }

    /**
     * 验证码结果回调
     *
     * @param result 结果
     */
    @Subscribe
    public void onVerifyEvent(String result) {
        switch (result) {
            case AppConstants.Code_Verify_Correct:
                HashMap<String, String> map = new HashMap<>();
                map.put("userName", mName);
                map.put("userPwd", mPassword);
                if (mType == AppConstants.ACCOUNT_TYPE_PHONE) {
                    map.put("telephone", mAccount);
                } else {
                    map.put("userEmail", mAccount);
                }
                mPresenter.registerUser(map);
                break;
            case AppConstants.Code_Verify_Invalid:
                UIUtils.showBaseToast("验证码不正确");
                break;
            case AppConstants.Code_Send_ServerError:
                UIUtils.showBaseToast("服务器出错");
                break;
            default:
                break;
        }
    }

    @Override
    public void initView() {
        super.initView();
        Intent intent = getIntent();
        if (intent != null) {
            mAccount = intent.getStringExtra(KEY_ACCOUNT);
            mType = intent.getIntExtra(KEY_TYPE, 0);
            mPassword = intent.getStringExtra(KEY_PASSWORD);
            mName = intent.getStringExtra(KEY_NAME);
        } else finish();

    }

    @Override
    public void initListener() {
        super.initListener();
        registerEventBus(this);
    }

    @Override
    protected SingUpPresenter createPresenter() {
        return new SingUpPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_user_register_second_layout;
    }

    @Override
    public void showCheckResult(boolean invalid, int accountType, String msg) {

    }

    @Override
    public void nextStep() {

    }

    @Override
    public Activity get() {
        return getCurrentActivity();
    }

    /**
     * 注册成功，返回登录页面
     */
    @Override
    public void registerSuccess() {
//        setResult(AppConstants.ACTIVITY_FINISH);
//        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }
}
