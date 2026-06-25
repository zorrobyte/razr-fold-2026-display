package com.motorola.plugin.core.retry;

/* JADX INFO: compiled from: Attempt.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface Attempt {
    Object get();

    long getAttemptNumber();

    Throwable getExceptionCause();

    Object getResult();

    long getTimeInMillsSinceFirstAttempt();

    boolean isException();

    boolean isResult();
}
