package com.bunny.groovy.ui.fragment.apply;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bunny.groovy.R;
import com.bunny.groovy.adapter.StyleGridAdapter;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.FragmentContainerActivity;
import com.bunny.groovy.model.StyleModel;
import com.bunny.groovy.model.VenueModel;
import com.bunny.groovy.presenter.ApplyVenuePresenter;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.utils.DateUtils;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.utils.Utils;
import com.bunny.groovy.view.IApplyVenueView;
import com.bunny.groovy.weidget.TimePopupWindow;
import com.bunny.groovy.weidget.datepick.DatePickerHelper;
import com.bunny.groovy.weidget.loopview.LoopView;
import com.bunny.groovy.weidget.loopview.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/****************************************
 * 功能说明:  申请演出厅表演页面
 *
 * Author: Created by bayin on 2018/1/3.
 ****************************************/

public class ApplyVenueFragment extends BaseFragment<ApplyVenuePresenter> implements IApplyVenueView,TimePopupWindow.OnTimeConfirmListener {

    public static String KEY_VENUE_BEAN = "KEY_VENUE_BEAN";
    private static VenueModel sVenueBean;
    private String mStartTime = "";//开始时间
    private String mEndTime = "";//结束时间
    private Calendar mSelectDate = Calendar.getInstance();
    private List<StyleModel> styleList;
    private TimePopupWindow mTimePop;
    private PopupWindow mPopupWindow;
    private StyleGridAdapter mAdapter;


    public static void launch(Activity from, Bundle bundle) {
        sVenueBean = bundle.getParcelable(KEY_VENUE_BEAN);
        bundle.putString(FragmentContainerActivity.FRAGMENT_TITLE, "APPLY");
        FragmentContainerActivity.launch(from, ApplyVenueFragment.class, bundle);
    }

    @Bind(R.id.apply_et_time)
    EditText etTime;
    @Bind(R.id.apply_et_style)
    EditText etStyle;
    @Bind(R.id.apply_et_bio)
    EditText etDesc;


    //弹出选择style窗口
    @OnClick(R.id.apply_et_style)
    public void showStyle() {
        UIUtils.hideSoftInput(etStyle);
        if (styleList == null || styleList.size() == 0)
            mPresenter.requestStyle();
        else showStylePop(styleList);
    }

    //弹出时间选择窗口
    @OnClick(R.id.apply_et_time)
    public void selectTime() {
        UIUtils.hideSoftInput(etStyle);
        mTimePop.showTimeChoosePop(etTime);
    }

    /**
     * 弹出选择时间窗口
     */

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        etStyle.setFocusable(false);
        etTime.setFocusable(false);
        mTimePop = new TimePopupWindow(getActivity());
        mTimePop.setListener(this);
    }

    //申请
    @OnClick(R.id.apply_tv_apply)
    public void apply() {
        UIUtils.hideSoftInput(etStyle);
        //拦截判空
        if (TextUtils.isEmpty(etDesc.getText().toString())) {
            UIUtils.showBaseToast("Please input description.");
            return;
        }

        if (TextUtils.isEmpty(etStyle.getText().toString())) {
            UIUtils.showBaseToast("Please choose perform style.");
            return;
        }

        //set params
        //   performType
        //   performStartDate
        //   performEndDate
        //   performDesc
        //   venueName
        //   venueAddress
        //   performerName
        //   venueLongitude
        //   venueLatitude
        //   venueID

        HashMap<String, String> map = new HashMap<>();
        map.put("performStartDate", DateUtils.getFormatTime(mSelectDate.getTime(), mStartTime));
        map.put("performEndDate", DateUtils.getFormatTime(mSelectDate.getTime(), mEndTime));
        map.put("performType", etStyle.getText().toString());
        map.put("performDesc", etDesc.getText().toString());
        map.put("performerName", AppCacheData.getPerformerUserModel().getUserName());
        map.put("venueID", sVenueBean.getVenueID());
        map.put("venueName", sVenueBean.getVenueName());
        map.put("venueAddress", sVenueBean.getVenueAddress());
        map.put("venueLongitude", sVenueBean.getLongitude());
        map.put("venueLatitude", sVenueBean.getLatitude());
        map.put("isOpportunity", "0");
        mPresenter.applyVenue(map);
    }

    @Override
    public Activity get() {
        return mActivity;
    }

    @Override
    public void showStylePop(List<StyleModel> modelList) {
        UIUtils.hideSoftInput(etStyle);
        styleList = modelList;
        if (mPopupWindow == null)
            initStylePop(modelList);
        mPopupWindow.showAtLocation(etStyle, Gravity.CENTER, 0, 0);
    }


    /**
     * 关闭选择style窗口
     */
    private void closePop() {
        if (mPopupWindow != null) mPopupWindow.dismiss();
    }

    private void initStylePop(List<StyleModel> modelList) {
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
        mAdapter = new StyleGridAdapter(modelList, etStyle.getText().toString().trim());
        recyclerview.setAdapter(mAdapter);
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
                etStyle.setText(mAdapter.getSelectStyles());
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
    protected ApplyVenuePresenter createPresenter() {
        return new ApplyVenuePresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_apply_opp_layout;
    }

    @Override
    protected void loadData() {
        //获取可选择的时间
    }

    @Override
    public void chooseTime(String startTime, String endTime, Calendar selectDate) {
        mStartTime = startTime;
        mEndTime = endTime;
        mSelectDate = selectDate;
        etTime.setText(DateUtils.getFormatTime(mSelectDate.getTime(), startTime) + "-" + endTime);
    }
}
