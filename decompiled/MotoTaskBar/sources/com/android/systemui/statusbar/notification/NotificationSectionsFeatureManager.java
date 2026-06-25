package com.android.systemui.statusbar.notification;

import android.content.Context;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.Utils;

/* JADX INFO: compiled from: NotificationSectionsFeatureManager.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationSectionsFeatureManager {
    private final Context context;
    private final DeviceConfigProxy proxy;

    public NotificationSectionsFeatureManager(DeviceConfigProxy deviceConfigProxy, Context context) {
        deviceConfigProxy.getClass();
        context.getClass();
        this.proxy = deviceConfigProxy;
        this.context = context;
    }

    public final void clearCache() {
        NotificationSectionsFeatureManagerKt.sUsePeopleFiltering = null;
    }

    public final int[] getNotificationBuckets() {
        return (isFilteringEnabled() && isMediaControlsEnabled()) ? new int[]{2, 3, 1, 4, 5, 6} : (isFilteringEnabled() || !isMediaControlsEnabled()) ? (!isFilteringEnabled() || isMediaControlsEnabled()) ? new int[]{5, 6} : new int[]{2, 3, 4, 5, 6} : new int[]{2, 3, 1, 5, 6};
    }

    public final boolean isFilteringEnabled() {
        return NotificationSectionsFeatureManagerKt.usePeopleFiltering(this.proxy);
    }

    public final boolean isMediaControlsEnabled() {
        return Utils.useQsMediaPlayer(this.context);
    }
}
