package com.bunny.groovy.ui.fragment.releaseshow;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.FragmentContainerActivity;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.model.StyleModel;
import com.bunny.groovy.presenter.InviteMusicianPresenter;
import com.bunny.groovy.utils.DateUtils;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.utils.Utils;
import com.bunny.groovy.view.ISetFileView;
import com.bunny.groovy.weidget.TimePopupWindow;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 邀请表演者
 * Created by Administrator on 2017/12/16.
 */

public class InviteMusicianFragment extends BaseFragment<InviteMusicianPresenter> implements ISetFileView, TimePopupWindow.OnTimeConfirmListener {

    private TimePopupWindow mTimePop;
    private Calendar mSelectDate = Calendar.getInstance();//选择的日期
    private String mStartTime = "";//开始时间
    private String mEndTime = "";//结束时间
    private static PerformerUserModel mPerformerModel;

    @Bind(R.id.iv_musician_head_pic)
    CircleImageView mHeadView;
    @Bind(R.id.musician_tv_name)
    TextView mNameView;
    @Bind(R.id.musician_tv_performerStar)
    TextView mStarView;
    @Bind(R.id.musician_tv_type)
    TextView mStyleView;
    @Bind(R.id.musician_tv_phone)
    TextView mPhoneView;


    public static void launch(Activity from, PerformerUserModel model) {
        mPerformerModel = model;
        Bundle bundle = new Bundle();
        bundle.putString(FragmentContainerActivity.FRAGMENT_TITLE, "INVITE");
        FragmentContainerActivity.launch(from, InviteMusicianFragment.class, bundle);
    }

    @Bind(R.id.release_et_time)
    EditText etTime;

    @Bind(R.id.release_et_bio)
    EditText etBio;

    @OnClick(R.id.tv_invite)
    public void invite() {
        //判断空
        if (UIUtils.isEdittextEmpty(etTime)) {
            UIUtils.showBaseToast("Please choose show time.");
            return;
        }
        if (UIUtils.isEdittextEmpty(etBio)) {
            UIUtils.showBaseToast("please input message.");
            return;
        }
        mPresenter.inviteMusician(mPerformerModel.getUserID(), DateUtils.getFormatTime(mSelectDate.getTime(), mStartTime),
                DateUtils.getFormatTime(mSelectDate.getTime(), mEndTime), etBio.getText().toString().trim());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPerformerModel = null;
    }

    @OnClick(R.id.release_et_time)
    public void selectTime() {
        mTimePop.showTimeChoosePop(etBio);
    }

    @Override
    protected InviteMusicianPresenter createPresenter() {
        return new InviteMusicianPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_invite_musician_layout;
    }

    @Override
    protected void loadData() {
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        etTime.setFocusable(false);
        if (!TextUtils.isEmpty(mPerformerModel.getHeadImg())) {
            Glide.with(getActivity()).load(mPerformerModel.getHeadImg())
                    .placeholder(R.drawable.musicion_default_photo)
                    .error(R.drawable.musicion_default_photo).dontAnimate()
                    .into(mHeadView);
        } else {
            mHeadView.setImageResource(R.drawable.musicion_default_photo);
        }
        mNameView.setText(mPerformerModel.getStageName());
        mStarView.setText(Utils.getStar(mPerformerModel.getStarLevel()));
        mStyleView.setText(mPerformerModel.getPerformTypeName());
        mPhoneView.setText(mPerformerModel.getTelephone());
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
    public void chooseTime(String startTime, String endTime, Calendar selectDate) {
        mStartTime = startTime;
        mEndTime = endTime;
        mSelectDate = selectDate;
        etTime.setText(DateUtils.getFormatTime(mSelectDate.getTime(), startTime) + "-" + endTime);
    }
}
