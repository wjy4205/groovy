package com.bunny.groovy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    }

    @Override
    public int getItemCount() {
        if (mList != null) return mList.size();
        return 0;
    }

    static class NotyfyHolder extends RecyclerView.ViewHolder {

        public NotyfyHolder(View itemView) {
            super(itemView);
        }
    }
}
