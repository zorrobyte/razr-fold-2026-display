package com.motorola.plugin.core.retry;

import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: SimplyRetryer.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class SimplyRetryer {
    private long counter;
    private final int maxCount;

    public SimplyRetryer() {
        this(0, 1, null);
    }

    public SimplyRetryer(int i) {
        this.maxCount = i;
    }

    public /* synthetic */ SimplyRetryer(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 20 : i);
    }

    public final boolean canRetry() {
        boolean z;
        synchronized (this) {
            z = this.counter < ((long) this.maxCount);
        }
        return z;
    }

    public final long failed() {
        long j;
        synchronized (this) {
            j = this.counter;
            this.counter = 1 + j;
        }
        return j;
    }

    public final void reset() {
        synchronized (this) {
            this.counter = 0L;
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void success() {
        synchronized (this) {
            this.counter = 0L;
            Unit unit = Unit.INSTANCE;
        }
    }
}
