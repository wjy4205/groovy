package com.bunny.groovy.ui.fragment.releaseshow;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bunny.groovy.R;
import com.bunny.groovy.adapter.OpportunityAdapter;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.base.FragmentContainerActivity;
import com.bunny.groovy.model.OpportunityModel;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/****************************************
 * 功能说明:  演出机会详情页
 *
 * Author: Created by bayin on 2017/12/26.
 ****************************************/

public class OpportunityDetailFragment extends BaseFragment {

    public static String KEY_OPPORTUNITY_BEAN = "key_opportunity_bean";
    private static OpportunityModel sParcelable;

    @Bind(R.id.opp_detail_tv_name)
    TextView mTvName;
    @Bind(R.id.opp_detail_tv_time)
    TextView mTvTime;
    @Bind(R.id.opp_detail_tv_desc)
    TextView mTvDesc;
    @Bind(R.id.opp_detail_tv_empty_info)
    TextView mTvEmpty;
    @Bind(R.id.include_detail_tv_venueName)
    TextView mTvVenueName;
    @Bind(R.id.include_detail_tv_venueStar)
    TextView mTvScore;
    @Bind(R.id.include_detail_tv_venueAddress)
    TextView mTvAddress;
    @Bind(R.id.include_detail_tv_tel)
    TextView mTvTel;
    @Bind(R.id.include_detail_tv_email)
    TextView mTvEmail;
    @Bind(R.id.include_detail_iv_head)
    ImageView mHead;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @OnClick(R.id.opp_detail_tv_apply)
    public void apply() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ApplyOppFragment.KEY_OPP_BEAN, sParcelable);
        ApplyOppFragment.launch(mActivity, bundle);
    }

    public static void launch(Activity activity, Bundle bundle) {
        sParcelable = bundle.getParcelable(KEY_OPPORTUNITY_BEAN);
        bundle.putString(FragmentContainerActivity.FRAGMENT_TITLE, "SHOW OPPORTUNITY");
        FragmentContainerActivity.launch(activity, OpportunityDetailFragment.class, bundle);
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_opportunity_detail_layout;
    }

    @Override
    protected void loadData() {
        if (sParcelable != null) {
            mTvTime.setText(sParcelable.getPerformDate() + " " + sParcelable.getPerformTime());
            mTvName.setText(sParcelable.getVenueName());
            mTvDesc.setText(sParcelable.getPerformDesc());
            mTvVenueName.setText(sParcelable.getVenueName());
            mTvAddress.setText(sParcelable.getVenueAddress());
            mTvTel.setText(sParcelable.getPhoneNumber());
            mTvEmail.setText(sParcelable.getWebSiteAddress());
            Glide.with(mActivity).load(sParcelable.getHeadImg()).placeholder(R.mipmap.venue_instead_pic).error(R.mipmap.venue_instead_pic)
                    .into(mHead);
            List<OpportunityModel.PerformerOpportunityBean> OpportunityList = sParcelable.getPerformerOpportunity();
            if (OpportunityList != null && OpportunityList.size() > 0) {
                //set adapter
                mTvEmpty.setVisibility(View.GONE);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
                mRecyclerView.setAdapter(new OpportunityAdapter(OpportunityList));
            } else {
                mTvEmpty.setVisibility(View.VISIBLE);
            }
        }
    }
}
