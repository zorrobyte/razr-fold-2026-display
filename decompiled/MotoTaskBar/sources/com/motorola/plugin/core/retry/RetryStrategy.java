package com.motorola.plugin.core.retry;

import java.util.function.Predicate;

/* JADX INFO: compiled from: RetryStrategy.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface RetryStrategy {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: RetryStrategy.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final RetryStrategy ifException(final Predicate predicate) {
            predicate.getClass();
            return new RetryStrategy() { // from class: com.motorola.plugin.core.retry.RetryStrategy$Companion$ifException$1
                @Override // com.motorola.plugin.core.retry.RetryStrategy
                public boolean match(Attempt attempt) {
                    attempt.getClass();
                    if (!attempt.isException()) {
                        return false;
                    }
                    Predicate predicate2 = predicate;
                    Throwable exceptionCause = attempt.getExceptionCause();
                    exceptionCause.getClass();
                    return predicate2.test(exceptionCause);
                }
            };
        }

        public final RetryStrategy ifExceptionClass(final Class cls) {
            cls.getClass();
            return new RetryStrategy() { // from class: com.motorola.plugin.core.retry.RetryStrategy$Companion$ifExceptionClass$1
                @Override // com.motorola.plugin.core.retry.RetryStrategy
                public boolean match(Attempt attempt) {
                    attempt.getClass();
                    if (!attempt.isException()) {
                        return false;
                    }
                    Class cls2 = cls;
                    Throwable exceptionCause = attempt.getExceptionCause();
                    exceptionCause.getClass();
                    return cls2.isAssignableFrom(exceptionCause.getClass());
                }
            };
        }

        public final RetryStrategy ifResult(final Predicate predicate) {
            predicate.getClass();
            return new RetryStrategy() { // from class: com.motorola.plugin.core.retry.RetryStrategy$Companion$ifResult$1
                @Override // com.motorola.plugin.core.retry.RetryStrategy
                public boolean match(Attempt attempt) {
                    attempt.getClass();
                    if (!attempt.isResult()) {
                        return false;
                    }
                    return predicate.test(attempt.getResult());
                }
            };
        }

        public final RetryStrategy neverTry() {
            return new RetryStrategy() { // from class: com.motorola.plugin.core.retry.RetryStrategy$Companion$neverTry$1
                @Override // com.motorola.plugin.core.retry.RetryStrategy
                public boolean match(Attempt attempt) {
                    attempt.getClass();
                    return false;
                }
            };
        }
    }

    boolean match(Attempt attempt);
}
