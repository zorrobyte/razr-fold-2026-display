package com.android.app.tracing.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContextImplKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: TraceContextElement.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class CoroutineTraceName implements CoroutineContext.Element {
    public static final Key Key = new Key(null);
    private final String name;

    /* JADX INFO: compiled from: TraceContextElement.kt */
    public final class Key implements CoroutineContext.Key {
        private Key() {
        }

        public /* synthetic */ Key(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public CoroutineTraceName(String str) {
        this.name = str;
    }

    @Override // kotlin.coroutines.CoroutineContext
    public Object fold(Object obj, Function2 function2) {
        return CoroutineContext.Element.DefaultImpls.fold(this, obj, function2);
    }

    @Override // kotlin.coroutines.CoroutineContext.Element, kotlin.coroutines.CoroutineContext
    public CoroutineContext.Element get(CoroutineContext.Key key) {
        key.getClass();
        return CoroutineContextImplKt.getPolymorphicElement(this, key);
    }

    @Override // kotlin.coroutines.CoroutineContext.Element
    public CoroutineContext.Key getKey() {
        return Key;
    }

    public final String getName$frameworks__libs__systemui__tracinglib__core__android_common__tracinglib_platform() {
        return this.name;
    }

    @Override // kotlin.coroutines.CoroutineContext
    public CoroutineContext minusKey(CoroutineContext.Key key) {
        key.getClass();
        return CoroutineContextImplKt.minusPolymorphicKey(this, key);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public CoroutineContext plus(CoroutineContext coroutineContext) {
        return CoroutineContext.Element.DefaultImpls.plus(this, coroutineContext);
    }
}
