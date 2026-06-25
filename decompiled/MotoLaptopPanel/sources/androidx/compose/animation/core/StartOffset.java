package androidx.compose.animation.core;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: AnimationSpec.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class StartOffset {
    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m49constructorimpl(int i, int i2) {
        return m50constructorimpl(i * i2);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    private static long m50constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: constructor-impl$default, reason: not valid java name */
    public static /* synthetic */ long m51constructorimpl$default(int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i3 & 2) != 0) {
            i2 = StartOffsetType.Companion.m55getDelayEo1U57Q();
        }
        return m49constructorimpl(i, i2);
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m52equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m53hashCodeimpl(long j) {
        return Long.hashCode(j);
    }
}
