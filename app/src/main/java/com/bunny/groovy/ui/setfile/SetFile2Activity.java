package com.bunny.groovy.ui.setfile;

import android.app.Activity;
import android.content.Intent;
import android.widget.EditText;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseActivity;
import com.bunny.groovy.presenter.SetFilePresenter;
import com.bunny.groovy.ui.login.LoginActivity;
import com.bunny.groovy.view.ISetFileView;
import com.xw.repo.XEditText;

import butterknife.Bind;
import butterknife.OnClick;

/****************************************
 * 功能说明:  完善资料第二步
 *
 * Author: Created by bayin on 2017/12/12.
 ****************************************/

public class SetFile2Activity extends BaseActivity<SetFilePresenter> implements ISetFileView {
    @OnClick(R.id.iv_select_music)
    void selectMusic() {
        startActivityForResult(new Intent(this, MusicListActivity.class), 1);
    }

    @OnClick(R.id.bt_play_music)
    void playMusic() {

    }

    @OnClick(R.id.et_select_style)
    void selectStyle() {

    }

    @OnClick(R.id.tv_next)
    void next() {

    }

    @OnClick(R.id.tv_login)
    void login() {
        LoginActivity.launch(this);
    }

    @Bind(R.id.et_bio)
    EditText etBio;
    @Bind(R.id.et_select_style)
    XEditText etSelectStyle;

    @Override
    public void initView() {
        super.initView();
        etSelectStyle.setFocusable(false);
    }

    @Override
    public Activity get() {
        return getCurrentActivity();
    }

    @Override
    protected SetFilePresenter createPresenter() {
        return new SetFilePresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.second_perfect_file_layout;
    }
}
