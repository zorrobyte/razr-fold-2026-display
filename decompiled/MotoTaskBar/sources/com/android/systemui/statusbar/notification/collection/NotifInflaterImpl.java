package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinder;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl;
import com.android.systemui.statusbar.notification.row.NotifInflationErrorManager;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinder;

/* JADX INFO: loaded from: classes.dex */
public class NotifInflaterImpl implements NotifInflater {
    private final NotifInflaterLogger mLogger;
    private final NotifInflationErrorManager mNotifErrorManager;
    private NotificationRowBinderImpl mNotificationRowBinder;

    public NotifInflaterImpl(NotifInflationErrorManager notifInflationErrorManager, NotifInflaterLogger notifInflaterLogger) {
        this.mNotifErrorManager = notifInflationErrorManager;
        this.mLogger = notifInflaterLogger;
    }

    private void inflateViewsImpl(NotificationEntry notificationEntry, NotifInflater.Params params, NotifInflater.InflationCallback inflationCallback) {
        try {
            requireBinder().inflateViews(notificationEntry, params, wrapInflationCallback(inflationCallback));
        } catch (InflationException e) {
            this.mLogger.logInflationException(notificationEntry, e);
            this.mNotifErrorManager.setInflationError(notificationEntry, e);
        }
    }

    private NotificationRowBinderImpl requireBinder() {
        NotificationRowBinderImpl notificationRowBinderImpl = this.mNotificationRowBinder;
        if (notificationRowBinderImpl != null) {
            return notificationRowBinderImpl;
        }
        throw new RuntimeException("NotificationRowBinder must be attached before using NotifInflaterImpl.");
    }

    private NotificationRowContentBinder.InflationCallback wrapInflationCallback(final NotifInflater.InflationCallback inflationCallback) {
        return new NotificationRowContentBinder.InflationCallback() { // from class: com.android.systemui.statusbar.notification.collection.NotifInflaterImpl.1
            @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
            public void handleInflationException(NotificationEntry notificationEntry, Exception exc) {
                if (notificationEntry.isDeskHeadsUp()) {
                    return;
                }
                NotifInflaterImpl.this.mNotifErrorManager.setInflationError(notificationEntry, exc);
            }

            @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
            public void onAsyncInflationFinished(NotificationEntry notificationEntry) {
                if (!notificationEntry.isDeskHeadsUp()) {
                    NotifInflaterImpl.this.mNotifErrorManager.clearInflationError(notificationEntry);
                }
                NotifInflater.InflationCallback inflationCallback2 = inflationCallback;
                if (inflationCallback2 != null) {
                    inflationCallback2.onInflationFinished(notificationEntry, notificationEntry.getRowController());
                }
            }
        };
    }

    @Override // com.android.systemui.statusbar.notification.collection.inflation.NotifInflater
    public boolean abortInflation(NotificationEntry notificationEntry) {
        boolean zAbortTask = notificationEntry.abortTask();
        if (zAbortTask) {
            this.mLogger.logAbortInflationAbortedTask(notificationEntry);
        }
        return zAbortTask;
    }

    public NotificationRowBinder getNotificationRowBinder() {
        return this.mNotificationRowBinder;
    }

    @Override // com.android.systemui.statusbar.notification.collection.inflation.NotifInflater
    public void inflateViews(NotificationEntry notificationEntry, NotifInflater.Params params, NotifInflater.InflationCallback inflationCallback) {
        this.mLogger.logInflatingViews(notificationEntry, params);
        inflateViewsImpl(notificationEntry, params, inflationCallback);
        this.mLogger.logInflatedViews(notificationEntry);
    }

    @Override // com.android.systemui.statusbar.notification.collection.inflation.NotifInflater
    public void rebindViews(NotificationEntry notificationEntry, NotifInflater.Params params, NotifInflater.InflationCallback inflationCallback) {
        this.mLogger.logRebindingViews(notificationEntry, params);
        inflateViewsImpl(notificationEntry, params, inflationCallback);
        this.mLogger.logReboundViews(notificationEntry);
    }

    @Override // com.android.systemui.statusbar.notification.collection.inflation.NotifInflater
    public void releaseViews(NotificationEntry notificationEntry) {
        this.mLogger.logReleasingViews(notificationEntry);
        requireBinder().releaseViews(notificationEntry);
    }

    public void setRowBinder(NotificationRowBinderImpl notificationRowBinderImpl) {
        this.mNotificationRowBinder = notificationRowBinderImpl;
    }
}
