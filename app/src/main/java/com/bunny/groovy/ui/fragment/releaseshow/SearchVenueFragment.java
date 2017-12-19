package com.bunny.groovy.ui.fragment.releaseshow;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.bunny.groovy.R;
import com.bunny.groovy.adapter.VenueListAdapter;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.FragmentContainerActivity;
import com.bunny.groovy.model.VenueModel;
import com.bunny.groovy.presenter.SearchVenueListPresenter;
import com.bunny.groovy.view.ISearchVenueList;

import java.util.List;

import butterknife.Bind;

/****************************************
 * 功能说明: 搜索音乐厅页面
 *
 * Author: Created by bayin on 2017/12/19.
 ****************************************/

public class SearchVenueFragment extends BaseFragment<SearchVenueListPresenter> implements ISearchVenueList {

    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.venue_list)
    RecyclerView mRecyclerView;
    private VenueListAdapter mVenueListAdapter;

    public static void launchForResult(Activity activity, Bundle bundle, int requestCode) {
        bundle.putString(FragmentContainerActivity.FRAGMENT_TITLE, "SEARCH VENUE");
        FragmentContainerActivity.launchForResult(activity, SearchVenueFragment.class, bundle, requestCode);
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public void initListener() {
        super.initListener();
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s))
                    mPresenter.searchVenue(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected SearchVenueListPresenter createPresenter() {
        return new SearchVenueListPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.framgent_search_venue_list_layout;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public FragmentActivity get() {
        return getActivity();
    }

    @Override
    public void setListView(List<VenueModel> list) {
        if (mVenueListAdapter == null) {
            mVenueListAdapter = new VenueListAdapter(list, etSearch.getText().toString().trim());
            mRecyclerView.setAdapter(mVenueListAdapter);
        } else mVenueListAdapter.refresh(list, etSearch.getText().toString().trim());
    }
}
