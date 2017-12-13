package com.bunny.groovy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bunny.groovy.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 音乐列表适配器
 * Created by Administrator on 2017/12/13.
 */

public class MusciAdapter extends RecyclerView.Adapter<MusciAdapter.MHodler> {

    private ArrayList<HashMap<String, String>> listData;

    public MusciAdapter(ArrayList<HashMap<String, String>> list) {
        this.listData = list;
    }

    @Override
    public MHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music_layout, parent, false);
        return new MHodler(inflate);
    }

    @Override
    public void onBindViewHolder(MHodler holder, int position) {
        HashMap<String, String> map = listData.get(position);
        holder.tvName.setText(String.valueOf(map.get("file_name")));
        holder.tvSize.setText(String.valueOf(map.get("file_size")));
    }

    @Override
    public int getItemCount() {
        if (listData != null) return listData.size();
        return 0;
    }

    public class MHodler extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvSize;

        public MHodler(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.item_music_name);
            tvSize = (TextView) itemView.findViewById(R.id.item_music_size);
        }
    }
}
