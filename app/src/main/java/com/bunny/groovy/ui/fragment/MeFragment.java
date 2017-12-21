package com.bunny.groovy.ui.fragment;

import android.app.Activity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.ViewParent;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.presenter.MePresenter;
import com.bunny.groovy.view.IMeView;
import com.bunny.groovy.view.IOverView;

import butterknife.Bind;
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
    PagerTabStrip pagerTabStrip;


    @Override
    protected MePresenter createPresenter() {
        return new MePresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_me_layout;
    }

    @Override
    protected void loadData() {
        //获取用户数据
        mPresenter.requestUserData();
        //设置viewpager

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
        tvScore.setText(model.getStarLevel());
        Glide.with(getActivity()).load(model.getHeadImg()).into(ivHeader);
    }
}
