package com.motorola.plugin.core.misc;

/* JADX INFO: compiled from: Inject.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class InjectKt {
    public static final Provider providerOf(final Object obj) {
        return new Provider() { // from class: com.motorola.plugin.core.misc.InjectKt.providerOf.1
            @Override // com.motorola.plugin.core.misc.Provider
            public Object get() {
                return obj;
            }
        };
    }

    public static final Provider singleton(Provider provider) {
        provider.getClass();
        return provider instanceof Singleton ? provider : new Singleton(provider);
    }
}
