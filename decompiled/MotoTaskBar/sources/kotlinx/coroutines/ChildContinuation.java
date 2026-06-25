package kotlinx.coroutines;

/* JADX INFO: compiled from: CancellableContinuationImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
final class ChildContinuation extends JobNode {
    public final CancellableContinuationImpl child;

    public ChildContinuation(CancellableContinuationImpl cancellableContinuationImpl) {
        cancellableContinuationImpl.getClass();
        this.child = cancellableContinuationImpl;
    }

    @Override // kotlinx.coroutines.JobNode
    public boolean getOnCancelling() {
        return true;
    }

    @Override // kotlinx.coroutines.JobNode
    public void invoke(Throwable th) {
        CancellableContinuationImpl cancellableContinuationImpl = this.child;
        cancellableContinuationImpl.parentCancelled$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(cancellableContinuationImpl.getContinuationCancellationCause(getJob()));
    }
}
