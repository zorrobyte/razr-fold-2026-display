package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLogger;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: NotificationStatsLoggerCoordinator.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationStatsLoggerCoordinator$collectionListener$1 implements NotifCollectionListener {
    final /* synthetic */ NotificationStatsLoggerCoordinator this$0;

    NotificationStatsLoggerCoordinator$collectionListener$1(NotificationStatsLoggerCoordinator notificationStatsLoggerCoordinator) {
        this.this$0 = notificationStatsLoggerCoordinator;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onEntryRemoved$lambda$1(NotificationEntry notificationEntry, NotificationStatsLogger notificationStatsLogger) {
        notificationStatsLogger.getClass();
        String key = notificationEntry.getKey();
        key.getClass();
        notificationStatsLogger.onNotificationRemoved(key);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onEntryUpdated$lambda$0(NotificationEntry notificationEntry, NotificationStatsLogger notificationStatsLogger) {
        notificationStatsLogger.getClass();
        String key = notificationEntry.getKey();
        key.getClass();
        notificationStatsLogger.onNotificationUpdated(key);
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public void onEntryRemoved(final NotificationEntry notificationEntry, int i) {
        notificationEntry.getClass();
        super.onEntryRemoved(notificationEntry, i);
        this.this$0.loggerOptional.ifPresent(new NotificationStatsLoggerCoordinator$sam$java_util_function_Consumer$0(new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotificationStatsLoggerCoordinator$collectionListener$1$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationStatsLoggerCoordinator$collectionListener$1.onEntryRemoved$lambda$1(notificationEntry, (NotificationStatsLogger) obj);
            }
        }));
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public void onEntryUpdated(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        super.onEntryUpdated(notificationEntry);
        this.this$0.loggerOptional.ifPresent(new NotificationStatsLoggerCoordinator$sam$java_util_function_Consumer$0(new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotificationStatsLoggerCoordinator$collectionListener$1$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationStatsLoggerCoordinator$collectionListener$1.onEntryUpdated$lambda$0(notificationEntry, (NotificationStatsLogger) obj);
            }
        }));
    }
}
