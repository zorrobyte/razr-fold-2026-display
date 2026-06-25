package kotlinx.coroutines;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.atomicfu.AtomicFU;

/* JADX INFO: compiled from: CompletionState.kt */
/* JADX INFO: loaded from: classes2.dex */
public class CompletedExceptionally {
    private final AtomicBoolean _handled;
    public final Throwable cause;

    public CompletedExceptionally(Throwable th, boolean z) {
        th.getClass();
        this.cause = th;
        this._handled = AtomicFU.atomic(z);
    }

    public /* synthetic */ CompletedExceptionally(Throwable th, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(th, (i & 2) != 0 ? false : z);
    }

    public final boolean getHandled() {
        return this._handled.getValue();
    }

    public final boolean makeHandled() {
        return this._handled.compareAndSet(false, true);
    }

    public String toString() {
        return DebugStringsKt.getClassSimpleName(this) + "[" + this.cause + "]";
    }
}
