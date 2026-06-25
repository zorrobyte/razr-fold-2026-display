package com.android.systemui.statusbar.notification.row;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.ArrayList;
import kotlin.ExceptionsKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: NotificationContentInflaterLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationContentInflaterLogger {
    public static final Companion Companion = new Companion(null);
    private final LogBuffer buffer;

    /* JADX INFO: compiled from: NotificationContentInflaterLogger.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final String flagToString(int i) {
            if (i == 0) {
                return "NONE";
            }
            if (i == 127) {
                return "ALL";
            }
            ArrayList arrayList = new ArrayList();
            if ((i & 1) != 0) {
                arrayList.add("CONTRACTED");
            }
            if ((i & 2) != 0) {
                arrayList.add("EXPANDED");
            }
            if ((i & 4) != 0) {
                arrayList.add("HEADS_UP");
            }
            if ((i & 8) != 0) {
                arrayList.add("PUBLIC");
            }
            if ((i & 16) != 0) {
                arrayList.add("SINGLE_LINE");
            }
            if ((i & 32) != 0) {
                arrayList.add("GROUP_SUMMARY_HEADER");
            }
            if ((i & 64) != 0) {
                arrayList.add("LOW_PRIORITY_GROUP_SUMMARY_HEADER");
            }
            return CollectionsKt.joinToString$default(arrayList, "|", null, null, 0, null, null, 62, null);
        }
    }

    public NotificationContentInflaterLogger(LogBuffer logBuffer) {
        logBuffer.getClass();
        this.buffer = logBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logAsyncTaskException$lambda$10(NotificationEntry notificationEntry, String str, Throwable th, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(str);
        logMessage.setStr3(ExceptionsKt.stackTraceToString(th));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logAsyncTaskException$lambda$11(LogMessage logMessage) {
        logMessage.getClass();
        return "async task for " + logMessage.getStr1() + " got exception " + logMessage.getStr2() + ": " + logMessage.getStr3();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logAsyncTaskProgress$lambda$8(NotificationEntry notificationEntry, String str, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logAsyncTaskProgress$lambda$9(LogMessage logMessage) {
        logMessage.getClass();
        return "async task for " + logMessage.getStr1() + ": " + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logBinding$lambda$2(NotificationEntry notificationEntry, int i, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setInt1(i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logBinding$lambda$3(LogMessage logMessage) {
        logMessage.getClass();
        return "binding views " + Companion.flagToString(logMessage.getInt1()) + " for " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logCancelBindAbortedTask$lambda$4(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logCancelBindAbortedTask$lambda$5(LogMessage logMessage) {
        logMessage.getClass();
        return "aborted task to cancel binding " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logInflateSingleLine$lambda$12(NotificationEntry notificationEntry, int i, boolean z, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setInt1(i);
        logMessage.setBool1(z);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logInflateSingleLine$lambda$13(LogMessage logMessage) {
        logMessage.getClass();
        return "inflateSingleLineView, inflationFlags: " + Companion.flagToString(logMessage.getInt1()) + " for " + logMessage.getStr1() + ", isConversation: " + logMessage.getBool1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logNotBindingRowWasRemoved$lambda$0(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logNotBindingRowWasRemoved$lambda$1(LogMessage logMessage) {
        logMessage.getClass();
        return "not inflating " + logMessage.getStr1() + ": row was removed";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logUnbinding$lambda$6(NotificationEntry notificationEntry, int i, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setInt1(i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logUnbinding$lambda$7(LogMessage logMessage) {
        logMessage.getClass();
        return "unbinding views " + Companion.flagToString(logMessage.getInt1()) + " for " + logMessage.getStr1();
    }

    public final void logAsyncTaskException(final NotificationEntry notificationEntry, final String str, final Throwable th) {
        notificationEntry.getClass();
        str.getClass();
        th.getClass();
        LogBuffer.log$default(this.buffer, "NotificationContentInflater", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflaterLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationContentInflaterLogger.logAsyncTaskException$lambda$10(notificationEntry, str, th, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflaterLogger$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationContentInflaterLogger.logAsyncTaskException$lambda$11((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logAsyncTaskProgress(final NotificationEntry notificationEntry, final String str) {
        notificationEntry.getClass();
        str.getClass();
        LogBuffer.log$default(this.buffer, "NotificationContentInflater", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflaterLogger$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationContentInflaterLogger.logAsyncTaskProgress$lambda$8(notificationEntry, str, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflaterLogger$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationContentInflaterLogger.logAsyncTaskProgress$lambda$9((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logBinding(final NotificationEntry notificationEntry, final int i) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotificationContentInflater", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflaterLogger$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationContentInflaterLogger.logBinding$lambda$2(notificationEntry, i, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflaterLogger$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationContentInflaterLogger.logBinding$lambda$3((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logCancelBindAbortedTask(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotificationContentInflater", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflaterLogger$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationContentInflaterLogger.logCancelBindAbortedTask$lambda$4(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflaterLogger$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationContentInflaterLogger.logCancelBindAbortedTask$lambda$5((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logInflateSingleLine(final NotificationEntry notificationEntry, final int i, final boolean z) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotificationContentInflater", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflaterLogger$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationContentInflaterLogger.logInflateSingleLine$lambda$12(notificationEntry, i, z, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflaterLogger$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationContentInflaterLogger.logInflateSingleLine$lambda$13((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logNotBindingRowWasRemoved(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotificationContentInflater", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflaterLogger$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationContentInflaterLogger.logNotBindingRowWasRemoved$lambda$0(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflaterLogger$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationContentInflaterLogger.logNotBindingRowWasRemoved$lambda$1((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logUnbinding(final NotificationEntry notificationEntry, final int i) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotificationContentInflater", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflaterLogger$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationContentInflaterLogger.logUnbinding$lambda$6(notificationEntry, i, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflaterLogger$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationContentInflaterLogger.logUnbinding$lambda$7((LogMessage) obj);
            }
        }, null, 16, null);
    }
}
