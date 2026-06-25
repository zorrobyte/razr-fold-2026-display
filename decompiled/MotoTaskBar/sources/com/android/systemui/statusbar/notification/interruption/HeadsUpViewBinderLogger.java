package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: HeadsUpViewBinderLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class HeadsUpViewBinderLogger {
    private final LogBuffer buffer;

    public HeadsUpViewBinderLogger(LogBuffer logBuffer) {
        logBuffer.getClass();
        this.buffer = logBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit currentOngoingBindingAborted$lambda$2(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String currentOngoingBindingAborted$lambda$3(LogMessage logMessage) {
        logMessage.getClass();
        return "aborted potential ongoing heads up entry binding " + logMessage.getStr1() + " ";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit entryBindStageParamsNullOnUnbind$lambda$10(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String entryBindStageParamsNullOnUnbind$lambda$11(LogMessage logMessage) {
        logMessage.getClass();
        return "heads up entry bind stage params null on unbind " + logMessage.getStr1() + " ";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit entryBoundSuccessfully$lambda$4(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String entryBoundSuccessfully$lambda$5(LogMessage logMessage) {
        logMessage.getClass();
        return "heads up entry bound successfully " + logMessage.getStr1() + " ";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit entryContentViewMarkedFreeable$lambda$8(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String entryContentViewMarkedFreeable$lambda$9(LogMessage logMessage) {
        logMessage.getClass();
        return "start unbinding heads up entry " + logMessage.getStr1() + " ";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit entryUnbound$lambda$6(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String entryUnbound$lambda$7(LogMessage logMessage) {
        logMessage.getClass();
        return "heads up entry unbound successfully " + logMessage.getStr1() + " ";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit startBindingHun$lambda$0(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String startBindingHun$lambda$1(LogMessage logMessage) {
        logMessage.getClass();
        return "start binding heads up entry " + logMessage.getStr1() + " ";
    }

    public final void currentOngoingBindingAborted(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "HeadsUpViewBinder", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinderLogger$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return HeadsUpViewBinderLogger.currentOngoingBindingAborted$lambda$2(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinderLogger$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return HeadsUpViewBinderLogger.currentOngoingBindingAborted$lambda$3((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void entryBindStageParamsNullOnUnbind(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "HeadsUpViewBinder", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinderLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return HeadsUpViewBinderLogger.entryBindStageParamsNullOnUnbind$lambda$10(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinderLogger$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return HeadsUpViewBinderLogger.entryBindStageParamsNullOnUnbind$lambda$11((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void entryBoundSuccessfully(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "HeadsUpViewBinder", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinderLogger$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return HeadsUpViewBinderLogger.entryBoundSuccessfully$lambda$4(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinderLogger$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return HeadsUpViewBinderLogger.entryBoundSuccessfully$lambda$5((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void entryContentViewMarkedFreeable(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "HeadsUpViewBinder", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinderLogger$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return HeadsUpViewBinderLogger.entryContentViewMarkedFreeable$lambda$8(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinderLogger$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return HeadsUpViewBinderLogger.entryContentViewMarkedFreeable$lambda$9((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void entryUnbound(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "HeadsUpViewBinder", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinderLogger$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return HeadsUpViewBinderLogger.entryUnbound$lambda$6(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinderLogger$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return HeadsUpViewBinderLogger.entryUnbound$lambda$7((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void startBindingHun(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "HeadsUpViewBinder", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinderLogger$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return HeadsUpViewBinderLogger.startBindingHun$lambda$0(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinderLogger$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return HeadsUpViewBinderLogger.startBindingHun$lambda$1((LogMessage) obj);
            }
        }, null, 16, null);
    }
}
