package com.bunny.groovy.ui.fragment.usercenter;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.presenter.SettingsPresenter;
import com.bunny.groovy.view.ISettingView;

import butterknife.Bind;
import butterknife.OnClick;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/12/22.
 ****************************************/

public class SettingsFragment extends BaseFragment<SettingsPresenter> implements ISettingView {

    @OnClick(R.id.settings_tv_logout)
    public void logout() {

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

    }
}
