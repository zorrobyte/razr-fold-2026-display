package kotlinx.coroutines.flow.internal;

/* JADX INFO: compiled from: FlowExceptions.common.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class FlowExceptions_commonKt {
    public static final void checkOwnership(AbortFlowException abortFlowException, Object obj) {
        abortFlowException.getClass();
        obj.getClass();
        if (abortFlowException.owner != obj) {
            throw abortFlowException;
        }
    }
}
