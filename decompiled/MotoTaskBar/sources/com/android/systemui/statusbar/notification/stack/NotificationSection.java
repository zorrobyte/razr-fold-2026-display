package com.android.systemui.statusbar.notification.stack;

import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.view.View;
import com.android.systemui.statusbar.notification.row.ExpandableView;

/* JADX INFO: loaded from: classes.dex */
public class NotificationSection {
    private final int mBucket;
    private ExpandableView mFirstVisibleChild;
    private ExpandableView mLastVisibleChild;
    private final View mOwningView;
    private final Rect mBounds = new Rect();
    private final Rect mCurrentBounds = new Rect(-1, -1, -1, -1);
    private final Rect mStartAnimationRect = new Rect();
    private final Rect mEndAnimationRect = new Rect();
    private ObjectAnimator mTopAnimator = null;
    private ObjectAnimator mBottomAnimator = null;

    NotificationSection(View view, int i) {
        this.mOwningView = view;
        this.mBucket = i;
    }

    public int getBucket() {
        return this.mBucket;
    }

    public ExpandableView getFirstVisibleChild() {
        return this.mFirstVisibleChild;
    }

    public ExpandableView getLastVisibleChild() {
        return this.mLastVisibleChild;
    }

    public boolean setFirstVisibleChild(ExpandableView expandableView) {
        boolean z = this.mFirstVisibleChild != expandableView;
        this.mFirstVisibleChild = expandableView;
        return z;
    }

    public boolean setLastVisibleChild(ExpandableView expandableView) {
        boolean z = this.mLastVisibleChild != expandableView;
        this.mLastVisibleChild = expandableView;
        return z;
    }
}
