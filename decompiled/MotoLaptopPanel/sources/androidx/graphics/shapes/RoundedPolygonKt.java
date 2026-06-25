package androidx.graphics.shapes;

import androidx.collection.FloatFloatPair;
import androidx.collection.MutableFloatList;
import androidx.graphics.shapes.Feature;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: RoundedPolygon.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class RoundedPolygonKt {
    public static final RoundedPolygon RoundedPolygon(int i, float f, float f2, float f3, CornerRounding cornerRounding) {
        cornerRounding.getClass();
        return RoundedPolygon$default(i, f, f2, f3, cornerRounding, null, 32, null);
    }

    public static final RoundedPolygon RoundedPolygon(int i, float f, float f2, float f3, CornerRounding cornerRounding, List list) {
        cornerRounding.getClass();
        return RoundedPolygon(verticesFromNumVerts(i, f, f2, f3), cornerRounding, list, f2, f3);
    }

    public static final RoundedPolygon RoundedPolygon(List list, float f, float f2) {
        list.getClass();
        if (list.size() < 2) {
            throw new IllegalArgumentException("Polygons must have at least 2 features");
        }
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            for (Cubic cubic : ((Feature) it.next()).getCubics()) {
                listCreateListBuilder.add(Float.valueOf(cubic.getAnchor0X()));
                listCreateListBuilder.add(Float.valueOf(cubic.getAnchor0Y()));
            }
        }
        float[] floatArray = CollectionsKt.toFloatArray(CollectionsKt.build(listCreateListBuilder));
        if (Float.isNaN(f)) {
            f = Float.intBitsToFloat((int) (calculateCenter(floatArray) >> 32));
        }
        if (Float.isNaN(f2)) {
            f2 = Float.intBitsToFloat((int) (calculateCenter(floatArray) & 4294967295L));
        }
        return new RoundedPolygon(list, FloatFloatPair.m11constructorimpl(f, f2), null);
    }

    public static final RoundedPolygon RoundedPolygon(float[] fArr, CornerRounding cornerRounding, List list, float f, float f2) {
        CornerRounding cornerRounding2;
        fArr.getClass();
        cornerRounding.getClass();
        if (fArr.length < 6) {
            throw new IllegalArgumentException("Polygons must have at least 3 vertices");
        }
        if (fArr.length % 2 == 1) {
            throw new IllegalArgumentException("The vertices array should have even size");
        }
        if (list != null && list.size() * 2 != fArr.length) {
            throw new IllegalArgumentException("perVertexRounding list should be either null or the same size as the number of vertices (vertices.size / 2)");
        }
        ArrayList arrayList = new ArrayList();
        int length = fArr.length / 2;
        ArrayList arrayList2 = new ArrayList();
        int i = 0;
        int i2 = 0;
        while (i2 < length) {
            CornerRounding cornerRounding3 = (list == null || (cornerRounding2 = (CornerRounding) list.get(i2)) == null) ? cornerRounding : cornerRounding2;
            int i3 = (((i2 + length) - 1) % length) * 2;
            int i4 = i2 + 1;
            int i5 = (i4 % length) * 2;
            int i6 = i2 * 2;
            arrayList2.add(new RoundedCorner(FloatFloatPair.m11constructorimpl(fArr[i3], fArr[i3 + 1]), FloatFloatPair.m11constructorimpl(fArr[i6], fArr[i6 + 1]), FloatFloatPair.m11constructorimpl(fArr[i5], fArr[i5 + 1]), cornerRounding3, null));
            i2 = i4;
        }
        IntRange intRangeUntil = RangesKt.until(0, length);
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRangeUntil, 10));
        Iterator it = intRangeUntil.iterator();
        while (it.hasNext()) {
            int iNextInt = ((IntIterator) it).nextInt();
            int i7 = (iNextInt + 1) % length;
            float expectedRoundCut = ((RoundedCorner) arrayList2.get(iNextInt)).getExpectedRoundCut() + ((RoundedCorner) arrayList2.get(i7)).getExpectedRoundCut();
            float expectedCut = ((RoundedCorner) arrayList2.get(iNextInt)).getExpectedCut() + ((RoundedCorner) arrayList2.get(i7)).getExpectedCut();
            int i8 = iNextInt * 2;
            int i9 = i7 * 2;
            float fDistance = Utils.distance(fArr[i8] - fArr[i9], fArr[i8 + 1] - fArr[i9 + 1]);
            arrayList3.add(expectedRoundCut > fDistance ? TuplesKt.to(Float.valueOf(fDistance / expectedRoundCut), Float.valueOf(0.0f)) : expectedCut > fDistance ? TuplesKt.to(Float.valueOf(1.0f), Float.valueOf((fDistance - expectedRoundCut) / (expectedCut - expectedRoundCut))) : TuplesKt.to(Float.valueOf(1.0f), Float.valueOf(1.0f)));
        }
        for (int i10 = 0; i10 < length; i10++) {
            MutableFloatList mutableFloatList = new MutableFloatList(2);
            for (int i11 = 0; i11 < 2; i11++) {
                Pair pair = (Pair) arrayList3.get((((i10 + length) - 1) + i11) % length);
                mutableFloatList.add((((RoundedCorner) arrayList2.get(i10)).getExpectedRoundCut() * ((Number) pair.component1()).floatValue()) + ((((RoundedCorner) arrayList2.get(i10)).getExpectedCut() - ((RoundedCorner) arrayList2.get(i10)).getExpectedRoundCut()) * ((Number) pair.component2()).floatValue()));
            }
            arrayList.add(((RoundedCorner) arrayList2.get(i10)).getCubics(mutableFloatList.get(0), mutableFloatList.get(1)));
        }
        ArrayList arrayList4 = new ArrayList();
        while (i < length) {
            int i12 = i + 1;
            int i13 = i12 % length;
            int i14 = i * 2;
            long jM11constructorimpl = FloatFloatPair.m11constructorimpl(fArr[i14], fArr[i14 + 1]);
            int i15 = (((i + length) - 1) % length) * 2;
            long jM11constructorimpl2 = FloatFloatPair.m11constructorimpl(fArr[i15], fArr[i15 + 1]);
            int i16 = i13 * 2;
            arrayList4.add(new Feature.Corner((List) arrayList.get(i), Utils.m1993convexb22R3LQ(jM11constructorimpl2, jM11constructorimpl, FloatFloatPair.m11constructorimpl(fArr[i16], fArr[i16 + 1]))));
            arrayList4.add(new Feature.Edge(CollectionsKt.listOf(Cubic.Companion.straightLine(((Cubic) CollectionsKt.last((List) arrayList.get(i))).getAnchor1X(), ((Cubic) CollectionsKt.last((List) arrayList.get(i))).getAnchor1Y(), ((Cubic) CollectionsKt.first((List) arrayList.get(i13))).getAnchor0X(), ((Cubic) CollectionsKt.first((List) arrayList.get(i13))).getAnchor0Y()))));
            i = i12;
        }
        long jCalculateCenter = (f == Float.MIN_VALUE || f2 == Float.MIN_VALUE) ? calculateCenter(fArr) : FloatFloatPair.m11constructorimpl(f, f2);
        return RoundedPolygon(arrayList4, Float.intBitsToFloat((int) (jCalculateCenter >> 32)), Float.intBitsToFloat((int) (jCalculateCenter & 4294967295L)));
    }

    public static /* synthetic */ RoundedPolygon RoundedPolygon$default(int i, float f, float f2, float f3, CornerRounding cornerRounding, List list, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            f = 1.0f;
        }
        if ((i2 & 4) != 0) {
            f2 = 0.0f;
        }
        if ((i2 & 8) != 0) {
            f3 = 0.0f;
        }
        if ((i2 & 16) != 0) {
            cornerRounding = CornerRounding.Unrounded;
        }
        if ((i2 & 32) != 0) {
            list = null;
        }
        List list2 = list;
        return RoundedPolygon(i, f, f2, f3, cornerRounding, list2);
    }

    public static final long calculateCenter(float[] fArr) {
        fArr.getClass();
        float f = 0.0f;
        int i = 0;
        float f2 = 0.0f;
        while (i < fArr.length) {
            int i2 = i + 1;
            f += fArr[i];
            i += 2;
            f2 += fArr[i2];
        }
        return FloatFloatPair.m11constructorimpl(f / (fArr.length / 2), f2 / (fArr.length / 2));
    }

    private static final float[] verticesFromNumVerts(int i, float f, float f2, float f3) {
        float[] fArr = new float[i * 2];
        int i2 = 0;
        int i3 = 0;
        while (i2 < i) {
            float f4 = f;
            long jM1987plusybeJwSQ = PointKt.m1987plusybeJwSQ(Utils.m1995radialToCartesianL6JJ3z0$default(f4, (Utils.getFloatPi() / i) * 2 * i2, 0L, 4, null), FloatFloatPair.m11constructorimpl(f2, f3));
            int i4 = i3 + 1;
            fArr[i3] = PointKt.m1983getXDnnuFBc(jM1987plusybeJwSQ);
            i3 += 2;
            fArr[i4] = PointKt.m1984getYDnnuFBc(jM1987plusybeJwSQ);
            i2++;
            f = f4;
        }
        return fArr;
    }
}
