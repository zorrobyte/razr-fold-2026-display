package com.android.systemui.statusbar.notification.row;

/* JADX INFO: loaded from: classes.dex */
public final class RowContentBindParams {
    private int mContentViews = 3;
    private int mDirtyContentViews = 3;
    private boolean mUseIncreasedHeadsUpHeight;
    private boolean mUseIncreasedHeight;
    private boolean mUseMinimized;
    private boolean mViewsNeedReinflation;

    void clearDirtyContentViews() {
        this.mDirtyContentViews = 0;
    }

    public int getContentViews() {
        return this.mContentViews;
    }

    public int getDirtyContentViews() {
        return this.mDirtyContentViews;
    }

    public void markContentViewsFreeable(int i) {
        int i2 = this.mContentViews;
        int i3 = i & i2;
        this.mContentViews = i2 & (~i3);
        this.mDirtyContentViews = i3 | this.mDirtyContentViews;
    }

    public boolean needsReinflation() {
        return this.mViewsNeedReinflation;
    }

    public void rebindAllContentViews() {
        this.mDirtyContentViews = this.mContentViews;
    }

    public void requireContentViews(int i) {
        int i2 = this.mContentViews;
        int i3 = i & (~i2);
        this.mContentViews = i2 | i3;
        this.mDirtyContentViews = i3 | this.mDirtyContentViews;
    }

    public void setNeedsReinflation(boolean z) {
        this.mViewsNeedReinflation = z;
        this.mDirtyContentViews = this.mContentViews | this.mDirtyContentViews;
    }

    public void setUseIncreasedCollapsedHeight(boolean z) {
        if (this.mUseIncreasedHeight != z) {
            this.mDirtyContentViews |= 1;
        }
        this.mUseIncreasedHeight = z;
    }

    public void setUseIncreasedHeadsUpHeight(boolean z) {
        if (this.mUseIncreasedHeadsUpHeight != z) {
            this.mDirtyContentViews |= 4;
        }
        this.mUseIncreasedHeadsUpHeight = z;
    }

    public void setUseMinimized(boolean z) {
        if (this.mUseMinimized != z) {
            this.mDirtyContentViews |= 3;
        }
        this.mUseMinimized = z;
    }

    public String toString() {
        return String.format("RowContentBindParams[mContentViews=%x mDirtyContentViews=%x mUseMinimized=%b mUseIncreasedHeight=%b mUseIncreasedHeadsUpHeight=%b mViewsNeedReinflation=%b]", Integer.valueOf(this.mContentViews), Integer.valueOf(this.mDirtyContentViews), Boolean.valueOf(this.mUseMinimized), Boolean.valueOf(this.mUseIncreasedHeight), Boolean.valueOf(this.mUseIncreasedHeadsUpHeight), Boolean.valueOf(this.mViewsNeedReinflation));
    }

    public boolean useIncreasedHeadsUpHeight() {
        return this.mUseIncreasedHeadsUpHeight;
    }

    public boolean useIncreasedHeight() {
        return this.mUseIncreasedHeight;
    }

    public boolean useMinimized() {
        return this.mUseMinimized;
    }
}
