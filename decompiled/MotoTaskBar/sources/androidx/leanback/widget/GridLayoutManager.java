package androidx.leanback.widget;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.FocusFinder;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.GridView;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.collection.CircularIntArray;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.leanback.widget.Grid;
import androidx.leanback.widget.WindowAlignment;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class GridLayoutManager extends RecyclerView.LayoutManager {
    private static final Rect sTempRect = new Rect();
    static int[] sTwoInts = new int[2];
    AudioManager mAudioManager;
    BaseGridView mBaseGridView;
    private ArrayList mChildViewHolderSelectedListeners;
    int mChildVisibility;
    final ViewsStateBundle mChildrenStates;
    GridLinearSmoothScroller mCurrentSmoothScroller;
    int[] mDisappearingPositions;
    private int mExtraLayoutSpace;
    int mExtraLayoutSpaceInPreLayout;
    private int mFixedRowSizeSecondary;
    int mFlag;
    int mFocusPosition;
    private int mFocusPositionOffset;
    private int mFocusScrollStrategy;
    private int mGravity;
    Grid mGrid;
    private final Grid.Provider mGridProvider;
    private int mHorizontalSpacing;
    private final ItemAlignment mItemAlignment;
    int mMaxPendingMoves;
    private int mMaxSizeSecondary;
    private final int[] mMeasuredDimension;
    int mNumRows;
    private int mNumRowsRequested;
    ArrayList mOnLayoutCompletedListeners;
    int mOrientation;
    private OrientationHelper mOrientationHelper;
    PendingMoveSmoothScroller mPendingMoveSmoothScroller;
    int mPositionDeltaInPreLayout;
    final SparseIntArray mPositionToRowInPostLayout;
    private int mPrimaryScrollExtra;
    RecyclerView.Recycler mRecycler;
    private final Runnable mRequestLayoutRunnable;
    private int[] mRowSizeSecondary;
    private int mRowSizeSecondaryRequested;
    private int mSaveContextLevel;
    int mScrollOffsetSecondary;
    private int mSizePrimary;
    float mSmoothScrollSpeedFactor;
    private int mSpacingPrimary;
    private int mSpacingSecondary;
    RecyclerView.State mState;
    int mSubFocusPosition;
    private int mVerticalSpacing;
    final WindowAlignment mWindowAlignment;

    abstract class GridLinearSmoothScroller extends LinearSmoothScroller {
        boolean mSkipOnStopInternal;

        GridLinearSmoothScroller() {
            super(GridLayoutManager.this.mBaseGridView.getContext());
        }

        @Override // androidx.recyclerview.widget.LinearSmoothScroller
        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            return super.calculateSpeedPerPixel(displayMetrics) * GridLayoutManager.this.mSmoothScrollSpeedFactor;
        }

        @Override // androidx.recyclerview.widget.LinearSmoothScroller
        protected int calculateTimeForScrolling(int i) {
            int iCalculateTimeForScrolling = super.calculateTimeForScrolling(i);
            if (GridLayoutManager.this.mWindowAlignment.mainAxis().getSize() > 0) {
                float size = (30.0f / GridLayoutManager.this.mWindowAlignment.mainAxis().getSize()) * i;
                if (iCalculateTimeForScrolling < size) {
                    return (int) size;
                }
            }
            return iCalculateTimeForScrolling;
        }

        @Override // androidx.recyclerview.widget.LinearSmoothScroller, androidx.recyclerview.widget.RecyclerView.SmoothScroller
        protected void onStop() {
            super.onStop();
            if (!this.mSkipOnStopInternal) {
                onStopInternal();
            }
            GridLayoutManager gridLayoutManager = GridLayoutManager.this;
            if (gridLayoutManager.mCurrentSmoothScroller == this) {
                gridLayoutManager.mCurrentSmoothScroller = null;
            }
            if (gridLayoutManager.mPendingMoveSmoothScroller == this) {
                gridLayoutManager.mPendingMoveSmoothScroller = null;
            }
        }

        protected void onStopInternal() {
            View viewFindViewByPosition = findViewByPosition(getTargetPosition());
            if (viewFindViewByPosition == null) {
                if (getTargetPosition() >= 0) {
                    GridLayoutManager.this.scrollToSelection(getTargetPosition(), 0, false, 0);
                    return;
                }
                return;
            }
            if (GridLayoutManager.this.mFocusPosition != getTargetPosition()) {
                GridLayoutManager.this.mFocusPosition = getTargetPosition();
            }
            if (GridLayoutManager.this.hasFocus()) {
                GridLayoutManager.this.mFlag |= 32;
                viewFindViewByPosition.requestFocus();
                GridLayoutManager.this.mFlag &= -33;
            }
            GridLayoutManager.this.dispatchChildSelected();
            GridLayoutManager.this.dispatchChildSelectedAndPositioned();
        }

        @Override // androidx.recyclerview.widget.LinearSmoothScroller, androidx.recyclerview.widget.RecyclerView.SmoothScroller
        protected void onTargetFound(View view, RecyclerView.State state, RecyclerView.SmoothScroller.Action action) {
            int i;
            int i2;
            if (GridLayoutManager.this.getScrollPosition(view, null, GridLayoutManager.sTwoInts)) {
                if (GridLayoutManager.this.mOrientation == 0) {
                    int[] iArr = GridLayoutManager.sTwoInts;
                    i2 = iArr[0];
                    i = iArr[1];
                } else {
                    int[] iArr2 = GridLayoutManager.sTwoInts;
                    int i3 = iArr2[1];
                    i = iArr2[0];
                    i2 = i3;
                }
                action.update(i2, i, calculateTimeForDeceleration((int) Math.sqrt((i2 * i2) + (i * i))), this.mDecelerateInterpolator);
            }
        }
    }

    final class LayoutParams extends RecyclerView.LayoutParams {
        private int[] mAlignMultiple;
        private int mAlignX;
        private int mAlignY;
        int mBottomInset;
        int mLeftInset;
        int mRightInset;
        int mTopInset;

        LayoutParams(int i, int i2) {
            super(i, i2);
        }

        LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        LayoutParams(LayoutParams layoutParams) {
            super((RecyclerView.LayoutParams) layoutParams);
        }

        LayoutParams(RecyclerView.LayoutParams layoutParams) {
            super(layoutParams);
        }

        int[] getAlignMultiple() {
            return this.mAlignMultiple;
        }

        int getAlignX() {
            return this.mAlignX;
        }

        int getAlignY() {
            return this.mAlignY;
        }

        ItemAlignmentFacet getItemAlignmentFacet() {
            return null;
        }

        int getOpticalHeight(View view) {
            return (view.getHeight() - this.mTopInset) - this.mBottomInset;
        }

        int getOpticalLeft(View view) {
            return view.getLeft() + this.mLeftInset;
        }

        int getOpticalLeftInset() {
            return this.mLeftInset;
        }

        int getOpticalRight(View view) {
            return view.getRight() - this.mRightInset;
        }

        int getOpticalRightInset() {
            return this.mRightInset;
        }

        int getOpticalTop(View view) {
            return view.getTop() + this.mTopInset;
        }

        int getOpticalTopInset() {
            return this.mTopInset;
        }

        int getOpticalWidth(View view) {
            return (view.getWidth() - this.mLeftInset) - this.mRightInset;
        }

        void setAlignX(int i) {
            this.mAlignX = i;
        }

        void setAlignY(int i) {
            this.mAlignY = i;
        }

        void setItemAlignmentFacet(ItemAlignmentFacet itemAlignmentFacet) {
        }

        void setOpticalInsets(int i, int i2, int i3, int i4) {
            this.mLeftInset = i;
            this.mTopInset = i2;
            this.mRightInset = i3;
            this.mBottomInset = i4;
        }
    }

    final class PendingMoveSmoothScroller extends GridLinearSmoothScroller {
        private int mPendingMoves;
        private final boolean mStaggeredGrid;

        PendingMoveSmoothScroller(int i, boolean z) {
            super();
            this.mPendingMoves = i;
            this.mStaggeredGrid = z;
            setTargetPosition(-2);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.SmoothScroller
        public PointF computeScrollVectorForPosition(int i) {
            int i2 = this.mPendingMoves;
            if (i2 == 0) {
                return null;
            }
            GridLayoutManager gridLayoutManager = GridLayoutManager.this;
            int i3 = ((gridLayoutManager.mFlag & 262144) == 0 ? i2 >= 0 : i2 <= 0) ? 1 : -1;
            return gridLayoutManager.mOrientation == 0 ? new PointF(i3, 0.0f) : new PointF(0.0f, i3);
        }

        void consumePendingMovesAfterLayout() {
            int i;
            if (this.mStaggeredGrid && (i = this.mPendingMoves) != 0) {
                this.mPendingMoves = GridLayoutManager.this.processSelectionMoves(true, i);
            }
            int i2 = this.mPendingMoves;
            if (i2 == 0 || ((i2 > 0 && GridLayoutManager.this.hasCreatedLastItem()) || (this.mPendingMoves < 0 && GridLayoutManager.this.hasCreatedFirstItem()))) {
                setTargetPosition(GridLayoutManager.this.mFocusPosition);
                stop();
            }
        }

        void consumePendingMovesBeforeLayout() {
            int i;
            int i2;
            View viewFindViewByPosition;
            if (this.mStaggeredGrid || (i = this.mPendingMoves) == 0) {
                return;
            }
            if (i > 0) {
                GridLayoutManager gridLayoutManager = GridLayoutManager.this;
                i2 = gridLayoutManager.mFocusPosition + gridLayoutManager.mNumRows;
            } else {
                GridLayoutManager gridLayoutManager2 = GridLayoutManager.this;
                i2 = gridLayoutManager2.mFocusPosition - gridLayoutManager2.mNumRows;
            }
            View view = null;
            while (this.mPendingMoves != 0 && (viewFindViewByPosition = findViewByPosition(i2)) != null) {
                if (GridLayoutManager.this.canScrollTo(viewFindViewByPosition)) {
                    GridLayoutManager gridLayoutManager3 = GridLayoutManager.this;
                    gridLayoutManager3.mFocusPosition = i2;
                    gridLayoutManager3.mSubFocusPosition = 0;
                    int i3 = this.mPendingMoves;
                    if (i3 > 0) {
                        this.mPendingMoves = i3 - 1;
                    } else {
                        this.mPendingMoves = i3 + 1;
                    }
                    view = viewFindViewByPosition;
                }
                i2 = this.mPendingMoves > 0 ? i2 + GridLayoutManager.this.mNumRows : i2 - GridLayoutManager.this.mNumRows;
            }
            if (view == null || !GridLayoutManager.this.hasFocus()) {
                return;
            }
            GridLayoutManager.this.mFlag |= 32;
            view.requestFocus();
            GridLayoutManager.this.mFlag &= -33;
        }

        void decreasePendingMoves() {
            int i = this.mPendingMoves;
            if (i > (-GridLayoutManager.this.mMaxPendingMoves)) {
                this.mPendingMoves = i - 1;
            }
        }

        void increasePendingMoves() {
            int i = this.mPendingMoves;
            if (i < GridLayoutManager.this.mMaxPendingMoves) {
                this.mPendingMoves = i + 1;
            }
        }

        @Override // androidx.leanback.widget.GridLayoutManager.GridLinearSmoothScroller
        protected void onStopInternal() {
            super.onStopInternal();
            this.mPendingMoves = 0;
            View viewFindViewByPosition = findViewByPosition(getTargetPosition());
            if (viewFindViewByPosition != null) {
                GridLayoutManager.this.scrollToView(viewFindViewByPosition, true);
            }
        }
    }

    final class SavedState implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: androidx.leanback.widget.GridLayoutManager.SavedState.1
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        Bundle mChildStates;
        int mIndex;

        SavedState() {
            this.mChildStates = Bundle.EMPTY;
        }

        SavedState(Parcel parcel) {
            this.mChildStates = Bundle.EMPTY;
            this.mIndex = parcel.readInt();
            this.mChildStates = parcel.readBundle(GridLayoutManager.class.getClassLoader());
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mIndex);
            parcel.writeBundle(this.mChildStates);
        }
    }

    public GridLayoutManager() {
        this(null);
    }

    GridLayoutManager(BaseGridView baseGridView) {
        this.mSmoothScrollSpeedFactor = 1.0f;
        this.mMaxPendingMoves = 10;
        this.mOrientation = 0;
        this.mOrientationHelper = OrientationHelper.createHorizontalHelper(this);
        this.mPositionToRowInPostLayout = new SparseIntArray();
        this.mFlag = 221696;
        this.mChildViewHolderSelectedListeners = null;
        this.mOnLayoutCompletedListeners = null;
        this.mFocusPosition = -1;
        this.mSubFocusPosition = 0;
        this.mFocusPositionOffset = 0;
        this.mGravity = 8388659;
        this.mNumRowsRequested = 1;
        this.mFocusScrollStrategy = 0;
        this.mWindowAlignment = new WindowAlignment();
        this.mItemAlignment = new ItemAlignment();
        this.mMeasuredDimension = new int[2];
        this.mChildrenStates = new ViewsStateBundle();
        this.mRequestLayoutRunnable = new Runnable() { // from class: androidx.leanback.widget.GridLayoutManager.1
            @Override // java.lang.Runnable
            public void run() {
                GridLayoutManager.this.requestLayout();
            }
        };
        this.mGridProvider = new Grid.Provider() { // from class: androidx.leanback.widget.GridLayoutManager.2
            @Override // androidx.leanback.widget.Grid.Provider
            public void addItem(Object obj, int i, int i2, int i3, int i4) {
                int i5;
                int i6;
                PendingMoveSmoothScroller pendingMoveSmoothScroller;
                View view = (View) obj;
                if (i4 == Integer.MIN_VALUE || i4 == Integer.MAX_VALUE) {
                    i4 = !GridLayoutManager.this.mGrid.isReversedFlow() ? GridLayoutManager.this.mWindowAlignment.mainAxis().getPaddingMin() : GridLayoutManager.this.mWindowAlignment.mainAxis().getSize() - GridLayoutManager.this.mWindowAlignment.mainAxis().getPaddingMax();
                }
                if (GridLayoutManager.this.mGrid.isReversedFlow()) {
                    i5 = i4 - i2;
                    i6 = i4;
                } else {
                    i6 = i2 + i4;
                    i5 = i4;
                }
                int rowStartSecondary = GridLayoutManager.this.getRowStartSecondary(i3) + GridLayoutManager.this.mWindowAlignment.secondAxis().getPaddingMin();
                GridLayoutManager gridLayoutManager = GridLayoutManager.this;
                int i7 = rowStartSecondary - gridLayoutManager.mScrollOffsetSecondary;
                gridLayoutManager.mChildrenStates.loadView(view, i);
                GridLayoutManager.this.layoutChild(i3, view, i5, i6, i7);
                if (!GridLayoutManager.this.mState.isPreLayout()) {
                    GridLayoutManager.this.updateScrollLimits();
                }
                GridLayoutManager gridLayoutManager2 = GridLayoutManager.this;
                if ((gridLayoutManager2.mFlag & 3) != 1 && (pendingMoveSmoothScroller = gridLayoutManager2.mPendingMoveSmoothScroller) != null) {
                    pendingMoveSmoothScroller.consumePendingMovesAfterLayout();
                }
                GridLayoutManager.this.getClass();
            }

            @Override // androidx.leanback.widget.Grid.Provider
            public int createItem(int i, boolean z, Object[] objArr, boolean z2) {
                GridLayoutManager gridLayoutManager = GridLayoutManager.this;
                View viewForPosition = gridLayoutManager.getViewForPosition(i - gridLayoutManager.mPositionDeltaInPreLayout);
                if (!((LayoutParams) viewForPosition.getLayoutParams()).isItemRemoved()) {
                    if (z2) {
                        if (z) {
                            GridLayoutManager.this.addDisappearingView(viewForPosition);
                        } else {
                            GridLayoutManager.this.addDisappearingView(viewForPosition, 0);
                        }
                    } else if (z) {
                        GridLayoutManager.this.addView(viewForPosition);
                    } else {
                        GridLayoutManager.this.addView(viewForPosition, 0);
                    }
                    int i2 = GridLayoutManager.this.mChildVisibility;
                    if (i2 != -1) {
                        viewForPosition.setVisibility(i2);
                    }
                    PendingMoveSmoothScroller pendingMoveSmoothScroller = GridLayoutManager.this.mPendingMoveSmoothScroller;
                    if (pendingMoveSmoothScroller != null) {
                        pendingMoveSmoothScroller.consumePendingMovesBeforeLayout();
                    }
                    int subPositionByView = GridLayoutManager.this.getSubPositionByView(viewForPosition, viewForPosition.findFocus());
                    GridLayoutManager gridLayoutManager2 = GridLayoutManager.this;
                    int i3 = gridLayoutManager2.mFlag;
                    if ((i3 & 3) != 1) {
                        if (i == gridLayoutManager2.mFocusPosition && subPositionByView == gridLayoutManager2.mSubFocusPosition && gridLayoutManager2.mPendingMoveSmoothScroller == null) {
                            gridLayoutManager2.dispatchChildSelected();
                        }
                    } else if ((i3 & 4) == 0) {
                        if ((i3 & 16) == 0 && i == gridLayoutManager2.mFocusPosition && subPositionByView == gridLayoutManager2.mSubFocusPosition) {
                            gridLayoutManager2.dispatchChildSelected();
                        } else if ((i3 & 16) != 0 && i >= gridLayoutManager2.mFocusPosition && viewForPosition.hasFocusable()) {
                            GridLayoutManager gridLayoutManager3 = GridLayoutManager.this;
                            gridLayoutManager3.mFocusPosition = i;
                            gridLayoutManager3.mSubFocusPosition = subPositionByView;
                            gridLayoutManager3.mFlag &= -17;
                            gridLayoutManager3.dispatchChildSelected();
                        }
                    }
                    GridLayoutManager.this.measureChild(viewForPosition);
                }
                objArr[0] = viewForPosition;
                GridLayoutManager gridLayoutManager4 = GridLayoutManager.this;
                return gridLayoutManager4.mOrientation == 0 ? gridLayoutManager4.getDecoratedMeasuredWidthWithMargin(viewForPosition) : gridLayoutManager4.getDecoratedMeasuredHeightWithMargin(viewForPosition);
            }

            @Override // androidx.leanback.widget.Grid.Provider
            public int getCount() {
                return GridLayoutManager.this.mState.getItemCount() + GridLayoutManager.this.mPositionDeltaInPreLayout;
            }

            @Override // androidx.leanback.widget.Grid.Provider
            public int getEdge(int i) {
                GridLayoutManager gridLayoutManager = GridLayoutManager.this;
                View viewFindViewByPosition = gridLayoutManager.findViewByPosition(i - gridLayoutManager.mPositionDeltaInPreLayout);
                GridLayoutManager gridLayoutManager2 = GridLayoutManager.this;
                return (gridLayoutManager2.mFlag & 262144) != 0 ? gridLayoutManager2.getViewMax(viewFindViewByPosition) : gridLayoutManager2.getViewMin(viewFindViewByPosition);
            }

            @Override // androidx.leanback.widget.Grid.Provider
            public int getMinIndex() {
                return GridLayoutManager.this.mPositionDeltaInPreLayout;
            }

            @Override // androidx.leanback.widget.Grid.Provider
            public int getSize(int i) {
                GridLayoutManager gridLayoutManager = GridLayoutManager.this;
                return gridLayoutManager.getViewPrimarySize(gridLayoutManager.findViewByPosition(i - gridLayoutManager.mPositionDeltaInPreLayout));
            }

            @Override // androidx.leanback.widget.Grid.Provider
            public void removeItem(int i) {
                GridLayoutManager gridLayoutManager = GridLayoutManager.this;
                View viewFindViewByPosition = gridLayoutManager.findViewByPosition(i - gridLayoutManager.mPositionDeltaInPreLayout);
                GridLayoutManager gridLayoutManager2 = GridLayoutManager.this;
                if ((gridLayoutManager2.mFlag & 3) == 1) {
                    gridLayoutManager2.detachAndScrapView(viewFindViewByPosition, gridLayoutManager2.mRecycler);
                } else {
                    gridLayoutManager2.removeAndRecycleView(viewFindViewByPosition, gridLayoutManager2.mRecycler);
                }
            }
        };
        this.mBaseGridView = baseGridView;
        this.mChildVisibility = -1;
        setItemPrefetchEnabled(false);
    }

    private void addA11yActionMovingBackward(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, boolean z) {
        if (this.mOrientation == 0) {
            accessibilityNodeInfoCompat.addAction(z ? AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_RIGHT : AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_LEFT);
        } else {
            accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_UP);
        }
        accessibilityNodeInfoCompat.setScrollable(true);
    }

    private void addA11yActionMovingForward(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, boolean z) {
        if (this.mOrientation == 0) {
            accessibilityNodeInfoCompat.addAction(z ? AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_LEFT : AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_RIGHT);
        } else {
            accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN);
        }
        accessibilityNodeInfoCompat.setScrollable(true);
    }

    private boolean appendOneColumnVisibleItems() {
        return this.mGrid.appendOneColumnVisibleItems();
    }

    private void appendVisibleItems() {
        this.mGrid.appendVisibleItems((this.mFlag & 262144) != 0 ? (-this.mExtraLayoutSpace) - this.mExtraLayoutSpaceInPreLayout : this.mSizePrimary + this.mExtraLayoutSpace + this.mExtraLayoutSpaceInPreLayout);
    }

    private void discardLayoutInfo() {
        this.mGrid = null;
        this.mRowSizeSecondary = null;
        this.mFlag &= -1025;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x008a A[LOOP:1: B:21:0x0088->B:22:0x008a, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00bb A[LOOP:3: B:31:0x00bb->B:48:?, LOOP_START] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void fastRelayout() {
        /*
            Method dump skipped, instruction units count: 219
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.GridLayoutManager.fastRelayout():void");
    }

    private int findImmediateChildIndex(View view) {
        BaseGridView baseGridView;
        View viewFindContainingItemView;
        if (view == null || (baseGridView = this.mBaseGridView) == null || view == baseGridView || (viewFindContainingItemView = findContainingItemView(view)) == null) {
            return -1;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (getChildAt(i) == viewFindContainingItemView) {
                return i;
            }
        }
        return -1;
    }

    private void focusToViewInLayout(boolean z, boolean z2, int i, int i2) {
        View viewFindViewByPosition = findViewByPosition(this.mFocusPosition);
        if (viewFindViewByPosition != null && z2) {
            scrollToView(viewFindViewByPosition, false, i, i2);
        }
        if (viewFindViewByPosition != null && z && !viewFindViewByPosition.hasFocus()) {
            viewFindViewByPosition.requestFocus();
            return;
        }
        if (z || this.mBaseGridView.hasFocus()) {
            return;
        }
        if (viewFindViewByPosition == null || !viewFindViewByPosition.hasFocusable()) {
            int childCount = getChildCount();
            int i3 = 0;
            while (true) {
                if (i3 < childCount) {
                    viewFindViewByPosition = getChildAt(i3);
                    if (viewFindViewByPosition != null && viewFindViewByPosition.hasFocusable()) {
                        this.mBaseGridView.focusableViewAvailable(viewFindViewByPosition);
                        break;
                    }
                    i3++;
                } else {
                    break;
                }
            }
        } else {
            this.mBaseGridView.focusableViewAvailable(viewFindViewByPosition);
        }
        if (z2 && viewFindViewByPosition != null && viewFindViewByPosition.hasFocus()) {
            scrollToView(viewFindViewByPosition, false, i, i2);
        }
    }

    private void forceRequestLayout() {
        ViewCompat.postOnAnimation(this.mBaseGridView, this.mRequestLayoutRunnable);
    }

    private int getAdapterPositionByIndex(int i) {
        return getAdapterPositionByView(getChildAt(i));
    }

    private int getAdapterPositionByView(View view) {
        LayoutParams layoutParams;
        if (view == null || (layoutParams = (LayoutParams) view.getLayoutParams()) == null || layoutParams.isItemRemoved()) {
            return -1;
        }
        return layoutParams.getAbsoluteAdapterPosition();
    }

    private int getAdjustedPrimaryAlignedScrollDistance(int i, View view, View view2) {
        int subPositionByView = getSubPositionByView(view, view2);
        if (subPositionByView == 0) {
            return i;
        }
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        return i + (layoutParams.getAlignMultiple()[subPositionByView] - layoutParams.getAlignMultiple()[0]);
    }

    private boolean getAlignedPosition(View view, View view2, int[] iArr) {
        int primaryAlignedScrollDistance = getPrimaryAlignedScrollDistance(view);
        if (view2 != null) {
            primaryAlignedScrollDistance = getAdjustedPrimaryAlignedScrollDistance(primaryAlignedScrollDistance, view, view2);
        }
        int secondaryScrollDistance = getSecondaryScrollDistance(view);
        int i = primaryAlignedScrollDistance + this.mPrimaryScrollExtra;
        if (i == 0 && secondaryScrollDistance == 0) {
            iArr[0] = 0;
            iArr[1] = 0;
            return false;
        }
        iArr[0] = i;
        iArr[1] = secondaryScrollDistance;
        return true;
    }

    private int getMovement(int i) {
        int i2 = this.mOrientation;
        if (i2 == 0) {
            if (i == 17) {
                return (this.mFlag & 262144) == 0 ? 0 : 1;
            }
            if (i == 33) {
                return 2;
            }
            if (i == 66) {
                return (this.mFlag & 262144) == 0 ? 1 : 0;
            }
            if (i == 130) {
                return 3;
            }
        } else if (i2 == 1) {
            if (i == 17) {
                return (this.mFlag & 524288) == 0 ? 2 : 3;
            }
            if (i == 33) {
                return 0;
            }
            if (i == 66) {
                return (this.mFlag & 524288) == 0 ? 3 : 2;
            }
            if (i == 130) {
                return 1;
            }
        }
        return 17;
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00ba  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean getNoneAlignedPosition(android.view.View r13, int[] r14) {
        /*
            Method dump skipped, instruction units count: 204
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.GridLayoutManager.getNoneAlignedPosition(android.view.View, int[]):boolean");
    }

    private int getPrimaryAlignedScrollDistance(View view) {
        return this.mWindowAlignment.mainAxis().getScroll(getViewCenter(view));
    }

    private int getRowSizeSecondary(int i) {
        int i2 = this.mFixedRowSizeSecondary;
        if (i2 != 0) {
            return i2;
        }
        int[] iArr = this.mRowSizeSecondary;
        if (iArr == null) {
            return 0;
        }
        return iArr[i];
    }

    private int getSecondaryScrollDistance(View view) {
        return this.mWindowAlignment.secondAxis().getScroll(getViewCenterSecondary(view));
    }

    private int getSizeSecondary() {
        int i = (this.mFlag & 524288) != 0 ? 0 : this.mNumRows - 1;
        return getRowStartSecondary(i) + getRowSizeSecondary(i);
    }

    private int getViewCenter(View view) {
        return this.mOrientation == 0 ? getViewCenterX(view) : getViewCenterY(view);
    }

    private int getViewCenterSecondary(View view) {
        return this.mOrientation == 0 ? getViewCenterY(view) : getViewCenterX(view);
    }

    private int getViewCenterX(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        return layoutParams.getOpticalLeft(view) + layoutParams.getAlignX();
    }

    private int getViewCenterY(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        return layoutParams.getOpticalTop(view) + layoutParams.getAlignY();
    }

    private boolean gridOnRequestFocusInDescendantsAligned(int i, Rect rect) {
        View viewFindViewByPosition = findViewByPosition(this.mFocusPosition);
        if (viewFindViewByPosition != null) {
            return viewFindViewByPosition.requestFocus(i, rect);
        }
        return false;
    }

    private boolean gridOnRequestFocusInDescendantsUnaligned(int i, Rect rect) {
        int i2;
        int i3;
        int i4;
        int childCount = getChildCount();
        if ((i & 2) != 0) {
            i3 = childCount;
            i2 = 0;
            i4 = 1;
        } else {
            i2 = childCount - 1;
            i3 = -1;
            i4 = -1;
        }
        int paddingMin = this.mWindowAlignment.mainAxis().getPaddingMin();
        int clientSize = this.mWindowAlignment.mainAxis().getClientSize() + paddingMin;
        while (i2 != i3) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 0 && getViewMin(childAt) >= paddingMin && getViewMax(childAt) <= clientSize && childAt.requestFocus(i, rect)) {
                return true;
            }
            i2 += i4;
        }
        return false;
    }

    private void initScrollController() {
        this.mWindowAlignment.reset();
        this.mWindowAlignment.horizontal.setSize(getWidth());
        this.mWindowAlignment.vertical.setSize(getHeight());
        this.mWindowAlignment.horizontal.setPadding(getPaddingLeft(), getPaddingRight());
        this.mWindowAlignment.vertical.setPadding(getPaddingTop(), getPaddingBottom());
        this.mSizePrimary = this.mWindowAlignment.mainAxis().getSize();
        this.mScrollOffsetSecondary = 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0076  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean layoutInit() {
        /*
            r5 = this;
            androidx.recyclerview.widget.RecyclerView$State r0 = r5.mState
            int r0 = r0.getItemCount()
            r1 = -1
            r2 = 1
            r3 = 0
            if (r0 != 0) goto L10
            r5.mFocusPosition = r1
            r5.mSubFocusPosition = r3
            goto L22
        L10:
            int r4 = r5.mFocusPosition
            if (r4 < r0) goto L1a
            int r0 = r0 - r2
            r5.mFocusPosition = r0
            r5.mSubFocusPosition = r3
            goto L22
        L1a:
            if (r4 != r1) goto L22
            if (r0 <= 0) goto L22
            r5.mFocusPosition = r3
            r5.mSubFocusPosition = r3
        L22:
            androidx.recyclerview.widget.RecyclerView$State r0 = r5.mState
            boolean r0 = r0.didStructureChange()
            if (r0 != 0) goto L52
            androidx.leanback.widget.Grid r0 = r5.mGrid
            if (r0 == 0) goto L52
            int r0 = r0.getFirstVisibleIndex()
            if (r0 < 0) goto L52
            int r0 = r5.mFlag
            r0 = r0 & 256(0x100, float:3.59E-43)
            if (r0 != 0) goto L52
            androidx.leanback.widget.Grid r0 = r5.mGrid
            int r0 = r0.getNumRows()
            int r1 = r5.mNumRows
            if (r0 != r1) goto L52
            r5.updateScrollController()
            r5.updateSecondaryScrollLimits()
            androidx.leanback.widget.Grid r0 = r5.mGrid
            int r5 = r5.mSpacingPrimary
            r0.setSpacing(r5)
            return r2
        L52:
            int r0 = r5.mFlag
            r0 = r0 & (-257(0xfffffffffffffeff, float:NaN))
            r5.mFlag = r0
            androidx.leanback.widget.Grid r0 = r5.mGrid
            r1 = 262144(0x40000, float:3.67342E-40)
            if (r0 == 0) goto L76
            int r4 = r5.mNumRows
            int r0 = r0.getNumRows()
            if (r4 != r0) goto L76
            int r0 = r5.mFlag
            r0 = r0 & r1
            if (r0 == 0) goto L6d
            r0 = r2
            goto L6e
        L6d:
            r0 = r3
        L6e:
            androidx.leanback.widget.Grid r4 = r5.mGrid
            boolean r4 = r4.isReversedFlow()
            if (r0 == r4) goto L8f
        L76:
            int r0 = r5.mNumRows
            androidx.leanback.widget.Grid r0 = androidx.leanback.widget.Grid.createGrid(r0)
            r5.mGrid = r0
            androidx.leanback.widget.Grid$Provider r4 = r5.mGridProvider
            r0.setProvider(r4)
            androidx.leanback.widget.Grid r0 = r5.mGrid
            int r4 = r5.mFlag
            r1 = r1 & r4
            if (r1 == 0) goto L8b
            goto L8c
        L8b:
            r2 = r3
        L8c:
            r0.setReversedFlow(r2)
        L8f:
            r5.initScrollController()
            r5.updateSecondaryScrollLimits()
            androidx.leanback.widget.Grid r0 = r5.mGrid
            int r1 = r5.mSpacingPrimary
            r0.setSpacing(r1)
            androidx.recyclerview.widget.RecyclerView$Recycler r0 = r5.mRecycler
            r5.detachAndScrapAttachedViews(r0)
            androidx.leanback.widget.Grid r0 = r5.mGrid
            r0.resetVisibleIndex()
            androidx.leanback.widget.WindowAlignment r0 = r5.mWindowAlignment
            androidx.leanback.widget.WindowAlignment$Axis r0 = r0.mainAxis()
            r0.invalidateScrollMin()
            androidx.leanback.widget.WindowAlignment r5 = r5.mWindowAlignment
            androidx.leanback.widget.WindowAlignment$Axis r5 = r5.mainAxis()
            r5.invalidateScrollMax()
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.GridLayoutManager.layoutInit():boolean");
    }

    private void leaveContext() {
        int i = this.mSaveContextLevel - 1;
        this.mSaveContextLevel = i;
        if (i == 0) {
            this.mRecycler = null;
            this.mState = null;
            this.mPositionDeltaInPreLayout = 0;
            this.mExtraLayoutSpaceInPreLayout = 0;
        }
    }

    private void measureScrapChild(int i, int i2, int i3, int[] iArr) {
        View viewForPosition = this.mRecycler.getViewForPosition(i);
        if (viewForPosition != null) {
            LayoutParams layoutParams = (LayoutParams) viewForPosition.getLayoutParams();
            Rect rect = sTempRect;
            calculateItemDecorationsForChild(viewForPosition, rect);
            viewForPosition.measure(ViewGroup.getChildMeasureSpec(i2, getPaddingLeft() + getPaddingRight() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin + rect.left + rect.right, ((ViewGroup.MarginLayoutParams) layoutParams).width), ViewGroup.getChildMeasureSpec(i3, getPaddingTop() + getPaddingBottom() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + rect.top + rect.bottom, ((ViewGroup.MarginLayoutParams) layoutParams).height));
            iArr[0] = getDecoratedMeasuredWidthWithMargin(viewForPosition);
            iArr[1] = getDecoratedMeasuredHeightWithMargin(viewForPosition);
            this.mRecycler.recycleView(viewForPosition);
        }
    }

    private void offsetChildrenPrimary(int i) {
        int childCount = getChildCount();
        int i2 = 0;
        if (this.mOrientation == 1) {
            while (i2 < childCount) {
                getChildAt(i2).offsetTopAndBottom(i);
                i2++;
            }
        } else {
            while (i2 < childCount) {
                getChildAt(i2).offsetLeftAndRight(i);
                i2++;
            }
        }
    }

    private void offsetChildrenSecondary(int i) {
        int childCount = getChildCount();
        int i2 = 0;
        if (this.mOrientation == 0) {
            while (i2 < childCount) {
                getChildAt(i2).offsetTopAndBottom(i);
                i2++;
            }
        } else {
            while (i2 < childCount) {
                getChildAt(i2).offsetLeftAndRight(i);
                i2++;
            }
        }
    }

    private boolean prependOneColumnVisibleItems() {
        return this.mGrid.prependOneColumnVisibleItems();
    }

    private void prependVisibleItems() {
        this.mGrid.prependVisibleItems((this.mFlag & 262144) != 0 ? this.mSizePrimary + this.mExtraLayoutSpace + this.mExtraLayoutSpaceInPreLayout : (-this.mExtraLayoutSpace) - this.mExtraLayoutSpaceInPreLayout);
    }

    private boolean processRowSizeSecondary(boolean z) {
        if (this.mFixedRowSizeSecondary != 0 || this.mRowSizeSecondary == null) {
            return false;
        }
        Grid grid = this.mGrid;
        CircularIntArray[] itemPositionsInRows = grid == null ? null : grid.getItemPositionsInRows();
        boolean z2 = false;
        int i = -1;
        for (int i2 = 0; i2 < this.mNumRows; i2++) {
            CircularIntArray circularIntArray = itemPositionsInRows == null ? null : itemPositionsInRows[i2];
            int size = circularIntArray == null ? 0 : circularIntArray.size();
            int i3 = -1;
            for (int i4 = 0; i4 < size; i4 += 2) {
                int i5 = circularIntArray.get(i4 + 1);
                for (int i6 = circularIntArray.get(i4); i6 <= i5; i6++) {
                    View viewFindViewByPosition = findViewByPosition(i6 - this.mPositionDeltaInPreLayout);
                    if (viewFindViewByPosition != null) {
                        if (z) {
                            measureChild(viewFindViewByPosition);
                        }
                        int decoratedMeasuredHeightWithMargin = this.mOrientation == 0 ? getDecoratedMeasuredHeightWithMargin(viewFindViewByPosition) : getDecoratedMeasuredWidthWithMargin(viewFindViewByPosition);
                        if (decoratedMeasuredHeightWithMargin > i3) {
                            i3 = decoratedMeasuredHeightWithMargin;
                        }
                    }
                }
            }
            int itemCount = this.mState.getItemCount();
            if (!this.mBaseGridView.hasFixedSize() && z && i3 < 0 && itemCount > 0) {
                if (i < 0) {
                    int i7 = this.mFocusPosition;
                    if (i7 < 0) {
                        i7 = 0;
                    } else if (i7 >= itemCount) {
                        i7 = itemCount - 1;
                    }
                    if (getChildCount() > 0) {
                        int layoutPosition = this.mBaseGridView.getChildViewHolder(getChildAt(0)).getLayoutPosition();
                        int layoutPosition2 = this.mBaseGridView.getChildViewHolder(getChildAt(getChildCount() - 1)).getLayoutPosition();
                        if (i7 >= layoutPosition && i7 <= layoutPosition2) {
                            i7 = i7 - layoutPosition <= layoutPosition2 - i7 ? layoutPosition - 1 : layoutPosition2 + 1;
                            if (i7 < 0 && layoutPosition2 < itemCount - 1) {
                                i7 = layoutPosition2 + 1;
                            } else if (i7 >= itemCount && layoutPosition > 0) {
                                i7 = layoutPosition - 1;
                            }
                        }
                    }
                    if (i7 >= 0 && i7 < itemCount) {
                        measureScrapChild(i7, View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0), this.mMeasuredDimension);
                        i = this.mOrientation == 0 ? this.mMeasuredDimension[1] : this.mMeasuredDimension[0];
                    }
                }
                if (i >= 0) {
                    i3 = i;
                }
            }
            if (i3 < 0) {
                i3 = 0;
            }
            int[] iArr = this.mRowSizeSecondary;
            if (iArr[i2] != i3) {
                iArr[i2] = i3;
                z2 = true;
            }
        }
        return z2;
    }

    private void removeInvisibleViewsAtEnd() {
        int i;
        int i2 = this.mFlag;
        if ((65600 & i2) == 65536) {
            Grid grid = this.mGrid;
            int i3 = this.mFocusPosition;
            if ((i2 & 262144) != 0) {
                i = -this.mExtraLayoutSpace;
            } else {
                i = this.mExtraLayoutSpace + this.mSizePrimary;
            }
            grid.removeInvisibleItemsAtEnd(i3, i);
        }
    }

    private void removeInvisibleViewsAtFront() {
        int i = this.mFlag;
        if ((65600 & i) == 65536) {
            this.mGrid.removeInvisibleItemsAtFront(this.mFocusPosition, (i & 262144) != 0 ? this.mSizePrimary + this.mExtraLayoutSpace : -this.mExtraLayoutSpace);
        }
    }

    private void saveContext(RecyclerView.Recycler recycler, RecyclerView.State state) {
        int i = this.mSaveContextLevel;
        if (i == 0) {
            this.mRecycler = recycler;
            this.mState = state;
            this.mPositionDeltaInPreLayout = 0;
            this.mExtraLayoutSpaceInPreLayout = 0;
        }
        this.mSaveContextLevel = i + 1;
    }

    private int scrollDirectionPrimary(int i) {
        int minScroll;
        int i2 = this.mFlag;
        if ((i2 & 64) == 0 && (i2 & 3) != 1 && (i <= 0 ? !(i >= 0 || this.mWindowAlignment.mainAxis().isMinUnknown() || i >= (minScroll = this.mWindowAlignment.mainAxis().getMinScroll())) : !(this.mWindowAlignment.mainAxis().isMaxUnknown() || i <= (minScroll = this.mWindowAlignment.mainAxis().getMaxScroll())))) {
            i = minScroll;
        }
        if (i == 0) {
            return 0;
        }
        offsetChildrenPrimary(-i);
        if ((this.mFlag & 3) == 1) {
            updateScrollLimits();
            return i;
        }
        int childCount = getChildCount();
        if ((this.mFlag & 262144) == 0 ? i >= 0 : i <= 0) {
            appendVisibleItems();
        } else {
            prependVisibleItems();
        }
        boolean z = getChildCount() > childCount;
        int childCount2 = getChildCount();
        if ((262144 & this.mFlag) == 0 ? i >= 0 : i <= 0) {
            removeInvisibleViewsAtFront();
        } else {
            removeInvisibleViewsAtEnd();
        }
        if (z | (getChildCount() < childCount2)) {
            updateRowSecondarySizeRefresh();
        }
        this.mBaseGridView.invalidate();
        updateScrollLimits();
        return i;
    }

    private int scrollDirectionSecondary(int i) {
        if (i == 0) {
            return 0;
        }
        offsetChildrenSecondary(-i);
        this.mScrollOffsetSecondary += i;
        updateSecondaryScrollLimits();
        this.mBaseGridView.invalidate();
        return i;
    }

    private void scrollGrid(int i, int i2, boolean z) {
        if ((this.mFlag & 3) == 1) {
            scrollDirectionPrimary(i);
            scrollDirectionSecondary(i2);
            return;
        }
        if (this.mOrientation != 0) {
            i2 = i;
            i = i2;
        }
        if (z) {
            this.mBaseGridView.smoothScrollBy(i, i2);
        } else {
            this.mBaseGridView.scrollBy(i, i2);
            dispatchChildSelectedAndPositioned();
        }
    }

    private void scrollToView(View view, View view2, boolean z) {
        scrollToView(view, view2, z, 0, 0);
    }

    private void scrollToView(View view, View view2, boolean z, int i, int i2) {
        if ((this.mFlag & 64) != 0) {
            return;
        }
        int adapterPositionByView = getAdapterPositionByView(view);
        int subPositionByView = getSubPositionByView(view, view2);
        if (adapterPositionByView != this.mFocusPosition || subPositionByView != this.mSubFocusPosition) {
            this.mFocusPosition = adapterPositionByView;
            this.mSubFocusPosition = subPositionByView;
            this.mFocusPositionOffset = 0;
            if ((this.mFlag & 3) != 1) {
                dispatchChildSelected();
            }
            if (this.mBaseGridView.isChildrenDrawingOrderEnabledInternal()) {
                this.mBaseGridView.invalidate();
            }
        }
        if (view == null) {
            return;
        }
        if (!view.hasFocus() && this.mBaseGridView.hasFocus()) {
            view.requestFocus();
        }
        if ((this.mFlag & 131072) == 0 && z) {
            return;
        }
        if (!getScrollPosition(view, view2, sTwoInts) && i == 0 && i2 == 0) {
            return;
        }
        int[] iArr = sTwoInts;
        scrollGrid(iArr[0] + i, iArr[1] + i2, z);
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private void sendTypeViewScrolledAccessibilityEvent() {
        AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain(4096);
        this.mBaseGridView.onInitializeAccessibilityEvent(accessibilityEventObtain);
        BaseGridView baseGridView = this.mBaseGridView;
        baseGridView.requestSendAccessibilityEvent(baseGridView, accessibilityEventObtain);
    }

    private void updateChildAlignments(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        layoutParams.getItemAlignmentFacet();
        layoutParams.setAlignX(this.mItemAlignment.horizontal.getAlignmentPosition(view));
        layoutParams.setAlignY(this.mItemAlignment.vertical.getAlignmentPosition(view));
    }

    private void updateRowSecondarySizeRefresh() {
        int i = (this.mFlag & (-1025)) | (processRowSizeSecondary(false) ? 1024 : 0);
        this.mFlag = i;
        if ((i & 1024) != 0) {
            forceRequestLayout();
        }
    }

    private void updateScrollController() {
        this.mWindowAlignment.horizontal.setSize(getWidth());
        this.mWindowAlignment.vertical.setSize(getHeight());
        this.mWindowAlignment.horizontal.setPadding(getPaddingLeft(), getPaddingRight());
        this.mWindowAlignment.vertical.setPadding(getPaddingTop(), getPaddingBottom());
        this.mSizePrimary = this.mWindowAlignment.mainAxis().getSize();
    }

    private void updateSecondaryScrollLimits() {
        WindowAlignment.Axis axisSecondAxis = this.mWindowAlignment.secondAxis();
        int paddingMin = axisSecondAxis.getPaddingMin() - this.mScrollOffsetSecondary;
        int sizeSecondary = getSizeSecondary() + paddingMin;
        axisSecondAxis.updateMinMax(paddingMin, sizeSecondary, paddingMin, sizeSecondary);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollHorizontally() {
        return this.mOrientation == 0 || this.mNumRows > 1;
    }

    boolean canScrollTo(View view) {
        if (view.getVisibility() == 0) {
            return !hasFocus() || view.hasFocusable();
        }
        return false;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollVertically() {
        return this.mOrientation == 1 || this.mNumRows > 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean checkLayoutParams(RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void collectAdjacentPrefetchPositions(int i, int i2, RecyclerView.State state, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        try {
            saveContext(null, state);
            if (this.mOrientation != 0) {
                i = i2;
            }
            if (getChildCount() != 0 && i != 0) {
                this.mGrid.collectAdjacentPrefetchPositions(i < 0 ? -this.mExtraLayoutSpace : this.mSizePrimary + this.mExtraLayoutSpace, i, layoutPrefetchRegistry);
                leaveContext();
            }
        } finally {
            leaveContext();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void collectInitialPrefetchPositions(int i, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int i2 = this.mBaseGridView.mInitialPrefetchItemCount;
        if (i == 0 || i2 == 0) {
            return;
        }
        int iMax = Math.max(0, Math.min(this.mFocusPosition - ((i2 - 1) / 2), i - i2));
        for (int i3 = iMax; i3 < i && i3 < iMax + i2; i3++) {
            layoutPrefetchRegistry.addPosition(i3, 0);
        }
    }

    void dispatchChildSelected() {
        if (hasOnChildViewHolderSelectedListener()) {
            int i = this.mFocusPosition;
            View viewFindViewByPosition = i == -1 ? null : findViewByPosition(i);
            if (viewFindViewByPosition != null) {
                fireOnChildViewHolderSelected(this.mBaseGridView, this.mBaseGridView.getChildViewHolder(viewFindViewByPosition), this.mFocusPosition, this.mSubFocusPosition);
            } else {
                fireOnChildViewHolderSelected(this.mBaseGridView, null, -1, 0);
            }
            if ((this.mFlag & 3) == 1 || this.mBaseGridView.isLayoutRequested()) {
                return;
            }
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                if (getChildAt(i2).isLayoutRequested()) {
                    forceRequestLayout();
                    return;
                }
            }
        }
    }

    void dispatchChildSelectedAndPositioned() {
        if (hasOnChildViewHolderSelectedListener()) {
            int i = this.mFocusPosition;
            View viewFindViewByPosition = i == -1 ? null : findViewByPosition(i);
            if (viewFindViewByPosition != null) {
                fireOnChildViewHolderSelectedAndPositioned(this.mBaseGridView, this.mBaseGridView.getChildViewHolder(viewFindViewByPosition), this.mFocusPosition, this.mSubFocusPosition);
            } else {
                fireOnChildViewHolderSelectedAndPositioned(this.mBaseGridView, null, -1, 0);
            }
        }
    }

    void fillScrapViewsInPostLayout() {
        List scrapList = this.mRecycler.getScrapList();
        int size = scrapList.size();
        if (size == 0) {
            return;
        }
        int[] iArr = this.mDisappearingPositions;
        if (iArr == null || size > iArr.length) {
            int length = iArr == null ? 16 : iArr.length;
            while (length < size) {
                length <<= 1;
            }
            this.mDisappearingPositions = new int[length];
        }
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            int absoluteAdapterPosition = ((RecyclerView.ViewHolder) scrapList.get(i2)).getAbsoluteAdapterPosition();
            if (absoluteAdapterPosition >= 0) {
                this.mDisappearingPositions[i] = absoluteAdapterPosition;
                i++;
            }
        }
        if (i > 0) {
            Arrays.sort(this.mDisappearingPositions, 0, i);
            this.mGrid.fillDisappearingItems(this.mDisappearingPositions, i, this.mPositionToRowInPostLayout);
        }
        this.mPositionToRowInPostLayout.clear();
    }

    void fireOnChildViewHolderSelected(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i, int i2) {
        ArrayList arrayList = this.mChildViewHolderSelectedListeners;
        if (arrayList == null) {
            return;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ((OnChildViewHolderSelectedListener) this.mChildViewHolderSelectedListeners.get(size)).onChildViewHolderSelected(recyclerView, viewHolder, i, i2);
        }
    }

    void fireOnChildViewHolderSelectedAndPositioned(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i, int i2) {
        ArrayList arrayList = this.mChildViewHolderSelectedListeners;
        if (arrayList == null) {
            return;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ((OnChildViewHolderSelectedListener) this.mChildViewHolderSelectedListeners.get(size)).onChildViewHolderSelectedAndPositioned(recyclerView, viewHolder, i, i2);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams ? new LayoutParams((LayoutParams) layoutParams) : layoutParams instanceof RecyclerView.LayoutParams ? new LayoutParams((RecyclerView.LayoutParams) layoutParams) : layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    AudioManager getAudioManager() {
        if (this.mAudioManager == null) {
            this.mAudioManager = (AudioManager) this.mBaseGridView.getContext().getSystemService("audio");
        }
        return this.mAudioManager;
    }

    int getChildDrawingOrder(RecyclerView recyclerView, int i, int i2) {
        int iIndexOfChild;
        View viewFindViewByPosition = findViewByPosition(this.mFocusPosition);
        return (viewFindViewByPosition != null && i2 >= (iIndexOfChild = recyclerView.indexOfChild(viewFindViewByPosition))) ? i2 < i + (-1) ? ((iIndexOfChild + i) - 1) - i2 : iIndexOfChild : i2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int getColumnCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Grid grid;
        return (this.mOrientation != 1 || (grid = this.mGrid) == null) ? super.getColumnCountForAccessibility(recycler, state) : grid.getNumRows();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int getDecoratedBottom(View view) {
        return super.getDecoratedBottom(view) - ((LayoutParams) view.getLayoutParams()).mBottomInset;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void getDecoratedBoundsWithMargins(View view, Rect rect) {
        super.getDecoratedBoundsWithMargins(view, rect);
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        rect.left += layoutParams.mLeftInset;
        rect.top += layoutParams.mTopInset;
        rect.right -= layoutParams.mRightInset;
        rect.bottom -= layoutParams.mBottomInset;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int getDecoratedLeft(View view) {
        return super.getDecoratedLeft(view) + ((LayoutParams) view.getLayoutParams()).mLeftInset;
    }

    int getDecoratedMeasuredHeightWithMargin(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        return getDecoratedMeasuredHeight(view) + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
    }

    int getDecoratedMeasuredWidthWithMargin(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        return getDecoratedMeasuredWidth(view) + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int getDecoratedRight(View view) {
        return super.getDecoratedRight(view) - ((LayoutParams) view.getLayoutParams()).mRightInset;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int getDecoratedTop(View view) {
        return super.getDecoratedTop(view) + ((LayoutParams) view.getLayoutParams()).mTopInset;
    }

    Object getFacet(RecyclerView.ViewHolder viewHolder, Class cls) {
        return null;
    }

    int getOpticalLeft(View view) {
        return ((LayoutParams) view.getLayoutParams()).getOpticalLeft(view);
    }

    int getOpticalRight(View view) {
        return ((LayoutParams) view.getLayoutParams()).getOpticalRight(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int getRowCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Grid grid;
        return (this.mOrientation != 0 || (grid = this.mGrid) == null) ? super.getRowCountForAccessibility(recycler, state) : grid.getNumRows();
    }

    int getRowStartSecondary(int i) {
        int rowSizeSecondary = 0;
        if ((this.mFlag & 524288) != 0) {
            for (int i2 = this.mNumRows - 1; i2 > i; i2--) {
                rowSizeSecondary += getRowSizeSecondary(i2) + this.mSpacingSecondary;
            }
            return rowSizeSecondary;
        }
        int rowSizeSecondary2 = 0;
        while (rowSizeSecondary < i) {
            rowSizeSecondary2 += getRowSizeSecondary(rowSizeSecondary) + this.mSpacingSecondary;
            rowSizeSecondary++;
        }
        return rowSizeSecondary2;
    }

    boolean getScrollPosition(View view, View view2, int[] iArr) {
        int i = this.mFocusScrollStrategy;
        return (i == 1 || i == 2) ? getNoneAlignedPosition(view, iArr) : getAlignedPosition(view, view2, iArr);
    }

    int getSelection() {
        return this.mFocusPosition;
    }

    int getSlideOutDistance() {
        int left;
        int right;
        int top;
        if (this.mOrientation == 1) {
            int i = -getHeight();
            return (getChildCount() <= 0 || (top = getChildAt(0).getTop()) >= 0) ? i : i + top;
        }
        if ((this.mFlag & 262144) != 0) {
            int width = getWidth();
            return (getChildCount() <= 0 || (right = getChildAt(0).getRight()) <= width) ? width : right;
        }
        int i2 = -getWidth();
        return (getChildCount() <= 0 || (left = getChildAt(0).getLeft()) >= 0) ? i2 : i2 + left;
    }

    int getSubPositionByView(View view, View view2) {
        if (view != null && view2 != null) {
            ((LayoutParams) view.getLayoutParams()).getItemAlignmentFacet();
        }
        return 0;
    }

    String getTag() {
        return "GridLayoutManager:" + this.mBaseGridView.getId();
    }

    int getVerticalSpacing() {
        return this.mVerticalSpacing;
    }

    View getViewForPosition(int i) {
        View viewForPosition = this.mRecycler.getViewForPosition(i);
        LayoutParams layoutParams = (LayoutParams) viewForPosition.getLayoutParams();
        ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(getFacet(this.mBaseGridView.getChildViewHolder(viewForPosition), ItemAlignmentFacet.class));
        layoutParams.setItemAlignmentFacet(null);
        return viewForPosition;
    }

    int getViewMax(View view) {
        return this.mOrientationHelper.getDecoratedEnd(view);
    }

    int getViewMin(View view) {
        return this.mOrientationHelper.getDecoratedStart(view);
    }

    int getViewPrimarySize(View view) {
        Rect rect = sTempRect;
        getDecoratedBoundsWithMargins(view, rect);
        return this.mOrientation == 0 ? rect.width() : rect.height();
    }

    boolean gridOnRequestFocusInDescendants(RecyclerView recyclerView, int i, Rect rect) {
        int i2 = this.mFocusScrollStrategy;
        return (i2 == 1 || i2 == 2) ? gridOnRequestFocusInDescendantsUnaligned(i, rect) : gridOnRequestFocusInDescendantsAligned(i, rect);
    }

    boolean hasCreatedFirstItem() {
        return getItemCount() == 0 || this.mBaseGridView.findViewHolderForAdapterPosition(0) != null;
    }

    boolean hasCreatedLastItem() {
        int itemCount = getItemCount();
        return itemCount == 0 || this.mBaseGridView.findViewHolderForAdapterPosition(itemCount - 1) != null;
    }

    boolean hasDoneFirstLayout() {
        return this.mGrid != null;
    }

    boolean hasOnChildViewHolderSelectedListener() {
        ArrayList arrayList = this.mChildViewHolderSelectedListeners;
        return arrayList != null && arrayList.size() > 0;
    }

    boolean isItemFullyVisible(int i) {
        RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = this.mBaseGridView.findViewHolderForAdapterPosition(i);
        return viewHolderFindViewHolderForAdapterPosition != null && viewHolderFindViewHolderForAdapterPosition.itemView.getLeft() >= 0 && viewHolderFindViewHolderForAdapterPosition.itemView.getRight() <= this.mBaseGridView.getWidth() && viewHolderFindViewHolderForAdapterPosition.itemView.getTop() >= 0 && viewHolderFindViewHolderForAdapterPosition.itemView.getBottom() <= this.mBaseGridView.getHeight();
    }

    boolean isScrollEnabled() {
        return (this.mFlag & 131072) != 0;
    }

    boolean isSlidingChildViews() {
        return (this.mFlag & 64) != 0;
    }

    void layoutChild(int i, View view, int i2, int i3, int i4) {
        int rowSizeSecondary;
        int i5;
        int i6;
        int i7;
        int i8;
        int decoratedMeasuredHeightWithMargin = this.mOrientation == 0 ? getDecoratedMeasuredHeightWithMargin(view) : getDecoratedMeasuredWidthWithMargin(view);
        int i9 = this.mFixedRowSizeSecondary;
        if (i9 > 0) {
            decoratedMeasuredHeightWithMargin = Math.min(decoratedMeasuredHeightWithMargin, i9);
        }
        int i10 = this.mGravity;
        int i11 = i10 & 112;
        int absoluteGravity = (this.mFlag & 786432) != 0 ? Gravity.getAbsoluteGravity(i10 & 8388615, 1) : i10 & 7;
        int i12 = this.mOrientation;
        if ((i12 != 0 || i11 != 48) && (i12 != 1 || absoluteGravity != 3)) {
            if ((i12 == 0 && i11 == 80) || (i12 == 1 && absoluteGravity == 5)) {
                rowSizeSecondary = getRowSizeSecondary(i) - decoratedMeasuredHeightWithMargin;
            } else if ((i12 == 0 && i11 == 16) || (i12 == 1 && absoluteGravity == 1)) {
                rowSizeSecondary = (getRowSizeSecondary(i) - decoratedMeasuredHeightWithMargin) / 2;
            }
            i4 += rowSizeSecondary;
        }
        if (this.mOrientation == 0) {
            i7 = i2;
            i8 = i3;
            i5 = i4;
            i6 = decoratedMeasuredHeightWithMargin + i4;
        } else {
            i5 = i2;
            i6 = i3;
            i7 = i4;
            i8 = decoratedMeasuredHeightWithMargin + i4;
        }
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        layoutDecoratedWithMargins(view, i7, i5, i8, i6);
        Rect rect = sTempRect;
        super.getDecoratedBoundsWithMargins(view, rect);
        layoutParams.setOpticalInsets(i7 - rect.left, i5 - rect.top, rect.right - i8, rect.bottom - i6);
        updateChildAlignments(view);
    }

    void measureChild(View view) {
        int childMeasureSpec;
        int childMeasureSpec2;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Rect rect = sTempRect;
        calculateItemDecorationsForChild(view, rect);
        int i = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin + rect.left + rect.right;
        int i2 = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + rect.top + rect.bottom;
        int iMakeMeasureSpec = this.mRowSizeSecondaryRequested == -2 ? View.MeasureSpec.makeMeasureSpec(0, 0) : View.MeasureSpec.makeMeasureSpec(this.mFixedRowSizeSecondary, 1073741824);
        if (this.mOrientation == 0) {
            childMeasureSpec2 = ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(0, 0), i, ((ViewGroup.MarginLayoutParams) layoutParams).width);
            childMeasureSpec = ViewGroup.getChildMeasureSpec(iMakeMeasureSpec, i2, ((ViewGroup.MarginLayoutParams) layoutParams).height);
        } else {
            int childMeasureSpec3 = ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(0, 0), i2, ((ViewGroup.MarginLayoutParams) layoutParams).height);
            int childMeasureSpec4 = ViewGroup.getChildMeasureSpec(iMakeMeasureSpec, i, ((ViewGroup.MarginLayoutParams) layoutParams).width);
            childMeasureSpec = childMeasureSpec3;
            childMeasureSpec2 = childMeasureSpec4;
        }
        view.measure(childMeasureSpec2, childMeasureSpec);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onAdapterChanged(RecyclerView.Adapter adapter, RecyclerView.Adapter adapter2) {
        if (adapter != null) {
            discardLayoutInfo();
            this.mFocusPosition = -1;
            this.mFocusPositionOffset = 0;
            this.mChildrenStates.clear();
        }
        super.onAdapterChanged(adapter, adapter2);
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x009c  */
    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onAddFocusables(androidx.recyclerview.widget.RecyclerView r19, java.util.ArrayList r20, int r21, int r22) {
        /*
            Method dump skipped, instruction units count: 411
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.GridLayoutManager.onAddFocusables(androidx.recyclerview.widget.RecyclerView, java.util.ArrayList, int, int):boolean");
    }

    void onChildRecycled(RecyclerView.ViewHolder viewHolder) {
        int absoluteAdapterPosition = viewHolder.getAbsoluteAdapterPosition();
        if (absoluteAdapterPosition != -1) {
            this.mChildrenStates.saveOffscreenView(viewHolder.itemView, absoluteAdapterPosition);
        }
    }

    void onFocusChanged(boolean z, int i, Rect rect) {
        if (!z) {
            return;
        }
        int i2 = this.mFocusPosition;
        while (true) {
            View viewFindViewByPosition = findViewByPosition(i2);
            if (viewFindViewByPosition == null) {
                return;
            }
            if (viewFindViewByPosition.getVisibility() == 0 && viewFindViewByPosition.hasFocusable()) {
                viewFindViewByPosition.requestFocus();
                return;
            }
            i2++;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onInitializeAccessibilityNodeInfo(RecyclerView.Recycler recycler, RecyclerView.State state, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        saveContext(recycler, state);
        int itemCount = state.getItemCount();
        int i = this.mFlag;
        boolean z = (262144 & i) != 0;
        if ((i & 2048) == 0 || (itemCount > 1 && !isItemFullyVisible(0))) {
            addA11yActionMovingBackward(accessibilityNodeInfoCompat, z);
        }
        if ((this.mFlag & 4096) == 0 || (itemCount > 1 && !isItemFullyVisible(itemCount - 1))) {
            addA11yActionMovingForward(accessibilityNodeInfoCompat, z);
        }
        accessibilityNodeInfoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(getRowCountForAccessibility(recycler, state), getColumnCountForAccessibility(recycler, state), isLayoutHierarchical(recycler, state), getSelectionModeForAccessibility(recycler, state)));
        accessibilityNodeInfoCompat.setClassName(GridView.class.getName());
        leaveContext();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (this.mGrid == null || !(layoutParams instanceof LayoutParams)) {
            return;
        }
        int absoluteAdapterPosition = ((LayoutParams) layoutParams).getAbsoluteAdapterPosition();
        int rowIndex = absoluteAdapterPosition >= 0 ? this.mGrid.getRowIndex(absoluteAdapterPosition) : -1;
        if (rowIndex < 0) {
            return;
        }
        int numRows = absoluteAdapterPosition / this.mGrid.getNumRows();
        if (this.mOrientation == 0) {
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(rowIndex, 1, numRows, 1, false, false));
        } else {
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(numRows, 1, rowIndex, 1, false, false));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public View onInterceptFocusSearch(View view, int i) {
        View viewFindNextFocus;
        View viewFindNextFocus2;
        if ((this.mFlag & 32768) != 0) {
            return view;
        }
        FocusFinder focusFinder = FocusFinder.getInstance();
        if (i == 2 || i == 1) {
            if (canScrollVertically()) {
                viewFindNextFocus = focusFinder.findNextFocus(this.mBaseGridView, view, i == 2 ? 130 : 33);
            } else {
                viewFindNextFocus = null;
            }
            if (canScrollHorizontally()) {
                viewFindNextFocus2 = focusFinder.findNextFocus(this.mBaseGridView, view, (getLayoutDirection() == 1) ^ (i == 2) ? 66 : 17);
            } else {
                viewFindNextFocus2 = viewFindNextFocus;
            }
        } else {
            viewFindNextFocus2 = focusFinder.findNextFocus(this.mBaseGridView, view, i);
        }
        if (viewFindNextFocus2 != null) {
            return viewFindNextFocus2;
        }
        if (this.mBaseGridView.getDescendantFocusability() == 393216) {
            return this.mBaseGridView.getParent().focusSearch(view, i);
        }
        int movement = getMovement(i);
        boolean z = this.mBaseGridView.getScrollState() != 0;
        if (movement == 1) {
            if (z || (this.mFlag & 4096) == 0) {
                viewFindNextFocus2 = view;
            }
            if ((this.mFlag & 131072) != 0 && !hasCreatedLastItem()) {
                processPendingMovement(true);
                viewFindNextFocus2 = view;
            }
        } else if (movement == 0) {
            if (z || (this.mFlag & 2048) == 0) {
                viewFindNextFocus2 = view;
            }
            if ((this.mFlag & 131072) != 0 && !hasCreatedFirstItem()) {
                processPendingMovement(false);
                viewFindNextFocus2 = view;
            }
        } else if (movement == 3) {
        }
        if (viewFindNextFocus2 != null) {
            return viewFindNextFocus2;
        }
        View viewFocusSearch = this.mBaseGridView.getParent().focusSearch(view, i);
        return viewFocusSearch != null ? viewFocusSearch : view != null ? view : this.mBaseGridView;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsAdded(RecyclerView recyclerView, int i, int i2) {
        Grid grid;
        int i3;
        if (this.mFocusPosition != -1 && (grid = this.mGrid) != null && grid.getFirstVisibleIndex() >= 0 && (i3 = this.mFocusPositionOffset) != Integer.MIN_VALUE && i <= this.mFocusPosition + i3) {
            this.mFocusPositionOffset = i3 + i2;
        }
        this.mChildrenStates.clear();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsChanged(RecyclerView recyclerView) {
        this.mFocusPositionOffset = 0;
        this.mChildrenStates.clear();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsMoved(RecyclerView recyclerView, int i, int i2, int i3) {
        int i4;
        int i5 = this.mFocusPosition;
        if (i5 != -1 && (i4 = this.mFocusPositionOffset) != Integer.MIN_VALUE) {
            int i6 = i5 + i4;
            if (i <= i6 && i6 < i + i3) {
                this.mFocusPositionOffset = i4 + (i2 - i);
            } else if (i < i6 && i2 > i6 - i3) {
                this.mFocusPositionOffset = i4 - i3;
            } else if (i > i6 && i2 < i6) {
                this.mFocusPositionOffset = i4 + i3;
            }
        }
        this.mChildrenStates.clear();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsRemoved(RecyclerView recyclerView, int i, int i2) {
        Grid grid;
        int i3;
        int i4;
        int i5;
        if (this.mFocusPosition != -1 && (grid = this.mGrid) != null && grid.getFirstVisibleIndex() >= 0 && (i3 = this.mFocusPositionOffset) != Integer.MIN_VALUE && i <= (i5 = (i4 = this.mFocusPosition) + i3)) {
            if (i + i2 > i5) {
                this.mFocusPosition = i4 + i3 + (i - i5);
                this.mFocusPositionOffset = Integer.MIN_VALUE;
            } else {
                this.mFocusPositionOffset = i3 - i2;
            }
        }
        this.mChildrenStates.clear();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsUpdated(RecyclerView recyclerView, int i, int i2) {
        int i3 = i2 + i;
        while (i < i3) {
            this.mChildrenStates.remove(i);
            i++;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        int remainingScrollHorizontal;
        int remainingScrollVertical;
        int i;
        int i2;
        int i3;
        if (this.mNumRows != 0 && state.getItemCount() >= 0) {
            if ((this.mFlag & 64) != 0 && getChildCount() > 0) {
                this.mFlag |= 128;
                return;
            }
            int i4 = this.mFlag;
            if ((i4 & 512) == 0) {
                discardLayoutInfo();
                removeAndRecycleAllViews(recycler);
                return;
            }
            this.mFlag = (i4 & (-4)) | 1;
            saveContext(recycler, state);
            int iMax = Integer.MIN_VALUE;
            if (state.isPreLayout()) {
                updatePositionDeltaInPreLayout();
                int childCount = getChildCount();
                if (this.mGrid != null && childCount > 0) {
                    int oldPosition = this.mBaseGridView.getChildViewHolder(getChildAt(0)).getOldPosition();
                    int oldPosition2 = this.mBaseGridView.getChildViewHolder(getChildAt(childCount - 1)).getOldPosition();
                    int iMin = Integer.MAX_VALUE;
                    while (i < childCount) {
                        View childAt = getChildAt(i);
                        LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                        int childAdapterPosition = this.mBaseGridView.getChildAdapterPosition(childAt);
                        if (layoutParams.isItemChanged() || layoutParams.isItemRemoved() || childAt.isLayoutRequested() || ((!childAt.hasFocus() && this.mFocusPosition == layoutParams.getAbsoluteAdapterPosition()) || ((childAt.hasFocus() && this.mFocusPosition != layoutParams.getAbsoluteAdapterPosition()) || childAdapterPosition < oldPosition || childAdapterPosition > oldPosition2))) {
                            iMin = Math.min(iMin, getViewMin(childAt));
                            iMax = Math.max(iMax, getViewMax(childAt));
                        }
                        i++;
                    }
                    if (iMax > iMin) {
                        this.mExtraLayoutSpaceInPreLayout = iMax - iMin;
                    }
                    appendVisibleItems();
                    prependVisibleItems();
                }
                this.mFlag &= -4;
                leaveContext();
                return;
            }
            if (state.willRunPredictiveAnimations()) {
                updatePositionToRowMapInPostLayout();
            }
            boolean z = !isSmoothScrolling() && this.mFocusScrollStrategy == 0;
            int i5 = this.mFocusPosition;
            if (i5 != -1 && (i3 = this.mFocusPositionOffset) != Integer.MIN_VALUE) {
                this.mFocusPosition = i5 + i3;
                this.mSubFocusPosition = 0;
            }
            this.mFocusPositionOffset = 0;
            View viewFindViewByPosition = findViewByPosition(this.mFocusPosition);
            int i6 = this.mFocusPosition;
            int i7 = this.mSubFocusPosition;
            boolean zHasFocus = this.mBaseGridView.hasFocus();
            Grid grid = this.mGrid;
            int firstVisibleIndex = grid != null ? grid.getFirstVisibleIndex() : -1;
            Grid grid2 = this.mGrid;
            int lastVisibleIndex = grid2 != null ? grid2.getLastVisibleIndex() : -1;
            if (this.mOrientation == 0) {
                remainingScrollVertical = state.getRemainingScrollHorizontal();
                remainingScrollHorizontal = state.getRemainingScrollVertical();
            } else {
                remainingScrollHorizontal = state.getRemainingScrollHorizontal();
                remainingScrollVertical = state.getRemainingScrollVertical();
            }
            if (layoutInit()) {
                this.mFlag |= 4;
                this.mGrid.setStart(this.mFocusPosition);
                fastRelayout();
            } else {
                int i8 = this.mFlag;
                this.mFlag = i8 & (-5);
                this.mFlag = (z ? 16 : 0) | (i8 & (-21));
                if (z && (firstVisibleIndex < 0 || (i = this.mFocusPosition) > lastVisibleIndex || i < firstVisibleIndex)) {
                    firstVisibleIndex = this.mFocusPosition;
                    lastVisibleIndex = firstVisibleIndex;
                }
                this.mGrid.setStart(firstVisibleIndex);
                if (lastVisibleIndex != -1) {
                    while (appendOneColumnVisibleItems() && findViewByPosition(lastVisibleIndex) == null) {
                    }
                }
            }
            while (true) {
                updateScrollLimits();
                int firstVisibleIndex2 = this.mGrid.getFirstVisibleIndex();
                int lastVisibleIndex2 = this.mGrid.getLastVisibleIndex();
                focusToViewInLayout(zHasFocus, z, -remainingScrollVertical, -remainingScrollHorizontal);
                appendVisibleItems();
                prependVisibleItems();
                if (this.mGrid.getFirstVisibleIndex() == firstVisibleIndex2 && this.mGrid.getLastVisibleIndex() == lastVisibleIndex2) {
                    break;
                }
            }
            removeInvisibleViewsAtFront();
            removeInvisibleViewsAtEnd();
            if (state.willRunPredictiveAnimations()) {
                fillScrapViewsInPostLayout();
            }
            int i9 = this.mFlag;
            if ((i9 & 1024) != 0) {
                this.mFlag = i9 & (-1025);
            } else {
                updateRowSecondarySizeRefresh();
            }
            if (((this.mFlag & 4) != 0 && ((i2 = this.mFocusPosition) != i6 || this.mSubFocusPosition != i7 || findViewByPosition(i2) != viewFindViewByPosition || (this.mFlag & 8) != 0)) || (this.mFlag & 20) == 16) {
                dispatchChildSelected();
            }
            dispatchChildSelectedAndPositioned();
            if ((this.mFlag & 64) != 0) {
                scrollDirectionPrimary(getSlideOutDistance());
            }
            this.mFlag &= -4;
            leaveContext();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutCompleted(RecyclerView.State state) {
        int size;
        if (this.mOnLayoutCompletedListeners == null || r1.size() - 1 < 0) {
            return;
        }
        ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(this.mOnLayoutCompletedListeners.get(size));
        throw null;
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00d9  */
    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onMeasure(androidx.recyclerview.widget.RecyclerView.Recycler r7, androidx.recyclerview.widget.RecyclerView.State r8, int r9, int r10) {
        /*
            Method dump skipped, instruction units count: 246
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.GridLayoutManager.onMeasure(androidx.recyclerview.widget.RecyclerView$Recycler, androidx.recyclerview.widget.RecyclerView$State, int, int):void");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean onRequestChildFocus(RecyclerView recyclerView, RecyclerView.State state, View view, View view2) {
        if ((this.mFlag & 32768) == 0 && getAdapterPositionByView(view) != -1 && (this.mFlag & 35) == 0) {
            scrollToView(view, view2, true);
        }
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            this.mFocusPosition = savedState.mIndex;
            this.mFocusPositionOffset = 0;
            this.mChildrenStates.loadFromBundle(savedState.mChildStates);
            this.mFlag |= 256;
            requestLayout();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x000b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void onRtlPropertiesChanged(int r6) {
        /*
            r5 = this;
            int r0 = r5.mOrientation
            r1 = 0
            r2 = 1
            if (r0 != 0) goto Ld
            if (r6 != r2) goto Lb
            r0 = 262144(0x40000, float:3.67342E-40)
            goto L11
        Lb:
            r0 = r1
            goto L11
        Ld:
            if (r6 != r2) goto Lb
            r0 = 524288(0x80000, float:7.34684E-40)
        L11:
            int r3 = r5.mFlag
            r4 = 786432(0xc0000, float:1.102026E-39)
            r4 = r4 & r3
            if (r4 != r0) goto L19
            return
        L19:
            r4 = -786433(0xfffffffffff3ffff, float:NaN)
            r3 = r3 & r4
            r0 = r0 | r3
            r0 = r0 | 256(0x100, float:3.59E-43)
            r5.mFlag = r0
            androidx.leanback.widget.WindowAlignment r5 = r5.mWindowAlignment
            androidx.leanback.widget.WindowAlignment$Axis r5 = r5.horizontal
            if (r6 != r2) goto L29
            r1 = r2
        L29:
            r5.setReversedFlow(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.GridLayoutManager.onRtlPropertiesChanged(int):void");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState();
        savedState.mIndex = getSelection();
        Bundle bundleSaveAsBundle = this.mChildrenStates.saveAsBundle();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            int adapterPositionByView = getAdapterPositionByView(childAt);
            if (adapterPositionByView != -1) {
                bundleSaveAsBundle = this.mChildrenStates.saveOnScreenView(bundleSaveAsBundle, childAt, adapterPositionByView);
            }
        }
        savedState.mChildStates = bundleSaveAsBundle;
        return savedState;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0046  */
    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean performAccessibilityAction(androidx.recyclerview.widget.RecyclerView.Recycler r5, androidx.recyclerview.widget.RecyclerView.State r6, int r7, android.os.Bundle r8) {
        /*
            r4 = this;
            boolean r8 = r4.isScrollEnabled()
            r0 = 1
            if (r8 != 0) goto L8
            return r0
        L8:
            r4.saveContext(r5, r6)
            int r5 = r4.mFlag
            r8 = 262144(0x40000, float:3.67342E-40)
            r5 = r5 & r8
            r8 = 0
            if (r5 == 0) goto L15
            r5 = r0
            goto L16
        L15:
            r5 = r8
        L16:
            int r1 = r4.mOrientation
            r2 = 8192(0x2000, float:1.14794E-41)
            r3 = 4096(0x1000, float:5.74E-42)
            if (r1 != 0) goto L34
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat r1 = androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_LEFT
            int r1 = r1.getId()
            if (r7 != r1) goto L29
            if (r5 == 0) goto L3c
            goto L46
        L29:
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat r1 = androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_RIGHT
            int r1 = r1.getId()
            if (r7 != r1) goto L47
            if (r5 == 0) goto L46
            goto L3c
        L34:
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat r5 = androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_UP
            int r5 = r5.getId()
            if (r7 != r5) goto L3e
        L3c:
            r7 = r2
            goto L47
        L3e:
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat r5 = androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN
            int r5 = r5.getId()
            if (r7 != r5) goto L47
        L46:
            r7 = r3
        L47:
            int r5 = r4.mFocusPosition
            if (r5 != 0) goto L4f
            if (r7 != r2) goto L4f
            r1 = r0
            goto L50
        L4f:
            r1 = r8
        L50:
            int r6 = r6.getItemCount()
            int r6 = r6 - r0
            if (r5 != r6) goto L5b
            if (r7 != r3) goto L5b
            r5 = r0
            goto L5c
        L5b:
            r5 = r8
        L5c:
            if (r1 != 0) goto L75
            if (r5 == 0) goto L61
            goto L75
        L61:
            if (r7 == r3) goto L6e
            if (r7 == r2) goto L66
            goto L78
        L66:
            r4.processPendingMovement(r8)
            r5 = -1
            r4.processSelectionMoves(r8, r5)
            goto L78
        L6e:
            r4.processPendingMovement(r0)
            r4.processSelectionMoves(r8, r0)
            goto L78
        L75:
            r4.sendTypeViewScrolledAccessibilityEvent()
        L78:
            r4.leaveContext()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.GridLayoutManager.performAccessibilityAction(androidx.recyclerview.widget.RecyclerView$Recycler, androidx.recyclerview.widget.RecyclerView$State, int, android.os.Bundle):boolean");
    }

    void processPendingMovement(boolean z) {
        int i;
        if (z) {
            if (hasCreatedLastItem()) {
                return;
            }
        } else if (hasCreatedFirstItem()) {
            return;
        }
        PendingMoveSmoothScroller pendingMoveSmoothScroller = this.mPendingMoveSmoothScroller;
        if (pendingMoveSmoothScroller == null) {
            PendingMoveSmoothScroller pendingMoveSmoothScroller2 = new PendingMoveSmoothScroller(z ? 1 : -1, this.mNumRows > 1);
            this.mFocusPositionOffset = 0;
            startSmoothScroll(pendingMoveSmoothScroller2);
        } else if (z) {
            pendingMoveSmoothScroller.increasePendingMoves();
        } else {
            pendingMoveSmoothScroller.decreasePendingMoves();
        }
        if (this.mOrientation == 0) {
            i = 4;
            if (getLayoutDirection() != 1 ? !z : z) {
                i = 3;
            }
        } else {
            i = z ? 2 : 1;
        }
        getAudioManager().playSoundEffect(i);
    }

    int processSelectionMoves(boolean z, int i) {
        Grid grid = this.mGrid;
        if (grid == null) {
            return i;
        }
        int i2 = this.mFocusPosition;
        int rowIndex = i2 != -1 ? grid.getRowIndex(i2) : -1;
        int childCount = getChildCount();
        View view = null;
        for (int i3 = 0; i3 < childCount && i != 0; i3++) {
            int i4 = i > 0 ? i3 : (childCount - 1) - i3;
            View childAt = getChildAt(i4);
            if (canScrollTo(childAt)) {
                int adapterPositionByIndex = getAdapterPositionByIndex(i4);
                int rowIndex2 = this.mGrid.getRowIndex(adapterPositionByIndex);
                if (rowIndex == -1) {
                    i2 = adapterPositionByIndex;
                    view = childAt;
                    rowIndex = rowIndex2;
                } else if (rowIndex2 == rowIndex && ((i > 0 && adapterPositionByIndex > i2) || (i < 0 && adapterPositionByIndex < i2))) {
                    i = i > 0 ? i - 1 : i + 1;
                    i2 = adapterPositionByIndex;
                    view = childAt;
                }
            }
        }
        if (view != null) {
            if (z) {
                if (hasFocus()) {
                    this.mFlag |= 32;
                    view.requestFocus();
                    this.mFlag &= -33;
                }
                this.mFocusPosition = i2;
                this.mSubFocusPosition = 0;
                return i;
            }
            scrollToView(view, true);
        }
        return i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void removeAndRecycleAllViews(RecyclerView.Recycler recycler) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            removeAndRecycleViewAt(childCount, recycler);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view, Rect rect, boolean z) {
        return false;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollHorizontallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if ((this.mFlag & 512) == 0 || !hasDoneFirstLayout()) {
            return 0;
        }
        saveContext(recycler, state);
        this.mFlag = (this.mFlag & (-4)) | 2;
        int iScrollDirectionPrimary = this.mOrientation == 0 ? scrollDirectionPrimary(i) : scrollDirectionSecondary(i);
        leaveContext();
        this.mFlag &= -4;
        return iScrollDirectionPrimary;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void scrollToPosition(int i) {
        setSelection(i, 0, false, 0);
    }

    void scrollToSelection(int i, int i2, boolean z, int i3) {
        this.mPrimaryScrollExtra = i3;
        View viewFindViewByPosition = findViewByPosition(i);
        boolean zIsSmoothScrolling = isSmoothScrolling();
        if (!zIsSmoothScrolling && !this.mBaseGridView.isLayoutRequested() && viewFindViewByPosition != null && getAdapterPositionByView(viewFindViewByPosition) == i) {
            this.mFlag |= 32;
            scrollToView(viewFindViewByPosition, z);
            this.mFlag &= -33;
            return;
        }
        int i4 = this.mFlag;
        if ((i4 & 512) == 0 || (i4 & 64) != 0) {
            this.mFocusPosition = i;
            this.mSubFocusPosition = i2;
            this.mFocusPositionOffset = Integer.MIN_VALUE;
            return;
        }
        if (z && !this.mBaseGridView.isLayoutRequested()) {
            this.mFocusPosition = i;
            this.mSubFocusPosition = i2;
            this.mFocusPositionOffset = Integer.MIN_VALUE;
            if (!hasDoneFirstLayout()) {
                Log.w(getTag(), "setSelectionSmooth should not be called before first layout pass");
                return;
            }
            int iStartPositionSmoothScroller = startPositionSmoothScroller(i);
            if (iStartPositionSmoothScroller != this.mFocusPosition) {
                this.mFocusPosition = iStartPositionSmoothScroller;
                this.mSubFocusPosition = 0;
                return;
            }
            return;
        }
        if (zIsSmoothScrolling) {
            skipSmoothScrollerOnStopInternal();
            this.mBaseGridView.stopScroll();
        }
        if (!this.mBaseGridView.isLayoutRequested() && viewFindViewByPosition != null && getAdapterPositionByView(viewFindViewByPosition) == i) {
            this.mFlag |= 32;
            scrollToView(viewFindViewByPosition, z);
            this.mFlag &= -33;
        } else {
            this.mFocusPosition = i;
            this.mSubFocusPosition = i2;
            this.mFocusPositionOffset = Integer.MIN_VALUE;
            this.mFlag |= 256;
            requestLayout();
        }
    }

    void scrollToView(View view, boolean z) {
        scrollToView(view, view == null ? null : view.findFocus(), z);
    }

    void scrollToView(View view, boolean z, int i, int i2) {
        scrollToView(view, view == null ? null : view.findFocus(), z, i, i2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollVerticallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if ((this.mFlag & 512) == 0 || !hasDoneFirstLayout()) {
            return 0;
        }
        this.mFlag = (this.mFlag & (-4)) | 2;
        saveContext(recycler, state);
        int iScrollDirectionPrimary = this.mOrientation == 1 ? scrollDirectionPrimary(i) : scrollDirectionSecondary(i);
        leaveContext();
        this.mFlag &= -4;
        return iScrollDirectionPrimary;
    }

    public void setFocusOutAllowed(boolean z, boolean z2) {
        this.mFlag = (z ? 2048 : 0) | (this.mFlag & (-6145)) | (z2 ? 4096 : 0);
    }

    void setFocusOutSideAllowed(boolean z, boolean z2) {
        this.mFlag = (z ? 8192 : 0) | (this.mFlag & (-24577)) | (z2 ? 16384 : 0);
    }

    void setGravity(int i) {
        this.mGravity = i;
    }

    void setGridView(BaseGridView baseGridView) {
        this.mBaseGridView = baseGridView;
        this.mGrid = null;
    }

    void setHorizontalSpacing(int i) {
        if (this.mOrientation == 0) {
            this.mHorizontalSpacing = i;
            this.mSpacingPrimary = i;
        } else {
            this.mHorizontalSpacing = i;
            this.mSpacingSecondary = i;
        }
    }

    void setNumRows(int i) {
        if (i < 0) {
            throw new IllegalArgumentException();
        }
        this.mNumRowsRequested = i;
    }

    void setOnChildViewHolderSelectedListener(OnChildViewHolderSelectedListener onChildViewHolderSelectedListener) {
        if (onChildViewHolderSelectedListener == null) {
            this.mChildViewHolderSelectedListeners = null;
            return;
        }
        ArrayList arrayList = this.mChildViewHolderSelectedListeners;
        if (arrayList == null) {
            this.mChildViewHolderSelectedListeners = new ArrayList();
        } else {
            arrayList.clear();
        }
        this.mChildViewHolderSelectedListeners.add(onChildViewHolderSelectedListener);
    }

    public void setOrientation(int i) {
        if (i == 0 || i == 1) {
            this.mOrientation = i;
            this.mOrientationHelper = OrientationHelper.createOrientationHelper(this, i);
            this.mWindowAlignment.setOrientation(i);
            this.mItemAlignment.setOrientation(i);
            this.mFlag |= 256;
        }
    }

    void setRowHeight(int i) {
        if (i >= 0 || i == -2) {
            this.mRowSizeSecondaryRequested = i;
            return;
        }
        throw new IllegalArgumentException("Invalid row height: " + i);
    }

    void setSelection(int i, int i2) {
        setSelection(i, 0, false, i2);
    }

    void setSelection(int i, int i2, boolean z, int i3) {
        if ((this.mFocusPosition == i || i == -1) && i2 == this.mSubFocusPosition && i3 == this.mPrimaryScrollExtra) {
            return;
        }
        scrollToSelection(i, i2, z, i3);
    }

    void setSelectionSmooth(int i) {
        setSelection(i, 0, true, 0);
    }

    void setSelectionWithSub(int i, int i2, int i3) {
        setSelection(i, i2, false, i3);
    }

    void setVerticalSpacing(int i) {
        if (this.mOrientation == 1) {
            this.mVerticalSpacing = i;
            this.mSpacingPrimary = i;
        } else {
            this.mVerticalSpacing = i;
            this.mSpacingSecondary = i;
        }
    }

    void setWindowAlignment(int i) {
        this.mWindowAlignment.mainAxis().setWindowAlignment(i);
    }

    void skipSmoothScrollerOnStopInternal() {
        GridLinearSmoothScroller gridLinearSmoothScroller = this.mCurrentSmoothScroller;
        if (gridLinearSmoothScroller != null) {
            gridLinearSmoothScroller.mSkipOnStopInternal = true;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i) {
        setSelection(i, 0, true, 0);
    }

    int startPositionSmoothScroller(int i) {
        GridLinearSmoothScroller gridLinearSmoothScroller = new GridLinearSmoothScroller() { // from class: androidx.leanback.widget.GridLayoutManager.4
            @Override // androidx.recyclerview.widget.RecyclerView.SmoothScroller
            public PointF computeScrollVectorForPosition(int i2) {
                if (getChildCount() == 0) {
                    return null;
                }
                GridLayoutManager gridLayoutManager = GridLayoutManager.this;
                int position = gridLayoutManager.getPosition(gridLayoutManager.getChildAt(0));
                GridLayoutManager gridLayoutManager2 = GridLayoutManager.this;
                int i3 = ((gridLayoutManager2.mFlag & 262144) == 0 ? i2 >= position : i2 <= position) ? 1 : -1;
                return gridLayoutManager2.mOrientation == 0 ? new PointF(i3, 0.0f) : new PointF(0.0f, i3);
            }
        };
        gridLinearSmoothScroller.setTargetPosition(i);
        startSmoothScroll(gridLinearSmoothScroller);
        return gridLinearSmoothScroller.getTargetPosition();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void startSmoothScroll(RecyclerView.SmoothScroller smoothScroller) {
        skipSmoothScrollerOnStopInternal();
        super.startSmoothScroll(smoothScroller);
        if (!smoothScroller.isRunning() || !(smoothScroller instanceof GridLinearSmoothScroller)) {
            this.mCurrentSmoothScroller = null;
            this.mPendingMoveSmoothScroller = null;
            return;
        }
        GridLinearSmoothScroller gridLinearSmoothScroller = (GridLinearSmoothScroller) smoothScroller;
        this.mCurrentSmoothScroller = gridLinearSmoothScroller;
        if (gridLinearSmoothScroller instanceof PendingMoveSmoothScroller) {
            this.mPendingMoveSmoothScroller = (PendingMoveSmoothScroller) gridLinearSmoothScroller;
        } else {
            this.mPendingMoveSmoothScroller = null;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean supportsPredictiveItemAnimations() {
        return true;
    }

    void updatePositionDeltaInPreLayout() {
        if (getChildCount() <= 0) {
            this.mPositionDeltaInPreLayout = 0;
        } else {
            this.mPositionDeltaInPreLayout = this.mGrid.getFirstVisibleIndex() - ((LayoutParams) getChildAt(0).getLayoutParams()).getViewLayoutPosition();
        }
    }

    void updatePositionToRowMapInPostLayout() {
        Grid.Location location;
        this.mPositionToRowInPostLayout.clear();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            int oldPosition = this.mBaseGridView.getChildViewHolder(getChildAt(i)).getOldPosition();
            if (oldPosition >= 0 && (location = this.mGrid.getLocation(oldPosition)) != null) {
                this.mPositionToRowInPostLayout.put(oldPosition, location.mRow);
            }
        }
    }

    void updateScrollLimits() {
        int firstVisibleIndex;
        int lastVisibleIndex;
        int itemCount;
        int itemCount2;
        int iFindRowMax;
        int viewCenter;
        int iFindRowMin;
        int viewCenter2;
        if (this.mState.getItemCount() == 0) {
            return;
        }
        if ((this.mFlag & 262144) == 0) {
            firstVisibleIndex = this.mGrid.getLastVisibleIndex();
            itemCount2 = this.mState.getItemCount() - 1;
            lastVisibleIndex = this.mGrid.getFirstVisibleIndex();
            itemCount = 0;
        } else {
            firstVisibleIndex = this.mGrid.getFirstVisibleIndex();
            lastVisibleIndex = this.mGrid.getLastVisibleIndex();
            itemCount = this.mState.getItemCount() - 1;
            itemCount2 = 0;
        }
        if (firstVisibleIndex < 0 || lastVisibleIndex < 0) {
            return;
        }
        boolean z = firstVisibleIndex == itemCount2;
        boolean z2 = lastVisibleIndex == itemCount;
        if (z || !this.mWindowAlignment.mainAxis().isMaxUnknown() || z2 || !this.mWindowAlignment.mainAxis().isMinUnknown()) {
            if (z) {
                iFindRowMax = this.mGrid.findRowMax(true, sTwoInts);
                View viewFindViewByPosition = findViewByPosition(sTwoInts[1]);
                viewCenter = getViewCenter(viewFindViewByPosition);
                int[] alignMultiple = ((LayoutParams) viewFindViewByPosition.getLayoutParams()).getAlignMultiple();
                if (alignMultiple != null && alignMultiple.length > 0) {
                    viewCenter += alignMultiple[alignMultiple.length - 1] - alignMultiple[0];
                }
            } else {
                iFindRowMax = Integer.MAX_VALUE;
                viewCenter = Integer.MAX_VALUE;
            }
            if (z2) {
                iFindRowMin = this.mGrid.findRowMin(false, sTwoInts);
                viewCenter2 = getViewCenter(findViewByPosition(sTwoInts[1]));
            } else {
                iFindRowMin = Integer.MIN_VALUE;
                viewCenter2 = Integer.MIN_VALUE;
            }
            this.mWindowAlignment.mainAxis().updateMinMax(iFindRowMin, iFindRowMax, viewCenter2, viewCenter);
        }
    }
}
