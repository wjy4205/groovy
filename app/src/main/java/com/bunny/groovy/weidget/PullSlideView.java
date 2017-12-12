package com.bunny.groovy.weidget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * 可以滑动的布局
 */
public class PullSlideView extends LinearLayout {

    public PullSlideView(Context context) {
        this(context, null);

    }

    public PullSlideView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public PullSlideView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float startY = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float offset = event.getRawY() - startY;
                setTranslationY(offset);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        startY = event.getRawY();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
