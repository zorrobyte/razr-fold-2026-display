package com.motorola.plugin.core.retry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: RetryerBuilder.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class RetryerBuilder {
    public static final Companion Companion = new Companion(null);
    private AttemptTimeLimiter attemptTimeLimiter;
    private BlockStrategy blockStrategy;
    private final List listeners;
    private RetryStrategy retryStrategy;
    private StopStrategy stopStrategy;
    private WaitStrategy waitStrategy;

    /* JADX INFO: compiled from: RetryerBuilder.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final RetryerBuilder newBuilder() {
            return new RetryerBuilder(null);
        }
    }

    private RetryerBuilder() {
        this.retryStrategy = RetryStrategy.Companion.neverTry();
        this.listeners = new ArrayList();
    }

    public /* synthetic */ RetryerBuilder(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: retryIfResultNull$lambda-0, reason: not valid java name */
    public static final boolean m2225retryIfResultNull$lambda0(Object obj) {
        return obj == null;
    }

    public final Retryer build() {
        AttemptTimeLimiter attemptTimeLimiterNoTimeLimit = this.attemptTimeLimiter;
        if (attemptTimeLimiterNoTimeLimit == null) {
            attemptTimeLimiterNoTimeLimit = AttemptTimeLimiter.Companion.noTimeLimit();
        }
        AttemptTimeLimiter attemptTimeLimiter = attemptTimeLimiterNoTimeLimit;
        StopStrategy stopStrategyNeverStop = this.stopStrategy;
        if (stopStrategyNeverStop == null) {
            stopStrategyNeverStop = StopStrategy.Companion.neverStop();
        }
        StopStrategy stopStrategy = stopStrategyNeverStop;
        WaitStrategy waitStrategyNoWait = this.waitStrategy;
        if (waitStrategyNoWait == null) {
            waitStrategyNoWait = WaitStrategy.Companion.noWait();
        }
        WaitStrategy waitStrategy = waitStrategyNoWait;
        BlockStrategy blockStrategyCoroutineDelayStrategy = this.blockStrategy;
        if (blockStrategyCoroutineDelayStrategy == null) {
            blockStrategyCoroutineDelayStrategy = BlockStrategy.Companion.coroutineDelayStrategy();
        }
        return new Retryer(attemptTimeLimiter, stopStrategy, waitStrategy, blockStrategyCoroutineDelayStrategy, this.retryStrategy, this.listeners);
    }

    public final RetryerBuilder retryIf(RetryStrategy retryStrategy) {
        retryStrategy.getClass();
        this.retryStrategy = RetryStrategyKt.plus(this.retryStrategy, retryStrategy);
        return this;
    }

    public final RetryerBuilder retryIfException() {
        this.retryStrategy = RetryStrategyKt.plus(this.retryStrategy, RetryStrategy.Companion.ifExceptionClass(Exception.class));
        return this;
    }

    public final RetryerBuilder retryIfException(Predicate predicate) {
        predicate.getClass();
        this.retryStrategy = RetryStrategyKt.plus(this.retryStrategy, RetryStrategy.Companion.ifException(predicate));
        return this;
    }

    public final RetryerBuilder retryIfExceptionOfType(Class cls) {
        cls.getClass();
        this.retryStrategy = RetryStrategyKt.plus(this.retryStrategy, RetryStrategy.Companion.ifExceptionClass(cls));
        return this;
    }

    public final RetryerBuilder retryIfResult(Predicate predicate) {
        predicate.getClass();
        this.retryStrategy = RetryStrategyKt.plus(this.retryStrategy, RetryStrategy.Companion.ifResult(predicate));
        return this;
    }

    public final RetryerBuilder retryIfResultNull() {
        this.retryStrategy = RetryStrategyKt.plus(this.retryStrategy, RetryStrategy.Companion.ifResult(new Predicate() { // from class: com.motorola.plugin.core.retry.RetryerBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return RetryerBuilder.m2225retryIfResultNull$lambda0(obj);
            }
        }));
        return this;
    }

    public final RetryerBuilder retryIfRuntimeException() {
        this.retryStrategy = RetryStrategyKt.plus(this.retryStrategy, RetryStrategy.Companion.ifExceptionClass(RuntimeException.class));
        return this;
    }

    public final RetryerBuilder withAttemptTimeLimiter(AttemptTimeLimiter attemptTimeLimiter) {
        attemptTimeLimiter.getClass();
        this.attemptTimeLimiter = attemptTimeLimiter;
        return this;
    }

    public final RetryerBuilder withBlockStrategy(BlockStrategy blockStrategy) {
        blockStrategy.getClass();
        this.blockStrategy = blockStrategy;
        return this;
    }

    public final RetryerBuilder withRetryListener(Function1 function1) {
        function1.getClass();
        this.listeners.add(function1);
        return this;
    }

    public final RetryerBuilder withStopStrategy(StopStrategy stopStrategy) {
        stopStrategy.getClass();
        this.stopStrategy = stopStrategy;
        return this;
    }

    public final RetryerBuilder withWaitStrategy(WaitStrategy waitStrategy) {
        waitStrategy.getClass();
        this.waitStrategy = waitStrategy;
        return this;
    }
}
