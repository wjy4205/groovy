package com.bunny.groovy.ui.fragment.wallet;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.base.FragmentContainerActivity;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.utils.UIUtils;

import butterknife.Bind;
import butterknife.OnClick;

/****************************************
 * 功能说明:  个人中心钱包页面
 *
 * Author: Created by bayin on 2018/1/10.
 ****************************************/

public class WalletFragment extends BaseFragment {


    @Bind(R.id.wallet_tv_balance)
    TextView mWalletTvBalance;
    @Bind(R.id.wallet_tv_recharge)
    TextView mWalletTvRecharge;
    @Bind(R.id.wallet_tv_withdraw)
    TextView mWalletTvWithdraw;
    @Bind(R.id.wallet_tv_bind_paypal)
    TextView mWalletTvBindPaypal;

    private String dollarTag = "$ %s";

    public static void launch(Activity from) {
        Bundle bundle = new Bundle();
        bundle.putString(FragmentContainerActivity.FRAGMENT_TITLE, "WALLET");
        FragmentContainerActivity.launch(from, WalletFragment.class, bundle);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_wallet_layout;
    }

    @Override
    protected void loadData() {
        mWalletTvBalance.setText(String.format(dollarTag, AppCacheData.getPerformerUserModel().getBalance()));
    }

    @OnClick({R.id.wallet_tv_recharge, R.id.wallet_tv_withdraw, R.id.wallet_tv_bind_paypal})
    public void onViewClicked(View view) {
        String paypalAccount = AppCacheData.getPerformerUserModel().getPaypalAccount();
        switch (view.getId()) {
            case R.id.wallet_tv_recharge:
                //充值
                if (TextUtils.isEmpty(paypalAccount)) {
                    UIUtils.showBaseToast("Please bind PayPal.");
                    return;
                }
                RechargeFragment.launch(mActivity);
                break;
            case R.id.wallet_tv_withdraw:
                //提现
                if (TextUtils.isEmpty(paypalAccount)) {
                    UIUtils.showBaseToast("Please bind PayPal.");
                    return;
                }
                WithDrawFragment.launch(mActivity);
                break;
            case R.id.wallet_tv_bind_paypal:
                //绑定
                if (!TextUtils.isEmpty(paypalAccount)) {
                    UIUtils.showBaseToast("You have bound PayPal.");
                    return;
                }
                BindPaypalFragment.launch(mActivity);
                break;
        }
    }
}
