package com.motorola.plugin.core.misc;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: Inject.kt */
/* JADX INFO: loaded from: classes2.dex */
final class Singleton implements Provider {
    private final Lazy value$delegate;

    public Singleton(final Provider provider) {
        provider.getClass();
        this.value$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.plugin.core.misc.Singleton$value$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return provider.get();
            }
        });
    }

    private final Object getValue() {
        return this.value$delegate.getValue();
    }

    @Override // com.motorola.plugin.core.misc.Provider
    public Object get() {
        return getValue();
    }
}
