package androidx.compose.ui;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: MotionDurationScale.kt */
/* JADX INFO: loaded from: classes.dex */
public interface MotionDurationScale extends CoroutineContext.Element {
    public static final Key Key = Key.$$INSTANCE;

    /* JADX INFO: compiled from: MotionDurationScale.kt */
    public abstract class DefaultImpls {
        public static Object fold(MotionDurationScale motionDurationScale, Object obj, Function2 function2) {
            return CoroutineContext.Element.DefaultImpls.fold(motionDurationScale, obj, function2);
        }

        public static CoroutineContext.Element get(MotionDurationScale motionDurationScale, CoroutineContext.Key key) {
            return CoroutineContext.Element.DefaultImpls.get(motionDurationScale, key);
        }

        public static CoroutineContext minusKey(MotionDurationScale motionDurationScale, CoroutineContext.Key key) {
            return CoroutineContext.Element.DefaultImpls.minusKey(motionDurationScale, key);
        }

        public static CoroutineContext plus(MotionDurationScale motionDurationScale, CoroutineContext coroutineContext) {
            return CoroutineContext.Element.DefaultImpls.plus(motionDurationScale, coroutineContext);
        }
    }

    /* JADX INFO: compiled from: MotionDurationScale.kt */
    public final class Key implements CoroutineContext.Key {
        static final /* synthetic */ Key $$INSTANCE = new Key();

        private Key() {
        }
    }

    @Override // kotlin.coroutines.CoroutineContext.Element
    default CoroutineContext.Key getKey() {
        return Key;
    }
}
