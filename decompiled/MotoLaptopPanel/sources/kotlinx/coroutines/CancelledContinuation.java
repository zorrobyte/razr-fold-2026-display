package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.coroutines.Continuation;
import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.atomicfu.AtomicFU;

/* JADX INFO: compiled from: CompletionState.kt */
/* JADX INFO: loaded from: classes.dex */
public final class CancelledContinuation extends CompletedExceptionally {
    private final AtomicBoolean _resumed;

    public CancelledContinuation(Continuation continuation, Throwable th, boolean z) {
        continuation.getClass();
        if (th == null) {
            th = new CancellationException("Continuation " + continuation + " was cancelled normally");
        }
        super(th, z);
        this._resumed = AtomicFU.atomic(false);
    }

    public final boolean makeResumed() {
        return this._resumed.compareAndSet(false, true);
    }
}
