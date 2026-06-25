package com.android.launcher3.icons;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;

/* JADX INFO: loaded from: classes.dex */
public class UserBadgeDrawable extends DrawableWrapper {
    private final int mBaseColor;
    private final int mBgColor;
    public final boolean mIsThemed;
    private final Paint mPaint;
    private boolean mShouldDrawBackground;

    class MyConstantState extends Drawable.ConstantState {
        private final Drawable.ConstantState mBase;
        private final int mBaseColor;
        private final int mBgColor;
        private final boolean mShouldDrawBackground;

        MyConstantState(Drawable.ConstantState constantState, int i, int i2, boolean z) {
            this.mBase = constantState;
            this.mBgColor = i;
            this.mBaseColor = i2;
            this.mShouldDrawBackground = z;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mBase.getChangingConfigurations();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new UserBadgeDrawable(this.mBase.newDrawable(), this.mBgColor, this.mBaseColor, this.mShouldDrawBackground);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new UserBadgeDrawable(this.mBase.newDrawable(resources), this.mBgColor, this.mBaseColor, this.mShouldDrawBackground);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources, Resources.Theme theme) {
            return new UserBadgeDrawable(this.mBase.newDrawable(resources, theme), this.mBgColor, this.mBaseColor, this.mShouldDrawBackground);
        }
    }

    public UserBadgeDrawable(Context context, int i, int i2, boolean z) {
        super(context.getDrawable(i));
        this.mPaint = new Paint(1);
        this.mShouldDrawBackground = true;
        this.mIsThemed = z;
        if (z) {
            mutate();
            this.mBaseColor = context.getColor(R$color.themed_badge_icon_color);
            this.mBgColor = context.getColor(R$color.themed_badge_icon_background_color);
        } else {
            this.mBaseColor = context.getColor(i2);
            this.mBgColor = -1;
        }
        setTint(this.mBaseColor);
    }

    private UserBadgeDrawable(Drawable drawable, int i, int i2, boolean z) {
        super(drawable);
        this.mPaint = new Paint(1);
        this.mIsThemed = false;
        this.mBgColor = i;
        this.mBaseColor = i2;
        this.mShouldDrawBackground = z;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (this.mShouldDrawBackground) {
            Rect bounds = getBounds();
            int iSave = canvas.save();
            canvas.translate(bounds.left, bounds.top);
            canvas.scale(bounds.width() / 24.0f, bounds.height() / 24.0f);
            this.mPaint.setColor(285212672);
            canvas.drawCircle(12.0f, 12.25f, 11.5f, this.mPaint);
            this.mPaint.setColor(this.mBgColor);
            canvas.drawCircle(12.0f, 12.0f, 11.0f, this.mPaint);
            canvas.restoreToCount(iSave);
        }
        super.draw(canvas);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        return new MyConstantState(getDrawable().getConstantState(), this.mBgColor, this.mBaseColor, this.mShouldDrawBackground);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        if (colorFilter == null) {
            super.setTint(this.mBaseColor);
            return;
        }
        if (!(colorFilter instanceof ColorMatrixColorFilter)) {
            Paint paint = new Paint();
            paint.setColorFilter(colorFilter);
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            new Canvas(bitmapCreateBitmap).drawPaint(paint);
            super.setTint(bitmapCreateBitmap.getPixel(0, 0));
            return;
        }
        ColorMatrix colorMatrix = new ColorMatrix();
        ((ColorMatrixColorFilter) colorFilter).getColorMatrix(colorMatrix);
        ColorMatrix colorMatrix2 = new ColorMatrix();
        float[] array = colorMatrix2.getArray();
        array[0] = Color.red(this.mBaseColor) / 255.0f;
        array[6] = Color.green(this.mBaseColor) / 255.0f;
        array[12] = Color.blue(this.mBaseColor) / 255.0f;
        array[18] = Color.alpha(this.mBaseColor) / 255.0f;
        colorMatrix2.postConcat(colorMatrix);
        super.setColorFilter(new ColorMatrixColorFilter(colorMatrix2));
    }
}
