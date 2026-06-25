package com.android.systemui.statusbar.notification.row.dagger;

import android.content.Context;
import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.row.dagger.ExpandableNotificationRowComponent;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ExpandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideAppNameFactory implements Factory {
    private final Provider contextProvider;
    private final Provider statusBarNotificationProvider;

    public ExpandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideAppNameFactory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.statusBarNotificationProvider = provider2;
    }

    public static ExpandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideAppNameFactory create(Provider provider, Provider provider2) {
        return new ExpandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideAppNameFactory(provider, provider2);
    }

    public static String provideAppName(Context context, StatusBarNotification statusBarNotification) {
        String strProvideAppName = ExpandableNotificationRowComponent.ExpandableNotificationRowModule.provideAppName(context, statusBarNotification);
        strProvideAppName.getClass();
        return strProvideAppName;
    }

    @Override // javax.inject.Provider
    public String get() {
        return provideAppName((Context) this.contextProvider.get(), (StatusBarNotification) this.statusBarNotificationProvider.get());
    }
}
