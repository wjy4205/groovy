package com.bunny.groovy.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseActivity;
import com.bunny.groovy.listener.VerifyEvent;
import com.bunny.groovy.presenter.SingUpPresenter;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.utils.PatternUtils;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.view.ISingUpView;
import com.xw.repo.XEditText;

import org.greenrobot.eventbus.Subscribe;

import java.net.URLEncoder;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mysty on 2018/2/26.
 */

public class VenueRegister2Activity extends BaseActivity<SingUpPresenter> implements ISingUpView {

    public static String KEY_ACCOUNT = "key_account";
    private String mAccount;
    public static String KEY_TYPE = "key_type";
    private int mType;
    public static String KEY_PASSWORD = "key_password";
    private String mPassword;
    public static String KEY_PUBLIC_NAME = "key_public_name";
    private String mPublicName;
    public static String KEY_ADDRESS = "key_address";
    private String mAddress;
    public static String KEY_HEAD_URL = "key_head_url";
    private String mHeadUrl;
    @Bind(R.id.et_venue_code)
    XEditText mEditCode;
    @Bind(R.id.et_venue_booking_phone)
    XEditText mEditPhone;
    @Bind(R.id.et_venue_booking_email)
    XEditText mEditEmail;
    @Bind(R.id.et_venue_service)
    XEditText mVenueService;
    @Bind(R.id.et_venue_phone)
    XEditText mVenuePhone;
    @Bind(R.id.et_venue_website)
    XEditText mVenueWebsite;

    private CheckBox[] checkBoxs = new CheckBox[3];
    private PopupWindow popupWindow;


    public static void start(Context outerContext, int userType) {
        Intent intent = new Intent(outerContext, VenueRegister2Activity.class);
        intent.putExtra(VenueRegister2Activity.KEY_ACCOUNT, "18068833520");
        intent.putExtra(VenueRegister2Activity.KEY_TYPE, userType);
        intent.putExtra(VenueRegister2Activity.KEY_PASSWORD, "123456789");
        outerContext.startActivity(intent);
    }

    @Override
    public void initView() {
        super.initView();
        Intent intent = getIntent();
        if (intent != null) {
            mAccount = intent.getStringExtra(KEY_ACCOUNT);
            mType = intent.getIntExtra(KEY_TYPE, 0);
            mPassword = intent.getStringExtra(KEY_PASSWORD);
            mPublicName = intent.getStringExtra(KEY_PUBLIC_NAME);
            mAddress = intent.getStringExtra(KEY_ADDRESS);
            mHeadUrl = intent.getStringExtra(KEY_HEAD_URL);
        } else finish();

        //set view
        if (AppConstants.ACCOUNT_TYPE_EMAIL == mType) {
            mEditEmail.setText(mAccount);
            mEditEmail.setFocusable(false);
            mEditEmail.setTextColor(Color.GRAY);
        } else if (AppConstants.ACCOUNT_TYPE_PHONE == mType) {
            mEditPhone.setText(mAccount);
            mEditPhone.setFocusable(false);
            mEditPhone.setTextColor(Color.GRAY);
        }
    }

    @OnClick(R.id.bt_sign_up)
    void signUp() {
        //验证码为空
        if (TextUtils.isEmpty(mEditCode.getTrimmedString())) {
            UIUtils.showBaseToast("Code must not be null.");
            return;
        }

        if (mType == AppConstants.ACCOUNT_TYPE_PHONE) {
            if (TextUtils.isEmpty(mEditEmail.getTrimmedString())) {
                //邮箱为空
                UIUtils.showBaseToast("E-mail must not be null.");
                return;
            } else if (!PatternUtils.isValidEmail(mEditEmail.getTrimmedString())) {
                //邮箱不合法
                UIUtils.showBaseToast("E-mail invalid.");
                return;
            }
            VerifyEvent.verifyCode(mEditCode.getTrimmedString());
        } else if (mType == AppConstants.ACCOUNT_TYPE_EMAIL) {
            if (TextUtils.isEmpty(mEditPhone.getTrimmedString()))
            //手机号为空
            {
                UIUtils.showBaseToast("Phone must not be null.");
                return;
            } else if (!PatternUtils.isUSphonenumber(mEditPhone.getTrimmedString()) && !PatternUtils.isCNPhone(mEditPhone.getTrimmedString())) {
                UIUtils.showBaseToast("Phone invalid.");
                return;
            }
            mPresenter.checkEmailCode(mEditCode.getTrimmedString(), URLEncoder.encode(mAccount));
        }

    }

    @Override
    protected SingUpPresenter createPresenter() {
        return new SingUpPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_venue_register_second;
    }

    @Override
    public void initListener() {
        super.initListener();
        mVenueService.setCursorVisible(false);
        mVenueService.setFocusable(false);
        mVenueService.setFocusableInTouchMode(false);
        registerEventBus(this);
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

    @Override
    public void showCheckResult(boolean invalid, int accountType, String msg) {

    }

    @Override
    public void nextStep() {

    }


    @OnClick(R.id.et_venue_service)
    void selectStyle() {
        UIUtils.hideSoftInput(mVenueService);
        showPopWindow(this, mVenueService.getTrimmedString());
    }

    private void showPopWindow(Context context, String selectType) {
        if (popupWindow == null) {
            popupWindow = new PopupWindow(context);
            View view = LayoutInflater.from(context).inflate(R.layout.venue_pop_service_type, null, false);
            TypedArray array = getResources().obtainTypedArray(R.array.venue_service_type);
            for (int i = 0; i < array.length(); i++) {
                checkBoxs[i] = view.findViewById(array.getResourceId(i, 0));
            }
            array.recycle();
            popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            popupWindow.setWidth(UIUtils.getScreenWidth() - UIUtils.dip2Px(110));
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setContentView(view);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    StringBuilder stringBuffer = new StringBuilder();
                    for (CheckBox checkBox : checkBoxs) {
                        if (checkBox.isChecked())
                            stringBuffer.append(checkBox.getText().toString()).append(",");
                    }
                    String substring = "";
                    if (stringBuffer.length() >= 1) {
                        substring = stringBuffer.substring(0, stringBuffer.length() - 1);
                    }
                    mVenueService.setText(substring);
                }
            });
            popupWindow.update();
        } else {
            String[] split = selectType.split(",");
            for (int i = 0; i < checkBoxs.length; i++) {
                for (int j = 0; j < split.length; j++) {
                    if (split[j].equals(checkBoxs[i].getText().toString())) {
                        checkBoxs[i].setChecked(true);
                    }
                }
            }
        }
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
            mVenueService.setText(selectType);
        } else {
            popupWindow.showAsDropDown(mVenueService);
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
                mPresenter.register_venue(mAccount, mPassword, mEditPhone.getTrimmedString(), mEditEmail.getTrimmedString(),
                        mEditCode.getTrimmedString(), mVenueService.getTrimmedString(), mAddress, mVenuePhone.getTrimmedString(),
                        mVenueWebsite.getTrimmedString(), "经度", "纬度", "twitterAccount", "facebookAccount",
                        mHeadUrl, "placeID", "venueScore");
                break;
            case AppConstants.Code_Verify_Invalid:
                UIUtils.showBaseToast("验证码不正确");
                break;
            case AppConstants.Code_Send_ServerError:
            default:
                UIUtils.showBaseToast("服务器出错");
                break;
        }
    }
}
