package com.bunny.groovy.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bunny.groovy.R;
import com.bunny.groovy.model.ShowHistoryModel;
import com.bunny.groovy.model.ShowModel;
import com.bunny.groovy.ui.fragment.releaseshow.ShowDetailFragment;
import com.bunny.groovy.weidget.HeightLightTextView;

import java.util.List;

/****************************************
 * 功能说明:  schedule时间计划 列表适配器
 *
 * Author: Created by bayin on 2017/12/25.
 ****************************************/

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.HisHolder> {
    private List<ShowModel> mList;
    private Activity mContext;

    public ScheduleAdapter(List<ShowModel> list) {
        mList = list;
    }

    public void refresh(List<ShowModel> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public HisHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = (Activity) parent.getContext();
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.include_recent_show_layout, null, false);
        return new HisHolder(inflate);
    }

    @Override
    public void onBindViewHolder(HisHolder holder, int position) {
        final ShowModel bean = mList.get(position);
        String performState = bean.getPerformState();
        holder.tvStatus.setVisibility(View.VISIBLE);
        switch (performState) {
            case "0"://待验证
                holder.tvStatus.setText("Verification");
                break;
            case "1"://已发布
                holder.tvStatus.setText("Confirmed");
                break;
            case "2"://已取消
                holder.tvStatus.setText("Rejected");
                break;
        }
        Glide.with(mContext).load(bean.getHeadImg())
                .placeholder(R.mipmap.venue_instead_pic)
                .error(R.mipmap.venue_instead_pic)
                .into(holder.mIvHead);
        holder.mTvStar.setText(bean.getVenueScore());
        holder.mTvName.setText(bean.getVenueName());
        holder.mTvStyle.setText(bean.getPerformType());
        holder.mTvShowTime.setText(bean.getPerformDate() + " " + bean.getPerformTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(ShowDetailFragment.KEY_SHOW_BEAN, bean);
                ShowDetailFragment.launch(mContext, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mList != null) return mList.size();
        return 0;
    }

    static class HisHolder extends RecyclerView.ViewHolder {
        private final ImageView mIvHead;
        private final HeightLightTextView mTvName;
        private final TextView mTvStar;
        private final TextView mTvStyle;
        private final TextView mTvShowTime;
        private final ImageView ivEdit;
        private final TextView tvStatus;

        public HisHolder(View itemView) {
            super(itemView);
            mIvHead = (ImageView) itemView.findViewById(R.id.nextshow_iv_head);
            mTvName = (HeightLightTextView) itemView.findViewById(R.id.nextshow_tv_performerName);
            mTvStar = (TextView) itemView.findViewById(R.id.nextshow_tv_performerStar);
            mTvStyle = (TextView) itemView.findViewById(R.id.nextshow_tv_address);
            mTvShowTime = (TextView) itemView.findViewById(R.id.nextshow_tv_time);
            tvStatus = (TextView) itemView.findViewById(R.id.nextshow_tv_status);
            ivEdit = (ImageView) itemView.findViewById(R.id.nextshow_iv_edit);
        }
    }
}
