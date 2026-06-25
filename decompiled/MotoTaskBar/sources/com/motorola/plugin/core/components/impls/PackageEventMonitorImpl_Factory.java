package com.motorola.plugin.core.components.impls;

import android.content.Context;
import com.motorola.plugin.core.misc.DisposableContainer;
import com.motorola.plugin.core.remote.RemotePluginManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class PackageEventMonitorImpl_Factory implements Factory {
    private final Provider contextProvider;
    private final Provider mDisposableProvider;
    private final Provider rpmProvider;

    public PackageEventMonitorImpl_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.rpmProvider = provider2;
        this.mDisposableProvider = provider3;
    }

    public static PackageEventMonitorImpl_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new PackageEventMonitorImpl_Factory(provider, provider2, provider3);
    }

    public static PackageEventMonitorImpl newInstance(Context context, RemotePluginManager remotePluginManager, DisposableContainer disposableContainer) {
        return new PackageEventMonitorImpl(context, remotePluginManager, disposableContainer);
    }

    @Override // javax.inject.Provider
    public PackageEventMonitorImpl get() {
        return newInstance((Context) this.contextProvider.get(), (RemotePluginManager) this.rpmProvider.get(), (DisposableContainer) this.mDisposableProvider.get());
    }
}
