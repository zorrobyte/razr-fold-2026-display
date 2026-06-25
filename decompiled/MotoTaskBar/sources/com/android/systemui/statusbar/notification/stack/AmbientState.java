package com.android.systemui.statusbar.notification.stack;

import android.content.Context;
import android.util.MathUtils;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.res.R$dimen;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm;
import com.android.systemui.statusbar.policy.AvalancheController;

/* JADX INFO: loaded from: classes.dex */
public class AmbientState implements Dumpable {
    private float mAppearFraction;
    private final AvalancheController mAvalancheController;
    private int mBaseZHeight;
    private boolean mClearAllInProgress;
    private int mContentHeight;
    private float mCurrentScrollVelocity;
    private boolean mDozing;
    private boolean mExpansionChanging;
    private float mFractionToShade;
    private float mHideAmount;
    private boolean mHideSensitive;
    private boolean mIsClosing;
    private ExpandableView mLastVisibleBackgroundChild;
    private int mLayoutHeight;
    private int mLayoutMaxHeight;
    private int mLayoutMinHeight;
    private float mMaxHeadsUpTranslation;
    private float mOverScrollBottomAmount;
    private float mOverScrollTopAmount;
    private ExpandableNotificationRow mPulsingRow;
    private int mScrollY;
    private final StackScrollAlgorithm.SectionProvider mSectionProvider;
    private float mStackEndHeight;
    private int mStackTopMargin;
    private float mStackTranslation;
    private int mTopPadding;
    private ExpandableNotificationRow mTrackedHeadsUpRow;
    private boolean mUseSplitShade;
    private int mZDistanceBetweenElements;
    private float mPulseHeight = 100000.0f;
    private float mDozeAmount = 0.0f;
    private float mStackY = 0.0f;
    private float mStackHeight = 0.0f;
    private boolean mIsFlingRequiredAfterLockScreenSwipeUp = false;

    public AmbientState(Context context, DumpManager dumpManager, StackScrollAlgorithm.SectionProvider sectionProvider, AvalancheController avalancheController) {
        this.mSectionProvider = sectionProvider;
        this.mAvalancheController = avalancheController;
        reload(context);
        dumpManager.registerDumpable(this);
    }

    private static int getBaseHeight(int i) {
        return 0;
    }

    private static int getZDistanceBetweenElements(Context context) {
        return Math.max(1, context.getResources().getDimensionPixelSize(R$dimen.z_distance_between_notifications));
    }

    public float getAppearFraction() {
        return this.mAppearFraction;
    }

    public float getDozeAmount() {
        return this.mDozeAmount;
    }

    public float getFractionToShade() {
        return this.mFractionToShade;
    }

    public float getHideAmount() {
        return this.mHideAmount;
    }

    public int getInnerHeight() {
        return getInnerHeight(false);
    }

    public int getInnerHeight(boolean z) {
        int iMax = Math.max(this.mLayoutMinHeight, Math.min(this.mLayoutHeight, this.mContentHeight) - this.mTopPadding);
        if (z) {
            return iMax;
        }
        float f = iMax;
        return (int) MathUtils.lerp(f, Math.min(this.mPulseHeight, f), this.mDozeAmount);
    }

    public int getLayoutMaxHeight() {
        return this.mLayoutMaxHeight;
    }

    public float getMaxHeadsUpTranslation() {
        return this.mMaxHeadsUpTranslation;
    }

    public float getOverScrollAmount(boolean z) {
        return z ? this.mOverScrollTopAmount : this.mOverScrollBottomAmount;
    }

    public int getScrollY() {
        return this.mScrollY;
    }

    public StackScrollAlgorithm.SectionProvider getSectionProvider() {
        return this.mSectionProvider;
    }

    public float getStackEndHeight() {
        return this.mStackEndHeight;
    }

    public float getStackHeight() {
        return this.mStackHeight;
    }

    public int getStackTopMargin() {
        return this.mStackTopMargin;
    }

    public float getStackTranslation() {
        return this.mStackTranslation;
    }

    public float getStackY() {
        return this.mStackY;
    }

    public int getTopPadding() {
        return this.mTopPadding;
    }

    public ExpandableNotificationRow getTrackedHeadsUpRow() {
        ExpandableNotificationRow expandableNotificationRow = this.mTrackedHeadsUpRow;
        if (expandableNotificationRow == null || !expandableNotificationRow.isAboveShelf()) {
            return null;
        }
        return this.mTrackedHeadsUpRow;
    }

    public boolean getUseSplitShade() {
        return this.mUseSplitShade;
    }

    public boolean isBypassEnabled() {
        return true;
    }

    public boolean isClearAllInProgress() {
        return this.mClearAllInProgress;
    }

    public boolean isClosing() {
        return this.mIsClosing;
    }

    public boolean isDozing() {
        return this.mDozing;
    }

    public boolean isExpansionChanging() {
        return this.mExpansionChanging;
    }

    public boolean isFlingRequiredAfterLockScreenSwipeUp() {
        return this.mIsFlingRequiredAfterLockScreenSwipeUp;
    }

    public boolean isFullyAwake() {
        return this.mDozeAmount == 0.0f;
    }

    public boolean isFullyHidden() {
        return this.mHideAmount == 1.0f;
    }

    public boolean isHiddenAtAll() {
        return this.mHideAmount != 0.0f;
    }

    public boolean isHideSensitive() {
        return this.mHideSensitive;
    }

    public boolean isOnKeyguard() {
        return false;
    }

    public boolean isPulseExpanding() {
        return (this.mPulseHeight == 100000.0f || this.mDozeAmount == 0.0f || this.mHideAmount == 1.0f) ? false : true;
    }

    public boolean isPulsingRow(ExpandableView expandableView) {
        return this.mPulsingRow == expandableView;
    }

    public boolean isShadeExpanded() {
        return true;
    }

    public void reload(Context context) {
        int zDistanceBetweenElements = getZDistanceBetweenElements(context);
        this.mZDistanceBetweenElements = zDistanceBetweenElements;
        this.mBaseZHeight = getBaseHeight(zDistanceBetweenElements);
    }

    public void setClearAllInProgress(boolean z) {
        this.mClearAllInProgress = z;
    }

    public void setContentHeight(int i) {
        this.mContentHeight = i;
    }

    public void setCurrentScrollVelocity(float f) {
        this.mCurrentScrollVelocity = f;
    }

    public void setFlingRequiredAfterLockScreenSwipeUp(boolean z) {
        this.mIsFlingRequiredAfterLockScreenSwipeUp = z;
    }

    public void setHideSensitive(boolean z) {
        this.mHideSensitive = z;
    }

    public void setLastVisibleBackgroundChild(ExpandableView expandableView) {
        this.mLastVisibleBackgroundChild = expandableView;
    }

    public void setLayoutHeight(int i) {
        this.mLayoutHeight = i;
    }

    public void setLayoutMaxHeight(int i) {
        this.mLayoutMaxHeight = i;
    }

    public void setLayoutMinHeight(int i) {
        this.mLayoutMinHeight = i;
    }

    public void setOverScrollAmount(float f, boolean z) {
        if (z) {
            this.mOverScrollTopAmount = f;
        } else {
            this.mOverScrollBottomAmount = f;
        }
    }

    public void setPulsingRow(ExpandableNotificationRow expandableNotificationRow) {
        this.mPulsingRow = expandableNotificationRow;
    }

    public void setScrollY(int i) {
        this.mScrollY = Math.max(i, 0);
    }

    public void setStackEndHeight(float f) {
        this.mStackEndHeight = f;
    }

    public void setStackHeight(float f) {
        this.mStackHeight = f;
    }

    public void setUseSplitShade(boolean z) {
        this.mUseSplitShade = z;
    }
}
