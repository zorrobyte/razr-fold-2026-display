package com.android.systemui.statusbar.notification.dagger;

import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationSectionHeadersModule_ProvidesIncomingHeaderSubcomponentFactory implements Factory {
    private final Provider builderProvider;

    public NotificationSectionHeadersModule_ProvidesIncomingHeaderSubcomponentFactory(Provider provider) {
        this.builderProvider = provider;
    }

    public static NotificationSectionHeadersModule_ProvidesIncomingHeaderSubcomponentFactory create(Provider provider) {
        return new NotificationSectionHeadersModule_ProvidesIncomingHeaderSubcomponentFactory(provider);
    }

    public static SectionHeaderControllerSubcomponent providesIncomingHeaderSubcomponent(javax.inject.Provider provider) {
        SectionHeaderControllerSubcomponent sectionHeaderControllerSubcomponentProvidesIncomingHeaderSubcomponent = NotificationSectionHeadersModule.providesIncomingHeaderSubcomponent(provider);
        sectionHeaderControllerSubcomponentProvidesIncomingHeaderSubcomponent.getClass();
        return sectionHeaderControllerSubcomponentProvidesIncomingHeaderSubcomponent;
    }

    @Override // javax.inject.Provider
    public SectionHeaderControllerSubcomponent get() {
        return providesIncomingHeaderSubcomponent(this.builderProvider);
    }
}
