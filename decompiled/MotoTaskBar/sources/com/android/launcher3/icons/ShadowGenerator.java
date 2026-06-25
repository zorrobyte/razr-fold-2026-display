package com.android.launcher3.icons;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

/* JADX INFO: loaded from: classes.dex */
public class ShadowGenerator {
    private final BlurMaskFilter mDefaultBlurMaskFilter;
    private final int mIconSize;
    private final Paint mBlurPaint = new Paint(3);
    private final Paint mDrawPaint = new Paint(3);

    public ShadowGenerator(int i) {
        this.mIconSize = i;
        this.mDefaultBlurMaskFilter = new BlurMaskFilter(i * 0.035f, BlurMaskFilter.Blur.NORMAL);
    }

    public static float getScaleForBounds(RectF rectF) {
        float fMin = Math.min(Math.min(rectF.left, rectF.right), rectF.top);
        float f = fMin < 0.035f ? 0.465f / (0.5f - fMin) : 1.0f;
        float f2 = rectF.bottom;
        return f2 < 0.035f ? Math.min(f, 0.465f / (0.5f - f2)) : f;
    }

    void addPathShadow(Path path, Canvas canvas) {
        this.mDrawPaint.setMaskFilter(this.mDefaultBlurMaskFilter);
        this.mDrawPaint.setAlpha(25);
        canvas.drawPath(path, this.mDrawPaint);
        int iSave = canvas.save();
        this.mDrawPaint.setAlpha(7);
        canvas.translate(0.0f, this.mIconSize * 0.020833334f);
        canvas.drawPath(path, this.mDrawPaint);
        canvas.restoreToCount(iSave);
        this.mDrawPaint.setMaskFilter(null);
    }

    public synchronized void drawShadow(Bitmap bitmap, Canvas canvas) {
        this.mBlurPaint.setMaskFilter(this.mDefaultBlurMaskFilter);
        Bitmap bitmapExtractAlpha = bitmap.extractAlpha(this.mBlurPaint, new int[2]);
        this.mDrawPaint.setAlpha(25);
        canvas.drawBitmap(bitmapExtractAlpha, r0[0], r0[1], this.mDrawPaint);
        this.mDrawPaint.setAlpha(7);
        canvas.drawBitmap(bitmapExtractAlpha, r0[0], r0[1] + (this.mIconSize * 0.020833334f), this.mDrawPaint);
    }

    public synchronized void recreateIcon(Bitmap bitmap, Canvas canvas) {
        drawShadow(bitmap, canvas);
        this.mDrawPaint.setAlpha(255);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.mDrawPaint);
    }
}
