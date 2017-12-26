package com.bunny.groovy.ui.fragment.notify;

import android.app.Activity;

import com.bunny.groovy.adapter.NotifyListAdapter;
import com.bunny.groovy.base.BaseListFragment;
import com.bunny.groovy.presenter.ListPresenter;
import com.bunny.groovy.view.IListPageView;

/**
 * 通知中心，申请演出机会列表
 *
 * Created by Administrator on 2017/12/26.
 */

public class ApplyOppListFragment extends BaseListFragment<ListPresenter,NotifyListAdapter> implements IListPageView{
    @Override
    protected void loadData() {

    }

    @Override
    protected ListPresenter createPresenter() {
        return null;
    }

    @Override
    public Activity get() {
        return mActivity;
    }

    @Override
    public void setView(Object o) {

    }

    @Override
    public void setNormalView() {

    }

    @Override
    public void setNodata() {

    }

    @Override
    public void setError() {

    }
}
