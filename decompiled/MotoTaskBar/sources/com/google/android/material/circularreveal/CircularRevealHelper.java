package com.google.android.material.circularreveal;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.google.android.material.circularreveal.CircularRevealWidget;
import com.google.android.material.math.MathUtils;

/* JADX INFO: loaded from: classes.dex */
public class CircularRevealHelper {
    private boolean buildingCircularRevealCache;
    private final Delegate delegate;
    private Drawable overlayDrawable;
    private CircularRevealWidget.RevealInfo revealInfo;
    private final Paint revealPaint;
    private final Path revealPath;
    private final Paint scrimPaint;
    private final View view;

    public interface Delegate {
        void actualDraw(Canvas canvas);

        boolean actualIsOpaque();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public CircularRevealHelper(Delegate delegate) {
        this.delegate = delegate;
        View view = (View) delegate;
        this.view = view;
        view.setWillNotDraw(false);
        this.revealPath = new Path();
        this.revealPaint = new Paint(7);
        Paint paint = new Paint(1);
        this.scrimPaint = paint;
        paint.setColor(0);
    }

    private void drawOverlayDrawable(Canvas canvas) {
        if (shouldDrawOverlayDrawable()) {
            Rect bounds = this.overlayDrawable.getBounds();
            float fWidth = this.revealInfo.centerX - (bounds.width() / 2.0f);
            float fHeight = this.revealInfo.centerY - (bounds.height() / 2.0f);
            canvas.translate(fWidth, fHeight);
            this.overlayDrawable.draw(canvas);
            canvas.translate(-fWidth, -fHeight);
        }
    }

    private float getDistanceToFurthestCorner(CircularRevealWidget.RevealInfo revealInfo) {
        return MathUtils.distanceToFurthestCorner(revealInfo.centerX, revealInfo.centerY, 0.0f, 0.0f, this.view.getWidth(), this.view.getHeight());
    }

    private void invalidateRevealInfo() {
        this.view.invalidate();
    }

    private boolean shouldDrawCircularReveal() {
        CircularRevealWidget.RevealInfo revealInfo = this.revealInfo;
        return !(revealInfo == null || revealInfo.isInvalid());
    }

    private boolean shouldDrawOverlayDrawable() {
        return (this.buildingCircularRevealCache || this.overlayDrawable == null || this.revealInfo == null) ? false : true;
    }

    private boolean shouldDrawScrim() {
        return (this.buildingCircularRevealCache || Color.alpha(this.scrimPaint.getColor()) == 0) ? false : true;
    }

    public void buildCircularRevealCache() {
    }

    public void destroyCircularRevealCache() {
    }

    public void draw(Canvas canvas) {
        Canvas canvas2;
        if (shouldDrawCircularReveal()) {
            this.delegate.actualDraw(canvas);
            if (shouldDrawScrim()) {
                canvas2 = canvas;
                canvas2.drawRect(0.0f, 0.0f, this.view.getWidth(), this.view.getHeight(), this.scrimPaint);
            } else {
                canvas2 = canvas;
            }
        } else {
            canvas2 = canvas;
            this.delegate.actualDraw(canvas2);
            if (shouldDrawScrim()) {
                canvas2.drawRect(0.0f, 0.0f, this.view.getWidth(), this.view.getHeight(), this.scrimPaint);
            }
        }
        drawOverlayDrawable(canvas2);
    }

    public int getCircularRevealScrimColor() {
        return this.scrimPaint.getColor();
    }

    public CircularRevealWidget.RevealInfo getRevealInfo() {
        CircularRevealWidget.RevealInfo revealInfo = this.revealInfo;
        if (revealInfo == null) {
            return null;
        }
        CircularRevealWidget.RevealInfo revealInfo2 = new CircularRevealWidget.RevealInfo(revealInfo);
        if (revealInfo2.isInvalid()) {
            revealInfo2.radius = getDistanceToFurthestCorner(revealInfo2);
        }
        return revealInfo2;
    }

    public boolean isOpaque() {
        return this.delegate.actualIsOpaque() && !shouldDrawCircularReveal();
    }

    public void setCircularRevealOverlayDrawable(Drawable drawable) {
        this.overlayDrawable = drawable;
        this.view.invalidate();
    }

    public void setCircularRevealScrimColor(int i) {
        this.scrimPaint.setColor(i);
        this.view.invalidate();
    }

    public void setRevealInfo(CircularRevealWidget.RevealInfo revealInfo) {
        if (revealInfo == null) {
            this.revealInfo = null;
        } else {
            CircularRevealWidget.RevealInfo revealInfo2 = this.revealInfo;
            if (revealInfo2 == null) {
                this.revealInfo = new CircularRevealWidget.RevealInfo(revealInfo);
            } else {
                revealInfo2.set(revealInfo);
            }
            if (MathUtils.geq(revealInfo.radius, getDistanceToFurthestCorner(revealInfo), 1.0E-4f)) {
                this.revealInfo.radius = Float.MAX_VALUE;
            }
        }
        invalidateRevealInfo();
    }
}
