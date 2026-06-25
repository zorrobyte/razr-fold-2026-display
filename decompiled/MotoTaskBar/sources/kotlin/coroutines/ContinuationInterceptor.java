package kotlin.coroutines;

import kotlin.coroutines.CoroutineContext;

/* JADX INFO: compiled from: ContinuationInterceptor.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface ContinuationInterceptor extends CoroutineContext.Element {
    public static final Key Key = Key.$$INSTANCE;

    /* JADX INFO: compiled from: ContinuationInterceptor.kt */
    public abstract class DefaultImpls {
        public static CoroutineContext.Element get(ContinuationInterceptor continuationInterceptor, CoroutineContext.Key key) {
            CoroutineContext.Element elementTryCast$kotlin_stdlib;
            key.getClass();
            if (!(key instanceof AbstractCoroutineContextKey)) {
                if (ContinuationInterceptor.Key != key) {
                    return null;
                }
                continuationInterceptor.getClass();
                return continuationInterceptor;
            }
            AbstractCoroutineContextKey abstractCoroutineContextKey = (AbstractCoroutineContextKey) key;
            if (!abstractCoroutineContextKey.isSubKey$kotlin_stdlib(continuationInterceptor.getKey()) || (elementTryCast$kotlin_stdlib = abstractCoroutineContextKey.tryCast$kotlin_stdlib(continuationInterceptor)) == null) {
                return null;
            }
            return elementTryCast$kotlin_stdlib;
        }

        public static CoroutineContext minusKey(ContinuationInterceptor continuationInterceptor, CoroutineContext.Key key) {
            key.getClass();
            if (!(key instanceof AbstractCoroutineContextKey)) {
                return ContinuationInterceptor.Key == key ? EmptyCoroutineContext.INSTANCE : continuationInterceptor;
            }
            AbstractCoroutineContextKey abstractCoroutineContextKey = (AbstractCoroutineContextKey) key;
            return (!abstractCoroutineContextKey.isSubKey$kotlin_stdlib(continuationInterceptor.getKey()) || abstractCoroutineContextKey.tryCast$kotlin_stdlib(continuationInterceptor) == null) ? continuationInterceptor : EmptyCoroutineContext.INSTANCE;
        }
    }

    /* JADX INFO: compiled from: ContinuationInterceptor.kt */
    public final class Key implements CoroutineContext.Key {
        static final /* synthetic */ Key $$INSTANCE = new Key();

        private Key() {
        }
    }

    Continuation interceptContinuation(Continuation continuation);

    void releaseInterceptedContinuation(Continuation continuation);
}
