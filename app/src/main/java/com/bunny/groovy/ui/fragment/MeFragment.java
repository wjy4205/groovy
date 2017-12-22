package com.bunny.groovy.ui.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.ViewParent;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bunny.groovy.R;
import com.bunny.groovy.adapter.UserCenterAdapter;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.BaseListFragment;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.presenter.MePresenter;
import com.bunny.groovy.ui.fragment.usercenter.FavoriteFragment;
import com.bunny.groovy.ui.fragment.usercenter.HistoryFragment;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.view.IMeView;
import com.bunny.groovy.view.IOverView;
import com.bunny.groovy.weidget.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/****************************************
 * 功能说明: 表演者个人中心
 *
 * Author: Created by bayin on 2017/12/15.
 ****************************************/

public class MeFragment extends BaseFragment<MePresenter> implements IMeView {

    @Bind(R.id.user_center_iv_header)
    CircleImageView ivHeader;

    @Bind(R.id.user_center_tv_username)
    TextView tvName;

    @Bind(R.id.user_center_tv_style)
    TextView tvStyle;

    @Bind(R.id.user_center_tv_score)
    TextView tvScore;

    @Bind(R.id.user_center_viewpager)
    ViewPager mViewPager;

    @Bind(R.id.user_center_pagetabstrip)
    SlidingTabLayout pagerTabStrip;

    @OnClick(R.id.user_center_tv_settings)
    public void setttings(){

    }

    private List<BaseListFragment> mFragments = new ArrayList<>();

    private String[] titleArray = new String[]{"MY FAVORITE", "SHOW HISTORY"};

    @Override
    protected MePresenter createPresenter() {
        return new MePresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_user_center_layout;
    }

    @Override
    protected void loadData() {
        //获取用户数据
        mPresenter.requestUserData();
        //设置viewpager
        mFragments.add(new FavoriteFragment());
        mFragments.add(new HistoryFragment());
        mViewPager.setAdapter(new UserCenterAdapter(mFragments, titleArray, getActivity().getSupportFragmentManager()));
        pagerTabStrip.setDistributeEvenly(true);
        pagerTabStrip.setSelectedIndicatorColors(getActivity().getResources().getColor(R.color.white));
        pagerTabStrip.setTitleTextColor(getResources().getColor(R.color.white), getResources().getColor(R.color.white));
        pagerTabStrip.setTabStripWidth(UIUtils.getScreenWidth() / 2);
        pagerTabStrip.setSelectedIndicatorColors(getActivity().getResources().getColor(R.color.white));
        pagerTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mFragments.get(position).loadAgain();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pagerTabStrip.setViewPager(mViewPager);
    }

    @Override
    public Activity get() {
        return getActivity();
    }


    /**
     * 设置用户数据
     *
     * @param model
     */
    @Override
    public void setUserView(PerformerUserModel model) {
        tvName.setText(model.getUserName());
        tvStyle.setText(model.getPerformTypeName());
        if (TextUtils.isEmpty(model.getStarLevel()))
            tvScore.setText("0.0");
        else tvScore.setText(model.getStarLevel());
        Glide.with(getActivity()).load(model.getHeadImg()).into(ivHeader);
    }
}
