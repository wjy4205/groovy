package com.bunny.groovy.ui;

import android.content.Intent;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseActivity;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.ui.login.LoginActivity;

import butterknife.OnClick;
import flyn.Eyes;

/**
 * 角色选择页面
 * <p>
 * Created by Administrator on 2017/12/2.
 */

public class RoleChooseActivity extends BaseActivity{
    @OnClick(R.id.tv_musician)
    void login() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_role_choose_layout;
    }

    @Override
    public void initView() {
        super.initView();
        Eyes.translucentStatusBar(this, true);
//        findViewById(R.id.tv_musician).setOnClickListener(v -> startActivity(new Intent(RoleChooseActivity.this, LoginActivity.class)));
    }


    @Override
    public boolean enableSlideClose() {
        return false;
    }
}
