package kotlinx.coroutines;

import kotlin.coroutines.Continuation;
import kotlinx.coroutines.internal.ScopeCoroutine;

/* JADX INFO: compiled from: Timeout.kt */
/* JADX INFO: loaded from: classes2.dex */
final class TimeoutCoroutine extends ScopeCoroutine implements Runnable {
    public final long time;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TimeoutCoroutine(long j, Continuation continuation) {
        super(continuation.getContext(), continuation);
        continuation.getClass();
        this.time = j;
    }

    @Override // kotlinx.coroutines.AbstractCoroutine, kotlinx.coroutines.JobSupport
    public String nameString$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        return super.nameString$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() + "(timeMillis=" + this.time + ")";
    }

    @Override // java.lang.Runnable
    public void run() {
        cancelCoroutine(TimeoutKt.TimeoutCancellationException(this.time, DelayKt.getDelay(getContext()), this));
    }
}
