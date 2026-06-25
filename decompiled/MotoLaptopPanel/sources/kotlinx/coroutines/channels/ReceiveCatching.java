package kotlinx.coroutines.channels;

import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.Waiter;
import kotlinx.coroutines.internal.Segment;

/* JADX INFO: compiled from: BufferedChannel.kt */
/* JADX INFO: loaded from: classes.dex */
final class ReceiveCatching implements Waiter {
    public final CancellableContinuationImpl cont;

    public ReceiveCatching(CancellableContinuationImpl cancellableContinuationImpl) {
        cancellableContinuationImpl.getClass();
        this.cont = cancellableContinuationImpl;
    }

    @Override // kotlinx.coroutines.Waiter
    public void invokeOnCancellation(Segment segment, int i) {
        segment.getClass();
        this.cont.invokeOnCancellation(segment, i);
    }
}
