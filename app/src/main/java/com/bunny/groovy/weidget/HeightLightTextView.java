package com.bunny.groovy.weidget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

/****************************************
 * 功能说明:  高亮显示的textview
 *
 * Author: Created by bayin on 2017/12/19.
 ****************************************/

public class HeightLightTextView extends android.support.v7.widget.AppCompatTextView {
    public HeightLightTextView(Context context) {
        super(context);
    }

    public HeightLightTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HeightLightTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTextHeighLight(String text, String heighlight) {
        if (TextUtils.isEmpty(text)) setText("");
        else {
            if (!TextUtils.isEmpty(heighlight)) {
                String tempSource = text.toLowerCase();
                String tempLight = heighlight.toLowerCase();
                if (tempSource.contains(tempLight)) {
                    //设置高亮
                    int start = tempSource.indexOf(tempLight);
                    SpannableStringBuilder builder = new SpannableStringBuilder(text);
                    builder.setSpan(new ForegroundColorSpan(Color.parseColor("#7B87F1")), start, start + heighlight.length(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    setText(builder);
                } else setText(text);
            } else {
                setText(text);
            }
        }
    }
}
