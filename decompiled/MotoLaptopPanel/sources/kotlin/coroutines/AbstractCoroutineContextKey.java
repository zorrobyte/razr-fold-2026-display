package kotlin.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: CoroutineContextImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractCoroutineContextKey implements CoroutineContext.Key {
    private final Function1 safeCast;
    private final CoroutineContext.Key topmostKey;

    public AbstractCoroutineContextKey(CoroutineContext.Key key, Function1 function1) {
        key.getClass();
        function1.getClass();
        this.safeCast = function1;
        this.topmostKey = key instanceof AbstractCoroutineContextKey ? ((AbstractCoroutineContextKey) key).topmostKey : key;
    }

    public final boolean isSubKey$kotlin_stdlib(CoroutineContext.Key key) {
        key.getClass();
        return key == this || this.topmostKey == key;
    }

    public final CoroutineContext.Element tryCast$kotlin_stdlib(CoroutineContext.Element element) {
        element.getClass();
        return (CoroutineContext.Element) this.safeCast.invoke(element);
    }
}
