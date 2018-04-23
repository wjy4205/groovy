package com.bunny.groovy.ui.setfile;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bunny.groovy.R;
import com.bunny.groovy.adapter.StyleGridAdapter;
import com.bunny.groovy.base.BaseActivity;
import com.bunny.groovy.model.MusicBean;
import com.bunny.groovy.model.StyleModel;
import com.bunny.groovy.presenter.SetFilePresenter;
import com.bunny.groovy.service.MusicService;
import com.bunny.groovy.ui.login.LoginActivity;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.view.ISetFileView;
import com.xw.repo.XEditText;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

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
    private MusicService.CallBack callBack;
    private String strFormat = "(%d/400)";
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
    private PopupWindow mPopupWindow;
    private StyleGridAdapter mAdapter;

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
                btPlay.setBackgroundResource(R.drawable.login_stop);
            } else {
                btPlay.setBackgroundResource(R.drawable.login_play);
            }
        }
    }

    @OnClick(R.id.et_select_style)
    void selectStyle() {
        mPresenter.requestStyle();
    }

    @OnClick(R.id.tv_next)
    void next() {
        if (TextUtils.isEmpty(etSelectStyle.getTrimmedString())) {
            UIUtils.showBaseToast("Please select Style.");
            return;
        }
        if (TextUtils.isEmpty(etBio.getText().toString())) {
            UIUtils.showBaseToast("Please input Bio.");
            return;
        }
        AppCacheData.getFileMap().put("performTypeName", etSelectStyle.getTrimmedString());
        AppCacheData.getFileMap().put("signature", etBio.getText().toString().trim());
        if (mMusic_file != null)
            AppCacheData.getFileMap().put("music", mMusic_file.getMusicPath());
        startActivityForResult(new Intent(this, SetFile3Activity.class), 2);
    }

    @OnClick(R.id.tv_login)
    void login() {
        LoginActivity.launch(this, AppConstants.USER_TYPE_MUSICIAN);
    }

    @Bind(R.id.tv_count)
    TextView tvCount;
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
        etBio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvCount.setText(String.format(strFormat, s.length()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Subscribe
    public void onMusicEnd(String event) {
        btPlay.setBackgroundResource(R.drawable.login_play);
    }


    @Override
    public Activity get() {
        return getCurrentActivity();
    }

    @Override
    public void showStylePop(List<StyleModel> modelList) {
        UIUtils.hideSoftInput(etSelectStyle);
        showPopWindow(modelList);
    }

    private void initPopWindow(List<StyleModel> modelList) {
        mPopupWindow = new PopupWindow(this);
        View popview = LayoutInflater.from(this).inflate(R.layout.pop_style_grid_layout, null, false);
        mPopupWindow.setContentView(popview);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setWidth(UIUtils.getScreenWidth() - UIUtils.dip2Px(32));
        mPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        RecyclerView recyclerview = popview.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new GridLayoutManager(SetFile2Activity.this, 3));
        mAdapter = new StyleGridAdapter(modelList, etSelectStyle.getText().toString().trim());
        recyclerview.setAdapter(mAdapter);
        mAdapter.setSelectNum(99);
        TextView textView = popview.findViewById(R.id.style_num_text);
        textView.setText("SELECT ALL");
        CheckBox checkBox = popview.findViewById(R.id.style_num_checkbox);
        checkBox.setVisibility(View.VISIBLE);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mAdapter.selectAll(b);
            }
        });
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
                etSelectStyle.setText(mAdapter.getSelectStyles());
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

    public void showPopWindow(List<StyleModel> dataList) {
        if (mPopupWindow == null) initPopWindow(dataList);
        if (mPopupWindow.isShowing()) mPopupWindow.dismiss();
        else mPopupWindow.showAtLocation(etSelectStyle, Gravity.CENTER, 0, UIUtils.dip2Px(15));
    }

    /**
     * 关闭选择style窗口
     */
    private void closePop() {
        if (mPopupWindow != null) mPopupWindow.dismiss();
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
        if (callBack != null && callBack.isPlaying()) {
            callBack.isPlayerMusic();
            btPlay.setBackgroundResource(R.drawable.login_play);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (callBack != null && callBack.isPlaying()) {
            callBack.isPlayerMusic();
            btPlay.setBackgroundResource(R.drawable.login_play);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                mMusic_file = data.getParcelableExtra("music_file");
                initMusicService();
            }
        } else if (requestCode == 2 && resultCode == AppConstants.ACTIVITY_FINISH) {
            setResult(AppConstants.ACTIVITY_FINISH);
            finish();
        }
    }
}
