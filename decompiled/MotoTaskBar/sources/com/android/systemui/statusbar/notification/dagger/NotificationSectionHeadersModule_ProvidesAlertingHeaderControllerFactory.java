package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationSectionHeadersModule_ProvidesAlertingHeaderControllerFactory implements Factory {
    private final Provider subcomponentProvider;

    public NotificationSectionHeadersModule_ProvidesAlertingHeaderControllerFactory(Provider provider) {
        this.subcomponentProvider = provider;
    }

    public static NotificationSectionHeadersModule_ProvidesAlertingHeaderControllerFactory create(Provider provider) {
        return new NotificationSectionHeadersModule_ProvidesAlertingHeaderControllerFactory(provider);
    }

    public static SectionHeaderController providesAlertingHeaderController(SectionHeaderControllerSubcomponent sectionHeaderControllerSubcomponent) {
        SectionHeaderController sectionHeaderControllerProvidesAlertingHeaderController = NotificationSectionHeadersModule.providesAlertingHeaderController(sectionHeaderControllerSubcomponent);
        sectionHeaderControllerProvidesAlertingHeaderController.getClass();
        return sectionHeaderControllerProvidesAlertingHeaderController;
    }

    @Override // javax.inject.Provider
    public SectionHeaderController get() {
        return providesAlertingHeaderController((SectionHeaderControllerSubcomponent) this.subcomponentProvider.get());
    }
}
