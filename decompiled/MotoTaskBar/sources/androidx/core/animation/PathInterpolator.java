package androidx.core.animation;

import android.graphics.Path;

/* JADX INFO: loaded from: classes.dex */
public class PathInterpolator implements Interpolator {
    private float[] mData;

    public PathInterpolator(float f, float f2, float f3, float f4) {
        initCubic(f, f2, f3, f4);
    }

    public PathInterpolator(Path path) {
        initPath(path);
    }

    private static boolean floatEquals(float f, float f2) {
        return Math.abs(f - f2) < 0.01f;
    }

    private float getFractionAtIndex(int i) {
        return this.mData[i * 3];
    }

    private int getNumOfPoints() {
        return this.mData.length / 3;
    }

    private float getXAtIndex(int i) {
        return this.mData[(i * 3) + 1];
    }

    private float getYAtIndex(int i) {
        return this.mData[(i * 3) + 2];
    }

    private void initCubic(float f, float f2, float f3, float f4) {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.cubicTo(f, f2, f3, f4, 1.0f, 1.0f);
        initPath(path);
    }

    private void initPath(Path path) {
        this.mData = PathUtils.createKeyFrameData(path, 0.002f);
        int numOfPoints = getNumOfPoints();
        int i = 0;
        float f = 0.0f;
        if (floatEquals(getXAtIndex(0), 0.0f) && floatEquals(getYAtIndex(0), 0.0f)) {
            int i2 = numOfPoints - 1;
            if (floatEquals(getXAtIndex(i2), 1.0f) && floatEquals(getYAtIndex(i2), 1.0f)) {
                float f2 = 0.0f;
                while (i < numOfPoints) {
                    float fractionAtIndex = getFractionAtIndex(i);
                    float xAtIndex = getXAtIndex(i);
                    if (fractionAtIndex == f && xAtIndex != f2) {
                        throw new IllegalArgumentException("The Path cannot have discontinuity in the X axis.");
                    }
                    if (xAtIndex < f2) {
                        throw new IllegalArgumentException("The Path cannot loop back on itself.");
                    }
                    i++;
                    f = fractionAtIndex;
                    f2 = xAtIndex;
                }
                return;
            }
        }
        throw new IllegalArgumentException("The Path must start at (0,0) and end at (1,1)");
    }

    @Override // androidx.core.animation.Interpolator
    public float getInterpolation(float f) {
        if (f <= 0.0f) {
            return 0.0f;
        }
        if (f >= 1.0f) {
            return 1.0f;
        }
        int numOfPoints = getNumOfPoints() - 1;
        int i = 0;
        while (numOfPoints - i > 1) {
            int i2 = (i + numOfPoints) / 2;
            if (f < getXAtIndex(i2)) {
                numOfPoints = i2;
            } else {
                i = i2;
            }
        }
        float xAtIndex = getXAtIndex(numOfPoints) - getXAtIndex(i);
        if (xAtIndex == 0.0f) {
            return getYAtIndex(i);
        }
        float xAtIndex2 = (f - getXAtIndex(i)) / xAtIndex;
        float yAtIndex = getYAtIndex(i);
        return yAtIndex + (xAtIndex2 * (getYAtIndex(numOfPoints) - yAtIndex));
    }
}
