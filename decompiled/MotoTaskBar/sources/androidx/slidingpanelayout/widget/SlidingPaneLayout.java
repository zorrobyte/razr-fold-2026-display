package androidx.slidingpanelayout.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.core.graphics.Insets;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.os.HandlerCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;
import androidx.slidingpanelayout.R$style;
import androidx.slidingpanelayout.R$styleable;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;
import androidx.window.layout.FoldingFeature;
import androidx.window.layout.WindowInfoTracker;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.KotlinNothingValueException;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.android.HandlerDispatcherKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* JADX INFO: compiled from: SlidingPaneLayout.kt */
/* JADX INFO: loaded from: classes.dex */
public class SlidingPaneLayout extends ViewGroup {
    public static final Companion Companion = new Companion(null);
    public static final UserResizeBehavior USER_RESIZE_RELAYOUT_WHEN_COMPLETE = new UserResizeBehavior() { // from class: androidx.slidingpanelayout.widget.SlidingPaneLayout$Companion$USER_RESIZE_RELAYOUT_WHEN_COMPLETE$1
        @Override // androidx.slidingpanelayout.widget.SlidingPaneLayout.UserResizeBehavior
        public void onUserResizeCancelled(SlidingPaneLayout slidingPaneLayout, int i) {
            slidingPaneLayout.getClass();
        }

        @Override // androidx.slidingpanelayout.widget.SlidingPaneLayout.UserResizeBehavior
        public void onUserResizeComplete(SlidingPaneLayout slidingPaneLayout, int i) {
            slidingPaneLayout.getClass();
            slidingPaneLayout.setSplitDividerPosition(i);
        }

        @Override // androidx.slidingpanelayout.widget.SlidingPaneLayout.UserResizeBehavior
        public void onUserResizeProgress(SlidingPaneLayout slidingPaneLayout, int i) {
            slidingPaneLayout.getClass();
        }

        @Override // androidx.slidingpanelayout.widget.SlidingPaneLayout.UserResizeBehavior
        public void onUserResizeStarted(SlidingPaneLayout slidingPaneLayout, int i) {
            slidingPaneLayout.getClass();
        }
    };
    public static final UserResizeBehavior USER_RESIZE_RELAYOUT_WHEN_MOVED = new UserResizeBehavior() { // from class: androidx.slidingpanelayout.widget.SlidingPaneLayout$Companion$USER_RESIZE_RELAYOUT_WHEN_MOVED$1
        @Override // androidx.slidingpanelayout.widget.SlidingPaneLayout.UserResizeBehavior
        public void onUserResizeCancelled(SlidingPaneLayout slidingPaneLayout, int i) {
            slidingPaneLayout.getClass();
        }

        @Override // androidx.slidingpanelayout.widget.SlidingPaneLayout.UserResizeBehavior
        public void onUserResizeComplete(SlidingPaneLayout slidingPaneLayout, int i) {
            slidingPaneLayout.getClass();
        }

        @Override // androidx.slidingpanelayout.widget.SlidingPaneLayout.UserResizeBehavior
        public void onUserResizeProgress(SlidingPaneLayout slidingPaneLayout, int i) {
            slidingPaneLayout.getClass();
            slidingPaneLayout.setSplitDividerPosition(i);
        }

        @Override // androidx.slidingpanelayout.widget.SlidingPaneLayout.UserResizeBehavior
        public void onUserResizeStarted(SlidingPaneLayout slidingPaneLayout, int i) {
            slidingPaneLayout.getClass();
        }
    };
    private boolean _isSlideable;
    private TouchHandler activeTouchHandler;
    private boolean awaitingFirstLayout;
    private final MotionEvent cancelEvent;
    private final Rect computedDividerExclusionRect;
    private float currentParallaxOffset;
    private float currentSlideOffset;
    private final Rect dividerGestureExclusionRect;
    private final DraggableDividerHandler draggableDividerHandler;
    private final FoldBoundsCalculator foldBoundsCalculator;
    private FoldingFeature foldingFeature;
    private final List gestureExclusionRectsList;
    private boolean isChildClippingToResizeDividerEnabled;
    private boolean isOverlappingEnabled;
    private boolean isUserResizingEnabled;
    private int lockMode;
    private View.OnClickListener onUserResizingDividerClickListener;
    private final OverlappingPaneHandler overlappingPaneHandler;
    private int parallaxDistance;
    private boolean preservedOpenState;
    private Drawable shadowDrawableLeft;
    private Drawable shadowDrawableRight;
    private int slideRange;
    private View slideableView;
    private int splitDividerPosition;
    private final Rect tmpRect;
    private final Rect tmpRect2;
    private final int touchTargetMin;
    private UserResizeBehavior userResizeBehavior;
    private Drawable userResizingDividerDrawable;
    private Job whileAttachedToVisibleWindowJob;
    private final WindowInfoTracker windowInfoTracker;

    /* JADX INFO: compiled from: SlidingPaneLayout.kt */
    public abstract class AbsDraggableDividerHandler implements TouchHandler {
        private boolean isDragging;
        private final int touchSlop;
        private float xDown = Float.NaN;
        private int dragPositionX = -1;

        public AbsDraggableDividerHandler(int i) {
            this.touchSlop = i;
        }

        private final boolean commonActionDown(MotionEvent motionEvent) {
            if (!dividerBoundsContains(MathKt.roundToInt(motionEvent.getX()), MathKt.roundToInt(motionEvent.getY()))) {
                return false;
            }
            this.xDown = motionEvent.getX();
            if (this.touchSlop == 0) {
                this.isDragging = true;
                this.dragPositionX = clampDraggingDividerPosition(MathKt.roundToInt(motionEvent.getX()));
                onUserResizeStarted();
            }
            return true;
        }

        private final boolean commonActionMove(MotionEvent motionEvent) {
            boolean z = false;
            if (Float.isNaN(this.xDown)) {
                return false;
            }
            if (!this.isDragging && Math.abs(motionEvent.getX() - this.xDown) >= this.touchSlop) {
                this.isDragging = true;
                z = true;
            }
            if (this.isDragging) {
                this.dragPositionX = clampDraggingDividerPosition(MathKt.roundToInt(motionEvent.getX()));
                if (z) {
                    onUserResizeStarted();
                }
                onUserResizeProgress();
            }
            return true;
        }

        public abstract int clampDraggingDividerPosition(int i);

        public abstract boolean dividerBoundsContains(int i, int i2);

        public final int getDragPositionX() {
            return this.dragPositionX;
        }

        public final boolean isDragging() {
            return this.isDragging;
        }

        public abstract void onDividerClicked();

        @Override // androidx.slidingpanelayout.widget.SlidingPaneLayout.TouchHandler
        public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            motionEvent.getClass();
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                return commonActionDown(motionEvent) && this.isDragging;
            }
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    return commonActionMove(motionEvent) && this.isDragging;
                }
                if (actionMasked != 3) {
                    return false;
                }
            }
            if (this.isDragging) {
                return false;
            }
            this.xDown = Float.NaN;
            return false;
        }

        @Override // androidx.slidingpanelayout.widget.SlidingPaneLayout.TouchHandler
        public final boolean onTouchEvent(MotionEvent motionEvent) {
            motionEvent.getClass();
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                return commonActionDown(motionEvent);
            }
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    return commonActionMove(motionEvent);
                }
                if (actionMasked != 3) {
                    return false;
                }
            }
            if (Float.isNaN(this.xDown)) {
                return false;
            }
            this.xDown = Float.NaN;
            if (this.isDragging) {
                this.isDragging = false;
                onUserResizeComplete(actionMasked == 3);
                this.dragPositionX = -1;
            } else if (actionMasked == 1 && dividerBoundsContains(MathKt.roundToInt(motionEvent.getX()), MathKt.roundToInt(motionEvent.getY()))) {
                onDividerClicked();
            }
            return true;
        }

        public abstract void onUserResizeComplete(boolean z);

        public abstract void onUserResizeProgress();

        public abstract void onUserResizeStarted();
    }

    /* JADX INFO: compiled from: SlidingPaneLayout.kt */
    public final class AccessibilityDelegate extends AccessibilityDelegateCompat {
        private final Rect tmpRect = new Rect();

        public AccessibilityDelegate() {
        }

        private final void copyNodeInfoNoChildren(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2) {
            Rect rect = this.tmpRect;
            accessibilityNodeInfoCompat2.getBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setVisibleToUser(accessibilityNodeInfoCompat2.isVisibleToUser());
            accessibilityNodeInfoCompat.setPackageName(accessibilityNodeInfoCompat2.getPackageName());
            accessibilityNodeInfoCompat.setClassName(accessibilityNodeInfoCompat2.getClassName());
            accessibilityNodeInfoCompat.setContentDescription(accessibilityNodeInfoCompat2.getContentDescription());
            accessibilityNodeInfoCompat.setEnabled(accessibilityNodeInfoCompat2.isEnabled());
            accessibilityNodeInfoCompat.setClickable(accessibilityNodeInfoCompat2.isClickable());
            accessibilityNodeInfoCompat.setFocusable(accessibilityNodeInfoCompat2.isFocusable());
            accessibilityNodeInfoCompat.setFocused(accessibilityNodeInfoCompat2.isFocused());
            accessibilityNodeInfoCompat.setAccessibilityFocused(accessibilityNodeInfoCompat2.isAccessibilityFocused());
            accessibilityNodeInfoCompat.setSelected(accessibilityNodeInfoCompat2.isSelected());
            accessibilityNodeInfoCompat.setLongClickable(accessibilityNodeInfoCompat2.isLongClickable());
            accessibilityNodeInfoCompat.addAction(accessibilityNodeInfoCompat2.getActions());
            accessibilityNodeInfoCompat.setMovementGranularities(accessibilityNodeInfoCompat2.getMovementGranularities());
        }

        public final boolean filter(View view) {
            return SlidingPaneLayout.this.isDimmed(view);
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            view.getClass();
            accessibilityEvent.getClass();
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName("androidx.slidingpanelayout.widget.SlidingPaneLayout");
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            view.getClass();
            accessibilityNodeInfoCompat.getClass();
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompatObtain = AccessibilityNodeInfoCompat.obtain(accessibilityNodeInfoCompat);
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompatObtain);
            accessibilityNodeInfoCompatObtain.getClass();
            copyNodeInfoNoChildren(accessibilityNodeInfoCompat, accessibilityNodeInfoCompatObtain);
            accessibilityNodeInfoCompatObtain.recycle();
            accessibilityNodeInfoCompat.setClassName("androidx.slidingpanelayout.widget.SlidingPaneLayout");
            accessibilityNodeInfoCompat.setSource(view);
            Object parentForAccessibility = view.getParentForAccessibility();
            if (parentForAccessibility instanceof View) {
                accessibilityNodeInfoCompat.setParent((View) parentForAccessibility);
            }
            int childCount = SlidingPaneLayout.this.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = SlidingPaneLayout.this.getChildAt(i);
                if (!filter(childAt) && childAt.getVisibility() == 0) {
                    childAt.setImportantForAccessibility(1);
                    accessibilityNodeInfoCompat.addChild(childAt);
                }
            }
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            viewGroup.getClass();
            view.getClass();
            accessibilityEvent.getClass();
            if (filter(view)) {
                return false;
            }
            return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
        }
    }

    /* JADX INFO: compiled from: SlidingPaneLayout.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: SlidingPaneLayout.kt */
    final class DraggableDividerHandler extends AbsDraggableDividerHandler {
        private final Rect tmpTargetRect;

        public DraggableDividerHandler() {
            super(ViewConfiguration.get(SlidingPaneLayout.this.getContext()).getScaledTouchSlop());
            this.tmpTargetRect = new Rect();
        }

        @Override // androidx.slidingpanelayout.widget.SlidingPaneLayout.AbsDraggableDividerHandler
        public int clampDraggingDividerPosition(int i) {
            View childAt;
            View childAt2;
            if (SlidingPaneLayout.this.isLayoutRtl()) {
                childAt = SlidingPaneLayout.this.getChildAt(1);
                childAt.getClass();
                childAt2 = SlidingPaneLayout.this.getChildAt(0);
                childAt2.getClass();
            } else {
                childAt = SlidingPaneLayout.this.getChildAt(0);
                childAt.getClass();
                childAt2 = SlidingPaneLayout.this.getChildAt(1);
                childAt2.getClass();
            }
            int paddingLeft = SlidingPaneLayout.this.getPaddingLeft();
            ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
            if (!(layoutParams instanceof LayoutParams)) {
                SlidingPaneLayoutKt.layoutParamsError(childAt, layoutParams);
                throw new KotlinNothingValueException();
            }
            LayoutParams layoutParams2 = (LayoutParams) layoutParams;
            int minimumChildWidth = paddingLeft + ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin + SlidingPaneLayout.this.getMinimumChildWidth(childAt);
            int width = SlidingPaneLayout.this.getWidth() - SlidingPaneLayout.this.getPaddingRight();
            ViewGroup.LayoutParams layoutParams3 = childAt2.getLayoutParams();
            if (layoutParams3 instanceof LayoutParams) {
                LayoutParams layoutParams4 = (LayoutParams) layoutParams3;
                return RangesKt.coerceIn(i, minimumChildWidth, (width - (((ViewGroup.MarginLayoutParams) layoutParams4).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams4).rightMargin)) - SlidingPaneLayout.this.getMinimumChildWidth(childAt2));
            }
            SlidingPaneLayoutKt.layoutParamsError(childAt2, layoutParams3);
            throw new KotlinNothingValueException();
        }

        @Override // androidx.slidingpanelayout.widget.SlidingPaneLayout.AbsDraggableDividerHandler
        public boolean dividerBoundsContains(int i, int i2) {
            SlidingPaneLayout slidingPaneLayout = SlidingPaneLayout.this;
            return slidingPaneLayout.computeDividerTargetRect(this.tmpTargetRect, slidingPaneLayout.getVisualDividerPosition()).contains(i, i2);
        }

        @Override // androidx.slidingpanelayout.widget.SlidingPaneLayout.AbsDraggableDividerHandler
        public void onDividerClicked() {
            View.OnClickListener onClickListener = SlidingPaneLayout.this.onUserResizingDividerClickListener;
            if (onClickListener != null) {
                onClickListener.onClick(SlidingPaneLayout.this);
            }
        }

        @Override // androidx.slidingpanelayout.widget.SlidingPaneLayout.AbsDraggableDividerHandler
        public void onUserResizeComplete(boolean z) {
            if (z) {
                SlidingPaneLayout.this.userResizeBehavior.onUserResizeCancelled(SlidingPaneLayout.this, getDragPositionX());
            } else {
                SlidingPaneLayout.this.userResizeBehavior.onUserResizeComplete(SlidingPaneLayout.this, getDragPositionX());
            }
            SlidingPaneLayout.this.invalidate();
        }

        @Override // androidx.slidingpanelayout.widget.SlidingPaneLayout.AbsDraggableDividerHandler
        public void onUserResizeProgress() {
            SlidingPaneLayout.this.userResizeBehavior.onUserResizeProgress(SlidingPaneLayout.this, getDragPositionX());
            SlidingPaneLayout.this.invalidate();
        }

        @Override // androidx.slidingpanelayout.widget.SlidingPaneLayout.AbsDraggableDividerHandler
        public void onUserResizeStarted() {
            SlidingPaneLayout.this.userResizeBehavior.onUserResizeStarted(SlidingPaneLayout.this, getDragPositionX());
            SlidingPaneLayout.this.drawableStateChanged();
        }
    }

    /* JADX INFO: compiled from: SlidingPaneLayout.kt */
    public class LayoutParams extends ViewGroup.MarginLayoutParams {
        public boolean dimWhenOffset;
        public boolean slideable;
        public float weight;

        public LayoutParams() {
            super(-1, -1);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context.getClass();
            int[] iArr = R$styleable.SlidingPaneLayout_Layout;
            iArr.getClass();
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, 0, 0);
            this.weight = typedArrayObtainStyledAttributes.getFloat(R$styleable.SlidingPaneLayout_Layout_android_layout_weight, 0.0f);
            typedArrayObtainStyledAttributes.recycle();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            marginLayoutParams.getClass();
        }
    }

    /* JADX INFO: compiled from: SlidingPaneLayout.kt */
    final class OverlappingPaneHandler extends ViewDragHelper.Callback implements TouchHandler {
        private final ViewDragHelper dragHelper;
        private float initialMotionX;
        private float initialMotionY;
        private boolean isUnableToDrag;
        private final List slideableStateListeners = new CopyOnWriteArrayList();
        private final List panelSlideListeners = new CopyOnWriteArrayList();

        public OverlappingPaneHandler() {
            ViewDragHelper viewDragHelperCreate = ViewDragHelper.create(SlidingPaneLayout.this, 0.5f, this);
            viewDragHelperCreate.setMinVelocity(400 * SlidingPaneLayout.this.getContext().getResources().getDisplayMetrics().density);
            this.dragHelper = viewDragHelperCreate;
        }

        public final void abort() {
            this.dragHelper.abort();
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public int clampViewPositionHorizontal(View view, int i, int i2) {
            view.getClass();
            View view2 = SlidingPaneLayout.this.slideableView;
            if (view2 == null) {
                throw new IllegalStateException("Required value was null.");
            }
            ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
            if (!(layoutParams instanceof LayoutParams)) {
                SlidingPaneLayoutKt.layoutParamsError(view2, layoutParams);
                throw new KotlinNothingValueException();
            }
            LayoutParams layoutParams2 = (LayoutParams) layoutParams;
            if (SlidingPaneLayout.this.isLayoutRtl()) {
                int width = SlidingPaneLayout.this.getWidth() - ((SlidingPaneLayout.this.getPaddingRight() + ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin) + view2.getWidth());
                return RangesKt.coerceIn(i, width - SlidingPaneLayout.this.slideRange, width);
            }
            int paddingLeft = SlidingPaneLayout.this.getPaddingLeft() + ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin;
            return RangesKt.coerceIn(i, paddingLeft, SlidingPaneLayout.this.slideRange + paddingLeft);
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public int clampViewPositionVertical(View view, int i, int i2) {
            view.getClass();
            return view.getTop();
        }

        public final void disableEdgeTracking() {
            this.dragHelper.setEdgeTrackingEnabled(0);
        }

        public final void dispatchOnPanelClosed(View view) {
            view.getClass();
            Iterator it = this.panelSlideListeners.iterator();
            if (it.hasNext()) {
                ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
                throw null;
            }
            SlidingPaneLayout.this.sendAccessibilityEvent(32);
        }

        public final void dispatchOnPanelOpened(View view) {
            view.getClass();
            Iterator it = this.panelSlideListeners.iterator();
            if (it.hasNext()) {
                ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
                throw null;
            }
            SlidingPaneLayout.this.sendAccessibilityEvent(32);
        }

        public final void dispatchOnPanelSlide(View view, float f) {
            view.getClass();
            Iterator it = this.panelSlideListeners.iterator();
            if (it.hasNext()) {
                ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
                throw null;
            }
        }

        public final void dispatchSlideableState(boolean z) {
            Iterator it = this.slideableStateListeners.iterator();
            if (it.hasNext()) {
                ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
                throw null;
            }
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public int getViewHorizontalDragRange(View view) {
            view.getClass();
            return SlidingPaneLayout.this.slideRange;
        }

        public final boolean isDraggable() {
            if (this.isUnableToDrag || SlidingPaneLayout.this.getLockMode() == 3) {
                return false;
            }
            if (SlidingPaneLayout.this.isOpen() && SlidingPaneLayout.this.getLockMode() == 1) {
                return false;
            }
            return SlidingPaneLayout.this.isOpen() || SlidingPaneLayout.this.getLockMode() != 2;
        }

        public final boolean isIdle() {
            return this.dragHelper.getViewDragState() == 0;
        }

        public final void onComputeScroll() {
            if (this.dragHelper.continueSettling(true)) {
                if (SlidingPaneLayout.this.isSlideable()) {
                    SlidingPaneLayout.this.postInvalidateOnAnimation();
                } else {
                    this.dragHelper.abort();
                }
            }
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onEdgeDragStarted(int i, int i2) {
            if (isDraggable()) {
                ViewDragHelper viewDragHelper = this.dragHelper;
                View view = SlidingPaneLayout.this.slideableView;
                view.getClass();
                viewDragHelper.captureChildView(view, i2);
            }
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onEdgeTouched(int i, int i2) {
            if (isDraggable()) {
                ViewDragHelper viewDragHelper = this.dragHelper;
                View view = SlidingPaneLayout.this.slideableView;
                view.getClass();
                viewDragHelper.captureChildView(view, i2);
            }
        }

        @Override // androidx.slidingpanelayout.widget.SlidingPaneLayout.TouchHandler
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            boolean z;
            View childAt;
            motionEvent.getClass();
            int actionMasked = motionEvent.getActionMasked();
            if (!SlidingPaneLayout.this.isSlideable() && actionMasked == 0 && SlidingPaneLayout.this.getChildCount() > 1 && (childAt = SlidingPaneLayout.this.getChildAt(1)) != null) {
                SlidingPaneLayout.this.preservedOpenState = this.dragHelper.isViewUnder(childAt, (int) motionEvent.getX(), (int) motionEvent.getY());
            }
            if (!SlidingPaneLayout.this.isSlideable() || (this.isUnableToDrag && actionMasked != 0)) {
                this.dragHelper.cancel();
                return SlidingPaneLayout.super.onInterceptTouchEvent(motionEvent);
            }
            if (actionMasked == 1 || actionMasked == 3) {
                this.dragHelper.cancel();
                return false;
            }
            if (actionMasked == 0) {
                this.isUnableToDrag = false;
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                this.initialMotionX = x;
                this.initialMotionY = y;
                if (this.dragHelper.isViewUnder(SlidingPaneLayout.this.slideableView, (int) x, (int) y)) {
                    SlidingPaneLayout slidingPaneLayout = SlidingPaneLayout.this;
                    if (slidingPaneLayout.isDimmed(slidingPaneLayout.slideableView)) {
                        z = true;
                    }
                }
                return !this.dragHelper.shouldInterceptTouchEvent(motionEvent) || z;
            }
            if (actionMasked == 2) {
                float x2 = motionEvent.getX();
                float y2 = motionEvent.getY();
                float fAbs = Math.abs(x2 - this.initialMotionX);
                float fAbs2 = Math.abs(y2 - this.initialMotionY);
                if (fAbs > this.dragHelper.getTouchSlop() && fAbs2 > fAbs) {
                    this.dragHelper.cancel();
                    this.isUnableToDrag = true;
                    return false;
                }
            }
            z = false;
            if (this.dragHelper.shouldInterceptTouchEvent(motionEvent)) {
            }
        }

        @Override // androidx.slidingpanelayout.widget.SlidingPaneLayout.TouchHandler
        public boolean onTouchEvent(MotionEvent motionEvent) {
            motionEvent.getClass();
            if (!SlidingPaneLayout.this.isSlideable()) {
                return SlidingPaneLayout.super.onTouchEvent(motionEvent);
            }
            this.dragHelper.processTouchEvent(motionEvent);
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                this.initialMotionX = x;
                this.initialMotionY = y;
                return true;
            }
            if (actionMasked == 1) {
                SlidingPaneLayout slidingPaneLayout = SlidingPaneLayout.this;
                if (slidingPaneLayout.isDimmed(slidingPaneLayout.slideableView)) {
                    float x2 = motionEvent.getX();
                    float y2 = motionEvent.getY();
                    float f = x2 - this.initialMotionX;
                    float f2 = y2 - this.initialMotionY;
                    int touchSlop = this.dragHelper.getTouchSlop();
                    if ((f * f) + (f2 * f2) < touchSlop * touchSlop && this.dragHelper.isViewUnder(SlidingPaneLayout.this.slideableView, (int) x2, (int) y2)) {
                        SlidingPaneLayout.this.closePane(0);
                    }
                }
            }
            return true;
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onViewCaptured(View view, int i) {
            view.getClass();
            SlidingPaneLayout.this.setAllChildrenVisible();
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onViewDragStateChanged(int i) {
            boolean z;
            if (this.dragHelper.getViewDragState() == 0) {
                SlidingPaneLayout slidingPaneLayout = SlidingPaneLayout.this;
                if (slidingPaneLayout.currentSlideOffset == 1.0f) {
                    SlidingPaneLayout slidingPaneLayout2 = SlidingPaneLayout.this;
                    slidingPaneLayout2.updateObscuredViewsVisibility(slidingPaneLayout2.slideableView);
                    View view = SlidingPaneLayout.this.slideableView;
                    view.getClass();
                    dispatchOnPanelClosed(view);
                    z = false;
                } else {
                    View view2 = SlidingPaneLayout.this.slideableView;
                    view2.getClass();
                    dispatchOnPanelOpened(view2);
                    z = true;
                }
                slidingPaneLayout.preservedOpenState = z;
            }
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
            view.getClass();
            SlidingPaneLayout.this.onPanelDragged(i);
            SlidingPaneLayout.this.invalidate();
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onViewReleased(View view, float f, float f2) {
            int paddingLeft;
            view.getClass();
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (!(layoutParams instanceof LayoutParams)) {
                SlidingPaneLayoutKt.layoutParamsError(view, layoutParams);
                throw new KotlinNothingValueException();
            }
            LayoutParams layoutParams2 = (LayoutParams) layoutParams;
            if (SlidingPaneLayout.this.isLayoutRtl()) {
                int paddingRight = SlidingPaneLayout.this.getPaddingRight() + ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin;
                if (f < 0.0f || (f == 0.0f && SlidingPaneLayout.this.currentSlideOffset > 0.5f)) {
                    paddingRight += SlidingPaneLayout.this.slideRange;
                }
                View view2 = SlidingPaneLayout.this.slideableView;
                view2.getClass();
                paddingLeft = (SlidingPaneLayout.this.getWidth() - paddingRight) - view2.getWidth();
            } else {
                paddingLeft = ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin + SlidingPaneLayout.this.getPaddingLeft();
                if (f > 0.0f || (f == 0.0f && SlidingPaneLayout.this.currentSlideOffset > 0.5f)) {
                    paddingLeft += SlidingPaneLayout.this.slideRange;
                }
            }
            this.dragHelper.settleCapturedViewAt(paddingLeft, view.getTop());
            SlidingPaneLayout.this.invalidate();
        }

        public final void setEdgeTrackingEnabled(int i, int i2) {
            this.dragHelper.setEdgeTrackingEnabled(i);
            ViewDragHelper viewDragHelper = this.dragHelper;
            viewDragHelper.setEdgeSize(RangesKt.coerceAtLeast(i2, viewDragHelper.getDefaultEdgeSize()));
        }

        public final boolean smoothSlideViewTo(View view, int i, int i2) {
            view.getClass();
            return this.dragHelper.smoothSlideViewTo(view, i, i2);
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public boolean tryCaptureView(View view, int i) {
            view.getClass();
            if (!isDraggable()) {
                return false;
            }
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof LayoutParams) {
                return ((LayoutParams) layoutParams).slideable;
            }
            SlidingPaneLayoutKt.layoutParamsError(view, layoutParams);
            throw new KotlinNothingValueException();
        }
    }

    /* JADX INFO: compiled from: SlidingPaneLayout.kt */
    public final class SavedState extends AbsSavedState {
        private boolean isOpen;
        private int lockMode;
        private int splitDividerPosition;
        public static final Companion Companion = new Companion(null);
        public static final Parcelable.Creator CREATOR = new Parcelable.ClassLoaderCreator() { // from class: androidx.slidingpanelayout.widget.SlidingPaneLayout$SavedState$Companion$CREATOR$1
            @Override // android.os.Parcelable.Creator
            public SlidingPaneLayout.SavedState createFromParcel(Parcel parcel) {
                parcel.getClass();
                return new SlidingPaneLayout.SavedState(parcel, null);
            }

            @Override // android.os.Parcelable.ClassLoaderCreator
            public SlidingPaneLayout.SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                parcel.getClass();
                classLoader.getClass();
                return new SlidingPaneLayout.SavedState(parcel, null);
            }

            @Override // android.os.Parcelable.Creator
            public SlidingPaneLayout.SavedState[] newArray(int i) {
                return new SlidingPaneLayout.SavedState[i];
            }
        };

        /* JADX INFO: compiled from: SlidingPaneLayout.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            parcel.getClass();
            this.splitDividerPosition = -1;
            this.isOpen = parcel.readInt() != 0;
            this.lockMode = parcel.readInt();
            this.splitDividerPosition = parcel.readInt();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SavedState(Parcelable parcelable) {
            super(parcelable);
            parcelable.getClass();
            this.splitDividerPosition = -1;
        }

        public final int getLockMode() {
            return this.lockMode;
        }

        public final int getSplitDividerPosition() {
            return this.splitDividerPosition;
        }

        public final boolean isOpen() {
            return this.isOpen;
        }

        public final void setLockMode(int i) {
            this.lockMode = i;
        }

        public final void setOpen(boolean z) {
            this.isOpen = z;
        }

        public final void setSplitDividerPosition(int i) {
            this.splitDividerPosition = i;
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.getClass();
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.isOpen ? 1 : 0);
            parcel.writeInt(this.lockMode);
            parcel.writeInt(this.splitDividerPosition);
        }
    }

    /* JADX INFO: compiled from: SlidingPaneLayout.kt */
    final class TouchBlocker extends ViewGroup {
        final /* synthetic */ SlidingPaneLayout this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public TouchBlocker(SlidingPaneLayout slidingPaneLayout, View view) {
            super(view.getContext());
            view.getClass();
            this.this$0 = slidingPaneLayout;
            addView(view);
        }

        @Override // android.view.ViewGroup
        protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
            return this.this$0.checkLayoutParams(layoutParams);
        }

        @Override // android.view.ViewGroup
        protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
            return this.this$0.generateDefaultLayoutParams();
        }

        @Override // android.view.ViewGroup
        protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
            return this.this$0.generateLayoutParams(layoutParams);
        }

        @Override // android.view.View
        public ViewGroup.LayoutParams getLayoutParams() {
            ViewGroup.LayoutParams layoutParams = getChildAt(0).getLayoutParams();
            layoutParams.getClass();
            return layoutParams;
        }

        @Override // android.view.View
        public boolean onGenericMotionEvent(MotionEvent motionEvent) {
            motionEvent.getClass();
            return this.this$0.isSlideable();
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            getChildAt(0).layout(0, 0, i3 - i, i4 - i2);
        }

        @Override // android.view.View
        protected void onMeasure(int i, int i2) {
            View childAt = getChildAt(0);
            childAt.measure(i, i2);
            setMeasuredDimension(childAt.getMeasuredWidth(), childAt.getMeasuredHeight());
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            motionEvent.getClass();
            return this.this$0.isSlideable();
        }

        @Override // android.view.View
        public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
            layoutParams.getClass();
            getChildAt(0).setLayoutParams(layoutParams);
        }
    }

    /* JADX INFO: compiled from: SlidingPaneLayout.kt */
    public interface TouchHandler {
        boolean onInterceptTouchEvent(MotionEvent motionEvent);

        boolean onTouchEvent(MotionEvent motionEvent);
    }

    /* JADX INFO: compiled from: SlidingPaneLayout.kt */
    public interface UserResizeBehavior {
        void onUserResizeCancelled(SlidingPaneLayout slidingPaneLayout, int i);

        void onUserResizeComplete(SlidingPaneLayout slidingPaneLayout, int i);

        void onUserResizeProgress(SlidingPaneLayout slidingPaneLayout, int i);

        void onUserResizeStarted(SlidingPaneLayout slidingPaneLayout, int i);
    }

    /* JADX INFO: renamed from: androidx.slidingpanelayout.widget.SlidingPaneLayout$onWindowVisibilityChanged$1, reason: invalid class name */
    /* JADX INFO: compiled from: SlidingPaneLayout.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Job $toJoin;
        int label;
        final /* synthetic */ SlidingPaneLayout this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Job job, SlidingPaneLayout slidingPaneLayout, Continuation continuation) {
            super(2, continuation);
            this.$toJoin = job;
            this.this$0 = slidingPaneLayout;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$toJoin, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:16:0x0036, code lost:
        
            if (r5.whileAttachedToVisibleWindow(r4) == r0) goto L17;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r5) {
            /*
                r4 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r4.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L1e
                if (r1 == r3) goto L1a
                if (r1 != r2) goto L12
                kotlin.ResultKt.throwOnFailure(r5)
                goto L39
            L12:
                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                r4.<init>(r5)
                throw r4
            L1a:
                kotlin.ResultKt.throwOnFailure(r5)
                goto L2e
            L1e:
                kotlin.ResultKt.throwOnFailure(r5)
                kotlinx.coroutines.Job r5 = r4.$toJoin
                if (r5 == 0) goto L2e
                r4.label = r3
                java.lang.Object r5 = r5.join(r4)
                if (r5 != r0) goto L2e
                goto L38
            L2e:
                androidx.slidingpanelayout.widget.SlidingPaneLayout r5 = r4.this$0
                r4.label = r2
                java.lang.Object r4 = androidx.slidingpanelayout.widget.SlidingPaneLayout.access$whileAttachedToVisibleWindow(r5, r4)
                if (r4 != r0) goto L39
            L38:
                return r0
            L39:
                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.slidingpanelayout.widget.SlidingPaneLayout.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SlidingPaneLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        context.getClass();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SlidingPaneLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        context.getClass();
        this.currentSlideOffset = 1.0f;
        this.touchTargetMin = MathKt.roundToInt(context.getResources().getDisplayMetrics().density * 48);
        this.overlappingPaneHandler = new OverlappingPaneHandler();
        this.draggableDividerHandler = new DraggableDividerHandler();
        this.cancelEvent = MotionEvent.obtain(0L, 0L, 3, 0.0f, 0.0f, 0);
        this.awaitingFirstLayout = true;
        this.tmpRect = new Rect();
        this.tmpRect2 = new Rect();
        this.foldBoundsCalculator = new FoldBoundsCalculator();
        this.isOverlappingEnabled = true;
        this.windowInfoTracker = WindowInfoTracker.Companion.getOrCreate(context);
        this.computedDividerExclusionRect = new Rect();
        Rect rect = new Rect();
        this.dividerGestureExclusionRect = rect;
        this.gestureExclusionRectsList = CollectionsKt.listOf(rect);
        this.splitDividerPosition = -1;
        this.isChildClippingToResizeDividerEnabled = true;
        UserResizeBehavior userResizeBehavior = USER_RESIZE_RELAYOUT_WHEN_COMPLETE;
        this.userResizeBehavior = userResizeBehavior;
        setWillNotDraw(false);
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegate());
        setImportantForAccessibility(1);
        int[] iArr = R$styleable.SlidingPaneLayout;
        iArr.getClass();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, 0, R$style.Widget_SlidingPaneLayout);
        setOverlappingEnabled(typedArrayObtainStyledAttributes.getBoolean(R$styleable.SlidingPaneLayout_isOverlappingEnabled, true));
        setUserResizingEnabled(typedArrayObtainStyledAttributes.getBoolean(R$styleable.SlidingPaneLayout_isUserResizingEnabled, false));
        this.userResizingDividerDrawable = typedArrayObtainStyledAttributes.getDrawable(R$styleable.SlidingPaneLayout_userResizingDividerDrawable);
        setChildClippingToResizeDividerEnabled(typedArrayObtainStyledAttributes.getBoolean(R$styleable.SlidingPaneLayout_isChildClippingToResizeDividerEnabled, true));
        int i2 = typedArrayObtainStyledAttributes.getInt(R$styleable.SlidingPaneLayout_userResizeBehavior, 0);
        if (i2 != 0) {
            if (i2 != 1) {
                throw new IllegalStateException((i2 + " is not a valid userResizeBehavior value").toString());
            }
            userResizeBehavior = USER_RESIZE_RELAYOUT_WHEN_MOVED;
        }
        this.userResizeBehavior = userResizeBehavior;
        typedArrayObtainStyledAttributes.recycle();
    }

    public /* synthetic */ SlidingPaneLayout(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean closePane(int i) {
        if (!isSlideable()) {
            this.preservedOpenState = false;
        }
        if (!this.awaitingFirstLayout && !smoothSlideTo(1.0f, i)) {
            return false;
        }
        this.preservedOpenState = false;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Rect computeDividerTargetRect(Rect rect, int i) {
        Drawable drawable = this.userResizingDividerDrawable;
        if (drawable == null) {
            rect.setEmpty();
            return rect;
        }
        int i2 = this.touchTargetMin;
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        int iMax = Math.max(intrinsicWidth, i2);
        int iMax2 = Math.max(intrinsicHeight, i2);
        int i3 = i - (iMax / 2);
        int height = ((((getHeight() - getPaddingTop()) - getPaddingBottom()) / 2) + getPaddingTop()) - (iMax2 / 2);
        rect.set(i3, height, iMax + i3, iMax2 + height);
        return rect;
    }

    private final int[] createUserResizingDividerDrawableState(int[] iArr) {
        if (!ArraysKt.contains(iArr, R.attr.state_pressed) && !isDividerDragging()) {
            return iArr;
        }
        if (isDividerDragging()) {
            int[] iArrCopyOf = Arrays.copyOf(iArr, iArr.length + 1);
            iArrCopyOf.getClass();
            iArrCopyOf[ArraysKt.getLastIndex(iArrCopyOf)] = 16842919;
            return iArrCopyOf;
        }
        int length = iArr.length - 1;
        int[] iArr2 = new int[length];
        boolean z = false;
        for (int i = 0; i < length; i++) {
            if (iArr[i] == 16842919) {
                z = true;
            }
            iArr2[i] = iArr[z ? i + 1 : i];
        }
        return iArr2;
    }

    private final void dispatchOnPanelSlide(View view) {
        this.overlappingPaneHandler.dispatchOnPanelSlide(view, this.currentSlideOffset);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getMinimumChildWidth(View view) {
        return view instanceof TouchBlocker ? ((TouchBlocker) view).getChildAt(0).getMinimumWidth() : view.getMinimumWidth();
    }

    private final Insets getSystemGestureInsets() {
        WindowInsetsCompat rootWindowInsets;
        if (!SlidingPaneLayoutKt.edgeSizeUsingSystemGestureInsets || (rootWindowInsets = ViewCompat.getRootWindowInsets(this)) == null) {
            return null;
        }
        return rootWindowInsets.getSystemGestureInsets();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isDimmed(View view) {
        if (view == null) {
            return false;
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof LayoutParams) {
            return isSlideable() && ((LayoutParams) layoutParams).dimWhenOffset && this.currentSlideOffset > 0.0f;
        }
        SlidingPaneLayoutKt.layoutParamsError(view, layoutParams);
        throw new KotlinNothingValueException();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isLayoutRtl() {
        return getLayoutDirection() == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onPanelDragged(int i) {
        View view = this.slideableView;
        if (view == null) {
            this.currentSlideOffset = 0.0f;
            return;
        }
        boolean zIsLayoutRtl = isLayoutRtl();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof LayoutParams)) {
            SlidingPaneLayoutKt.layoutParamsError(view, layoutParams);
            throw new KotlinNothingValueException();
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        int width = view.getWidth();
        if (zIsLayoutRtl) {
            i = (getWidth() - i) - width;
        }
        this.currentSlideOffset = (i - ((zIsLayoutRtl ? getPaddingRight() : getPaddingLeft()) + (zIsLayoutRtl ? ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin : ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin))) / this.slideRange;
        if (getParallaxDistance() != 0) {
            parallaxOtherViews(this.currentSlideOffset);
        }
        dispatchOnPanelSlide(view);
    }

    private final boolean openPane(int i) {
        if (!isSlideable()) {
            this.preservedOpenState = true;
        }
        if (!this.awaitingFirstLayout && !smoothSlideTo(0.0f, i)) {
            return false;
        }
        this.preservedOpenState = true;
        return true;
    }

    private final void parallaxOtherViews(float f) {
        boolean zIsLayoutRtl = isLayoutRtl();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt != this.slideableView) {
                float f2 = 1;
                int parallaxDistance = (int) ((f2 - this.currentParallaxOffset) * getParallaxDistance());
                this.currentParallaxOffset = f;
                int parallaxDistance2 = parallaxDistance - ((int) ((f2 - f) * getParallaxDistance()));
                if (zIsLayoutRtl) {
                    parallaxDistance2 = -parallaxDistance2;
                }
                childAt.offsetLeftAndRight(parallaxDistance2);
            }
        }
    }

    private final boolean remeasureForFoldingFeature(FoldingFeature foldingFeature) {
        Rect rect;
        Rect rect2;
        Rect rect3 = this.tmpRect;
        Rect rect4 = this.tmpRect2;
        boolean zSplitViewPositions = this.foldBoundsCalculator.splitViewPositions(foldingFeature, this, rect3, rect4);
        if (zSplitViewPositions) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                childAt.getClass();
                if (childAt.getVisibility() != 8) {
                    if (i == 0) {
                        rect2 = rect3;
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("too many children to split");
                        }
                        rect2 = rect4;
                    }
                    ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                    if (!(layoutParams instanceof LayoutParams)) {
                        SlidingPaneLayoutKt.layoutParamsError(childAt, layoutParams);
                        throw new KotlinNothingValueException();
                    }
                    LayoutParams layoutParams2 = (LayoutParams) layoutParams;
                    if ((((ViewGroup.MarginLayoutParams) layoutParams2).width != -1 && layoutParams2.weight <= 0.0f) || RangesKt.coerceAtLeast(getMinimumChildWidth(childAt), ((ViewGroup.MarginLayoutParams) layoutParams2).width) + ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin > rect2.width()) {
                        return false;
                    }
                }
            }
            int childCount2 = getChildCount();
            for (int i2 = 0; i2 < childCount2; i2++) {
                View childAt2 = getChildAt(i2);
                childAt2.getClass();
                if (childAt2.getVisibility() != 8) {
                    if (i2 == 0) {
                        rect = rect3;
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("too many children to split");
                        }
                        rect = rect4;
                    }
                    ViewGroup.LayoutParams layoutParams3 = childAt2.getLayoutParams();
                    if (!(layoutParams3 instanceof LayoutParams)) {
                        SlidingPaneLayoutKt.layoutParamsError(childAt2, layoutParams3);
                        throw new KotlinNothingValueException();
                    }
                    LayoutParams layoutParams4 = (LayoutParams) layoutParams3;
                    childAt2.measure(View.MeasureSpec.makeMeasureSpec(RangesKt.coerceAtLeast(rect.width() - (((ViewGroup.MarginLayoutParams) layoutParams4).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams4).rightMargin), 0), 1073741824), View.MeasureSpec.makeMeasureSpec(childAt2.getMeasuredHeight(), 1073741824));
                }
            }
        }
        return zSplitViewPositions;
    }

    private final TouchHandler selectActiveTouchHandler() {
        setActiveTouchHandler(isSlideable() ? this.overlappingPaneHandler : isUserResizable() ? this.draggableDividerHandler : null);
        return this.activeTouchHandler;
    }

    private final void setActiveTouchHandler(TouchHandler touchHandler) {
        if (Intrinsics.areEqual(this.activeTouchHandler, touchHandler)) {
            return;
        }
        TouchHandler touchHandler2 = this.activeTouchHandler;
        if (touchHandler2 != null) {
            MotionEvent motionEvent = this.cancelEvent;
            motionEvent.getClass();
            touchHandler2.onTouchEvent(motionEvent);
        }
        this.activeTouchHandler = touchHandler;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setAllChildrenVisible() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            childAt.getClass();
            if (childAt.getVisibility() == 4) {
                childAt.setVisibility(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setFoldingFeature(FoldingFeature foldingFeature) {
        if (Intrinsics.areEqual(foldingFeature, this.foldingFeature)) {
            return;
        }
        this.foldingFeature = foldingFeature;
        requestLayout();
    }

    private final boolean smoothSlideTo(float f, int i) {
        View view;
        if (!isSlideable() || (view = this.slideableView) == null) {
            return false;
        }
        boolean zIsLayoutRtl = isLayoutRtl();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof LayoutParams)) {
            SlidingPaneLayoutKt.layoutParamsError(view, layoutParams);
            throw new KotlinNothingValueException();
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        if (!this.overlappingPaneHandler.smoothSlideViewTo(view, zIsLayoutRtl ? (int) (getWidth() - (((getPaddingRight() + ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin) + (f * this.slideRange)) + view.getWidth())) : (int) (getPaddingLeft() + ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin + (f * this.slideRange)), view.getTop())) {
            return false;
        }
        setAllChildrenVisible();
        postInvalidateOnAnimation();
        return true;
    }

    private final void updateDividerDrawableBounds(int i) {
        Drawable drawable;
        if (getWidth() <= 0 || getHeight() <= 0 || (drawable = this.userResizingDividerDrawable) == null) {
            return;
        }
        int height = (((getHeight() - getPaddingTop()) - getPaddingBottom()) / 2) + getPaddingTop();
        int intrinsicWidth = i - (drawable.getIntrinsicWidth() / 2);
        int intrinsicHeight = height - (drawable.getIntrinsicHeight() / 2);
        drawable.setBounds(intrinsicWidth, intrinsicHeight, drawable.getIntrinsicWidth() + intrinsicWidth, drawable.getIntrinsicHeight() + intrinsicHeight);
    }

    private final void updateGestureExclusion(int i) {
        if (i < 0) {
            this.computedDividerExclusionRect.setEmpty();
        } else {
            computeDividerTargetRect(this.computedDividerExclusionRect, i);
        }
        if (Intrinsics.areEqual(this.computedDividerExclusionRect, this.dividerGestureExclusionRect)) {
            return;
        }
        if (this.computedDividerExclusionRect.isEmpty()) {
            ViewCompat.setSystemGestureExclusionRects(this, CollectionsKt.emptyList());
        } else {
            this.dividerGestureExclusionRect.set(this.computedDividerExclusionRect);
            ViewCompat.setSystemGestureExclusionRects(this, this.gestureExclusionRectsList);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateObscuredViewsVisibility(View view) {
        int left;
        int right;
        int top;
        int bottom;
        boolean z;
        View view2 = view;
        boolean zIsLayoutRtl = isLayoutRtl();
        int width = zIsLayoutRtl ? getWidth() - getPaddingRight() : getPaddingLeft();
        int paddingLeft = zIsLayoutRtl ? getPaddingLeft() : getWidth() - getPaddingRight();
        int paddingTop = getPaddingTop();
        int height = getHeight() - getPaddingBottom();
        if (view2 == null || !view2.isOpaque()) {
            left = 0;
            right = 0;
            top = 0;
            bottom = 0;
        } else {
            left = view2.getLeft();
            right = view2.getRight();
            top = view2.getTop();
            bottom = view2.getBottom();
        }
        int childCount = getChildCount();
        int i = 0;
        while (i < childCount) {
            View childAt = getChildAt(i);
            childAt.getClass();
            if (childAt == view2) {
                return;
            }
            if (childAt.getVisibility() != 8) {
                z = zIsLayoutRtl;
                childAt.setVisibility((RangesKt.coerceAtLeast(zIsLayoutRtl ? paddingLeft : width, childAt.getLeft()) < left || RangesKt.coerceAtLeast(paddingTop, childAt.getTop()) < top || RangesKt.coerceAtMost(zIsLayoutRtl ? width : paddingLeft, childAt.getRight()) > right || RangesKt.coerceAtMost(height, childAt.getBottom()) > bottom) ? 0 : 4);
            } else {
                z = zIsLayoutRtl;
            }
            i++;
            view2 = view;
            zIsLayoutRtl = z;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object whileAttachedToVisibleWindow(Continuation continuation) {
        WindowInfoTracker windowInfoTracker = this.windowInfoTracker;
        Context context = getContext();
        context.getClass();
        final Flow flowWindowLayoutInfo = windowInfoTracker.windowLayoutInfo(context);
        Object objCollect = FlowKt.distinctUntilChanged(new Flow() { // from class: androidx.slidingpanelayout.widget.SlidingPaneLayout$whileAttachedToVisibleWindow$$inlined$mapNotNull$1

            /* JADX INFO: renamed from: androidx.slidingpanelayout.widget.SlidingPaneLayout$whileAttachedToVisibleWindow$$inlined$mapNotNull$1$2, reason: invalid class name */
            /* JADX INFO: compiled from: Emitters.kt */
            public final class AnonymousClass2 implements FlowCollector {
                final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* JADX INFO: renamed from: androidx.slidingpanelayout.widget.SlidingPaneLayout$whileAttachedToVisibleWindow$$inlined$mapNotNull$1$2$1, reason: invalid class name */
                /* JADX INFO: compiled from: Emitters.kt */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof androidx.slidingpanelayout.widget.SlidingPaneLayout$whileAttachedToVisibleWindow$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        androidx.slidingpanelayout.widget.SlidingPaneLayout$whileAttachedToVisibleWindow$$inlined$mapNotNull$1$2$1 r0 = (androidx.slidingpanelayout.widget.SlidingPaneLayout$whileAttachedToVisibleWindow$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        androidx.slidingpanelayout.widget.SlidingPaneLayout$whileAttachedToVisibleWindow$$inlined$mapNotNull$1$2$1 r0 = new androidx.slidingpanelayout.widget.SlidingPaneLayout$whileAttachedToVisibleWindow$$inlined$mapNotNull$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L31
                        if (r2 != r3) goto L29
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L66
                    L29:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L31:
                        kotlin.ResultKt.throwOnFailure(r7)
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        androidx.window.layout.WindowLayoutInfo r6 = (androidx.window.layout.WindowLayoutInfo) r6
                        java.util.List r6 = r6.getDisplayFeatures()
                        java.util.Iterator r6 = r6.iterator()
                    L40:
                        boolean r7 = r6.hasNext()
                        r2 = 0
                        if (r7 == 0) goto L53
                        java.lang.Object r7 = r6.next()
                        r4 = r7
                        androidx.window.layout.DisplayFeature r4 = (androidx.window.layout.DisplayFeature) r4
                        boolean r4 = r4 instanceof androidx.window.layout.FoldingFeature
                        if (r4 == 0) goto L40
                        goto L54
                    L53:
                        r7 = r2
                    L54:
                        boolean r6 = r7 instanceof androidx.window.layout.FoldingFeature
                        if (r6 == 0) goto L5b
                        r2 = r7
                        androidx.window.layout.FoldingFeature r2 = (androidx.window.layout.FoldingFeature) r2
                    L5b:
                        if (r2 == 0) goto L66
                        r0.label = r3
                        java.lang.Object r5 = r5.emit(r2, r0)
                        if (r5 != r1) goto L66
                        return r1
                    L66:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.slidingpanelayout.widget.SlidingPaneLayout$whileAttachedToVisibleWindow$$inlined$mapNotNull$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation2) {
                Object objCollect2 = flowWindowLayoutInfo.collect(new AnonymousClass2(flowCollector), continuation2);
                return objCollect2 == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect2 : Unit.INSTANCE;
            }
        }).collect(new FlowCollector() { // from class: androidx.slidingpanelayout.widget.SlidingPaneLayout.whileAttachedToVisibleWindow.3
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(FoldingFeature foldingFeature, Continuation continuation2) {
                SlidingPaneLayout.this.setFoldingFeature(foldingFeature);
                return Unit.INSTANCE;
            }
        }, continuation);
        return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        view.getClass();
        if (getChildCount() == 1) {
            super.addView(new TouchBlocker(this, view), i, layoutParams);
        } else {
            super.addView(view, i, layoutParams);
        }
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    public boolean closePane() {
        return closePane(0);
    }

    @Override // android.view.View
    public void computeScroll() {
        this.overlappingPaneHandler.onComputeScroll();
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        int i;
        int right;
        canvas.getClass();
        super.draw(canvas);
        Drawable drawable = isLayoutRtl() ? this.shadowDrawableRight : this.shadowDrawableLeft;
        View childAt = getChildCount() > 1 ? getChildAt(1) : null;
        if (childAt != null && drawable != null) {
            int top = childAt.getTop();
            int bottom = childAt.getBottom();
            int intrinsicWidth = drawable.getIntrinsicWidth();
            if (isLayoutRtl()) {
                right = childAt.getRight();
                i = intrinsicWidth + right;
            } else {
                int left = childAt.getLeft();
                int i2 = left - intrinsicWidth;
                i = left;
                right = i2;
            }
            drawable.setBounds(right, top, i, bottom);
            drawable.draw(canvas);
        }
        Drawable drawable2 = this.userResizingDividerDrawable;
        if (drawable2 != null) {
            Drawable drawable3 = isUserResizable() ? drawable2 : null;
            if (drawable3 != null) {
                updateDividerDrawableBounds(getVisualDividerPosition());
                drawable3.draw(canvas);
            }
        }
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View view, long j) {
        int visualDividerPosition;
        canvas.getClass();
        view.getClass();
        if (isSlideable()) {
            Insets systemGestureInsets = getSystemGestureInsets();
            if (isLayoutRtl() ^ isOpen()) {
                this.overlappingPaneHandler.setEdgeTrackingEnabled(1, systemGestureInsets != null ? systemGestureInsets.left : 0);
            } else {
                this.overlappingPaneHandler.setEdgeTrackingEnabled(2, systemGestureInsets != null ? systemGestureInsets.right : 0);
            }
        } else {
            this.overlappingPaneHandler.disableEdgeTracking();
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof LayoutParams)) {
            SlidingPaneLayoutKt.layoutParamsError(view, layoutParams);
            throw new KotlinNothingValueException();
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        int iSave = canvas.save();
        if (isSlideable() && !layoutParams2.slideable && this.slideableView != null) {
            canvas.getClipBounds(this.tmpRect);
            if (isLayoutRtl()) {
                Rect rect = this.tmpRect;
                int i = rect.left;
                View view2 = this.slideableView;
                view2.getClass();
                rect.left = Math.max(i, view2.getRight());
            } else {
                Rect rect2 = this.tmpRect;
                int i2 = rect2.right;
                View view3 = this.slideableView;
                view3.getClass();
                rect2.right = Math.min(i2, view3.getLeft());
            }
            canvas.clipRect(this.tmpRect);
        }
        if (!isSlideable() && this.isChildClippingToResizeDividerEnabled && (visualDividerPosition = getVisualDividerPosition()) >= 0) {
            Rect rect3 = this.tmpRect;
            if ((view == getChildAt(0)) ^ isLayoutRtl()) {
                rect3.left = getPaddingLeft();
                rect3.right = visualDividerPosition;
            } else {
                rect3.left = visualDividerPosition;
                rect3.right = getWidth() - getPaddingRight();
            }
            rect3.top = getPaddingTop();
            rect3.bottom = getHeight() - getPaddingBottom();
            canvas.clipRect(this.tmpRect);
        }
        boolean zDrawChild = super.drawChild(canvas, view, j);
        canvas.restoreToCount(iSave);
        return zDrawChild;
    }

    @Override // android.view.View
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawable drawable = this.userResizingDividerDrawable;
        if (drawable != null) {
            DrawableCompat.setHotspot(drawable, f, f2);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.userResizingDividerDrawable;
        if (drawable == null || !drawable.isStateful()) {
            return;
        }
        int[] drawableState = getDrawableState();
        drawableState.getClass();
        if (drawable.setState(createUserResizingDividerDrawableState(drawableState))) {
            invalidateDrawable(drawable);
        }
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        Context context = getContext();
        context.getClass();
        return new LayoutParams(context, attributeSet);
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new ViewGroup.LayoutParams(layoutParams);
    }

    public final int getLockMode() {
        return this.lockMode;
    }

    public int getParallaxDistance() {
        return this.parallaxDistance;
    }

    public final int getVisualDividerPosition() {
        View childAt;
        View childAt2;
        if (!isUserResizable()) {
            return -1;
        }
        if (isDividerDragging()) {
            return this.draggableDividerHandler.getDragPositionX();
        }
        int i = this.splitDividerPosition;
        if (i >= 0) {
            return i;
        }
        if (isLayoutRtl()) {
            childAt = getChildAt(1);
            childAt.getClass();
            childAt2 = getChildAt(0);
            childAt2.getClass();
        } else {
            childAt = getChildAt(0);
            childAt.getClass();
            childAt2 = getChildAt(1);
            childAt2.getClass();
        }
        int right = childAt.getRight();
        ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
        if (!(layoutParams instanceof LayoutParams)) {
            SlidingPaneLayoutKt.layoutParamsError(childAt, layoutParams);
            throw new KotlinNothingValueException();
        }
        int left = right + ((ViewGroup.MarginLayoutParams) ((LayoutParams) layoutParams)).rightMargin + childAt2.getLeft();
        ViewGroup.LayoutParams layoutParams2 = childAt2.getLayoutParams();
        if (layoutParams2 instanceof LayoutParams) {
            return (left - ((ViewGroup.MarginLayoutParams) ((LayoutParams) layoutParams2)).leftMargin) / 2;
        }
        SlidingPaneLayoutKt.layoutParamsError(childAt2, layoutParams2);
        throw new KotlinNothingValueException();
    }

    public final boolean isDividerDragging() {
        return this.draggableDividerHandler.isDragging();
    }

    public boolean isOpen() {
        return !isSlideable() || this.currentSlideOffset == 0.0f;
    }

    public boolean isSlideable() {
        return this._isSlideable;
    }

    public final boolean isUserResizable() {
        return (isSlideable() || !this.isUserResizingEnabled || this.userResizingDividerDrawable == null) ? false : true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.userResizingDividerDrawable;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.awaitingFirstLayout = true;
        Job job = this.whileAttachedToVisibleWindowJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        Job job = this.whileAttachedToVisibleWindowJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        }
        this.awaitingFirstLayout = true;
        super.onDetachedFromWindow();
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        motionEvent.getClass();
        TouchHandler touchHandlerSelectActiveTouchHandler = selectActiveTouchHandler();
        if (touchHandlerSelectActiveTouchHandler != null) {
            return touchHandlerSelectActiveTouchHandler.onInterceptTouchEvent(motionEvent);
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00f8  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void onLayout(boolean r19, int r20, int r21, int r22, int r23) {
        /*
            Method dump skipped, instruction units count: 326
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.slidingpanelayout.widget.SlidingPaneLayout.onLayout(boolean, int, int, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:126:0x022f  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0262  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x014c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00a1 A[PHI: r16
      0x00a1: PHI (r16v2 float) = (r16v1 float), (r16v3 float) binds: [B:20:0x0091, B:22:0x0097] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x013d  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void onMeasure(int r26, int r27) {
        /*
            Method dump skipped, instruction units count: 689
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.slidingpanelayout.widget.SlidingPaneLayout.onMeasure(int, int):void");
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (savedState.isOpen()) {
            openPane();
        } else {
            closePane();
        }
        this.preservedOpenState = savedState.isOpen();
        this.lockMode = savedState.getLockMode();
        setSplitDividerPosition(savedState.getSplitDividerPosition());
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.setOpen(isSlideable() ? isOpen() : this.preservedOpenState);
        savedState.setLockMode(this.lockMode);
        savedState.setSplitDividerPosition(this.splitDividerPosition);
        return savedState;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3) {
            this.awaitingFirstLayout = true;
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        motionEvent.getClass();
        TouchHandler touchHandlerSelectActiveTouchHandler = selectActiveTouchHandler();
        if (touchHandlerSelectActiveTouchHandler != null) {
            return touchHandlerSelectActiveTouchHandler.onTouchEvent(motionEvent);
        }
        return false;
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        Job job = this.whileAttachedToVisibleWindowJob;
        Job jobLaunch$default = null;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        } else {
            job = null;
        }
        if (i == 0) {
            Handler handlerCreateAsync = HandlerCompat.createAsync(getHandler().getLooper());
            handlerCreateAsync.getClass();
            jobLaunch$default = BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(HandlerDispatcherKt.from$default(handlerCreateAsync, null, 1, null)), null, CoroutineStart.UNDISPATCHED, new AnonymousClass1(job, this, null), 1, null);
        }
        this.whileAttachedToVisibleWindowJob = jobLaunch$default;
    }

    public boolean openPane() {
        return openPane(0);
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void removeView(View view) {
        view.getClass();
        if (!(view.getParent() instanceof TouchBlocker)) {
            super.removeView(view);
            return;
        }
        Object parent = view.getParent();
        parent.getClass();
        super.removeView((View) parent);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestChildFocus(View view, View view2) {
        super.requestChildFocus(view, view2);
        if (isInTouchMode() || isSlideable()) {
            return;
        }
        this.preservedOpenState = view == this.slideableView;
    }

    public final void setChildClippingToResizeDividerEnabled(boolean z) {
        if (z != this.isChildClippingToResizeDividerEnabled) {
            this.isChildClippingToResizeDividerEnabled = z;
            invalidate();
        }
    }

    public final void setOverlappingEnabled(boolean z) {
        if (z != this.isOverlappingEnabled) {
            this.isOverlappingEnabled = z;
            requestLayout();
        }
    }

    public final void setSplitDividerPosition(int i) {
        if (this.splitDividerPosition != i) {
            this.splitDividerPosition = i;
            if (isSlideable()) {
                return;
            }
            requestLayout();
        }
    }

    public final void setUserResizingEnabled(boolean z) {
        if (z != this.isUserResizingEnabled) {
            this.isUserResizingEnabled = z;
            requestLayout();
        }
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        drawable.getClass();
        return super.verifyDrawable(drawable) || drawable == this.userResizingDividerDrawable;
    }
}
