package com.android.launcher3.icons;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.FloatProperty;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

/* JADX INFO: loaded from: classes.dex */
public class FastBitmapDrawable extends Drawable implements Drawable.Callback {
    protected static final float HOVERED_SCALE = 1.1f;
    protected static final float PRESSED_SCALE = 1.1f;
    private Drawable mBadge;
    public final BitmapInfo mBitmapInfo;
    private boolean mChecked;
    private Drawable mCheckedIcon;
    private Path mCheckedShapePath;
    private ColorFilter mColorFilter;
    private boolean mIsCloneBadge;
    protected boolean mIsDisabled;
    protected boolean mIsHovered;
    protected boolean mIsPressed;
    protected ObjectAnimator mScaleAnimation;
    private static final Interpolator ACCEL = new AccelerateInterpolator();
    private static final Interpolator DEACCEL = new DecelerateInterpolator();
    private static final Interpolator HOVER_EMPHASIZED_DECELERATE_INTERPOLATOR = new PathInterpolator(0.05f, 0.7f, 0.1f, 1.0f);
    private static boolean sFlagHoverEnabled = false;
    protected static final FloatProperty SCALE = new FloatProperty("scale") { // from class: com.android.launcher3.icons.FastBitmapDrawable.1
        @Override // android.util.Property
        public Float get(FastBitmapDrawable fastBitmapDrawable) {
            return Float.valueOf(fastBitmapDrawable.mScale);
        }

        @Override // android.util.FloatProperty
        public void setValue(FastBitmapDrawable fastBitmapDrawable, float f) {
            fastBitmapDrawable.mScale = f;
            fastBitmapDrawable.invalidateSelf();
        }
    };
    protected final Paint mPaint = new Paint(3);
    public float mDisabledAlpha = 1.0f;
    int mCreationFlags = 0;
    private int mCheckedColor = 0;
    private float mScale = 1.0f;
    private int mAlpha = 255;
    private boolean mShowPressAnim = true;

    public class FastBitmapConstantState extends Drawable.ConstantState {
        private Drawable.ConstantState mBadgeConstantState;
        protected final BitmapInfo mBitmapInfo;
        int mCreationFlags;
        private boolean mIsCloneBadge;
        protected boolean mIsDisabled;

        public FastBitmapConstantState(Bitmap bitmap, int i) {
            this(BitmapInfo.of(bitmap, i));
        }

        public FastBitmapConstantState(BitmapInfo bitmapInfo) {
            this.mCreationFlags = 0;
            this.mBitmapInfo = bitmapInfo;
        }

        protected FastBitmapDrawable createDrawable() {
            return new FastBitmapDrawable(this.mBitmapInfo);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return 0;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final FastBitmapDrawable newDrawable() {
            FastBitmapDrawable fastBitmapDrawableCreateDrawable = createDrawable();
            fastBitmapDrawableCreateDrawable.setIsDisabled(this.mIsDisabled);
            Drawable.ConstantState constantState = this.mBadgeConstantState;
            if (constantState != null) {
                if (this.mIsCloneBadge) {
                    fastBitmapDrawableCreateDrawable.setCloneBadge(constantState.newDrawable().mutate());
                } else {
                    fastBitmapDrawableCreateDrawable.setBadge(constantState.newDrawable());
                }
            }
            fastBitmapDrawableCreateDrawable.mCreationFlags = this.mCreationFlags;
            return fastBitmapDrawableCreateDrawable;
        }
    }

    public FastBitmapDrawable(BitmapInfo bitmapInfo) {
        this.mBitmapInfo = bitmapInfo;
        setFilterBitmap(true);
    }

    protected static final int getDisabledColor(int i) {
        int iMin = Math.min(Math.round(((((Color.red(i) + Color.green(i)) + Color.blue(i)) / 3) * 0.5f) + 127), 255);
        return Color.rgb(iMin, iMin, iMin);
    }

    public static ColorFilter getDisabledColorFilter() {
        return getDisabledColorFilter(1.0f);
    }

    private static ColorFilter getDisabledColorFilter(float f) {
        ColorMatrix colorMatrix = new ColorMatrix();
        ColorMatrix colorMatrix2 = new ColorMatrix();
        colorMatrix2.setSaturation(0.0f);
        float[] array = colorMatrix.getArray();
        array[0] = 0.5f;
        array[6] = 0.5f;
        array[12] = 0.5f;
        float f2 = 127;
        array[4] = f2;
        array[9] = f2;
        array[14] = f2;
        array[18] = f;
        colorMatrix2.preConcat(colorMatrix);
        return new ColorMatrixColorFilter(colorMatrix2);
    }

    public static void setBadgeBounds(Drawable drawable, Rect rect) {
        int badgeSizeForIconSize = BaseIconFactory.getBadgeSizeForIconSize(rect.width());
        int i = rect.right;
        int i2 = rect.bottom;
        drawable.setBounds(i - badgeSizeForIconSize, i2 - badgeSizeForIconSize, i, i2);
    }

    private void updateBadgeBounds(Rect rect) {
        Drawable drawable = this.mBadge;
        if (drawable != null) {
            setBadgeBounds(drawable, rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        if (this.mScale == 1.0f) {
            Rect bounds = getBounds();
            drawInternal(canvas, bounds);
            drawCheckMark(canvas, bounds);
            Drawable drawable = this.mBadge;
            if (drawable != null) {
                if (this.mIsCloneBadge) {
                    drawable.setBounds(bounds);
                }
                this.mBadge.draw(canvas);
                return;
            }
            return;
        }
        int iSave = canvas.save();
        Rect bounds2 = getBounds();
        float f = this.mScale;
        canvas.scale(f, f, bounds2.exactCenterX(), bounds2.exactCenterY());
        drawInternal(canvas, bounds2);
        drawCheckMark(canvas, bounds2);
        Drawable drawable2 = this.mBadge;
        if (drawable2 != null) {
            if (this.mIsCloneBadge) {
                drawable2.setBounds(bounds2);
            }
            this.mBadge.draw(canvas);
        }
        canvas.restoreToCount(iSave);
    }

    public void drawCheckMark(Canvas canvas, Rect rect) {
        if (!this.mChecked || this.mCheckedIcon == null || this.mCheckedShapePath == null || this.mCheckedColor == 0) {
            return;
        }
        int iSave = canvas.save();
        float f = ExtendedBitmapInfo.sScale;
        canvas.scale(f, f, rect.exactCenterX(), rect.exactCenterY());
        canvas.translate(rect.left, rect.top);
        canvas.scale(rect.width() / 100.0f, rect.height() / 100.0f);
        Paint paint = new Paint(3);
        paint.setColor(this.mCheckedColor);
        canvas.drawPath(this.mCheckedShapePath, paint);
        canvas.restoreToCount(iSave);
        int iMin = (int) (Math.min(rect.width(), rect.height()) / 2.0f);
        int iWidth = (rect.width() - iMin) / 2;
        int iHeight = (rect.height() - iMin) / 2;
        this.mCheckedIcon.setBounds(iWidth, iHeight, iWidth + iMin, iMin + iHeight);
        this.mCheckedIcon.draw(canvas);
    }

    protected void drawInternal(Canvas canvas, Rect rect) {
        try {
            canvas.drawBitmap(this.mBitmapInfo.icon, (Rect) null, rect, this.mPaint);
        } catch (IllegalArgumentException unused) {
            canvas.drawBitmap(this.mBitmapInfo.icon.copy(Bitmap.Config.ARGB_4444, false), (Rect) null, rect, this.mPaint);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mAlpha;
    }

    public Drawable getBadge() {
        return this.mBadge;
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        FastBitmapConstantState fastBitmapConstantStateNewConstantState = newConstantState();
        fastBitmapConstantStateNewConstantState.mIsDisabled = this.mIsDisabled;
        Drawable drawable = this.mBadge;
        if (drawable != null) {
            fastBitmapConstantStateNewConstantState.mBadgeConstantState = drawable.getConstantState();
            fastBitmapConstantStateNewConstantState.mIsCloneBadge = this.mIsCloneBadge;
        }
        fastBitmapConstantStateNewConstantState.mCreationFlags = this.mCreationFlags;
        return fastBitmapConstantStateNewConstantState;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mBitmapInfo.icon.getHeight();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mBitmapInfo.icon.getWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumHeight() {
        return getBounds().height();
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumWidth() {
        return getBounds().width();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        if (drawable == this.mBadge) {
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    protected FastBitmapConstantState newConstantState() {
        return new FastBitmapConstantState(this.mBitmapInfo);
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        updateBadgeBounds(rect);
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        boolean z;
        int length = iArr.length;
        int i = 0;
        boolean z2 = false;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            }
            int i2 = iArr[i];
            if (i2 == 16842919) {
                z = true;
                break;
            }
            if (sFlagHoverEnabled && i2 == 16843623) {
                z2 = true;
            }
            i++;
        }
        if ((this.mIsPressed == z && this.mIsHovered == z2) || !this.mShowPressAnim) {
            return false;
        }
        ObjectAnimator objectAnimator = this.mScaleAnimation;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        float f = 1.1f;
        if (!z && !z2) {
            f = 1.0f;
        }
        if (this.mScale != f) {
            if (isVisible()) {
                boolean z3 = this.mIsPressed;
                Interpolator interpolator = z != z3 ? z ? ACCEL : DEACCEL : HOVER_EMPHASIZED_DECELERATE_INTERPOLATOR;
                int i3 = z != z3 ? 200 : 300;
                ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, SCALE, f);
                this.mScaleAnimation = objectAnimatorOfFloat;
                objectAnimatorOfFloat.setDuration(i3);
                this.mScaleAnimation.setInterpolator(interpolator);
                this.mScaleAnimation.start();
            } else {
                this.mScale = f;
                invalidateSelf();
            }
        }
        this.mIsPressed = z;
        this.mIsHovered = z2;
        return true;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        if (drawable == this.mBadge) {
            scheduleSelf(runnable, j);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (this.mAlpha != i) {
            this.mAlpha = i;
            this.mPaint.setAlpha(i);
            invalidateSelf();
            Drawable drawable = this.mBadge;
            if (drawable != null) {
                drawable.setAlpha(i);
            }
        }
    }

    public void setBadge(Drawable drawable) {
        Drawable drawable2 = this.mBadge;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.mBadge = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        updateBadgeBounds(getBounds());
        updateFilter();
    }

    public void setCloneBadge(Drawable drawable) {
        this.mIsCloneBadge = true;
        Drawable drawable2 = this.mBadge;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.mBadge = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        updateFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mColorFilter = colorFilter;
        updateFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public void setFilterBitmap(boolean z) {
        this.mPaint.setFilterBitmap(z);
        this.mPaint.setAntiAlias(z);
    }

    public void setIsDisabled(boolean z) {
        if (this.mIsDisabled != z) {
            this.mIsDisabled = z;
            updateFilter();
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        unscheduleSelf(runnable);
    }

    protected void updateFilter() {
        this.mPaint.setColorFilter(this.mIsDisabled ? getDisabledColorFilter(this.mDisabledAlpha) : this.mColorFilter);
        Drawable drawable = this.mBadge;
        if (drawable != null) {
            drawable.setColorFilter(getColorFilter());
        }
        invalidateSelf();
    }
}
