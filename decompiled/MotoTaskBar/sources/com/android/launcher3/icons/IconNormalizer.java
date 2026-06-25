package com.android.launcher3.icons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import com.android.systemui.plugins.DarkIconDispatcher;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public class IconNormalizer {
    private final RectF mAdaptiveIconBounds;
    private float mAdaptiveIconScale;
    private final Bitmap mBitmap;
    private final Rect mBounds;
    private final Canvas mCanvas;
    private boolean mEnableShapeDetection;
    private final float[] mLeftBorder;
    private final Matrix mMatrix;
    private final int mMaxSize;
    private final Paint mPaintMaskShape;
    private final Paint mPaintMaskShapeOutline;
    private final byte[] mPixels;
    private final float[] mRightBorder;
    private final Path mShapePath;

    IconNormalizer(Context context, int i, boolean z) {
        int i2 = i * 2;
        this.mMaxSize = i2;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i2, i2, Bitmap.Config.ALPHA_8);
        this.mBitmap = bitmapCreateBitmap;
        this.mCanvas = new Canvas(bitmapCreateBitmap);
        this.mPixels = new byte[i2 * i2];
        this.mLeftBorder = new float[i2];
        this.mRightBorder = new float[i2];
        this.mBounds = new Rect();
        this.mAdaptiveIconBounds = new RectF();
        Paint paint = new Paint();
        this.mPaintMaskShape = paint;
        paint.setColor(-65536);
        paint.setStyle(Paint.Style.FILL);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        Paint paint2 = new Paint();
        this.mPaintMaskShapeOutline = paint2;
        paint2.setStrokeWidth(context.getResources().getDisplayMetrics().density * 2.0f);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setColor(DarkIconDispatcher.DEFAULT_INVERSE_ICON_TINT);
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.mShapePath = new Path();
        this.mMatrix = new Matrix();
        this.mAdaptiveIconScale = 0.0f;
        this.mEnableShapeDetection = z;
    }

    private static void convertToConvexArray(float[] fArr, int i, int i2, int i3) {
        float[] fArr2 = new float[fArr.length - 1];
        int i4 = -1;
        float f = Float.MAX_VALUE;
        for (int i5 = i2 + 1; i5 <= i3; i5++) {
            float f2 = fArr[i5];
            if (f2 > -1.0f) {
                if (f == Float.MAX_VALUE) {
                    i4 = i2;
                } else {
                    float f3 = ((f2 - fArr[i4]) / (i5 - i4)) - f;
                    float f4 = i;
                    if (f3 * f4 < 0.0f) {
                        while (i4 > i2) {
                            i4--;
                            if ((((fArr[i5] - fArr[i4]) / (i5 - i4)) - fArr2[i4]) * f4 >= 0.0f) {
                                break;
                            }
                        }
                    }
                }
                f = (fArr[i5] - fArr[i4]) / (i5 - i4);
                for (int i6 = i4; i6 < i5; i6++) {
                    fArr2[i6] = f;
                    fArr[i6] = fArr[i4] + ((i6 - i4) * f);
                }
                i4 = i5;
            }
        }
    }

    private static float getScale(float f, float f2, float f3) {
        float f4 = f / f2;
        if (f / f3 > (f4 < 0.7853982f ? 0.6597222f : ((1.0f - f4) * 0.040449437f) + 0.6510417f)) {
            return (float) Math.sqrt(r3 / r2);
        }
        return 1.0f;
    }

    private boolean isShape(Path path) {
        if (Math.abs((this.mBounds.width() / this.mBounds.height()) - 1.0f) > 0.05f) {
            return false;
        }
        this.mMatrix.reset();
        this.mMatrix.setScale(this.mBounds.width(), this.mBounds.height());
        Matrix matrix = this.mMatrix;
        Rect rect = this.mBounds;
        matrix.postTranslate(rect.left, rect.top);
        path.transform(this.mMatrix, this.mShapePath);
        this.mCanvas.drawPath(this.mShapePath, this.mPaintMaskShape);
        this.mCanvas.drawPath(this.mShapePath, this.mPaintMaskShapeOutline);
        return isTransparentBitmap();
    }

    private boolean isTransparentBitmap() {
        Rect rect;
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(this.mPixels);
        byteBufferWrap.rewind();
        this.mBitmap.copyPixelsToBuffer(byteBufferWrap);
        Rect rect2 = this.mBounds;
        int i = rect2.top;
        int i2 = this.mMaxSize;
        int i3 = i * i2;
        int i4 = i2 - rect2.right;
        int i5 = 0;
        while (true) {
            rect = this.mBounds;
            if (i >= rect.bottom) {
                break;
            }
            int i6 = rect.left;
            int i7 = i3 + i6;
            while (i6 < this.mBounds.right) {
                if ((this.mPixels[i7] & 255) > 40) {
                    i5++;
                }
                i7++;
                i6++;
            }
            i3 = i7 + i4;
            i++;
        }
        return ((float) i5) / ((float) (rect.width() * this.mBounds.height())) < 0.005f;
    }

    public static float normalizeAdaptiveIcon(Drawable drawable, int i, RectF rectF) {
        Rect rect = new Rect(drawable.getBounds());
        drawable.setBounds(0, 0, i, i);
        Path iconMask = drawable instanceof DynamicAdaptiveIconDrawable ? ((DynamicAdaptiveIconDrawable) drawable).getIconMask() : ((AdaptiveIconDrawable) drawable).getIconMask();
        Region region = new Region();
        region.setPath(iconMask, new Region(0, 0, i, i));
        Rect bounds = region.getBounds();
        int area = GraphicsUtils.getArea(region);
        if (rectF != null) {
            float f = i;
            rectF.set(bounds.left / f, bounds.top / f, 1.0f - (bounds.right / f), 1.0f - (bounds.bottom / f));
        }
        drawable.setBounds(rect);
        float f2 = area;
        return getScale(f2, f2, i * i);
    }

    public synchronized float getScale(Drawable drawable, RectF rectF, Path path, boolean[] zArr) {
        try {
            if (!(drawable instanceof AdaptiveIconDrawable) && !(drawable instanceof DynamicAdaptiveIconDrawable)) {
                int intrinsicWidth = drawable.getIntrinsicWidth();
                int intrinsicHeight = drawable.getIntrinsicHeight();
                if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
                    if (intrinsicWidth <= 0 || intrinsicWidth > this.mMaxSize) {
                        intrinsicWidth = this.mMaxSize;
                    }
                    if (intrinsicHeight <= 0 || intrinsicHeight > this.mMaxSize) {
                        intrinsicHeight = this.mMaxSize;
                    }
                } else {
                    int i = this.mMaxSize;
                    if (intrinsicWidth > i || intrinsicHeight > i) {
                        int iMax = Math.max(intrinsicWidth, intrinsicHeight);
                        int i2 = this.mMaxSize;
                        intrinsicWidth = (intrinsicWidth * i2) / iMax;
                        intrinsicHeight = (i2 * intrinsicHeight) / iMax;
                    }
                }
                int i3 = 0;
                this.mBitmap.eraseColor(0);
                drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
                drawable.draw(this.mCanvas);
                ByteBuffer byteBufferWrap = ByteBuffer.wrap(this.mPixels);
                byteBufferWrap.rewind();
                this.mBitmap.copyPixelsToBuffer(byteBufferWrap);
                int i4 = this.mMaxSize;
                int i5 = i4 + 1;
                int i6 = i4 - intrinsicWidth;
                int i7 = 0;
                int i8 = 0;
                int i9 = -1;
                int iMax2 = -1;
                int i10 = -1;
                while (i7 < intrinsicHeight) {
                    int i11 = i3;
                    int i12 = -1;
                    int i13 = -1;
                    while (i11 < intrinsicWidth) {
                        int i14 = i6;
                        if ((this.mPixels[i8] & 255) > 40) {
                            if (i13 == -1) {
                                i13 = i11;
                            }
                            i12 = i11;
                        }
                        i8++;
                        i11++;
                        i6 = i14;
                    }
                    int i15 = i6;
                    i8 += i15;
                    this.mLeftBorder[i7] = i13;
                    this.mRightBorder[i7] = i12;
                    if (i13 != -1) {
                        if (i9 == -1) {
                            i9 = i7;
                        }
                        int iMin = Math.min(i5, i13);
                        iMax2 = Math.max(iMax2, i12);
                        i5 = iMin;
                        i10 = i7;
                    }
                    i7++;
                    i3 = i11;
                    i6 = i15;
                }
                int i16 = i3;
                if (i9 == -1 || iMax2 == -1) {
                    return 1.0f;
                }
                convertToConvexArray(this.mLeftBorder, 1, i9, i10);
                convertToConvexArray(this.mRightBorder, -1, i9, i10);
                float f = 0.0f;
                for (int i17 = i16; i17 < intrinsicHeight; i17++) {
                    float f2 = this.mLeftBorder[i17];
                    if (f2 > -1.0f) {
                        f += (this.mRightBorder[i17] - f2) + 1.0f;
                    }
                }
                Rect rect = this.mBounds;
                rect.left = i5;
                rect.right = iMax2;
                rect.top = i9;
                rect.bottom = i10;
                if (rectF != null) {
                    float f3 = intrinsicWidth;
                    float f4 = intrinsicHeight;
                    rectF.set(i5 / f3, i9 / f4, 1.0f - (iMax2 / f3), 1.0f - (i10 / f4));
                }
                if (zArr != null && this.mEnableShapeDetection && zArr.length > 0) {
                    zArr[i16] = isShape(path);
                }
                return getScale(f, ((i10 + 1) - i9) * ((iMax2 + 1) - i5), intrinsicWidth * intrinsicHeight);
            }
            this.mAdaptiveIconScale = normalizeAdaptiveIcon(drawable, this.mMaxSize, this.mAdaptiveIconBounds);
            if (rectF != null) {
                rectF.set(this.mAdaptiveIconBounds);
            }
            return this.mAdaptiveIconScale;
        } finally {
        }
    }
}
