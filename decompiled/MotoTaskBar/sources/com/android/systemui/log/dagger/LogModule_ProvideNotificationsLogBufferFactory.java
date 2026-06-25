package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class LogModule_ProvideNotificationsLogBufferFactory implements Factory {
    private final Provider factoryProvider;
    private final Provider notifPipelineFlagsProvider;

    public LogModule_ProvideNotificationsLogBufferFactory(Provider provider, Provider provider2) {
        this.factoryProvider = provider;
        this.notifPipelineFlagsProvider = provider2;
    }

    public static LogModule_ProvideNotificationsLogBufferFactory create(Provider provider, Provider provider2) {
        return new LogModule_ProvideNotificationsLogBufferFactory(provider, provider2);
    }

    public static LogBuffer provideNotificationsLogBuffer(LogBufferFactory logBufferFactory, NotifPipelineFlags notifPipelineFlags) {
        LogBuffer logBufferProvideNotificationsLogBuffer = LogModule.provideNotificationsLogBuffer(logBufferFactory, notifPipelineFlags);
        logBufferProvideNotificationsLogBuffer.getClass();
        return logBufferProvideNotificationsLogBuffer;
    }

    @Override // javax.inject.Provider
    public LogBuffer get() {
        return provideNotificationsLogBuffer((LogBufferFactory) this.factoryProvider.get(), (NotifPipelineFlags) this.notifPipelineFlagsProvider.get());
    }
}
