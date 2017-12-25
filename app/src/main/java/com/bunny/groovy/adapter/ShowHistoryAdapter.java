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
import com.bunny.groovy.model.ShowHistoryModel;
import com.bunny.groovy.weidget.HeightLightTextView;

import java.util.List;

/****************************************
 * 功能说明:  个人中心历史记录适配器
 *
 * Author: Created by bayin on 2017/12/25.
 ****************************************/

public class ShowHistoryAdapter extends RecyclerView.Adapter<ShowHistoryAdapter.HisHolder> {
    private List<ShowHistoryModel> mList;
    private Context mContext;

    public ShowHistoryAdapter(List<ShowHistoryModel> list) {
        mList = list;
    }

    public void refresh(List<ShowHistoryModel> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public HisHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.include_recent_show_layout, null, false);
        return new HisHolder(inflate);
    }

    @Override
    public void onBindViewHolder(HisHolder holder, int position) {
        ShowHistoryModel bean = mList.get(position);
        Glide.with(mContext).load(bean.getHeadImg())
                .placeholder(R.mipmap.venue_instead_pic)
                .error(R.mipmap.venue_instead_pic)
                .into(holder.mIvHead);
        holder.mTvStar.setText(bean.getVenueScore());
        holder.mTvName.setText(bean.getVenueName());
        holder.mTvStyle.setText(bean.getPerformType());
        holder.mTvShowTime.setText(bean.getPerformDate() + " " + bean.getPerformTime());
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

        public HisHolder(View itemView) {
            super(itemView);
            mIvHead = (ImageView) itemView.findViewById(R.id.nextshow_iv_head);
            mTvName = (HeightLightTextView) itemView.findViewById(R.id.nextshow_tv_performerName);
            mTvStar = (TextView) itemView.findViewById(R.id.nextshow_tv_performerStar);
            mTvStyle = (TextView) itemView.findViewById(R.id.nextshow_tv_address);
            mTvShowTime = (TextView) itemView.findViewById(R.id.nextshow_tv_time);
        }
    }
}
