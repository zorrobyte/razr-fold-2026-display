package com.android.systemui.statusbar.notification.collection.init;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifInflaterImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder;
import com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescer;
import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinators;
import com.android.systemui.statusbar.notification.collection.render.RenderStageManager;
import com.android.systemui.statusbar.notification.collection.render.ShadeViewManagerFactory;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotifPipelineInitializer_Factory implements Factory {
    private final Provider dumpManagerProvider;
    private final Provider groupCoalescerProvider;
    private final Provider listBuilderProvider;
    private final Provider notifCollectionProvider;
    private final Provider notifCoordinatorsProvider;
    private final Provider notifInflaterProvider;
    private final Provider pipelineWrapperProvider;
    private final Provider renderStageManagerProvider;
    private final Provider shadeViewManagerFactoryProvider;

    public NotifPipelineInitializer_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        this.pipelineWrapperProvider = provider;
        this.groupCoalescerProvider = provider2;
        this.notifCollectionProvider = provider3;
        this.listBuilderProvider = provider4;
        this.renderStageManagerProvider = provider5;
        this.notifCoordinatorsProvider = provider6;
        this.notifInflaterProvider = provider7;
        this.dumpManagerProvider = provider8;
        this.shadeViewManagerFactoryProvider = provider9;
    }

    public static NotifPipelineInitializer_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        return new NotifPipelineInitializer_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    public static NotifPipelineInitializer newInstance(NotifPipeline notifPipeline, GroupCoalescer groupCoalescer, NotifCollection notifCollection, ShadeListBuilder shadeListBuilder, RenderStageManager renderStageManager, NotifCoordinators notifCoordinators, NotifInflaterImpl notifInflaterImpl, DumpManager dumpManager, ShadeViewManagerFactory shadeViewManagerFactory) {
        return new NotifPipelineInitializer(notifPipeline, groupCoalescer, notifCollection, shadeListBuilder, renderStageManager, notifCoordinators, notifInflaterImpl, dumpManager, shadeViewManagerFactory);
    }

    @Override // javax.inject.Provider
    public NotifPipelineInitializer get() {
        return newInstance((NotifPipeline) this.pipelineWrapperProvider.get(), (GroupCoalescer) this.groupCoalescerProvider.get(), (NotifCollection) this.notifCollectionProvider.get(), (ShadeListBuilder) this.listBuilderProvider.get(), (RenderStageManager) this.renderStageManagerProvider.get(), (NotifCoordinators) this.notifCoordinatorsProvider.get(), (NotifInflaterImpl) this.notifInflaterProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (ShadeViewManagerFactory) this.shadeViewManagerFactoryProvider.get());
    }
}
