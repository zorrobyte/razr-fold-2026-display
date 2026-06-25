package com.android.systemui.shared.navigationbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.CanvasProperty;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Trace;
import android.util.Log;
import android.view.RenderNodeAnimator;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.systemui.plugins.DarkIconDispatcher;
import java.util.ArrayList;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public class KeyButtonRipple extends Drawable {
    private static final Interpolator ALPHA_OUT_INTERPOLATOR = new PathInterpolator(0.0f, 0.0f, 0.8f, 1.0f);
    private final AnimatorListenerAdapter mAnimatorListener;
    private CanvasProperty mBottomProp;
    private boolean mDark;
    private boolean mDelayTouchFeedback;
    private boolean mDrawingHardwareGlow;
    private float mGlowAlpha;
    private float mGlowScale;
    private final Handler mHandler;
    private final Interpolator mInterpolator;
    private boolean mLastDark;
    private CanvasProperty mLeftProp;
    private int mMaxWidth;
    private final int mMaxWidthResource;
    private Runnable mOnInvisibleRunnable;
    private CanvasProperty mPaintProp;
    private boolean mPressed;
    private CanvasProperty mRightProp;
    private Paint mRipplePaint;
    private final HashSet mRunningAnimations;
    private CanvasProperty mRxProp;
    private CanvasProperty mRyProp;
    private boolean mSpeedUpNextFade;
    private boolean mSupportHardware;
    private final View mTargetView;
    private final ArrayList mTmpArray;
    private CanvasProperty mTopProp;
    private Type mType;
    private boolean mVisible;

    public enum Type {
        OVAL,
        ROUNDED_RECT
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

    private void endAnimations(String str, boolean z) {
        if (Trace.isEnabled()) {
            Trace.instant(4096L, "KeyButtonRipple.endAnim: reason=" + str + " cancel=" + z);
        }
        this.mVisible = false;
        this.mTmpArray.addAll(this.mRunningAnimations);
        int size = this.mTmpArray.size();
        for (int i = 0; i < size; i++) {
            Animator animator = (Animator) this.mTmpArray.get(i);
            if (z) {
                animator.cancel();
            } else {
                animator.end();
            }
        }
        this.mTmpArray.clear();
        this.mRunningAnimations.clear();
        this.mHandler.removeCallbacksAndMessages(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enterHardware() {
        if (!this.mTargetView.isAttachedToWindow()) {
            Log.w("KeyButtonRipple", "enterHardware when view detached", new Throwable());
            return;
        }
        endAnimations("enterHardware", true);
        this.mVisible = true;
        this.mDrawingHardwareGlow = true;
        setExtendStart(CanvasProperty.createFloat(getExtendSize() / 2));
        RenderNodeAnimator renderNodeAnimator = new RenderNodeAnimator(getExtendStart(), (getExtendSize() / 2) - ((getRippleSize() * 1.35f) / 2.0f));
        renderNodeAnimator.setDuration(350L);
        renderNodeAnimator.setInterpolator(this.mInterpolator);
        renderNodeAnimator.addListener(this.mAnimatorListener);
        renderNodeAnimator.setTarget(this.mTargetView);
        setExtendEnd(CanvasProperty.createFloat(getExtendSize() / 2));
        RenderNodeAnimator renderNodeAnimator2 = new RenderNodeAnimator(getExtendEnd(), (getExtendSize() / 2) + ((getRippleSize() * 1.35f) / 2.0f));
        renderNodeAnimator2.setDuration(350L);
        renderNodeAnimator2.setInterpolator(this.mInterpolator);
        renderNodeAnimator2.addListener(this.mAnimatorListener);
        renderNodeAnimator2.addListener((Animator.AnimatorListener) null);
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
        this.mGlowScale = 1.35f;
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
        endAnimations("enterSoftware", true);
        this.mVisible = true;
        this.mGlowAlpha = getMaxGlowAlpha();
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, "glowScale", 0.0f, 1.35f);
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
        if (!this.mTargetView.isAttachedToWindow()) {
            Log.w("KeyButtonRipple", "exitHardware when view detached", new Throwable());
            return;
        }
        this.mPaintProp = CanvasProperty.createPaint(getRipplePaint());
        RenderNodeAnimator renderNodeAnimator = new RenderNodeAnimator(this.mPaintProp, 1, 0.0f);
        renderNodeAnimator.setDuration(getFadeDuration());
        renderNodeAnimator.setInterpolator(ALPHA_OUT_INTERPOLATOR);
        renderNodeAnimator.addListener(this.mAnimatorListener);
        renderNodeAnimator.addListener((Animator.AnimatorListener) null);
        renderNodeAnimator.setTarget(this.mTargetView);
        renderNodeAnimator.start();
        this.mRunningAnimations.add(renderNodeAnimator);
        invalidateSelf();
    }

    private void exitSoftware() {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, "glowAlpha", this.mGlowAlpha, 0.0f);
        objectAnimatorOfFloat.setInterpolator(ALPHA_OUT_INTERPOLATOR);
        objectAnimatorOfFloat.setDuration(getFadeDuration());
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

    private int getFadeDuration() {
        int i = this.mSpeedUpNextFade ? 80 : 450;
        this.mSpeedUpNextFade = false;
        return i;
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

    private void setPressed(boolean z) {
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
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.shared.navigationbar.KeyButtonRipple$$ExternalSyntheticLambda0
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
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.shared.navigationbar.KeyButtonRipple$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.enterSoftware();
                }
            }, ViewConfiguration.getTapTimeout());
        } else if (this.mVisible) {
            enterSoftware();
        }
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
        if (this.mPressed || this.mVisible || this.mOnInvisibleRunnable == null) {
            return;
        }
        new Handler(Looper.getMainLooper()).post(this.mOnInvisibleRunnable);
        this.mOnInvisibleRunnable = null;
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
        endAnimations("jumpToCurrentState", false);
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

    public void setGlowAlpha(float f) {
        this.mGlowAlpha = f;
        invalidateSelf();
    }

    public void setGlowScale(float f) {
        this.mGlowScale = f;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (visible) {
            jumpToCurrentState();
        }
        return visible;
    }

    public void updateResources() {
        this.mMaxWidth = this.mTargetView.getContext().getResources().getDimensionPixelSize(this.mMaxWidthResource);
        invalidateSelf();
    }
}
