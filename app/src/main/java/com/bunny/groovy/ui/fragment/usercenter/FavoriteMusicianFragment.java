package com.bunny.groovy.ui.fragment.usercenter;

import android.app.Activity;

import com.bunny.groovy.adapter.CollectMusicianListAdapter;
import com.bunny.groovy.base.BaseListFragment;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.presenter.VenueListPresenter;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.view.IListPageView;

import java.util.List;

/**
 * 个人中心 收藏fragment
 * Created by Administrator on 2017/12/21.
 */

public class FavoriteMusicianFragment extends BaseListFragment<VenueListPresenter, CollectMusicianListAdapter> implements IListPageView<List<PerformerUserModel>> {

    @Override
    public Activity get() {
        return getActivity();
    }

    @Override
    public void setView(List<PerformerUserModel> o) {
        setNormalView();
        setEmptyViewText("You aren't yet following any musicians! Find musicians you enjoy and follow them for updates and alerts.");
        if (mAdapter == null) {
            mAdapter = new CollectMusicianListAdapter(o);
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
        setEmptyViewText("You aren't yet following any musicians! Find musicians you enjoy and follow them for updates and alerts.");
        setPageState(PageState.NODATA);
    }

    @Override
    public void setError() {
        setPageState(PageState.ERROR);
    }


    @Override
    protected void loadData() {
        mPresenter.getCollectionPerformerList(AppCacheData.getPerformerUserModel().getUserID());
    }

    @Override
    protected VenueListPresenter createPresenter() {
        return new VenueListPresenter(this);
    }
}
