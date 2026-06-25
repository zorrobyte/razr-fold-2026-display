package com.android.systemui.media.controls.ui.drawable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.os.Trace;
import android.util.MathUtils;
import com.android.app.animation.Interpolators;
import com.android.app.tracing.TraceUtilsKt;
import com.android.internal.graphics.ColorUtils;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: SquigglyProgress.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SquigglyProgress extends Drawable {
    private boolean animate;
    private ValueAnimator heightAnimator;
    private float heightFraction;
    private long lastFrameTime;
    private float lineAmplitude;
    private final Paint linePaint;
    private final float matchedWaveEndpoint;
    private final float minWaveEndpoint;
    private final Path path;
    private float phaseOffset;
    private float phaseSpeed;
    private float strokeWidth;
    private boolean transitionEnabled;
    private final float transitionPeriods;
    private float waveLength;
    private final Paint wavePaint;

    public SquigglyProgress() {
        Paint paint = new Paint();
        this.wavePaint = paint;
        Paint paint2 = new Paint();
        this.linePaint = paint2;
        this.path = new Path();
        this.lastFrameTime = -1L;
        this.transitionPeriods = 1.5f;
        this.minWaveEndpoint = 0.2f;
        this.matchedWaveEndpoint = 0.6f;
        this.transitionEnabled = true;
        Paint.Cap cap = Paint.Cap.ROUND;
        paint.setStrokeCap(cap);
        paint2.setStrokeCap(cap);
        Paint.Style style = Paint.Style.STROKE;
        paint2.setStyle(style);
        paint.setStyle(style);
        paint2.setAlpha(77);
    }

    private final void drawTraced(Canvas canvas) {
        if (this.animate) {
            invalidateSelf();
            long jUptimeMillis = SystemClock.uptimeMillis();
            this.phaseOffset = (this.phaseOffset + (((jUptimeMillis - this.lastFrameTime) / 1000.0f) * this.phaseSpeed)) % this.waveLength;
            this.lastFrameTime = jUptimeMillis;
        }
        float level = getLevel() / 10000.0f;
        float fWidth = getBounds().width();
        float f = fWidth * level;
        if (this.transitionEnabled) {
            float f2 = this.matchedWaveEndpoint;
            if (level <= f2) {
                level = MathUtils.lerp(this.minWaveEndpoint, f2, MathUtils.lerpInv(0.0f, f2, level));
            }
        }
        final float f3 = level * fWidth;
        float f4 = (-this.phaseOffset) - (this.waveLength / 2.0f);
        float f5 = this.transitionEnabled ? fWidth : f3;
        Function2 function2 = new Function2() { // from class: com.android.systemui.media.controls.ui.drawable.SquigglyProgress$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Float.valueOf(SquigglyProgress.drawTraced$lambda$2(this.f$0, f3, ((Float) obj).floatValue(), ((Float) obj2).floatValue()));
            }
        };
        this.path.rewind();
        this.path.moveTo(f4, 0.0f);
        float f6 = 1.0f;
        float fFloatValue = ((Number) function2.invoke(Float.valueOf(f4), Float.valueOf(1.0f))).floatValue();
        float f7 = this.waveLength / 2.0f;
        float f8 = fFloatValue;
        float f9 = f4;
        while (f9 < f5) {
            f6 = -f6;
            float f10 = f9 + f7;
            float f11 = f9 + (f7 / 2);
            float fFloatValue2 = ((Number) function2.invoke(Float.valueOf(f10), Float.valueOf(f6))).floatValue();
            this.path.cubicTo(f11, f8, f11, fFloatValue2, f10, fFloatValue2);
            f8 = fFloatValue2;
            f9 = f10;
        }
        float f12 = this.lineAmplitude + this.strokeWidth;
        canvas.save();
        canvas.translate(getBounds().left, getBounds().centerY());
        canvas.save();
        float f13 = (-1.0f) * f12;
        canvas.clipRect(0.0f, f13, f, f12);
        canvas.drawPath(this.path, this.wavePaint);
        canvas.restore();
        if (this.transitionEnabled) {
            canvas.save();
            canvas.clipRect(f, f13, fWidth, f12);
            canvas.drawPath(this.path, this.linePaint);
            canvas.restore();
        } else {
            canvas.drawLine(f, 0.0f, fWidth, 0.0f, this.linePaint);
        }
        canvas.drawPoint(0.0f, ((float) Math.cos((Math.abs(f4) / this.waveLength) * 6.2831855f)) * this.lineAmplitude * this.heightFraction, this.wavePaint);
        canvas.restore();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float drawTraced$lambda$2(SquigglyProgress squigglyProgress, float f, float f2, float f3) {
        if (!squigglyProgress.transitionEnabled) {
            return f3 * squigglyProgress.heightFraction * squigglyProgress.lineAmplitude;
        }
        float f4 = (squigglyProgress.transitionPeriods * squigglyProgress.waveLength) / 2.0f;
        return f3 * squigglyProgress.heightFraction * squigglyProgress.lineAmplitude * MathUtils.lerpInvSat(f + f4, f - f4, f2);
    }

    private final void updateColors(int i, int i2) {
        this.wavePaint.setColor(ColorUtils.setAlphaComponent(i, i2));
        this.linePaint.setColor(ColorUtils.setAlphaComponent(i, (int) (77 * (i2 / 255.0f))));
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        canvas.getClass();
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("SquigglyProgress#draw");
        }
        try {
            drawTraced(canvas);
            Unit unit = Unit.INSTANCE;
        } finally {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.wavePaint.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onLevelChange(int i) {
        return this.animate;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        updateColors(this.wavePaint.getColor(), i);
    }

    public final void setAnimate(boolean z) {
        if (this.animate == z) {
            return;
        }
        this.animate = z;
        if (z) {
            this.lastFrameTime = SystemClock.uptimeMillis();
        }
        ValueAnimator valueAnimator = this.heightAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.heightFraction, this.animate ? 1.0f : 0.0f);
        if (this.animate) {
            valueAnimatorOfFloat.setStartDelay(60L);
            valueAnimatorOfFloat.setDuration(800L);
            valueAnimatorOfFloat.setInterpolator(Interpolators.EMPHASIZED_DECELERATE);
        } else {
            valueAnimatorOfFloat.setDuration(550L);
            valueAnimatorOfFloat.setInterpolator(Interpolators.STANDARD_DECELERATE);
        }
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.controls.ui.drawable.SquigglyProgress$animate$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                valueAnimator2.getClass();
                SquigglyProgress squigglyProgress = this.this$0;
                Object animatedValue = valueAnimator2.getAnimatedValue();
                animatedValue.getClass();
                squigglyProgress.heightFraction = ((Float) animatedValue).floatValue();
                this.this$0.invalidateSelf();
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.media.controls.ui.drawable.SquigglyProgress$animate$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                animator.getClass();
                this.this$0.heightAnimator = null;
            }
        });
        valueAnimatorOfFloat.start();
        this.heightAnimator = valueAnimatorOfFloat;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.wavePaint.setColorFilter(colorFilter);
        this.linePaint.setColorFilter(colorFilter);
    }

    public final void setLineAmplitude(float f) {
        this.lineAmplitude = f;
    }

    public final void setPhaseSpeed(float f) {
        this.phaseSpeed = f;
    }

    public final void setStrokeWidth(float f) {
        if (this.strokeWidth == f) {
            return;
        }
        this.strokeWidth = f;
        this.wavePaint.setStrokeWidth(f);
        this.linePaint.setStrokeWidth(f);
    }

    @Override // android.graphics.drawable.Drawable
    public void setTint(int i) {
        updateColors(i, getAlpha());
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        if (colorStateList == null) {
            return;
        }
        updateColors(colorStateList.getDefaultColor(), getAlpha());
    }

    public final void setTransitionEnabled(boolean z) {
        this.transitionEnabled = z;
        invalidateSelf();
    }

    public final void setWaveLength(float f) {
        this.waveLength = f;
    }
}
