package dagger.internal;

import dagger.Lazy;

/* JADX INFO: loaded from: classes2.dex */
public final class DoubleCheck implements Provider, Lazy {
    private static final Object UNINITIALIZED = new Object();
    private volatile Object instance = UNINITIALIZED;
    private volatile Provider provider;

    private DoubleCheck(Provider provider) {
        this.provider = provider;
    }

    private synchronized Object getSynchronized() {
        Object obj;
        obj = this.instance;
        if (obj == UNINITIALIZED) {
            obj = this.provider.get();
            this.instance = reentrantCheck(this.instance, obj);
            this.provider = null;
        }
        return obj;
    }

    public static Lazy lazy(Provider provider) {
        if (provider instanceof Lazy) {
            return (Lazy) provider;
        }
        provider.getClass();
        return new DoubleCheck(provider);
    }

    public static Lazy lazy(javax.inject.Provider provider) {
        return lazy(Providers.asDaggerProvider(provider));
    }

    public static Provider provider(Provider provider) {
        provider.getClass();
        return provider instanceof DoubleCheck ? provider : new DoubleCheck(provider);
    }

    public static javax.inject.Provider provider(javax.inject.Provider provider) {
        return provider(Providers.asDaggerProvider(provider));
    }

    private static Object reentrantCheck(Object obj, Object obj2) {
        if (obj == UNINITIALIZED || obj == obj2) {
            return obj2;
        }
        throw new IllegalStateException("Scoped provider was invoked recursively returning different results: " + obj + " & " + obj2 + ". This is likely due to a circular dependency.");
    }

    @Override // javax.inject.Provider
    public Object get() {
        Object obj = this.instance;
        return obj == UNINITIALIZED ? getSynchronized() : obj;
    }
}
