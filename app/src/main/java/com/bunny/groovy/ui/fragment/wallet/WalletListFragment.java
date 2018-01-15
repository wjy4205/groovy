package com.bunny.groovy.ui.fragment.wallet;

import android.app.Activity;
import android.os.Bundle;

import com.bunny.groovy.R;
import com.bunny.groovy.adapter.WalletAdapter;
import com.bunny.groovy.base.BaseListFragment;
import com.bunny.groovy.base.FragmentContainerActivity;
import com.bunny.groovy.divider.HLineDecoration;
import com.bunny.groovy.model.WalletBean;

import java.util.List;

/**
 * Created by bayin on 2018/1/15.
 */

public class WalletListFragment extends BaseListFragment<WalletListPresetner, WalletAdapter> implements IWalletListView {

    public static void launch(Activity from) {
        Bundle bundle = new Bundle();
        bundle.putString(FragmentContainerActivity.FRAGMENT_TITLE, "WALLET RECORD");
        FragmentContainerActivity.launch(from, WalletListFragment.class, bundle);
    }

    @Override
    protected WalletListPresetner createPresenter() {
        return new WalletListPresetner(this);
    }

    @Override
    protected void loadData() {
        mPresenter.fetchList();
    }

    @Override
    public Activity get() {
        return mActivity;
    }

    @Override
    public void setListData(List<WalletBean> listData) {
        setPageState(PageState.NORMAL);
        if (mAdapter == null) {
            mAdapter = new WalletAdapter(listData);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.addItemDecoration(new HLineDecoration(mActivity, HLineDecoration.VERTICAL_LIST,
                    R.drawable.shape_item_divider_line));
        } else mAdapter.refresh(listData);


    }

    @Override
    public void noData() {
        setPageState(PageState.NODATA);
    }
}
