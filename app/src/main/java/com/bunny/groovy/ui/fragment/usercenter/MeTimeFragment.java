package com.bunny.groovy.ui.fragment.usercenter;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.FragmentContainerActivity;
import com.bunny.groovy.model.VenueModel;
import com.bunny.groovy.presenter.TimePresenter;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.view.ITimeView;
import com.bunny.groovy.weidget.TimePopupWindow;
import com.bunny.groovy.weidget.loopview.LoopView;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;

/****************************************
 * 功能说明:  演出厅Me中的时间设置
 *
 ****************************************/

public class MeTimeFragment extends BaseFragment<TimePresenter> implements ITimeView, View.OnClickListener, SwitchButton.OnCheckedChangeListener,TimePopupWindow.OnTimeConfirmListener {

    @Bind(R.id.switch_time_mon)
    SwitchButton switchTimeMon;
    @Bind(R.id.switch_time_tues)
    SwitchButton switchTimeTues;
    @Bind(R.id.switch_time_wed)
    SwitchButton switchTimeWed;
    @Bind(R.id.switch_time_thur)
    SwitchButton switchTimeThur;
    @Bind(R.id.switch_time_fri)
    SwitchButton switchTimeFri;
    @Bind(R.id.switch_time_sta)
    SwitchButton switchTimeSta;
    @Bind(R.id.switch_time_sun)
    SwitchButton switchTimeSun;

    @Bind(R.id.tv_time_mon)
    TextView tvTimeMon;
    @Bind(R.id.tv_time_tues)
    TextView tvTimeTues;
    @Bind(R.id.tv_time_wed)
    TextView tvTimeWed;
    @Bind(R.id.tv_time_thur)
    TextView tvTimeThur;
    @Bind(R.id.tv_time_fri)
    TextView tvTimeFri;
    @Bind(R.id.tv_time_sta)
    TextView tvTimeSta;
    @Bind(R.id.tv_time_sun)
    TextView tvTimeSun;

    private List<TextView> tvTime = new ArrayList<>();
    private List<SwitchButton> tvSwitchButton = new ArrayList<>();
    private List<VenueModel.ScheduleListBean> mModel;
    private Calendar mSelectDate = Calendar.getInstance();//选择的日期
    private String mStartTime = "";//开始时间
    private String mEndTime = "";//结束时间
    private TimePopupWindow mTimePop;
    private int mIndex = -1;

    public static void launch(Activity from) {
        Bundle bundle = new Bundle();
        bundle.putString(FragmentContainerActivity.FRAGMENT_TITLE, "TIME");
        FragmentContainerActivity.launch(from, MeTimeFragment.class, bundle);
    }

    @Override
    protected TimePresenter createPresenter() {
        return new TimePresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_me_time_layout;
    }

    @Override
    protected void loadData() {
        mPresenter.getVenueSchedule();
    }

    @Override
    public void setView(List<VenueModel.ScheduleListBean> model) {
        mModel = model;
        for (int i = 0; i < model.size(); i++) {
            tvTime.get(i).setText(getDefaultDate(model.get(i).getStartDate()) + "-" + getDefaultDate(model.get(i).getEndDate()));
            tvSwitchButton.get(i).setChecked(TextUtils.equals(model.get(i).getIsHaveCharges(), "1"));
        }
    }

    private String getDefaultDate(String date) {
        if(TextUtils.isEmpty(date)){
            date = "00:00AM";
        }
        return date;
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        tvTime.add(tvTimeMon);
        tvTime.add(tvTimeTues);
        tvTime.add(tvTimeWed);
        tvTime.add(tvTimeThur);
        tvTime.add(tvTimeFri);
        tvTime.add(tvTimeSta);
        tvTime.add(tvTimeSun);

        tvSwitchButton.add(switchTimeMon);
        tvSwitchButton.add(switchTimeTues);
        tvSwitchButton.add(switchTimeWed);
        tvSwitchButton.add(switchTimeThur);
        tvSwitchButton.add(switchTimeFri);
        tvSwitchButton.add(switchTimeSta);
        tvSwitchButton.add(switchTimeSun);

        for (int i = 0; i < tvTime.size(); i++) {
            tvTime.get(i).setOnClickListener(this);
        }

        for (int i = 0; i < tvSwitchButton.size(); i++) {
            tvSwitchButton.get(i).setOnCheckedChangeListener(this);
        }
        mTimePop = new TimePopupWindow(getActivity());
        mTimePop.setListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_time_mon:
                mIndex = 0;
                break;
            case R.id.tv_time_tues:
                mIndex = 1;
                break;
            case R.id.tv_time_wed:
                mIndex = 2;
                break;
            case R.id.tv_time_thur:
                mIndex = 3;
                break;
            case R.id.tv_time_fri:
                mIndex = 4;
                break;
            case R.id.tv_time_sta:
                mIndex = 5;
                break;
            case R.id.tv_time_sun:
                mIndex = 6;
                break;
        }
        mTimePop.showTimeChoosePop(tvTime.get(mIndex));
    }

    @Override
    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
        switch (view.getId()) {
            case R.id.switch_time_mon:
                requestSwitch(0);
                break;
            case R.id.switch_time_tues:
                requestSwitch(1);
                break;
            case R.id.switch_time_wed:
                requestSwitch(2);
                break;
            case R.id.switch_time_thur:
                requestSwitch(3);
                break;
            case R.id.switch_time_fri:
                requestSwitch(4);
                break;
            case R.id.switch_time_sta:
                requestSwitch(5);
                break;
            case R.id.switch_time_sun:
                requestSwitch(6);
                break;

        }
    }

    private void requestSwitch(int index) {
        mIndex = index;
        if (mIndex != -1) {
            VenueModel.ScheduleListBean bean = mModel.get(mIndex);
            mPresenter.updateScheduleIsHaveCharges(bean.getScheduleID(), tvSwitchButton.get(mIndex).isChecked() ? "1" : "0");
        }
    }

    @Override
    public void updateScheduleView(String scheduleID, String startDate, String endDate) {
        if (mIndex != -1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(startDate).append("-").append(endDate);
            tvTime.get(mIndex).setText(stringBuilder.toString());
        }
    }

    @Override
    public void updateScheduleIsHaveCharges(String scheduleID, String isHaveCharges) {
        if (mIndex != -1)
            tvSwitchButton.get(mIndex).setChecked(TextUtils.equals("1", isHaveCharges));
    }

    @Override
    public Activity get() {
        return getActivity();
    }

    @Override
    public void chooseTime(String startTime, String endTime, Calendar selectDate) {
        mStartTime = startTime;
        mEndTime = endTime;
        mSelectDate = selectDate;
        if (mIndex != -1) {
            VenueModel.ScheduleListBean bean = mModel.get(mIndex);
            mPresenter.updateScheduleDate(bean.getScheduleID(), mStartTime.replaceAll(" ", ""), mEndTime.replaceAll(" ", ""));
        }
    }
}
