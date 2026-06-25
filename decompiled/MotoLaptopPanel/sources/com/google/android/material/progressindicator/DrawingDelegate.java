package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
abstract class DrawingDelegate {
    final PathMeasure activePathMeasure;
    final Path cachedActivePath;
    final Path displayedActivePath;
    BaseProgressIndicatorSpec spec;
    final Matrix transform;

    public class ActiveIndicator {
        float amplitudeFraction = 1.0f;
        int color;
        float endFraction;
        int gapSize;
        boolean isDeterminate;
        float phaseFraction;
        float rotationDegree;
        float startFraction;

        protected ActiveIndicator() {
        }
    }

    public class PathPoint {
        float[] posVec;
        float[] tanVec;
        final Matrix transform;

        public PathPoint() {
            this.posVec = new float[2];
            this.tanVec = new float[]{1.0f, 0.0f};
            this.transform = new Matrix();
        }

        public PathPoint(DrawingDelegate drawingDelegate, PathPoint pathPoint) {
            this(pathPoint.posVec, pathPoint.tanVec);
        }

        public PathPoint(float[] fArr, float[] fArr2) {
            float[] fArr3 = new float[2];
            this.posVec = fArr3;
            this.tanVec = new float[2];
            System.arraycopy(fArr, 0, fArr3, 0, 2);
            System.arraycopy(fArr2, 0, this.tanVec, 0, 2);
            this.transform = new Matrix();
        }

        void moveAcross(float f) {
            float[] fArr = this.tanVec;
            float fAtan2 = (float) (Math.atan2(fArr[1], fArr[0]) + 1.5707963267948966d);
            float[] fArr2 = this.posVec;
            double d = f;
            double d2 = fAtan2;
            fArr2[0] = (float) (((double) fArr2[0]) + (Math.cos(d2) * d));
            float[] fArr3 = this.posVec;
            fArr3[1] = (float) (((double) fArr3[1]) + (d * Math.sin(d2)));
        }

        void moveAlong(float f) {
            float[] fArr = this.tanVec;
            float fAtan2 = (float) Math.atan2(fArr[1], fArr[0]);
            float[] fArr2 = this.posVec;
            double d = f;
            double d2 = fAtan2;
            fArr2[0] = (float) (((double) fArr2[0]) + (Math.cos(d2) * d));
            float[] fArr3 = this.posVec;
            fArr3[1] = (float) (((double) fArr3[1]) + (d * Math.sin(d2)));
        }

        public void reset() {
            Arrays.fill(this.posVec, 0.0f);
            Arrays.fill(this.tanVec, 0.0f);
            this.tanVec[0] = 1.0f;
            this.transform.reset();
        }

        public void rotate(float f) {
            this.transform.reset();
            this.transform.setRotate(f);
            this.transform.mapPoints(this.posVec);
            this.transform.mapPoints(this.tanVec);
        }

        void scale(float f, float f2) {
            float[] fArr = this.posVec;
            fArr[0] = fArr[0] * f;
            fArr[1] = fArr[1] * f2;
            float[] fArr2 = this.tanVec;
            fArr2[0] = fArr2[0] * f;
            fArr2[1] = fArr2[1] * f2;
        }

        void translate(float f, float f2) {
            float[] fArr = this.posVec;
            fArr[0] = fArr[0] + f;
            fArr[1] = fArr[1] + f2;
        }
    }

    public DrawingDelegate(BaseProgressIndicatorSpec baseProgressIndicatorSpec) {
        Path path = new Path();
        this.cachedActivePath = path;
        this.displayedActivePath = new Path();
        this.activePathMeasure = new PathMeasure(path, false);
        this.spec = baseProgressIndicatorSpec;
        this.transform = new Matrix();
    }

    abstract void adjustCanvas(Canvas canvas, Rect rect, float f, boolean z, boolean z2);

    abstract void drawStopIndicator(Canvas canvas, Paint paint, int i, int i2);

    abstract void fillIndicator(Canvas canvas, Paint paint, ActiveIndicator activeIndicator, int i);

    abstract void fillTrack(Canvas canvas, Paint paint, float f, float f2, int i, int i2, int i3);

    abstract int getPreferredHeight();

    abstract int getPreferredWidth();

    abstract void invalidateCachedPaths();

    void validateSpecAndAdjustCanvas(Canvas canvas, Rect rect, float f, boolean z, boolean z2) {
        this.spec.validateSpec();
        adjustCanvas(canvas, rect, f, z, z2);
    }

    float vectorToCanvasRotation(float[] fArr) {
        return (float) Math.toDegrees(Math.atan2(fArr[1], fArr[0]));
    }
}
