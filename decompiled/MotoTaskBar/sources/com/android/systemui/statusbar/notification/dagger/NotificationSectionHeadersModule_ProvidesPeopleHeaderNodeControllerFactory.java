package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.statusbar.notification.collection.render.NodeController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationSectionHeadersModule_ProvidesPeopleHeaderNodeControllerFactory implements Factory {
    private final Provider subcomponentProvider;

    public NotificationSectionHeadersModule_ProvidesPeopleHeaderNodeControllerFactory(Provider provider) {
        this.subcomponentProvider = provider;
    }

    public static NotificationSectionHeadersModule_ProvidesPeopleHeaderNodeControllerFactory create(Provider provider) {
        return new NotificationSectionHeadersModule_ProvidesPeopleHeaderNodeControllerFactory(provider);
    }

    public static NodeController providesPeopleHeaderNodeController(SectionHeaderControllerSubcomponent sectionHeaderControllerSubcomponent) {
        NodeController nodeControllerProvidesPeopleHeaderNodeController = NotificationSectionHeadersModule.providesPeopleHeaderNodeController(sectionHeaderControllerSubcomponent);
        nodeControllerProvidesPeopleHeaderNodeController.getClass();
        return nodeControllerProvidesPeopleHeaderNodeController;
    }

    @Override // javax.inject.Provider
    public NodeController get() {
        return providesPeopleHeaderNodeController((SectionHeaderControllerSubcomponent) this.subcomponentProvider.get());
    }
}
