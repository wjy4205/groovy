package com.bunny.groovy.ui.fragment.wallet;

import android.app.Activity;

import com.bunny.groovy.model.WalletBean;

import java.util.List;

/**
 * Created by bayin on 2018/1/15.
 */

public interface IWalletListView {
    Activity get();

    void setListData(List<WalletBean> listData);

    void noData();
}
