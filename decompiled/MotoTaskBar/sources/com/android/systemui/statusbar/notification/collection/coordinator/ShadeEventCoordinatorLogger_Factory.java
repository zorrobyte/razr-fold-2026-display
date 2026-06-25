package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ShadeEventCoordinatorLogger_Factory implements Factory {
    private final Provider bufferProvider;

    public ShadeEventCoordinatorLogger_Factory(Provider provider) {
        this.bufferProvider = provider;
    }

    public static ShadeEventCoordinatorLogger_Factory create(Provider provider) {
        return new ShadeEventCoordinatorLogger_Factory(provider);
    }

    public static ShadeEventCoordinatorLogger newInstance(LogBuffer logBuffer) {
        return new ShadeEventCoordinatorLogger(logBuffer);
    }

    @Override // javax.inject.Provider
    public ShadeEventCoordinatorLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
