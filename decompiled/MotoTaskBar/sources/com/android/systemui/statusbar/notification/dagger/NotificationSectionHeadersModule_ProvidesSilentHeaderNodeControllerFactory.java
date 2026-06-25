package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.statusbar.notification.collection.render.NodeController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationSectionHeadersModule_ProvidesSilentHeaderNodeControllerFactory implements Factory {
    private final Provider subcomponentProvider;

    public NotificationSectionHeadersModule_ProvidesSilentHeaderNodeControllerFactory(Provider provider) {
        this.subcomponentProvider = provider;
    }

    public static NotificationSectionHeadersModule_ProvidesSilentHeaderNodeControllerFactory create(Provider provider) {
        return new NotificationSectionHeadersModule_ProvidesSilentHeaderNodeControllerFactory(provider);
    }

    public static NodeController providesSilentHeaderNodeController(SectionHeaderControllerSubcomponent sectionHeaderControllerSubcomponent) {
        NodeController nodeControllerProvidesSilentHeaderNodeController = NotificationSectionHeadersModule.providesSilentHeaderNodeController(sectionHeaderControllerSubcomponent);
        nodeControllerProvidesSilentHeaderNodeController.getClass();
        return nodeControllerProvidesSilentHeaderNodeController;
    }

    @Override // javax.inject.Provider
    public NodeController get() {
        return providesSilentHeaderNodeController((SectionHeaderControllerSubcomponent) this.subcomponentProvider.get());
    }
}
