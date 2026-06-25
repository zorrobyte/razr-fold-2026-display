package com.android.systemui.statusbar.notification.collection.coordinator.dagger;

import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinators;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorsSubcomponent;

/* JADX INFO: compiled from: CoordinatorsModule.kt */
/* JADX INFO: loaded from: classes.dex */
public final class CoordinatorsModule {
    public static final CoordinatorsModule INSTANCE = new CoordinatorsModule();

    private CoordinatorsModule() {
    }

    public static final NotifCoordinators notifCoordinators(CoordinatorsSubcomponent.Factory factory) {
        factory.getClass();
        return factory.create().getNotifCoordinators();
    }
}
