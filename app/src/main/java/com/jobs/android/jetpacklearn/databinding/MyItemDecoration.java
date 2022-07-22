package com.jobs.android.jetpacklearn.databinding;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 作者    你的名字
 * 时间    2022/7/22 9:44
 * 文件    JetpackLearn
 * 描述
 */
public class MyItemDecoration extends RecyclerView.ItemDecoration {

    private Paint mPaint;
    private Rect mRect;

    public MyItemDecoration(){
        mPaint = new Paint();
        mPaint.setColor(Color.YELLOW);

        mRect = new Rect();
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        Log.e("aaaa","getItemOffsets");
        int childPosition = parent.getChildAdapterPosition(view);
        if(childPosition == parent.getChildCount()){//最后一条数据不设置分割线
            outRect.bottom = 0;
        }else {
            outRect.bottom = 10;
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        Log.e("aaaa","onDraw");
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int top = view.getBottom();
            int bottom = view.getBottom() + 10;
            mRect.set(left, top, right, bottom);
            c.drawRect(mRect, mPaint);
        }
        mRect.set(0, 0, parent.getWidth(), parent.getHeight());
        //c.drawRect(mRect, mPaint);//在RecyclerView背景上绘制整个黄色，相当于给RecyclerView设置背景
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        Log.e("aaaa","onDrawOver");
    }
}
