package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.statusbar.notification.collection.render.NodeController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationSectionHeadersModule_ProvidesAlertingHeaderNodeControllerFactory implements Factory {
    private final Provider subcomponentProvider;

    public NotificationSectionHeadersModule_ProvidesAlertingHeaderNodeControllerFactory(Provider provider) {
        this.subcomponentProvider = provider;
    }

    public static NotificationSectionHeadersModule_ProvidesAlertingHeaderNodeControllerFactory create(Provider provider) {
        return new NotificationSectionHeadersModule_ProvidesAlertingHeaderNodeControllerFactory(provider);
    }

    public static NodeController providesAlertingHeaderNodeController(SectionHeaderControllerSubcomponent sectionHeaderControllerSubcomponent) {
        NodeController nodeControllerProvidesAlertingHeaderNodeController = NotificationSectionHeadersModule.providesAlertingHeaderNodeController(sectionHeaderControllerSubcomponent);
        nodeControllerProvidesAlertingHeaderNodeController.getClass();
        return nodeControllerProvidesAlertingHeaderNodeController;
    }

    @Override // javax.inject.Provider
    public NodeController get() {
        return providesAlertingHeaderNodeController((SectionHeaderControllerSubcomponent) this.subcomponentProvider.get());
    }
}
