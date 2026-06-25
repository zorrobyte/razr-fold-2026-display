package com.motorola.taskbar.recent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.shared.recents.model.Task;
import com.android.systemui.shared.recents.model.ThumbnailData;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt;

/* JADX INFO: compiled from: TaskThumbnailView.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class TaskThumbnailView extends View {
    private final Paint mBackgroundPaint;
    private BitmapShader mBitmapShader;
    private float mClipBottom;
    private final RectF mClippedInsets;
    private final float mCornerRadius;
    private final Matrix mMatrix;
    private final Paint mPaint;
    private Task mTask;
    private ThumbnailData mThumbnailData;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TaskThumbnailView(Context context) {
        this(context, null, 0, 6, null);
        context.getClass();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TaskThumbnailView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        context.getClass();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TaskThumbnailView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        context.getClass();
        Paint paint = new Paint(1);
        paint.setFilterBitmap(true);
        this.mPaint = paint;
        Paint paint2 = new Paint(1);
        paint2.setColor(-1);
        this.mBackgroundPaint = paint2;
        this.mMatrix = new Matrix();
        this.mClipBottom = -1.0f;
        this.mClippedInsets = new RectF();
        this.mCornerRadius = TaskLayoutUtil.Companion.getTaskCornerRadius(context);
    }

    public /* synthetic */ TaskThumbnailView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0018  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x002d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void drawOnCanvas(android.graphics.Canvas r21, float r22, float r23, float r24, float r25, float r26) {
        /*
            r20 = this;
            r0 = r20
            com.android.systemui.shared.recents.model.Task r1 = r0.mTask
            if (r1 == 0) goto L18
            r1.getClass()
            boolean r1 = r1.isLocked
            if (r1 != 0) goto L18
            android.graphics.BitmapShader r1 = r0.mBitmapShader
            if (r1 == 0) goto L18
            com.android.systemui.shared.recents.model.ThumbnailData r1 = r0.mThumbnailData
            if (r1 != 0) goto L16
            goto L18
        L16:
            r1 = 0
            goto L19
        L18:
            r1 = 1
        L19:
            r2 = 0
            if (r1 != 0) goto L2d
            float r3 = r0.mClipBottom
            int r3 = (r3 > r2 ? 1 : (r3 == r2 ? 0 : -1))
            if (r3 > 0) goto L2d
            com.android.systemui.shared.recents.model.ThumbnailData r3 = r0.mThumbnailData
            r3.getClass()
            boolean r3 = r3.isTranslucent()
            if (r3 == 0) goto L43
        L2d:
            android.graphics.Paint r11 = r0.mBackgroundPaint
            r10 = r26
            r4 = r21
            r5 = r22
            r6 = r23
            r7 = r24
            r8 = r25
            r9 = r26
            r4.drawRoundRect(r5, r6, r7, r8, r9, r10, r11)
            if (r1 == 0) goto L43
            return
        L43:
            float r1 = r0.mClipBottom
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 <= 0) goto L6a
            r21.save()
            float r1 = r0.mClipBottom
            r12 = r21
            r13 = r22
            r14 = r23
            r15 = r24
            r12.clipRect(r13, r14, r15, r1)
            android.graphics.Paint r0 = r0.mPaint
            r18 = r26
            r16 = r25
            r17 = r26
            r19 = r0
            r12.drawRoundRect(r13, r14, r15, r16, r17, r18, r19)
            r21.restore()
            return
        L6a:
            android.graphics.Paint r0 = r0.mPaint
            r18 = r26
            r12 = r21
            r13 = r22
            r14 = r23
            r15 = r24
            r16 = r25
            r17 = r26
            r19 = r0
            r12.drawRoundRect(r13, r14, r15, r16, r17, r18, r19)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.taskbar.recent.TaskThumbnailView.drawOnCanvas(android.graphics.Canvas, float, float, float, float, float):void");
    }

    private final void updateThumbnailMatrix() {
        ThumbnailData thumbnailData;
        this.mClipBottom = -1.0f;
        if (this.mBitmapShader != null && (thumbnailData = this.mThumbnailData) != null) {
            thumbnailData.getClass();
            float f = thumbnailData.scale;
            ThumbnailData thumbnailData2 = this.mThumbnailData;
            thumbnailData2.getClass();
            Rect rect = thumbnailData2.insets;
            ThumbnailData thumbnailData3 = this.mThumbnailData;
            thumbnailData3.getClass();
            thumbnailData3.getThumbnail().getClass();
            float width = r2.getWidth() - ((rect.left + rect.right) * f);
            ThumbnailData thumbnailData4 = this.mThumbnailData;
            thumbnailData4.getClass();
            thumbnailData4.getThumbnail().getClass();
            float height = r3.getHeight() - ((rect.top + rect.bottom) * f);
            float measuredWidth = getMeasuredWidth() == 0 ? 0.0f : getMeasuredWidth() / width;
            this.mClippedInsets.offsetTo(rect.left * f, rect.top * f);
            Matrix matrix = this.mMatrix;
            RectF rectF = this.mClippedInsets;
            matrix.setTranslate(-rectF.left, -rectF.top);
            ThumbnailData thumbnailData5 = this.mThumbnailData;
            thumbnailData5.getClass();
            thumbnailData5.getThumbnail().getClass();
            ThumbnailData thumbnailData6 = this.mThumbnailData;
            thumbnailData6.getClass();
            thumbnailData6.getThumbnail().getClass();
            RectF rectF2 = this.mClippedInsets;
            float f2 = rectF2.left * measuredWidth;
            rectF2.left = f2;
            rectF2.top *= measuredWidth;
            rectF2.right = ((r0.getWidth() * measuredWidth) - f2) - getMeasuredWidth();
            RectF rectF3 = this.mClippedInsets;
            rectF3.bottom = ((r1.getHeight() * measuredWidth) - rectF3.top) - getMeasuredHeight();
            this.mMatrix.postScale(measuredWidth, measuredWidth);
            BitmapShader bitmapShader = this.mBitmapShader;
            bitmapShader.getClass();
            bitmapShader.setLocalMatrix(this.mMatrix);
            float fMax = Math.max(height * measuredWidth, 0.0f);
            if (MathKt.roundToInt(fMax) < getMeasuredHeight()) {
                this.mClipBottom = fMax;
            }
            this.mPaint.setShader(this.mBitmapShader);
        }
        invalidate();
    }

    public final void bind(Task task) {
        this.mTask = task;
        int i = DarkIconDispatcher.DEFAULT_INVERSE_ICON_TINT;
        if (task != null) {
            i = (-16777216) | task.colorBackground;
        }
        this.mPaint.setColor(i);
        this.mBackgroundPaint.setColor(i);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        canvas.getClass();
        canvas.save();
        canvas.translate(0.0f, 0.0f);
        drawOnCanvas(canvas, 0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight(), this.mCornerRadius);
        canvas.restore();
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        updateThumbnailMatrix();
    }

    public final void setThumbnail(Task task, ThumbnailData thumbnailData) {
        Bitmap thumbnail;
        this.mTask = task;
        if (thumbnailData != null && (thumbnail = thumbnailData.getThumbnail()) != null) {
            if (thumbnail.isRecycled()) {
                thumbnail = null;
            }
            if (thumbnail != null) {
                thumbnail.prepareToDraw();
                Shader.TileMode tileMode = Shader.TileMode.CLAMP;
                BitmapShader bitmapShader = new BitmapShader(thumbnail, tileMode, tileMode);
                this.mBitmapShader = bitmapShader;
                this.mPaint.setShader(bitmapShader);
                this.mThumbnailData = thumbnailData;
                updateThumbnailMatrix();
                return;
            }
        }
        this.mBitmapShader = null;
        this.mThumbnailData = null;
        this.mPaint.setShader(null);
    }
}
