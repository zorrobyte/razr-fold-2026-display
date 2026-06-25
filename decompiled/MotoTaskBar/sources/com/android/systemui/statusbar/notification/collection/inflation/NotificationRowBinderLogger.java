package com.android.systemui.statusbar.notification.collection.inflation;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: NotificationRowBinderLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationRowBinderLogger {
    private final LogBuffer buffer;

    public NotificationRowBinderLogger(LogBuffer logBuffer) {
        logBuffer.getClass();
        this.buffer = logBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logCreatingRow$lambda$0(NotificationEntry notificationEntry, NotifInflater.Params params, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(params.getReason());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logCreatingRow$lambda$1(LogMessage logMessage) {
        logMessage.getClass();
        return "creating row for " + logMessage.getStr1() + ": " + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logInflatedRow$lambda$4(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logInflatedRow$lambda$5(LogMessage logMessage) {
        logMessage.getClass();
        return "inflated row for " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logInflatingRow$lambda$2(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logInflatingRow$lambda$3(LogMessage logMessage) {
        logMessage.getClass();
        return "inflating row for " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logNotReleasingViewsRowDoesntExist$lambda$10(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logNotReleasingViewsRowDoesntExist$lambda$11(LogMessage logMessage) {
        logMessage.getClass();
        return "not releasing views for " + logMessage.getStr1() + ": row doesn't exist";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logRebindComplete$lambda$14(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(notificationEntry.getKey());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logRebindComplete$lambda$15(LogMessage logMessage) {
        logMessage.getClass();
        return "rebind complete for " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logReleasingViews$lambda$8(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logReleasingViews$lambda$9(LogMessage logMessage) {
        logMessage.getClass();
        return "releasing views for " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logRequestingRebind$lambda$12(NotificationEntry notificationEntry, NotifInflater.Params params, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(notificationEntry.getKey());
        logMessage.setStr2(params.getReason());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logRequestingRebind$lambda$13(LogMessage logMessage) {
        logMessage.getClass();
        return "requesting rebind for " + logMessage.getStr1() + ": " + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logUpdatingRow$lambda$6(NotificationEntry notificationEntry, NotifInflater.Params params, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(params.getReason());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logUpdatingRow$lambda$7(LogMessage logMessage) {
        logMessage.getClass();
        return "updating row for " + logMessage.getStr1() + ": " + logMessage.getStr2();
    }

    public final void logCreatingRow(final NotificationEntry notificationEntry, final NotifInflater.Params params) {
        notificationEntry.getClass();
        params.getClass();
        LogBuffer.log$default(this.buffer, "NotificationRowBinder", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderLogger$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowBinderLogger.logCreatingRow$lambda$0(notificationEntry, params, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderLogger$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowBinderLogger.logCreatingRow$lambda$1((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logInflatedRow(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotificationRowBinder", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderLogger$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowBinderLogger.logInflatedRow$lambda$4(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderLogger$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowBinderLogger.logInflatedRow$lambda$5((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logInflatingRow(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotificationRowBinder", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderLogger$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowBinderLogger.logInflatingRow$lambda$2(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderLogger$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowBinderLogger.logInflatingRow$lambda$3((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logNotReleasingViewsRowDoesntExist(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotificationRowBinder", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowBinderLogger.logNotReleasingViewsRowDoesntExist$lambda$10(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderLogger$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowBinderLogger.logNotReleasingViewsRowDoesntExist$lambda$11((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logRebindComplete(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotificationRowBinder", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderLogger$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowBinderLogger.logRebindComplete$lambda$14(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderLogger$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowBinderLogger.logRebindComplete$lambda$15((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logReleasingViews(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotificationRowBinder", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderLogger$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowBinderLogger.logReleasingViews$lambda$8(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderLogger$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowBinderLogger.logReleasingViews$lambda$9((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logRequestingRebind(final NotificationEntry notificationEntry, final NotifInflater.Params params) {
        notificationEntry.getClass();
        params.getClass();
        LogBuffer.log$default(this.buffer, "NotificationRowBinder", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderLogger$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowBinderLogger.logRequestingRebind$lambda$12(notificationEntry, params, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderLogger$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowBinderLogger.logRequestingRebind$lambda$13((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logUpdatingRow(final NotificationEntry notificationEntry, final NotifInflater.Params params) {
        notificationEntry.getClass();
        params.getClass();
        LogBuffer.log$default(this.buffer, "NotificationRowBinder", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderLogger$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowBinderLogger.logUpdatingRow$lambda$6(notificationEntry, params, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderLogger$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowBinderLogger.logUpdatingRow$lambda$7((LogMessage) obj);
            }
        }, null, 16, null);
    }
}
