package kotlinx.coroutines;

import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: CancellableContinuationImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public interface CancelHandler extends NotCompleted {

    /* JADX INFO: compiled from: CancellableContinuationImpl.kt */
    public final class UserSupplied implements CancelHandler {
        private final Function1 handler;

        public UserSupplied(Function1 function1) {
            function1.getClass();
            this.handler = function1;
        }

        @Override // kotlinx.coroutines.CancelHandler
        public void invoke(Throwable th) {
            this.handler.invoke(th);
        }

        public String toString() {
            return "CancelHandler.UserSupplied[" + DebugStringsKt.getClassSimpleName(this.handler) + "@" + DebugStringsKt.getHexAddress(this) + "]";
        }
    }

    void invoke(Throwable th);
}
