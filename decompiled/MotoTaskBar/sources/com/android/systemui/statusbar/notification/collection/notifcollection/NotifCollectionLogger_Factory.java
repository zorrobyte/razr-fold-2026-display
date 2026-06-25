package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotifCollectionLogger_Factory implements Factory {
    private final Provider bufferProvider;

    public NotifCollectionLogger_Factory(Provider provider) {
        this.bufferProvider = provider;
    }

    public static NotifCollectionLogger_Factory create(Provider provider) {
        return new NotifCollectionLogger_Factory(provider);
    }

    public static NotifCollectionLogger newInstance(LogBuffer logBuffer) {
        return new NotifCollectionLogger(logBuffer);
    }

    @Override // javax.inject.Provider
    public NotifCollectionLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
