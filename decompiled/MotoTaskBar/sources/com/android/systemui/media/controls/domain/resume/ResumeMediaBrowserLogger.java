package com.android.systemui.media.controls.domain.resume;

import android.content.ComponentName;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: ResumeMediaBrowserLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ResumeMediaBrowserLogger {
    private final LogBuffer buffer;

    public ResumeMediaBrowserLogger(LogBuffer logBuffer) {
        logBuffer.getClass();
        this.buffer = logBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logConnection$lambda$0(ComponentName componentName, String str, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(componentName.toShortString());
        logMessage.setStr2(str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logConnection$lambda$1(LogMessage logMessage) {
        logMessage.getClass();
        return "Connecting browser for component " + logMessage.getStr1() + " due to " + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logDisconnect$lambda$2(ComponentName componentName, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(componentName.toShortString());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logDisconnect$lambda$3(LogMessage logMessage) {
        logMessage.getClass();
        return "Disconnecting browser for component " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logSessionDestroyed$lambda$4(boolean z, ComponentName componentName, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setBool1(z);
        logMessage.setStr1(componentName.toShortString());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logSessionDestroyed$lambda$5(LogMessage logMessage) {
        logMessage.getClass();
        return "Session destroyed. Active browser = " + logMessage.getBool1() + ". Browser component = " + logMessage.getStr1() + ".";
    }

    public final void logConnection(final ComponentName componentName, final String str) {
        componentName.getClass();
        str.getClass();
        LogBuffer.log$default(this.buffer, "MediaBrowser", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.media.controls.domain.resume.ResumeMediaBrowserLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ResumeMediaBrowserLogger.logConnection$lambda$0(componentName, str, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.media.controls.domain.resume.ResumeMediaBrowserLogger$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ResumeMediaBrowserLogger.logConnection$lambda$1((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logDisconnect(final ComponentName componentName) {
        componentName.getClass();
        LogBuffer.log$default(this.buffer, "MediaBrowser", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.media.controls.domain.resume.ResumeMediaBrowserLogger$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ResumeMediaBrowserLogger.logDisconnect$lambda$2(componentName, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.media.controls.domain.resume.ResumeMediaBrowserLogger$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ResumeMediaBrowserLogger.logDisconnect$lambda$3((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logSessionDestroyed(final boolean z, final ComponentName componentName) {
        componentName.getClass();
        LogBuffer.log$default(this.buffer, "MediaBrowser", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.media.controls.domain.resume.ResumeMediaBrowserLogger$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ResumeMediaBrowserLogger.logSessionDestroyed$lambda$4(z, componentName, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.media.controls.domain.resume.ResumeMediaBrowserLogger$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ResumeMediaBrowserLogger.logSessionDestroyed$lambda$5((LogMessage) obj);
            }
        }, null, 16, null);
    }
}
