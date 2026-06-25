package androidx.graphics.shapes;

import java.util.Arrays;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RoundedPolygon.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RoundedPolygon {
    public static final Companion Companion = new Companion(null);
    private final long center;
    private final List cubics;
    private final List features;

    /* JADX INFO: compiled from: RoundedPolygon.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private RoundedPolygon(List list, long j) {
        List listMutableListOf;
        List listMutableListOf2;
        Cubic cubic;
        List cubics;
        list.getClass();
        this.features = list;
        this.center = j;
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        int i = 0;
        Cubic cubic2 = null;
        if (list.size() <= 0 || ((Feature) list.get(0)).getCubics().size() != 3) {
            listMutableListOf = null;
            listMutableListOf2 = null;
        } else {
            Pair pairSplit = ((Cubic) ((Feature) list.get(0)).getCubics().get(1)).split(0.5f);
            Cubic cubic3 = (Cubic) pairSplit.component1();
            Cubic cubic4 = (Cubic) pairSplit.component2();
            listMutableListOf2 = CollectionsKt.mutableListOf(((Feature) list.get(0)).getCubics().get(0), cubic3);
            listMutableListOf = CollectionsKt.mutableListOf(cubic4, ((Feature) list.get(0)).getCubics().get(2));
        }
        int size = list.size();
        if (size >= 0) {
            int i2 = 0;
            Cubic cubic5 = null;
            while (true) {
                if (i2 == 0 && listMutableListOf != null) {
                    cubics = listMutableListOf;
                } else if (i2 != this.features.size()) {
                    cubics = ((Feature) this.features.get(i2)).getCubics();
                } else if (listMutableListOf2 == null) {
                    break;
                } else {
                    cubics = listMutableListOf2;
                }
                int size2 = cubics.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    Cubic cubic6 = (Cubic) cubics.get(i3);
                    if (!cubic6.zeroLength$graphics_shapes()) {
                        if (cubic5 != null) {
                            listCreateListBuilder.add(cubic5);
                        }
                        if (cubic2 == null) {
                            cubic2 = cubic6;
                            cubic5 = cubic2;
                        } else {
                            cubic5 = cubic6;
                        }
                    } else if (cubic5 != null) {
                        float[] points$graphics_shapes = cubic5.getPoints$graphics_shapes();
                        float[] fArrCopyOf = Arrays.copyOf(points$graphics_shapes, points$graphics_shapes.length);
                        fArrCopyOf.getClass();
                        Cubic cubic7 = new Cubic(fArrCopyOf);
                        cubic7.getPoints$graphics_shapes()[6] = cubic6.getAnchor1X();
                        cubic7.getPoints$graphics_shapes()[7] = cubic6.getAnchor1Y();
                        cubic5 = cubic7;
                    }
                }
                if (i2 == size) {
                    break;
                } else {
                    i2++;
                }
            }
            cubic = cubic2;
            cubic2 = cubic5;
        } else {
            cubic = null;
        }
        if (cubic2 == null || cubic == null) {
            listCreateListBuilder.add(CubicKt.Cubic(getCenterX(), getCenterY(), getCenterX(), getCenterY(), getCenterX(), getCenterY(), getCenterX(), getCenterY()));
        } else {
            listCreateListBuilder.add(CubicKt.Cubic(cubic2.getAnchor0X(), cubic2.getAnchor0Y(), cubic2.getControl0X(), cubic2.getControl0Y(), cubic2.getControl1X(), cubic2.getControl1Y(), cubic.getAnchor0X(), cubic.getAnchor0Y()));
        }
        List listBuild = CollectionsKt.build(listCreateListBuilder);
        this.cubics = listBuild;
        Object obj = listBuild.get(listBuild.size() - 1);
        int size3 = listBuild.size();
        while (i < size3) {
            Cubic cubic8 = (Cubic) this.cubics.get(i);
            Cubic cubic9 = (Cubic) obj;
            if (Math.abs(cubic8.getAnchor0X() - cubic9.getAnchor1X()) > 1.0E-4f || Math.abs(cubic8.getAnchor0Y() - cubic9.getAnchor1Y()) > 1.0E-4f) {
                throw new IllegalArgumentException("RoundedPolygon must be contiguous, with the anchor points of all curves matching the anchor points of the preceding and succeeding cubics");
            }
            i++;
            obj = cubic8;
        }
    }

    public /* synthetic */ RoundedPolygon(List list, long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, j);
    }

    public static /* synthetic */ float[] calculateBounds$default(RoundedPolygon roundedPolygon, float[] fArr, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            fArr = new float[4];
        }
        if ((i & 2) != 0) {
            z = true;
        }
        return roundedPolygon.calculateBounds(fArr, z);
    }

    public final float[] calculateBounds(float[] fArr) {
        fArr.getClass();
        return calculateBounds$default(this, fArr, false, 2, null);
    }

    public final float[] calculateBounds(float[] fArr, boolean z) {
        fArr.getClass();
        if (fArr.length < 4) {
            throw new IllegalArgumentException("Required bounds size of 4");
        }
        int size = this.cubics.size();
        float fMax = Float.MIN_VALUE;
        float fMin = Float.MAX_VALUE;
        float fMin2 = Float.MAX_VALUE;
        float fMax2 = Float.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            ((Cubic) this.cubics.get(i)).calculateBounds$graphics_shapes(fArr, z);
            fMin = Math.min(fMin, fArr[0]);
            fMin2 = Math.min(fMin2, fArr[1]);
            fMax = Math.max(fMax, fArr[2]);
            fMax2 = Math.max(fMax2, fArr[3]);
        }
        fArr[0] = fMin;
        fArr[1] = fMin2;
        fArr[2] = fMax;
        fArr[3] = fMax2;
        return fArr;
    }

    public final float[] calculateMaxBounds(float[] fArr) {
        fArr.getClass();
        if (fArr.length < 4) {
            throw new IllegalArgumentException("Required bounds size of 4");
        }
        int size = this.cubics.size();
        float fMax = 0.0f;
        for (int i = 0; i < size; i++) {
            Cubic cubic = (Cubic) this.cubics.get(i);
            float fDistanceSquared = Utils.distanceSquared(cubic.getAnchor0X() - getCenterX(), cubic.getAnchor0Y() - getCenterY());
            long jM1974pointOnCurveOOQOV4g$graphics_shapes = cubic.m1974pointOnCurveOOQOV4g$graphics_shapes(0.5f);
            fMax = Math.max(fMax, Math.max(fDistanceSquared, Utils.distanceSquared(PointKt.m1983getXDnnuFBc(jM1974pointOnCurveOOQOV4g$graphics_shapes) - getCenterX(), PointKt.m1984getYDnnuFBc(jM1974pointOnCurveOOQOV4g$graphics_shapes) - getCenterY())));
        }
        float fSqrt = (float) Math.sqrt(fMax);
        fArr[0] = getCenterX() - fSqrt;
        fArr[1] = getCenterY() - fSqrt;
        fArr[2] = getCenterX() + fSqrt;
        fArr[3] = getCenterY() + fSqrt;
        return fArr;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof RoundedPolygon) {
            return Intrinsics.areEqual(this.features, ((RoundedPolygon) obj).features);
        }
        return false;
    }

    public final float getCenterX() {
        return PointKt.m1983getXDnnuFBc(this.center);
    }

    public final float getCenterY() {
        return PointKt.m1984getYDnnuFBc(this.center);
    }

    public final List getFeatures() {
        return this.features;
    }

    public int hashCode() {
        return this.features.hashCode();
    }

    public String toString() {
        return "[RoundedPolygon. Cubics = " + CollectionsKt.joinToString$default(this.cubics, null, null, null, 0, null, null, 63, null) + " || Features = " + CollectionsKt.joinToString$default(this.features, null, null, null, 0, null, null, 63, null) + " || Center = (" + getCenterX() + ", " + getCenterY() + ")]";
    }

    public final RoundedPolygon transformed(PointTransformer pointTransformer) {
        pointTransformer.getClass();
        long jM1989transformedso9K2fw = PointKt.m1989transformedso9K2fw(this.center, pointTransformer);
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        int size = this.features.size();
        for (int i = 0; i < size; i++) {
            listCreateListBuilder.add(((Feature) this.features.get(i)).transformed(pointTransformer));
        }
        return new RoundedPolygon(CollectionsKt.build(listCreateListBuilder), jM1989transformedso9K2fw, null);
    }
}
