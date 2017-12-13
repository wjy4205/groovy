package com.bunny.groovy.ui.setfile;

import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.bunny.groovy.R;
import com.bunny.groovy.adapter.MusciAdapter;
import com.bunny.groovy.base.BaseActivity;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;

/**
 * 音乐列表
 * Created by Administrator on 2017/12/13.
 */

public class MusicListActivity extends BaseActivity {
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    @Bind(R.id.progress)
    ProgressBar progressBar;

    @Override
    public void initView() {
        super.initView();
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        ArrayList<HashMap<String, String>> playList = Utils.getPlayList("/mnt/");
        MusciAdapter musciAdapter = new MusciAdapter(playList);
        progressBar.setVisibility(View.GONE);
        recyclerView.setAdapter(musciAdapter);
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
