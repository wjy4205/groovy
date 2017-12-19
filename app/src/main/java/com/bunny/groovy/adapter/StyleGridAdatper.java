package com.bunny.groovy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bunny.groovy.R;
import com.bunny.groovy.model.StyleModel;

import java.util.List;

/****************************************
 * 功能说明: 表演类型适配器
 *
 * Author: Created by bayin on 2017/12/19.
 ****************************************/

public class StyleGridAdatper extends RecyclerView.Adapter<StyleGridAdatper.StyleHolder> {
    private List<StyleModel> dataList;
    private Context mContext;
    private String selectedStyle;

    public StyleGridAdatper(List<StyleModel> dataList, String selected) {
        this.dataList = dataList;
        selectedStyle = selected;
    }

    @Override
    public StyleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_style_grid_layout, null, false);
        return new StyleHolder(inflate);
    }

    @Override
    public void onBindViewHolder(StyleHolder holder, final int position) {
        StyleModel styleModel = dataList.get(position);
        if (!TextUtils.isEmpty(styleModel.getTypeImg())) {
            Glide.with(mContext).load(styleModel.getTypeImg()).into(holder.mIvPic);
        } else holder.mIvPic.setImageResource(R.mipmap.icon_load_pic);
        holder.mTvCheckBox.setChecked(styleModel.isChecked());
        holder.mTvName.setText(styleModel.getTypeName());
        holder.mTvCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dataList.get(position).setChecked(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (dataList != null) return dataList.size();
        return 0;
    }

    public String getSelectStyles() {
        selectedStyle = "";
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).isChecked())
                selectedStyle = selectedStyle + dataList.get(i).getTypeName() + ",";
        }
        if (selectedStyle.length() > 0)
            return selectedStyle.substring(0, selectedStyle.length() - 1);
        return selectedStyle;
    }

    public void updateSelectedStyle(String selectedStyle) {
        if (TextUtils.isEmpty(selectedStyle)) return;
        String[] split = selectedStyle.split(",");
        for (int i = 0; i < dataList.size(); i++) {
            for (int j = 0; j < split.length; j++) {
                if (split[j].equals(dataList.get(i)))
                    dataList.get(i).setChecked(true);
                else dataList.get(i).setChecked(false);
                break;
            }
        }
        notifyDataSetChanged();
    }

    static class StyleHolder extends RecyclerView.ViewHolder {

        private final CheckBox mTvCheckBox;
        private final TextView mTvName;
        private final ImageView mIvPic;

        public StyleHolder(View itemView) {
            super(itemView);
            mIvPic = (ImageView) itemView.findViewById(R.id.item_style_grid_iv_pic);
            mTvName = (TextView) itemView.findViewById(R.id.item_style_grid_tv_name);
            mTvCheckBox = (CheckBox) itemView.findViewById(R.id.item_style_grid_cb);
        }
    }

}
