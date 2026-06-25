package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class PreparationCoordinatorLogger_Factory implements Factory {
    private final Provider bufferProvider;

    public PreparationCoordinatorLogger_Factory(Provider provider) {
        this.bufferProvider = provider;
    }

    public static PreparationCoordinatorLogger_Factory create(Provider provider) {
        return new PreparationCoordinatorLogger_Factory(provider);
    }

    public static PreparationCoordinatorLogger newInstance(LogBuffer logBuffer) {
        return new PreparationCoordinatorLogger(logBuffer);
    }

    @Override // javax.inject.Provider
    public PreparationCoordinatorLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
