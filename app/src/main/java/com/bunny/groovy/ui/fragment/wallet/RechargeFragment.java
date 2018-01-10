package com.bunny.groovy.ui.fragment.wallet;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.FragmentContainerActivity;
import com.bunny.groovy.utils.AppCacheData;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/****************************************
 * 功能说明:  充值界面
 *
 * Author: Created by bayin on 2018/1/10.
 ****************************************/

public class RechargeFragment extends BaseFragment<PayPalPresenter> implements IPayPalView {
    @Bind(R.id.recharge_tv_paypal_value)
    TextView mRechargeTvPaypalValue;
    @Bind(R.id.recharge_tv_paypal_max)
    TextView mRechargeTvPaypalMax;
    @Bind(R.id.recharge_et_balance)
    EditText mRechargeEtBalance;
    @Bind(R.id.tv_recharge)
    TextView mTvRecharge;

    public static void launch(Activity from){
        Bundle bundle = new Bundle();
        bundle.putString(FragmentContainerActivity.FRAGMENT_TITLE,"RECHARGE");
        FragmentContainerActivity.launch(from,RechargeFragment.class,bundle);
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        mRechargeTvPaypalValue.setText(AppCacheData.getPerformerUserModel().getPaypalAccount());
    }

    @Override
    public Activity get() {
        return mActivity;
    }

    @Override
    protected PayPalPresenter createPresenter() {
        return new PayPalPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_recharge_layout;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.tv_recharge)
    public void onViewClicked() {
    }
}
