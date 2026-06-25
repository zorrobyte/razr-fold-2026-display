package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class HeadsUpCoordinatorLogger_Factory implements Factory {
    private final Provider bufferProvider;

    public HeadsUpCoordinatorLogger_Factory(Provider provider) {
        this.bufferProvider = provider;
    }

    public static HeadsUpCoordinatorLogger_Factory create(Provider provider) {
        return new HeadsUpCoordinatorLogger_Factory(provider);
    }

    public static HeadsUpCoordinatorLogger newInstance(LogBuffer logBuffer) {
        return new HeadsUpCoordinatorLogger(logBuffer);
    }

    @Override // javax.inject.Provider
    public HeadsUpCoordinatorLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
