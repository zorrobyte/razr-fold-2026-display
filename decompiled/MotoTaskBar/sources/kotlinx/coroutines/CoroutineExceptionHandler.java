package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: CoroutineExceptionHandler.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface CoroutineExceptionHandler extends CoroutineContext.Element {
    public static final Key Key = Key.$$INSTANCE;

    /* JADX INFO: compiled from: CoroutineExceptionHandler.kt */
    public abstract class DefaultImpls {
        public static Object fold(CoroutineExceptionHandler coroutineExceptionHandler, Object obj, Function2 function2) {
            function2.getClass();
            return CoroutineContext.Element.DefaultImpls.fold(coroutineExceptionHandler, obj, function2);
        }

        public static CoroutineContext.Element get(CoroutineExceptionHandler coroutineExceptionHandler, CoroutineContext.Key key) {
            key.getClass();
            return CoroutineContext.Element.DefaultImpls.get(coroutineExceptionHandler, key);
        }

        public static CoroutineContext minusKey(CoroutineExceptionHandler coroutineExceptionHandler, CoroutineContext.Key key) {
            key.getClass();
            return CoroutineContext.Element.DefaultImpls.minusKey(coroutineExceptionHandler, key);
        }

        public static CoroutineContext plus(CoroutineExceptionHandler coroutineExceptionHandler, CoroutineContext coroutineContext) {
            coroutineContext.getClass();
            return CoroutineContext.Element.DefaultImpls.plus(coroutineExceptionHandler, coroutineContext);
        }
    }

    /* JADX INFO: compiled from: CoroutineExceptionHandler.kt */
    public final class Key implements CoroutineContext.Key {
        static final /* synthetic */ Key $$INSTANCE = new Key();

        private Key() {
        }
    }

    void handleException(CoroutineContext coroutineContext, Throwable th);
}
