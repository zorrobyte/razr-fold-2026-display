package com.android.systemui.dagger;

import android.content.Context;
import com.android.internal.util.NotificationMessagingUtil;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class DependencyProvider_ProvideNotificationMessagingUtilFactory implements Factory {
    private final Provider contextProvider;
    private final DependencyProvider module;

    public DependencyProvider_ProvideNotificationMessagingUtilFactory(DependencyProvider dependencyProvider, Provider provider) {
        this.module = dependencyProvider;
        this.contextProvider = provider;
    }

    public static DependencyProvider_ProvideNotificationMessagingUtilFactory create(DependencyProvider dependencyProvider, Provider provider) {
        return new DependencyProvider_ProvideNotificationMessagingUtilFactory(dependencyProvider, provider);
    }

    public static NotificationMessagingUtil provideNotificationMessagingUtil(DependencyProvider dependencyProvider, Context context) {
        NotificationMessagingUtil notificationMessagingUtilProvideNotificationMessagingUtil = dependencyProvider.provideNotificationMessagingUtil(context);
        notificationMessagingUtilProvideNotificationMessagingUtil.getClass();
        return notificationMessagingUtilProvideNotificationMessagingUtil;
    }

    @Override // javax.inject.Provider
    public NotificationMessagingUtil get() {
        return provideNotificationMessagingUtil(this.module, (Context) this.contextProvider.get());
    }
}
