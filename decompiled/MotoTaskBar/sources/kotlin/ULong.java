package kotlin;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ULong.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class ULong implements Comparable {
    public static final Companion Companion = new Companion(null);

    /* JADX INFO: compiled from: ULong.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m2715constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m2716equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m2717hashCodeimpl(long j) {
        return Long.hashCode(j);
    }
}
