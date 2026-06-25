package com.android.systemui.statusbar.notification.row;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: RowContentBindStageLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RowContentBindStageLogger {
    private final LogBuffer buffer;

    public RowContentBindStageLogger(LogBuffer logBuffer) {
        logBuffer.getClass();
        this.buffer = logBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logAbortStageCancelledBind$lambda$2(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logAbortStageCancelledBind$lambda$3(LogMessage logMessage) {
        logMessage.getClass();
        return "cancelled bind to abort stage for " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logExecutingStage$lambda$0(NotificationEntry notificationEntry, RowContentBindParams rowContentBindParams, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(rowContentBindParams.toString());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logExecutingStage$lambda$1(LogMessage logMessage) {
        logMessage.getClass();
        return "executing bind stage for " + logMessage.getStr1() + " with params " + logMessage.getStr2();
    }

    public final void logAbortStageCancelledBind(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "RowContentBindStage", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.row.RowContentBindStageLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RowContentBindStageLogger.logAbortStageCancelledBind$lambda$2(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.row.RowContentBindStageLogger$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RowContentBindStageLogger.logAbortStageCancelledBind$lambda$3((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logExecutingStage(final NotificationEntry notificationEntry, final RowContentBindParams rowContentBindParams) {
        notificationEntry.getClass();
        rowContentBindParams.getClass();
        LogBuffer.log$default(this.buffer, "RowContentBindStage", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.row.RowContentBindStageLogger$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RowContentBindStageLogger.logExecutingStage$lambda$0(notificationEntry, rowContentBindParams, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.row.RowContentBindStageLogger$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RowContentBindStageLogger.logExecutingStage$lambda$1((LogMessage) obj);
            }
        }, null, 16, null);
    }
}
