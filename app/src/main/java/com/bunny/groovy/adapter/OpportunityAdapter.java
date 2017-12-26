package com.bunny.groovy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bunny.groovy.R;
import com.bunny.groovy.model.OpportunityModel;

import java.util.List;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/12/26.
 ****************************************/

public class OpportunityAdapter extends RecyclerView.Adapter<OpportunityAdapter.MyHolder> {

    private List<OpportunityModel.PerformerOpportunityBean> mList;
    private Context mContext;

    public OpportunityAdapter(List<OpportunityModel.PerformerOpportunityBean> list) {
        mList = list;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_opp_detail_list_layout, null, false);
        return new MyHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        if (position == 0){
            holder.mTvTopLine.setVisibility(View.INVISIBLE);
            holder.mTvBottomLine.setVisibility(View.VISIBLE);
        }else if (position==mList.size()-1){
            holder.mTvTopLine.setVisibility(View.VISIBLE);
            holder.mTvBottomLine.setVisibility(View.INVISIBLE);
        }else {
            holder.mTvTopLine.setVisibility(View.VISIBLE);
            holder.mTvBottomLine.setVisibility(View.VISIBLE);
        }
        OpportunityModel.PerformerOpportunityBean bean = mList.get(position);
        holder.mTvPerformTime.setText(bean.getPerformDate()+" "+bean.getPerformTime());
        holder.mTvMessage.setText(bean.getPerformDesc());
    }

    @Override
    public int getItemCount() {
        if (mList!=null) return mList.size();
        return 0;
    }

    static class MyHolder extends RecyclerView.ViewHolder{

        private final View mTvTopLine;
        private final View mTvDot;
        private final View mTvBottomLine;
        private final TextView mTvPerformTime;
        private final TextView mTvMessage;
        private final TextView mTvApply;

        public MyHolder(View itemView) {
            super(itemView);
            mTvTopLine = itemView.findViewById(R.id.tvTopLine);
            mTvDot = itemView.findViewById(R.id.tvDot);
            mTvBottomLine = itemView.findViewById(R.id.tvBottomLine);
            mTvPerformTime = (TextView) itemView.findViewById(R.id.tvPerformTime);
            mTvMessage = (TextView) itemView.findViewById(R.id.tvMessage);
            mTvApply = (TextView) itemView.findViewById(R.id.tv_apply);
        }
    }
}
