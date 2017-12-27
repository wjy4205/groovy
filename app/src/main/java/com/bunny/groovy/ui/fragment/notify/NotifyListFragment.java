package com.bunny.groovy.ui.fragment.notify;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bunny.groovy.R;
import com.bunny.groovy.adapter.NotifyListAdapter;
import com.bunny.groovy.base.BaseListFragment;
import com.bunny.groovy.divider.HLineDecoration;
import com.bunny.groovy.model.ShowModel;
import com.bunny.groovy.presenter.ListPresenter;
import com.bunny.groovy.view.IListPageView;

import java.util.List;

/**
 * 通知中心，申请演出机会列表
 * <p>
 * Created by Administrator on 2017/12/26.
 */

public class NotifyListFragment extends BaseListFragment<ListPresenter, NotifyListAdapter> implements IListPageView<List<ShowModel>> {
    private int mTYPE;
    public static String KEY_TYPE = "notifytype";


    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        Bundle args = getArguments();
        mTYPE = args.getInt(KEY_TYPE);
        mRecyclerView.addItemDecoration(new HLineDecoration(mActivity, LinearLayoutManager.VERTICAL, R.drawable.shape_item_divider_line));
    }

    @Override
    protected void loadData() {
        switch (mTYPE) {
            case 0:
                mPresenter.getOpportunityList();
                break;
            case 1:
                mPresenter.getInviteList();
                break;
            case 2:
                mPresenter.getReleaseList();
                break;
        }
    }

    @Override
    protected ListPresenter createPresenter() {
        return new ListPresenter(this);
    }

    @Override
    public Activity get() {
        return mActivity;
    }

    @Override
    public void setView(List<ShowModel> o) {
        setNormalView();
        if (mAdapter == null) {
            mAdapter = new NotifyListAdapter(o, mTYPE);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.refresh(o);
        }
    }

    @Override
    public void setNormalView() {
        setPageState(PageState.NORMAL);
    }

    @Override
    public void setNodata() {
        setPageState(PageState.NODATA);
    }

    @Override
    public void setError() {
        setPageState(PageState.ERROR);
    }
}
