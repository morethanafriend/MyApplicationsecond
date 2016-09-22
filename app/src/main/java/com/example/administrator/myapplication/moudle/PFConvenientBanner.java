package com.example.administrator.myapplication.moudle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.bigkoo.convenientbanner.ConvenientBanner;

import cn.piaofun.common.util.DisplayUtil;

/**
 * Created by Administrator on 2016/9/22.
 */
public class PFConvenientBanner extends ConvenientBanner {

    private int defaultX = -999;

    private int downX = defaultX;
    private int currentX;

    public PFConvenientBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = (int) ev.getX();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                downX = defaultX;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean isGrabbed() {
        return downX != defaultX && Math.abs(downX - currentX) > DisplayUtil.dip2px(getContext(), 1);
    }
}
