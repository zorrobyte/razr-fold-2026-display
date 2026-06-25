package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.Flags;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.shared.NotificationsLiveDataStoreRefactor;
import java.util.Optional;

/* JADX INFO: compiled from: NotificationStatsLoggerCoordinator.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationStatsLoggerCoordinator implements Coordinator {
    private final NotificationStatsLoggerCoordinator$collectionListener$1 collectionListener;
    private final Optional loggerOptional;

    public NotificationStatsLoggerCoordinator(Optional optional) {
        optional.getClass();
        this.loggerOptional = optional;
        this.collectionListener = new NotificationStatsLoggerCoordinator$collectionListener$1(this);
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.getClass();
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        NotificationsLiveDataStoreRefactor notificationsLiveDataStoreRefactor = NotificationsLiveDataStoreRefactor.INSTANCE;
        boolean zNotificationsLiveDataStoreRefactor = Flags.notificationsLiveDataStoreRefactor();
        if (!zNotificationsLiveDataStoreRefactor) {
            refactorFlagUtils.assertOnEngBuild("New code path expects " + ((Object) "NotificationsLiveDataStoreRefactor") + " to be enabled.");
        }
        if (zNotificationsLiveDataStoreRefactor) {
            notifPipeline.addCollectionListener(this.collectionListener);
        }
    }
}
