package com.android.systemui.plugins.clocks;

import android.graphics.Rect;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: VRect.kt */
/* JADX INFO: loaded from: classes.dex */
public final class VRect {
    public static final Companion Companion = new Companion(null);
    private static final long ZERO = m1519constructorimpl(0, 0, 0, 0);
    private final long data;

    /* JADX INFO: compiled from: VRect.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: fromCenter-bR_NODY, reason: not valid java name */
        public final long m1539fromCenterbR_NODY(long j, long j2) {
            return VRect.m1522constructorimpl((short) (VPoint.m1427getXimpl(j) - (VPoint.m1427getXimpl(j2) / 2)), (short) (VPoint.m1428getYimpl(j) - (VPoint.m1428getYimpl(j2) / 2)), (short) (VPoint.m1427getXimpl(j) + (VPoint.m1427getXimpl(j2) / 2)), (short) (VPoint.m1428getYimpl(j) + (VPoint.m1428getYimpl(j2) / 2)));
        }

        /* JADX INFO: renamed from: fromLong-qYjogQA, reason: not valid java name */
        public final long m1540fromLongqYjogQA(long j) {
            return VRect.m1520constructorimpl(ULong.m2715constructorimpl(j));
        }

        /* JADX INFO: renamed from: fromTopLeft-bR_NODY, reason: not valid java name */
        public final long m1541fromTopLeftbR_NODY(long j, long j2) {
            return VRect.m1522constructorimpl((short) VPoint.m1427getXimpl(j), (short) VPoint.m1428getYimpl(j), (short) (VPoint.m1427getXimpl(j) + VPoint.m1427getXimpl(j2)), (short) (VPoint.m1428getYimpl(j) + VPoint.m1428getYimpl(j2)));
        }

        /* JADX INFO: renamed from: getZERO-luuqa1s, reason: not valid java name */
        public final long m1542getZEROluuqa1s() {
            return VRect.ZERO;
        }
    }

    private /* synthetic */ VRect(long j) {
        this.data = j;
    }

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ VRect m1518boximpl(long j) {
        return new VRect(j);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m1519constructorimpl(int i, int i2, int i3, int i4) {
        return m1522constructorimpl((short) i, (short) i2, (short) i3, (short) i4);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m1520constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m1521constructorimpl(Rect rect) {
        rect.getClass();
        return m1522constructorimpl((short) rect.left, (short) rect.top, (short) rect.right, (short) rect.bottom);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m1522constructorimpl(short s, short s2, short s3, short s4) {
        return m1520constructorimpl(VRectKt.pack(s, s2, s3, s4));
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m1523equalsimpl(long j, Object obj) {
        return (obj instanceof VRect) && j == ((VRect) obj).m1538unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m1524equalsimpl0(long j, long j2) {
        return ULong.m2716equalsimpl0(j, j2);
    }

    /* JADX INFO: renamed from: getBottom-impl, reason: not valid java name */
    public static final int m1525getBottomimpl(long j) {
        return VRectKt.m1572unpackBottomVKZWuLQ(j);
    }

    /* JADX INFO: renamed from: getCenter-VJhY4ng, reason: not valid java name */
    public static final long m1526getCenterVJhY4ng(long j) {
        return VPoint.m1435plus6OOQ2wA(VPoint.m1419constructorimpl(m1528getLeftimpl(j), m1531getTopimpl(j)), VPoint.m1423divDO4cnVw(m1530getSizeVJhY4ng(j), 2));
    }

    /* JADX INFO: renamed from: getHeight-impl, reason: not valid java name */
    public static final int m1527getHeightimpl(long j) {
        return m1525getBottomimpl(j) - m1531getTopimpl(j);
    }

    /* JADX INFO: renamed from: getLeft-impl, reason: not valid java name */
    public static final int m1528getLeftimpl(long j) {
        return VRectKt.m1573unpackLeftVKZWuLQ(j);
    }

    /* JADX INFO: renamed from: getRight-impl, reason: not valid java name */
    public static final int m1529getRightimpl(long j) {
        return VRectKt.m1574unpackRightVKZWuLQ(j);
    }

    /* JADX INFO: renamed from: getSize-VJhY4ng, reason: not valid java name */
    public static final long m1530getSizeVJhY4ng(long j) {
        return VPoint.m1419constructorimpl(m1532getWidthimpl(j), m1527getHeightimpl(j));
    }

    /* JADX INFO: renamed from: getTop-impl, reason: not valid java name */
    public static final int m1531getTopimpl(long j) {
        return VRectKt.m1575unpackTopVKZWuLQ(j);
    }

    /* JADX INFO: renamed from: getWidth-impl, reason: not valid java name */
    public static final int m1532getWidthimpl(long j) {
        return m1529getRightimpl(j) - m1528getLeftimpl(j);
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m1533hashCodeimpl(long j) {
        return ULong.m2717hashCodeimpl(j);
    }

    /* JADX INFO: renamed from: toLong-impl, reason: not valid java name */
    public static final long m1534toLongimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: toRect-impl, reason: not valid java name */
    public static final Rect m1535toRectimpl(long j) {
        return new Rect(m1528getLeftimpl(j), m1531getTopimpl(j), m1529getRightimpl(j), m1525getBottomimpl(j));
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m1536toStringimpl(long j) {
        return "(" + m1528getLeftimpl(j) + ", " + m1531getTopimpl(j) + ") -> (" + m1529getRightimpl(j) + ", " + m1525getBottomimpl(j) + ")";
    }

    public boolean equals(Object obj) {
        return m1523equalsimpl(this.data, obj);
    }

    /* JADX INFO: renamed from: getData-s-VKNKU, reason: not valid java name */
    public final long m1537getDatasVKNKU() {
        return this.data;
    }

    public int hashCode() {
        return m1533hashCodeimpl(this.data);
    }

    public String toString() {
        return m1536toStringimpl(this.data);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m1538unboximpl() {
        return this.data;
    }
}
