package com.android.systemui.statusbar.notification.row;

import android.view.ViewGroup;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: NotificationRowLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationRowLogger {
    private final LogBuffer buffer;
    private final LogBuffer notificationRenderBuffer;

    public NotificationRowLogger(LogBuffer logBuffer, LogBuffer logBuffer2) {
        logBuffer.getClass();
        logBuffer2.getClass();
        this.buffer = logBuffer;
        this.notificationRenderBuffer = logBuffer2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logAddTransientRow$lambda$10(NotificationEntry notificationEntry, NotificationEntry notificationEntry2, int i, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(NotificationUtilsKt.getLogKey(notificationEntry2));
        logMessage.setInt1(i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logAddTransientRow$lambda$11(LogMessage logMessage) {
        logMessage.getClass();
        return "addTransientRow to row: childKey: " + logMessage.getStr1() + " -- containerKey: " + logMessage.getStr2() + " -- index: " + logMessage.getInt1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logKeepInParentChildDetached$lambda$0(NotificationEntry notificationEntry, NotificationEntry notificationEntry2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(NotificationUtilsKt.getLogKey(notificationEntry2));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logKeepInParentChildDetached$lambda$1(LogMessage logMessage) {
        logMessage.getClass();
        return "Detach child " + logMessage.getStr1() + " kept in parent " + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logRemoveTransientFromContainer$lambda$4(NotificationEntry notificationEntry, NotificationEntry notificationEntry2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(NotificationUtilsKt.getLogKey(notificationEntry2));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logRemoveTransientFromContainer$lambda$5(LogMessage logMessage) {
        logMessage.getClass();
        return "RemoveTransientRow from ChildrenContainer: childKey: " + logMessage.getStr1() + " -- containerKey: " + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logRemoveTransientFromNssl$lambda$6(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logRemoveTransientFromNssl$lambda$7(LogMessage logMessage) {
        logMessage.getClass();
        return "RemoveTransientRow from Nssl: childKey: " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logRemoveTransientFromViewGroup$lambda$8(NotificationEntry notificationEntry, ViewGroup viewGroup, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(viewGroup.toString());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logRemoveTransientFromViewGroup$lambda$9(LogMessage logMessage) {
        logMessage.getClass();
        return "RemoveTransientRow from other ViewGroup: childKey: " + logMessage.getStr1() + " -- ViewGroup: " + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logRemoveTransientRow$lambda$12(NotificationEntry notificationEntry, NotificationEntry notificationEntry2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(NotificationUtilsKt.getLogKey(notificationEntry2));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logRemoveTransientRow$lambda$13(LogMessage logMessage) {
        logMessage.getClass();
        return "removeTransientRow from row: childKey: " + logMessage.getStr1() + " -- containerKey: " + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logSkipAttachingKeepInParentChild$lambda$2(NotificationEntry notificationEntry, NotificationEntry notificationEntry2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(NotificationUtilsKt.getLogKey(notificationEntry2));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logSkipAttachingKeepInParentChild$lambda$3(LogMessage logMessage) {
        logMessage.getClass();
        return "Skipping to attach " + logMessage.getStr1() + " to " + logMessage.getStr2() + ", because it still flagged to keep in parent";
    }

    public final void logAddTransientRow(final NotificationEntry notificationEntry, final NotificationEntry notificationEntry2, final int i) {
        notificationEntry.getClass();
        notificationEntry2.getClass();
        LogBuffer.log$default(this.notificationRenderBuffer, "NotifRow", LogLevel.ERROR, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowLogger$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowLogger.logAddTransientRow$lambda$10(notificationEntry, notificationEntry2, i, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowLogger$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowLogger.logAddTransientRow$lambda$11((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logKeepInParentChildDetached(final NotificationEntry notificationEntry, final NotificationEntry notificationEntry2) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotifRow", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowLogger.logKeepInParentChildDetached$lambda$0(notificationEntry, notificationEntry2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowLogger$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowLogger.logKeepInParentChildDetached$lambda$1((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logRemoveTransientFromContainer(final NotificationEntry notificationEntry, final NotificationEntry notificationEntry2) {
        notificationEntry.getClass();
        notificationEntry2.getClass();
        LogBuffer.log$default(this.notificationRenderBuffer, "NotifRow", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowLogger$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowLogger.logRemoveTransientFromContainer$lambda$4(notificationEntry, notificationEntry2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowLogger$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowLogger.logRemoveTransientFromContainer$lambda$5((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logRemoveTransientFromNssl(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.notificationRenderBuffer, "NotifRow", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowLogger$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowLogger.logRemoveTransientFromNssl$lambda$6(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowLogger$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowLogger.logRemoveTransientFromNssl$lambda$7((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logRemoveTransientFromViewGroup(final NotificationEntry notificationEntry, final ViewGroup viewGroup) {
        notificationEntry.getClass();
        viewGroup.getClass();
        LogBuffer.log$default(this.notificationRenderBuffer, "NotifRow", LogLevel.WARNING, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowLogger$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowLogger.logRemoveTransientFromViewGroup$lambda$8(notificationEntry, viewGroup, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowLogger$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowLogger.logRemoveTransientFromViewGroup$lambda$9((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logRemoveTransientRow(final NotificationEntry notificationEntry, final NotificationEntry notificationEntry2) {
        notificationEntry.getClass();
        notificationEntry2.getClass();
        LogBuffer.log$default(this.notificationRenderBuffer, "NotifRow", LogLevel.ERROR, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowLogger$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowLogger.logRemoveTransientRow$lambda$12(notificationEntry, notificationEntry2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowLogger$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowLogger.logRemoveTransientRow$lambda$13((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logSkipAttachingKeepInParentChild(final NotificationEntry notificationEntry, final NotificationEntry notificationEntry2) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotifRow", LogLevel.WARNING, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowLogger$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowLogger.logSkipAttachingKeepInParentChild$lambda$2(notificationEntry, notificationEntry2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowLogger$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationRowLogger.logSkipAttachingKeepInParentChild$lambda$3((LogMessage) obj);
            }
        }, null, 16, null);
    }
}
