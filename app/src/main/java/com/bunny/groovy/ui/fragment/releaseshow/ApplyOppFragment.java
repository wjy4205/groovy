package com.bunny.groovy.ui.fragment.releaseshow;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.FragmentContainerActivity;
import com.bunny.groovy.model.OpportunityModel;
import com.bunny.groovy.presenter.ExplorerOpportunityPresenter;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.view.IExploreView;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/****************************************
 * 功能说明:  申请演出机会
 *
 * Author: Created by bayin on 2017/12/28.
 ****************************************/

public class ApplyOppFragment extends BaseFragment<ExplorerOpportunityPresenter> implements IExploreView {
    private static OpportunityModel oppBean;
    public static String KEY_OPP_BEAN = "key_opp";

    public static void launch(Activity from, Bundle bundle) {
        oppBean = bundle.getParcelable(KEY_OPP_BEAN);
        bundle.putString(FragmentContainerActivity.FRAGMENT_TITLE, "APPLY");
        FragmentContainerActivity.launch(from, ApplyOppFragment.class, bundle);
    }

    @Bind(R.id.apply_et_time)
    EditText etTime;
    @Bind(R.id.apply_et_style)
    EditText etStyle;
    @Bind(R.id.apply_et_bio)
    EditText etDesc;

    @OnClick(R.id.apply_tv_apply)
    public void apply() {
        //set params
        //   venueID
        //   performType
        //   performStartDate
        //   performEndDate
        //   performDesc
        //   performerID
        //   opportunityID
        HashMap<String, String> map = new HashMap<>();
        map.put("venueID", oppBean.getVenueID());
        map.put("performType", AppCacheData.getPerformerUserModel().getPerformTypeName());
        map.put("performStartDate", oppBean.getStartDate());
        map.put("performEndDate", oppBean.getEndDate());
        map.put("performDesc", etDesc.getText().toString());
        map.put("performerID", AppCacheData.getPerformerUserModel().getUserID());
        map.put("opportunityID", oppBean.getOpportunityID());
        mPresenter.applyOpportunity(map);
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        etStyle.setFocusable(false);
        etTime.setFocusable(false);

        if (oppBean != null) {
            etTime.setText(oppBean.getPerformDate() + " " + oppBean.getPerformTime());
            etStyle.setText(oppBean.getVenueTypeName());
        }
    }

    @Override
    public Activity get() {
        return mActivity;
    }

    @Override
    public void setListData(List<OpportunityModel> list) {

    }

    @Override
    public void applyResult(boolean success, String msg) {
        UIUtils.showBaseToast(success + msg);
    }

    @Override
    protected ExplorerOpportunityPresenter createPresenter() {
        return new ExplorerOpportunityPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_apply_opp_layout;
    }

    @Override
    protected void loadData() {

    }
}
