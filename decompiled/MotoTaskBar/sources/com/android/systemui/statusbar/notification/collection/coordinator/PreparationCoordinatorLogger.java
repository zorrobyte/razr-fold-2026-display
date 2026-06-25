package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: PreparationCoordinatorLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PreparationCoordinatorLogger {
    private final LogBuffer buffer;

    public PreparationCoordinatorLogger(LogBuffer logBuffer) {
        logBuffer.getClass();
        this.buffer = logBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logDelayingGroupRelease$lambda$10(GroupEntry groupEntry, NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(groupEntry));
        logMessage.setStr2(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logDelayingGroupRelease$lambda$11(LogMessage logMessage) {
        logMessage.getClass();
        return "Delaying release of group " + logMessage.getStr1() + " because child " + logMessage.getStr2() + " is still inflating";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logDoneWaitingForGroupInflation$lambda$6(GroupEntry groupEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(groupEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logDoneWaitingForGroupInflation$lambda$7(LogMessage logMessage) {
        logMessage.getClass();
        return "Finished inflating all members of group " + logMessage.getStr1() + ", releasing group";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logFreeNotifViews$lambda$4(NotificationEntry notificationEntry, String str, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logFreeNotifViews$lambda$5(LogMessage logMessage) {
        logMessage.getClass();
        return "Freeing content views for notif " + logMessage.getStr1() + " reason=" + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logGroupInflationTookTooLong$lambda$8(GroupEntry groupEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(groupEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logGroupInflationTookTooLong$lambda$9(LogMessage logMessage) {
        logMessage.getClass();
        return "Group inflation took too long for " + logMessage.getStr1() + ", releasing children early";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logInflationAborted$lambda$2(NotificationEntry notificationEntry, String str, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logInflationAborted$lambda$3(LogMessage logMessage) {
        logMessage.getClass();
        return "Infation aborted for notif " + logMessage.getStr1() + " reason=" + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logNotifInflated$lambda$0(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logNotifInflated$lambda$1(LogMessage logMessage) {
        logMessage.getClass();
        return "Inflation completed for notif " + logMessage.getStr1();
    }

    public final void logDelayingGroupRelease(final GroupEntry groupEntry, final NotificationEntry notificationEntry) {
        groupEntry.getClass();
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "PreparationCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return PreparationCoordinatorLogger.logDelayingGroupRelease$lambda$10(groupEntry, notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return PreparationCoordinatorLogger.logDelayingGroupRelease$lambda$11((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logDoneWaitingForGroupInflation(final GroupEntry groupEntry) {
        groupEntry.getClass();
        LogBuffer.log$default(this.buffer, "PreparationCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return PreparationCoordinatorLogger.logDoneWaitingForGroupInflation$lambda$6(groupEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return PreparationCoordinatorLogger.logDoneWaitingForGroupInflation$lambda$7((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logFreeNotifViews(final NotificationEntry notificationEntry, final String str) {
        notificationEntry.getClass();
        str.getClass();
        LogBuffer.log$default(this.buffer, "PreparationCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return PreparationCoordinatorLogger.logFreeNotifViews$lambda$4(notificationEntry, str, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return PreparationCoordinatorLogger.logFreeNotifViews$lambda$5((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logGroupInflationTookTooLong(final GroupEntry groupEntry) {
        groupEntry.getClass();
        LogBuffer.log$default(this.buffer, "PreparationCoordinator", LogLevel.WARNING, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return PreparationCoordinatorLogger.logGroupInflationTookTooLong$lambda$8(groupEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return PreparationCoordinatorLogger.logGroupInflationTookTooLong$lambda$9((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logInflationAborted(final NotificationEntry notificationEntry, final String str) {
        notificationEntry.getClass();
        str.getClass();
        LogBuffer.log$default(this.buffer, "PreparationCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return PreparationCoordinatorLogger.logInflationAborted$lambda$2(notificationEntry, str, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return PreparationCoordinatorLogger.logInflationAborted$lambda$3((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logNotifInflated(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "PreparationCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return PreparationCoordinatorLogger.logNotifInflated$lambda$0(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return PreparationCoordinatorLogger.logNotifInflated$lambda$1((LogMessage) obj);
            }
        }, null, 16, null);
    }
}
