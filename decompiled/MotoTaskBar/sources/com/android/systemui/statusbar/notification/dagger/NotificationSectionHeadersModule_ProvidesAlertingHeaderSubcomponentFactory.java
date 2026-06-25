package com.android.systemui.statusbar.notification.dagger;

import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationSectionHeadersModule_ProvidesAlertingHeaderSubcomponentFactory implements Factory {
    private final Provider builderProvider;

    public NotificationSectionHeadersModule_ProvidesAlertingHeaderSubcomponentFactory(Provider provider) {
        this.builderProvider = provider;
    }

    public static NotificationSectionHeadersModule_ProvidesAlertingHeaderSubcomponentFactory create(Provider provider) {
        return new NotificationSectionHeadersModule_ProvidesAlertingHeaderSubcomponentFactory(provider);
    }

    public static SectionHeaderControllerSubcomponent providesAlertingHeaderSubcomponent(javax.inject.Provider provider) {
        SectionHeaderControllerSubcomponent sectionHeaderControllerSubcomponentProvidesAlertingHeaderSubcomponent = NotificationSectionHeadersModule.providesAlertingHeaderSubcomponent(provider);
        sectionHeaderControllerSubcomponentProvidesAlertingHeaderSubcomponent.getClass();
        return sectionHeaderControllerSubcomponentProvidesAlertingHeaderSubcomponent;
    }

    @Override // javax.inject.Provider
    public SectionHeaderControllerSubcomponent get() {
        return providesAlertingHeaderSubcomponent(this.builderProvider);
    }
}
