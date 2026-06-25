package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: ShadeViewDifferLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ShadeViewDifferLogger {
    private final LogBuffer buffer;

    public ShadeViewDifferLogger(LogBuffer logBuffer) {
        logBuffer.getClass();
        this.buffer = logBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logAttachingChild$lambda$4(String str, String str2, int i, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(str);
        logMessage.setStr2(str2);
        logMessage.setInt1(i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logAttachingChild$lambda$5(LogMessage logMessage) {
        logMessage.getClass();
        return "Attaching view " + logMessage.getStr1() + " to " + logMessage.getStr2() + " at index " + logMessage.getInt1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logDetachingChild$lambda$0(String str, boolean z, boolean z2, String str2, String str3, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(str);
        logMessage.setBool1(z);
        logMessage.setBool2(z2);
        logMessage.setStr2(str2);
        logMessage.setStr3(str3);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logDetachingChild$lambda$1(LogMessage logMessage) {
        logMessage.getClass();
        return "Detach " + logMessage.getStr1() + " isTransfer=" + logMessage.getBool1() + " isParentRemoved=" + logMessage.getBool2() + " oldParent=" + logMessage.getStr2() + " newParent=" + logMessage.getStr3();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logDuplicateNodeInTree$lambda$8(RuntimeException runtimeException, NodeSpec nodeSpec, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(runtimeException.toString());
        logMessage.setStr2(NodeControllerKt.treeSpecToStr(nodeSpec));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logDuplicateNodeInTree$lambda$9(LogMessage logMessage) {
        logMessage.getClass();
        return logMessage.getStr1() + " when mapping tree: " + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logMovingChild$lambda$6(String str, String str2, int i, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(str);
        logMessage.setStr2(str2);
        logMessage.setInt1(i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logMovingChild$lambda$7(LogMessage logMessage) {
        logMessage.getClass();
        return "Moving child view " + logMessage.getStr1() + " in " + logMessage.getStr2() + " to index " + logMessage.getInt1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logSkipDetachingChild$lambda$2(String str, String str2, boolean z, boolean z2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(str);
        logMessage.setStr2(str2);
        logMessage.setBool1(z);
        logMessage.setBool2(z2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logSkipDetachingChild$lambda$3(LogMessage logMessage) {
        logMessage.getClass();
        return "Skip detaching " + logMessage.getStr1() + " from " + logMessage.getStr2() + " isTransfer=" + logMessage.getBool1() + " isParentRemoved=" + logMessage.getBool2();
    }

    public final void logAttachingChild(final String str, final String str2, final int i) {
        str.getClass();
        str2.getClass();
        LogBuffer.log$default(this.buffer, "NotifViewManager", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.ShadeViewDifferLogger$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeViewDifferLogger.logAttachingChild$lambda$4(str, str2, i, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.ShadeViewDifferLogger$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeViewDifferLogger.logAttachingChild$lambda$5((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logDetachingChild(final String str, final boolean z, final boolean z2, final String str2, final String str3) {
        str.getClass();
        LogBuffer.log$default(this.buffer, "NotifViewManager", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.ShadeViewDifferLogger$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeViewDifferLogger.logDetachingChild$lambda$0(str, z, z2, str2, str3, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.ShadeViewDifferLogger$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeViewDifferLogger.logDetachingChild$lambda$1((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logDuplicateNodeInTree(final NodeSpec nodeSpec, final RuntimeException runtimeException) {
        nodeSpec.getClass();
        runtimeException.getClass();
        LogBuffer.log$default(this.buffer, "NotifViewManager", LogLevel.ERROR, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.ShadeViewDifferLogger$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeViewDifferLogger.logDuplicateNodeInTree$lambda$8(runtimeException, nodeSpec, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.ShadeViewDifferLogger$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeViewDifferLogger.logDuplicateNodeInTree$lambda$9((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logMovingChild(final String str, final String str2, final int i) {
        str.getClass();
        str2.getClass();
        LogBuffer.log$default(this.buffer, "NotifViewManager", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.ShadeViewDifferLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeViewDifferLogger.logMovingChild$lambda$6(str, str2, i, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.ShadeViewDifferLogger$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeViewDifferLogger.logMovingChild$lambda$7((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logSkipDetachingChild(final String str, final String str2, final boolean z, final boolean z2) {
        str.getClass();
        LogBuffer.log$default(this.buffer, "NotifViewManager", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.ShadeViewDifferLogger$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeViewDifferLogger.logSkipDetachingChild$lambda$2(str, str2, z, z2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.ShadeViewDifferLogger$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShadeViewDifferLogger.logSkipDetachingChild$lambda$3((LogMessage) obj);
            }
        }, null, 16, null);
    }
}
