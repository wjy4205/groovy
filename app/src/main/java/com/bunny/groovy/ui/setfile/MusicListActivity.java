package com.bunny.groovy.ui.setfile;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bunny.groovy.R;
import com.bunny.groovy.adapter.MusicAdapter;
import com.bunny.groovy.base.BaseActivity;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.MusicBean;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.utils.Utils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 音乐列表
 * Created by Administrator on 2017/12/13.
 */

public class MusicListActivity extends BaseActivity implements TextWatcher {
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    @Bind(R.id.progress)
    ProgressBar progressBar;
    @Bind(R.id.et_search)
    EditText mSearchText;
    @Bind(R.id.base_no_data)
    TextView mEmptyView;
    private ArrayList<MusicBean> mPlayList;
    private String mKeyword = "";
    private MusicAdapter mMusicAdapter;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                mPlayList = Utils.queryMusic(mKeyword, MusicListActivity.this);
                mEmptyView.setVisibility(mPlayList.size() > 0 ? View.GONE : View.VISIBLE);
                mMusicAdapter.refresh(mPlayList);
            }
        }
    };

    @OnClick(R.id.actionbar_iv_back)
    public void clickBack() {
        finish();
    }

    @Override
    public void initView() {
        super.initView();
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        mPlayList = Utils.queryMusic(mKeyword, this);
        mEmptyView.setVisibility(mPlayList.size() > 0 ? View.GONE : View.VISIBLE);
        mMusicAdapter = new MusicAdapter(mPlayList);
        progressBar.setVisibility(View.GONE);
        mMusicAdapter.setItemClickListener(new MusicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int positon) {
                mPlayList.get(positon);
                if (mPlayList.get(positon).getLength() > 10) {
                    UIUtils.showBaseToast("Music size must within 10M.");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("music_file", mPlayList.get(positon));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        recyclerView.setAdapter(mMusicAdapter);
        mSearchText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mKeyword = s.toString();
        mHandler.removeMessages(1);
        mHandler.sendEmptyMessageDelayed(1, 1000);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_music_list_layout;
    }
}
