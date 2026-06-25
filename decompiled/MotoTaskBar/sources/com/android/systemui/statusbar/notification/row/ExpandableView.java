package com.android.systemui.statusbar.notification.row;

import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import com.android.app.animation.Interpolators;
import com.android.systemui.Dumpable;
import com.android.systemui.res.R$dimen;
import com.android.systemui.statusbar.notification.Roundable;
import com.android.systemui.statusbar.notification.RoundableState;
import com.android.systemui.statusbar.notification.stack.ExpandableViewState;
import com.android.systemui.util.Compile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class ExpandableView extends FrameLayout implements Dumpable, Roundable {
    protected static final boolean DUMP_VERBOSE;
    private static Rect mClipRect;
    private int mActualHeight;
    private boolean mChangingPosition;
    protected int mClipBottomAmount;
    private boolean mClipToActualHeight;
    protected int mClipTopAmount;
    protected int mContentShift;
    protected float mContentTransformationAmount;
    private float mContentTranslation;
    protected float mExtraWidthForClipping;
    private boolean mInRemovalAnimation;
    private boolean mInShelf;
    protected boolean mIsLastChild;
    protected boolean mLastInSection;
    private ArrayList mMatchParentViews;
    protected int mMinimumHeightForClipping;
    protected OnHeightChangedListener mOnHeightChangedListener;
    private RoundableState mRoundableState;
    private boolean mTransformingInShelf;
    private ViewGroup mTransientContainer;
    private final ExpandableViewState mViewState;
    private boolean mWillBeGone;

    public interface OnHeightChangedListener {
        void onHeightChanged(ExpandableView expandableView, boolean z);

        void onReset(ExpandableView expandableView);
    }

    static {
        DUMP_VERBOSE = Compile.IS_DEBUG && Log.isLoggable("ExpandableView", 2);
        mClipRect = new Rect();
    }

    public ExpandableView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRoundableState = null;
        this.mMinimumHeightForClipping = 0;
        this.mExtraWidthForClipping = 0.0f;
        this.mMatchParentViews = new ArrayList();
        this.mClipToActualHeight = true;
        this.mChangingPosition = false;
        this.mViewState = createExpandableViewState();
        initDimens();
    }

    private void initDimens() {
        this.mContentShift = getResources().getDimensionPixelSize(R$dimen.shelf_transform_content_shift);
    }

    public void addToTransientContainer(ViewGroup viewGroup, int i) {
        viewGroup.addTransientView(this, i);
        setTransientContainer(viewGroup);
    }

    protected void applyContentTransformation(float f, float f2) {
    }

    public void applyViewState() {
        ExpandableViewState expandableViewState = this.mViewState;
        if (expandableViewState.gone) {
            return;
        }
        expandableViewState.applyToView(this);
    }

    public boolean areChildrenExpanded() {
        return false;
    }

    protected ExpandableViewState createExpandableViewState() {
        return new ExpandableViewState();
    }

    public int getActualHeight() {
        return this.mActualHeight;
    }

    public void getBoundsOnScreen(Rect rect, boolean z) {
        super.getBoundsOnScreen(rect, z);
        if (getTop() + getTranslationY() < 0.0f) {
            rect.top = (int) (rect.top + getTop() + getTranslationY());
        }
        rect.bottom = rect.top + getActualHeight();
        rect.top += Math.max(0, getClipTopAmount());
    }

    public int getClipBottomAmount() {
        return this.mClipBottomAmount;
    }

    public int getClipHeight() {
        return Math.max(Math.max((this.mActualHeight - this.mClipTopAmount) - this.mClipBottomAmount, 0), this.mMinimumHeightForClipping);
    }

    public int getClipTopAmount() {
        return this.mClipTopAmount;
    }

    public int getCollapsedHeight() {
        return getHeight();
    }

    protected float getContentTransformationShift() {
        return this.mContentShift;
    }

    @Override // android.view.View
    public void getDrawingRect(Rect rect) {
        super.getDrawingRect(rect);
        rect.left = (int) (rect.left + getTranslationX());
        rect.right = (int) (rect.right + getTranslationX());
        rect.bottom = (int) (rect.top + getTranslationY() + getActualHeight());
        rect.top = (int) (rect.top + getTranslationY() + getClipTopAmount());
    }

    public int getHeightWithoutLockscreenConstraints() {
        return getHeight();
    }

    public int getIntrinsicHeight() {
        return getHeight();
    }

    public int getMaxContentHeight() {
        return getHeight();
    }

    public int getMinHeight() {
        return getMinHeight(false);
    }

    public int getMinHeight(boolean z) {
        return getHeight();
    }

    public float getOutlineAlpha() {
        return 0.0f;
    }

    public int getOutlineTranslation() {
        return 0;
    }

    public int getRelativeTopPadding(View view) {
        int top = 0;
        while (view.getParent() instanceof ViewGroup) {
            top += view.getTop();
            view = (View) view.getParent();
            if (view == this) {
                break;
            }
        }
        return top;
    }

    public RoundableState getRoundableState() {
        if (this.mRoundableState == null) {
            this.mRoundableState = new RoundableState(this, this, 0.0f);
        }
        return this.mRoundableState;
    }

    public ViewGroup getTransientContainer() {
        return this.mTransientContainer;
    }

    public float getTranslation() {
        return getTranslationX();
    }

    public ExpandableViewState getViewState() {
        return this.mViewState;
    }

    public boolean hasExpandingChild() {
        return false;
    }

    public boolean hasNoContentHeight() {
        return false;
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return super.hasOverlappingRendering() && getActualHeight() <= getHeight();
    }

    public boolean inRemovalAnimation() {
        return this.mInRemovalAnimation;
    }

    public boolean isChangingPosition() {
        return this.mChangingPosition;
    }

    public boolean isChildInGroup() {
        return false;
    }

    public boolean isExpandAnimationRunning() {
        return false;
    }

    public boolean isGroupExpanded() {
        return false;
    }

    public boolean isGroupExpansionChanging() {
        return false;
    }

    public boolean isHeadsUpAnimatingAway() {
        return false;
    }

    public boolean isInShelf() {
        return this.mInShelf;
    }

    public boolean isLastInSection() {
        return this.mLastInSection;
    }

    public boolean isPinned() {
        return false;
    }

    public boolean isSummaryWithChildren() {
        return false;
    }

    public boolean isTransparent() {
        return false;
    }

    public boolean mustStayOnScreen() {
        return false;
    }

    public void notifyHeightChanged(boolean z) {
        OnHeightChangedListener onHeightChangedListener = this.mOnHeightChangedListener;
        if (onHeightChangedListener != null) {
            onHeightChangedListener.onHeightChanged(this, z);
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        initDimens();
    }

    public void onHeightReset() {
        OnHeightChangedListener onHeightChangedListener = this.mOnHeightChangedListener;
        if (onHeightChangedListener != null) {
            onHeightChangedListener.onReset(this);
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        updateClipping();
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i2);
        int paddingStart = getPaddingStart() + getPaddingEnd();
        int mode = View.MeasureSpec.getMode(i2);
        int iMin = Integer.MAX_VALUE;
        if (mode != 0 && size != 0) {
            iMin = Math.min(size, Integer.MAX_VALUE);
        }
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(iMin, Integer.MIN_VALUE);
        int childCount = getChildCount();
        int i3 = 0;
        int iMax = 0;
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            if (childAt.getVisibility() != 8) {
                ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                int i5 = layoutParams.height;
                if (i5 != -1) {
                    childAt.measure(FrameLayout.getChildMeasureSpec(i, paddingStart, layoutParams.width), i5 >= 0 ? View.MeasureSpec.makeMeasureSpec(Math.min(i5, iMin), 1073741824) : iMakeMeasureSpec);
                    iMax = Math.max(iMax, childAt.getMeasuredHeight());
                } else {
                    this.mMatchParentViews.add(childAt);
                }
            }
        }
        if (mode != 1073741824) {
            size = Math.min(iMin, iMax);
        }
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(size, 1073741824);
        ArrayList arrayList = this.mMatchParentViews;
        int size2 = arrayList.size();
        while (i3 < size2) {
            Object obj = arrayList.get(i3);
            i3++;
            View view = (View) obj;
            view.measure(FrameLayout.getChildMeasureSpec(i, paddingStart, view.getLayoutParams().width), iMakeMeasureSpec2);
        }
        this.mMatchParentViews.clear();
        setMeasuredDimension(View.MeasureSpec.getSize(i), size);
    }

    public void performAddAnimation(long j, long j2, boolean z) {
        performAddAnimation(j, j2, z, null);
    }

    public abstract void performAddAnimation(long j, long j2, boolean z, Runnable runnable);

    public abstract long performRemoveAnimation(long j, long j2, float f, boolean z, Runnable runnable, Runnable runnable2, AnimatorListenerAdapter animatorListenerAdapter);

    public boolean pointInView(float f, float f2, float f3) {
        return f >= (-f3) && f2 >= ((float) Math.max(0, this.mClipTopAmount)) - f3 && f < ((float) (((FrameLayout) this).mRight - ((FrameLayout) this).mLeft)) + f3 && f2 < ((float) this.mActualHeight) + f3;
    }

    public void removeFromTransientContainer() {
        ViewGroup transientContainer = getTransientContainer();
        if (transientContainer == null) {
            return;
        }
        ViewParent parent = getParent();
        if (parent == transientContainer) {
            transientContainer.removeTransientView(this);
            setTransientContainer(null);
            return;
        }
        Log.w("ExpandableView", "Expandable view " + this + " has transient container " + transientContainer + " but different parent " + parent);
        setTransientContainer(null);
    }

    public void removeFromTransientContainerForAdditionTo(ViewGroup viewGroup) {
        ViewParent parent = getParent();
        ViewGroup transientContainer = getTransientContainer();
        if (parent == null || parent == viewGroup) {
            removeFromTransientContainer();
            return;
        }
        if (transientContainer == null) {
            throw new IllegalStateException("Can't add view " + this + " to container " + viewGroup + "; current parent " + parent + " is not a transient container");
        }
        if (transientContainer != parent) {
            throw new IllegalStateException("Expandable view " + this + " has transient container " + transientContainer + " but different parent " + parent);
        }
        Log.w("ExpandableView", "Removing view " + this + " from transient container " + transientContainer + " in preparation for moving to parent " + viewGroup);
        transientContainer.removeTransientView(this);
        setTransientContainer(null);
    }

    public ExpandableViewState resetViewState() {
        this.mViewState.height = getIntrinsicHeight();
        this.mViewState.gone = getVisibility() == 8;
        this.mViewState.setAlpha(1.0f);
        ExpandableViewState expandableViewState = this.mViewState;
        expandableViewState.notGoneIndex = -1;
        expandableViewState.setXTranslation(getTranslationX());
        ExpandableViewState expandableViewState2 = this.mViewState;
        expandableViewState2.hidden = false;
        expandableViewState2.setScaleX(getScaleX());
        this.mViewState.setScaleY(getScaleY());
        ExpandableViewState expandableViewState3 = this.mViewState;
        expandableViewState3.inShelf = false;
        expandableViewState3.headsUpIsVisible = false;
        if (this instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this;
            List attachedChildren = expandableNotificationRow.getAttachedChildren();
            if (expandableNotificationRow.isSummaryWithChildren() && attachedChildren != null) {
                Iterator it = attachedChildren.iterator();
                while (it.hasNext()) {
                    ((ExpandableNotificationRow) it.next()).resetViewState();
                }
            }
        }
        return this.mViewState;
    }

    public void setActualHeight(int i) {
        setActualHeight(i, true);
    }

    public void setActualHeight(int i, boolean z) {
        if (this.mActualHeight != i) {
            this.mActualHeight = i;
            updateClipping();
            if (z) {
                notifyHeightChanged(false);
            }
        }
    }

    public void setActualHeightAnimating(boolean z) {
    }

    public void setBelowSpeedBump(boolean z) {
    }

    public void setChangingPosition(boolean z) {
        this.mChangingPosition = z;
    }

    public void setClipBottomAmount(int i) {
        this.mClipBottomAmount = i;
        updateClipping();
    }

    public void setClipToActualHeight(boolean z) {
        this.mClipToActualHeight = z;
        updateClipping();
    }

    public void setClipTopAmount(int i) {
        this.mClipTopAmount = i;
        updateClipping();
    }

    public void setContentTransformationAmount(float f, boolean z) {
        boolean z2 = (z != this.mIsLastChild) | (this.mContentTransformationAmount != f);
        this.mIsLastChild = z;
        this.mContentTransformationAmount = f;
        if (z2) {
            updateContentTransformation();
        }
    }

    public void setExtraWidthForClipping(float f) {
        this.mExtraWidthForClipping = f;
    }

    public void setFakeShadowIntensity(float f, float f2, int i, int i2) {
    }

    public void setHeadsUpIsVisible() {
    }

    public void setHideSensitive(boolean z, boolean z2, long j, long j2) {
    }

    public void setHideSensitiveForIntrinsicHeight(boolean z) {
    }

    public void setInRemovalAnimation(boolean z) {
        this.mInRemovalAnimation = z;
    }

    public void setInShelf(boolean z) {
        this.mInShelf = z;
    }

    @Override // android.view.View
    public void setLayerType(int i, Paint paint) {
        if (i == 0 || hasOverlappingRendering()) {
            super.setLayerType(i, paint);
        }
    }

    public void setMinimumHeightForClipping(int i) {
        this.mMinimumHeightForClipping = i;
        updateClipping();
    }

    public void setOnHeightChangedListener(OnHeightChangedListener onHeightChangedListener) {
        this.mOnHeightChangedListener = onHeightChangedListener;
    }

    public void setTransformingInShelf(boolean z) {
        this.mTransformingInShelf = z;
    }

    public void setTransientContainer(ViewGroup viewGroup) {
        this.mTransientContainer = viewGroup;
    }

    public void setWillBeGone(boolean z) {
        this.mWillBeGone = z;
    }

    protected boolean shouldClipToActualHeight() {
        return true;
    }

    public boolean showingPulsing() {
        return false;
    }

    protected void updateClipping() {
        if (!this.mClipToActualHeight || !shouldClipToActualHeight()) {
            setClipBounds(null);
            return;
        }
        int clipTopAmount = getClipTopAmount();
        mClipRect.set(Integer.MIN_VALUE, clipTopAmount, Integer.MAX_VALUE, Math.max(Math.max(getActualHeight() - this.mClipBottomAmount, clipTopAmount), this.mMinimumHeightForClipping));
        setClipBounds(mClipRect);
    }

    protected void updateContentTransformation() {
        float contentTransformationShift = (-this.mContentTransformationAmount) * getContentTransformationShift();
        float interpolation = Interpolators.ALPHA_OUT.getInterpolation(Math.min((1.0f - this.mContentTransformationAmount) / 0.5f, 1.0f));
        if (this.mIsLastChild) {
            contentTransformationShift *= 0.4f;
        }
        this.mContentTranslation = contentTransformationShift;
        applyContentTransformation(interpolation, contentTransformationShift);
    }

    public boolean willBeGone() {
        return this.mWillBeGone;
    }
}
