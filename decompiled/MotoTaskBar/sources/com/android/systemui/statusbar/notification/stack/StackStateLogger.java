package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.util.DumpUtilsKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: StackStateLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class StackStateLogger {
    private final LogBuffer buffer;
    private final LogBuffer notificationRenderBuffer;

    public StackStateLogger(LogBuffer logBuffer, LogBuffer logBuffer2) {
        logBuffer.getClass();
        logBuffer2.getClass();
        this.buffer = logBuffer;
        this.notificationRenderBuffer = logBuffer2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit animationEnd$lambda$12(String str, String str2, boolean z, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.logKey(str));
        logMessage.setStr2(str2);
        logMessage.setBool1(z);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String animationEnd$lambda$13(LogMessage logMessage) {
        logMessage.getClass();
        return "Animation End, type: " + logMessage.getStr2() + ", notif key: " + logMessage.getStr1() + ", isHeadsUp: " + logMessage.getBool1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit animationStart$lambda$10(String str, String str2, boolean z, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.logKey(str));
        logMessage.setStr2(str2);
        logMessage.setBool1(z);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String animationStart$lambda$11(LogMessage logMessage) {
        logMessage.getClass();
        return "Animation Start, type: " + logMessage.getStr2() + ", notif key: " + logMessage.getStr1() + ", isHeadsUp: " + logMessage.getBool1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit appearAnimationEnded$lambda$4(String str, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.logKey(str));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String appearAnimationEnded$lambda$5(LogMessage logMessage) {
        logMessage.getClass();
        return "Heads up notification appear animation ended " + logMessage.getStr1() + " ";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logHUNViewAppearing$lambda$0(String str, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.logKey(str));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logHUNViewAppearing$lambda$1(LogMessage logMessage) {
        logMessage.getClass();
        return "Heads up notification view appearing " + logMessage.getStr1() + " ";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logHUNViewAppearingWithAddEvent$lambda$2(String str, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.logKey(str));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logHUNViewAppearingWithAddEvent$lambda$3(LogMessage logMessage) {
        logMessage.getClass();
        return "Heads up view appearing " + logMessage.getStr1() + " for ANIMATION_TYPE_ADD";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit processAnimationEventsRemoval$lambda$6(String str, int i, boolean z, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.logKey(str));
        logMessage.setInt1(i);
        logMessage.setBool1(z);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String processAnimationEventsRemoval$lambda$7(LogMessage logMessage) {
        logMessage.getClass();
        return "ProcessAnimationEvents ANIMATION_TYPE_REMOVE for: " + logMessage.getStr1() + ", changingViewVisibility: " + DumpUtilsKt.visibilityString(logMessage.getInt1()) + ", isHeadsUp: " + logMessage.getBool1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit processAnimationEventsRemoveSwipeOut$lambda$8(String str, boolean z, boolean z2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.logKey(str));
        logMessage.setBool1(z);
        logMessage.setBool2(z2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String processAnimationEventsRemoveSwipeOut$lambda$9(LogMessage logMessage) {
        logMessage.getClass();
        return "ProcessAnimationEvents ANIMATION_TYPE_REMOVE_SWIPED_OUT for: " + logMessage.getStr1() + ", isFullySwipedOut: " + logMessage.getBool1() + ", isHeadsUp: " + logMessage.getBool2();
    }

    public final void animationEnd(final String str, final String str2, final boolean z) {
        str.getClass();
        str2.getClass();
        LogBuffer.log$default(this.notificationRenderBuffer, "StackScroll", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.StackStateLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return StackStateLogger.animationEnd$lambda$12(str, str2, z, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.StackStateLogger$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return StackStateLogger.animationEnd$lambda$13((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void animationStart(final String str, final String str2, final boolean z) {
        str2.getClass();
        LogBuffer.log$default(this.notificationRenderBuffer, "StackScroll", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.StackStateLogger$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return StackStateLogger.animationStart$lambda$10(str, str2, z, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.StackStateLogger$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return StackStateLogger.animationStart$lambda$11((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void appearAnimationEnded(final String str) {
        str.getClass();
        LogBuffer.log$default(this.buffer, "StackScroll", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.StackStateLogger$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return StackStateLogger.appearAnimationEnded$lambda$4(str, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.StackStateLogger$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return StackStateLogger.appearAnimationEnded$lambda$5((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logHUNViewAppearing(final String str) {
        str.getClass();
        LogBuffer.log$default(this.buffer, "StackScroll", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.StackStateLogger$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return StackStateLogger.logHUNViewAppearing$lambda$0(str, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.StackStateLogger$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return StackStateLogger.logHUNViewAppearing$lambda$1((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logHUNViewAppearingWithAddEvent(final String str) {
        str.getClass();
        LogBuffer.log$default(this.buffer, "StackScroll", LogLevel.ERROR, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.StackStateLogger$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return StackStateLogger.logHUNViewAppearingWithAddEvent$lambda$2(str, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.StackStateLogger$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return StackStateLogger.logHUNViewAppearingWithAddEvent$lambda$3((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void processAnimationEventsRemoval(final String str, final int i, final boolean z) {
        str.getClass();
        LogBuffer.log$default(this.notificationRenderBuffer, "StackScroll", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.StackStateLogger$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return StackStateLogger.processAnimationEventsRemoval$lambda$6(str, i, z, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.StackStateLogger$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return StackStateLogger.processAnimationEventsRemoval$lambda$7((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void processAnimationEventsRemoveSwipeOut(final String str, final boolean z, final boolean z2) {
        str.getClass();
        LogBuffer.log$default(this.notificationRenderBuffer, "StackScroll", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.StackStateLogger$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return StackStateLogger.processAnimationEventsRemoveSwipeOut$lambda$8(str, z, z2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.StackStateLogger$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return StackStateLogger.processAnimationEventsRemoveSwipeOut$lambda$9((LogMessage) obj);
            }
        }, null, 16, null);
    }
}
