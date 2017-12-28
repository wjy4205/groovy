package com.bunny.groovy.ui.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.view.View;
import android.widget.TextView;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.model.ScheduleModel;
import com.bunny.groovy.model.ShowModel;
import com.bunny.groovy.presenter.SchedulePresenter;
import com.bunny.groovy.utils.DateUtils;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.view.IScheduleView;
import com.bunny.groovy.weidget.MoveLayout;
import com.bunny.groovy.weidget.datepick.DatePickerHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/****************************************
 * 功能说明: 表演者时间计划
 *
 * Author: Created by bayin on 2017/12/15.
 ****************************************/

public class ScheduleFragment extends BaseFragment<SchedulePresenter> implements IScheduleView {

    private ScheduleModel mScheduleModel;
    private Calendar todayCal = Calendar.getInstance();
    private ArrayList<TextView> mTextList;


    @Bind(R.id.moveLayout)
    MoveLayout mMoveLayout;
    @Bind(R.id.schedule_tv_time)
    TextView mTvTime;
    @Bind(R.id.schedule_tv_show_number_1)
    TextView mTvNumber_1;
    @Bind(R.id.schedule_tv_show_number_2)
    TextView mTvNumber_2;
    @Bind(R.id.schedule_tv_show_number_3)
    TextView mTvNumber_3;
    @Bind(R.id.schedule_tv_show_number_4)
    TextView mTvNumber_4;
    @Bind(R.id.schedule_tv_show_number_5)
    TextView mTvNumber_5;
    @Bind(R.id.schedule_tv_show_number_6)
    TextView mTvNumber_6;
    @Bind(R.id.schedule_tv_show_number_7)
    TextView mTvNumber_7;
    @Bind(R.id.schedule_tv_list_title)
    TextView mTvListTitle;

    @OnClick(R.id.schedule_bt_last_week)
    public void lastWeek() {
        List<Date> dates = DateUtils.lastWeek(monDate, sunDate);
        monDate = dates.get(0);
        sunDate = dates.get(1);
        mTvTime.setText(String.format(timeStr, generateTime(monDate, sunDate)));
    }

    @OnClick(R.id.schedule_bt_next_week)
    public void nextWeek() {
        List<Date> dates = DateUtils.nextWeek(monDate, sunDate);
        monDate = dates.get(0);
        sunDate = dates.get(1);
        mTvTime.setText(String.format(timeStr, generateTime(monDate, sunDate)));
    }

    @OnClick(R.id.schedule_rl_1)
    public void mondayList() {

    }

    @OnClick(R.id.schedule_rl_2)
    public void tuesdayList() {

    }

    @OnClick(R.id.schedule_rl_3)
    public void wensdayList() {

    }

    @OnClick(R.id.schedule_rl_4)
    public void thursdayList() {

    }

    @OnClick(R.id.schedule_rl_5)
    public void fridayList() {

    }

    @OnClick(R.id.schedule_rl_6)
    public void statdayList() {

    }

    @OnClick(R.id.schedule_rl_7)
    public void sundayList() {

    }


    private Date monDate;//周一日期
    private Date sunDate;//周日日期
    private String timeStr = "WEEK: %s";
    private String sameMonthTimeStr = "%s %s-%s";
    private String diffMonthTimeStr = "%s %s-%s %s";
    private String listTitleStr = "SHOW LIST ON %s";

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        mMoveLayout.setLoaction(UIUtils.getScreenHeight() / 2);
        //setTime
        List<Date> weekStartEndDate = DateUtils.getWeekStartEndDate();
        monDate = weekStartEndDate.get(0);
        sunDate = weekStartEndDate.get(1);
        mTvTime.setText(String.format(timeStr, generateTime(monDate, sunDate)));
        //init list
        mTextList = new ArrayList<>();
        mTextList.add(mTvNumber_1);
        mTextList.add(mTvNumber_2);
        mTextList.add(mTvNumber_3);
        mTextList.add(mTvNumber_4);
        mTextList.add(mTvNumber_5);
        mTextList.add(mTvNumber_6);
        mTextList.add(mTvNumber_7);
    }

    private String generateTime(Date from, Date end) {
        if (from.getMonth() == end.getMonth()) {
            String month = DateUtils.getMonthEn(from);
            return String.format(sameMonthTimeStr, month, from.getDate(), end.getDate());
        } else {
            return String.format(diffMonthTimeStr, DateUtils.getMonthEn(from), from.getDate(),
                    DateUtils.getMonthEn(end), end.getDate());
        }
    }

    @Override
    protected SchedulePresenter createPresenter() {
        return new SchedulePresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_schedule_layout;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void refreshUI() {
        mPresenter.requestWeekList(DateUtils.getFormatTime(monDate), DateUtils.getFormatTime(sunDate));
    }

    @Override
    public Activity get() {
        return mActivity;
    }

    @Override
    public void setFailureView() {

    }

    @Override
    public void setSuccessView(ScheduleModel model) {
        mScheduleModel = model;
        //设置星期几
        for (int i = 1; i < 8; i++) {
            List<ShowModel> showModelList = mScheduleModel.getShowModelList(String.valueOf(i));
            if (showModelList != null) {
                mTextList.get(i - 1).setText(String.valueOf(showModelList.size()));
                mTextList.get(i - 1).setBackgroundResource(R.drawable.shape_blue_circle_solid_bg);
            } else {
                mTextList.get(i - 1).setText("0");
                mTextList.get(i - 1).setBackgroundResource(R.drawable.shape_circle_grey_solid_bg);
            }
        }
        //title
        mTvListTitle.setText(String.format(listTitleStr,DateUtils.getDayOfWeek(todayCal)));
        //list

    }
}
