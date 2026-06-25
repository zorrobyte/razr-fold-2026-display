package com.android.systemui.statusbar.notification.collection.coordinator.dagger;

import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinators;

/* JADX INFO: compiled from: CoordinatorsModule.kt */
/* JADX INFO: loaded from: classes.dex */
public interface CoordinatorsSubcomponent {

    /* JADX INFO: compiled from: CoordinatorsModule.kt */
    public interface Factory {
        CoordinatorsSubcomponent create();
    }

    NotifCoordinators getNotifCoordinators();
}
