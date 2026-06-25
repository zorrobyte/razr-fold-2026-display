package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import kotlin.ExceptionsKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: NotifInflaterLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotifInflaterLogger {
    private final LogBuffer buffer;

    public NotifInflaterLogger(LogBuffer logBuffer) {
        logBuffer.getClass();
        this.buffer = logBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logAbortInflationAbortedTask$lambda$10(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logAbortInflationAbortedTask$lambda$11(LogMessage logMessage) {
        logMessage.getClass();
        return "aborted task to abort inflation for " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logInflatedViews$lambda$2(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logInflatedViews$lambda$3(LogMessage logMessage) {
        logMessage.getClass();
        return "inflated views for " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logInflatingViews$lambda$0(NotificationEntry notificationEntry, NotifInflater.Params params, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(params.getReason());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logInflatingViews$lambda$1(LogMessage logMessage) {
        logMessage.getClass();
        return "inflating views for " + logMessage.getStr1() + ": " + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logInflationException$lambda$8(NotificationEntry notificationEntry, InflationException inflationException, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(ExceptionsKt.stackTraceToString(inflationException));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logInflationException$lambda$9(LogMessage logMessage) {
        logMessage.getClass();
        return "exception inflating views for " + logMessage.getStr1() + ": " + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logRebindingViews$lambda$4(NotificationEntry notificationEntry, NotifInflater.Params params, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(params.getReason());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logRebindingViews$lambda$5(LogMessage logMessage) {
        logMessage.getClass();
        return "rebinding views for " + logMessage.getStr1() + ": " + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logReboundViews$lambda$6(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logReboundViews$lambda$7(LogMessage logMessage) {
        logMessage.getClass();
        return "rebound views for " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logReleasingViews$lambda$12(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logReleasingViews$lambda$13(LogMessage logMessage) {
        logMessage.getClass();
        return "aborting inflation for " + logMessage.getStr1();
    }

    public final void logAbortInflationAbortedTask(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotifInflater", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.NotifInflaterLogger$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifInflaterLogger.logAbortInflationAbortedTask$lambda$10(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.NotifInflaterLogger$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifInflaterLogger.logAbortInflationAbortedTask$lambda$11((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logInflatedViews(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotifInflater", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.NotifInflaterLogger$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifInflaterLogger.logInflatedViews$lambda$2(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.NotifInflaterLogger$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifInflaterLogger.logInflatedViews$lambda$3((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logInflatingViews(final NotificationEntry notificationEntry, final NotifInflater.Params params) {
        notificationEntry.getClass();
        params.getClass();
        LogBuffer.log$default(this.buffer, "NotifInflater", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.NotifInflaterLogger$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifInflaterLogger.logInflatingViews$lambda$0(notificationEntry, params, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.NotifInflaterLogger$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifInflaterLogger.logInflatingViews$lambda$1((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logInflationException(final NotificationEntry notificationEntry, final InflationException inflationException) {
        notificationEntry.getClass();
        inflationException.getClass();
        LogBuffer.log$default(this.buffer, "NotifInflater", LogLevel.WARNING, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.NotifInflaterLogger$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifInflaterLogger.logInflationException$lambda$8(notificationEntry, inflationException, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.NotifInflaterLogger$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifInflaterLogger.logInflationException$lambda$9((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logRebindingViews(final NotificationEntry notificationEntry, final NotifInflater.Params params) {
        notificationEntry.getClass();
        params.getClass();
        LogBuffer.log$default(this.buffer, "NotifInflater", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.NotifInflaterLogger$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifInflaterLogger.logRebindingViews$lambda$4(notificationEntry, params, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.NotifInflaterLogger$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifInflaterLogger.logRebindingViews$lambda$5((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logReboundViews(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotifInflater", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.NotifInflaterLogger$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifInflaterLogger.logReboundViews$lambda$6(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.NotifInflaterLogger$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifInflaterLogger.logReboundViews$lambda$7((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logReleasingViews(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotifInflater", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.NotifInflaterLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifInflaterLogger.logReleasingViews$lambda$12(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.NotifInflaterLogger$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifInflaterLogger.logReleasingViews$lambda$13((LogMessage) obj);
            }
        }, null, 16, null);
    }
}
