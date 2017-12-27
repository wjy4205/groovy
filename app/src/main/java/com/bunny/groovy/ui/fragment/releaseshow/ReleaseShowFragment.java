package com.bunny.groovy.ui.fragment.releaseshow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Contacts;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bunny.groovy.R;
import com.bunny.groovy.adapter.StyleGridAdapter;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.FragmentContainerActivity;
import com.bunny.groovy.model.StyleModel;
import com.bunny.groovy.model.VenueModel;
import com.bunny.groovy.presenter.ReleasePresenter;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.utils.DateUtils;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.utils.Utils;
import com.bunny.groovy.view.ISetFileView;
import com.bunny.groovy.weidget.datepick.DatePickerHelper;
import com.bunny.groovy.weidget.loopview.LoopView;
import com.bunny.groovy.weidget.loopview.OnItemSelectedListener;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
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

import static android.app.Activity.RESULT_OK;

/**
 * 发布演出页面
 * Created by Administrator on 2017/12/16.
 */

public class ReleaseShowFragment extends BaseFragment<ReleasePresenter> implements ISetFileView {

    private PopupWindow mPopupWindow;
    private StyleGridAdapter mAdatper;
    private List<StyleModel> styleList;
    private PopupWindow mTimePop;
    private Calendar mSelectDate = Calendar.getInstance();//选择的日期
    private String startTime = "";//开始时间
    private String endTime = "";//结束时间
    private List<String> mTimeClockList;
    private PopupWindow mDatePop;
    private TextView mTvTimeTitle;
    private Date today = Calendar.getInstance().getTime();
    private VenueModel mVenueModel;
    private Place mPlace;

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

    @OnClick(R.id.tv_release)
    public void release() {
        //判断空
        if (UIUtils.isEdittextEmpty(etVenue)) {
            UIUtils.showBaseToast("请选择音乐厅");
            return;
        }
        if (UIUtils.isEdittextEmpty(etStyle)) {
            UIUtils.showBaseToast("请选择演出类型");
            return;
        }
        if (UIUtils.isEdittextEmpty(etTime)) {
            UIUtils.showBaseToast("请选择演出时间");
            return;
        }
        if (UIUtils.isEdittextEmpty(etBio)) {
            UIUtils.showBaseToast("请填写演出介绍");
            return;
        }
        Map<String, String> map = new HashMap<>();
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
            UIUtils.showBaseToast("音乐厅选取失败，请重新选择");
            return;
        }
        map.put("performType", etStyle.getText().toString());
        map.put("performStartDate", DateUtils.getFormatTime(mSelectDate.getTime(), startTime));
        map.put("performEndDate", DateUtils.getFormatTime(mSelectDate.getTime(), endTime));
        map.put("performDesc", etBio.getText().toString());
        map.put("performerName", AppCacheData.getPerformerUserModel().getUserName());
        mPresenter.releaseShow(map);
    }

    /**
     * 跳转到搜索音乐厅界面
     */
    @OnClick(R.id.release_tv_search)
    public void search() {
        SearchVenueFragment.launchForResult(mActivity, new Bundle(), 1);
    }

    @OnClick(R.id.release_et_style)
    public void showPop() {
        if (styleList == null || styleList.size() == 0)
            mPresenter.requestStyle();
        else showStylePop(styleList);
    }

    @OnClick(R.id.release_et_time)
    public void selectTime() {
        showTimeChoosePop();
    }

    /**
     * 弹出选择时间窗口
     */
    private void showTimeChoosePop() {
        if (mTimePop == null)
            initTimePop();
        mTimePop.showAtLocation(etBio, Gravity.CENTER, 0, 0);
    }

    /**
     * 关闭时间点选择框
     */
    private void closeTimePop() {
        if (mTimePop != null) mTimePop.dismiss();
    }

    /**
     * 初始化选择时间点弹框
     */
    private void initTimePop() {
        mTimePop = new PopupWindow(getActivity());
        View timeView = LayoutInflater.from(getActivity()).inflate(R.layout.weidget_time_choose_layout, null);
        mTimePop.setContentView(timeView);
        mTimePop.setWidth(UIUtils.getScreenWidth() - UIUtils.dip2Px(32));
        mTimePop.setHeight(UIUtils.getScreenHeight() / 2);
        timeView.setFocusable(true);
        timeView.setFocusableInTouchMode(true);
        mTimePop.setFocusable(true);
        mTvTimeTitle = (TextView) timeView.findViewById(R.id.weidget_tv_title);
        final LoopView loopviewFromTime = (LoopView) timeView.findViewById(R.id.weidget_from_time);
        final LoopView loopviewEndTime = (LoopView) timeView.findViewById(R.id.weidget_end_time);

        //set data
        mTvTimeTitle.setText(Utils.getFormatDate(mSelectDate.getTime()));
        loopviewFromTime.setItems(mTimeClockList);
        loopviewEndTime.setItems(mTimeClockList);
        //set listener
        timeView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    closeTimePop();
                    return true;
                }
                return false;
            }
        });
        timeView.findViewById(R.id.pop_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeTimePop();
            }
        });
        timeView.findViewById(R.id.pop_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loopviewFromTime.getSelectedItem() >= loopviewEndTime.getSelectedItem()) {
                    UIUtils.showBaseToast("开始时间不能小于结束时间");
                } else {
                    closeTimePop();
                    //设置开始结束时间
                    startTime = mTimeClockList.get(loopviewFromTime.getSelectedItem());
                    endTime = mTimeClockList.get(loopviewEndTime.getSelectedItem());
                    etTime.setText(DateUtils.getFormatTime(mSelectDate.getTime(), startTime) + "-" + endTime);
                }
            }
        });
        mTvTimeTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出选择日期的弹窗
                showDatePop();
            }
        });
    }

    /**
     * 显示日期选择器
     */
    private void showDatePop() {
        if (mDatePop == null)
            initDatePop();
        mDatePop.showAtLocation(etBio, Gravity.CENTER, 0, 0);
    }

    /**
     * 关闭选择日期窗口
     */
    private void closeDatePop() {
        if (mDatePop != null) mDatePop.dismiss();
    }

    /**
     * 初始化选择日期窗口
     */
    private void initDatePop() {
        Calendar minCalendar = Calendar.getInstance();
        Calendar maxCalendar = Calendar.getInstance();
        maxCalendar.add(Calendar.YEAR, 1);
        maxCalendar.add(Calendar.MINUTE, -1);

        mDatePop = new PopupWindow(getActivity());
        View dateView = LayoutInflater.from(getActivity()).inflate(R.layout.weidget_date_choose_layout, null, false);
        mDatePop.setContentView(dateView);
        mDatePop.setWidth(UIUtils.getScreenWidth() - UIUtils.dip2Px(32));
        mDatePop.setHeight(UIUtils.getScreenHeight() / 2);
        LoopView loopMonth = (LoopView) dateView.findViewById(R.id.weidget_month);
        final LoopView loopDay = (LoopView) dateView.findViewById(R.id.weidget_day);
        LoopView loopYear = (LoopView) dateView.findViewById(R.id.weidget_year);
        //set data
        final DatePickerHelper helper = new DatePickerHelper();
        //年
        final List<String> years = new ArrayList<>();
        years.add(String.valueOf(minCalendar.get(Calendar.YEAR)));
        years.add(String.valueOf(maxCalendar.get(Calendar.YEAR)));
        loopYear.setItems(years);
        loopYear.setNotLoop();
        //listener
        loopYear.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
//                currentYear = Integer.parseInt(years.get(index));
                mSelectDate.set(Calendar.YEAR, Integer.parseInt(years.get(index)));
            }
        });
        //月份
        String[] monthValues = helper.getEnMonths();
        loopMonth.setItems(Arrays.asList(monthValues));
        loopMonth.setNotLoop();
        //listener
        loopMonth.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
//                currentMonth = index + 1;
                mSelectDate.set(Calendar.MONTH, index);
                loopDay.setItems(Arrays.asList(helper.getDisplayDayAndWeek(mSelectDate.get(Calendar.YEAR),
                        mSelectDate.get(Calendar.MONTH))));
            }
        });
        //日期
        String[] dayValues = helper.getDisplayDayAndWeek(mSelectDate.get(Calendar.YEAR), mSelectDate.get(Calendar.MONTH));
        loopDay.setItems(Arrays.asList(dayValues));
        loopDay.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                mSelectDate.set(Calendar.DATE, index + 1);
            }
        });
        //初始日期
        loopYear.setInitPosition(mSelectDate.get(Calendar.YEAR));
        loopMonth.setInitPosition(mSelectDate.get(Calendar.MONTH));
        loopDay.setInitPosition(mSelectDate.get(Calendar.DATE) - 1);
        //点击事件
        dateView.findViewById(R.id.pop_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDatePop();
            }
        });
        dateView.findViewById(R.id.pop_confirm).setOnClickListener(new View.OnClickListener() {
            /**
             * @param v
             */
            @Override
            public void onClick(View v) {
                if (mSelectDate.getTime().before(today)) {
                    UIUtils.showBaseToast("选择日期小于今天");
                } else {
                    closeDatePop();
                    //设置title
                    mTvTimeTitle.setText(Utils.getFormatDate(mSelectDate.getTime()));
                }
            }
        });
    }


    /**
     * 接收选择的演出厅
     *
     * @param model
     */
    @Subscribe
    public void onChooseVenue(VenueModel model) {
        mVenueModel = model;
        if (model != null) etVenue.setText(model.getVenueName());
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
        //获取可选择的时间
        String[] timeClockArray = mActivity.getResources().getStringArray(R.array.time_clock_array_24);
        mTimeClockList = Arrays.asList(timeClockArray);
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        //禁用编辑
        etVenue.setFocusable(false);
        etStyle.setFocusable(false);
        etTime.setFocusable(false);
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
        mPopupWindow.showAtLocation(etBio, Gravity.CENTER, 0, 0);
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
        RecyclerView recyclerview = (RecyclerView) popview.findViewById(R.id.recyclerview);
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
}
