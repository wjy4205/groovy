package com.bunny.groovy.ui.fragment.releaseshow;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.base.FragmentContainerActivity;
import com.bunny.groovy.model.ShowModel;
import com.bunny.groovy.utils.Utils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 表演详情 日程表详情
 * <p>
 * Created by Administrator on 2017/12/25.
 */

public class ShowDetailFragment extends BaseFragment {

    @Bind(R.id.show_detail_tv_date)
    TextView mTvDate;

    @Bind(R.id.show_detail_tv_performer_name)
    TextView mTvPerformerName;

    @Bind(R.id.show_detail_tv_venue_name)
    TextView mTvVenueName_1;

    @Bind(R.id.show_detail_tv_style)
    TextView mTvStyle;

    @Bind(R.id.show_detail_tv_time)
    TextView mTvTime;

    @Bind(R.id.show_detail_tv_distance)
    TextView mTvDistance;

    @Bind(R.id.show_detail_tv_desc)
    TextView mTvDesc;

    @Bind(R.id.include_detail_tv_venueName)
    TextView mTvVenueName_2;

    @Bind(R.id.include_detail_tv_venueStar)
    TextView mTvVenueScore;

    @Bind(R.id.include_detail_tv_venueAddress)
    TextView mTvAddress;

    @Bind(R.id.include_detail_tv_tel)
    TextView mTvTel;

    @Bind(R.id.include_detail_tv_email)
    TextView mTvEmail;

    @Bind(R.id.include_detail_iv_head)
    ImageView mHead;

    @Bind(R.id.show_detail_tv_notify)
    TextView mTvNotify;

    @OnClick(R.id.show_detail_iv_phone)
    public void call() {
        Utils.CallPhone(mActivity, model.getPhoneNumber());
    }

    @OnClick(R.id.show_detail_iv_email)
    public void email() {
        Utils.sendEmail(mActivity, model.getVenueEmail());
    }

    private static ShowModel model;
    public static String KEY_SHOW_BEAN = "key_show_bean";

    public static void launch(Activity from, Bundle bundle) {
        model = bundle.getParcelable(KEY_SHOW_BEAN);
        bundle.putString(FragmentContainerActivity.FRAGMENT_TITLE, "DETAIL");
        FragmentContainerActivity.launch(from, ShowDetailFragment.class, bundle);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_show_detial_layout;
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        mTvNotify.setVisibility(View.GONE);
        if (model != null) {
            mTvDate.setText(model.getPerformDate());
            mTvPerformerName.setText(model.getPerformerName());
            mTvVenueName_1.setText(model.getVenueName());
            mTvVenueName_2.setText(model.getVenueName());
            mTvStyle.setText(model.getPerformType());
            mTvTime.setText(model.getPerformTime());
            mTvDistance.setText(model.getDistance()+"km");
            mTvDesc.setText(model.getSignature());
            mTvVenueScore.setText(model.getVenueScore());
            mTvAddress.setText(model.getVenueAddress());
            mTvTel.setText(model.getPhoneNumber());
            mTvEmail.setText(model.getWebSiteAddress());
            Glide.with(mActivity).load(model.getHeadImg()).placeholder(R.mipmap.venue_instead_pic).error(R.mipmap.venue_instead_pic)
                    .into(mHead);
        }
    }

    @Override
    protected void loadData() {

    }
}
