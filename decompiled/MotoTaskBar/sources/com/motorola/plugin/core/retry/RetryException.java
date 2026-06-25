package com.motorola.plugin.core.retry;

/* JADX INFO: compiled from: RetryException.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class RetryException extends Exception {
    private final Attempt lastFailedAttempt;
    private final int numberOfFailedAttempts;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public RetryException(int i, Attempt attempt) {
        this("Retrying failed to complete successfully after " + i + " attempts.", i, attempt);
        attempt.getClass();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RetryException(String str, int i, Attempt attempt) {
        super(str, attempt.isException() ? attempt.getExceptionCause() : null);
        attempt.getClass();
        this.numberOfFailedAttempts = i;
        this.lastFailedAttempt = attempt;
    }

    public final Attempt getLastFailedAttempt() {
        return this.lastFailedAttempt;
    }

    public final int getNumberOfFailedAttempts() {
        return this.numberOfFailedAttempts;
    }
}
