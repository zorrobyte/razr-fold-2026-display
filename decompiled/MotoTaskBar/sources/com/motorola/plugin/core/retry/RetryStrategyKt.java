package com.motorola.plugin.core.retry;

/* JADX INFO: compiled from: RetryStrategy.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class RetryStrategyKt {
    public static final RetryStrategy plus(final RetryStrategy retryStrategy, final RetryStrategy retryStrategy2) {
        retryStrategy.getClass();
        retryStrategy2.getClass();
        return new RetryStrategy() { // from class: com.motorola.plugin.core.retry.RetryStrategyKt.plus.1
            @Override // com.motorola.plugin.core.retry.RetryStrategy
            public boolean match(Attempt attempt) {
                attempt.getClass();
                return retryStrategy.match(attempt) || retryStrategy2.match(attempt);
            }
        };
    }
}
