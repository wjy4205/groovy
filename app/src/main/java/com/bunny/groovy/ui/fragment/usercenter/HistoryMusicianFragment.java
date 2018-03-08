package com.bunny.groovy.ui.fragment.usercenter;

import android.app.Activity;

import com.bunny.groovy.adapter.ShowHistoryAdapter;
import com.bunny.groovy.adapter.ShowMusicianHistoryAdapter;
import com.bunny.groovy.base.BaseListFragment;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.model.ShowModel;
import com.bunny.groovy.model.ShowMusicianHistoryModel;
import com.bunny.groovy.presenter.ListPresenter;
import com.bunny.groovy.presenter.VenueListPresenter;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.view.IListPageView;

import java.util.List;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/12/22.
 ****************************************/

public class HistoryMusicianFragment extends BaseListFragment<VenueListPresenter, ShowMusicianHistoryAdapter> implements IListPageView<List<ShowMusicianHistoryModel>> {
    @Override
    public Activity get() {
        return getActivity();
    }

    @Override
    public void setView(List<ShowMusicianHistoryModel> o) {
        setNormalView();
        if (mAdapter == null) {
            mAdapter = new ShowMusicianHistoryAdapter(o);
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

    @Override
    protected void loadData() {
        mPresenter.getPerformHistoryList();
    }

    @Override
    protected VenueListPresenter createPresenter() {
        return new VenueListPresenter(this);
    }
}
