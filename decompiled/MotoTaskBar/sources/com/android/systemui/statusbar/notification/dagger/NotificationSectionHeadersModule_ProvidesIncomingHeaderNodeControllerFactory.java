package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.statusbar.notification.collection.render.NodeController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationSectionHeadersModule_ProvidesIncomingHeaderNodeControllerFactory implements Factory {
    private final Provider subcomponentProvider;

    public NotificationSectionHeadersModule_ProvidesIncomingHeaderNodeControllerFactory(Provider provider) {
        this.subcomponentProvider = provider;
    }

    public static NotificationSectionHeadersModule_ProvidesIncomingHeaderNodeControllerFactory create(Provider provider) {
        return new NotificationSectionHeadersModule_ProvidesIncomingHeaderNodeControllerFactory(provider);
    }

    public static NodeController providesIncomingHeaderNodeController(SectionHeaderControllerSubcomponent sectionHeaderControllerSubcomponent) {
        NodeController nodeControllerProvidesIncomingHeaderNodeController = NotificationSectionHeadersModule.providesIncomingHeaderNodeController(sectionHeaderControllerSubcomponent);
        nodeControllerProvidesIncomingHeaderNodeController.getClass();
        return nodeControllerProvidesIncomingHeaderNodeController;
    }

    @Override // javax.inject.Provider
    public NodeController get() {
        return providesIncomingHeaderNodeController((SectionHeaderControllerSubcomponent) this.subcomponentProvider.get());
    }
}
