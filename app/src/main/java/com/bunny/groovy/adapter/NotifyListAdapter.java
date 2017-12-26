package com.bunny.groovy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/12/27.
 */

public class NotifyListAdapter extends RecyclerView.Adapter<NotifyListAdapter.NotyfyHolder> {

    @Override
    public NotyfyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(NotyfyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class NotyfyHolder extends RecyclerView.ViewHolder{

        public NotyfyHolder(View itemView) {
            super(itemView);
        }
    }
}
