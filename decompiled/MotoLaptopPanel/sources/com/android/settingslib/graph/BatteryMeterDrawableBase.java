package com.android.settingslib.graph;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/* JADX INFO: loaded from: classes.dex */
public abstract class BatteryMeterDrawableBase extends Drawable {
    public static final String TAG = BatteryMeterDrawableBase.class.getSimpleName();
    protected final Paint mBatteryPaint;
    private final RectF mBoltFrame;
    protected final Paint mBoltPaint;
    private final Path mBoltPath;
    private final float[] mBoltPoints;
    private final RectF mButtonFrame;
    protected float mButtonHeightFraction;
    private int mChargeColor;
    private boolean mCharging;
    private final int[] mColors;
    private final int mCriticalLevel;
    private final RectF mFrame;
    protected final Paint mFramePaint;
    private int mHeight;
    private int mIconTint;
    private final int mIntrinsicHeight;
    private final int mIntrinsicWidth;
    private int mLevel;
    private final Path mOutlinePath;
    private final Rect mPadding;
    private final RectF mPlusFrame;
    protected final Paint mPlusPaint;
    private final Path mPlusPath;
    private final float[] mPlusPoints;
    protected boolean mPowerSaveAsColorError;
    private boolean mPowerSaveEnabled;
    protected final Paint mPowersavePaint;
    private final Path mShapePath;
    private boolean mShowPercent;
    private float mTextHeight;
    protected final Paint mTextPaint;
    private final Path mTextPath;
    private String mWarningString;
    private float mWarningTextHeight;
    protected final Paint mWarningTextPaint;
    private int mWidth;

    private int getColorForLevel(int i) {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int[] iArr = this.mColors;
            if (i2 >= iArr.length) {
                return i3;
            }
            int i4 = iArr[i2];
            int i5 = iArr[i2 + 1];
            if (i <= i4) {
                return i2 == iArr.length + (-2) ? this.mIconTint : i5;
            }
            i2 += 2;
            i3 = i5;
        }
    }

    private void updateSize() {
        Rect bounds = getBounds();
        int i = bounds.bottom;
        Rect rect = this.mPadding;
        int i2 = (i - rect.bottom) - (bounds.top + rect.top);
        this.mHeight = i2;
        this.mWidth = (bounds.right - rect.right) - (bounds.left + rect.left);
        this.mWarningTextPaint.setTextSize(i2 * 0.75f);
        this.mWarningTextHeight = -this.mWarningTextPaint.getFontMetrics().ascent;
    }

    protected int batteryColorForLevel(int i) {
        return (this.mCharging || (this.mPowerSaveEnabled && this.mPowerSaveAsColorError)) ? this.mChargeColor : getColorForLevel(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        float fHeight;
        boolean z;
        float[] fArr;
        String strValueOf;
        float f;
        boolean z2;
        float[] fArr2;
        int i = this.mLevel;
        Rect bounds = getBounds();
        if (i == -1) {
            return;
        }
        float f2 = i / 100.0f;
        int i2 = this.mHeight;
        int aspectRatio = (int) (getAspectRatio() * this.mHeight);
        int i3 = (this.mWidth - aspectRatio) / 2;
        float f3 = i2;
        int iRound = Math.round(this.mButtonHeightFraction * f3);
        Rect rect = this.mPadding;
        int i4 = rect.left + bounds.left;
        float f4 = i4;
        float f5 = (bounds.bottom - rect.bottom) - i2;
        this.mFrame.set(f4, f5, i4 + aspectRatio, i2 + r3);
        float f6 = i3;
        float f7 = 0.0f;
        this.mFrame.offset(f6, 0.0f);
        RectF rectF = this.mButtonFrame;
        float f8 = aspectRatio * 0.28f;
        float fRound = this.mFrame.left + Math.round(f8);
        RectF rectF2 = this.mFrame;
        float f9 = iRound;
        rectF.set(fRound, rectF2.top, rectF2.right - Math.round(f8), this.mFrame.top + f9);
        this.mFrame.top += f9;
        this.mBatteryPaint.setColor(batteryColorForLevel(i));
        if (i >= 96) {
            f2 = 1.0f;
        } else if (i <= this.mCriticalLevel) {
            f2 = 0.0f;
        }
        if (f2 == 1.0f) {
            fHeight = this.mButtonFrame.top;
        } else {
            RectF rectF3 = this.mFrame;
            fHeight = (rectF3.height() * (1.0f - f2)) + rectF3.top;
        }
        this.mShapePath.reset();
        this.mOutlinePath.reset();
        float radiusRatio = getRadiusRatio() * (this.mFrame.height() + f9);
        this.mShapePath.setFillType(Path.FillType.WINDING);
        Path path = this.mShapePath;
        RectF rectF4 = this.mFrame;
        Path.Direction direction = Path.Direction.CW;
        path.addRoundRect(rectF4, radiusRatio, radiusRatio, direction);
        this.mShapePath.addRect(this.mButtonFrame, direction);
        this.mOutlinePath.addRoundRect(this.mFrame, radiusRatio, radiusRatio, direction);
        Path path2 = new Path();
        path2.addRect(this.mButtonFrame, direction);
        this.mOutlinePath.op(path2, Path.Op.XOR);
        if (this.mCharging) {
            RectF rectF5 = this.mFrame;
            float fWidth = rectF5.left + (rectF5.width() / 4.0f) + 1.0f;
            RectF rectF6 = this.mFrame;
            float fHeight2 = rectF6.top + (rectF6.height() / 6.0f);
            RectF rectF7 = this.mFrame;
            z = false;
            float fWidth2 = (rectF7.right - (rectF7.width() / 4.0f)) + 1.0f;
            RectF rectF8 = this.mFrame;
            float fHeight3 = rectF8.bottom - (rectF8.height() / 10.0f);
            RectF rectF9 = this.mBoltFrame;
            if (rectF9.left != fWidth || rectF9.top != fHeight2 || rectF9.right != fWidth2 || rectF9.bottom != fHeight3) {
                rectF9.set(fWidth, fHeight2, fWidth2, fHeight3);
                this.mBoltPath.reset();
                Path path3 = this.mBoltPath;
                RectF rectF10 = this.mBoltFrame;
                float fWidth3 = rectF10.left + (this.mBoltPoints[0] * rectF10.width());
                RectF rectF11 = this.mBoltFrame;
                path3.moveTo(fWidth3, rectF11.top + (this.mBoltPoints[1] * rectF11.height()));
                int i5 = 2;
                while (true) {
                    fArr2 = this.mBoltPoints;
                    if (i5 >= fArr2.length) {
                        break;
                    }
                    Path path4 = this.mBoltPath;
                    RectF rectF12 = this.mBoltFrame;
                    float fWidth4 = rectF12.left + (fArr2[i5] * rectF12.width());
                    RectF rectF13 = this.mBoltFrame;
                    path4.lineTo(fWidth4, rectF13.top + (this.mBoltPoints[i5 + 1] * rectF13.height()));
                    i5 += 2;
                }
                Path path5 = this.mBoltPath;
                RectF rectF14 = this.mBoltFrame;
                float fWidth5 = rectF14.left + (fArr2[0] * rectF14.width());
                RectF rectF15 = this.mBoltFrame;
                path5.lineTo(fWidth5, rectF15.top + (this.mBoltPoints[1] * rectF15.height()));
            }
            RectF rectF16 = this.mBoltFrame;
            float f10 = rectF16.bottom;
            if (Math.min(Math.max((f10 - fHeight) / (f10 - rectF16.top), 0.0f), 1.0f) <= 0.3f) {
                canvas.drawPath(this.mBoltPath, this.mBoltPaint);
            } else {
                this.mShapePath.op(this.mBoltPath, Path.Op.DIFFERENCE);
            }
        } else {
            z = false;
            if (this.mPowerSaveEnabled) {
                float fWidth6 = (this.mFrame.width() * 2.0f) / 3.0f;
                RectF rectF17 = this.mFrame;
                float fWidth7 = rectF17.left + ((rectF17.width() - fWidth6) / 2.0f);
                RectF rectF18 = this.mFrame;
                float fHeight4 = rectF18.top + ((rectF18.height() - fWidth6) / 2.0f);
                RectF rectF19 = this.mFrame;
                float fWidth8 = rectF19.right - ((rectF19.width() - fWidth6) / 2.0f);
                RectF rectF20 = this.mFrame;
                float fHeight5 = rectF20.bottom - ((rectF20.height() - fWidth6) / 2.0f);
                RectF rectF21 = this.mPlusFrame;
                if (rectF21.left != fWidth7 || rectF21.top != fHeight4 || rectF21.right != fWidth8 || rectF21.bottom != fHeight5) {
                    rectF21.set(fWidth7, fHeight4, fWidth8, fHeight5);
                    this.mPlusPath.reset();
                    Path path6 = this.mPlusPath;
                    RectF rectF22 = this.mPlusFrame;
                    float fWidth9 = rectF22.left + (this.mPlusPoints[0] * rectF22.width());
                    RectF rectF23 = this.mPlusFrame;
                    path6.moveTo(fWidth9, rectF23.top + (this.mPlusPoints[1] * rectF23.height()));
                    int i6 = 2;
                    while (true) {
                        fArr = this.mPlusPoints;
                        if (i6 >= fArr.length) {
                            break;
                        }
                        Path path7 = this.mPlusPath;
                        RectF rectF24 = this.mPlusFrame;
                        float fWidth10 = rectF24.left + (fArr[i6] * rectF24.width());
                        RectF rectF25 = this.mPlusFrame;
                        path7.lineTo(fWidth10, rectF25.top + (this.mPlusPoints[i6 + 1] * rectF25.height()));
                        i6 += 2;
                    }
                    Path path8 = this.mPlusPath;
                    RectF rectF26 = this.mPlusFrame;
                    float fWidth11 = rectF26.left + (fArr[0] * rectF26.width());
                    RectF rectF27 = this.mPlusFrame;
                    path8.lineTo(fWidth11, rectF27.top + (this.mPlusPoints[1] * rectF27.height()));
                }
                this.mShapePath.op(this.mPlusPath, Path.Op.DIFFERENCE);
                if (this.mPowerSaveAsColorError) {
                    canvas.drawPath(this.mPlusPath, this.mPlusPaint);
                }
            }
        }
        if (this.mCharging || this.mPowerSaveEnabled || i <= this.mCriticalLevel || !this.mShowPercent) {
            strValueOf = null;
            f = 0.0f;
            z2 = z;
        } else {
            this.mTextPaint.setColor(getColorForLevel(i));
            this.mTextPaint.setTextSize(f3 * (this.mLevel == 100 ? 0.38f : 0.5f));
            this.mTextHeight = -this.mTextPaint.getFontMetrics().ascent;
            strValueOf = String.valueOf(i);
            float f11 = (this.mWidth * 0.5f) + f4;
            float f12 = ((this.mHeight + this.mTextHeight) * 0.47f) + f5;
            z2 = fHeight > f12 ? true : z;
            if (!z2) {
                this.mTextPath.reset();
                this.mTextPaint.getTextPath(strValueOf, 0, strValueOf.length(), f11, f12, this.mTextPath);
                this.mShapePath.op(this.mTextPath, Path.Op.DIFFERENCE);
            }
            f7 = f11;
            f = f12;
        }
        String str = strValueOf;
        canvas.drawPath(this.mShapePath, this.mFramePaint);
        this.mFrame.top = fHeight;
        canvas.save();
        canvas.clipRect(this.mFrame);
        canvas.drawPath(this.mShapePath, this.mBatteryPaint);
        canvas.restore();
        if (!this.mCharging && !this.mPowerSaveEnabled) {
            if (i <= this.mCriticalLevel) {
                canvas.drawText(this.mWarningString, (this.mWidth * 0.5f) + f4, ((this.mHeight + this.mWarningTextHeight) * 0.48f) + f5, this.mWarningTextPaint);
            } else if (z2) {
                canvas.drawText(str, f7, f, this.mTextPaint);
            }
        }
        if (!this.mCharging && this.mPowerSaveEnabled && this.mPowerSaveAsColorError) {
            canvas.drawPath(this.mOutlinePath, this.mPowersavePaint);
        }
    }

    protected abstract float getAspectRatio();

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mIntrinsicHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mIntrinsicWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(Rect rect) {
        Rect rect2 = this.mPadding;
        if (rect2.left == 0 && rect2.top == 0 && rect2.right == 0 && rect2.bottom == 0) {
            return super.getPadding(rect);
        }
        rect.set(rect2);
        return true;
    }

    protected abstract float getRadiusRatio();

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(int i, int i2, int i3, int i4) {
        super.setBounds(i, i2, i3, i4);
        updateSize();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mFramePaint.setColorFilter(colorFilter);
        this.mBatteryPaint.setColorFilter(colorFilter);
        this.mWarningTextPaint.setColorFilter(colorFilter);
        this.mBoltPaint.setColorFilter(colorFilter);
        this.mPlusPaint.setColorFilter(colorFilter);
    }
}
