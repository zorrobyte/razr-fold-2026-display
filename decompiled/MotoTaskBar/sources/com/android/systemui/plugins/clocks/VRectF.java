package com.android.systemui.plugins.clocks;

import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Half;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: VRect.kt */
/* JADX INFO: loaded from: classes.dex */
public final class VRectF {
    public static final Companion Companion = new Companion(null);
    private static final long ZERO = m1544constructorimpl(0.0f, 0.0f, 0.0f, 0.0f);
    private final long data;

    /* JADX INFO: compiled from: VRect.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final float fromBits(short s) {
            return Half.toFloat(Half.intBitsToHalf(s));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final short toBits(float f) {
            return Half.halfToShortBits(Half.toHalf(f));
        }

        /* JADX INFO: renamed from: fromCenter-cwyIbD4, reason: not valid java name */
        public final long m1564fromCentercwyIbD4(long j, long j2) {
            float f = 2;
            return VRectF.m1544constructorimpl(VPointF.m1478getXimpl(j) - (VPointF.m1478getXimpl(j2) / f), VPointF.m1479getYimpl(j) - (VPointF.m1479getYimpl(j2) / f), VPointF.m1478getXimpl(j) + (VPointF.m1478getXimpl(j2) / f), VPointF.m1479getYimpl(j) + (VPointF.m1479getYimpl(j2) / f));
        }

        /* JADX INFO: renamed from: fromLong-WMibXUk, reason: not valid java name */
        public final long m1565fromLongWMibXUk(long j) {
            return VRectF.m1545constructorimpl(ULong.m2715constructorimpl(j));
        }

        /* JADX INFO: renamed from: fromTopLeft-cwyIbD4, reason: not valid java name */
        public final long m1566fromTopLeftcwyIbD4(long j, long j2) {
            return VRectF.m1544constructorimpl(VPointF.m1478getXimpl(j), VPointF.m1479getYimpl(j), VPointF.m1478getXimpl(j) + VPointF.m1478getXimpl(j2), VPointF.m1479getYimpl(j) + VPointF.m1479getYimpl(j2));
        }

        /* JADX INFO: renamed from: getZERO-3Hl7r_E, reason: not valid java name */
        public final long m1567getZERO3Hl7r_E() {
            return VRectF.ZERO;
        }
    }

    private /* synthetic */ VRectF(long j) {
        this.data = j;
    }

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ VRectF m1543boximpl(long j) {
        return new VRectF(j);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m1544constructorimpl(float f, float f2, float f3, float f4) {
        Companion companion = Companion;
        return m1545constructorimpl(VRectKt.pack(companion.toBits(f), companion.toBits(f2), companion.toBits(f3), companion.toBits(f4)));
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m1545constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m1546constructorimpl(Rect rect) {
        rect.getClass();
        return m1544constructorimpl(rect.left, rect.top, rect.right, rect.bottom);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m1547constructorimpl(RectF rectF) {
        rectF.getClass();
        return m1544constructorimpl(rectF.left, rectF.top, rectF.right, rectF.bottom);
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m1548equalsimpl(long j, Object obj) {
        return (obj instanceof VRectF) && j == ((VRectF) obj).m1563unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m1549equalsimpl0(long j, long j2) {
        return ULong.m2716equalsimpl0(j, j2);
    }

    /* JADX INFO: renamed from: getBottom-impl, reason: not valid java name */
    public static final float m1550getBottomimpl(long j) {
        return Companion.fromBits(VRectKt.m1572unpackBottomVKZWuLQ(j));
    }

    /* JADX INFO: renamed from: getCenter-Jv7bpU8, reason: not valid java name */
    public static final long m1551getCenterJv7bpU8(long j) {
        return VPointF.m1490plusb2IjXjg(VPointF.m1465constructorimpl(m1553getLeftimpl(j), m1556getTopimpl(j)), VPointF.m1471divAsyRdg(m1555getSizeJv7bpU8(j), 2.0f));
    }

    /* JADX INFO: renamed from: getHeight-impl, reason: not valid java name */
    public static final float m1552getHeightimpl(long j) {
        return m1550getBottomimpl(j) - m1556getTopimpl(j);
    }

    /* JADX INFO: renamed from: getLeft-impl, reason: not valid java name */
    public static final float m1553getLeftimpl(long j) {
        return Companion.fromBits(VRectKt.m1573unpackLeftVKZWuLQ(j));
    }

    /* JADX INFO: renamed from: getRight-impl, reason: not valid java name */
    public static final float m1554getRightimpl(long j) {
        return Companion.fromBits(VRectKt.m1574unpackRightVKZWuLQ(j));
    }

    /* JADX INFO: renamed from: getSize-Jv7bpU8, reason: not valid java name */
    public static final long m1555getSizeJv7bpU8(long j) {
        return VPointF.m1465constructorimpl(m1557getWidthimpl(j), m1552getHeightimpl(j));
    }

    /* JADX INFO: renamed from: getTop-impl, reason: not valid java name */
    public static final float m1556getTopimpl(long j) {
        return Companion.fromBits(VRectKt.m1575unpackTopVKZWuLQ(j));
    }

    /* JADX INFO: renamed from: getWidth-impl, reason: not valid java name */
    public static final float m1557getWidthimpl(long j) {
        return m1554getRightimpl(j) - m1553getLeftimpl(j);
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m1558hashCodeimpl(long j) {
        return ULong.m2717hashCodeimpl(j);
    }

    /* JADX INFO: renamed from: toLong-impl, reason: not valid java name */
    public static final long m1559toLongimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: toRectF-impl, reason: not valid java name */
    public static final RectF m1560toRectFimpl(long j) {
        return new RectF(m1553getLeftimpl(j), m1556getTopimpl(j), m1554getRightimpl(j), m1550getBottomimpl(j));
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m1561toStringimpl(long j) {
        return "(" + m1553getLeftimpl(j) + ", " + m1556getTopimpl(j) + ") -> (" + m1554getRightimpl(j) + ", " + m1550getBottomimpl(j) + ")";
    }

    public boolean equals(Object obj) {
        return m1548equalsimpl(this.data, obj);
    }

    /* JADX INFO: renamed from: getData-s-VKNKU, reason: not valid java name */
    public final long m1562getDatasVKNKU() {
        return this.data;
    }

    public int hashCode() {
        return m1558hashCodeimpl(this.data);
    }

    public String toString() {
        return m1561toStringimpl(this.data);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m1563unboximpl() {
        return this.data;
    }
}
