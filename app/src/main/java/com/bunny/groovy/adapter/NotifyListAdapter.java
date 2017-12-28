package com.bunny.groovy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bunny.groovy.R;
import com.bunny.groovy.model.ShowModel;

import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */

public class NotifyListAdapter extends RecyclerView.Adapter<NotifyListAdapter.NotyfyHolder> {
    private List<ShowModel> mList;
    private int mTYPE;
    private Context mContext;
    private String Status_OK = "CONFIRMED";
    private String Status_NO = "REJECTED";

    private String Opp_confirm_perform = "has confirmed your application";
    private String Opp_reject_perform = "has rejected your application";

    public NotifyListAdapter(List<ShowModel> list, int type) {
        mList = list;
        mTYPE = type;
    }

    public void refresh(List<ShowModel> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public NotyfyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_notification_layout, null, false);

        return new NotyfyHolder(inflate);
    }

    @Override
    public void onBindViewHolder(NotyfyHolder holder, int position) {
        switch (mTYPE) {
            case 0:
                holder.llActionLayout.setVisibility(View.GONE);
                break;
            case 1:
                holder.llActionLayout.setVisibility(View.VISIBLE);
                break;
            case 2:
                holder.llActionLayout.setVisibility(View.GONE);
                break;
        }
        ShowModel showModel = mList.get(position);
        Glide.with(mContext).load(showModel.getHeadImg()).into(holder.ivHeader);
        holder.tvName.setText(showModel.getVenueName());
        //todo  处理状态
        holder.tvStatus.setText(showModel.getPerformState());
        holder.tvScore.setText(showModel.getVenueScore());
        holder.tvCreateTime.setText(showModel.getCreateDate());
        holder.tvPerformDate.setText(showModel.getPerformDate()+" "+showModel.getPerformTime());
    }

    @Override
    public int getItemCount() {
        if (mList != null) return mList.size();
        return 0;
    }

    static class NotyfyHolder extends RecyclerView.ViewHolder {

        private final View btConfirm;
        private final View btReject;
        private final View llActionLayout;
        private final TextView tvPerformDate;
        private final View btDetails;
        private final View btEmail;
        private final View btPhone;
        private final TextView tvMsg;
        private final TextView tvStatus;
        private final TextView tvScore;
        private final TextView tvName;
        private final ImageView ivHeader;
        private final TextView tvCreateTime;

        public NotyfyHolder(View itemView) {
            super(itemView);
            tvCreateTime = (TextView) findViewById(R.id.item_notification_tv_createTime);
            ivHeader = (ImageView) findViewById(R.id.item_notification_iv_header);
            tvName = (TextView) findViewById(R.id.item_notification_tv_name);
            tvScore = (TextView) findViewById(R.id.item_notification_tv_score);
            tvStatus = (TextView) findViewById(R.id.item_notification_tv_status);
            tvMsg = (TextView) findViewById(R.id.item_notification_tv_msg);
            btPhone = findViewById(R.id.item_notification_iv_phone);
            btEmail = findViewById(R.id.item_notification_iv_email);
            btDetails = findViewById(R.id.item_notification_tv_details);
            tvPerformDate = (TextView) findViewById(R.id.item_notification_tv_perform_date);
            llActionLayout = findViewById(R.id.item_notification_ll_action);
            btReject = findViewById(R.id.item_notification_tv_reject);
            btConfirm = findViewById(R.id.item_notification_tv_confirm);
        }

        private View findViewById(int res) {
            return itemView.findViewById(res);
        }
    }
}
