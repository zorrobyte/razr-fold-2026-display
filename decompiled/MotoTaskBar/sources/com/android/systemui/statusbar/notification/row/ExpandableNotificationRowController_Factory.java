package com.android.systemui.statusbar.notification.row;

import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.notification.ColorUpdateLogger;
import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProvider;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainerLogger;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationRowStatsLogger;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.SmartReplyConstants;
import com.android.systemui.statusbar.policy.dagger.RemoteInputViewSubcomponent;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ExpandableNotificationRowController_Factory implements Factory {
    private final Provider activatableNotificationViewControllerProvider;
    private final Provider allowLongPressProvider;
    private final Provider appNameProvider;
    private final Provider childrenContainerLoggerProvider;
    private final Provider clockProvider;
    private final Provider colorUpdateLoggerProvider;
    private final Provider dismissibilityProvider;
    private final Provider dragControllerProvider;
    private final Provider featureFlagsProvider;
    private final Provider groupExpansionManagerProvider;
    private final Provider groupMembershipManagerProvider;
    private final Provider headsUpManagerProvider;
    private final Provider listContainerProvider;
    private final Provider logBufferLoggerProvider;
    private final Provider notificationKeyProvider;
    private final Provider onExpandClickListenerProvider;
    private final Provider onUserInteractionCallbackProvider;
    private final Provider peopleNotificationIdentifierProvider;
    private final Provider rivSubcomponentFactoryProvider;
    private final Provider rowContentBindStageProvider;
    private final Provider settingsControllerProvider;
    private final Provider smartReplyConstantsProvider;
    private final Provider smartReplyControllerProvider;
    private final Provider statsLoggerProvider;
    private final Provider statusBarServiceProvider;
    private final Provider viewProvider;

    public ExpandableNotificationRowController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26) {
        this.viewProvider = provider;
        this.activatableNotificationViewControllerProvider = provider2;
        this.rivSubcomponentFactoryProvider = provider3;
        this.colorUpdateLoggerProvider = provider4;
        this.logBufferLoggerProvider = provider5;
        this.childrenContainerLoggerProvider = provider6;
        this.listContainerProvider = provider7;
        this.smartReplyConstantsProvider = provider8;
        this.smartReplyControllerProvider = provider9;
        this.clockProvider = provider10;
        this.appNameProvider = provider11;
        this.notificationKeyProvider = provider12;
        this.groupMembershipManagerProvider = provider13;
        this.groupExpansionManagerProvider = provider14;
        this.rowContentBindStageProvider = provider15;
        this.statsLoggerProvider = provider16;
        this.headsUpManagerProvider = provider17;
        this.onExpandClickListenerProvider = provider18;
        this.allowLongPressProvider = provider19;
        this.onUserInteractionCallbackProvider = provider20;
        this.featureFlagsProvider = provider21;
        this.peopleNotificationIdentifierProvider = provider22;
        this.settingsControllerProvider = provider23;
        this.dragControllerProvider = provider24;
        this.dismissibilityProvider = provider25;
        this.statusBarServiceProvider = provider26;
    }

    public static ExpandableNotificationRowController_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26) {
        return new ExpandableNotificationRowController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, provider18, provider19, provider20, provider21, provider22, provider23, provider24, provider25, provider26);
    }

    public static ExpandableNotificationRowController newInstance(ExpandableNotificationRow expandableNotificationRow, ActivatableNotificationViewController activatableNotificationViewController, RemoteInputViewSubcomponent.Factory factory, ColorUpdateLogger colorUpdateLogger, NotificationRowLogger notificationRowLogger, NotificationChildrenContainerLogger notificationChildrenContainerLogger, NotificationListContainer notificationListContainer, SmartReplyConstants smartReplyConstants, SmartReplyController smartReplyController, SystemClock systemClock, String str, String str2, GroupMembershipManager groupMembershipManager, GroupExpansionManager groupExpansionManager, RowContentBindStage rowContentBindStage, NotificationRowStatsLogger notificationRowStatsLogger, HeadsUpManager headsUpManager, ExpandableNotificationRow.OnExpandClickListener onExpandClickListener, boolean z, OnUserInteractionCallback onUserInteractionCallback, FeatureFlags featureFlags, PeopleNotificationIdentifier peopleNotificationIdentifier, NotificationSettingsController notificationSettingsController, ExpandableNotificationRowDragController expandableNotificationRowDragController, NotificationDismissibilityProvider notificationDismissibilityProvider, IStatusBarService iStatusBarService) {
        return new ExpandableNotificationRowController(expandableNotificationRow, activatableNotificationViewController, factory, colorUpdateLogger, notificationRowLogger, notificationChildrenContainerLogger, notificationListContainer, smartReplyConstants, smartReplyController, systemClock, str, str2, groupMembershipManager, groupExpansionManager, rowContentBindStage, notificationRowStatsLogger, headsUpManager, onExpandClickListener, z, onUserInteractionCallback, featureFlags, peopleNotificationIdentifier, notificationSettingsController, expandableNotificationRowDragController, notificationDismissibilityProvider, iStatusBarService);
    }

    @Override // javax.inject.Provider
    public ExpandableNotificationRowController get() {
        return newInstance((ExpandableNotificationRow) this.viewProvider.get(), (ActivatableNotificationViewController) this.activatableNotificationViewControllerProvider.get(), (RemoteInputViewSubcomponent.Factory) this.rivSubcomponentFactoryProvider.get(), (ColorUpdateLogger) this.colorUpdateLoggerProvider.get(), (NotificationRowLogger) this.logBufferLoggerProvider.get(), (NotificationChildrenContainerLogger) this.childrenContainerLoggerProvider.get(), (NotificationListContainer) this.listContainerProvider.get(), (SmartReplyConstants) this.smartReplyConstantsProvider.get(), (SmartReplyController) this.smartReplyControllerProvider.get(), (SystemClock) this.clockProvider.get(), (String) this.appNameProvider.get(), (String) this.notificationKeyProvider.get(), (GroupMembershipManager) this.groupMembershipManagerProvider.get(), (GroupExpansionManager) this.groupExpansionManagerProvider.get(), (RowContentBindStage) this.rowContentBindStageProvider.get(), (NotificationRowStatsLogger) this.statsLoggerProvider.get(), (HeadsUpManager) this.headsUpManagerProvider.get(), (ExpandableNotificationRow.OnExpandClickListener) this.onExpandClickListenerProvider.get(), ((Boolean) this.allowLongPressProvider.get()).booleanValue(), (OnUserInteractionCallback) this.onUserInteractionCallbackProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), (PeopleNotificationIdentifier) this.peopleNotificationIdentifierProvider.get(), (NotificationSettingsController) this.settingsControllerProvider.get(), (ExpandableNotificationRowDragController) this.dragControllerProvider.get(), (NotificationDismissibilityProvider) this.dismissibilityProvider.get(), (IStatusBarService) this.statusBarServiceProvider.get());
    }
}
