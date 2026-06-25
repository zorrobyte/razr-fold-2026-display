package com.android.systemui.surfaceeffects.ripple;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.android.systemui.surfaceeffects.ripple.RippleShader;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RippleView.kt */
/* JADX INFO: loaded from: classes.dex */
public class RippleView extends View {
    private final ValueAnimator animator;
    private float centerX;
    private float centerY;
    private long duration;
    private final Paint ripplePaint;
    protected RippleShader rippleShader;
    private RippleShader.RippleShape rippleShape;

    public RippleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.ripplePaint = new Paint();
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.getClass();
        this.animator = valueAnimatorOfFloat;
        this.duration = 1750L;
    }

    protected final RippleShader getRippleShader() {
        RippleShader rippleShader = this.rippleShader;
        if (rippleShader != null) {
            return rippleShader;
        }
        Intrinsics.throwUninitializedPropertyAccessException("rippleShader");
        return null;
    }

    public final RippleShader.RippleShape getRippleShape() {
        RippleShader.RippleShape rippleShape = this.rippleShape;
        if (rippleShape != null) {
            return rippleShape;
        }
        Intrinsics.throwUninitializedPropertyAccessException("rippleShape");
        return null;
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        getRippleShader().setPixelDensity(getResources().getDisplayMetrics().density);
        super.onAttachedToWindow();
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        getRippleShader().setPixelDensity(getResources().getDisplayMetrics().density);
        super.onConfigurationChanged(configuration);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        canvas.getClass();
        if (canvas.isHardwareAccelerated()) {
            if (getRippleShape() == RippleShader.RippleShape.CIRCLE) {
                canvas.drawCircle(this.centerX, this.centerY, getRippleShader().getRippleSize().getCurrentWidth(), this.ripplePaint);
            } else {
                if (getRippleShape() != RippleShader.RippleShape.ELLIPSE) {
                    canvas.drawPaint(this.ripplePaint);
                    return;
                }
                float f = 2;
                float currentWidth = getRippleShader().getRippleSize().getCurrentWidth() * f;
                float currentHeight = getRippleShader().getRippleSize().getCurrentHeight() * f;
                float f2 = this.centerX;
                float f3 = this.centerY;
                canvas.drawRect(f2 - currentWidth, f3 - currentHeight, f2 + currentWidth, f3 + currentHeight, this.ripplePaint);
            }
        }
    }
}
