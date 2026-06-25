package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.provider.SectionStyleProvider;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotifCoordinatorsImpl_Factory implements Factory {
    private final Provider colorizedFgsCoordinatorProvider;
    private final Provider conversationCoordinatorProvider;
    private final Provider dataStoreCoordinatorProvider;
    private final Provider deviceProvisionedCoordinatorProvider;
    private final Provider dismissibilityCoordinatorProvider;
    private final Provider groupCountCoordinatorProvider;
    private final Provider groupWhenCoordinatorProvider;
    private final Provider headsUpCoordinatorProvider;
    private final Provider hideLocallyDismissedNotifsCoordinatorProvider;
    private final Provider hideNotifsForOtherUsersCoordinatorProvider;
    private final Provider mediaCoordinatorProvider;
    private final Provider preparationCoordinatorProvider;
    private final Provider rankingCoordinatorProvider;
    private final Provider remoteInputCoordinatorProvider;
    private final Provider rowAlertTimeCoordinatorProvider;
    private final Provider rowAppearanceCoordinatorProvider;
    private final Provider sectionStyleProvider;
    private final Provider sensitiveContentCoordinatorProvider;
    private final Provider shadeEventCoordinatorProvider;
    private final Provider stackCoordinatorProvider;
    private final Provider statsLoggerCoordinatorProvider;
    private final Provider viewConfigCoordinatorProvider;
    private final Provider visualStabilityCoordinatorProvider;

    public NotifCoordinatorsImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23) {
        this.sectionStyleProvider = provider;
        this.dataStoreCoordinatorProvider = provider2;
        this.hideLocallyDismissedNotifsCoordinatorProvider = provider3;
        this.hideNotifsForOtherUsersCoordinatorProvider = provider4;
        this.rankingCoordinatorProvider = provider5;
        this.colorizedFgsCoordinatorProvider = provider6;
        this.deviceProvisionedCoordinatorProvider = provider7;
        this.headsUpCoordinatorProvider = provider8;
        this.conversationCoordinatorProvider = provider9;
        this.groupCountCoordinatorProvider = provider10;
        this.groupWhenCoordinatorProvider = provider11;
        this.mediaCoordinatorProvider = provider12;
        this.preparationCoordinatorProvider = provider13;
        this.remoteInputCoordinatorProvider = provider14;
        this.rowAlertTimeCoordinatorProvider = provider15;
        this.rowAppearanceCoordinatorProvider = provider16;
        this.stackCoordinatorProvider = provider17;
        this.shadeEventCoordinatorProvider = provider18;
        this.viewConfigCoordinatorProvider = provider19;
        this.visualStabilityCoordinatorProvider = provider20;
        this.sensitiveContentCoordinatorProvider = provider21;
        this.dismissibilityCoordinatorProvider = provider22;
        this.statsLoggerCoordinatorProvider = provider23;
    }

    public static NotifCoordinatorsImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23) {
        return new NotifCoordinatorsImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, provider18, provider19, provider20, provider21, provider22, provider23);
    }

    public static NotifCoordinatorsImpl newInstance(SectionStyleProvider sectionStyleProvider, DataStoreCoordinator dataStoreCoordinator, HideLocallyDismissedNotifsCoordinator hideLocallyDismissedNotifsCoordinator, HideNotifsForOtherUsersCoordinator hideNotifsForOtherUsersCoordinator, RankingCoordinator rankingCoordinator, ColorizedFgsCoordinator colorizedFgsCoordinator, DeviceProvisionedCoordinator deviceProvisionedCoordinator, HeadsUpCoordinator headsUpCoordinator, ConversationCoordinator conversationCoordinator, GroupCountCoordinator groupCountCoordinator, GroupWhenCoordinator groupWhenCoordinator, MediaCoordinator mediaCoordinator, PreparationCoordinator preparationCoordinator, RemoteInputCoordinator remoteInputCoordinator, RowAlertTimeCoordinator rowAlertTimeCoordinator, RowAppearanceCoordinator rowAppearanceCoordinator, StackCoordinator stackCoordinator, ShadeEventCoordinator shadeEventCoordinator, ViewConfigCoordinator viewConfigCoordinator, VisualStabilityCoordinator visualStabilityCoordinator, SensitiveContentCoordinator sensitiveContentCoordinator, DismissibilityCoordinator dismissibilityCoordinator, NotificationStatsLoggerCoordinator notificationStatsLoggerCoordinator) {
        return new NotifCoordinatorsImpl(sectionStyleProvider, dataStoreCoordinator, hideLocallyDismissedNotifsCoordinator, hideNotifsForOtherUsersCoordinator, rankingCoordinator, colorizedFgsCoordinator, deviceProvisionedCoordinator, headsUpCoordinator, conversationCoordinator, groupCountCoordinator, groupWhenCoordinator, mediaCoordinator, preparationCoordinator, remoteInputCoordinator, rowAlertTimeCoordinator, rowAppearanceCoordinator, stackCoordinator, shadeEventCoordinator, viewConfigCoordinator, visualStabilityCoordinator, sensitiveContentCoordinator, dismissibilityCoordinator, notificationStatsLoggerCoordinator);
    }

    @Override // javax.inject.Provider
    public NotifCoordinatorsImpl get() {
        return newInstance((SectionStyleProvider) this.sectionStyleProvider.get(), (DataStoreCoordinator) this.dataStoreCoordinatorProvider.get(), (HideLocallyDismissedNotifsCoordinator) this.hideLocallyDismissedNotifsCoordinatorProvider.get(), (HideNotifsForOtherUsersCoordinator) this.hideNotifsForOtherUsersCoordinatorProvider.get(), (RankingCoordinator) this.rankingCoordinatorProvider.get(), (ColorizedFgsCoordinator) this.colorizedFgsCoordinatorProvider.get(), (DeviceProvisionedCoordinator) this.deviceProvisionedCoordinatorProvider.get(), (HeadsUpCoordinator) this.headsUpCoordinatorProvider.get(), (ConversationCoordinator) this.conversationCoordinatorProvider.get(), (GroupCountCoordinator) this.groupCountCoordinatorProvider.get(), (GroupWhenCoordinator) this.groupWhenCoordinatorProvider.get(), (MediaCoordinator) this.mediaCoordinatorProvider.get(), (PreparationCoordinator) this.preparationCoordinatorProvider.get(), (RemoteInputCoordinator) this.remoteInputCoordinatorProvider.get(), (RowAlertTimeCoordinator) this.rowAlertTimeCoordinatorProvider.get(), (RowAppearanceCoordinator) this.rowAppearanceCoordinatorProvider.get(), (StackCoordinator) this.stackCoordinatorProvider.get(), (ShadeEventCoordinator) this.shadeEventCoordinatorProvider.get(), (ViewConfigCoordinator) this.viewConfigCoordinatorProvider.get(), (VisualStabilityCoordinator) this.visualStabilityCoordinatorProvider.get(), (SensitiveContentCoordinator) this.sensitiveContentCoordinatorProvider.get(), (DismissibilityCoordinator) this.dismissibilityCoordinatorProvider.get(), (NotificationStatsLoggerCoordinator) this.statsLoggerCoordinatorProvider.get());
    }
}
