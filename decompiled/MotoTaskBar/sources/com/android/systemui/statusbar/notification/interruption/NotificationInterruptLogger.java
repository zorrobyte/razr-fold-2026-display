package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: NotificationInterruptLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationInterruptLogger {
    private final LogBuffer buffer;

    public NotificationInterruptLogger(LogBuffer logBuffer) {
        logBuffer.getClass();
        this.buffer = logBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logFullscreen$lambda$58(NotificationEntry notificationEntry, String str, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logFullscreen$lambda$59(LogMessage logMessage) {
        logMessage.getClass();
        return "FullScreenIntent: " + logMessage.getStr2() + ": " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logHeadsUp$lambda$30(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logHeadsUp$lambda$31(LogMessage logMessage) {
        logMessage.getClass();
        return "Heads up: " + logMessage.getStr1();
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
        return "heads up is enabled=" + logMessage.getBool1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logHeadsUpPackageSnoozeBypassedHasFsi$lambda$14(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logHeadsUpPackageSnoozeBypassedHasFsi$lambda$15(LogMessage logMessage) {
        logMessage.getClass();
        return "Heads up: package snooze bypassed because notification has full-screen intent: " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logMaybeHeadsUpDespiteOldWhen$lambda$26(NotificationEntry notificationEntry, String str, long j, long j2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(str);
        logMessage.setLong1(j);
        logMessage.setLong2(j2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logMaybeHeadsUpDespiteOldWhen$lambda$27(LogMessage logMessage) {
        logMessage.getClass();
        return "Maybe heads up: old when " + logMessage.getLong1() + " (age=" + logMessage.getLong2() + " ms) but " + logMessage.getStr2() + ": " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logNoAlertingAppSuspended$lambda$6(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logNoAlertingAppSuspended$lambda$7(LogMessage logMessage) {
        logMessage.getClass();
        return "No alerting: app is suspended: " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logNoAlertingGroupAlertBehavior$lambda$34(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logNoAlertingGroupAlertBehavior$lambda$35(LogMessage logMessage) {
        logMessage.getClass();
        return "No alerting: suppressed due to group alert behavior: " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logNoAlertingNotificationHidden$lambda$60(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logNoAlertingNotificationHidden$lambda$61(LogMessage logMessage) {
        logMessage.getClass();
        return "No alerting: notification hidden on lock screen: " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logNoAlertingRecentFullscreen$lambda$38(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logNoAlertingRecentFullscreen$lambda$39(LogMessage logMessage) {
        logMessage.getClass();
        return "No alerting: recent fullscreen: " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logNoFullscreen$lambda$54(NotificationEntry notificationEntry, String str, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logNoFullscreen$lambda$55(LogMessage logMessage) {
        logMessage.getClass();
        return "No FullScreenIntent: " + logMessage.getStr2() + ": " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logNoFullscreenWarning$lambda$56(NotificationEntry notificationEntry, String str, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logNoFullscreenWarning$lambda$57(LogMessage logMessage) {
        logMessage.getClass();
        return "No FullScreenIntent: WARNING: " + logMessage.getStr2() + ": " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logNoHeadsUpFeatureDisabled$lambda$10(LogMessage logMessage) {
        logMessage.getClass();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logNoHeadsUpFeatureDisabled$lambda$11(LogMessage logMessage) {
        logMessage.getClass();
        return "No heads up: no huns";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logNoHeadsUpNotImportant$lambda$20(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logNoHeadsUpNotImportant$lambda$21(LogMessage logMessage) {
        logMessage.getClass();
        return "No heads up: unimportant notification: " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logNoHeadsUpOldWhen$lambda$24(NotificationEntry notificationEntry, long j, long j2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setLong1(j);
        logMessage.setLong2(j2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logNoHeadsUpOldWhen$lambda$25(LogMessage logMessage) {
        logMessage.getClass();
        return "No heads up: old when " + logMessage.getLong1() + " (age=" + logMessage.getLong2() + " ms): " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logNoHeadsUpPackageSnoozed$lambda$12(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logNoHeadsUpPackageSnoozed$lambda$13(LogMessage logMessage) {
        logMessage.getClass();
        return "No heads up: snoozed package: " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logNoHeadsUpSuppressedByDnd$lambda$18(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logNoHeadsUpSuppressedByDnd$lambda$19(LogMessage logMessage) {
        logMessage.getClass();
        return "No heads up: suppressed by DND: " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logWillDismissAll$lambda$2(LogMessage logMessage) {
        logMessage.getClass();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logWillDismissAll$lambda$3(LogMessage logMessage) {
        logMessage.getClass();
        return "dismissing any existing heads up notification on disable event";
    }

    public final void logFullscreen(final NotificationEntry notificationEntry, final String str) {
        notificationEntry.getClass();
        str.getClass();
        LogBuffer.log$default(this.buffer, "InterruptionStateProvider", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda24
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logFullscreen$lambda$58(notificationEntry, str, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda25
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logFullscreen$lambda$59((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logHeadsUp(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "InterruptionStateProvider", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logHeadsUp$lambda$30(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logHeadsUp$lambda$31((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logHeadsUpFeatureChanged(final boolean z) {
        LogBuffer.log$default(this.buffer, "InterruptionStateProvider", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logHeadsUpFeatureChanged$lambda$0(z, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logHeadsUpFeatureChanged$lambda$1((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logHeadsUpPackageSnoozeBypassedHasFsi(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "InterruptionStateProvider", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda26
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logHeadsUpPackageSnoozeBypassedHasFsi$lambda$14(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda27
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logHeadsUpPackageSnoozeBypassedHasFsi$lambda$15((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logMaybeHeadsUpDespiteOldWhen(final NotificationEntry notificationEntry, final long j, final long j2, final String str) {
        notificationEntry.getClass();
        str.getClass();
        LogBuffer.log$default(this.buffer, "InterruptionStateProvider", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda18
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logMaybeHeadsUpDespiteOldWhen$lambda$26(notificationEntry, str, j, j2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda19
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logMaybeHeadsUpDespiteOldWhen$lambda$27((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logNoAlertingAppSuspended(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "InterruptionStateProvider", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logNoAlertingAppSuspended$lambda$6(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logNoAlertingAppSuspended$lambda$7((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logNoAlertingGroupAlertBehavior(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "InterruptionStateProvider", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda28
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logNoAlertingGroupAlertBehavior$lambda$34(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda29
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logNoAlertingGroupAlertBehavior$lambda$35((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logNoAlertingNotificationHidden(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "InterruptionStateProvider", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda32
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logNoAlertingNotificationHidden$lambda$60(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda33
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logNoAlertingNotificationHidden$lambda$61((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logNoAlertingRecentFullscreen(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "InterruptionStateProvider", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logNoAlertingRecentFullscreen$lambda$38(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logNoAlertingRecentFullscreen$lambda$39((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logNoFullscreen(final NotificationEntry notificationEntry, final String str) {
        notificationEntry.getClass();
        str.getClass();
        LogBuffer.log$default(this.buffer, "InterruptionStateProvider", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda22
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logNoFullscreen$lambda$54(notificationEntry, str, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda23
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logNoFullscreen$lambda$55((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logNoFullscreenWarning(final NotificationEntry notificationEntry, final String str) {
        notificationEntry.getClass();
        str.getClass();
        LogBuffer.log$default(this.buffer, "InterruptionStateProvider", LogLevel.WARNING, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda30
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logNoFullscreenWarning$lambda$56(notificationEntry, str, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda31
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logNoFullscreenWarning$lambda$57((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logNoHeadsUpFeatureDisabled() {
        LogBuffer.log$default(this.buffer, "InterruptionStateProvider", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logNoHeadsUpFeatureDisabled$lambda$10((LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logNoHeadsUpFeatureDisabled$lambda$11((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logNoHeadsUpNotImportant(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "InterruptionStateProvider", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda20
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logNoHeadsUpNotImportant$lambda$20(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda21
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logNoHeadsUpNotImportant$lambda$21((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logNoHeadsUpOldWhen(final NotificationEntry notificationEntry, final long j, final long j2) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "InterruptionStateProvider", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logNoHeadsUpOldWhen$lambda$24(notificationEntry, j, j2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logNoHeadsUpOldWhen$lambda$25((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logNoHeadsUpPackageSnoozed(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "InterruptionStateProvider", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logNoHeadsUpPackageSnoozed$lambda$12(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logNoHeadsUpPackageSnoozed$lambda$13((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logNoHeadsUpSuppressedByDnd(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "InterruptionStateProvider", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda16
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logNoHeadsUpSuppressedByDnd$lambda$18(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logNoHeadsUpSuppressedByDnd$lambda$19((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logWillDismissAll() {
        LogBuffer.log$default(this.buffer, "InterruptionStateProvider", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logWillDismissAll$lambda$2((LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationInterruptLogger.logWillDismissAll$lambda$3((LogMessage) obj);
            }
        }, null, 16, null);
    }
}
