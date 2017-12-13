package com.bunny.groovy.ui.setfile;

import android.app.Activity;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseActivity;
import com.bunny.groovy.presenter.SetFilePresenter;
import com.bunny.groovy.view.ISetFileView;

/****************************************
 * 功能说明:  完善资料第三步
 *
 * Author: Created by bayin on 2017/12/12.
 ****************************************/

public class SetFile3Activity extends BaseActivity<SetFilePresenter> implements ISetFileView {

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
        return R.layout.third_perfect_file_layout;
    }
}
