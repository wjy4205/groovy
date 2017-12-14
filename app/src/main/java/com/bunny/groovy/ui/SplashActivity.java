package com.bunny.groovy.ui;

import android.content.Intent;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseActivity;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.ui.setfile.SetFile1Activity;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.utils.SharedPreferencesUtils;
import com.bunny.groovy.utils.UIUtils;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/12/14.
 ****************************************/

public class SplashActivity extends BaseActivity {
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.acitivty_splash_layout;
    }

    @Override
    public void initView() {
        super.initView();
        boolean isLogin = (boolean) SharedPreferencesUtils.getParam(this, AppConstants.KEY_LOGIN, false);
        if (isLogin) {
            //已登录
            //加载用户数据
            AppCacheData.getPerformerUserModel().setUserID((String) SharedPreferencesUtils.getParam(this,AppConstants.KEY_USERID,""));
            AppCacheData.getPerformerUserModel().setTelephone((String) SharedPreferencesUtils.getParam(this,AppConstants.KEY_PHONE,""));
            //判断是去主页，还是完善资料
            String level = (String) SharedPreferencesUtils.getParam(this,
                    AppConstants.KEY_USERFILE_LEVEL, AppConstants.USERFILE_LEVLE_NONE);
            switch (level) {
                case AppConstants.USERFILE_LEVLE_FULL:
                    //进入首页
                    MainActivity.launch(this);
                    break;
                case AppConstants.USERFILE_LEVLE_FIRST:
                case AppConstants.USERFILE_LEVLE_SECOND:
                case AppConstants.USERFILE_LEVLE_NONE:
                default:
                    UIUtils.showBaseToast(getString(R.string.perfect_info));
                    startActivity(new Intent(this, SetFile1Activity.class));
                    break;
            }
        } else {
            //未登录
            startActivity(new Intent(this, RoleChooseActivity.class));
        }
        finish();
    }
}
