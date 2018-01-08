package com.bunny.groovy.ui.fragment;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bunny.groovy.R;
import com.bunny.groovy.adapter.UserCenterAdapter;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.BaseListFragment;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.model.StyleModel;
import com.bunny.groovy.presenter.MePresenter;
import com.bunny.groovy.service.MusicService;
import com.bunny.groovy.ui.fragment.usercenter.FavoriteFragment;
import com.bunny.groovy.ui.fragment.usercenter.HistoryFragment;
import com.bunny.groovy.ui.fragment.usercenter.PersonalDataFragment;
import com.bunny.groovy.ui.fragment.usercenter.SettingsFragment;
import com.bunny.groovy.utils.MusicBox;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.view.IMeView;
import com.bunny.groovy.view.IOverView;
import com.bunny.groovy.weidget.SlidingTabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/****************************************
 * 功能说明: 表演者个人中心
 *
 * Author: Created by bayin on 2017/12/15.
 ****************************************/

public class MeFragment extends BaseFragment<MePresenter> implements IMeView {

    @Bind(R.id.user_center_iv_header)
    CircleImageView ivHeader;

    @Bind(R.id.user_center_tv_username)
    TextView tvName;

    @Bind(R.id.user_center_tv_style)
    TextView tvStyle;

    @Bind(R.id.user_center_tv_score)
    TextView tvScore;

    @Bind(R.id.user_center_viewpager)
    ViewPager mViewPager;

    @Bind(R.id.user_center_pagetabstrip)
    SlidingTabLayout pagerTabStrip;

    @Bind(R.id.me_play_music)
    ImageView mePlayButton;

    private List<BaseListFragment> mFragments = new ArrayList<>();

    private String[] titleArray = new String[]{"MY FAVORITE", "SHOW HISTORY"};

    private MusicService.CallBack callBack;

    private boolean isHaveMusicFile = false;//用户是否上传了音乐文件


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
    private PerformerUserModel mModel;

    @OnClick(R.id.user_center_tv_settings)
    public void setttings() {
        SettingsFragment.launch(getActivity());
    }

    @OnClick(R.id.user_center_iv_header)
    public void setProfile() {
        PersonalDataFragment.launch(getActivity());
    }

    private MusicBox.MusicPlayCallback mMusicPlayCallback = new MusicBox.MusicPlayCallback() {
        @Override
        public void playMusic() {
            mePlayButton.setImageResource(R.drawable.login_stop);
        }

        @Override
        public void stopPlay() {
            mePlayButton.setImageResource(R.drawable.login_play);
        }

        @Override
        public void musicEnd() {
            mePlayButton.setImageResource(R.drawable.login_play);
        }

    };

    @OnClick(R.id.me_play_music)
    public void playMusic() {
        if (isHaveMusicFile) {
            MusicBox.getInstance().setMusicPlayCallback(mMusicPlayCallback);
            MusicBox.getInstance().playOnLineMusic(mModel.getMusicFile(), mActivity);
        } else {
            UIUtils.showBaseToast("Please upload music.");
        }
    }

    /**
     * 控制音乐播放
     */
    private void handleMusic() {
        if (callBack != null) {
            boolean isPlay = callBack.isPlayerMusic();
            if (isPlay) {
                mePlayButton.setImageResource(R.mipmap.login_stop);
            } else {
                mePlayButton.setImageResource(R.mipmap.login_play);
            }
        }
    }



    @Override
    protected MePresenter createPresenter() {
        return new MePresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_user_center_layout;
    }

    @Override
    protected void loadData() {
        //获取用户数据
        mPresenter.requestUserData();
        //设置viewpager
        mFragments.add(new FavoriteFragment());
        mFragments.add(new HistoryFragment());
        mViewPager.setAdapter(new UserCenterAdapter(mFragments, titleArray, getActivity().getSupportFragmentManager()));
        pagerTabStrip.setDistributeEvenly(true);
        pagerTabStrip.setCustomTabView(R.layout.custorm_tab_layout, R.id.tv_tab);
        pagerTabStrip.setSelectedIndicatorColors(getActivity().getResources().getColor(R.color.white));
        pagerTabStrip.setTitleTextColor(getResources().getColor(R.color.white), getResources().getColor(R.color.white));
        pagerTabStrip.setTabStripWidth(UIUtils.getScreenWidth() / 2);
        pagerTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mFragments.get(position).loadAgain();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pagerTabStrip.setViewPager(mViewPager);
    }

    @Override
    public Activity get() {
        return mActivity;
    }


    /**
     * 设置用户数据
     *
     * @param model
     */
    @Override
    public void setUserView(PerformerUserModel model) {
        mModel = model;
        tvName.setText(mModel.getUserName());
        tvStyle.setText(model.getPerformTypeName());
        if (TextUtils.isEmpty(model.getStarLevel()))
            tvScore.setText("0.0");
        else tvScore.setText(model.getStarLevel());
        Glide.with(getActivity()).load(model.getHeadImg()).into(ivHeader);
        //prepare music
        isHaveMusicFile = !TextUtils.isEmpty(model.getMusicFile());
//        if (!TextUtils.isEmpty(model.getMusicFile())) {
//            isHaveMusicFile = true;
//            Intent intent = new Intent();
//            intent.setClass(mActivity, MusicService.class);
//            intent.putExtra(MusicService.MUSIC_EXTRA, model.getMusicFile());
//            mActivity.startService(intent);
//            mActivity.bindService(intent, conn, Service.BIND_AUTO_CREATE);
//        }else isHaveMusicFile = false;
    }

    @Override
    public void showStylePop(List<StyleModel> modelList) {
        //do nothing
    }

    @Override
    public void refreshUI() {
        //获取用户数据
        mPresenter.requestUserData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFragmentVisible())
            mPresenter.requestUserData();
    }


    @Override
    public void onPause() {
        super.onPause();
        if (callBack != null && callBack.isPlaying()) {
            callBack.isPlayerMusic();
            mePlayButton.setBackgroundResource(R.mipmap.login_play);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (callBack != null && callBack.isPlaying()) {
            callBack.isPlayerMusic();
            mePlayButton.setBackgroundResource(R.mipmap.login_play);
        }
    }
}
