package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Pair;
import androidx.core.math.MathUtils;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.progressindicator.DrawingDelegate;

/* JADX INFO: loaded from: classes.dex */
final class LinearDrawingDelegate extends DrawingDelegate {
    private float adjustedWavelength;
    private int cachedWavelength;
    private float displayedAmplitude;
    private float displayedCornerRadius;
    private float displayedTrackThickness;
    private boolean drawingDeterminateIndicator;
    Pair endPoints;
    private float totalTrackLengthFraction;
    private float trackLength;
    private boolean useStrokeCap;

    LinearDrawingDelegate(LinearProgressIndicatorSpec linearProgressIndicatorSpec) {
        super(linearProgressIndicatorSpec);
        this.trackLength = 300.0f;
        this.endPoints = new Pair(new DrawingDelegate.PathPoint(), new DrawingDelegate.PathPoint());
    }

    private void calculateDisplayedPath(PathMeasure pathMeasure, Path path, Pair pair, float f, float f2, float f3, float f4) {
        int i = this.drawingDeterminateIndicator ? ((LinearProgressIndicatorSpec) this.spec).wavelengthDeterminate : ((LinearProgressIndicatorSpec) this.spec).wavelengthIndeterminate;
        if (pathMeasure == this.activePathMeasure && i != this.cachedWavelength) {
            this.cachedWavelength = i;
            invalidateCachedPaths();
        }
        path.rewind();
        float f5 = (-this.trackLength) / 2.0f;
        boolean zHasWavyEffect = ((LinearProgressIndicatorSpec) this.spec).hasWavyEffect(this.drawingDeterminateIndicator);
        if (zHasWavyEffect) {
            float f6 = this.trackLength;
            float f7 = this.adjustedWavelength;
            float f8 = f6 / f7;
            float f9 = f4 / f8;
            float f10 = f8 / (f8 + 1.0f);
            f = (f + f9) * f10;
            f2 = (f2 + f9) * f10;
            f5 -= f4 * f7;
        }
        float length = f * pathMeasure.getLength();
        float length2 = f2 * pathMeasure.getLength();
        pathMeasure.getSegment(length, length2, path, true);
        DrawingDelegate.PathPoint pathPoint = (DrawingDelegate.PathPoint) pair.first;
        pathPoint.reset();
        pathMeasure.getPosTan(length, pathPoint.posVec, pathPoint.tanVec);
        DrawingDelegate.PathPoint pathPoint2 = (DrawingDelegate.PathPoint) pair.second;
        pathPoint2.reset();
        pathMeasure.getPosTan(length2, pathPoint2.posVec, pathPoint2.tanVec);
        this.transform.reset();
        this.transform.setTranslate(f5, 0.0f);
        pathPoint.translate(f5, 0.0f);
        pathPoint2.translate(f5, 0.0f);
        if (zHasWavyEffect) {
            float f11 = this.displayedAmplitude * f3;
            this.transform.postScale(1.0f, f11);
            pathPoint.scale(1.0f, f11);
            pathPoint2.scale(1.0f, f11);
        }
        path.transform(this.transform);
    }

    private void drawLine(Canvas canvas, Paint paint, float f, float f2, int i, int i2, int i3, float f3, float f4, boolean z) {
        float fClamp = MathUtils.clamp(f, 0.0f, 1.0f);
        float fClamp2 = MathUtils.clamp(f2, 0.0f, 1.0f);
        float fLerp = com.google.android.material.math.MathUtils.lerp(1.0f - this.totalTrackLengthFraction, 1.0f, fClamp);
        float fLerp2 = com.google.android.material.math.MathUtils.lerp(1.0f - this.totalTrackLengthFraction, 1.0f, fClamp2);
        int iClamp = (int) ((i2 * MathUtils.clamp(fLerp, 0.0f, 0.01f)) / 0.01f);
        int iClamp2 = (int) ((i3 * (1.0f - MathUtils.clamp(fLerp2, 0.99f, 1.0f))) / 0.01f);
        float f5 = this.trackLength;
        int i4 = (int) ((fLerp * f5) + iClamp);
        int i5 = (int) ((fLerp2 * f5) - iClamp2);
        float f6 = (-f5) / 2.0f;
        boolean z2 = ((LinearProgressIndicatorSpec) this.spec).hasWavyEffect(this.drawingDeterminateIndicator) && z && f3 > 0.0f;
        if (i4 <= i5) {
            float f7 = this.displayedCornerRadius;
            float f8 = i4 + f7;
            float f9 = i5 - f7;
            float f10 = f7 * 2.0f;
            paint.setColor(i);
            paint.setAntiAlias(true);
            paint.setStrokeWidth(this.displayedTrackThickness);
            ((DrawingDelegate.PathPoint) this.endPoints.first).reset();
            ((DrawingDelegate.PathPoint) this.endPoints.second).reset();
            ((DrawingDelegate.PathPoint) this.endPoints.first).translate(f8 + f6, 0.0f);
            ((DrawingDelegate.PathPoint) this.endPoints.second).translate(f6 + f9, 0.0f);
            if (f8 >= f9) {
                Pair pair = this.endPoints;
                drawRoundedBlock(canvas, paint, (DrawingDelegate.PathPoint) pair.first, (DrawingDelegate.PathPoint) pair.second, f10, this.displayedTrackThickness);
                return;
            }
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(this.useStrokeCap ? Paint.Cap.ROUND : Paint.Cap.BUTT);
            if (z2) {
                PathMeasure pathMeasure = this.activePathMeasure;
                Path path = this.displayedActivePath;
                Pair pair2 = this.endPoints;
                float f11 = this.trackLength;
                calculateDisplayedPath(pathMeasure, path, pair2, f8 / f11, f9 / f11, f3, f4);
                canvas.drawPath(this.displayedActivePath, paint);
            } else {
                Pair pair3 = this.endPoints;
                Object obj = pair3.first;
                float f12 = ((DrawingDelegate.PathPoint) obj).posVec[0];
                float f13 = ((DrawingDelegate.PathPoint) obj).posVec[1];
                Object obj2 = pair3.second;
                canvas.drawLine(f12, f13, ((DrawingDelegate.PathPoint) obj2).posVec[0], ((DrawingDelegate.PathPoint) obj2).posVec[1], paint);
            }
            if (this.useStrokeCap || this.displayedCornerRadius <= 0.0f) {
                return;
            }
            if (f8 > 0.0f) {
                drawRoundedBlock(canvas, paint, (DrawingDelegate.PathPoint) this.endPoints.first, f10, this.displayedTrackThickness);
            }
            if (f9 < this.trackLength) {
                drawRoundedBlock(canvas, paint, (DrawingDelegate.PathPoint) this.endPoints.second, f10, this.displayedTrackThickness);
            }
        }
    }

    private void drawRoundedBlock(Canvas canvas, Paint paint, DrawingDelegate.PathPoint pathPoint, float f, float f2) {
        drawRoundedBlock(canvas, paint, pathPoint, null, f, f2);
    }

    private void drawRoundedBlock(Canvas canvas, Paint paint, DrawingDelegate.PathPoint pathPoint, DrawingDelegate.PathPoint pathPoint2, float f, float f2) {
        float fMin = Math.min(f2, this.displayedTrackThickness);
        float f3 = f / 2.0f;
        float fMin2 = Math.min(f3, (this.displayedCornerRadius * fMin) / this.displayedTrackThickness);
        RectF rectF = new RectF((-f) / 2.0f, (-fMin) / 2.0f, f3, fMin / 2.0f);
        paint.setStyle(Paint.Style.FILL);
        canvas.save();
        if (pathPoint2 != null) {
            float[] fArr = pathPoint2.posVec;
            canvas.translate(fArr[0], fArr[1]);
            canvas.rotate(vectorToCanvasRotation(pathPoint2.tanVec));
            Path path = new Path();
            path.addRoundRect(rectF, fMin2, fMin2, Path.Direction.CCW);
            canvas.clipPath(path);
            canvas.rotate(-vectorToCanvasRotation(pathPoint2.tanVec));
            float[] fArr2 = pathPoint2.posVec;
            canvas.translate(-fArr2[0], -fArr2[1]);
        }
        float[] fArr3 = pathPoint.posVec;
        canvas.translate(fArr3[0], fArr3[1]);
        canvas.rotate(vectorToCanvasRotation(pathPoint.tanVec));
        canvas.drawRoundRect(rectF, fMin2, fMin2, paint);
        canvas.restore();
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    void adjustCanvas(Canvas canvas, Rect rect, float f, boolean z, boolean z2) {
        if (this.trackLength != rect.width()) {
            this.trackLength = rect.width();
            invalidateCachedPaths();
        }
        float preferredHeight = getPreferredHeight();
        canvas.translate(rect.left + (rect.width() / 2.0f), rect.top + (rect.height() / 2.0f) + Math.max(0.0f, (rect.height() - preferredHeight) / 2.0f));
        if (((LinearProgressIndicatorSpec) this.spec).drawHorizontallyInverse) {
            canvas.scale(-1.0f, 1.0f);
        }
        float f2 = this.trackLength / 2.0f;
        float f3 = preferredHeight / 2.0f;
        canvas.clipRect(-f2, -f3, f2, f3);
        BaseProgressIndicatorSpec baseProgressIndicatorSpec = this.spec;
        this.useStrokeCap = ((float) ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec).trackThickness) / 2.0f <= ((float) ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec).trackCornerRadius);
        this.displayedTrackThickness = ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec).trackThickness * f;
        this.displayedCornerRadius = Math.min(((LinearProgressIndicatorSpec) baseProgressIndicatorSpec).trackThickness / 2.0f, ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec).trackCornerRadius) * f;
        BaseProgressIndicatorSpec baseProgressIndicatorSpec2 = this.spec;
        this.displayedAmplitude = ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec2).waveAmplitude * f;
        if (z || z2) {
            if ((z && ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec2).showAnimationBehavior == 2) || (z2 && ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec2).hideAnimationBehavior == 1)) {
                canvas.scale(1.0f, -1.0f);
            }
            if (z || (z2 && ((LinearProgressIndicatorSpec) this.spec).hideAnimationBehavior != 3)) {
                canvas.translate(0.0f, (((LinearProgressIndicatorSpec) this.spec).trackThickness * (1.0f - f)) / 2.0f);
            }
        }
        if (z2 && ((LinearProgressIndicatorSpec) this.spec).hideAnimationBehavior == 3) {
            this.totalTrackLengthFraction = f;
        } else {
            this.totalTrackLengthFraction = 1.0f;
        }
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    void drawStopIndicator(Canvas canvas, Paint paint, int i, int i2) {
        int iCompositeARGBWithAlpha = MaterialColors.compositeARGBWithAlpha(i, i2);
        this.drawingDeterminateIndicator = false;
        if (((LinearProgressIndicatorSpec) this.spec).trackStopIndicatorSize <= 0 || iCompositeARGBWithAlpha == 0) {
            return;
        }
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(iCompositeARGBWithAlpha);
        DrawingDelegate.PathPoint pathPoint = new DrawingDelegate.PathPoint(new float[]{(this.trackLength / 2.0f) - (this.displayedTrackThickness / 2.0f), 0.0f}, new float[]{1.0f, 0.0f});
        BaseProgressIndicatorSpec baseProgressIndicatorSpec = this.spec;
        drawRoundedBlock(canvas, paint, pathPoint, ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec).trackStopIndicatorSize, ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec).trackStopIndicatorSize);
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    void fillIndicator(Canvas canvas, Paint paint, DrawingDelegate.ActiveIndicator activeIndicator, int i) {
        int iCompositeARGBWithAlpha = MaterialColors.compositeARGBWithAlpha(activeIndicator.color, i);
        this.drawingDeterminateIndicator = activeIndicator.isDeterminate;
        float f = activeIndicator.startFraction;
        float f2 = activeIndicator.endFraction;
        int i2 = activeIndicator.gapSize;
        drawLine(canvas, paint, f, f2, iCompositeARGBWithAlpha, i2, i2, activeIndicator.amplitudeFraction, activeIndicator.phaseFraction, true);
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    void fillTrack(Canvas canvas, Paint paint, float f, float f2, int i, int i2, int i3) {
        int iCompositeARGBWithAlpha = MaterialColors.compositeARGBWithAlpha(i, i2);
        this.drawingDeterminateIndicator = false;
        drawLine(canvas, paint, f, f2, iCompositeARGBWithAlpha, i3, i3, 0.0f, 0.0f, false);
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    int getPreferredHeight() {
        BaseProgressIndicatorSpec baseProgressIndicatorSpec = this.spec;
        return ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec).trackThickness + (((LinearProgressIndicatorSpec) baseProgressIndicatorSpec).waveAmplitude * 2);
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    int getPreferredWidth() {
        return -1;
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    void invalidateCachedPaths() {
        this.cachedActivePath.rewind();
        if (((LinearProgressIndicatorSpec) this.spec).hasWavyEffect(this.drawingDeterminateIndicator)) {
            int i = this.drawingDeterminateIndicator ? ((LinearProgressIndicatorSpec) this.spec).wavelengthDeterminate : ((LinearProgressIndicatorSpec) this.spec).wavelengthIndeterminate;
            float f = this.trackLength;
            int i2 = (int) (f / i);
            this.adjustedWavelength = f / i2;
            for (int i3 = 0; i3 <= i2; i3++) {
                int i4 = i3 * 2;
                float f2 = i4 + 1;
                this.cachedActivePath.cubicTo(i4 + 0.48f, 0.0f, f2 - 0.48f, 1.0f, f2, 1.0f);
                float f3 = i4 + 2;
                this.cachedActivePath.cubicTo(f2 + 0.48f, 1.0f, f3 - 0.48f, 0.0f, f3, 0.0f);
            }
            this.transform.reset();
            this.transform.setScale(this.adjustedWavelength / 2.0f, -2.0f);
            this.transform.postTranslate(0.0f, 1.0f);
            this.cachedActivePath.transform(this.transform);
        } else {
            this.cachedActivePath.lineTo(this.trackLength, 0.0f);
        }
        this.activePathMeasure.setPath(this.cachedActivePath, false);
    }
}
