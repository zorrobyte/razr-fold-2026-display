package com.motorola.taskbar.recent;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import com.motorola.taskbar.R$styleable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt;

/* JADX INFO: compiled from: DisplayRatioScaledView.kt */
/* JADX INFO: loaded from: classes2.dex */
public class DisplayRatioScaledView extends FrameLayout {
    private float ratioX;
    private float ratioY;
    private final Point tmpPoint;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DisplayRatioScaledView(Context context) {
        this(context, null, 0, 6, null);
        context.getClass();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DisplayRatioScaledView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        context.getClass();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DisplayRatioScaledView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        context.getClass();
        this.tmpPoint = new Point();
        this.ratioX = 1.0f;
        this.ratioY = 1.0f;
        int[] iArr = R$styleable.DisplayRatioScaledView;
        iArr.getClass();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, i, 0);
        float fraction = typedArrayObtainStyledAttributes.getFraction(R$styleable.DisplayRatioScaledView_ratio, 1, 1, 1.0f);
        this.ratioX = fraction;
        this.ratioY = fraction;
        typedArrayObtainStyledAttributes.recycle();
    }

    public /* synthetic */ DisplayRatioScaledView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        Display display = getDisplay();
        if (display == null) {
            super.onMeasure(0, 0);
            return;
        }
        display.getRealSize(this.tmpPoint);
        int iRoundToInt = MathKt.roundToInt(this.tmpPoint.x * this.ratioX);
        int iRoundToInt2 = MathKt.roundToInt(this.tmpPoint.y * this.ratioY);
        ViewParent parent = getParent();
        ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
        int paddingStart = viewGroup != null ? viewGroup.getPaddingStart() + viewGroup.getPaddingEnd() : 0;
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(iRoundToInt - paddingStart, 1073741824), View.MeasureSpec.makeMeasureSpec(iRoundToInt2 - paddingStart, 1073741824));
    }

    public final void setRatioX(float f) {
        this.ratioX = f;
    }

    public final void setRatioY(float f) {
        this.ratioY = f;
    }
}
