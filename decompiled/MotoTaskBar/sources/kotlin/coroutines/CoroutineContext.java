package kotlin.coroutines;

import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CoroutineContext.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface CoroutineContext {

    /* JADX INFO: compiled from: CoroutineContext.kt */
    public abstract class DefaultImpls {
        public static CoroutineContext plus(CoroutineContext coroutineContext, CoroutineContext coroutineContext2) {
            coroutineContext2.getClass();
            return coroutineContext2 == EmptyCoroutineContext.INSTANCE ? coroutineContext : (CoroutineContext) coroutineContext2.fold(coroutineContext, new Function2() { // from class: kotlin.coroutines.CoroutineContext$DefaultImpls$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return CoroutineContext.DefaultImpls.plus$lambda$0((CoroutineContext) obj, (CoroutineContext.Element) obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static CoroutineContext plus$lambda$0(CoroutineContext coroutineContext, Element element) {
            coroutineContext.getClass();
            element.getClass();
            CoroutineContext coroutineContextMinusKey = coroutineContext.minusKey(element.getKey());
            EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
            if (coroutineContextMinusKey == emptyCoroutineContext) {
                return element;
            }
            ContinuationInterceptor.Key key = ContinuationInterceptor.Key;
            ContinuationInterceptor continuationInterceptor = (ContinuationInterceptor) coroutineContextMinusKey.get(key);
            if (continuationInterceptor == null) {
                return new CombinedContext(coroutineContextMinusKey, element);
            }
            CoroutineContext coroutineContextMinusKey2 = coroutineContextMinusKey.minusKey(key);
            return coroutineContextMinusKey2 == emptyCoroutineContext ? new CombinedContext(element, continuationInterceptor) : new CombinedContext(new CombinedContext(coroutineContextMinusKey2, element), continuationInterceptor);
        }
    }

    /* JADX INFO: compiled from: CoroutineContext.kt */
    public interface Element extends CoroutineContext {

        /* JADX INFO: compiled from: CoroutineContext.kt */
        public abstract class DefaultImpls {
            public static Object fold(Element element, Object obj, Function2 function2) {
                function2.getClass();
                return function2.invoke(obj, element);
            }

            public static Element get(Element element, Key key) {
                key.getClass();
                if (Intrinsics.areEqual(element.getKey(), key)) {
                    return element;
                }
                return null;
            }

            public static CoroutineContext minusKey(Element element, Key key) {
                key.getClass();
                return Intrinsics.areEqual(element.getKey(), key) ? EmptyCoroutineContext.INSTANCE : element;
            }

            public static CoroutineContext plus(Element element, CoroutineContext coroutineContext) {
                coroutineContext.getClass();
                return DefaultImpls.plus(element, coroutineContext);
            }
        }

        @Override // kotlin.coroutines.CoroutineContext
        Element get(Key key);

        Key getKey();
    }

    /* JADX INFO: compiled from: CoroutineContext.kt */
    public interface Key {
    }

    Object fold(Object obj, Function2 function2);

    Element get(Key key);

    CoroutineContext minusKey(Key key);

    CoroutineContext plus(CoroutineContext coroutineContext);
}
