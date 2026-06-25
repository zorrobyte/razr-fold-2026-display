package com.motorola.taskbar.recent;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: RecentsView.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class RecentsView extends FrameLayout {
    private final Lazy mDownPoint$delegate;
    private TaskViewHostCallback mHostCallback;
    private final Lazy mSquaredTouchSlop$delegate;
    private boolean mTouchDownToQuitRecents;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public RecentsView(Context context) {
        this(context, null, 0, 6, null);
        context.getClass();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public RecentsView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        context.getClass();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RecentsView(final Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        context.getClass();
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        this.mDownPoint$delegate = LazyKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.motorola.taskbar.recent.RecentsView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return RecentsView.mDownPoint_delegate$lambda$0();
            }
        });
        this.mSquaredTouchSlop$delegate = LazyKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.motorola.taskbar.recent.RecentsView$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return Float.valueOf(RecentsView.mSquaredTouchSlop_delegate$lambda$1(context));
            }
        });
    }

    public /* synthetic */ RecentsView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    private final PointF getMDownPoint() {
        return (PointF) this.mDownPoint$delegate.getValue();
    }

    private final float getMSquaredTouchSlop() {
        return ((Number) this.mSquaredTouchSlop$delegate.getValue()).floatValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final PointF mDownPoint_delegate$lambda$0() {
        return new PointF();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float mSquaredTouchSlop_delegate$lambda$1(Context context) {
        int scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        return scaledTouchSlop * scaledTouchSlop;
    }

    private final float squaredHypot(float f, float f2) {
        return (f * f) + (f2 * f2);
    }

    public final void attachHostCallback(TaskViewHostCallback taskViewHostCallback) {
        taskViewHostCallback.getClass();
        this.mHostCallback = taskViewHostCallback;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        TaskViewHostCallback taskViewHostCallback;
        motionEvent.getClass();
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mTouchDownToQuitRecents = true;
            getMDownPoint().set(x, y);
        } else if (action == 1) {
            if (this.mTouchDownToQuitRecents && (taskViewHostCallback = this.mHostCallback) != null) {
                taskViewHostCallback.hideRecents(this);
            }
            this.mTouchDownToQuitRecents = false;
        } else if (action != 2) {
            if (action == 3) {
                this.mTouchDownToQuitRecents = false;
            }
        } else if (this.mTouchDownToQuitRecents && squaredHypot(getMDownPoint().x - x, getMDownPoint().y - y) > getMSquaredTouchSlop()) {
            this.mTouchDownToQuitRecents = false;
        }
        return true;
    }
}
