package androidx.compose.ui.node;

import androidx.compose.ui.unit.LayoutDirection;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: TouchBoundsExpansion.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class TouchBoundsExpansion {
    public static final Companion Companion = new Companion(null);
    private static final long None = TouchBoundsExpansionKt.TouchBoundsExpansion$default(0, 0, 0, 0, 14, null);

    /* JADX INFO: compiled from: TouchBoundsExpansion.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final long trimAndShift(int i, int i2) {
            return ((long) (i & 32767)) << (i2 * 15);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final int unpack(long j, int i) {
            return ((int) (j >> (i * 15))) & 32767;
        }

        /* JADX INFO: renamed from: getNone-RZrCHBk, reason: not valid java name */
        public final long m1425getNoneRZrCHBk() {
            return TouchBoundsExpansion.None;
        }

        public final long pack$ui_release(int i, int i2, int i3, int i4, boolean z) {
            return trimAndShift(i2, 1) | trimAndShift(i, 0) | trimAndShift(i3, 2) | trimAndShift(i4, 3) | (z ? Long.MIN_VALUE : 0L);
        }
    }

    /* JADX INFO: renamed from: computeLeft-impl$ui_release, reason: not valid java name */
    public static final int m1417computeLeftimpl$ui_release(long j, LayoutDirection layoutDirection) {
        return (!m1424isLayoutDirectionAwareimpl(j) || layoutDirection == LayoutDirection.Ltr) ? m1422getStartimpl(j) : m1421getEndimpl(j);
    }

    /* JADX INFO: renamed from: computeRight-impl$ui_release, reason: not valid java name */
    public static final int m1418computeRightimpl$ui_release(long j, LayoutDirection layoutDirection) {
        return (!m1424isLayoutDirectionAwareimpl(j) || layoutDirection == LayoutDirection.Ltr) ? m1421getEndimpl(j) : m1422getStartimpl(j);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m1419constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: getBottom-impl, reason: not valid java name */
    public static final int m1420getBottomimpl(long j) {
        return Companion.unpack(j, 3);
    }

    /* JADX INFO: renamed from: getEnd-impl, reason: not valid java name */
    public static final int m1421getEndimpl(long j) {
        return Companion.unpack(j, 2);
    }

    /* JADX INFO: renamed from: getStart-impl, reason: not valid java name */
    public static final int m1422getStartimpl(long j) {
        return Companion.unpack(j, 0);
    }

    /* JADX INFO: renamed from: getTop-impl, reason: not valid java name */
    public static final int m1423getTopimpl(long j) {
        return Companion.unpack(j, 1);
    }

    /* JADX INFO: renamed from: isLayoutDirectionAware-impl, reason: not valid java name */
    public static final boolean m1424isLayoutDirectionAwareimpl(long j) {
        return (j & Long.MIN_VALUE) != 0;
    }
}
