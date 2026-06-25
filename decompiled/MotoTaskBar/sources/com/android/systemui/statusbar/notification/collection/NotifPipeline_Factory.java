package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.notification.collection.render.RenderStageManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotifPipeline_Factory implements Factory {
    private final Provider mNotifCollectionProvider;
    private final Provider mRenderStageManagerProvider;
    private final Provider mShadeListBuilderProvider;

    public NotifPipeline_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.mNotifCollectionProvider = provider;
        this.mShadeListBuilderProvider = provider2;
        this.mRenderStageManagerProvider = provider3;
    }

    public static NotifPipeline_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new NotifPipeline_Factory(provider, provider2, provider3);
    }

    public static NotifPipeline newInstance(NotifCollection notifCollection, ShadeListBuilder shadeListBuilder, RenderStageManager renderStageManager) {
        return new NotifPipeline(notifCollection, shadeListBuilder, renderStageManager);
    }

    @Override // javax.inject.Provider
    public NotifPipeline get() {
        return newInstance((NotifCollection) this.mNotifCollectionProvider.get(), (ShadeListBuilder) this.mShadeListBuilderProvider.get(), (RenderStageManager) this.mRenderStageManagerProvider.get());
    }
}
