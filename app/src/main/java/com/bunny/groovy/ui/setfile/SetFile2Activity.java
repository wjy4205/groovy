package com.bunny.groovy.ui.setfile;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Parcelable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseActivity;
import com.bunny.groovy.model.MusicBean;
import com.bunny.groovy.presenter.SetFilePresenter;
import com.bunny.groovy.service.MusicService;
import com.bunny.groovy.ui.login.LoginActivity;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.view.ISetFileView;
import com.xw.repo.XEditText;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.OnClick;

/****************************************
 * 功能说明:  完善资料第二步
 *
 performTypeName
 signature
 music
 *
 * Author: Created by bayin on 2017/12/12.
 ****************************************/

public class SetFile2Activity extends BaseActivity<SetFilePresenter> implements ISetFileView {

    private MusicBean mMusic_file;
    private String performTypeName = "";//表演类型，用“，“隔开
    private MusicService.CallBack callBack;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            callBack = (MusicService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            callBack = null;
        }
    };

    @OnClick(R.id.iv_select_music)
    void selectMusic() {
        startActivityForResult(new Intent(this, MusicListActivity.class), 1);
    }

    @Bind(R.id.bt_play_music)
    Button btPlay;

    @OnClick(R.id.bt_play_music)
    void playMusic() {
        if (mMusic_file == null || TextUtils.isEmpty(mMusic_file.getMusicPath())) {
            UIUtils.showBaseToast("Choose music.");
            return;
        }
        handleMusic();
    }

    private void initMusicService() {
        /** 构造启动音乐播放服务的Intent，设置音乐资源 */
        Intent intent = new Intent(this, MusicService.class);
        intent.putExtra("music_path", mMusic_file.getMusicPath());
        startService(intent);
        bindService(intent, conn, Service.BIND_AUTO_CREATE);
    }

    /**
     * 控制音乐播放
     */
    private void handleMusic() {
        if (callBack != null) {
            boolean isPlay = callBack.isPlayerMusic();
            if (isPlay) {
                btPlay.setBackgroundResource(R.mipmap.login_stop);
            } else {
                btPlay.setBackgroundResource(R.mipmap.login_play);
            }
        }
    }

    @OnClick(R.id.et_select_style)
    void selectStyle() {

    }

    @OnClick(R.id.tv_next)
    void next() {
        // TODO: 2017/12/14 拦截判断空
        AppCacheData.getFileMap().put("performTypeName", etSelectStyle.getTrimmedString());
        AppCacheData.getFileMap().put("signature", etBio.getText().toString().trim());
        AppCacheData.getFileMap().put("music", mMusic_file.getMusicPath());
        startActivityForResult(new Intent(this, SetFile3Activity.class), 1);
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
    }

    @Override
    public void initListener() {
        super.initListener();
        registerEventBus(this);
    }

    @Subscribe
    public void onMusicEnd(String event) {
        btPlay.setBackgroundResource(R.mipmap.login_play);
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

    @Override
    protected void onPause() {
        super.onPause();
        if (callBack!=null && callBack.isPlaying()) callBack.isPlayerMusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (callBack!=null && callBack.isPlaying()) callBack.isPlayerMusic();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                mMusic_file = data.getParcelableExtra("music_file");
                initMusicService();
            }
        }
    }
}
