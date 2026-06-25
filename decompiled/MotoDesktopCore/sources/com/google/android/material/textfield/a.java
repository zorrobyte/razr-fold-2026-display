package com.google.android.material.textfield;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public final class a extends GradientDrawable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Paint f2239a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final RectF f2240b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f2241c;

    public a() {
        Paint paint = new Paint(1);
        this.f2239a = paint;
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(-1);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        this.f2240b = new RectF();
    }

    @Override // android.graphics.drawable.GradientDrawable, android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        Drawable.Callback callback = getCallback();
        if (callback instanceof View) {
            ((View) callback).setLayerType(2, null);
        } else {
            this.f2241c = canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), null);
        }
        super.draw(canvas);
        canvas.drawRect(this.f2240b, this.f2239a);
        if (getCallback() instanceof View) {
            return;
        }
        canvas.restoreToCount(this.f2241c);
    }
}
