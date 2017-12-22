package com.bunny.groovy.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bunny.groovy.R;
import com.bunny.groovy.presenter.ListPresenter;
import com.github.nukc.stateview.StateView;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/21.
 */

abstract public class BaseListFragment<T extends BasePresenter,A extends RecyclerView.Adapter> extends LazyLoadFragment {
    protected T mPresenter;
    private View rootView;
    protected BaseActivity mActivity;

    @Bind(R.id.base_recyclerview)
    protected RecyclerView mRecyclerView;

    @Bind(R.id.base_net_error)
    View mNetErrorView;

    @Bind(R.id.base_no_data)
    View mEmptyView;

    @OnClick(R.id.base_net_error)
    public void loadAgain() {
        loadData();
    }

    protected A mAdapter;

    public enum PageState {
        NORMAL, NODATA, ERROR
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = createPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(provideContentViewId(), container, false);
            ButterKnife.bind(this, rootView);


            initView(rootView);
            initData();
            initListener();
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
    }

    /**
     * 设置页面状态
     *
     * @param state
     */
    protected void setPageState(PageState state) {
        switch (state) {
            case NORMAL:
                mRecyclerView.setVisibility(View.VISIBLE);
                mEmptyView.setVisibility(View.GONE);
                mNetErrorView.setVisibility(View.GONE);
                break;
            case NODATA:
                mEmptyView.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
                mNetErrorView.setVisibility(View.GONE);
                break;
            case ERROR:
                mNetErrorView.setVisibility(View.VISIBLE);
                mEmptyView.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 初始化一些view
     *
     * @param rootView
     */
    public void initView(View rootView) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    /**
     * 初始化数据
     */
    public void initData() {

    }

    /**
     * 设置listener的操作
     */
    public void initListener() {

    }

    @Override
    protected void onFragmentFirstVisible() {
        //当第一次可见的时候，加载数据
        loadData();
    }

    //加载数据
    protected abstract void loadData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        rootView = null;
    }

    public boolean isEventBusRegisted(Object subscribe) {
        return EventBus.getDefault().isRegistered(subscribe);
    }

    public void registerEventBus(Object subscribe) {
        if (!isEventBusRegisted(subscribe)) {
            EventBus.getDefault().register(subscribe);
        }
    }

    public void unregisterEventBus(Object subscribe) {
        if (isEventBusRegisted(subscribe)) {
            EventBus.getDefault().unregister(subscribe);
        }
    }

    protected abstract T createPresenter();

    protected int provideContentViewId() {
        return R.layout.base_recyclerview_layout;
    }
}
