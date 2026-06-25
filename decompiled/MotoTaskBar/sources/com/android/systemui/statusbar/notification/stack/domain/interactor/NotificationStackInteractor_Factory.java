package com.android.systemui.statusbar.notification.stack.domain.interactor;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationStackInteractor_Factory implements Factory {

    abstract class InstanceHolder {
        static final NotificationStackInteractor_Factory INSTANCE = new NotificationStackInteractor_Factory();
    }

    public static NotificationStackInteractor_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static NotificationStackInteractor newInstance() {
        return new NotificationStackInteractor();
    }

    @Override // javax.inject.Provider
    public NotificationStackInteractor get() {
        return newInstance();
    }
}
