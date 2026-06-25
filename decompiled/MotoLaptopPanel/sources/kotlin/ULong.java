package kotlin;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ULong.kt */
/* JADX INFO: loaded from: classes.dex */
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
    public static long m2196constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m2197equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m2198hashCodeimpl(long j) {
        return Long.hashCode(j);
    }
}
