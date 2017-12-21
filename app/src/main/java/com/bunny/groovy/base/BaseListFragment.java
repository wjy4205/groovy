package com.bunny.groovy.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bunny.groovy.R;
import com.bunny.groovy.presenter.ListPresenter;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/21.
 */

abstract public class BaseListFragment<T extends ListPresenter> extends BaseFragment {

    @Bind(R.id.base_recyclerview)
    RecyclerView mRecylerView;

    @Bind(R.id.base_net_error)
    View mNetErrorView;

    @Bind(R.id.base_no_data)
    View mEmptyView;

    @OnClick(R.id.base_net_error)
    public void loadAgain(){
        loadData();
    }


    protected RecyclerView.Adapter mAdapter;

    public enum PageState {
        NORMAL, NODATA, ERROR
    }

    /**
     * 设置页面状态
     *
     * @param state
     */
    protected void setPageState(PageState state) {
        switch (state){
            case NORMAL:
                mRecylerView.setVisibility(View.VISIBLE);
                mEmptyView.setVisibility(View.GONE);
                mNetErrorView.setVisibility(View.GONE);
                break;
            case NODATA:
                mEmptyView.setVisibility(View.VISIBLE);
                mRecylerView.setVisibility(View.GONE);
                mNetErrorView.setVisibility(View.GONE);
                break;
            case ERROR:
                mNetErrorView.setVisibility(View.VISIBLE);
                mEmptyView.setVisibility(View.GONE);
                mRecylerView.setVisibility(View.GONE);
                break;
        }
    }

    protected abstract T createPresenter();

    @Override
    protected int provideContentViewId() {
        return R.layout.base_recyclerview_layout;
    }
}
