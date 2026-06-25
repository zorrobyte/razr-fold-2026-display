package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.NotificationSectionsFeatureManager;
import com.android.systemui.statusbar.notification.collection.render.MediaContainerController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationSectionsManager_Factory implements Factory {
    private final Provider alertingHeaderControllerProvider;
    private final Provider configurationControllerProvider;
    private final Provider incomingHeaderControllerProvider;
    private final Provider mediaContainerControllerProvider;
    private final Provider notificationRoundnessManagerProvider;
    private final Provider peopleHeaderControllerProvider;
    private final Provider sectionsFeatureManagerProvider;
    private final Provider silentHeaderControllerProvider;

    public NotificationSectionsManager_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        this.configurationControllerProvider = provider;
        this.sectionsFeatureManagerProvider = provider2;
        this.mediaContainerControllerProvider = provider3;
        this.notificationRoundnessManagerProvider = provider4;
        this.incomingHeaderControllerProvider = provider5;
        this.peopleHeaderControllerProvider = provider6;
        this.alertingHeaderControllerProvider = provider7;
        this.silentHeaderControllerProvider = provider8;
    }

    public static NotificationSectionsManager_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        return new NotificationSectionsManager_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public static NotificationSectionsManager newInstance(ConfigurationController configurationController, NotificationSectionsFeatureManager notificationSectionsFeatureManager, MediaContainerController mediaContainerController, NotificationRoundnessManager notificationRoundnessManager, SectionHeaderController sectionHeaderController, SectionHeaderController sectionHeaderController2, SectionHeaderController sectionHeaderController3, SectionHeaderController sectionHeaderController4) {
        return new NotificationSectionsManager(configurationController, notificationSectionsFeatureManager, mediaContainerController, notificationRoundnessManager, sectionHeaderController, sectionHeaderController2, sectionHeaderController3, sectionHeaderController4);
    }

    @Override // javax.inject.Provider
    public NotificationSectionsManager get() {
        return newInstance((ConfigurationController) this.configurationControllerProvider.get(), (NotificationSectionsFeatureManager) this.sectionsFeatureManagerProvider.get(), (MediaContainerController) this.mediaContainerControllerProvider.get(), (NotificationRoundnessManager) this.notificationRoundnessManagerProvider.get(), (SectionHeaderController) this.incomingHeaderControllerProvider.get(), (SectionHeaderController) this.peopleHeaderControllerProvider.get(), (SectionHeaderController) this.alertingHeaderControllerProvider.get(), (SectionHeaderController) this.silentHeaderControllerProvider.get());
    }
}
