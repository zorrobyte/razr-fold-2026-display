package androidx.compose.animation.core;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpOffset;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlin.jvm.internal.IntCompanionObject;

/* JADX INFO: compiled from: VisibilityThresholds.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class VisibilityThresholdsKt {
    private static final Rect RectVisibilityThreshold;
    private static final Map VisibilityThresholdMap;

    static {
        Float fValueOf = Float.valueOf(0.5f);
        RectVisibilityThreshold = new Rect(0.5f, 0.5f, 0.5f, 0.5f);
        TwoWayConverter vectorConverter = VectorConvertersKt.getVectorConverter(IntCompanionObject.INSTANCE);
        Float fValueOf2 = Float.valueOf(1.0f);
        Pair pair = TuplesKt.to(vectorConverter, fValueOf2);
        Pair pair2 = TuplesKt.to(VectorConvertersKt.getVectorConverter(IntSize.Companion), fValueOf2);
        Pair pair3 = TuplesKt.to(VectorConvertersKt.getVectorConverter(IntOffset.Companion), fValueOf2);
        Pair pair4 = TuplesKt.to(VectorConvertersKt.getVectorConverter(FloatCompanionObject.INSTANCE), Float.valueOf(0.01f));
        Pair pair5 = TuplesKt.to(VectorConvertersKt.getVectorConverter(Rect.Companion), fValueOf);
        Pair pair6 = TuplesKt.to(VectorConvertersKt.getVectorConverter(Size.Companion), fValueOf);
        Pair pair7 = TuplesKt.to(VectorConvertersKt.getVectorConverter(Offset.Companion), fValueOf);
        TwoWayConverter vectorConverter2 = VectorConvertersKt.getVectorConverter(Dp.Companion);
        Float fValueOf3 = Float.valueOf(0.1f);
        VisibilityThresholdMap = MapsKt.mapOf(pair, pair2, pair3, pair4, pair5, pair6, pair7, TuplesKt.to(vectorConverter2, fValueOf3), TuplesKt.to(VectorConvertersKt.getVectorConverter(DpOffset.Companion), fValueOf3));
    }

    public static final float getVisibilityThreshold(Dp.Companion companion) {
        return Dp.m1877constructorimpl(0.1f);
    }

    public static final int getVisibilityThreshold(IntCompanionObject intCompanionObject) {
        return 1;
    }

    public static final long getVisibilityThreshold(Offset.Companion companion) {
        return Offset.m752constructorimpl((((long) Float.floatToRawIntBits(0.5f)) << 32) | (((long) Float.floatToRawIntBits(0.5f)) & 4294967295L));
    }

    public static final long getVisibilityThreshold(Size.Companion companion) {
        return Size.m783constructorimpl((((long) Float.floatToRawIntBits(0.5f)) << 32) | (((long) Float.floatToRawIntBits(0.5f)) & 4294967295L));
    }

    public static final long getVisibilityThreshold(IntOffset.Companion companion) {
        long j = 1;
        return IntOffset.m1902constructorimpl((j & 4294967295L) | (j << 32));
    }

    public static final long getVisibilityThreshold(IntSize.Companion companion) {
        long j = 1;
        return IntSize.m1919constructorimpl((j & 4294967295L) | (j << 32));
    }

    public static final Rect getVisibilityThreshold(Rect.Companion companion) {
        return RectVisibilityThreshold;
    }

    public static final Map getVisibilityThresholdMap() {
        return VisibilityThresholdMap;
    }
}
