package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: NotificationChildrenContainerLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationChildrenContainerLogger {
    public static final Companion Companion = new Companion(null);
    private final LogBuffer notificationRenderBuffer;

    /* JADX INFO: compiled from: NotificationChildrenContainerLogger.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public NotificationChildrenContainerLogger(LogBuffer logBuffer) {
        logBuffer.getClass();
        this.notificationRenderBuffer = logBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit addTransientRow$lambda$0(NotificationEntry notificationEntry, NotificationEntry notificationEntry2, int i, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(NotificationUtilsKt.getLogKey(notificationEntry2));
        logMessage.setInt1(i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String addTransientRow$lambda$1(LogMessage logMessage) {
        logMessage.getClass();
        return "addTransientRow: childKey: " + logMessage.getStr1() + " -- containerKey: " + logMessage.getStr2() + " -- index: " + logMessage.getInt1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit removeTransientRow$lambda$2(NotificationEntry notificationEntry, NotificationEntry notificationEntry2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(NotificationUtilsKt.getLogKey(notificationEntry2));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String removeTransientRow$lambda$3(LogMessage logMessage) {
        logMessage.getClass();
        return "removeTransientRow: childKey: " + logMessage.getStr1() + " -- containerKey: " + logMessage.getStr2();
    }

    public final void addTransientRow(final NotificationEntry notificationEntry, final NotificationEntry notificationEntry2, final int i) {
        notificationEntry.getClass();
        notificationEntry2.getClass();
        LogBuffer.log$default(this.notificationRenderBuffer, "NotifChildrenContainer", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationChildrenContainerLogger$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationChildrenContainerLogger.addTransientRow$lambda$0(notificationEntry, notificationEntry2, i, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationChildrenContainerLogger$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationChildrenContainerLogger.addTransientRow$lambda$1((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void removeTransientRow(final NotificationEntry notificationEntry, final NotificationEntry notificationEntry2) {
        notificationEntry.getClass();
        notificationEntry2.getClass();
        LogBuffer.log$default(this.notificationRenderBuffer, "NotifChildrenContainer", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationChildrenContainerLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationChildrenContainerLogger.removeTransientRow$lambda$2(notificationEntry, notificationEntry2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationChildrenContainerLogger$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationChildrenContainerLogger.removeTransientRow$lambda$3((LogMessage) obj);
            }
        }, null, 16, null);
    }
}
