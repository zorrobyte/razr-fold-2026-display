package com.android.systemui.statusbar.notification.stack;

import android.view.ViewGroup;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.motorola.plugin.core.R;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: NotificationStackScrollLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationStackScrollLogger {
    private final LogBuffer buffer;
    private final LogBuffer notificationRenderBuffer;
    private final LogBuffer shadeLogBuffer;

    public NotificationStackScrollLogger(LogBuffer logBuffer, LogBuffer logBuffer2, LogBuffer logBuffer3) {
        logBuffer.getClass();
        logBuffer2.getClass();
        logBuffer3.getClass();
        this.buffer = logBuffer;
        this.notificationRenderBuffer = logBuffer2;
        this.shadeLogBuffer = logBuffer3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit addTransientChildNotificationToChildContainer$lambda$10(NotificationEntry notificationEntry, NotificationEntry notificationEntry2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(NotificationUtilsKt.getLogKey(notificationEntry2));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String addTransientChildNotificationToChildContainer$lambda$11(LogMessage logMessage) {
        logMessage.getClass();
        return "addTransientChildToContainer from onViewRemovedInternal: childKey: " + logMessage.getStr1() + " -- containerKey: " + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit addTransientChildNotificationToNssl$lambda$12(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String addTransientChildNotificationToNssl$lambda$13(LogMessage logMessage) {
        logMessage.getClass();
        return "addTransientRowToNssl from onViewRemovedInternal: childKey: " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit addTransientChildNotificationToViewGroup$lambda$14(NotificationEntry notificationEntry, ViewGroup viewGroup, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(viewGroup.toString());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String addTransientChildNotificationToViewGroup$lambda$15(LogMessage logMessage) {
        logMessage.getClass();
        return "addTransientRowTo unhandled ViewGroup from onViewRemovedInternal: childKey: " + logMessage.getStr1() + " -- ViewGroup: " + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit addTransientRow$lambda$16(NotificationEntry notificationEntry, int i, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setInt1(i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String addTransientRow$lambda$17(LogMessage logMessage) {
        logMessage.getClass();
        return "addTransientRow to NSSL: childKey: " + logMessage.getStr1() + " -- index: " + logMessage.getInt1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit hunAnimationEventAdded$lambda$2(NotificationEntry notificationEntry, String str, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String hunAnimationEventAdded$lambda$3(LogMessage logMessage) {
        logMessage.getClass();
        return "heads up animation added: " + logMessage.getStr1() + " with type " + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit hunAnimationSkipped$lambda$0(NotificationEntry notificationEntry, String str, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String hunAnimationSkipped$lambda$1(LogMessage logMessage) {
        logMessage.getClass();
        return "heads up animation skipped: key: " + logMessage.getStr1() + " reason: " + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit hunSkippedForUnexpectedState$lambda$4(NotificationEntry notificationEntry, boolean z, boolean z2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setBool1(z);
        logMessage.setBool2(z2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String hunSkippedForUnexpectedState$lambda$5(LogMessage logMessage) {
        logMessage.getClass();
        return "HUN animation skipped for unexpected hun state: key: " + logMessage.getStr1() + " expected: " + logMessage.getBool1() + " actual: " + logMessage.getBool2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logEmptySpaceClick$lambda$6(int i, boolean z, boolean z2, String str, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setInt1(i);
        logMessage.setBool1(z);
        logMessage.setBool2(z2);
        logMessage.setStr1(str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logEmptySpaceClick$lambda$7(LogMessage logMessage) {
        logMessage.getClass();
        return "handleEmptySpaceClick: statusBarState: " + logMessage.getInt1() + " isTouchAClick: " + logMessage.getBool1() + " isTouchBelowNotification: " + logMessage.getBool2() + " motionEvent: " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit removeTransientRow$lambda$18(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String removeTransientRow$lambda$19(LogMessage logMessage) {
        logMessage.getClass();
        return "removeTransientRow from NSSL: childKey: " + logMessage.getStr1();
    }

    public final void addTransientChildNotificationToChildContainer(final NotificationEntry notificationEntry, final NotificationEntry notificationEntry2) {
        notificationEntry.getClass();
        notificationEntry2.getClass();
        LogBuffer.log$default(this.notificationRenderBuffer, "NotificationStackScroll", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationStackScrollLogger.addTransientChildNotificationToChildContainer$lambda$10(notificationEntry, notificationEntry2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationStackScrollLogger.addTransientChildNotificationToChildContainer$lambda$11((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void addTransientChildNotificationToNssl(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.notificationRenderBuffer, "NotificationStackScroll", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationStackScrollLogger.addTransientChildNotificationToNssl$lambda$12(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationStackScrollLogger.addTransientChildNotificationToNssl$lambda$13((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void addTransientChildNotificationToViewGroup(final NotificationEntry notificationEntry, final ViewGroup viewGroup) {
        notificationEntry.getClass();
        viewGroup.getClass();
        LogBuffer.log$default(this.notificationRenderBuffer, "NotificationStackScroll", LogLevel.ERROR, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationStackScrollLogger.addTransientChildNotificationToViewGroup$lambda$14(notificationEntry, viewGroup, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationStackScrollLogger.addTransientChildNotificationToViewGroup$lambda$15((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void addTransientRow(final NotificationEntry notificationEntry, final int i) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.notificationRenderBuffer, "NotificationStackScroll", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationStackScrollLogger.addTransientRow$lambda$16(notificationEntry, i, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationStackScrollLogger.addTransientRow$lambda$17((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void hunAnimationEventAdded(final NotificationEntry notificationEntry, int i) {
        final String strValueOf;
        notificationEntry.getClass();
        if (i != 0) {
            switch (i) {
                case R.styleable.GradientColor_android_endY /* 11 */:
                    strValueOf = "HEADS_UP_APPEAR";
                    break;
                case 12:
                    strValueOf = "HEADS_UP_DISAPPEAR";
                    break;
                case 13:
                    strValueOf = "HEADS_UP_DISAPPEAR_CLICK";
                    break;
                case 14:
                    strValueOf = "HEADS_UP_OTHER";
                    break;
                default:
                    strValueOf = String.valueOf(i);
                    break;
            }
        } else {
            strValueOf = "ADD";
        }
        LogBuffer.log$default(this.buffer, "NotificationStackScroll", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationStackScrollLogger.hunAnimationEventAdded$lambda$2(notificationEntry, strValueOf, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationStackScrollLogger.hunAnimationEventAdded$lambda$3((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void hunAnimationSkipped(final NotificationEntry notificationEntry, final String str) {
        notificationEntry.getClass();
        str.getClass();
        LogBuffer.log$default(this.buffer, "NotificationStackScroll", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationStackScrollLogger.hunAnimationSkipped$lambda$0(notificationEntry, str, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationStackScrollLogger.hunAnimationSkipped$lambda$1((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void hunSkippedForUnexpectedState(final NotificationEntry notificationEntry, final boolean z, final boolean z2) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotificationStackScroll", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationStackScrollLogger.hunSkippedForUnexpectedState$lambda$4(notificationEntry, z, z2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationStackScrollLogger.hunSkippedForUnexpectedState$lambda$5((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logEmptySpaceClick(final boolean z, final int i, final boolean z2, final String str) {
        str.getClass();
        LogBuffer.log$default(this.shadeLogBuffer, "NotificationStackScroll", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$$ExternalSyntheticLambda16
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationStackScrollLogger.logEmptySpaceClick$lambda$6(i, z2, z, str, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationStackScrollLogger.logEmptySpaceClick$lambda$7((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logShadeDebugEvent(String str) {
        str.getClass();
    }

    public final void removeTransientRow(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.notificationRenderBuffer, "NotificationStackScroll", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationStackScrollLogger.removeTransientRow$lambda$18(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationStackScrollLogger.removeTransientRow$lambda$19((LogMessage) obj);
            }
        }, null, 16, null);
    }
}
