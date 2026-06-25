package kotlinx.coroutines.channels;

import kotlinx.coroutines.Waiter;

/* JADX INFO: compiled from: BufferedChannel.kt */
/* JADX INFO: loaded from: classes.dex */
final class WaiterEB {
    public final Waiter waiter;

    public WaiterEB(Waiter waiter) {
        waiter.getClass();
        this.waiter = waiter;
    }

    public String toString() {
        return "WaiterEB(" + this.waiter + ")";
    }
}
