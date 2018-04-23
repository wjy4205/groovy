package com.bunny.groovy.ui.fragment.releaseshow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bunny.groovy.R;
import com.bunny.groovy.adapter.StyleGridAdapter;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.FragmentContainerActivity;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.model.StyleModel;
import com.bunny.groovy.model.VenueModel;
import com.bunny.groovy.presenter.ReleasePresenter;
import com.bunny.groovy.ui.fragment.spotlight.SpotlightFragment;
import com.bunny.groovy.ui.fragment.spotlight.SpotlightInfoFragment;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.utils.DateUtils;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.utils.Utils;
import com.bunny.groovy.view.ISetFileView;
import com.bunny.groovy.weidget.TimePopupWindow;
import com.bunny.groovy.weidget.datepick.DatePickerHelper;
import com.bunny.groovy.weidget.loopview.LoopView;
import com.bunny.groovy.weidget.loopview.OnItemSelectedListener;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * 发布演出页面
 * Created by Administrator on 2017/12/16.
 */

public class ReleaseShowFragment extends BaseFragment<ReleasePresenter> implements ISetFileView, TimePopupWindow.OnTimeConfirmListener {

    private PopupWindow mPopupWindow;
    private StyleGridAdapter mAdatper;
    private List<StyleModel> styleList;
    private Calendar mSelectDate = Calendar.getInstance();//选择的日期
    private String mStartTime = "";//开始时间
    private String mEndTime = "";//结束时间
    private VenueModel mVenueModel;
    private PerformerUserModel mPerformerModel;
    private Place mPlace;
    private int mType;
    private TimePopupWindow mTimePop;

    public static void launch(Activity from) {
        Bundle bundle = new Bundle();
        bundle.putString(FragmentContainerActivity.FRAGMENT_TITLE, "RELEASE SHOW");
        FragmentContainerActivity.launch(from, ReleaseShowFragment.class, bundle);
    }

    @Bind(R.id.release_et_venue)
    EditText etVenue;

    @Bind(R.id.release_et_style)
    EditText etStyle;

    @Bind(R.id.release_et_time)
    EditText etTime;

    @Bind(R.id.release_et_bio)
    EditText etBio;

    @Bind(R.id.release_include_venue)
    View venueInfoLayout;

    @Bind(R.id.venue_info_headimg)
    CircleImageView venueHeadImg;

    @Bind(R.id.venue_info_tv_name)
    TextView tvVenueName;

    @Bind(R.id.venue_info_tv_address)
    TextView tvVenueAddress;

    @Bind(R.id.venue_info_tv_score)
    TextView tvVenueScore;

    @Bind(R.id.venue_info_tv_phone)
    TextView tvVenuePhone;

    @Bind(R.id.release_ll_spotlight)
    View llSpotLight;

    @Bind(R.id.release_tv_money)
    TextView tvSpotLightMoney;

    @Bind(R.id.release_checkbox_use_spotlight)
    CheckBox cbUseSpotlight;

    @Bind(R.id.release_name)
    TextView mReleaseName;

    @OnClick(R.id.release_tv_spotlight)
    public void spotLight() {
        SpotlightInfoFragment.launch(get());
    }

    @OnClick(R.id.release_tv_get_credits)
    public void getCredits() {
        SpotlightFragment.launch(get());
    }


    @OnClick(R.id.tv_release)
    public void release() {
        //判断空
        if (UIUtils.isEdittextEmpty(etVenue)) {
            UIUtils.showBaseToast(mType == 2 ? "Please search or input artist's name" : "Please choose venue.");
            return;
        }
        if (UIUtils.isEdittextEmpty(etStyle)) {
            UIUtils.showBaseToast("Please choose show genre.");
            return;
        }
        if (UIUtils.isEdittextEmpty(etTime)) {
            UIUtils.showBaseToast("Please choose show time.");
            return;
        }
        if (UIUtils.isEdittextEmpty(etBio)) {
            UIUtils.showBaseToast("Please input show description.");
            return;
        }
        Map<String, String> map = new HashMap<>();
        //mType-2:演出厅发布演出
        if (mType == AppConstants.USER_TYPE_VENUE) {
            if (mPerformerModel != null && !TextUtils.isEmpty(mPerformerModel.getUserID())) {
                map.put("performerID", mPerformerModel.getUserID());
            }
            map.put("performerName", etVenue.getText().toString());
            PerformerUserModel performerModel = AppCacheData.getPerformerUserModel();
            map.put("venueName", performerModel.getUserName());
            map.put("venueLongitude", performerModel.getLongitude());
            map.put("venueLatitude", performerModel.getLatitude());
            map.put("venueAddress", performerModel.getVenueAddress());
        } else {
            if (mVenueModel != null && !TextUtils.isEmpty(mVenueModel.getVenueID()) && !TextUtils.isEmpty(mVenueModel.getVenueName())) {
                map.put("venueID", mVenueModel.getVenueID());
                map.put("venueName", mVenueModel.getVenueName());
                map.put("venueAddress", mVenueModel.getVenueAddress());
                map.put("venueLongitude", mVenueModel.getLongitude());
                map.put("venueLatitude", mVenueModel.getLatitude());
            } else if (mPlace != null) {
                map.put("venueName", mPlace.getName().toString());
                map.put("venueAddress", mPlace.getAddress().toString());
                map.put("venueLongitude", String.valueOf(mPlace.getLatLng().longitude));
                map.put("venueLatitude", String.valueOf(mPlace.getLatLng().latitude));
            } else {
                UIUtils.showBaseToast("Please Try again.");
                return;
            }
            map.put("performerName", AppCacheData.getPerformerUserModel().getUserName());
        }
        map.put("performType", etStyle.getText().toString());
        map.put("performStartDate", DateUtils.getFormatTime(mSelectDate.getTime(), mStartTime));
        map.put("performEndDate", DateUtils.getFormatTime(mSelectDate.getTime(), mEndTime));
        map.put("performDesc", etBio.getText().toString());
        if (cbUseSpotlight.isChecked()) {
            map.put("isOpportunity", "1");
        } else map.put("isOpportunity", "0");
        mPresenter.releaseShow(map, mType);
    }

    /**
     * 跳转到搜索音乐厅界面
     */
    @OnClick(R.id.release_tv_search)
    public void search() {
        if (mType == AppConstants.USER_TYPE_VENUE) {
            SearchMusicianFragment.launchForResult(mActivity, new Bundle(), 1);
        } else {
            SearchVenueFragment.launchForResult(mActivity, new Bundle(), 1);
        }
    }

    @OnClick(R.id.release_et_style)
    public void showPop() {
        if (styleList == null || styleList.size() == 0)
            mPresenter.requestStyle();
        else showStylePop(styleList);
    }

    @OnClick(R.id.release_et_time)
    public void selectTime() {
        mTimePop.showTimeChoosePop(etBio);
    }


    /**
     * 接收选择的演出厅
     *
     * @param model
     */
    @Subscribe
    public void onChooseVenue(VenueModel model) {
        mVenueModel = model;
        if (model != null) {
            etVenue.setText(model.getVenueName());
            venueInfoLayout.setVisibility(View.VISIBLE);
            Glide.with(get()).load(model.getHeadImg()).placeholder(R.drawable.venue_default_photo)
                    .error(R.drawable.venue_default_photo).dontAnimate().into(venueHeadImg);
            tvVenueName.setText(model.getVenueName());
            tvVenueScore.setText(Utils.getStar(model.getVenueScore()));
            tvVenueAddress.setText(model.getVenueAddress());
            tvVenuePhone.setText(model.getPhoneNumber());
        } else {
            venueInfoLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 接收选择的演出厅
     *
     * @param model
     */
    @Subscribe
    public void onChooseMusician(PerformerUserModel model) {
        mPerformerModel = model;
        if (model != null) {
            etVenue.setText(model.getStageName());
            venueInfoLayout.setVisibility(View.VISIBLE);
            Glide.with(get()).load(model.getHeadImg()).placeholder(R.drawable.venue_default_photo)
                    .error(R.drawable.venue_default_photo).into(venueHeadImg);
            tvVenueName.setText(model.getStageName());
            tvVenueScore.setText(Utils.getStar(model.getStarLevel()));
            tvVenueAddress.setText(model.getPerformTypeName());
            tvVenuePhone.setText(model.getTelephone());
        } else {
            venueInfoLayout.setVisibility(View.GONE);
        }
    }


    @Override
    protected ReleasePresenter createPresenter() {
        return new ReleasePresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_release_show_layout;
    }

    @Override
    protected void loadData() {
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        mType = Integer.parseInt(AppCacheData.getPerformerUserModel().getUserType());
        //演出厅
        if (mType == 2) {
            mReleaseName.setText("SELECT MUSICIAN");
            etVenue.setHint("Fill in musician name or search");
        } else {
            etVenue.setFocusable(false);
        }
        mTimePop = new TimePopupWindow(getActivity());
        mTimePop.setListener(this);
        //禁用编辑
        etStyle.setFocusable(false);
        etTime.setFocusable(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        //spotlight
        if (Integer.parseInt(AppCacheData.getPerformerUserModel().getPackageCount()) > 0) {
            tvSpotLightMoney.setVisibility(View.VISIBLE);
            tvSpotLightMoney.setText(AppCacheData.getPerformerUserModel().getPackageCount());
            cbUseSpotlight.setVisibility(View.VISIBLE);
//            cbUseSpotlight.setChecked(true);
        } else {
            tvSpotLightMoney.setVisibility(View.GONE);
            cbUseSpotlight.setVisibility(View.GONE);
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        registerEventBus(this);
    }

    @Override
    public Activity get() {
        return getActivity();
    }

    @Override
    public void showStylePop(List<StyleModel> modelList) {
        UIUtils.hideSoftInput(etStyle);
        styleList = modelList;
        if (mPopupWindow == null)
            initPopWindow(modelList);
        mPopupWindow.showAtLocation(etBio, Gravity.CENTER, 0, UIUtils.dip2Px(15));
    }

    /**
     * 关闭选择style窗口
     */
    private void closePop() {
        if (mPopupWindow != null) mPopupWindow.dismiss();
    }

    private void initPopWindow(List<StyleModel> modelList) {
        mPopupWindow = new PopupWindow(getActivity());
        View popview = LayoutInflater.from(getActivity()).inflate(R.layout.pop_style_grid_layout, null, false);
        mPopupWindow.setContentView(popview);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setWidth(UIUtils.getScreenWidth() - UIUtils.dip2Px(32));
        mPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        RecyclerView recyclerview = popview.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mAdatper = new StyleGridAdapter(modelList, etStyle.getText().toString().trim());
        recyclerview.setAdapter(mAdatper);
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
                etStyle.setText(mAdatper.getSelectStyles());
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                mPlace = PlacePicker.getPlace(data, mActivity);
                etVenue.setText(mPlace.getName());
            }
        }
    }

    @Override
    public void chooseTime(String startTime, String endTime, Calendar selectDate) {
        mStartTime = startTime;
        mEndTime = endTime;
        mSelectDate = selectDate;
        etTime.setText(DateUtils.getFormatTime(mSelectDate.getTime(), startTime) + "-" + endTime);
    }
}
