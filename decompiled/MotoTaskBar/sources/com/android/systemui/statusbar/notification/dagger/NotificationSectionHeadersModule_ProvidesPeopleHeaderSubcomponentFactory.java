package com.android.systemui.statusbar.notification.dagger;

import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationSectionHeadersModule_ProvidesPeopleHeaderSubcomponentFactory implements Factory {
    private final Provider builderProvider;

    public NotificationSectionHeadersModule_ProvidesPeopleHeaderSubcomponentFactory(Provider provider) {
        this.builderProvider = provider;
    }

    public static NotificationSectionHeadersModule_ProvidesPeopleHeaderSubcomponentFactory create(Provider provider) {
        return new NotificationSectionHeadersModule_ProvidesPeopleHeaderSubcomponentFactory(provider);
    }

    public static SectionHeaderControllerSubcomponent providesPeopleHeaderSubcomponent(javax.inject.Provider provider) {
        SectionHeaderControllerSubcomponent sectionHeaderControllerSubcomponentProvidesPeopleHeaderSubcomponent = NotificationSectionHeadersModule.providesPeopleHeaderSubcomponent(provider);
        sectionHeaderControllerSubcomponentProvidesPeopleHeaderSubcomponent.getClass();
        return sectionHeaderControllerSubcomponentProvidesPeopleHeaderSubcomponent;
    }

    @Override // javax.inject.Provider
    public SectionHeaderControllerSubcomponent get() {
        return providesPeopleHeaderSubcomponent(this.builderProvider);
    }
}
