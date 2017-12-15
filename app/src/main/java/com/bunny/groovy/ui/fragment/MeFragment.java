package com.bunny.groovy.ui.fragment;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.view.IOverView;

/****************************************
 * 功能说明: 表演者个人中心
 *
 * Author: Created by bayin on 2017/12/15.
 ****************************************/

public class MeFragment extends BaseFragment{
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_me_layout;
    }

    @Override
    protected void loadData() {

    }
}
