package com.android.systemui.media.controls.ui.controller;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: MediaViewLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaViewLogger {
    private final LogBuffer buffer;

    public MediaViewLogger(LogBuffer logBuffer) {
        logBuffer.getClass();
        this.buffer = logBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logMediaLocation$lambda$2(String str, int i, int i2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(str);
        logMessage.setInt1(i);
        logMessage.setInt2(i2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logMediaLocation$lambda$3(LogMessage logMessage) {
        logMessage.getClass();
        return "location (" + logMessage.getStr1() + "): " + logMessage.getInt1() + " -> " + logMessage.getInt2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logMediaSize$lambda$0(String str, int i, int i2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(str);
        logMessage.setInt1(i);
        logMessage.setInt2(i2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logMediaSize$lambda$1(LogMessage logMessage) {
        logMessage.getClass();
        return "size (" + logMessage.getStr1() + "): " + logMessage.getInt1() + " x " + logMessage.getInt2();
    }

    public final void logMediaLocation(final String str, final int i, final int i2) {
        str.getClass();
        LogBuffer.log$default(this.buffer, "MediaView", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.media.controls.ui.controller.MediaViewLogger$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaViewLogger.logMediaLocation$lambda$2(str, i, i2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.media.controls.ui.controller.MediaViewLogger$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaViewLogger.logMediaLocation$lambda$3((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logMediaSize(final String str, final int i, final int i2) {
        str.getClass();
        LogBuffer.log$default(this.buffer, "MediaView", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.media.controls.ui.controller.MediaViewLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaViewLogger.logMediaSize$lambda$0(str, i, i2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.media.controls.ui.controller.MediaViewLogger$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaViewLogger.logMediaSize$lambda$1((LogMessage) obj);
            }
        }, null, 16, null);
    }
}
