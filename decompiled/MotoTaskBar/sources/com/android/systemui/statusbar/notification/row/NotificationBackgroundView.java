package com.android.systemui.statusbar.notification.row;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.view.View;
import com.android.internal.util.ContrastColorUtil;
import com.android.systemui.Dumpable;
import com.android.systemui.res.R$bool;
import com.android.systemui.res.R$color;
import com.android.systemui.res.R$dimen;
import com.android.systemui.util.ColorUtilKt;
import com.android.systemui.util.DrawableDumpKt;
import com.motorola.taskbar.R$id;

/* JADX INFO: loaded from: classes.dex */
public class NotificationBackgroundView extends View implements Dumpable {
    private int mActualHeight;
    private int mActualWidth;
    private Drawable mBackground;
    private boolean mBottomAmountClips;
    private boolean mBottomIsRounded;
    private int mClipBottomAmount;
    private int mClipTopAmount;
    private final float[] mCornerRadii;
    private final ColorStateList mDarkColoredStatefulColors;
    private final boolean mDontModifyCorners;
    private int mDrawableAlpha;
    private int mExpandAnimationHeight;
    private boolean mExpandAnimationRunning;
    private int mExpandAnimationWidth;
    private final float[] mFocusOverlayCornerRadii;
    private float mFocusOverlayStroke;
    private final ColorStateList mLightColoredStatefulColors;
    private final int mNormalColor;
    private Integer mRippleColor;
    private int mTintColor;

    public NotificationBackgroundView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCornerRadii = new float[8];
        this.mFocusOverlayCornerRadii = new float[8];
        this.mFocusOverlayStroke = 0.0f;
        this.mBottomAmountClips = true;
        this.mActualHeight = -1;
        this.mActualWidth = -1;
        this.mExpandAnimationWidth = -1;
        this.mExpandAnimationHeight = -1;
        this.mDrawableAlpha = 255;
        this.mDontModifyCorners = getResources().getBoolean(R$bool.config_clipNotificationsToOutline);
        this.mLightColoredStatefulColors = getResources().getColorStateList(R$color.notification_state_color_light);
        this.mDarkColoredStatefulColors = getResources().getColorStateList(R$color.notification_state_color_dark);
        this.mNormalColor = ((View) this).mContext.getColor(R.color.material_blue_grey_200);
        this.mFocusOverlayStroke = getResources().getDimension(R$dimen.notification_focus_stroke_width);
    }

    private void draw(Canvas canvas, Drawable drawable) {
        if (drawable != null) {
            int actualHeight = getActualHeight();
            if (this.mBottomIsRounded && this.mBottomAmountClips && !this.mExpandAnimationRunning) {
                actualHeight -= this.mClipBottomAmount;
            }
            boolean zIsLayoutRtl = isLayoutRtl();
            int width = getWidth();
            int actualWidth = getActualWidth();
            int i = zIsLayoutRtl ? width - actualWidth : 0;
            int i2 = zIsLayoutRtl ? width : actualWidth;
            if (this.mExpandAnimationRunning) {
                i = (int) ((width - actualWidth) / 2.0f);
                i2 = i + actualWidth;
            }
            drawable.setBounds(i, 0, i2, actualHeight);
            drawable.draw(canvas);
        }
    }

    private int getActualHeight() {
        int i;
        if (this.mExpandAnimationRunning && (i = this.mExpandAnimationHeight) > -1) {
            return i;
        }
        int i2 = this.mActualHeight;
        return i2 > -1 ? i2 : getHeight();
    }

    private int getActualWidth() {
        int i;
        if (this.mExpandAnimationRunning && (i = this.mExpandAnimationWidth) > -1) {
            return i;
        }
        int i2 = this.mActualWidth;
        return i2 > -1 ? i2 : getWidth();
    }

    private Drawable getBaseBackgroundLayer() {
        return ((LayerDrawable) this.mBackground).getDrawable(0);
    }

    private Drawable getStatefulBackgroundLayer() {
        return ((LayerDrawable) this.mBackground).getDrawable(1);
    }

    private void setStatefulColors() {
        int i = this.mTintColor;
        if (i != this.mNormalColor) {
            ((GradientDrawable) getStatefulBackgroundLayer().mutate()).setColor(ContrastColorUtil.isColorDark(i) ? this.mDarkColoredStatefulColors : this.mLightColoredStatefulColors);
        }
    }

    private void updateBackgroundRadii() {
        if (this.mDontModifyCorners) {
            return;
        }
        Drawable drawable = this.mBackground;
        if (drawable instanceof LayerDrawable) {
            LayerDrawable layerDrawable = (LayerDrawable) drawable;
            int numberOfLayers = layerDrawable.getNumberOfLayers();
            for (int i = 0; i < numberOfLayers; i++) {
                ((GradientDrawable) layerDrawable.getDrawable(i)).setCornerRadii(this.mCornerRadii);
            }
            updateFocusOverlayRadii(layerDrawable);
        }
    }

    private void updateFocusOverlayRadii(LayerDrawable layerDrawable) {
        GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R$id.notification_focus_overlay);
        int i = 0;
        while (true) {
            float[] fArr = this.mCornerRadii;
            if (i >= fArr.length) {
                gradientDrawable.setCornerRadii(this.mFocusOverlayCornerRadii);
                return;
            } else {
                this.mFocusOverlayCornerRadii[i] = Math.max(0.0f, fArr[i] - this.mFocusOverlayStroke);
                i++;
            }
        }
    }

    @Override // android.view.View
    public void drawableHotspotChanged(float f, float f2) {
        Drawable drawable = this.mBackground;
        if (drawable != null) {
            drawable.setHotspot(f, f2);
        }
    }

    @Override // android.view.View
    protected void drawableStateChanged() {
        setState(getDrawableState());
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.mClipTopAmount + this.mClipBottomAmount < getActualHeight() || this.mExpandAnimationRunning) {
            canvas.save();
            if (!this.mExpandAnimationRunning) {
                canvas.clipRect(0, this.mClipTopAmount, getWidth(), getActualHeight() - this.mClipBottomAmount);
            }
            draw(canvas, this.mBackground);
            canvas.restore();
        }
    }

    public void setActualHeight(int i) {
        if (this.mExpandAnimationRunning) {
            return;
        }
        this.mActualHeight = i;
        invalidate();
    }

    public void setBottomAmountClips(boolean z) {
        if (z != this.mBottomAmountClips) {
            this.mBottomAmountClips = z;
            invalidate();
        }
    }

    public void setClipBottomAmount(int i) {
        this.mClipBottomAmount = i;
        invalidate();
    }

    public void setClipTopAmount(int i) {
        this.mClipTopAmount = i;
        invalidate();
    }

    public void setCustomBackground(int i) {
        setCustomBackground(((View) this).mContext.getDrawable(i));
    }

    public void setCustomBackground(Drawable drawable) {
        Drawable drawable2 = this.mBackground;
        if (drawable2 != null) {
            drawable2.setCallback(null);
            unscheduleDrawable(this.mBackground);
        }
        this.mBackground = drawable;
        this.mRippleColor = null;
        drawable.mutate();
        Drawable drawable3 = this.mBackground;
        if (drawable3 != null) {
            drawable3.setCallback(this);
            setTint(this.mTintColor);
        }
        Drawable drawable4 = this.mBackground;
        if (drawable4 instanceof RippleDrawable) {
            ((RippleDrawable) drawable4).setForceSoftware(true);
        }
        updateBackgroundRadii();
        invalidate();
    }

    public void setRadius(float f, float f2) {
        float[] fArr = this.mCornerRadii;
        if (f == fArr[0] && f2 == fArr[4]) {
            return;
        }
        this.mBottomIsRounded = f2 != 0.0f;
        fArr[0] = f;
        fArr[1] = f;
        fArr[2] = f;
        fArr[3] = f;
        fArr[4] = f2;
        fArr[5] = f2;
        fArr[6] = f2;
        fArr[7] = f2;
        updateBackgroundRadii();
    }

    public void setRippleColor(int i) {
        Drawable drawable = this.mBackground;
        if (!(drawable instanceof RippleDrawable)) {
            this.mRippleColor = null;
        } else {
            ((RippleDrawable) drawable).setColor(ColorStateList.valueOf(i));
            this.mRippleColor = Integer.valueOf(i);
        }
    }

    public void setState(int[] iArr) {
        Drawable drawable = this.mBackground;
        if (drawable == null || !drawable.isStateful()) {
            return;
        }
        this.mBackground.setState(iArr);
    }

    public void setTint(int i) {
        Drawable baseBackgroundLayer = getBaseBackgroundLayer();
        baseBackgroundLayer.mutate().setTintMode(PorterDuff.Mode.SRC_ATOP);
        baseBackgroundLayer.setTint(i);
        this.mTintColor = i;
        setStatefulColors();
        invalidate();
    }

    public String toDumpString() {
        return "<NotificationBackgroundView tintColor=" + ColorUtilKt.hexColorString(Integer.valueOf(this.mTintColor)) + " rippleColor=" + ColorUtilKt.hexColorString(this.mRippleColor) + " bgColor=" + DrawableDumpKt.getSolidColor(this.mBackground) + ">";
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mBackground;
    }
}
