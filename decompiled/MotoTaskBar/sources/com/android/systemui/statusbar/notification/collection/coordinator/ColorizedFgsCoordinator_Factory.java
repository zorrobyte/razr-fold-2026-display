package com.android.systemui.statusbar.notification.collection.coordinator;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class ColorizedFgsCoordinator_Factory implements Factory {

    abstract class InstanceHolder {
        static final ColorizedFgsCoordinator_Factory INSTANCE = new ColorizedFgsCoordinator_Factory();
    }

    public static ColorizedFgsCoordinator_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static ColorizedFgsCoordinator newInstance() {
        return new ColorizedFgsCoordinator();
    }

    @Override // javax.inject.Provider
    public ColorizedFgsCoordinator get() {
        return newInstance();
    }
}
