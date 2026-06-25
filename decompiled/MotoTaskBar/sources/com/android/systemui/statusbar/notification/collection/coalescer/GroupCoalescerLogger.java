package com.android.systemui.statusbar.notification.collection.coalescer;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: GroupCoalescerLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class GroupCoalescerLogger {
    private final LogBuffer buffer;

    public GroupCoalescerLogger(LogBuffer logBuffer) {
        logBuffer.getClass();
        this.buffer = logBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logEarlyEmit$lambda$4(String str, String str2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(str);
        logMessage.setStr2(str2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logEarlyEmit$lambda$5(LogMessage logMessage) {
        logMessage.getClass();
        return "Modification of notif " + logMessage.getStr1() + " triggered early emit of batched group " + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logEmitBatch$lambda$2(String str, int i, long j, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(str);
        logMessage.setInt1(i);
        logMessage.setLong1(j);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logEmitBatch$lambda$3(LogMessage logMessage) {
        logMessage.getClass();
        return "Emitting batch for group " + logMessage.getStr1() + " size=" + logMessage.getInt1() + " age=" + logMessage.getLong1() + "ms";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logEventCoalesced$lambda$0(String str, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logEventCoalesced$lambda$1(LogMessage logMessage) {
        logMessage.getClass();
        return "COALESCED: " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logMaxBatchTimeout$lambda$6(String str, String str2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(str);
        logMessage.setStr2(str2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logMaxBatchTimeout$lambda$7(LogMessage logMessage) {
        logMessage.getClass();
        return "Modification of notif " + logMessage.getStr1() + " triggered TIMEOUT emit of batched group " + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logMissingRanking$lambda$8(String str, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logMissingRanking$lambda$9(LogMessage logMessage) {
        logMessage.getClass();
        return "RankingMap is missing an entry for coalesced notification " + logMessage.getStr1();
    }

    public final void logEarlyEmit(final String str, final String str2) {
        str.getClass();
        str2.getClass();
        LogBuffer.log$default(this.buffer, "GroupCoalescer", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescerLogger$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return GroupCoalescerLogger.logEarlyEmit$lambda$4(str, str2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescerLogger$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return GroupCoalescerLogger.logEarlyEmit$lambda$5((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logEmitBatch(final String str, final int i, final long j) {
        str.getClass();
        LogBuffer.log$default(this.buffer, "GroupCoalescer", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescerLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return GroupCoalescerLogger.logEmitBatch$lambda$2(str, i, j, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescerLogger$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return GroupCoalescerLogger.logEmitBatch$lambda$3((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logEventCoalesced(final String str) {
        str.getClass();
        LogBuffer.log$default(this.buffer, "GroupCoalescer", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescerLogger$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return GroupCoalescerLogger.logEventCoalesced$lambda$0(str, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescerLogger$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return GroupCoalescerLogger.logEventCoalesced$lambda$1((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logMaxBatchTimeout(final String str, final String str2) {
        str.getClass();
        str2.getClass();
        LogBuffer.log$default(this.buffer, "GroupCoalescer", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescerLogger$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return GroupCoalescerLogger.logMaxBatchTimeout$lambda$6(str, str2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescerLogger$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return GroupCoalescerLogger.logMaxBatchTimeout$lambda$7((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logMissingRanking(final String str) {
        str.getClass();
        LogBuffer.log$default(this.buffer, "GroupCoalescer", LogLevel.WARNING, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescerLogger$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return GroupCoalescerLogger.logMissingRanking$lambda$8(str, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescerLogger$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return GroupCoalescerLogger.logMissingRanking$lambda$9((LogMessage) obj);
            }
        }, null, 16, null);
    }
}
