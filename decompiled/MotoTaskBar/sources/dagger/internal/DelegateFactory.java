package dagger.internal;

/* JADX INFO: loaded from: classes2.dex */
public final class DelegateFactory implements Factory {
    private Provider delegate;

    public static void setDelegate(Provider provider, Provider provider2) {
        setDelegateInternal((DelegateFactory) provider, provider2);
    }

    public static void setDelegate(javax.inject.Provider provider, javax.inject.Provider provider2) {
        setDelegateInternal((DelegateFactory) provider, Providers.asDaggerProvider(provider2));
    }

    private static void setDelegateInternal(DelegateFactory delegateFactory, Provider provider) {
        provider.getClass();
        if (delegateFactory.delegate != null) {
            throw new IllegalStateException();
        }
        delegateFactory.delegate = provider;
    }

    @Override // javax.inject.Provider
    public Object get() {
        Provider provider = this.delegate;
        if (provider != null) {
            return provider.get();
        }
        throw new IllegalStateException();
    }
}
