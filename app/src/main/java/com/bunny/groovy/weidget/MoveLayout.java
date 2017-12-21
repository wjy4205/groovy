package com.bunny.groovy.weidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/12/21.
 ****************************************/

public class MoveLayout extends LinearLayout {

    private float mStartY;
    private int mRawLocation;
    private float mDownY;
    private boolean isAtTop = false;

    public MoveLayout(Context context) {
        super(context);
    }

    public MoveLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mRawLocation = (int) getTranslationY();
    }

    public void setLoaction(int y) {
        setTranslationY(y);
        mRawLocation = y;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = event.getRawY();
                mStartY = event.getRawY() - getTranslationY();
                break;
            case MotionEvent.ACTION_MOVE:
                float offset = event.getRawY() - mStartY;
                setTranslationY(offset);
                break;
            case MotionEvent.ACTION_UP:
                float distance = event.getRawY() - mDownY;
                if (distance > 0) {
                    animate().translationY(mRawLocation);
                    isAtTop = false;
                } else {
                    animate().translationY(0);
                    isAtTop = true;
                }
                break;
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return !isAtTop;
    }
}
