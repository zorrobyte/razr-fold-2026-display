package com.android.systemui.statusbar.notification.row;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: RowInflaterTaskLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RowInflaterTaskLogger {
    private final LogBuffer buffer;

    public RowInflaterTaskLogger(LogBuffer logBuffer) {
        logBuffer.getClass();
        this.buffer = logBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logCreatedRow$lambda$2(NotificationEntry notificationEntry, long j, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setLong1(j);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logCreatedRow$lambda$3(LogMessage logMessage) {
        logMessage.getClass();
        return "created row in " + logMessage.getLong1() + " ms for " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logInflateFinish$lambda$4(NotificationEntry notificationEntry, long j, boolean z, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setLong1(j);
        logMessage.setBool1(z);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logInflateFinish$lambda$5(LogMessage logMessage) {
        logMessage.getClass();
        return "finished " + (logMessage.getBool1() ? "cancelled " : "") + "row inflation in " + logMessage.getLong1() + " ms for " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logInflateStart$lambda$0(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logInflateStart$lambda$1(LogMessage logMessage) {
        logMessage.getClass();
        return "started row inflation for " + logMessage.getStr1();
    }

    public final void logCreatedRow(final NotificationEntry notificationEntry, final long j) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "RowInflaterTask", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.row.RowInflaterTaskLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RowInflaterTaskLogger.logCreatedRow$lambda$2(notificationEntry, j, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.row.RowInflaterTaskLogger$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RowInflaterTaskLogger.logCreatedRow$lambda$3((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logInflateFinish(final NotificationEntry notificationEntry, final long j, final boolean z) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "RowInflaterTask", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.row.RowInflaterTaskLogger$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RowInflaterTaskLogger.logInflateFinish$lambda$4(notificationEntry, j, z, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.row.RowInflaterTaskLogger$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RowInflaterTaskLogger.logInflateFinish$lambda$5((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logInflateStart(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "RowInflaterTask", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.row.RowInflaterTaskLogger$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RowInflaterTaskLogger.logInflateStart$lambda$0(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.row.RowInflaterTaskLogger$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RowInflaterTaskLogger.logInflateStart$lambda$1((LogMessage) obj);
            }
        }, null, 16, null);
    }
}
