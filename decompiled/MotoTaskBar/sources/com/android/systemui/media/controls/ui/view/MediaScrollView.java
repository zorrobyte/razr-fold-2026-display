package com.android.systemui.media.controls.ui.view;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import com.android.systemui.Gefingerpoken;
import com.android.wm.shell.shared.animation.PhysicsAnimatorProxKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MediaScrollView.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaScrollView extends HorizontalScrollView {
    private float animationTargetX;
    private ViewGroup contentContainer;
    private Gefingerpoken touchListener;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public MediaScrollView(Context context) {
        this(context, null, 0, 6, null);
        context.getClass();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public MediaScrollView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        context.getClass();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        context.getClass();
    }

    public /* synthetic */ MediaScrollView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    private final int transformScrollX(int i) {
        return isLayoutRtl() ? (getContentContainer().getWidth() - getWidth()) - i : i;
    }

    public final void cancelCurrentScroll() {
        long jUptimeMillis = SystemClock.uptimeMillis();
        MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
        motionEventObtain.setSource(4098);
        super.onTouchEvent(motionEventObtain);
        motionEventObtain.recycle();
    }

    public final ViewGroup getContentContainer() {
        ViewGroup viewGroup = this.contentContainer;
        if (viewGroup != null) {
            return viewGroup;
        }
        Intrinsics.throwUninitializedPropertyAccessException("contentContainer");
        return null;
    }

    public final float getContentTranslation() {
        return PhysicsAnimatorProxKt.getPhysicsAnimator(getContentContainer()).isRunning() ? this.animationTargetX : getContentContainer().getTranslationX();
    }

    public final int getRelativeScrollX() {
        return transformScrollX(getScrollX());
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        View childAt = getChildAt(0);
        childAt.getClass();
        this.contentContainer = (ViewGroup) childAt;
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        Gefingerpoken gefingerpoken = this.touchListener;
        return super.onInterceptTouchEvent(motionEvent) || (gefingerpoken != null ? gefingerpoken.onInterceptTouchEvent(motionEvent) : false);
    }

    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (!isLaidOut() && isLayoutRtl()) {
            ((HorizontalScrollView) this).mScrollX = getRelativeScrollX();
        }
        super.onLayout(z, i, i2, i3, i4);
    }

    @Override // android.widget.HorizontalScrollView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Gefingerpoken gefingerpoken = this.touchListener;
        return super.onTouchEvent(motionEvent) || (gefingerpoken != null ? gefingerpoken.onTouchEvent(motionEvent) : false);
    }

    @Override // android.view.View
    protected boolean overScrollBy(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
        if (getContentTranslation() == 0.0f) {
            return super.overScrollBy(i, i2, i3, i4, i5, i6, i7, i8, z);
        }
        return false;
    }

    @Override // android.widget.HorizontalScrollView, android.view.View
    public void scrollTo(int i, int i2) {
        int i3 = ((HorizontalScrollView) this).mScrollX;
        if (i3 == i && ((HorizontalScrollView) this).mScrollY == i2) {
            return;
        }
        int i4 = ((HorizontalScrollView) this).mScrollY;
        ((HorizontalScrollView) this).mScrollX = i;
        ((HorizontalScrollView) this).mScrollY = i2;
        invalidateParentCaches();
        onScrollChanged(((HorizontalScrollView) this).mScrollX, ((HorizontalScrollView) this).mScrollY, i3, i4);
        if (awakenScrollBars()) {
            return;
        }
        postInvalidateOnAnimation();
    }

    public final void setAnimationTargetX(float f) {
        this.animationTargetX = f;
    }

    public final void setRelativeScrollX(int i) {
        setScrollX(transformScrollX(i));
    }

    public final void setTouchListener(Gefingerpoken gefingerpoken) {
        this.touchListener = gefingerpoken;
    }
}
