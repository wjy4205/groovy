package com.bunny.groovy.ui;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseActivity;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.weidget.NoScrollViewPager;
import com.chaychan.library.BottomBarLayout;

import butterknife.Bind;

public class MainActivity extends BaseActivity {
    @Bind(R.id.vp_content)
    NoScrollViewPager noScrollViewPager;
    @Bind(R.id.bottom_bar)
    BottomBarLayout bottomBarLayout;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        super.initView();

    }
}
