package com.android.systemui.statusbar.notification;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class RemoteInputControllerLogger_Factory implements Factory {
    private final Provider logBufferProvider;

    public RemoteInputControllerLogger_Factory(Provider provider) {
        this.logBufferProvider = provider;
    }

    public static RemoteInputControllerLogger_Factory create(Provider provider) {
        return new RemoteInputControllerLogger_Factory(provider);
    }

    public static RemoteInputControllerLogger newInstance(LogBuffer logBuffer) {
        return new RemoteInputControllerLogger(logBuffer);
    }

    @Override // javax.inject.Provider
    public RemoteInputControllerLogger get() {
        return newInstance((LogBuffer) this.logBufferProvider.get());
    }
}
