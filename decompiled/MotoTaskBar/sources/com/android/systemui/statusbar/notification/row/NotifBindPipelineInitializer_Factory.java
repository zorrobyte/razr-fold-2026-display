package com.android.systemui.statusbar.notification.row;

import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotifBindPipelineInitializer_Factory implements Factory {
    private final Provider pipelineProvider;
    private final Provider stageProvider;

    public NotifBindPipelineInitializer_Factory(Provider provider, Provider provider2) {
        this.pipelineProvider = provider;
        this.stageProvider = provider2;
    }

    public static NotifBindPipelineInitializer_Factory create(Provider provider, Provider provider2) {
        return new NotifBindPipelineInitializer_Factory(provider, provider2);
    }

    public static NotifBindPipelineInitializer newInstance(NotifBindPipeline notifBindPipeline, RowContentBindStage rowContentBindStage) {
        return new NotifBindPipelineInitializer(notifBindPipeline, rowContentBindStage);
    }

    @Override // javax.inject.Provider
    public NotifBindPipelineInitializer get() {
        return newInstance((NotifBindPipeline) this.pipelineProvider.get(), (RowContentBindStage) this.stageProvider.get());
    }
}
