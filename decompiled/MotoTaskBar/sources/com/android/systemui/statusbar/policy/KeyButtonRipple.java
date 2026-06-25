package com.android.systemui.statusbar.policy;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CanvasProperty;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.RenderNodeAnimator;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import com.android.systemui.Interpolators;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.motorola.taskbar.R$dimen;
import java.util.ArrayList;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public class KeyButtonRipple extends Drawable {
    private CanvasProperty mBottomProp;
    private boolean mDark;
    private boolean mDelayTouchFeedback;
    private boolean mDrawingHardwareGlow;
    private boolean mLastDark;
    private CanvasProperty mLeftProp;
    private int mMaxWidth;
    private CanvasProperty mPaintProp;
    private boolean mPressed;
    private CanvasProperty mRightProp;
    private Paint mRipplePaint;
    private CanvasProperty mRxProp;
    private CanvasProperty mRyProp;
    private boolean mSupportHardware;
    private final View mTargetView;
    private CanvasProperty mTopProp;
    private boolean mVisible;
    private float mGlowAlpha = 0.0f;
    private float mGlowScale = 1.0f;
    private final Interpolator mInterpolator = new LogInterpolator();
    private final Handler mHandler = new Handler();
    private final HashSet mRunningAnimations = new HashSet();
    private final ArrayList mTmpArray = new ArrayList();
    private Type mType = Type.ROUNDED_RECT;
    private final AnimatorListenerAdapter mAnimatorListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.policy.KeyButtonRipple.1
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            KeyButtonRipple.this.mRunningAnimations.remove(animator);
            if (!KeyButtonRipple.this.mRunningAnimations.isEmpty() || KeyButtonRipple.this.mPressed) {
                return;
            }
            KeyButtonRipple.this.mVisible = false;
            KeyButtonRipple.this.mDrawingHardwareGlow = false;
            KeyButtonRipple.this.invalidateSelf();
        }
    };

    final class LogInterpolator implements Interpolator {
        private LogInterpolator() {
        }

        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            return 1.0f - ((float) Math.pow(400.0d, ((double) (-f)) * 1.4d));
        }
    }

    public enum Type {
        OVAL,
        ROUNDED_RECT
    }

    public KeyButtonRipple(Context context, View view) {
        this.mMaxWidth = context.getResources().getDimensionPixelSize(R$dimen.key_button_ripple_max_width);
        this.mTargetView = view;
    }

    private void cancelAnimations() {
        this.mVisible = false;
        this.mTmpArray.addAll(this.mRunningAnimations);
        int size = this.mTmpArray.size();
        for (int i = 0; i < size; i++) {
            ((Animator) this.mTmpArray.get(i)).cancel();
        }
        this.mTmpArray.clear();
        this.mRunningAnimations.clear();
        this.mHandler.removeCallbacksAndMessages(null);
    }

    private void drawHardware(RecordingCanvas recordingCanvas) {
        if (this.mDrawingHardwareGlow) {
            if (this.mType == Type.ROUNDED_RECT) {
                recordingCanvas.drawRoundRect(this.mLeftProp, this.mTopProp, this.mRightProp, this.mBottomProp, this.mRxProp, this.mRyProp, this.mPaintProp);
            } else {
                recordingCanvas.drawCircle(CanvasProperty.createFloat(getBounds().width() / 2), CanvasProperty.createFloat(getBounds().height() / 2), CanvasProperty.createFloat((Math.min(getBounds().width(), getBounds().height()) * 1.0f) / 2.0f), this.mPaintProp);
            }
        }
    }

    private void drawSoftware(Canvas canvas) {
        if (this.mGlowAlpha > 0.0f) {
            Paint ripplePaint = getRipplePaint();
            ripplePaint.setAlpha((int) (this.mGlowAlpha * 255.0f));
            float fWidth = getBounds().width();
            float fHeight = getBounds().height();
            boolean z = fWidth > fHeight;
            float rippleSize = getRippleSize() * this.mGlowScale * 0.5f;
            float f = fWidth * 0.5f;
            float f2 = fHeight * 0.5f;
            float f3 = z ? rippleSize : f;
            if (z) {
                rippleSize = f2;
            }
            float f4 = z ? f2 : f;
            if (this.mType == Type.ROUNDED_RECT) {
                float f5 = rippleSize;
                canvas.drawRoundRect(f - f3, f2 - f5, f3 + f, f2 + f5, f4, f4, ripplePaint);
                return;
            }
            canvas.save();
            canvas.translate(f, f2);
            float fMin = Math.min(f3, rippleSize);
            float f6 = -fMin;
            canvas.drawOval(f6, f6, fMin, fMin, ripplePaint);
            canvas.restore();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enterHardware() {
        cancelAnimations();
        this.mVisible = true;
        this.mDrawingHardwareGlow = true;
        setExtendStart(CanvasProperty.createFloat(getExtendSize() / 2));
        RenderNodeAnimator renderNodeAnimator = new RenderNodeAnimator(getExtendStart(), (getExtendSize() / 2) - ((getRippleSize() * 1.0f) / 2.0f));
        renderNodeAnimator.setDuration(350L);
        renderNodeAnimator.setInterpolator(this.mInterpolator);
        renderNodeAnimator.addListener(this.mAnimatorListener);
        renderNodeAnimator.setTarget(this.mTargetView);
        setExtendEnd(CanvasProperty.createFloat(getExtendSize() / 2));
        RenderNodeAnimator renderNodeAnimator2 = new RenderNodeAnimator(getExtendEnd(), (getExtendSize() / 2) + ((getRippleSize() * 1.0f) / 2.0f));
        renderNodeAnimator2.setDuration(350L);
        renderNodeAnimator2.setInterpolator(this.mInterpolator);
        renderNodeAnimator2.addListener(this.mAnimatorListener);
        renderNodeAnimator2.setTarget(this.mTargetView);
        if (isHorizontal()) {
            this.mTopProp = CanvasProperty.createFloat(0.0f);
            this.mBottomProp = CanvasProperty.createFloat(getBounds().height());
            this.mRxProp = CanvasProperty.createFloat(getBounds().height() / 2);
            this.mRyProp = CanvasProperty.createFloat(getBounds().height() / 2);
        } else {
            this.mLeftProp = CanvasProperty.createFloat(0.0f);
            this.mRightProp = CanvasProperty.createFloat(getBounds().width());
            this.mRxProp = CanvasProperty.createFloat(getBounds().width() / 2);
            this.mRyProp = CanvasProperty.createFloat(getBounds().width() / 2);
        }
        this.mGlowScale = 1.0f;
        this.mGlowAlpha = getMaxGlowAlpha();
        Paint ripplePaint = getRipplePaint();
        this.mRipplePaint = ripplePaint;
        ripplePaint.setAlpha((int) (this.mGlowAlpha * 255.0f));
        this.mPaintProp = CanvasProperty.createPaint(this.mRipplePaint);
        renderNodeAnimator.start();
        renderNodeAnimator2.start();
        this.mRunningAnimations.add(renderNodeAnimator);
        this.mRunningAnimations.add(renderNodeAnimator2);
        invalidateSelf();
        if (!this.mDelayTouchFeedback || this.mPressed) {
            return;
        }
        exitHardware();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enterSoftware() {
        cancelAnimations();
        this.mVisible = true;
        this.mGlowAlpha = getMaxGlowAlpha();
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, "glowScale", 0.0f, 1.0f);
        objectAnimatorOfFloat.setInterpolator(this.mInterpolator);
        objectAnimatorOfFloat.setDuration(350L);
        objectAnimatorOfFloat.addListener(this.mAnimatorListener);
        objectAnimatorOfFloat.start();
        this.mRunningAnimations.add(objectAnimatorOfFloat);
        if (!this.mDelayTouchFeedback || this.mPressed) {
            return;
        }
        exitSoftware();
    }

    private void exitHardware() {
        View view = this.mTargetView;
        if (view == null || !view.isAttachedToWindow()) {
            return;
        }
        this.mPaintProp = CanvasProperty.createPaint(getRipplePaint());
        RenderNodeAnimator renderNodeAnimator = new RenderNodeAnimator(this.mPaintProp, 1, 0.0f);
        renderNodeAnimator.setDuration(450L);
        renderNodeAnimator.setInterpolator(Interpolators.ALPHA_OUT);
        renderNodeAnimator.addListener(this.mAnimatorListener);
        renderNodeAnimator.setTarget(this.mTargetView);
        renderNodeAnimator.start();
        this.mRunningAnimations.add(renderNodeAnimator);
        invalidateSelf();
    }

    private void exitSoftware() {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, "glowAlpha", this.mGlowAlpha, 0.0f);
        objectAnimatorOfFloat.setInterpolator(Interpolators.ALPHA_OUT);
        objectAnimatorOfFloat.setDuration(450L);
        objectAnimatorOfFloat.addListener(this.mAnimatorListener);
        objectAnimatorOfFloat.start();
        this.mRunningAnimations.add(objectAnimatorOfFloat);
    }

    private CanvasProperty getExtendEnd() {
        return isHorizontal() ? this.mRightProp : this.mBottomProp;
    }

    private int getExtendSize() {
        boolean zIsHorizontal = isHorizontal();
        Rect bounds = getBounds();
        return zIsHorizontal ? bounds.width() : bounds.height();
    }

    private CanvasProperty getExtendStart() {
        return isHorizontal() ? this.mLeftProp : this.mTopProp;
    }

    private float getMaxGlowAlpha() {
        return this.mLastDark ? 0.1f : 0.2f;
    }

    private Paint getRipplePaint() {
        if (this.mRipplePaint == null) {
            Paint paint = new Paint();
            this.mRipplePaint = paint;
            paint.setAntiAlias(true);
            this.mRipplePaint.setColor(this.mLastDark ? DarkIconDispatcher.DEFAULT_INVERSE_ICON_TINT : -1);
        }
        return this.mRipplePaint;
    }

    private int getRippleSize() {
        return Math.min(isHorizontal() ? getBounds().width() : getBounds().height(), this.mMaxWidth);
    }

    private boolean isHorizontal() {
        return getBounds().width() > getBounds().height();
    }

    private void setExtendEnd(CanvasProperty canvasProperty) {
        if (isHorizontal()) {
            this.mRightProp = canvasProperty;
        } else {
            this.mBottomProp = canvasProperty;
        }
    }

    private void setExtendStart(CanvasProperty canvasProperty) {
        if (isHorizontal()) {
            this.mLeftProp = canvasProperty;
        } else {
            this.mTopProp = canvasProperty;
        }
    }

    private void setPressedHardware(boolean z) {
        if (!z) {
            exitHardware();
            return;
        }
        if (!this.mDelayTouchFeedback) {
            enterHardware();
            return;
        }
        if (this.mRunningAnimations.isEmpty()) {
            this.mHandler.removeCallbacksAndMessages(null);
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.policy.KeyButtonRipple$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.enterHardware();
                }
            }, ViewConfiguration.getTapTimeout());
        } else if (this.mVisible) {
            enterHardware();
        }
    }

    private void setPressedSoftware(boolean z) {
        if (!z) {
            exitSoftware();
            return;
        }
        if (!this.mDelayTouchFeedback) {
            enterSoftware();
            return;
        }
        if (this.mRunningAnimations.isEmpty()) {
            this.mHandler.removeCallbacksAndMessages(null);
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.policy.KeyButtonRipple$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.enterSoftware();
                }
            }, ViewConfiguration.getTapTimeout());
        } else if (this.mVisible) {
            enterSoftware();
        }
    }

    public void abortDelayedRipple() {
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        boolean zIsHardwareAccelerated = canvas.isHardwareAccelerated();
        this.mSupportHardware = zIsHardwareAccelerated;
        if (zIsHardwareAccelerated) {
            drawHardware((RecordingCanvas) canvas);
        } else {
            drawSoftware(canvas);
        }
    }

    public float getGlowAlpha() {
        return this.mGlowAlpha;
    }

    public float getGlowScale() {
        return this.mGlowScale;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean hasFocusStateSpecified() {
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public void jumpToCurrentState() {
        cancelAnimations();
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        boolean z;
        int i = 0;
        while (true) {
            if (i >= iArr.length) {
                z = false;
                break;
            }
            if (iArr[i] == 16842919) {
                z = true;
                break;
            }
            i++;
        }
        if (z == this.mPressed) {
            return false;
        }
        setPressed(z);
        this.mPressed = z;
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public void setDarkIntensity(float f) {
        this.mDark = f >= 0.5f;
    }

    public void setGlowAlpha(float f) {
        this.mGlowAlpha = f;
        invalidateSelf();
    }

    public void setGlowScale(float f) {
        this.mGlowScale = f;
        invalidateSelf();
    }

    public void setPressed(boolean z) {
        boolean z2 = this.mDark;
        if (z2 != this.mLastDark && z) {
            this.mRipplePaint = null;
            this.mLastDark = z2;
        }
        if (this.mSupportHardware) {
            setPressedHardware(z);
        } else {
            setPressedSoftware(z);
        }
    }

    public void setType(Type type) {
        this.mType = type;
    }
}
