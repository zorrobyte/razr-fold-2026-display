package com.android.systemui.statusbar.notification.collection.coordinator;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class RowAlertTimeCoordinator_Factory implements Factory {

    abstract class InstanceHolder {
        static final RowAlertTimeCoordinator_Factory INSTANCE = new RowAlertTimeCoordinator_Factory();
    }

    public static RowAlertTimeCoordinator_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static RowAlertTimeCoordinator newInstance() {
        return new RowAlertTimeCoordinator();
    }

    @Override // javax.inject.Provider
    public RowAlertTimeCoordinator get() {
        return newInstance();
    }
}
