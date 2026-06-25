package kotlin;

import kotlin.Result;

/* JADX INFO: compiled from: Result.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ResultKt {
    public static final Object createFailure(Throwable th) {
        th.getClass();
        return new Result.Failure(th);
    }

    public static final void throwOnFailure(Object obj) {
        if (obj instanceof Result.Failure) {
            throw ((Result.Failure) obj).exception;
        }
    }
}
