package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: VisualInterruptionDecisionLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class VisualInterruptionDecisionLogger {
    private final LogBuffer buffer;

    public VisualInterruptionDecisionLogger(LogBuffer logBuffer) {
        logBuffer.getClass();
        this.buffer = logBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logDecision$lambda$4(String str, VisualInterruptionDecisionProvider.Decision decision, NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(str);
        logMessage.setBool1(decision.getShouldInterrupt());
        logMessage.setStr2(decision.getLogReason());
        logMessage.setStr3(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logDecision$lambda$5(LogMessage logMessage) {
        logMessage.getClass();
        String str = logMessage.getBool1() ? "allowed" : "suppressed";
        return logMessage.getStr1() + " " + str + ": " + logMessage.getStr2() + " (key=" + logMessage.getStr3() + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logFullScreenIntentDecision$lambda$6(VisualInterruptionDecisionProvider.FullScreenIntentDecision fullScreenIntentDecision, NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setBool1(fullScreenIntentDecision.getShouldInterrupt());
        logMessage.setBool2(fullScreenIntentDecision.getWouldInterruptWithoutDnd());
        logMessage.setStr1(fullScreenIntentDecision.getLogReason());
        logMessage.setStr2(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logFullScreenIntentDecision$lambda$7(LogMessage logMessage) {
        logMessage.getClass();
        return "FSI " + (logMessage.getBool1() ? "allowed" : logMessage.getBool2() ? "suppressed only by DND" : "suppressed") + ": " + logMessage.getStr1() + " (key=" + logMessage.getStr2() + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logHeadsUpFeatureChanged$lambda$0(boolean z, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setBool1(z);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logHeadsUpFeatureChanged$lambda$1(LogMessage logMessage) {
        logMessage.getClass();
        return "HUN feature is now " + (logMessage.getBool1() ? "enabled" : "disabled");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logWillDismissAll$lambda$2(LogMessage logMessage) {
        logMessage.getClass();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logWillDismissAll$lambda$3(LogMessage logMessage) {
        logMessage.getClass();
        return "dismissing all HUNs since feature was disabled";
    }

    public final void logDecision(final String str, final NotificationEntry notificationEntry, final VisualInterruptionDecisionProvider.Decision decision) {
        str.getClass();
        notificationEntry.getClass();
        decision.getClass();
        LogBuffer.log$default(this.buffer, "VisualInterruptionDecisionProvider", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionLogger$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return VisualInterruptionDecisionLogger.logDecision$lambda$4(str, decision, notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionLogger$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return VisualInterruptionDecisionLogger.logDecision$lambda$5((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logFullScreenIntentDecision(final NotificationEntry notificationEntry, final VisualInterruptionDecisionProvider.FullScreenIntentDecision fullScreenIntentDecision, boolean z) {
        notificationEntry.getClass();
        fullScreenIntentDecision.getClass();
        LogBuffer.log$default(this.buffer, "VisualInterruptionDecisionProvider", z ? LogLevel.WARNING : LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return VisualInterruptionDecisionLogger.logFullScreenIntentDecision$lambda$6(fullScreenIntentDecision, notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionLogger$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return VisualInterruptionDecisionLogger.logFullScreenIntentDecision$lambda$7((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logHeadsUpFeatureChanged(final boolean z) {
        LogBuffer.log$default(this.buffer, "VisualInterruptionDecisionProvider", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionLogger$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return VisualInterruptionDecisionLogger.logHeadsUpFeatureChanged$lambda$0(z, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionLogger$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return VisualInterruptionDecisionLogger.logHeadsUpFeatureChanged$lambda$1((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logWillDismissAll() {
        LogBuffer.log$default(this.buffer, "VisualInterruptionDecisionProvider", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionLogger$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return VisualInterruptionDecisionLogger.logWillDismissAll$lambda$2((LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionLogger$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return VisualInterruptionDecisionLogger.logWillDismissAll$lambda$3((LogMessage) obj);
            }
        }, null, 16, null);
    }
}
