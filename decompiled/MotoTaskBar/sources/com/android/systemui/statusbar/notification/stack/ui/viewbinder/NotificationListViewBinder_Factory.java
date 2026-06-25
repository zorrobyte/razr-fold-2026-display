package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import com.android.systemui.common.ui.ConfigurationState;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel;
import com.android.systemui.statusbar.notification.ui.viewbinder.HeadsUpNotificationViewBinder;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.Optional;
import kotlinx.coroutines.CoroutineDispatcher;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationListViewBinder_Factory implements Factory {
    private final Provider backgroundDispatcherProvider;
    private final Provider configurationProvider;
    private final Provider hunBinderProvider;
    private final Provider loggerOptionalProvider;
    private final Provider notificationActivityStarterProvider;
    private final Provider silentHeaderControllerProvider;
    private final Provider viewModelProvider;

    public NotificationListViewBinder_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        this.backgroundDispatcherProvider = provider;
        this.configurationProvider = provider2;
        this.hunBinderProvider = provider3;
        this.loggerOptionalProvider = provider4;
        this.notificationActivityStarterProvider = provider5;
        this.silentHeaderControllerProvider = provider6;
        this.viewModelProvider = provider7;
    }

    public static NotificationListViewBinder_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        return new NotificationListViewBinder_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7);
    }

    public static NotificationListViewBinder newInstance(CoroutineDispatcher coroutineDispatcher, ConfigurationState configurationState, HeadsUpNotificationViewBinder headsUpNotificationViewBinder, Optional optional, javax.inject.Provider provider, SectionHeaderController sectionHeaderController, NotificationListViewModel notificationListViewModel) {
        return new NotificationListViewBinder(coroutineDispatcher, configurationState, headsUpNotificationViewBinder, optional, provider, sectionHeaderController, notificationListViewModel);
    }

    @Override // javax.inject.Provider
    public NotificationListViewBinder get() {
        return newInstance((CoroutineDispatcher) this.backgroundDispatcherProvider.get(), (ConfigurationState) this.configurationProvider.get(), (HeadsUpNotificationViewBinder) this.hunBinderProvider.get(), (Optional) this.loggerOptionalProvider.get(), this.notificationActivityStarterProvider, (SectionHeaderController) this.silentHeaderControllerProvider.get(), (NotificationListViewModel) this.viewModelProvider.get());
    }
}
