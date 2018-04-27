package com.bunny.groovy.ui.fragment.apply;

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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bunny.groovy.R;
import com.bunny.groovy.adapter.StyleGridAdapter;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.FragmentContainerActivity;
import com.bunny.groovy.model.StyleModel;
import com.bunny.groovy.presenter.ApplyVenuePresenter;
import com.bunny.groovy.utils.DateUtils;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.view.IApplyVenueView;
import com.bunny.groovy.weidget.TimePopupWindow;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/****************************************
 * 功能说明:  普通用户地图模式搜索过滤界面
 *
 * Author: Created by wjy on 2018/3/16.
 ****************************************/

public class UserFilterFragment extends BaseFragment<ApplyVenuePresenter> implements IApplyVenueView, TimePopupWindow.OnTimeConfirmListener {

    public final static String KEY_DISTANCE = "key_distance";
    public final static String KEY_START_TIME = "key_start_time";
    public final static String KEY_END_TIME = "key_end_time";
    public final static String KEY_VENUE_TYPE = "key_venue_type";
    public final static String KEY_PERFORM_TYPE = "key_perform_type";

    private Calendar mSelectDate = Calendar.getInstance();//选择的日期
    private String mStartTime = "";//开始时间
    private String mEndTime = "";//结束时间
    private int mDistance = 25;//距离
    private StringBuilder mVenueType;//演播厅类型
    private String mPerformType;//表演类型
    private List<StyleModel> styleList;
    private TimePopupWindow mTimePop;
    private PopupWindow mPopupWindow;
    private StyleGridAdapter mAdapter;
    private CheckBox mCheckList[];//演播厅类型多选框


    public static void launchForResult(Activity from, Bundle bundle, int requestCode) {
        bundle.putString(FragmentContainerActivity.FRAGMENT_TITLE, "FILTER");
        FragmentContainerActivity.launchForResult(from, UserFilterFragment.class, bundle, requestCode);
    }

    @Bind(R.id.filter_seekbar)
    SeekBar mSeekBar;
    @Bind(R.id.filter_time)
    EditText mEtTime;
    @Bind(R.id.filter_style)
    EditText mEtPerformStyle;
    @Bind(R.id.service_choose_1)
    CheckBox mCb1;
    @Bind(R.id.service_choose_2)
    CheckBox mCb2;
    @Bind(R.id.service_choose_3)
    CheckBox mCb3;
    @Bind(R.id.filter_tv_distance)
    TextView mFilterDistance;

    //弹出选择style窗口
    @OnClick(R.id.filter_style)
    public void showStyle() {
        UIUtils.hideSoftInput(mEtPerformStyle);
        if (styleList == null || styleList.size() == 0)
            mPresenter.requestStyle();
        else showStylePop(styleList);
    }

    //弹出时间选择窗口
    @OnClick(R.id.filter_time)
    public void selectTime() {
        UIUtils.hideSoftInput(mEtTime);
        mTimePop.showTimeChoosePop(mEtTime);
    }

    @OnClick(R.id.tv_apply)
    public void apply() {
        //获取距离
        String distance = String.valueOf(mSeekBar.getProgress());
        //获取演播厅类型
        mVenueType = new StringBuilder();
        int length = mCheckList.length;
        for (int i = 0; i < length; i++) {
            if (mCheckList[i].isChecked()) {
                if (mVenueType.toString().length() != 0) {
                    mVenueType.append(",");
                }
                if (i == 0) {
                    mVenueType.append("Exclude 21+");
                } else {
                    mVenueType.append(mCheckList[i].getText().toString().trim());
                }
            }
        }
        Intent intent = new Intent();
        intent.putExtra(KEY_DISTANCE, distance);
        intent.putExtra(KEY_VENUE_TYPE, mVenueType.toString());
        intent.putExtra(KEY_PERFORM_TYPE, mEtPerformStyle.getText().toString().trim());
        intent.putExtra(KEY_START_TIME, TextUtils.isEmpty(mStartTime) ? "" : DateUtils.getFormatTime(mSelectDate.getTime(), mStartTime));
        intent.putExtra(KEY_END_TIME, TextUtils.isEmpty(mEndTime) ? "" : DateUtils.getFormatTime(mSelectDate.getTime(), mEndTime));
        mActivity.setResult(Activity.RESULT_OK, intent);
        mActivity.finish();
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
//        mDistance = getArguments().getInt(KEY_DISTANCE);
//        mPerformType = getArguments().getString(KEY_PERFORM_TYPE);
//        String venueType = getArguments().getString(KEY_VENUE_TYPE);
//        mEtPerformStyle.setText(mPerformType);
        mEtPerformStyle.setFocusable(false);
        mCheckList = new CheckBox[3];
        mCheckList[0] = mCb1;
        mCheckList[1] = mCb2;
        mCheckList[2] = mCb3;
//        String startDate = getArguments().getString(KEY_START_TIME);
//        String endDate = getArguments().getString(KEY_END_TIME);
//        if (!TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(endDate)) {
//            Date date = null;
//            try {
//                date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startDate);
//                startTime = startDate.split(" ")[1];
//                endTime = endDate.split(" ")[1];
//                mEtTime.setText(startDate + "-" + endTime);
//            } catch (Exception e) {
//            }
//            if (date != null) mSelectDate.setTime(date);
//
//        }
        mEtTime.setFocusable(false);
//        if (!TextUtils.isEmpty(venueType)) {
//            mVenueType = new StringBuilder(venueType);
//            String types[] = venueType.split(",");
//            for (String t : types) {
//                for (int i = 0; i < mCheckList.length; i++) {
//                    if (TextUtils.equals(mCheckList[i].getText().toString().trim(), t)) {
//                        mCheckList[i].setChecked(true);
//                        break;
//                    }
//                }
//            }
//        }
        mSeekBar.setProgress(mDistance);
        mFilterDistance.setText(mDistance + "mi");
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mFilterDistance.setText(i + "mi");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mTimePop = new TimePopupWindow(getActivity());
        mTimePop.setListener(this);
    }

    @Override
    public Activity get() {
        return mActivity;
    }

    @Override
    public void showStylePop(List<StyleModel> modelList) {
        UIUtils.hideSoftInput(mEtPerformStyle);
        styleList = modelList;
        if (mPopupWindow == null)
            initStylePop(modelList);
        mPopupWindow.showAtLocation(mEtPerformStyle, Gravity.CENTER, 0, 0);
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
        CheckBox checkBox = popview.findViewById(R.id.style_num_checkbox);
        checkBox.setVisibility(View.VISIBLE);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mAdapter.selectAll(b);
            }
        });
        TextView textView = popview.findViewById(R.id.style_num_text);
        textView.setText("SELECT ALL");
        recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mAdapter = new StyleGridAdapter(modelList, mEtPerformStyle.getText().toString().trim());
        mAdapter.setSelectNum(100);
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
                mEtPerformStyle.setText(mAdapter.getSelectStyles());
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
        return R.layout.fragment_user_map_filter_layout;
    }

    @Override
    protected void loadData() {
    }

    @Override
    public void chooseTime(String startTime, String endTime, Calendar selectDate) {
        mStartTime = startTime;
        mEndTime = endTime;
        mSelectDate = selectDate;
        mEtTime.setText(DateUtils.getFormatTime(mSelectDate.getTime(), startTime) + "-" + endTime);
    }
}
