package com.android.systemui.plugins.clocks;

import android.graphics.Point;
import android.graphics.Rect;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: VPoint.kt */
/* JADX INFO: loaded from: classes.dex */
public final class VPoint {
    public static final Companion Companion = new Companion(null);
    private static final long ZERO = m1419constructorimpl(0, 0);
    private final long data;

    /* JADX INFO: compiled from: VPoint.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: div-1WsrENI, reason: not valid java name */
        public final long m1447div1WsrENI(float f, long j) {
            return VPointF.m1465constructorimpl(f / VPoint.m1427getXimpl(j), f / VPoint.m1428getYimpl(j));
        }

        /* JADX INFO: renamed from: div-K4Yh7zs, reason: not valid java name */
        public final long m1448divK4Yh7zs(int i, long j) {
            return VPoint.m1419constructorimpl(i / VPoint.m1427getXimpl(j), i / VPoint.m1428getYimpl(j));
        }

        /* JADX INFO: renamed from: fromLong-DO4cnVw, reason: not valid java name */
        public final long m1449fromLongDO4cnVw(long j) {
            return VPoint.m1420constructorimpl(ULong.m2715constructorimpl(j));
        }

        /* JADX INFO: renamed from: getCenter-DO4cnVw, reason: not valid java name */
        public final long m1450getCenterDO4cnVw(Rect rect) {
            rect.getClass();
            return VPoint.m1419constructorimpl(rect.centerX(), rect.centerY());
        }

        /* JADX INFO: renamed from: getSize-DO4cnVw, reason: not valid java name */
        public final long m1451getSizeDO4cnVw(Rect rect) {
            rect.getClass();
            return VPoint.m1419constructorimpl(rect.width(), rect.height());
        }

        /* JADX INFO: renamed from: getZERO-VJhY4ng, reason: not valid java name */
        public final long m1452getZEROVJhY4ng() {
            return VPoint.ZERO;
        }

        /* JADX INFO: renamed from: max-qc1rFFo, reason: not valid java name */
        public final long m1453maxqc1rFFo(long j, long j2) {
            return VPoint.m1419constructorimpl(Math.max(VPoint.m1427getXimpl(j), VPoint.m1427getXimpl(j2)), Math.max(VPoint.m1428getYimpl(j), VPoint.m1428getYimpl(j2)));
        }

        /* JADX INFO: renamed from: min-qc1rFFo, reason: not valid java name */
        public final long m1454minqc1rFFo(long j, long j2) {
            return VPoint.m1419constructorimpl(Math.min(VPoint.m1427getXimpl(j), VPoint.m1427getXimpl(j2)), Math.min(VPoint.m1428getYimpl(j), VPoint.m1428getYimpl(j2)));
        }

        /* JADX INFO: renamed from: minus-1WsrENI, reason: not valid java name */
        public final long m1455minus1WsrENI(float f, long j) {
            return VPointF.m1465constructorimpl(f - VPoint.m1427getXimpl(j), f - VPoint.m1428getYimpl(j));
        }

        /* JADX INFO: renamed from: minus-K4Yh7zs, reason: not valid java name */
        public final long m1456minusK4Yh7zs(int i, long j) {
            return VPoint.m1419constructorimpl(i - VPoint.m1427getXimpl(j), i - VPoint.m1428getYimpl(j));
        }

        /* JADX INFO: renamed from: plus-1WsrENI, reason: not valid java name */
        public final long m1457plus1WsrENI(float f, long j) {
            return VPointF.m1465constructorimpl(VPoint.m1427getXimpl(j) + f, f + VPoint.m1428getYimpl(j));
        }

        /* JADX INFO: renamed from: plus-K4Yh7zs, reason: not valid java name */
        public final long m1458plusK4Yh7zs(int i, long j) {
            return VPoint.m1419constructorimpl(VPoint.m1427getXimpl(j) + i, i + VPoint.m1428getYimpl(j));
        }

        /* JADX INFO: renamed from: times-1WsrENI, reason: not valid java name */
        public final long m1459times1WsrENI(float f, long j) {
            return VPointF.m1465constructorimpl(VPoint.m1427getXimpl(j) * f, f * VPoint.m1428getYimpl(j));
        }

        /* JADX INFO: renamed from: times-K4Yh7zs, reason: not valid java name */
        public final long m1460timesK4Yh7zs(int i, long j) {
            return VPoint.m1419constructorimpl(VPoint.m1427getXimpl(j) * i, i * VPoint.m1428getYimpl(j));
        }
    }

    private /* synthetic */ VPoint(long j) {
        this.data = j;
    }

    /* JADX INFO: renamed from: abs-VJhY4ng, reason: not valid java name */
    public static final long m1415absVJhY4ng(long j) {
        return m1419constructorimpl(Math.abs(m1427getXimpl(j)), Math.abs(m1428getYimpl(j)));
    }

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ VPoint m1416boximpl(long j) {
        return new VPoint(j);
    }

    /* JADX INFO: renamed from: component1-impl, reason: not valid java name */
    public static final int m1417component1impl(long j) {
        return m1427getXimpl(j);
    }

    /* JADX INFO: renamed from: component2-impl, reason: not valid java name */
    public static final int m1418component2impl(long j) {
        return m1428getYimpl(j);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m1419constructorimpl(int i, int i2) {
        return m1420constructorimpl(VPointKt.pack(i, i2));
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m1420constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: div--AsyRdg, reason: not valid java name */
    public static final long m1421divAsyRdg(long j, float f) {
        return VPointF.m1465constructorimpl(m1427getXimpl(j) / f, m1428getYimpl(j) / f);
    }

    /* JADX INFO: renamed from: div-6OOQ2wA, reason: not valid java name */
    public static final long m1422div6OOQ2wA(long j, long j2) {
        return m1419constructorimpl(m1427getXimpl(j) / m1427getXimpl(j2), m1428getYimpl(j) / m1428getYimpl(j2));
    }

    /* JADX INFO: renamed from: div-DO4cnVw, reason: not valid java name */
    public static final long m1423divDO4cnVw(long j, int i) {
        return m1419constructorimpl(m1427getXimpl(j) / i, m1428getYimpl(j) / i);
    }

    /* JADX INFO: renamed from: div-b2IjXjg, reason: not valid java name */
    public static final long m1424divb2IjXjg(long j, long j2) {
        return VPointF.m1465constructorimpl(m1427getXimpl(j) / VPointF.m1478getXimpl(j2), m1428getYimpl(j) / VPointF.m1479getYimpl(j2));
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m1425equalsimpl(long j, Object obj) {
        return (obj instanceof VPoint) && j == ((VPoint) obj).m1446unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m1426equalsimpl0(long j, long j2) {
        return ULong.m2716equalsimpl0(j, j2);
    }

    /* JADX INFO: renamed from: getX-impl, reason: not valid java name */
    public static final int m1427getXimpl(long j) {
        return VPointKt.m1516unpackXVKZWuLQ(j);
    }

    /* JADX INFO: renamed from: getY-impl, reason: not valid java name */
    public static final int m1428getYimpl(long j) {
        return VPointKt.m1517unpackYVKZWuLQ(j);
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m1429hashCodeimpl(long j) {
        return ULong.m2717hashCodeimpl(j);
    }

    /* JADX INFO: renamed from: minus--AsyRdg, reason: not valid java name */
    public static final long m1430minusAsyRdg(long j, float f) {
        return VPointF.m1465constructorimpl(m1427getXimpl(j) - f, m1428getYimpl(j) - f);
    }

    /* JADX INFO: renamed from: minus-6OOQ2wA, reason: not valid java name */
    public static final long m1431minus6OOQ2wA(long j, long j2) {
        return m1419constructorimpl(m1427getXimpl(j) - m1427getXimpl(j2), m1428getYimpl(j) - m1428getYimpl(j2));
    }

    /* JADX INFO: renamed from: minus-DO4cnVw, reason: not valid java name */
    public static final long m1432minusDO4cnVw(long j, int i) {
        return m1419constructorimpl(m1427getXimpl(j) - i, m1428getYimpl(j) - i);
    }

    /* JADX INFO: renamed from: minus-b2IjXjg, reason: not valid java name */
    public static final long m1433minusb2IjXjg(long j, long j2) {
        return VPointF.m1465constructorimpl(m1427getXimpl(j) - VPointF.m1478getXimpl(j2), m1428getYimpl(j) - VPointF.m1479getYimpl(j2));
    }

    /* JADX INFO: renamed from: plus--AsyRdg, reason: not valid java name */
    public static final long m1434plusAsyRdg(long j, float f) {
        return VPointF.m1465constructorimpl(m1427getXimpl(j) + f, m1428getYimpl(j) + f);
    }

    /* JADX INFO: renamed from: plus-6OOQ2wA, reason: not valid java name */
    public static final long m1435plus6OOQ2wA(long j, long j2) {
        return m1419constructorimpl(m1427getXimpl(j) + m1427getXimpl(j2), m1428getYimpl(j) + m1428getYimpl(j2));
    }

    /* JADX INFO: renamed from: plus-DO4cnVw, reason: not valid java name */
    public static final long m1436plusDO4cnVw(long j, int i) {
        return m1419constructorimpl(m1427getXimpl(j) + i, m1428getYimpl(j) + i);
    }

    /* JADX INFO: renamed from: plus-b2IjXjg, reason: not valid java name */
    public static final long m1437plusb2IjXjg(long j, long j2) {
        return VPointF.m1465constructorimpl(m1427getXimpl(j) + VPointF.m1478getXimpl(j2), m1428getYimpl(j) + VPointF.m1479getYimpl(j2));
    }

    /* JADX INFO: renamed from: times--AsyRdg, reason: not valid java name */
    public static final long m1438timesAsyRdg(long j, float f) {
        return VPointF.m1465constructorimpl(m1427getXimpl(j) * f, m1428getYimpl(j) * f);
    }

    /* JADX INFO: renamed from: times-6OOQ2wA, reason: not valid java name */
    public static final long m1439times6OOQ2wA(long j, long j2) {
        return m1419constructorimpl(m1427getXimpl(j) * m1427getXimpl(j2), m1428getYimpl(j) * m1428getYimpl(j2));
    }

    /* JADX INFO: renamed from: times-DO4cnVw, reason: not valid java name */
    public static final long m1440timesDO4cnVw(long j, int i) {
        return m1419constructorimpl(m1427getXimpl(j) * i, m1428getYimpl(j) * i);
    }

    /* JADX INFO: renamed from: times-b2IjXjg, reason: not valid java name */
    public static final long m1441timesb2IjXjg(long j, long j2) {
        return VPointF.m1465constructorimpl(m1427getXimpl(j) * VPointF.m1478getXimpl(j2), m1428getYimpl(j) * VPointF.m1479getYimpl(j2));
    }

    /* JADX INFO: renamed from: toLong-impl, reason: not valid java name */
    public static final long m1442toLongimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: toPoint-impl, reason: not valid java name */
    public static final Point m1443toPointimpl(long j) {
        return new Point(m1427getXimpl(j), m1428getYimpl(j));
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m1444toStringimpl(long j) {
        return "(" + m1427getXimpl(j) + ", " + m1428getYimpl(j) + ")";
    }

    public boolean equals(Object obj) {
        return m1425equalsimpl(this.data, obj);
    }

    /* JADX INFO: renamed from: getData-s-VKNKU, reason: not valid java name */
    public final long m1445getDatasVKNKU() {
        return this.data;
    }

    public int hashCode() {
        return m1429hashCodeimpl(this.data);
    }

    public String toString() {
        return m1444toStringimpl(this.data);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m1446unboximpl() {
        return this.data;
    }
}
