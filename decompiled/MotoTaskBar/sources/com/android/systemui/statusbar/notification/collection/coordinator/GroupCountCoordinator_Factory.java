package com.android.systemui.statusbar.notification.collection.coordinator;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class GroupCountCoordinator_Factory implements Factory {

    abstract class InstanceHolder {
        static final GroupCountCoordinator_Factory INSTANCE = new GroupCountCoordinator_Factory();
    }

    public static GroupCountCoordinator_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static GroupCountCoordinator newInstance() {
        return new GroupCountCoordinator();
    }

    @Override // javax.inject.Provider
    public GroupCountCoordinator get() {
        return newInstance();
    }
}
