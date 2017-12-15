package com.bunny.groovy.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseActivity;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.listener.PermissionListener;
import com.bunny.groovy.ui.login.LoginActivity;
import com.bunny.groovy.utils.UIUtils;

import java.security.Permission;
import java.security.Permissions;
import java.util.List;
import java.util.jar.Manifest;

import butterknife.OnClick;
import flyn.Eyes;

/**
 * 角色选择页面
 * <p>
 * Created by Administrator on 2017/12/2.
 */

public class RoleChooseActivity extends BaseActivity {
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
    }

    public static void launch(Context activity) {
        activity.startActivity(new Intent(activity, RoleChooseActivity.class));
    }

    @Override
    public void initListener() {
        super.initListener();
        requestRuntimePermission(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied(List<String> deniedPermissions) {

            }
        });
    }

    @Override
    public boolean enableSlideClose() {
        return false;
    }
}
