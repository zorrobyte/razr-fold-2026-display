package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.Sequence;

/* JADX INFO: compiled from: Job.kt */
/* JADX INFO: loaded from: classes.dex */
public interface Job extends CoroutineContext.Element {
    public static final Key Key = Key.$$INSTANCE;

    /* JADX INFO: compiled from: Job.kt */
    public abstract class DefaultImpls {
        public static /* synthetic */ void cancel$default(Job job, CancellationException cancellationException, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: cancel");
            }
            if ((i & 1) != 0) {
                cancellationException = null;
            }
            job.cancel(cancellationException);
        }

        public static Object fold(Job job, Object obj, Function2 function2) {
            function2.getClass();
            return CoroutineContext.Element.DefaultImpls.fold(job, obj, function2);
        }

        public static CoroutineContext.Element get(Job job, CoroutineContext.Key key) {
            key.getClass();
            return CoroutineContext.Element.DefaultImpls.get(job, key);
        }

        public static CoroutineContext minusKey(Job job, CoroutineContext.Key key) {
            key.getClass();
            return CoroutineContext.Element.DefaultImpls.minusKey(job, key);
        }

        public static CoroutineContext plus(Job job, CoroutineContext coroutineContext) {
            coroutineContext.getClass();
            return CoroutineContext.Element.DefaultImpls.plus(job, coroutineContext);
        }
    }

    /* JADX INFO: compiled from: Job.kt */
    public final class Key implements CoroutineContext.Key {
        static final /* synthetic */ Key $$INSTANCE = new Key();

        private Key() {
        }
    }

    ChildHandle attachChild(ChildJob childJob);

    void cancel(CancellationException cancellationException);

    CancellationException getCancellationException();

    Sequence getChildren();

    DisposableHandle invokeOnCompletion(Function1 function1);

    DisposableHandle invokeOnCompletion(boolean z, boolean z2, Function1 function1);

    boolean isActive();

    Object join(Continuation continuation);

    boolean start();
}
