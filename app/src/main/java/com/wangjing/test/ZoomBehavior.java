package com.wangjing.test;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

/**
 * create by wangjing on 2019/12/11 0011
 * description:
 */
public class ZoomBehavior extends ViewOffsetBehavior {

    private static final String TAG = ZoomBehavior.class.getSimpleName();

    private static int resolveGravity(int gravity) {
        return gravity == 0 ? 8388659 : gravity;
    }

    private final Rect tempRect1 = new Rect();
    private final Rect tempRect2 = new Rect();

    private int headOriginTop;

    public ZoomBehavior() {
    }

    public ZoomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof BLAppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        BLAppBarLayout BLAppBarLayout = (BLAppBarLayout) dependency;
        int offset = BLAppBarLayout.getTop() - headOriginTop;
        Log.e(TAG, "onDependentViewChanged: " + offset);
        if (offset > 0) {
            setTopAndBottomOffset(0);
            float scale = (child.getHeight() + offset * 2) / (child.getHeight() * 1f) ;
            child.setScaleX(scale);
            child.setScaleY(scale);
        } else {
            child.setScaleX(1f);
            child.setScaleY(1f);
            setTopAndBottomOffset(offset);
        }
        return super.onDependentViewChanged(parent, child, dependency);
    }


    @Override
    public void layoutChild(@NonNull CoordinatorLayout parent, @NonNull View child, int layoutDirection) {
        parent.setClipChildren(false);
        List<View> dependencies = parent.getDependencies(child);
        View header = this.findFirstDependency(dependencies);
        ViewCompat.setElevation(child, -1f);
        if (header != null) {
            headOriginTop = header.getTop();
            header.setBackgroundColor(Color.TRANSPARENT);
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            Rect available = this.tempRect1;
            available.set(parent.getPaddingLeft() + lp.leftMargin, lp.topMargin, parent.getWidth() - parent.getPaddingRight() - lp.rightMargin, child.getHeight() - lp.bottomMargin);
            WindowInsetsCompat parentInsets = parent.getLastWindowInsets();
            if (parentInsets != null && ViewCompat.getFitsSystemWindows(parent) && !ViewCompat.getFitsSystemWindows(child)) {
                available.left += parentInsets.getSystemWindowInsetLeft();
                available.right -= parentInsets.getSystemWindowInsetRight();
            }
            Rect out = this.tempRect2;
            GravityCompat.apply(resolveGravity(lp.gravity), child.getMeasuredWidth(), child.getMeasuredHeight(), available, out, layoutDirection);
            child.layout(out.left, out.top, out.right, out.bottom);
        } else {
            parent.onLayoutChild(child, layoutDirection);
        }
    }


    private BLAppBarLayout findFirstDependency(List<View> views) {
        int i = 0;
        for (int z = views.size(); i < z; ++i) {
            View view = views.get(i);
            if (view instanceof BLAppBarLayout) {
                return (BLAppBarLayout) view;
            }
        }
        return null;
    }
}
