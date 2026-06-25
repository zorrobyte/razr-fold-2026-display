package kotlinx.coroutines;

import java.util.concurrent.Future;

/* JADX INFO: compiled from: Executors.kt */
/* JADX INFO: loaded from: classes2.dex */
final class CancelFutureOnCancel implements CancelHandler {
    private final Future future;

    public CancelFutureOnCancel(Future future) {
        future.getClass();
        this.future = future;
    }

    @Override // kotlinx.coroutines.CancelHandler
    public void invoke(Throwable th) {
        this.future.cancel(false);
    }

    public String toString() {
        return "CancelFutureOnCancel[" + this.future + "]";
    }
}
