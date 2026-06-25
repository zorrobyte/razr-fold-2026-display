package com.android.systemui.statusbar.notification;

import com.android.systemui.dump.DumpManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ColorUpdateLogger_Factory implements Factory {
    private final Provider dumpManagerProvider;

    public ColorUpdateLogger_Factory(Provider provider) {
        this.dumpManagerProvider = provider;
    }

    public static ColorUpdateLogger_Factory create(Provider provider) {
        return new ColorUpdateLogger_Factory(provider);
    }

    public static ColorUpdateLogger newInstance(DumpManager dumpManager) {
        return new ColorUpdateLogger(dumpManager);
    }

    @Override // javax.inject.Provider
    public ColorUpdateLogger get() {
        return newInstance((DumpManager) this.dumpManagerProvider.get());
    }
}
