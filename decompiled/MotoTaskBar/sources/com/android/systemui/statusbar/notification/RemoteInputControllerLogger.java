package com.android.systemui.statusbar.notification;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: RemoteInputControllerLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RemoteInputControllerLogger {
    private static final Companion Companion = new Companion(null);
    private final LogBuffer logBuffer;

    /* JADX INFO: compiled from: RemoteInputControllerLogger.kt */
    final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public RemoteInputControllerLogger(LogBuffer logBuffer) {
        logBuffer.getClass();
        this.logBuffer = logBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logAddRemoteInput$lambda$0(String str, String str2, String str3, boolean z, boolean z2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(str);
        logMessage.setStr2(str2);
        logMessage.setStr3(str3);
        logMessage.setBool1(z);
        logMessage.setBool2(z2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logAddRemoteInput$lambda$1(LogMessage logMessage) {
        logMessage.getClass();
        return "addRemoteInput reason:" + logMessage.getStr2() + " entry: " + logMessage.getStr1() + ", style:" + logMessage.getStr3() + ", isAlreadyActive: " + logMessage.getBool1() + ", isFound:" + logMessage.getBool2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logRemoteInputApplySkipped$lambda$4(String str, String str2, String str3, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(str);
        logMessage.setStr2(str2);
        logMessage.setStr3(str3);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logRemoteInputApplySkipped$lambda$5(LogMessage logMessage) {
        logMessage.getClass();
        return "removeRemoteInput[apply is skipped] reason: " + logMessage.getStr2() + "for entry: " + logMessage.getStr1() + ", style: " + logMessage.getStr3() + " ";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logRemoveRemoteInput$lambda$2(String str, String str2, String str3, boolean z, boolean z2, boolean z3, boolean z4, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(str);
        logMessage.setStr2(str2);
        logMessage.setStr3(str3);
        logMessage.setBool1(z);
        logMessage.setBool2(z2);
        logMessage.setBool3(z3);
        logMessage.setBool4(z4);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logRemoveRemoteInput$lambda$3(LogMessage logMessage) {
        logMessage.getClass();
        return "removeRemoteInput reason: " + logMessage.getStr2() + " entry: " + logMessage.getStr1() + ", style: " + logMessage.getStr3() + ", remoteEditImeVisible: " + logMessage.getBool1() + ", remoteEditImeAnimatingAway: " + logMessage.getBool2() + ", isRemoteInputActiveForEntry: " + logMessage.getBool3() + ", isRemoteInputActive: " + logMessage.getBool4();
    }

    public final void logAddRemoteInput(final String str, final boolean z, final boolean z2, final String str2, final String str3) {
        str.getClass();
        str2.getClass();
        str3.getClass();
        LogBuffer.log$default(this.logBuffer, "RemoteInputControllerLog", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.RemoteInputControllerLogger$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RemoteInputControllerLogger.logAddRemoteInput$lambda$0(str, str2, str3, z, z2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.RemoteInputControllerLogger$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RemoteInputControllerLogger.logAddRemoteInput$lambda$1((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logRemoteInputApplySkipped(final String str, final String str2, final String str3) {
        str.getClass();
        str2.getClass();
        str3.getClass();
        LogBuffer.log$default(this.logBuffer, "RemoteInputControllerLog", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.RemoteInputControllerLogger$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RemoteInputControllerLogger.logRemoteInputApplySkipped$lambda$4(str, str2, str3, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.RemoteInputControllerLogger$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RemoteInputControllerLogger.logRemoteInputApplySkipped$lambda$5((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logRemoveRemoteInput(final String str, final boolean z, final boolean z2, final boolean z3, final boolean z4, final String str2, final String str3) {
        str.getClass();
        str2.getClass();
        str3.getClass();
        LogBuffer.log$default(this.logBuffer, "RemoteInputControllerLog", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.RemoteInputControllerLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RemoteInputControllerLogger.logRemoveRemoteInput$lambda$2(str, str2, str3, z, z2, z3, z4, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.RemoteInputControllerLogger$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RemoteInputControllerLogger.logRemoveRemoteInput$lambda$3((LogMessage) obj);
            }
        }, null, 16, null);
    }
}
