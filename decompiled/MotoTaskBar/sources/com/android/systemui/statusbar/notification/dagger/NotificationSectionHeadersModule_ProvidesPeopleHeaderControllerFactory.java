package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationSectionHeadersModule_ProvidesPeopleHeaderControllerFactory implements Factory {
    private final Provider subcomponentProvider;

    public NotificationSectionHeadersModule_ProvidesPeopleHeaderControllerFactory(Provider provider) {
        this.subcomponentProvider = provider;
    }

    public static NotificationSectionHeadersModule_ProvidesPeopleHeaderControllerFactory create(Provider provider) {
        return new NotificationSectionHeadersModule_ProvidesPeopleHeaderControllerFactory(provider);
    }

    public static SectionHeaderController providesPeopleHeaderController(SectionHeaderControllerSubcomponent sectionHeaderControllerSubcomponent) {
        SectionHeaderController sectionHeaderControllerProvidesPeopleHeaderController = NotificationSectionHeadersModule.providesPeopleHeaderController(sectionHeaderControllerSubcomponent);
        sectionHeaderControllerProvidesPeopleHeaderController.getClass();
        return sectionHeaderControllerProvidesPeopleHeaderController;
    }

    @Override // javax.inject.Provider
    public SectionHeaderController get() {
        return providesPeopleHeaderController((SectionHeaderControllerSubcomponent) this.subcomponentProvider.get());
    }
}
