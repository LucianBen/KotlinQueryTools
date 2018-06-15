package com.ben.kotlindemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.Scroller;

public class MyHorizontalScrollView extends HorizontalScrollView {
    Scroller scroller;

    public MyHorizontalScrollView(Context context) {
        super(context);
        scroller = new Scroller(context);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        scroller = new Scroller(context);
    }

    /*
    当手动滑动完成时调用回调方法。
     */
    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (mOnOverScrolledListener != null) {
            mOnOverScrolledListener.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        }
    }

    /*
    设置回调接口
     */
    public void setOnOverScrolledListener(OnOverScrolledListener mOnOverScrolledListener) {
        this.mOnOverScrolledListener = mOnOverScrolledListener;
    }

    private OnOverScrolledListener mOnOverScrolledListener;

    /*
    定义回调接口
     */
    public interface OnOverScrolledListener {
        public abstract void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY);
    }
}
