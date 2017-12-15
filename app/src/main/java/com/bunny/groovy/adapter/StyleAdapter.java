package com.bunny.groovy.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bunny.groovy.R;
import com.bunny.groovy.model.PerformStyleModel;
import com.bunny.groovy.model.PerformerUserModel;
import com.socks.library.KLog;

import java.util.List;

/****************************************
 * 功能说明:  表演类型的适配器
 *
 * Author: Created by bayin on 2017/12/15.
 ****************************************/

public class StyleAdapter extends BaseAdapter {
    private List<PerformStyleModel> dataList;
    private StringBuffer mStringBuffer = new StringBuffer();//选择的style

    public StyleAdapter(List<PerformStyleModel> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        if (dataList != null) return dataList.size();
        return 0;
    }

    @Override
    public PerformStyleModel getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyHolder holder;
        if (convertView == null) {
            holder = new MyHolder();
            convertView = View.inflate(parent.getContext(), R.layout.item_perform_style_layout, null);
            holder.tvName = (TextView) convertView.findViewById(R.id.item_style_tv_name);
            holder.mCheckBox = (CheckBox) convertView.findViewById(R.id.item_style_checkbox);
            convertView.setTag(holder);
        } else {
            holder = (MyHolder) convertView.getTag();
        }
        holder.tvName.setText(dataList.get(position).getTypeName());
        holder.mCheckBox.setChecked(dataList.get(position).isChecked());
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mCheckListener != null) {
                    mCheckListener.onCheck(dataList.get(position), isChecked);
                }
                dataList.get(position).setChecked(isChecked);
            }
        });
        return convertView;
    }

    public String getSelectStyle(){
        for (PerformStyleModel model:
             dataList) {
            if (model.isChecked())
                mStringBuffer.append(model.getTypeName()).append(",");
        }
        String substring = mStringBuffer.substring(0, mStringBuffer.length() - 1);
        KLog.a(substring);
        return substring;
    }

    private static class MyHolder {
        private TextView tvName;
        private CheckBox mCheckBox;
    }

    private CheckListener mCheckListener;

    public void setCheckListener(CheckListener listener) {
        mCheckListener = listener;
    }

    public interface CheckListener {
        void onCheck(PerformStyleModel styleModel, boolean isCheck);
    }
}
