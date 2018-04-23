package com.bunny.groovy.ui.setfile;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.bunny.groovy.R;
import com.bunny.groovy.adapter.MusicAdapter;
import com.bunny.groovy.base.BaseActivity;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.MusicBean;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.utils.Utils;

import java.io.File;
import java.util.ArrayList;

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
    private ArrayList<MusicBean> mPlayList;

    @Override
    public void initView() {
        super.initView();
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        mPlayList = Utils.queryMusic(Environment.getExternalStorageDirectory() + File.separator, this);
        MusicAdapter musciAdapter = new MusicAdapter(mPlayList);
        progressBar.setVisibility(View.GONE);
        musciAdapter.setItemClickListener(new MusicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int positon) {
                mPlayList.get(positon);
                if (mPlayList.get(positon).getLength() > 5) {
                    UIUtils.showBaseToast("Music size must within 5M.");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("music_file",mPlayList.get(positon));
                setResult(RESULT_OK,intent);
                finish();
            }
        });
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
