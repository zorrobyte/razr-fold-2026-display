package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NodeSpecBuilderLogger_Factory implements Factory {
    private final Provider bufferProvider;
    private final Provider notifPipelineFlagsProvider;

    public NodeSpecBuilderLogger_Factory(Provider provider, Provider provider2) {
        this.notifPipelineFlagsProvider = provider;
        this.bufferProvider = provider2;
    }

    public static NodeSpecBuilderLogger_Factory create(Provider provider, Provider provider2) {
        return new NodeSpecBuilderLogger_Factory(provider, provider2);
    }

    public static NodeSpecBuilderLogger newInstance(NotifPipelineFlags notifPipelineFlags, LogBuffer logBuffer) {
        return new NodeSpecBuilderLogger(notifPipelineFlags, logBuffer);
    }

    @Override // javax.inject.Provider
    public NodeSpecBuilderLogger get() {
        return newInstance((NotifPipelineFlags) this.notifPipelineFlagsProvider.get(), (LogBuffer) this.bufferProvider.get());
    }
}
