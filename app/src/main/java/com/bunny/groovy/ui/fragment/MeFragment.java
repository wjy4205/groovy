package com.bunny.groovy.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bunny.groovy.R;
import com.bunny.groovy.adapter.UserCenterAdapter;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.BaseListFragment;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.model.StyleModel;
import com.bunny.groovy.presenter.MePresenter;
import com.bunny.groovy.ui.fragment.spotlight.SpotlightFragment;
import com.bunny.groovy.ui.fragment.usercenter.FavoriteFragment;
import com.bunny.groovy.ui.fragment.usercenter.HistoryFragment;
import com.bunny.groovy.ui.fragment.usercenter.PersonalDataFragment;
import com.bunny.groovy.ui.fragment.usercenter.SettingsFragment;
import com.bunny.groovy.ui.fragment.wallet.WalletFragment;
import com.bunny.groovy.ui.login.LoginActivity;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.utils.MusicBox;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.utils.Utils;
import com.bunny.groovy.view.IMeView;
import com.bunny.groovy.weidget.SlidingTabLayout;

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

    @Bind(R.id.me_tv_facebook)
    ImageView mFacebookView;
    @Bind(R.id.me_tv_twitter)
    ImageView mTwitterView;
    @Bind(R.id.me_tv_cloud)
    ImageView mCloudView;

    private List<BaseListFragment> mFragments = new ArrayList<>();

    private String[] titleArray = new String[]{"MY FAVORITE", "SHOW HISTORY"};


    private boolean isHaveMusicFile = false;//用户是否上传了音乐文件

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

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        setUserView(AppCacheData.getPerformerUserModel());
    }

    @OnClick(R.id.me_play_music)
    public void playMusic() {
        if (isHaveMusicFile) {
            MusicBox.getInstance().setMusicPlayCallback(mMusicPlayCallback);
            MusicBox.getInstance().playOnLineMusic(mModel.getMusicFile(), mActivity);
        } else {
            UIUtils.showBaseToast("Please upload music.");
        }
    }

    @OnClick(R.id.me_tv_facebook)
    public void facebook() {
        if (!TextUtils.isEmpty(AppCacheData.getPerformerUserModel().getFacebookAccount()))
            Utils.openFacebook(mActivity, AppCacheData.getPerformerUserModel().getFacebookAccount());
    }

    @OnClick(R.id.me_tv_cloud)
    public void cloud() {
        if (!TextUtils.isEmpty(AppCacheData.getPerformerUserModel().getSoundcloudAccount()))
            Utils.openSoundCloud(mActivity, AppCacheData.getPerformerUserModel().getSoundcloudAccount());
    }

    @OnClick(R.id.me_tv_twitter)
    public void twitter() {
        if (!TextUtils.isEmpty(AppCacheData.getPerformerUserModel().getTwitterAccount()))
            Utils.openTwitter(mActivity, AppCacheData.getPerformerUserModel().getTwitterAccount());
    }

    @OnClick(R.id.me_tv_wallet)
    public void wallet() {
        WalletFragment.launch(mActivity);
    }

    @OnClick(R.id.me_tv_spotlight)
    public void spotlight() {
        SpotlightFragment.launch(mActivity);
    }

    @OnClick(R.id.me_tv_switch_to)
    public void switchTo() {
        startActivity(new Intent(getActivity(), LoginActivity.class).putExtra("switch_type", AppConstants.USER_TYPE_NORMAL));
        getActivity().finish();
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
        try {
            mModel = model;
            tvName.setText(mModel.getUserName());
            tvStyle.setText(model.getPerformTypeName());
            tvScore.setText(Utils.getStar(model.getStarLevel()));
            if(TextUtils.isEmpty(model.getHeadImg())){
                ivHeader.setImageResource(R.drawable.musicion_default_photo);
            }else {
                Glide.with(getActivity()).load(model.getHeadImg()).placeholder(R.drawable.musicion_default_photo)
                        .error(R.drawable.musicion_default_photo).into(ivHeader);
            }
            //prepare music state
            isHaveMusicFile = !TextUtils.isEmpty(model.getMusicFile());
            mFacebookView.setImageResource(TextUtils.isEmpty(mModel.getFacebookAccount())? R.drawable.icon_facebook:R.drawable.user_facebook);
            mTwitterView.setImageResource(TextUtils.isEmpty(mModel.getTwitterAccount())? R.drawable.icon_twiter:R.drawable.user_twiter);
            mCloudView.setImageResource(TextUtils.isEmpty(mModel.getSoundcloudAccount())? R.drawable.icon_soundcloud:R.drawable.user_soundcloud);

        }catch (Exception e){}
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
        if (!isFirstEnter())
            mPresenter.requestUserData();
    }


    @Override
    public void onPause() {
        super.onPause();
        MusicBox.getInstance().relasePlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MusicBox.getInstance().relasePlayer();
    }
}
