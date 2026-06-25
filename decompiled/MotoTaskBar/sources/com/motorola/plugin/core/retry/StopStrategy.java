package com.motorola.plugin.core.retry;

import java.util.concurrent.TimeUnit;

/* JADX INFO: compiled from: StopStrategy.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface StopStrategy {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: StopStrategy.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final StopStrategy neverStop() {
            return new StopStrategy() { // from class: com.motorola.plugin.core.retry.StopStrategy$Companion$neverStop$1
                @Override // com.motorola.plugin.core.retry.StopStrategy
                public boolean shouldStop(Attempt attempt) {
                    attempt.getClass();
                    return false;
                }
            };
        }

        public final StopStrategy stopAfterAttempt(final int i) {
            return new StopStrategy() { // from class: com.motorola.plugin.core.retry.StopStrategy$Companion$stopAfterAttempt$1
                @Override // com.motorola.plugin.core.retry.StopStrategy
                public boolean shouldStop(Attempt attempt) {
                    attempt.getClass();
                    return attempt.getAttemptNumber() >= ((long) i);
                }
            };
        }

        public final StopStrategy stopAfterDelay(final long j, final TimeUnit timeUnit) {
            timeUnit.getClass();
            return new StopStrategy() { // from class: com.motorola.plugin.core.retry.StopStrategy$Companion$stopAfterDelay$1
                @Override // com.motorola.plugin.core.retry.StopStrategy
                public boolean shouldStop(Attempt attempt) {
                    attempt.getClass();
                    return attempt.getTimeInMillsSinceFirstAttempt() >= timeUnit.toMillis(j);
                }
            };
        }
    }

    boolean shouldStop(Attempt attempt);
}
