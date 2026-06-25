package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationSectionHeadersModule_ProvidesIncomingHeaderControllerFactory implements Factory {
    private final Provider subcomponentProvider;

    public NotificationSectionHeadersModule_ProvidesIncomingHeaderControllerFactory(Provider provider) {
        this.subcomponentProvider = provider;
    }

    public static NotificationSectionHeadersModule_ProvidesIncomingHeaderControllerFactory create(Provider provider) {
        return new NotificationSectionHeadersModule_ProvidesIncomingHeaderControllerFactory(provider);
    }

    public static SectionHeaderController providesIncomingHeaderController(SectionHeaderControllerSubcomponent sectionHeaderControllerSubcomponent) {
        SectionHeaderController sectionHeaderControllerProvidesIncomingHeaderController = NotificationSectionHeadersModule.providesIncomingHeaderController(sectionHeaderControllerSubcomponent);
        sectionHeaderControllerProvidesIncomingHeaderController.getClass();
        return sectionHeaderControllerProvidesIncomingHeaderController;
    }

    @Override // javax.inject.Provider
    public SectionHeaderController get() {
        return providesIncomingHeaderController((SectionHeaderControllerSubcomponent) this.subcomponentProvider.get());
    }
}
