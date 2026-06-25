package com.android.systemui.util.animation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: UniqueObjectHostView.kt */
/* JADX INFO: loaded from: classes.dex */
public final class UniqueObjectHostView extends FrameLayout {
    public MeasurementManager measurementManager;

    /* JADX INFO: compiled from: UniqueObjectHostView.kt */
    public interface MeasurementManager {
        MeasurementOutput onMeasure(MeasurementInput measurementInput);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UniqueObjectHostView(Context context) {
        super(context);
        context.getClass();
    }

    private final boolean isCurrentHost() {
        return getChildCount() != 0;
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (view == null) {
            throw new IllegalArgumentException("child must be non-null");
        }
        if (view.getMeasuredWidth() == 0 || getMeasuredWidth() == 0 || UniqueObjectHostViewKt.getRequiresRemeasuring(view)) {
            super.addView(view, i, layoutParams);
            return;
        }
        invalidate();
        addViewInLayout(view, i, layoutParams, true);
        view.resolveRtlPropertiesIfNeeded();
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        view.layout(paddingLeft, paddingTop, (getMeasuredWidth() + paddingLeft) - (getPaddingStart() + getPaddingEnd()), (getMeasuredHeight() + paddingTop) - (getPaddingTop() + getPaddingBottom()));
    }

    public final MeasurementManager getMeasurementManager() {
        MeasurementManager measurementManager = this.measurementManager;
        if (measurementManager != null) {
            return measurementManager;
        }
        Intrinsics.throwUninitializedPropertyAccessException("measurementManager");
        return null;
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int paddingStart = getPaddingStart() + getPaddingEnd();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        MeasurementOutput measurementOutputOnMeasure = getMeasurementManager().onMeasure(new MeasurementInput(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i) - paddingStart, View.MeasureSpec.getMode(i)), View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2) - paddingTop, View.MeasureSpec.getMode(i2))));
        int iComponent1 = measurementOutputOnMeasure.component1();
        int iComponent2 = measurementOutputOnMeasure.component2();
        if (isCurrentHost()) {
            super.onMeasure(i, i2);
            View childAt = getChildAt(0);
            if (childAt != null) {
                UniqueObjectHostViewKt.setRequiresRemeasuring(childAt, false);
            }
        }
        setMeasuredDimension(iComponent1 + paddingStart, iComponent2 + paddingTop);
    }

    public final void setMeasurementManager(MeasurementManager measurementManager) {
        measurementManager.getClass();
        this.measurementManager = measurementManager;
    }
}
