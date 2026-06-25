package com.android.systemui.statusbar.notification;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public interface NotificationFadeAware {

    public interface FadeOptimizedNotification extends NotificationFadeAware {
        boolean isNotificationFaded();
    }

    static void setLayerTypeForFaded(View view, boolean z) {
        if (view != null) {
            view.setLayerType(z ? 2 : 0, null);
        }
    }

    void setNotificationFaded(boolean z);
}
