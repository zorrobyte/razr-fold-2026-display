package kotlinx.coroutines;

import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;

/* JADX INFO: compiled from: CancellableContinuation.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface CancellableContinuation extends Continuation {

    /* JADX INFO: compiled from: CancellableContinuation.kt */
    public abstract class DefaultImpls {
        public static /* synthetic */ boolean cancel$default(CancellableContinuation cancellableContinuation, Throwable th, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: cancel");
            }
            if ((i & 1) != 0) {
                th = null;
            }
            return cancellableContinuation.cancel(th);
        }
    }

    boolean cancel(Throwable th);

    void completeResume(Object obj);

    void invokeOnCancellation(Function1 function1);

    boolean isActive();

    void resume(Object obj, Function3 function3);

    void resumeUndispatched(CoroutineDispatcher coroutineDispatcher, Object obj);

    Object tryResume(Object obj, Object obj2, Function3 function3);
}
