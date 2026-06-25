package com.android.systemui.surfaceeffects.loadingeffect;

import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/* JADX INFO: compiled from: LoadingEffectView.kt */
/* JADX INFO: loaded from: classes.dex */
public class LoadingEffectView extends View {
    private BlendMode blendMode;
    private Paint paint;

    public LoadingEffectView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.blendMode = BlendMode.SRC_OVER;
    }

    public final void draw(Paint paint) {
        paint.getClass();
        this.paint = paint;
        paint.setBlendMode(this.blendMode);
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Paint paint;
        canvas.getClass();
        if (canvas.isHardwareAccelerated() && (paint = this.paint) != null) {
            canvas.drawPaint(paint);
        }
    }

    public final void setBlendMode(BlendMode blendMode) {
        blendMode.getClass();
        this.blendMode = blendMode;
    }
}
