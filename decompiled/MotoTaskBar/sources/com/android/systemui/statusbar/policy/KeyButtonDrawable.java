package com.android.systemui.statusbar.policy;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.util.FloatProperty;
import android.view.ContextThemeWrapper;
import com.motorola.taskbar.R$attr;
import com.motorola.taskbar.R$color;
import com.motorola.taskbar.R$dimen;
import com.motorola.taskbar.util.Utils;

/* JADX INFO: loaded from: classes.dex */
public class KeyButtonDrawable extends Drawable {
    public static final FloatProperty KEY_DRAWABLE_ROTATE = new FloatProperty("KeyButtonRotation") { // from class: com.android.systemui.statusbar.policy.KeyButtonDrawable.1
        @Override // android.util.Property
        public Float get(KeyButtonDrawable keyButtonDrawable) {
            return Float.valueOf(keyButtonDrawable.getRotation());
        }

        @Override // android.util.FloatProperty
        public void setValue(KeyButtonDrawable keyButtonDrawable, float f) {
            keyButtonDrawable.setRotation(f);
        }
    };
    public static final FloatProperty KEY_DRAWABLE_TRANSLATE_Y = new FloatProperty("KeyButtonTranslateY") { // from class: com.android.systemui.statusbar.policy.KeyButtonDrawable.2
        @Override // android.util.Property
        public Float get(KeyButtonDrawable keyButtonDrawable) {
            return Float.valueOf(keyButtonDrawable.getTranslationY());
        }

        @Override // android.util.FloatProperty
        public void setValue(KeyButtonDrawable keyButtonDrawable, float f) {
            keyButtonDrawable.setTranslationY(f);
        }
    };
    private AnimatedVectorDrawable mAnimatedDrawable;
    private final Paint mIconPaint;
    private final Paint mShadowPaint;
    private final ShadowDrawableState mState;

    class ShadowDrawableState extends Drawable.ConstantState {
        int mAlpha = 255;
        int mBaseHeight;
        int mBaseWidth;
        int mChangingConfigurations;
        Drawable.ConstantState mChildState;
        final int mDarkColor;
        float mDarkIntensity;
        boolean mHorizontalFlip;
        boolean mIsHardwareBitmap;
        Bitmap mLastDrawnIcon;
        Bitmap mLastDrawnShadow;
        final int mLightColor;
        final Color mOvalBackgroundColor;
        float mRotateDegrees;
        int mShadowColor;
        int mShadowOffsetX;
        int mShadowOffsetY;
        int mShadowSize;
        final boolean mSupportsAnimation;
        float mTranslationX;
        float mTranslationY;

        public ShadowDrawableState(int i, int i2, boolean z, boolean z2, Color color) {
            this.mLightColor = i;
            this.mDarkColor = i2;
            this.mSupportsAnimation = z;
            this.mHorizontalFlip = z2;
            this.mOvalBackgroundColor = color;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return true;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new KeyButtonDrawable(null, this);
        }
    }

    public KeyButtonDrawable(Drawable drawable, int i, int i2, boolean z, Color color) {
        this(drawable, new ShadowDrawableState(i, i2, drawable instanceof AnimatedVectorDrawable, z, color));
    }

    private KeyButtonDrawable(Drawable drawable, ShadowDrawableState shadowDrawableState) {
        this.mIconPaint = new Paint(3);
        this.mShadowPaint = new Paint(3);
        this.mState = shadowDrawableState;
        if (drawable != null) {
            shadowDrawableState.mBaseHeight = drawable.getIntrinsicHeight();
            shadowDrawableState.mBaseWidth = drawable.getIntrinsicWidth();
            shadowDrawableState.mChangingConfigurations = drawable.getChangingConfigurations();
            shadowDrawableState.mChildState = drawable.getConstantState();
        }
        if (canAnimate()) {
            AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) shadowDrawableState.mChildState.newDrawable().mutate();
            this.mAnimatedDrawable = animatedVectorDrawable;
            setDrawableBounds(animatedVectorDrawable);
        }
    }

    public static KeyButtonDrawable create(Context context, int i, int i2, int i3, boolean z, Color color) {
        Resources resources = context.getResources();
        boolean z2 = resources.getConfiguration().getLayoutDirection() == 1;
        Drawable drawable = context.getDrawable(i3);
        KeyButtonDrawable keyButtonDrawable = new KeyButtonDrawable(drawable, i, i2, z2 && drawable.isAutoMirrored(), color);
        if (z) {
            keyButtonDrawable.setShadowProperties(resources.getDimensionPixelSize(R$dimen.nav_key_button_shadow_offset_x), resources.getDimensionPixelSize(R$dimen.nav_key_button_shadow_offset_y), resources.getDimensionPixelSize(R$dimen.nav_key_button_shadow_radius), context.getColor(R$color.nav_key_button_shadow_color));
        }
        return keyButtonDrawable;
    }

    private static KeyButtonDrawable create(Context context, int i, int i2, Drawable drawable, boolean z, Color color) {
        Resources resources = context.getResources();
        KeyButtonDrawable keyButtonDrawable = new KeyButtonDrawable(drawable, i, i2, (resources.getConfiguration().getLayoutDirection() == 1) && drawable.isAutoMirrored(), color);
        if (z) {
            keyButtonDrawable.setShadowProperties(resources.getDimensionPixelSize(R$dimen.nav_key_button_shadow_offset_x), resources.getDimensionPixelSize(R$dimen.nav_key_button_shadow_offset_y), resources.getDimensionPixelSize(R$dimen.nav_key_button_shadow_radius), context.getColor(R$color.nav_key_button_shadow_color));
        }
        return keyButtonDrawable;
    }

    public static KeyButtonDrawable create(Context context, int i, boolean z) {
        return create(context, i, z, (Color) null);
    }

    public static KeyButtonDrawable create(Context context, int i, boolean z, Color color) {
        return create(new ContextThemeWrapper(context, Utils.getThemeAttr(context, R$attr.lightIconTheme)), new ContextThemeWrapper(context, Utils.getThemeAttr(context, R$attr.darkIconTheme)), i, z, color);
    }

    public static KeyButtonDrawable create(Context context, Context context2, int i, boolean z, Color color) {
        int i2 = R$attr.singleToneColor;
        return create(context, Utils.getColorAttrDefaultColor(context, i2), Utils.getColorAttrDefaultColor(context2, i2), i, z, color);
    }

    private static KeyButtonDrawable create(Context context, Context context2, Drawable drawable, boolean z, Color color) {
        int i = R$attr.singleToneColor;
        return create(context, Utils.getColorAttrDefaultColor(context, i), Utils.getColorAttrDefaultColor(context2, i), drawable, z, color);
    }

    public static KeyButtonDrawable create(Context context, Drawable drawable, boolean z) {
        return create(context, drawable, z, (Color) null);
    }

    private static KeyButtonDrawable create(Context context, Drawable drawable, boolean z, Color color) {
        return create(new ContextThemeWrapper(context, Utils.getThemeAttr(context, R$attr.lightIconTheme)), new ContextThemeWrapper(context, Utils.getThemeAttr(context, R$attr.darkIconTheme)), drawable, z, color);
    }

    private void regenerateBitmapIconCache() {
        int intrinsicWidth = getIntrinsicWidth();
        int intrinsicHeight = getIntrinsicHeight();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Drawable drawableMutate = this.mState.mChildState.newDrawable().mutate();
        setDrawableBounds(drawableMutate);
        canvas.save();
        if (this.mState.mHorizontalFlip) {
            canvas.scale(-1.0f, 1.0f, intrinsicWidth * 0.5f, intrinsicHeight * 0.5f);
        }
        drawableMutate.draw(canvas);
        canvas.restore();
        if (this.mState.mIsHardwareBitmap) {
            bitmapCreateBitmap = bitmapCreateBitmap.copy(Bitmap.Config.HARDWARE, false);
        }
        this.mState.mLastDrawnIcon = bitmapCreateBitmap;
    }

    private void regenerateBitmapShadowCache() {
        ShadowDrawableState shadowDrawableState = this.mState;
        if (shadowDrawableState.mShadowSize == 0) {
            shadowDrawableState.mLastDrawnIcon = null;
            return;
        }
        int intrinsicWidth = getIntrinsicWidth();
        int intrinsicHeight = getIntrinsicHeight();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Drawable drawableMutate = this.mState.mChildState.newDrawable().mutate();
        setDrawableBounds(drawableMutate);
        canvas.save();
        if (this.mState.mHorizontalFlip) {
            canvas.scale(-1.0f, 1.0f, intrinsicWidth * 0.5f, intrinsicHeight * 0.5f);
        }
        drawableMutate.draw(canvas);
        canvas.restore();
        Paint paint = new Paint(3);
        paint.setMaskFilter(new BlurMaskFilter(this.mState.mShadowSize, BlurMaskFilter.Blur.NORMAL));
        Bitmap bitmapExtractAlpha = bitmapCreateBitmap.extractAlpha(paint, new int[2]);
        paint.setMaskFilter(null);
        bitmapCreateBitmap.eraseColor(0);
        canvas.drawBitmap(bitmapExtractAlpha, r1[0], r1[1], paint);
        if (this.mState.mIsHardwareBitmap) {
            bitmapCreateBitmap = bitmapCreateBitmap.copy(Bitmap.Config.HARDWARE, false);
        }
        this.mState.mLastDrawnShadow = bitmapCreateBitmap;
    }

    private void setDrawableBounds(Drawable drawable) {
        ShadowDrawableState shadowDrawableState = this.mState;
        int iAbs = shadowDrawableState.mShadowSize + Math.abs(shadowDrawableState.mShadowOffsetX);
        ShadowDrawableState shadowDrawableState2 = this.mState;
        int iAbs2 = shadowDrawableState2.mShadowSize + Math.abs(shadowDrawableState2.mShadowOffsetY);
        drawable.setBounds(iAbs, iAbs2, getIntrinsicWidth() - iAbs, getIntrinsicHeight() - iAbs2);
    }

    private void updateShadowAlpha() {
        int iAlpha = Color.alpha(this.mState.mShadowColor);
        this.mShadowPaint.setAlpha(Math.round(iAlpha * (r4.mAlpha / 255.0f) * (1.0f - this.mState.mDarkIntensity)));
    }

    public boolean canAnimate() {
        return this.mState.mSupportsAnimation;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        return this.mState.canApplyTheme();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        if (bounds.isEmpty()) {
            return;
        }
        AnimatedVectorDrawable animatedVectorDrawable = this.mAnimatedDrawable;
        if (animatedVectorDrawable != null) {
            animatedVectorDrawable.draw(canvas);
            return;
        }
        boolean z = this.mState.mIsHardwareBitmap != canvas.isHardwareAccelerated();
        if (z) {
            this.mState.mIsHardwareBitmap = canvas.isHardwareAccelerated();
        }
        if (this.mState.mLastDrawnIcon == null || z) {
            regenerateBitmapIconCache();
        }
        canvas.save();
        ShadowDrawableState shadowDrawableState = this.mState;
        canvas.translate(shadowDrawableState.mTranslationX, shadowDrawableState.mTranslationY);
        canvas.rotate(this.mState.mRotateDegrees, getIntrinsicWidth() / 2, getIntrinsicHeight() / 2);
        ShadowDrawableState shadowDrawableState2 = this.mState;
        if (shadowDrawableState2.mShadowSize > 0) {
            if (shadowDrawableState2.mLastDrawnShadow == null || z) {
                regenerateBitmapShadowCache();
            }
            double d = (float) ((((double) this.mState.mRotateDegrees) * 3.141592653589793d) / 180.0d);
            double dSin = Math.sin(d) * ((double) this.mState.mShadowOffsetY);
            double dCos = Math.cos(d);
            ShadowDrawableState shadowDrawableState3 = this.mState;
            float f = ((float) (dSin + (dCos * ((double) shadowDrawableState3.mShadowOffsetX)))) - shadowDrawableState3.mTranslationX;
            double dCos2 = Math.cos(d) * ((double) this.mState.mShadowOffsetY);
            double dSin2 = Math.sin(d);
            ShadowDrawableState shadowDrawableState4 = this.mState;
            canvas.drawBitmap(shadowDrawableState4.mLastDrawnShadow, f, ((float) (dCos2 - (dSin2 * ((double) shadowDrawableState4.mShadowOffsetX)))) - shadowDrawableState4.mTranslationY, this.mShadowPaint);
        }
        canvas.drawBitmap(this.mState.mLastDrawnIcon, (Rect) null, bounds, this.mIconPaint);
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        return this.mState;
    }

    int getDrawableBackgroundColor() {
        return this.mState.mOvalBackgroundColor.toArgb();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        ShadowDrawableState shadowDrawableState = this.mState;
        return shadowDrawableState.mBaseHeight + ((shadowDrawableState.mShadowSize + Math.abs(shadowDrawableState.mShadowOffsetY)) * 2);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        ShadowDrawableState shadowDrawableState = this.mState;
        return shadowDrawableState.mBaseWidth + ((shadowDrawableState.mShadowSize + Math.abs(shadowDrawableState.mShadowOffsetX)) * 2);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public float getRotation() {
        return this.mState.mRotateDegrees;
    }

    public float getTranslationY() {
        return this.mState.mTranslationY;
    }

    boolean hasOvalBg() {
        return this.mState.mOvalBackgroundColor != null;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mState.mAlpha = i;
        this.mIconPaint.setAlpha(i);
        updateShadowAlpha();
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mIconPaint.setColorFilter(colorFilter);
        if (this.mAnimatedDrawable != null) {
            if (hasOvalBg()) {
                this.mAnimatedDrawable.setColorFilter(new PorterDuffColorFilter(this.mState.mLightColor, PorterDuff.Mode.SRC_IN));
            } else {
                this.mAnimatedDrawable.setColorFilter(colorFilter);
            }
        }
        invalidateSelf();
    }

    public void setDarkIntensity(float f) {
        this.mState.mDarkIntensity = f;
        int iIntValue = ((Integer) ArgbEvaluator.getInstance().evaluate(f, Integer.valueOf(this.mState.mLightColor), Integer.valueOf(this.mState.mDarkColor))).intValue();
        updateShadowAlpha();
        setColorFilter(new PorterDuffColorFilter(iIntValue, PorterDuff.Mode.SRC_ATOP));
    }

    public void setRotation(float f) {
        if (canAnimate()) {
            return;
        }
        ShadowDrawableState shadowDrawableState = this.mState;
        if (shadowDrawableState.mRotateDegrees != f) {
            shadowDrawableState.mRotateDegrees = f;
            invalidateSelf();
        }
    }

    public void setShadowProperties(int i, int i2, int i3, int i4) {
        if (canAnimate()) {
            return;
        }
        ShadowDrawableState shadowDrawableState = this.mState;
        if (shadowDrawableState.mShadowOffsetX == i && shadowDrawableState.mShadowOffsetY == i2 && shadowDrawableState.mShadowSize == i3 && shadowDrawableState.mShadowColor == i4) {
            return;
        }
        shadowDrawableState.mShadowOffsetX = i;
        shadowDrawableState.mShadowOffsetY = i2;
        shadowDrawableState.mShadowSize = i3;
        shadowDrawableState.mShadowColor = i4;
        this.mShadowPaint.setColorFilter(new PorterDuffColorFilter(this.mState.mShadowColor, PorterDuff.Mode.SRC_ATOP));
        updateShadowAlpha();
        invalidateSelf();
    }

    public void setTranslation(float f, float f2) {
        ShadowDrawableState shadowDrawableState = this.mState;
        if (shadowDrawableState.mTranslationX == f && shadowDrawableState.mTranslationY == f2) {
            return;
        }
        shadowDrawableState.mTranslationX = f;
        shadowDrawableState.mTranslationY = f2;
        invalidateSelf();
    }

    public void setTranslationY(float f) {
        setTranslation(this.mState.mTranslationX, f);
    }
}
