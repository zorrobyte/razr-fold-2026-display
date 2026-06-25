package com.android.systemui.statusbar.policy;

import com.android.systemui.dump.DumpManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class AvalancheController_Factory implements Factory {
    private final Provider dumpManagerProvider;

    public AvalancheController_Factory(Provider provider) {
        this.dumpManagerProvider = provider;
    }

    public static AvalancheController_Factory create(Provider provider) {
        return new AvalancheController_Factory(provider);
    }

    public static AvalancheController newInstance(DumpManager dumpManager) {
        return new AvalancheController(dumpManager);
    }

    @Override // javax.inject.Provider
    public AvalancheController get() {
        return newInstance((DumpManager) this.dumpManagerProvider.get());
    }
}
