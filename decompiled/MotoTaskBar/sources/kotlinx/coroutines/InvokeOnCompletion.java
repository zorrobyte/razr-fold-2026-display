package kotlinx.coroutines;

import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: JobSupport.kt */
/* JADX INFO: loaded from: classes2.dex */
final class InvokeOnCompletion extends JobNode {
    private final Function1 handler;

    public InvokeOnCompletion(Function1 function1) {
        function1.getClass();
        this.handler = function1;
    }

    @Override // kotlinx.coroutines.JobNode
    public boolean getOnCancelling() {
        return false;
    }

    @Override // kotlinx.coroutines.JobNode
    public void invoke(Throwable th) {
        this.handler.invoke(th);
    }
}
