package com.android.systemui.statusbar.notification.stack;

import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.ColorUpdateLogger;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProvider;
import com.android.systemui.statusbar.notification.collection.provider.VisibilityLocationProviderDelegator;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor;
import com.android.systemui.statusbar.notification.init.NotificationsController;
import com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper;
import com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationStackScrollLayoutController_Factory implements Factory {
    private final Provider activityStarterProvider;
    private final Provider allowLongPressProvider;
    private final Provider colorUpdateLoggerProvider;
    private final Provider configurationControllerProvider;
    private final Provider deviceProvisionedControllerProvider;
    private final Provider dismissibilityProvider;
    private final Provider dumpManagerProvider;
    private final Provider dynamicPrivacyControllerProvider;
    private final Provider groupManagerProvider;
    private final Provider headsUpManagerProvider;
    private final Provider jankMonitorProvider;
    private final Provider lockscreenUserManagerProvider;
    private final Provider loggerProvider;
    private final Provider notifCollectionProvider;
    private final Provider notifPipelineProvider;
    private final Provider notificationRoundnessManagerProvider;
    private final Provider notificationStackSizeCalculatorProvider;
    private final Provider notificationSwipeHelperBuilderProvider;
    private final Provider notificationTargetsHelperProvider;
    private final Provider notificationsControllerProvider;
    private final Provider remoteInputManagerProvider;
    private final Provider secureSettingsProvider;
    private final Provider seenNotificationsInteractorProvider;
    private final Provider sensitiveNotificationProtectionControllerProvider;
    private final Provider silentHeaderControllerProvider;
    private final Provider stackLoggerProvider;
    private final Provider uiEventLoggerProvider;
    private final Provider viewBinderProvider;
    private final Provider visibilityLocationProviderDelegatorProvider;
    private final Provider visibilityProvider;

    public NotificationStackScrollLayoutController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26, Provider provider27, Provider provider28, Provider provider29, Provider provider30) {
        this.allowLongPressProvider = provider;
        this.notificationsControllerProvider = provider2;
        this.visibilityProvider = provider3;
        this.headsUpManagerProvider = provider4;
        this.notificationRoundnessManagerProvider = provider5;
        this.deviceProvisionedControllerProvider = provider6;
        this.dynamicPrivacyControllerProvider = provider7;
        this.configurationControllerProvider = provider8;
        this.lockscreenUserManagerProvider = provider9;
        this.colorUpdateLoggerProvider = provider10;
        this.dumpManagerProvider = provider11;
        this.notificationSwipeHelperBuilderProvider = provider12;
        this.groupManagerProvider = provider13;
        this.silentHeaderControllerProvider = provider14;
        this.notifPipelineProvider = provider15;
        this.notifCollectionProvider = provider16;
        this.uiEventLoggerProvider = provider17;
        this.remoteInputManagerProvider = provider18;
        this.visibilityLocationProviderDelegatorProvider = provider19;
        this.seenNotificationsInteractorProvider = provider20;
        this.viewBinderProvider = provider21;
        this.jankMonitorProvider = provider22;
        this.stackLoggerProvider = provider23;
        this.loggerProvider = provider24;
        this.notificationStackSizeCalculatorProvider = provider25;
        this.notificationTargetsHelperProvider = provider26;
        this.secureSettingsProvider = provider27;
        this.dismissibilityProvider = provider28;
        this.activityStarterProvider = provider29;
        this.sensitiveNotificationProtectionControllerProvider = provider30;
    }

    public static NotificationStackScrollLayoutController_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26, Provider provider27, Provider provider28, Provider provider29, Provider provider30) {
        return new NotificationStackScrollLayoutController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, provider18, provider19, provider20, provider21, provider22, provider23, provider24, provider25, provider26, provider27, provider28, provider29, provider30);
    }

    public static NotificationStackScrollLayoutController newInstance(boolean z, NotificationsController notificationsController, NotificationVisibilityProvider notificationVisibilityProvider, HeadsUpManager headsUpManager, NotificationRoundnessManager notificationRoundnessManager, DeviceProvisionedController deviceProvisionedController, DynamicPrivacyController dynamicPrivacyController, ConfigurationController configurationController, NotificationLockscreenUserManager notificationLockscreenUserManager, ColorUpdateLogger colorUpdateLogger, DumpManager dumpManager, Object obj, GroupExpansionManager groupExpansionManager, SectionHeaderController sectionHeaderController, NotifPipeline notifPipeline, NotifCollection notifCollection, UiEventLogger uiEventLogger, NotificationRemoteInputManager notificationRemoteInputManager, VisibilityLocationProviderDelegator visibilityLocationProviderDelegator, SeenNotificationsInteractor seenNotificationsInteractor, NotificationListViewBinder notificationListViewBinder, InteractionJankMonitor interactionJankMonitor, StackStateLogger stackStateLogger, NotificationStackScrollLogger notificationStackScrollLogger, NotificationStackSizeCalculator notificationStackSizeCalculator, NotificationTargetsHelper notificationTargetsHelper, SecureSettings secureSettings, NotificationDismissibilityProvider notificationDismissibilityProvider, ActivityStarter activityStarter, SensitiveNotificationProtectionController sensitiveNotificationProtectionController) {
        return new NotificationStackScrollLayoutController(z, notificationsController, notificationVisibilityProvider, headsUpManager, notificationRoundnessManager, deviceProvisionedController, dynamicPrivacyController, configurationController, notificationLockscreenUserManager, colorUpdateLogger, dumpManager, (NotificationSwipeHelper.Builder) obj, groupExpansionManager, sectionHeaderController, notifPipeline, notifCollection, uiEventLogger, notificationRemoteInputManager, visibilityLocationProviderDelegator, seenNotificationsInteractor, notificationListViewBinder, interactionJankMonitor, stackStateLogger, notificationStackScrollLogger, notificationStackSizeCalculator, notificationTargetsHelper, secureSettings, notificationDismissibilityProvider, activityStarter, sensitiveNotificationProtectionController);
    }

    @Override // javax.inject.Provider
    public NotificationStackScrollLayoutController get() {
        return newInstance(((Boolean) this.allowLongPressProvider.get()).booleanValue(), (NotificationsController) this.notificationsControllerProvider.get(), (NotificationVisibilityProvider) this.visibilityProvider.get(), (HeadsUpManager) this.headsUpManagerProvider.get(), (NotificationRoundnessManager) this.notificationRoundnessManagerProvider.get(), (DeviceProvisionedController) this.deviceProvisionedControllerProvider.get(), (DynamicPrivacyController) this.dynamicPrivacyControllerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get(), (ColorUpdateLogger) this.colorUpdateLoggerProvider.get(), (DumpManager) this.dumpManagerProvider.get(), this.notificationSwipeHelperBuilderProvider.get(), (GroupExpansionManager) this.groupManagerProvider.get(), (SectionHeaderController) this.silentHeaderControllerProvider.get(), (NotifPipeline) this.notifPipelineProvider.get(), (NotifCollection) this.notifCollectionProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (NotificationRemoteInputManager) this.remoteInputManagerProvider.get(), (VisibilityLocationProviderDelegator) this.visibilityLocationProviderDelegatorProvider.get(), (SeenNotificationsInteractor) this.seenNotificationsInteractorProvider.get(), (NotificationListViewBinder) this.viewBinderProvider.get(), (InteractionJankMonitor) this.jankMonitorProvider.get(), (StackStateLogger) this.stackLoggerProvider.get(), (NotificationStackScrollLogger) this.loggerProvider.get(), (NotificationStackSizeCalculator) this.notificationStackSizeCalculatorProvider.get(), (NotificationTargetsHelper) this.notificationTargetsHelperProvider.get(), (SecureSettings) this.secureSettingsProvider.get(), (NotificationDismissibilityProvider) this.dismissibilityProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (SensitiveNotificationProtectionController) this.sensitiveNotificationProtectionControllerProvider.get());
    }
}
