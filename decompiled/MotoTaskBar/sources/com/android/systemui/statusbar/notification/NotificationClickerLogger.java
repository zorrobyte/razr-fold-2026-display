package com.android.systemui.statusbar.notification;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: NotificationClickerLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationClickerLogger {
    private final LogBuffer buffer;

    public NotificationClickerLogger(LogBuffer logBuffer) {
        logBuffer.getClass();
        this.buffer = logBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logChildrenExpanded$lambda$6(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logChildrenExpanded$lambda$7(LogMessage logMessage) {
        logMessage.getClass();
        return "Ignoring click on " + logMessage.getStr1() + "; children are expanded";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logMenuVisible$lambda$2(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logMenuVisible$lambda$3(LogMessage logMessage) {
        logMessage.getClass();
        return "Ignoring click on " + logMessage.getStr1() + "; menu is visible";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logOnClick$lambda$0(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(notificationEntry.getRanking().getChannel().getId());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logOnClick$lambda$1(LogMessage logMessage) {
        logMessage.getClass();
        return "CLICK " + logMessage.getStr1() + " (channel=" + logMessage.getStr2() + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logParentMenuVisible$lambda$4(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logParentMenuVisible$lambda$5(LogMessage logMessage) {
        logMessage.getClass();
        return "Ignoring click on " + logMessage.getStr1() + "; parent menu is visible";
    }

    public final void logChildrenExpanded(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotificationClicker", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationClickerLogger$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationClickerLogger.logChildrenExpanded$lambda$6(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationClickerLogger$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationClickerLogger.logChildrenExpanded$lambda$7((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logMenuVisible(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotificationClicker", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationClickerLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationClickerLogger.logMenuVisible$lambda$2(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationClickerLogger$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationClickerLogger.logMenuVisible$lambda$3((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logOnClick(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotificationClicker", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationClickerLogger$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationClickerLogger.logOnClick$lambda$0(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationClickerLogger$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationClickerLogger.logOnClick$lambda$1((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logParentMenuVisible(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotificationClicker", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationClickerLogger$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationClickerLogger.logParentMenuVisible$lambda$4(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationClickerLogger$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationClickerLogger.logParentMenuVisible$lambda$5((LogMessage) obj);
            }
        }, null, 16, null);
    }
}
