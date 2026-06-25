package com.android.systemui.statusbar.notification.collection.listbuilder;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ShadeListBuilderLogger_Factory implements Factory {
    private final Provider bufferProvider;
    private final Provider notifPipelineFlagsProvider;

    public ShadeListBuilderLogger_Factory(Provider provider, Provider provider2) {
        this.notifPipelineFlagsProvider = provider;
        this.bufferProvider = provider2;
    }

    public static ShadeListBuilderLogger_Factory create(Provider provider, Provider provider2) {
        return new ShadeListBuilderLogger_Factory(provider, provider2);
    }

    public static ShadeListBuilderLogger newInstance(NotifPipelineFlags notifPipelineFlags, LogBuffer logBuffer) {
        return new ShadeListBuilderLogger(notifPipelineFlags, logBuffer);
    }

    @Override // javax.inject.Provider
    public ShadeListBuilderLogger get() {
        return newInstance((NotifPipelineFlags) this.notifPipelineFlagsProvider.get(), (LogBuffer) this.bufferProvider.get());
    }
}
