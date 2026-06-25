package dagger.internal;

/* JADX INFO: loaded from: classes2.dex */
public abstract class Providers {
    public static Provider asDaggerProvider(final javax.inject.Provider provider) {
        provider.getClass();
        return provider instanceof Provider ? (Provider) provider : new Provider() { // from class: dagger.internal.Providers.1
            @Override // javax.inject.Provider
            public Object get() {
                return provider.get();
            }
        };
    }
}
