package com.bunny.groovy.ui;

import android.content.Intent;
import android.view.View;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseActivity;
import com.bunny.groovy.ui.login.ITestView;
import com.bunny.groovy.ui.login.LoginActivity;
import com.bunny.groovy.ui.login.TestPresenter;

import butterknife.OnClick;
import flyn.Eyes;

/**
 * 角色选择页面
 * <p>
 * Created by Administrator on 2017/12/2.
 */

public class RoleChooseActivity extends BaseActivity<TestPresenter> implements ITestView{
    @OnClick(R.id.tv_musician)
    void login() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    protected TestPresenter createPresenter() {
        return new TestPresenter(this);
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
