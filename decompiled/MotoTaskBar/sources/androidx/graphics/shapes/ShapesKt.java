package androidx.graphics.shapes;

import androidx.graphics.shapes.RoundedPolygon;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: Shapes.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ShapesKt {
    public static final RoundedPolygon circle(RoundedPolygon.Companion companion) {
        companion.getClass();
        return circle$default(companion, 0, 0.0f, 0.0f, 0.0f, 15, null);
    }

    public static final RoundedPolygon circle(RoundedPolygon.Companion companion, int i) {
        companion.getClass();
        return circle$default(companion, i, 0.0f, 0.0f, 0.0f, 14, null);
    }

    public static final RoundedPolygon circle(RoundedPolygon.Companion companion, int i, float f, float f2, float f3) {
        companion.getClass();
        if (i >= 3) {
            return RoundedPolygonKt.RoundedPolygon$default(i, f / ((float) Math.cos(Utils.getFloatPi() / i)), f2, f3, new CornerRounding(f, 0.0f, 2, null), null, 32, null);
        }
        throw new IllegalArgumentException("Circle must have at least three vertices");
    }

    public static /* synthetic */ RoundedPolygon circle$default(RoundedPolygon.Companion companion, int i, float f, float f2, float f3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 8;
        }
        if ((i2 & 2) != 0) {
            f = 1.0f;
        }
        if ((i2 & 4) != 0) {
            f2 = 0.0f;
        }
        if ((i2 & 8) != 0) {
            f3 = 0.0f;
        }
        return circle(companion, i, f, f2, f3);
    }

    public static final RoundedPolygon rectangle(RoundedPolygon.Companion companion, float f, float f2, CornerRounding cornerRounding, List list, float f3, float f4) {
        companion.getClass();
        cornerRounding.getClass();
        float f5 = 2;
        float f6 = f / f5;
        float f7 = f3 - f6;
        float f8 = f2 / f5;
        float f9 = f4 - f8;
        float f10 = f6 + f3;
        float f11 = f8 + f4;
        return RoundedPolygonKt.RoundedPolygon(new float[]{f10, f11, f7, f11, f7, f9, f10, f9}, cornerRounding, list, f3, f4);
    }

    public static final RoundedPolygon star(RoundedPolygon.Companion companion, int i, float f, float f2, CornerRounding cornerRounding) {
        companion.getClass();
        cornerRounding.getClass();
        return star$default(companion, i, f, f2, cornerRounding, null, null, 0.0f, 0.0f, 240, null);
    }

    public static final RoundedPolygon star(RoundedPolygon.Companion companion, int i, float f, float f2, CornerRounding cornerRounding, CornerRounding cornerRounding2, List list, float f3, float f4) {
        companion.getClass();
        cornerRounding.getClass();
        if (f <= 0.0f || f2 <= 0.0f) {
            throw new IllegalArgumentException("Star radii must both be greater than 0");
        }
        if (f2 >= f) {
            throw new IllegalArgumentException("innerRadius must be less than radius");
        }
        if (list == null && cornerRounding2 != null) {
            IntRange intRangeUntil = RangesKt.until(0, i);
            list = new ArrayList();
            Iterator it = intRangeUntil.iterator();
            while (it.hasNext()) {
                ((IntIterator) it).nextInt();
                CollectionsKt.addAll(list, CollectionsKt.listOf((Object[]) new CornerRounding[]{cornerRounding, cornerRounding2}));
            }
        }
        return RoundedPolygonKt.RoundedPolygon(starVerticesFromNumVerts(i, f, f2, f3, f4), cornerRounding, list, f3, f4);
    }

    public static /* synthetic */ RoundedPolygon star$default(RoundedPolygon.Companion companion, int i, float f, float f2, CornerRounding cornerRounding, CornerRounding cornerRounding2, List list, float f3, float f4, int i2, Object obj) {
        return star(companion, i, (i2 & 2) != 0 ? 1.0f : f, (i2 & 4) != 0 ? 0.5f : f2, (i2 & 8) != 0 ? CornerRounding.Unrounded : cornerRounding, (i2 & 16) != 0 ? null : cornerRounding2, (i2 & 32) == 0 ? list : null, (i2 & 64) != 0 ? 0.0f : f3, (i2 & 128) != 0 ? 0.0f : f4);
    }

    private static final float[] starVerticesFromNumVerts(int i, float f, float f2, float f3, float f4) {
        float[] fArr = new float[i * 4];
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            float f5 = i;
            long jM1088radialToCartesianL6JJ3z0$default = Utils.m1088radialToCartesianL6JJ3z0$default(f, (Utils.getFloatPi() / f5) * 2 * i3, 0L, 4, null);
            fArr[i2] = PointKt.m1076getXDnnuFBc(jM1088radialToCartesianL6JJ3z0$default) + f3;
            fArr[i2 + 1] = PointKt.m1077getYDnnuFBc(jM1088radialToCartesianL6JJ3z0$default) + f4;
            long jM1088radialToCartesianL6JJ3z0$default2 = Utils.m1088radialToCartesianL6JJ3z0$default(f2, (Utils.getFloatPi() / f5) * ((i3 * 2) + 1), 0L, 4, null);
            int i4 = i2 + 3;
            fArr[i2 + 2] = PointKt.m1076getXDnnuFBc(jM1088radialToCartesianL6JJ3z0$default2) + f3;
            i2 += 4;
            fArr[i4] = PointKt.m1077getYDnnuFBc(jM1088radialToCartesianL6JJ3z0$default2) + f4;
        }
        return fArr;
    }
}
