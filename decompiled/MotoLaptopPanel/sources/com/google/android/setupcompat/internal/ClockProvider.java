package com.google.android.setupcompat.internal;

import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public abstract class ClockProvider {
    private static final Ticker SYSTEM_TICKER;
    private static Ticker ticker;

    public interface Supplier {
        Object get();
    }

    static {
        Ticker ticker2 = new Ticker() { // from class: com.google.android.setupcompat.internal.ClockProvider$$ExternalSyntheticLambda0
            @Override // com.google.android.setupcompat.internal.Ticker
            public final long read() {
                return System.nanoTime();
            }
        };
        SYSTEM_TICKER = ticker2;
        ticker = ticker2;
    }

    public static void resetInstance() {
        ticker = SYSTEM_TICKER;
    }

    public static void setInstance(final Supplier supplier) {
        ticker = new Ticker() { // from class: com.google.android.setupcompat.internal.ClockProvider$$ExternalSyntheticLambda1
            @Override // com.google.android.setupcompat.internal.Ticker
            public final long read() {
                return ((Long) supplier.get()).longValue();
            }
        };
    }

    public static long timeInMillis() {
        return TimeUnit.NANOSECONDS.toMillis(timeInNanos());
    }

    public static long timeInNanos() {
        return ticker.read();
    }
}
