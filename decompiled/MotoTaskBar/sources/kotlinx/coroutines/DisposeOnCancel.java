package kotlinx.coroutines;

/* JADX INFO: compiled from: CancellableContinuation.kt */
/* JADX INFO: loaded from: classes2.dex */
final class DisposeOnCancel implements CancelHandler {
    private final DisposableHandle handle;

    public DisposeOnCancel(DisposableHandle disposableHandle) {
        disposableHandle.getClass();
        this.handle = disposableHandle;
    }

    @Override // kotlinx.coroutines.CancelHandler
    public void invoke(Throwable th) {
        this.handle.dispose();
    }

    public String toString() {
        return "DisposeOnCancel[" + this.handle + "]";
    }
}
