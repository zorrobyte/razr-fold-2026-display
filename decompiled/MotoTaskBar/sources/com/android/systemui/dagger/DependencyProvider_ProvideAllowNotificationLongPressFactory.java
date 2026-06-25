package com.android.systemui.dagger;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class DependencyProvider_ProvideAllowNotificationLongPressFactory implements Factory {

    abstract class InstanceHolder {
        static final DependencyProvider_ProvideAllowNotificationLongPressFactory INSTANCE = new DependencyProvider_ProvideAllowNotificationLongPressFactory();
    }

    public static DependencyProvider_ProvideAllowNotificationLongPressFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static boolean provideAllowNotificationLongPress() {
        return DependencyProvider.provideAllowNotificationLongPress();
    }

    @Override // javax.inject.Provider
    public Boolean get() {
        return Boolean.valueOf(provideAllowNotificationLongPress());
    }
}
