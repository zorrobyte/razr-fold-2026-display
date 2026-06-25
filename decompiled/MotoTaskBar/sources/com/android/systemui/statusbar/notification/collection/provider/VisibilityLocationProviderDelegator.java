package com.android.systemui.statusbar.notification.collection.provider;

import com.android.systemui.statusbar.notification.VisibilityLocationProvider;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* JADX INFO: compiled from: VisibilityLocationProviderDelegator.kt */
/* JADX INFO: loaded from: classes.dex */
public final class VisibilityLocationProviderDelegator implements VisibilityLocationProvider {
    private VisibilityLocationProvider delegate;

    @Override // com.android.systemui.statusbar.notification.VisibilityLocationProvider
    public boolean isInVisibleLocation(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        VisibilityLocationProvider visibilityLocationProvider = this.delegate;
        if (visibilityLocationProvider != null) {
            return visibilityLocationProvider.isInVisibleLocation(notificationEntry);
        }
        throw new IllegalArgumentException("delegate not initialized");
    }

    public final void setDelegate(VisibilityLocationProvider visibilityLocationProvider) {
        visibilityLocationProvider.getClass();
        this.delegate = visibilityLocationProvider;
    }
}
