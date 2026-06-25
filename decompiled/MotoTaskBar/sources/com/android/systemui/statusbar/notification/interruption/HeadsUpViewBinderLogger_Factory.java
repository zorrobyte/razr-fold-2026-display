package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class HeadsUpViewBinderLogger_Factory implements Factory {
    private final Provider bufferProvider;

    public HeadsUpViewBinderLogger_Factory(Provider provider) {
        this.bufferProvider = provider;
    }

    public static HeadsUpViewBinderLogger_Factory create(Provider provider) {
        return new HeadsUpViewBinderLogger_Factory(provider);
    }

    public static HeadsUpViewBinderLogger newInstance(LogBuffer logBuffer) {
        return new HeadsUpViewBinderLogger(logBuffer);
    }

    @Override // javax.inject.Provider
    public HeadsUpViewBinderLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
