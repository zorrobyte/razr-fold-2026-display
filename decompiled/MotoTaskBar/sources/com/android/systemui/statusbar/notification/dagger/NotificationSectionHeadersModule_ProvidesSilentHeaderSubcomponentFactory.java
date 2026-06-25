package com.android.systemui.statusbar.notification.dagger;

import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationSectionHeadersModule_ProvidesSilentHeaderSubcomponentFactory implements Factory {
    private final Provider builderProvider;

    public NotificationSectionHeadersModule_ProvidesSilentHeaderSubcomponentFactory(Provider provider) {
        this.builderProvider = provider;
    }

    public static NotificationSectionHeadersModule_ProvidesSilentHeaderSubcomponentFactory create(Provider provider) {
        return new NotificationSectionHeadersModule_ProvidesSilentHeaderSubcomponentFactory(provider);
    }

    public static SectionHeaderControllerSubcomponent providesSilentHeaderSubcomponent(javax.inject.Provider provider) {
        SectionHeaderControllerSubcomponent sectionHeaderControllerSubcomponentProvidesSilentHeaderSubcomponent = NotificationSectionHeadersModule.providesSilentHeaderSubcomponent(provider);
        sectionHeaderControllerSubcomponentProvidesSilentHeaderSubcomponent.getClass();
        return sectionHeaderControllerSubcomponentProvidesSilentHeaderSubcomponent;
    }

    @Override // javax.inject.Provider
    public SectionHeaderControllerSubcomponent get() {
        return providesSilentHeaderSubcomponent(this.builderProvider);
    }
}
