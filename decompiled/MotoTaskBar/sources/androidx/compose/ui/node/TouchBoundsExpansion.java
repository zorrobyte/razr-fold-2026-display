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
        public final long m679getNoneRZrCHBk() {
            return TouchBoundsExpansion.None;
        }

        public final long pack$ui_release(int i, int i2, int i3, int i4, boolean z) {
            return trimAndShift(i2, 1) | trimAndShift(i, 0) | trimAndShift(i3, 2) | trimAndShift(i4, 3) | (z ? Long.MIN_VALUE : 0L);
        }
    }

    /* JADX INFO: renamed from: computeLeft-impl$ui_release, reason: not valid java name */
    public static final int m671computeLeftimpl$ui_release(long j, LayoutDirection layoutDirection) {
        return (!m678isLayoutDirectionAwareimpl(j) || layoutDirection == LayoutDirection.Ltr) ? m676getStartimpl(j) : m675getEndimpl(j);
    }

    /* JADX INFO: renamed from: computeRight-impl$ui_release, reason: not valid java name */
    public static final int m672computeRightimpl$ui_release(long j, LayoutDirection layoutDirection) {
        return (!m678isLayoutDirectionAwareimpl(j) || layoutDirection == LayoutDirection.Ltr) ? m675getEndimpl(j) : m676getStartimpl(j);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m673constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: getBottom-impl, reason: not valid java name */
    public static final int m674getBottomimpl(long j) {
        return Companion.unpack(j, 3);
    }

    /* JADX INFO: renamed from: getEnd-impl, reason: not valid java name */
    public static final int m675getEndimpl(long j) {
        return Companion.unpack(j, 2);
    }

    /* JADX INFO: renamed from: getStart-impl, reason: not valid java name */
    public static final int m676getStartimpl(long j) {
        return Companion.unpack(j, 0);
    }

    /* JADX INFO: renamed from: getTop-impl, reason: not valid java name */
    public static final int m677getTopimpl(long j) {
        return Companion.unpack(j, 1);
    }

    /* JADX INFO: renamed from: isLayoutDirectionAware-impl, reason: not valid java name */
    public static final boolean m678isLayoutDirectionAwareimpl(long j) {
        return (j & Long.MIN_VALUE) != 0;
    }
}
