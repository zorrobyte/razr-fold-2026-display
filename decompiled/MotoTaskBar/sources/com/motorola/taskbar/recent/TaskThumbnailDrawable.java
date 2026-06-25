package com.motorola.taskbar.recent;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.shared.recents.model.Task;
import com.android.systemui.shared.recents.model.ThumbnailData;

/* JADX INFO: loaded from: classes2.dex */
public class TaskThumbnailDrawable extends Drawable {
    private BitmapShader mBitmapShader;
    private RectF mClippedInsets;
    private int mImageHeight;
    private int mImageWidth;
    private final Matrix mMatrix;
    private final Paint mPaint;
    private ThumbnailData mThumbnailData;

    public TaskThumbnailDrawable(Task task) {
        Paint paint = new Paint(1);
        this.mPaint = paint;
        this.mMatrix = new Matrix();
        this.mClippedInsets = new RectF();
        ThumbnailData thumbnailData = task.thumbnail;
        this.mThumbnailData = thumbnailData;
        Bitmap thumbnail = thumbnailData.getThumbnail();
        thumbnail.prepareToDraw();
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        this.mBitmapShader = new BitmapShader(thumbnail, tileMode, tileMode);
        paint.setColor(task.colorBackground | DarkIconDispatcher.DEFAULT_INVERSE_ICON_TINT);
        updateThumbnailMatrix();
    }

    public static TaskThumbnailDrawable createTaskThumbnailDrawable(Task task) {
        return new TaskThumbnailDrawable(task);
    }

    private void updateThumbnailMatrix() {
        if (this.mBitmapShader == null || this.mThumbnailData == null) {
            return;
        }
        Rect bounds = getBounds();
        this.mMatrix.reset();
        this.mClippedInsets.setEmpty();
        ThumbnailData thumbnailData = this.mThumbnailData;
        float f = thumbnailData.scale;
        Rect rect = thumbnailData.insets;
        float f2 = rect.left * f;
        float f3 = rect.top * f;
        this.mImageWidth = (int) (thumbnailData.getThumbnail().getWidth() - ((rect.left + rect.right) * f));
        this.mImageHeight = (int) (this.mThumbnailData.getThumbnail().getHeight() - ((rect.top + rect.bottom) * f));
        if (bounds.isEmpty()) {
            return;
        }
        float fWidth = bounds.width() / this.mImageWidth;
        float fHeight = bounds.height() / this.mImageHeight;
        if (fWidth > fHeight) {
            fWidth = fHeight;
        }
        this.mMatrix.postTranslate(-f2, -f3);
        this.mMatrix.postScale(fWidth, fWidth);
        this.mBitmapShader.setLocalMatrix(this.mMatrix);
        this.mPaint.setShader(this.mBitmapShader);
        float f4 = this.mImageWidth * fWidth;
        float f5 = this.mImageHeight * fWidth;
        this.mClippedInsets.left = (bounds.width() - f4) / 2.0f;
        this.mClippedInsets.top = (bounds.height() - f5) / 2.0f;
        RectF rectF = this.mClippedInsets;
        rectF.right = rectF.left + f4;
        rectF.bottom = rectF.top + f5;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        canvas.save();
        RectF rectF = this.mClippedInsets;
        canvas.translate(rectF.left, rectF.top);
        canvas.clipRect(0.0f, 0.0f, this.mClippedInsets.width(), this.mClippedInsets.height());
        canvas.clipRect(this.mClippedInsets);
        canvas.drawRect(getBounds(), this.mPaint);
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mImageHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mImageWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(int i, int i2, int i3, int i4) {
        super.setBounds(i, i2, i3, i4);
        updateThumbnailMatrix();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }
}
