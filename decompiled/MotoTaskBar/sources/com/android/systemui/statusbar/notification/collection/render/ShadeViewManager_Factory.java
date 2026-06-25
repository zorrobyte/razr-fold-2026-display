package com.android.systemui.statusbar.notification.collection.render;

import android.content.Context;
import com.android.systemui.statusbar.notification.NotificationSectionsFeatureManager;
import com.android.systemui.statusbar.notification.collection.provider.SectionHeaderVisibilityProvider;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ShadeViewManager_Factory {
    private final Provider contextProvider;
    private final Provider featureManagerProvider;
    private final Provider mediaContainerControllerProvider;
    private final Provider nodeSpecBuilderLoggerProvider;
    private final Provider sectionHeaderVisibilityProvider;
    private final Provider shadeViewDifferLoggerProvider;
    private final Provider viewBarnProvider;

    public ShadeViewManager_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        this.contextProvider = provider;
        this.mediaContainerControllerProvider = provider2;
        this.featureManagerProvider = provider3;
        this.sectionHeaderVisibilityProvider = provider4;
        this.nodeSpecBuilderLoggerProvider = provider5;
        this.shadeViewDifferLoggerProvider = provider6;
        this.viewBarnProvider = provider7;
    }

    public static ShadeViewManager_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        return new ShadeViewManager_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7);
    }

    public static ShadeViewManager newInstance(Context context, NotificationListContainer notificationListContainer, NotifStackController notifStackController, MediaContainerController mediaContainerController, NotificationSectionsFeatureManager notificationSectionsFeatureManager, SectionHeaderVisibilityProvider sectionHeaderVisibilityProvider, NodeSpecBuilderLogger nodeSpecBuilderLogger, ShadeViewDifferLogger shadeViewDifferLogger, NotifViewBarn notifViewBarn) {
        return new ShadeViewManager(context, notificationListContainer, notifStackController, mediaContainerController, notificationSectionsFeatureManager, sectionHeaderVisibilityProvider, nodeSpecBuilderLogger, shadeViewDifferLogger, notifViewBarn);
    }

    public ShadeViewManager get(NotificationListContainer notificationListContainer, NotifStackController notifStackController) {
        return newInstance((Context) this.contextProvider.get(), notificationListContainer, notifStackController, (MediaContainerController) this.mediaContainerControllerProvider.get(), (NotificationSectionsFeatureManager) this.featureManagerProvider.get(), (SectionHeaderVisibilityProvider) this.sectionHeaderVisibilityProvider.get(), (NodeSpecBuilderLogger) this.nodeSpecBuilderLoggerProvider.get(), (ShadeViewDifferLogger) this.shadeViewDifferLoggerProvider.get(), (NotifViewBarn) this.viewBarnProvider.get());
    }
}
