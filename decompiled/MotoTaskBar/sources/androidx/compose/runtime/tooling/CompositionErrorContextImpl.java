package androidx.compose.runtime.tooling;

import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.changelist.OperationErrorContext;
import java.util.List;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: CompositionErrorContext.kt */
/* JADX INFO: loaded from: classes.dex */
public final class CompositionErrorContextImpl implements CompositionErrorContext, OperationErrorContext, CoroutineContext.Element {
    private final ComposerImpl composer;
    public static final Key Key = new Key(null);
    public static final int $stable = 8;

    /* JADX INFO: compiled from: CompositionErrorContext.kt */
    public final class Key implements CoroutineContext.Key {
        private Key() {
        }

        public /* synthetic */ Key(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public String toString() {
            return "CompositionErrorContext";
        }
    }

    public CompositionErrorContextImpl(ComposerImpl composerImpl) {
        this.composer = composerImpl;
    }

    @Override // androidx.compose.runtime.tooling.CompositionErrorContext
    public boolean attachComposeStackTrace(Throwable th, final Object obj) {
        return ComposeStackTraceKt.tryAttachComposeStackTrace(th, new Function0() { // from class: androidx.compose.runtime.tooling.CompositionErrorContextImpl.attachComposeStackTrace.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final List mo2224invoke() {
                return CompositionErrorContextImpl.this.composer.stackTraceForValue$runtime_release(obj);
            }
        });
    }

    @Override // androidx.compose.runtime.changelist.OperationErrorContext
    public List buildStackTrace(Integer num) {
        return this.composer.parentStackTrace();
    }

    @Override // kotlin.coroutines.CoroutineContext
    public Object fold(Object obj, Function2 function2) {
        return CoroutineContext.Element.DefaultImpls.fold(this, obj, function2);
    }

    @Override // kotlin.coroutines.CoroutineContext.Element, kotlin.coroutines.CoroutineContext
    public CoroutineContext.Element get(CoroutineContext.Key key) {
        return CoroutineContext.Element.DefaultImpls.get(this, key);
    }

    @Override // kotlin.coroutines.CoroutineContext.Element
    public CoroutineContext.Key getKey() {
        return Key;
    }

    @Override // kotlin.coroutines.CoroutineContext
    public CoroutineContext minusKey(CoroutineContext.Key key) {
        return CoroutineContext.Element.DefaultImpls.minusKey(this, key);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public CoroutineContext plus(CoroutineContext coroutineContext) {
        return CoroutineContext.Element.DefaultImpls.plus(this, coroutineContext);
    }
}
