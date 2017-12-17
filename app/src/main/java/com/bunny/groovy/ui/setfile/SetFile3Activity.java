package com.bunny.groovy.ui.setfile;

import android.app.Activity;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseActivity;
import com.bunny.groovy.model.StyleModel;
import com.bunny.groovy.presenter.SetFilePresenter;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.view.ISetFileView;
import com.xw.repo.XEditText;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/****************************************
 * 功能说明:  完善资料第三步
 *
 * Author: Created by bayin on 2017/12/12.
 ****************************************/

public class SetFile3Activity extends BaseActivity<SetFilePresenter> implements ISetFileView {
    @OnClick(R.id.tv_go)
    void go() {
        AppCacheData.getFileMap().put("facebookAccount", etFaceBook.getTrimmedString());
        AppCacheData.getFileMap().put("twitterAccount", etTwitter.getTrimmedString());
        AppCacheData.getFileMap().put("soundcloudAccount", etSoundCloud.getTrimmedString());

        mPresenter.updateUserInfo(AppCacheData.getFileMap());
    }

    @Bind(R.id.et_facebook)
    XEditText etFaceBook;
    @Bind(R.id.et_twitter)
    XEditText etTwitter;
    @Bind(R.id.et_soundcloud)
    XEditText etSoundCloud;

    @Override
    public Activity get() {
        return getCurrentActivity();
    }

    @Override
    public void showStylePop(List<StyleModel> modelList) {

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
