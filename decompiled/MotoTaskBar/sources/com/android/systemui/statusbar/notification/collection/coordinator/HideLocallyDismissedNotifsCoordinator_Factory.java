package com.android.systemui.statusbar.notification.collection.coordinator;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class HideLocallyDismissedNotifsCoordinator_Factory implements Factory {

    abstract class InstanceHolder {
        static final HideLocallyDismissedNotifsCoordinator_Factory INSTANCE = new HideLocallyDismissedNotifsCoordinator_Factory();
    }

    public static HideLocallyDismissedNotifsCoordinator_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static HideLocallyDismissedNotifsCoordinator newInstance() {
        return new HideLocallyDismissedNotifsCoordinator();
    }

    @Override // javax.inject.Provider
    public HideLocallyDismissedNotifsCoordinator get() {
        return newInstance();
    }
}
