package com.android.systemui.statusbar.notification.row.dagger;

import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.row.dagger.ExpandableNotificationRowComponent;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ExpandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideNotificationKeyFactory implements Factory {
    private final Provider statusBarNotificationProvider;

    public ExpandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideNotificationKeyFactory(Provider provider) {
        this.statusBarNotificationProvider = provider;
    }

    public static ExpandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideNotificationKeyFactory create(Provider provider) {
        return new ExpandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideNotificationKeyFactory(provider);
    }

    public static String provideNotificationKey(StatusBarNotification statusBarNotification) {
        String strProvideNotificationKey = ExpandableNotificationRowComponent.ExpandableNotificationRowModule.provideNotificationKey(statusBarNotification);
        strProvideNotificationKey.getClass();
        return strProvideNotificationKey;
    }

    @Override // javax.inject.Provider
    public String get() {
        return provideNotificationKey((StatusBarNotification) this.statusBarNotificationProvider.get());
    }
}
