package com.android.systemui.statusbar.notification.stack.ui.view;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationStatsLoggerImpl_Factory implements Factory {

    abstract class InstanceHolder {
        static final NotificationStatsLoggerImpl_Factory INSTANCE = new NotificationStatsLoggerImpl_Factory();
    }

    public static NotificationStatsLoggerImpl_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static NotificationStatsLoggerImpl newInstance() {
        return new NotificationStatsLoggerImpl();
    }

    @Override // javax.inject.Provider
    public NotificationStatsLoggerImpl get() {
        return newInstance();
    }
}
