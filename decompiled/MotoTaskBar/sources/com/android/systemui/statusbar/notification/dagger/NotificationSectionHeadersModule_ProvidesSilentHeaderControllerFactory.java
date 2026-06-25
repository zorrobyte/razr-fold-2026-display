package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationSectionHeadersModule_ProvidesSilentHeaderControllerFactory implements Factory {
    private final Provider subcomponentProvider;

    public NotificationSectionHeadersModule_ProvidesSilentHeaderControllerFactory(Provider provider) {
        this.subcomponentProvider = provider;
    }

    public static NotificationSectionHeadersModule_ProvidesSilentHeaderControllerFactory create(Provider provider) {
        return new NotificationSectionHeadersModule_ProvidesSilentHeaderControllerFactory(provider);
    }

    public static SectionHeaderController providesSilentHeaderController(SectionHeaderControllerSubcomponent sectionHeaderControllerSubcomponent) {
        SectionHeaderController sectionHeaderControllerProvidesSilentHeaderController = NotificationSectionHeadersModule.providesSilentHeaderController(sectionHeaderControllerSubcomponent);
        sectionHeaderControllerProvidesSilentHeaderController.getClass();
        return sectionHeaderControllerProvidesSilentHeaderController;
    }

    @Override // javax.inject.Provider
    public SectionHeaderController get() {
        return providesSilentHeaderController((SectionHeaderControllerSubcomponent) this.subcomponentProvider.get());
    }
}
