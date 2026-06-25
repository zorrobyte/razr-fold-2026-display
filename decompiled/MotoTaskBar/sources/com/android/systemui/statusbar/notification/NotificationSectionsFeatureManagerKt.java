package com.android.systemui.statusbar.notification;

import com.android.systemui.util.DeviceConfigProxy;

/* JADX INFO: compiled from: NotificationSectionsFeatureManager.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class NotificationSectionsFeatureManagerKt {
    private static Boolean sUsePeopleFiltering;

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean usePeopleFiltering(DeviceConfigProxy deviceConfigProxy) {
        if (sUsePeopleFiltering == null) {
            sUsePeopleFiltering = Boolean.valueOf(deviceConfigProxy.getBoolean("systemui", "notifications_use_people_filtering", true));
        }
        Boolean bool = sUsePeopleFiltering;
        bool.getClass();
        return bool.booleanValue();
    }
}
