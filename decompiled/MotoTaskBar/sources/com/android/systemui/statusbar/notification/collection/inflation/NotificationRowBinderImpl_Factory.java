package com.android.systemui.statusbar.notification.collection.inflation;

import android.content.Context;
import com.android.internal.util.NotificationMessagingUtil;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.icon.IconManager;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import com.android.systemui.statusbar.notification.row.dagger.ExpandableNotificationRowComponent;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationRowBinderImpl_Factory implements Factory {
    private final Provider contextProvider;
    private final Provider expandableNotificationRowComponentBuilderProvider;
    private final Provider featureFlagsProvider;
    private final Provider iconManagerProvider;
    private final Provider loggerProvider;
    private final Provider notifBindPipelineProvider;
    private final Provider notificationLockscreenUserManagerProvider;
    private final Provider notificationMessagingUtilProvider;
    private final Provider notificationRemoteInputManagerProvider;
    private final Provider rowContentBindStageProvider;
    private final Provider rowInflaterTaskProvider;

    public NotificationRowBinderImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11) {
        this.contextProvider = provider;
        this.notificationMessagingUtilProvider = provider2;
        this.notificationRemoteInputManagerProvider = provider3;
        this.notificationLockscreenUserManagerProvider = provider4;
        this.notifBindPipelineProvider = provider5;
        this.rowContentBindStageProvider = provider6;
        this.rowInflaterTaskProvider = provider7;
        this.expandableNotificationRowComponentBuilderProvider = provider8;
        this.iconManagerProvider = provider9;
        this.loggerProvider = provider10;
        this.featureFlagsProvider = provider11;
    }

    public static NotificationRowBinderImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11) {
        return new NotificationRowBinderImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11);
    }

    public static NotificationRowBinderImpl newInstance(Context context, NotificationMessagingUtil notificationMessagingUtil, NotificationRemoteInputManager notificationRemoteInputManager, NotificationLockscreenUserManager notificationLockscreenUserManager, NotifBindPipeline notifBindPipeline, RowContentBindStage rowContentBindStage, javax.inject.Provider provider, ExpandableNotificationRowComponent.Builder builder, IconManager iconManager, NotificationRowBinderLogger notificationRowBinderLogger, FeatureFlags featureFlags) {
        return new NotificationRowBinderImpl(context, notificationMessagingUtil, notificationRemoteInputManager, notificationLockscreenUserManager, notifBindPipeline, rowContentBindStage, provider, builder, iconManager, notificationRowBinderLogger, featureFlags);
    }

    @Override // javax.inject.Provider
    public NotificationRowBinderImpl get() {
        return newInstance((Context) this.contextProvider.get(), (NotificationMessagingUtil) this.notificationMessagingUtilProvider.get(), (NotificationRemoteInputManager) this.notificationRemoteInputManagerProvider.get(), (NotificationLockscreenUserManager) this.notificationLockscreenUserManagerProvider.get(), (NotifBindPipeline) this.notifBindPipelineProvider.get(), (RowContentBindStage) this.rowContentBindStageProvider.get(), this.rowInflaterTaskProvider, (ExpandableNotificationRowComponent.Builder) this.expandableNotificationRowComponentBuilderProvider.get(), (IconManager) this.iconManagerProvider.get(), (NotificationRowBinderLogger) this.loggerProvider.get(), (FeatureFlags) this.featureFlagsProvider.get());
    }
}
