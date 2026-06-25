package kotlinx.coroutines;

import kotlin.jvm.functions.Function1;
import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.atomicfu.AtomicFU;

/* JADX INFO: compiled from: JobSupport.kt */
/* JADX INFO: loaded from: classes.dex */
final class InvokeOnCancelling extends JobNode {
    private final AtomicBoolean _invoked;
    private final Function1 handler;

    public InvokeOnCancelling(Function1 function1) {
        function1.getClass();
        this.handler = function1;
        this._invoked = AtomicFU.atomic(false);
    }

    @Override // kotlinx.coroutines.JobNode
    public boolean getOnCancelling() {
        return true;
    }

    @Override // kotlinx.coroutines.JobNode
    public void invoke(Throwable th) {
        if (this._invoked.compareAndSet(false, true)) {
            this.handler.invoke(th);
        }
    }
}
