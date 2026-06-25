package com.android.systemui.plugins.clocks;

import android.graphics.PointF;
import android.graphics.RectF;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FloatCompanionObject;

/* JADX INFO: compiled from: VPoint.kt */
/* JADX INFO: loaded from: classes.dex */
public final class VPointF {
    public static final Companion Companion = new Companion(null);
    private static final long ZERO = m1468constructorimpl(0, 0);
    private final long data;

    /* JADX INFO: compiled from: VPoint.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: div-NvxBqkk, reason: not valid java name */
        public final long m1501divNvxBqkk(float f, long j) {
            return VPointF.m1465constructorimpl(f / VPointF.m1478getXimpl(j), f / VPointF.m1479getYimpl(j));
        }

        /* JADX INFO: renamed from: div-NvxBqkk, reason: not valid java name */
        public final long m1502divNvxBqkk(int i, long j) {
            float f = i;
            return VPointF.m1465constructorimpl(f / VPointF.m1478getXimpl(j), f / VPointF.m1479getYimpl(j));
        }

        /* JADX INFO: renamed from: fromLong--AsyRdg, reason: not valid java name */
        public final long m1503fromLongAsyRdg(long j) {
            return VPointF.m1469constructorimpl(ULong.m2715constructorimpl(j));
        }

        /* JADX INFO: renamed from: getCenter--AsyRdg, reason: not valid java name */
        public final long m1504getCenterAsyRdg(RectF rectF) {
            rectF.getClass();
            return VPointF.m1465constructorimpl(rectF.centerX(), rectF.centerY());
        }

        /* JADX INFO: renamed from: getSize--AsyRdg, reason: not valid java name */
        public final long m1505getSizeAsyRdg(RectF rectF) {
            rectF.getClass();
            return VPointF.m1465constructorimpl(rectF.width(), rectF.height());
        }

        /* JADX INFO: renamed from: getZERO-Jv7bpU8, reason: not valid java name */
        public final long m1506getZEROJv7bpU8() {
            return VPointF.ZERO;
        }

        /* JADX INFO: renamed from: max-5C5yMpM, reason: not valid java name */
        public final long m1507max5C5yMpM(long j, long j2) {
            return VPointF.m1465constructorimpl(Math.max(VPointF.m1478getXimpl(j), VPointF.m1478getXimpl(j2)), Math.max(VPointF.m1479getYimpl(j), VPointF.m1479getYimpl(j2)));
        }

        /* JADX INFO: renamed from: min-5C5yMpM, reason: not valid java name */
        public final long m1508min5C5yMpM(long j, long j2) {
            return VPointF.m1465constructorimpl(Math.min(VPointF.m1478getXimpl(j), VPointF.m1478getXimpl(j2)), Math.min(VPointF.m1479getYimpl(j), VPointF.m1479getYimpl(j2)));
        }

        /* JADX INFO: renamed from: minus-NvxBqkk, reason: not valid java name */
        public final long m1509minusNvxBqkk(float f, long j) {
            return VPointF.m1465constructorimpl(f - VPointF.m1478getXimpl(j), f - VPointF.m1479getYimpl(j));
        }

        /* JADX INFO: renamed from: minus-NvxBqkk, reason: not valid java name */
        public final long m1510minusNvxBqkk(int i, long j) {
            float f = i;
            return VPointF.m1465constructorimpl(f - VPointF.m1478getXimpl(j), f - VPointF.m1479getYimpl(j));
        }

        /* JADX INFO: renamed from: plus-NvxBqkk, reason: not valid java name */
        public final long m1511plusNvxBqkk(float f, long j) {
            return VPointF.m1465constructorimpl(VPointF.m1478getXimpl(j) + f, f + VPointF.m1479getYimpl(j));
        }

        /* JADX INFO: renamed from: times-NvxBqkk, reason: not valid java name */
        public final long m1512timesNvxBqkk(float f, long j) {
            return VPointF.m1465constructorimpl(VPointF.m1478getXimpl(j) * f, f * VPointF.m1479getYimpl(j));
        }

        /* JADX INFO: renamed from: times-NvxBqkk, reason: not valid java name */
        public final long m1513timesNvxBqkk(int i, long j) {
            float f = i;
            return VPointF.m1465constructorimpl(VPointF.m1478getXimpl(j) * f, f * VPointF.m1479getYimpl(j));
        }
    }

    private /* synthetic */ VPointF(long j) {
        this.data = j;
    }

    /* JADX INFO: renamed from: abs-Jv7bpU8, reason: not valid java name */
    public static final long m1461absJv7bpU8(long j) {
        return m1465constructorimpl(Math.abs(m1478getXimpl(j)), Math.abs(m1479getYimpl(j)));
    }

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ VPointF m1462boximpl(long j) {
        return new VPointF(j);
    }

    /* JADX INFO: renamed from: component1-impl, reason: not valid java name */
    public static final float m1463component1impl(long j) {
        return m1478getXimpl(j);
    }

    /* JADX INFO: renamed from: component2-impl, reason: not valid java name */
    public static final float m1464component2impl(long j) {
        return m1479getYimpl(j);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m1465constructorimpl(float f, float f2) {
        return m1469constructorimpl(VPointKt.pack(Float.floatToIntBits(f), Float.floatToIntBits(f2)));
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m1466constructorimpl(float f, int i) {
        return m1465constructorimpl(f, i);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m1467constructorimpl(int i, float f) {
        return m1465constructorimpl(i, f);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m1468constructorimpl(int i, int i2) {
        return m1465constructorimpl(i, i2);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m1469constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m1470constructorimpl(PointF pointF) {
        pointF.getClass();
        return m1465constructorimpl(pointF.x, pointF.y);
    }

    /* JADX INFO: renamed from: div--AsyRdg, reason: not valid java name */
    public static final long m1471divAsyRdg(long j, float f) {
        return m1465constructorimpl(m1478getXimpl(j) / f, m1479getYimpl(j) / f);
    }

    /* JADX INFO: renamed from: div--AsyRdg, reason: not valid java name */
    public static final long m1472divAsyRdg(long j, int i) {
        float f = i;
        return m1465constructorimpl(m1478getXimpl(j) / f, m1479getYimpl(j) / f);
    }

    /* JADX INFO: renamed from: div-b2IjXjg, reason: not valid java name */
    public static final long m1473divb2IjXjg(long j, long j2) {
        return m1465constructorimpl(m1478getXimpl(j) / m1478getXimpl(j2), m1479getYimpl(j) / m1479getYimpl(j2));
    }

    /* JADX INFO: renamed from: div-tHZnGzg, reason: not valid java name */
    public static final long m1474divtHZnGzg(long j, long j2) {
        return m1465constructorimpl(m1478getXimpl(j) / VPoint.m1427getXimpl(j2), m1479getYimpl(j) / VPoint.m1428getYimpl(j2));
    }

    /* JADX INFO: renamed from: dot-78DuFIo, reason: not valid java name */
    public static final float m1475dot78DuFIo(long j, long j2) {
        return (m1478getXimpl(j) * m1478getXimpl(j2)) + (m1479getYimpl(j) * m1479getYimpl(j2));
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m1476equalsimpl(long j, Object obj) {
        return (obj instanceof VPointF) && j == ((VPointF) obj).m1500unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m1477equalsimpl0(long j, long j2) {
        return ULong.m2716equalsimpl0(j, j2);
    }

    /* JADX INFO: renamed from: getX-impl, reason: not valid java name */
    public static final float m1478getXimpl(long j) {
        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
        return Float.intBitsToFloat(VPointKt.m1516unpackXVKZWuLQ(j));
    }

    /* JADX INFO: renamed from: getY-impl, reason: not valid java name */
    public static final float m1479getYimpl(long j) {
        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
        return Float.intBitsToFloat(VPointKt.m1517unpackYVKZWuLQ(j));
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m1480hashCodeimpl(long j) {
        return ULong.m2717hashCodeimpl(j);
    }

    /* JADX INFO: renamed from: length-impl, reason: not valid java name */
    public static final float m1481lengthimpl(long j) {
        return (float) Math.sqrt(m1482lengthSqimpl(j));
    }

    /* JADX INFO: renamed from: lengthSq-impl, reason: not valid java name */
    public static final float m1482lengthSqimpl(long j) {
        return (m1478getXimpl(j) * m1478getXimpl(j)) + (m1479getYimpl(j) * m1479getYimpl(j));
    }

    /* JADX INFO: renamed from: minus--AsyRdg, reason: not valid java name */
    public static final long m1483minusAsyRdg(long j, float f) {
        return m1465constructorimpl(m1478getXimpl(j) - f, m1479getYimpl(j) - f);
    }

    /* JADX INFO: renamed from: minus--AsyRdg, reason: not valid java name */
    public static final long m1484minusAsyRdg(long j, int i) {
        float f = i;
        return m1465constructorimpl(m1478getXimpl(j) - f, m1479getYimpl(j) - f);
    }

    /* JADX INFO: renamed from: minus-b2IjXjg, reason: not valid java name */
    public static final long m1485minusb2IjXjg(long j, long j2) {
        return m1465constructorimpl(m1478getXimpl(j) - m1478getXimpl(j2), m1479getYimpl(j) - m1479getYimpl(j2));
    }

    /* JADX INFO: renamed from: minus-tHZnGzg, reason: not valid java name */
    public static final long m1486minustHZnGzg(long j, long j2) {
        return m1465constructorimpl(m1478getXimpl(j) - VPoint.m1427getXimpl(j2), m1479getYimpl(j) - VPoint.m1428getYimpl(j2));
    }

    /* JADX INFO: renamed from: normalize-Jv7bpU8, reason: not valid java name */
    public static final long m1487normalizeJv7bpU8(long j) {
        float fM1481lengthimpl = m1481lengthimpl(j);
        return m1465constructorimpl(m1478getXimpl(j) / fM1481lengthimpl, m1479getYimpl(j) / fM1481lengthimpl);
    }

    /* JADX INFO: renamed from: plus--AsyRdg, reason: not valid java name */
    public static final long m1488plusAsyRdg(long j, float f) {
        return m1465constructorimpl(m1478getXimpl(j) + f, m1479getYimpl(j) + f);
    }

    /* JADX INFO: renamed from: plus--AsyRdg, reason: not valid java name */
    public static final long m1489plusAsyRdg(long j, int i) {
        float f = i;
        return m1465constructorimpl(m1478getXimpl(j) + f, m1479getYimpl(j) + f);
    }

    /* JADX INFO: renamed from: plus-b2IjXjg, reason: not valid java name */
    public static final long m1490plusb2IjXjg(long j, long j2) {
        return m1465constructorimpl(m1478getXimpl(j) + m1478getXimpl(j2), m1479getYimpl(j) + m1479getYimpl(j2));
    }

    /* JADX INFO: renamed from: plus-tHZnGzg, reason: not valid java name */
    public static final long m1491plustHZnGzg(long j, long j2) {
        return m1465constructorimpl(m1478getXimpl(j) + VPoint.m1427getXimpl(j2), m1479getYimpl(j) + VPoint.m1428getYimpl(j2));
    }

    /* JADX INFO: renamed from: times--AsyRdg, reason: not valid java name */
    public static final long m1492timesAsyRdg(long j, float f) {
        return m1465constructorimpl(m1478getXimpl(j) * f, m1479getYimpl(j) * f);
    }

    /* JADX INFO: renamed from: times--AsyRdg, reason: not valid java name */
    public static final long m1493timesAsyRdg(long j, int i) {
        float f = i;
        return m1465constructorimpl(m1478getXimpl(j) * f, m1479getYimpl(j) * f);
    }

    /* JADX INFO: renamed from: times-b2IjXjg, reason: not valid java name */
    public static final long m1494timesb2IjXjg(long j, long j2) {
        return m1465constructorimpl(m1478getXimpl(j) * m1478getXimpl(j2), m1479getYimpl(j) * m1479getYimpl(j2));
    }

    /* JADX INFO: renamed from: times-tHZnGzg, reason: not valid java name */
    public static final long m1495timestHZnGzg(long j, long j2) {
        return m1465constructorimpl(m1478getXimpl(j) * VPoint.m1427getXimpl(j2), m1479getYimpl(j) * VPoint.m1428getYimpl(j2));
    }

    /* JADX INFO: renamed from: toLong-impl, reason: not valid java name */
    public static final long m1496toLongimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: toPointF-impl, reason: not valid java name */
    public static final PointF m1497toPointFimpl(long j) {
        return new PointF(m1478getXimpl(j), m1479getYimpl(j));
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m1498toStringimpl(long j) {
        return "(" + m1478getXimpl(j) + ", " + m1479getYimpl(j) + ")";
    }

    public boolean equals(Object obj) {
        return m1476equalsimpl(this.data, obj);
    }

    /* JADX INFO: renamed from: getData-s-VKNKU, reason: not valid java name */
    public final long m1499getDatasVKNKU() {
        return this.data;
    }

    public int hashCode() {
        return m1480hashCodeimpl(this.data);
    }

    public String toString() {
        return m1498toStringimpl(this.data);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m1500unboximpl() {
        return this.data;
    }
}
