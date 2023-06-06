package com.jobs.android.jetpacklearn.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class FollowView extends View {
    private int lastX, lastY;

    public FollowView(Context context) {
        super(context);
    }

    public FollowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FollowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("aaaa", "自定义组件被触摸");
        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = x - lastX;
                float moveY = y - lastY;
                layout(getLeft() + (int)moveX, getTop() + (int)moveY,
                        getRight() + (int)moveX, getBottom() + (int)moveY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
