package com.android.systemui.statusbar.notification.row;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: NotifBindPipelineLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotifBindPipelineLogger {
    private final LogBuffer buffer;

    public NotifBindPipelineLogger(LogBuffer logBuffer) {
        logBuffer.getClass();
        this.buffer = logBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logFinishedPipeline$lambda$10(NotificationEntry notificationEntry, int i, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setInt1(i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logFinishedPipeline$lambda$11(LogMessage logMessage) {
        logMessage.getClass();
        return "Finished pipeline for notif " + logMessage.getStr1() + " with " + logMessage.getInt1() + " callbacks";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logManagedRow$lambda$2(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logManagedRow$lambda$3(LogMessage logMessage) {
        logMessage.getClass();
        return "Row set for notif: " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logRequestPipelineRowNotSet$lambda$6(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logRequestPipelineRowNotSet$lambda$7(LogMessage logMessage) {
        logMessage.getClass();
        return "Row is not set so pipeline will not run. notif = " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logRequestPipelineRun$lambda$4(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logRequestPipelineRun$lambda$5(LogMessage logMessage) {
        logMessage.getClass();
        return "Request pipeline run for notif: " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logStageSet$lambda$0(String str, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logStageSet$lambda$1(LogMessage logMessage) {
        logMessage.getClass();
        return "Stage set: " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logStartPipeline$lambda$8(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logStartPipeline$lambda$9(LogMessage logMessage) {
        logMessage.getClass();
        return "Start pipeline for notif: " + logMessage.getStr1();
    }

    public final void logFinishedPipeline(final NotificationEntry notificationEntry, final int i) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotifBindPipeline", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifBindPipelineLogger.logFinishedPipeline$lambda$10(notificationEntry, i, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifBindPipelineLogger.logFinishedPipeline$lambda$11((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logManagedRow(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotifBindPipeline", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifBindPipelineLogger.logManagedRow$lambda$2(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifBindPipelineLogger.logManagedRow$lambda$3((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logRequestPipelineRowNotSet(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotifBindPipeline", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifBindPipelineLogger.logRequestPipelineRowNotSet$lambda$6(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifBindPipelineLogger.logRequestPipelineRowNotSet$lambda$7((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logRequestPipelineRun(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotifBindPipeline", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifBindPipelineLogger.logRequestPipelineRun$lambda$4(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifBindPipelineLogger.logRequestPipelineRun$lambda$5((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logStageSet(final String str) {
        str.getClass();
        LogBuffer.log$default(this.buffer, "NotifBindPipeline", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifBindPipelineLogger.logStageSet$lambda$0(str, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifBindPipelineLogger.logStageSet$lambda$1((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logStartPipeline(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotifBindPipeline", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifBindPipelineLogger.logStartPipeline$lambda$8(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifBindPipelineLogger.logStartPipeline$lambda$9((LogMessage) obj);
            }
        }, null, 16, null);
    }
}
