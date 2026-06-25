package com.motorola.plugin.core.retry;

import java.util.concurrent.TimeUnit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.TimeoutKt;

/* JADX INFO: compiled from: AttemptTimeLimiter.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface AttemptTimeLimiter {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: AttemptTimeLimiter.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final AttemptTimeLimiter fixedTimeLimit(final long j, final TimeUnit timeUnit) {
            timeUnit.getClass();
            return new AttemptTimeLimiter() { // from class: com.motorola.plugin.core.retry.AttemptTimeLimiter$Companion$fixedTimeLimit$1
                @Override // com.motorola.plugin.core.retry.AttemptTimeLimiter
                public Object call(Function1 function1, Continuation continuation) {
                    return TimeoutKt.withTimeout(timeUnit.toMillis(j), new AttemptTimeLimiter$Companion$fixedTimeLimit$1$call$2(function1, null), continuation);
                }
            };
        }

        public final AttemptTimeLimiter noTimeLimit() {
            return new AttemptTimeLimiter() { // from class: com.motorola.plugin.core.retry.AttemptTimeLimiter$Companion$noTimeLimit$1
                @Override // com.motorola.plugin.core.retry.AttemptTimeLimiter
                public Object call(Function1 function1, Continuation continuation) {
                    return function1.invoke(continuation);
                }
            };
        }
    }

    Object call(Function1 function1, Continuation continuation);
}
