package com.bunny.groovy.weidget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.bunny.groovy.utils.UIUtils;

/**
 * 自定义toolbar高度   80px
 */
public class ToolbarAutoHeight extends Toolbar {
    public ToolbarAutoHeight(Context context) {
        super(context);
    }

    public ToolbarAutoHeight(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolbarAutoHeight(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        if(params!=null){
            setMinimumHeight(UIUtils.dip2Px(80));
            params.height= UIUtils.dip2Px(80);
        }
        super.setLayoutParams(params);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(this.getContext(), attrs);
    }

}
