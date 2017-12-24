package com.bunny.groovy.ui.fragment.usercenter;

import android.app.Activity;
import android.os.Bundle;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.FragmentContainerActivity;
import com.bunny.groovy.presenter.SettingsPresenter;
import com.bunny.groovy.ui.RoleChooseActivity;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.utils.Utils;
import com.bunny.groovy.view.ISettingView;

import org.greenrobot.eventbus.EventBus;

import butterknife.OnClick;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/12/22.
 ****************************************/

public class SettingsFragment extends BaseFragment<SettingsPresenter> implements ISettingView {

    @OnClick(R.id.settings_tv_logout)
    public void logout() {
        mPresenter.logout();
    }

    public static void launch(Activity from){
        Bundle bundle = new Bundle();
        bundle.putString(FragmentContainerActivity.FRAGMENT_TITLE,"SETTINGS");
        FragmentContainerActivity.launch(from,SettingsFragment.class,bundle);
    }

    @Override
    protected SettingsPresenter createPresenter() {
        return new SettingsPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_settings_layout;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void loginOut() {
        Utils.clearLoginData(get());
        //跳转到角色选择页面
        RoleChooseActivity.launch(getActivity());
//        //退出setting页面
        getActivity().finish();
//        //退出MainActivity
        EventBus.getDefault().post(AppConstants.EVENT_LOGIN_OUT);
    }

    @Override
    public Activity get() {
        return getActivity();
    }
}
