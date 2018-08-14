package com.bunny.groovy.ui.fragment.releaseshow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bunny.groovy.R;
import com.bunny.groovy.adapter.DiscoverMusicianListAdapter;
import com.bunny.groovy.adapter.StyleGridAdapter;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.FragmentContainerActivity;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.model.StyleModel;
import com.bunny.groovy.presenter.DiscoverMusicianListPresenter;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.view.IDiscoverSearchMusicianList;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/****************************************
 * 功能说明: 发现表演者页面
 ****************************************/

public class DiscoverMusicianFragment extends BaseFragment<DiscoverMusicianListPresenter> implements IDiscoverSearchMusicianList {

    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.venue_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.discover_sort_rating)
    TextView mDiscoverSortRating;
    @Bind(R.id.discover_sort_distance)
    TextView mDiscoverSortDistance;
    @Bind(R.id.base_no_data)
    TextView mEmptyView;
    private String mSortType = "1";
    private String mPerformType;
    private String mKeyWord;
    private PopupWindow mPopupWindow;
    private StyleGridAdapter mAdapter;
    private List<StyleModel> styleList;
    private DiscoverMusicianListAdapter mMusicianListAdapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mPresenter.searchPerformer(mKeyWord, mSortType, mPerformType);
        }
    };

    public static void launch(Activity activity) {
        Bundle bundle = new Bundle();
        bundle.putString(FragmentContainerActivity.FRAGMENT_TITLE, "DISCOVER MUSICIANS");
        FragmentContainerActivity.launch(activity, DiscoverMusicianFragment.class, bundle);
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mDiscoverSortRating.setActivated(true);
        mPresenter.searchPerformer(mKeyWord, mSortType, mPerformType);
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

            }

            @Override
            public void afterTextChanged(Editable s) {
                mKeyWord = s.toString().trim();
                mHandler.removeMessages(1);
                mHandler.sendEmptyMessageDelayed(1, 1000);
            }
        });
    }

    @OnClick(R.id.discover_sort_distance)
    void sortDistance() {
        mDiscoverSortDistance.setActivated(true);
        mDiscoverSortRating.setActivated(false);
        mSortType = "0";
        mPresenter.searchPerformer("", mSortType, mPerformType);
    }

    @OnClick(R.id.discover_sort_rating)
    void sortRating() {
        mSortType = "1";
        mDiscoverSortDistance.setActivated(false);
        mDiscoverSortRating.setActivated(true);
        mPresenter.searchPerformer(mKeyWord, mSortType, mPerformType);
    }

    @OnClick(R.id.discover_sort_style)
    public void showPop() {
        if (styleList == null || styleList.size() == 0)
            mPresenter.requestStyle();
        else showStylePop(styleList);
    }

    @Override
    protected DiscoverMusicianListPresenter createPresenter() {
        return new DiscoverMusicianListPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.framgent_discover_musician_list_layout;
    }

    @Override
    protected void loadData() {

    }

    int PLACE_PICKER_REQUEST = 1;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        search();
        return super.onOptionsItemSelected(item);
    }

    /**
     * 进入google自动搜索地址页面
     */
    public void search() {
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
            }
        }
    }

    @Override
    public FragmentActivity get() {
        return getActivity();
    }

    @Override
    public void setListView(List<PerformerUserModel> list) {
        mEmptyView.setVisibility(list != null && list.size() > 0 ? View.GONE : View.VISIBLE);
        if (mMusicianListAdapter == null) {
            mMusicianListAdapter = new DiscoverMusicianListAdapter(list, etSearch.getText().toString().trim());
            mRecyclerView.setAdapter(mMusicianListAdapter);
        } else mMusicianListAdapter.refresh(list, etSearch.getText().toString().trim());
    }

    @Override
    public void showStylePop(List<StyleModel> modelList) {
        styleList = modelList;
        if (mPopupWindow == null)
            initPopWindow(modelList);
        mPopupWindow.showAtLocation(mRecyclerView, Gravity.CENTER, 0, UIUtils.dip2Px(15));
    }

    /**
     * 关闭选择style窗口
     */
    private void closePop() {
        if (mPopupWindow != null) mPopupWindow.dismiss();
    }

    private void initPopWindow(List<StyleModel> modelList) {
        mPopupWindow = new PopupWindow(getActivity());
        View popview = LayoutInflater.from(getActivity()).inflate(R.layout.pop_style_grid_layout, null, false);
        mPopupWindow.setContentView(popview);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setWidth(UIUtils.getScreenWidth() - UIUtils.dip2Px(32));
        mPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        RecyclerView recyclerview = popview.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mAdapter = new StyleGridAdapter(modelList, mPerformType);
        mAdapter.setSelectNum(100);
        CheckBox checkBox = popview.findViewById(R.id.style_num_checkbox);
        checkBox.setVisibility(View.VISIBLE);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mAdapter.selectAll(b);
            }
        });
        TextView textView = popview.findViewById(R.id.style_num_text);
        textView.setText("SELECT ALL");
        recyclerview.setAdapter(mAdapter);
        popview.findViewById(R.id.pop_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePop();
            }
        });
        popview.findViewById(R.id.pop_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePop();
                mPerformType = mAdapter.getSelectStyles();
                mPresenter.searchPerformer(mKeyWord, mSortType, mPerformType);
            }
        });
        // 按下android回退物理键 PopipWindow消失解决
        popview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    closePop();
                    return true;
                }
                return false;
            }
        });

    }
}
