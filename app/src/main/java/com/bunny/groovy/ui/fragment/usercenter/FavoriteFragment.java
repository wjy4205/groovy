package com.bunny.groovy.ui.fragment.usercenter;

import android.app.Activity;

import com.bunny.groovy.base.BaseListFragment;
import com.bunny.groovy.model.FavoriteModel;
import com.bunny.groovy.presenter.ListPresenter;
import com.bunny.groovy.view.IListPageView;

import java.util.List;

/**
 * 个人中心 收藏fragment
 * Created by Administrator on 2017/12/21.
 */

public class FavoriteFragment extends BaseListFragment implements IListPageView<List<FavoriteModel>> {

    @Override
    public Activity get() {
        return getActivity();
    }

    @Override
    public void setView(List<FavoriteModel> o) {
        setNormalView();
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


    @Override
    protected void loadData() {
        mPresenter.getFavoriteList();
    }

    @Override
    protected ListPresenter createPresenter() {
        return new ListPresenter(this);
    }
}
