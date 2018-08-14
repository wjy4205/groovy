package com.bunny.groovy.ui.fragment.releaseshow;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.FragmentContainerActivity;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.model.StyleModel;
import com.bunny.groovy.model.VenueModel;
import com.bunny.groovy.presenter.ReleaseShowOpportunityPresenter;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.utils.DateUtils;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.utils.Utils;
import com.bunny.groovy.view.ISetFileView;
import com.bunny.groovy.weidget.TimePopupWindow;
import com.bunny.groovy.weidget.datepick.DatePickerHelper;
import com.bunny.groovy.weidget.loopview.LoopView;
import com.bunny.groovy.weidget.loopview.OnItemSelectedListener;
import com.google.android.gms.location.places.Place;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 发布演出机会页面
 * Created by Administrator on 2017/12/16.
 */

public class ReleaseShowOpportunityFragment extends BaseFragment<ReleaseShowOpportunityPresenter> implements ISetFileView, TimePopupWindow.OnTimeConfirmListener {

    private Calendar mSelectDate = Calendar.getInstance();//选择的日期
    private String mStartTime = "";//开始时间
    private String mEndTime = "";//结束时间
    private TimePopupWindow mTimePop;

    public static void launch(Activity from) {
        Bundle bundle = new Bundle();
        bundle.putString(FragmentContainerActivity.FRAGMENT_TITLE, "RELEASE SHOW OPPORTUNITY");
        FragmentContainerActivity.launch(from, ReleaseShowOpportunityFragment.class, bundle);
    }

    @Bind(R.id.release_et_time)
    EditText etTime;

    @Bind(R.id.release_et_bio)
    EditText etBio;

    @OnClick(R.id.tv_release)
    public void release() {
        //判断空
        if (UIUtils.isEdittextEmpty(etTime)) {
            UIUtils.showBaseToast("Please choose show time.");
            return;
        }
        if (UIUtils.isEdittextEmpty(etBio)) {
            UIUtils.showBaseToast("Please input message.");
            return;
        }
        mPresenter.releaseShow(DateUtils.getFormatTime(mSelectDate.getTime(), mStartTime),
                DateUtils.getFormatTime(mSelectDate.getTime(), mEndTime), etBio.getText().toString().trim());
    }

    @OnClick(R.id.release_et_time)
    public void selectTime() {
        mTimePop.showTimeChoosePop(etBio);
    }

    @Override
    protected ReleaseShowOpportunityPresenter createPresenter() {
        return new ReleaseShowOpportunityPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_release_show_opportunity_layout;
    }

    @Override
    protected void loadData() {
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        etTime.setFocusable(false);
        mTimePop = new TimePopupWindow(getActivity());
        mTimePop.setListener(this);
    }

    @Override
    public void initListener() {
        super.initListener();
    }

    @Override
    public Activity get() {
        return getActivity();
    }

    @Override
    public void showStylePop(List<StyleModel> modelList) {
    }

    @Override
    public void chooseTime(String startTime, String endTime, Calendar selectDate, String showStartTime, String showEndTime) {
        mStartTime = startTime;
        mEndTime = endTime;
        mSelectDate = selectDate;
        etTime.setText(DateUtils.getTimeDialogformat(mSelectDate.getTime()) + showStartTime + "-" + showEndTime);
    }
}
