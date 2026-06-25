package com.motorola.plugin.core.retry;

/* JADX INFO: compiled from: WaitStrategy.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class WaitStrategyKt {
    public static final WaitStrategy plus(final WaitStrategy waitStrategy, final WaitStrategy waitStrategy2) {
        waitStrategy.getClass();
        waitStrategy2.getClass();
        return new WaitStrategy() { // from class: com.motorola.plugin.core.retry.WaitStrategyKt.plus.1
            @Override // com.motorola.plugin.core.retry.WaitStrategy
            public long computeSleepTime(Attempt attempt) {
                attempt.getClass();
                return waitStrategy.computeSleepTime(attempt) + waitStrategy2.computeSleepTime(attempt);
            }
        };
    }
}
