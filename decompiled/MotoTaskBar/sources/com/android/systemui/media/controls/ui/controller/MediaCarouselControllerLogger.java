package com.android.systemui.media.controls.ui.controller;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: MediaCarouselControllerLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaCarouselControllerLogger {
    private final LogBuffer buffer;

    public MediaCarouselControllerLogger(LogBuffer logBuffer) {
        logBuffer.getClass();
        this.buffer = logBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logMediaLoaded$lambda$2(String str, boolean z, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(str);
        logMessage.setBool1(z);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logMediaLoaded$lambda$3(LogMessage logMessage) {
        logMessage.getClass();
        return "add player " + logMessage.getStr1() + ", active: " + logMessage.getBool1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logMediaRemoved$lambda$4(String str, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logMediaRemoved$lambda$5(LogMessage logMessage) {
        logMessage.getClass();
        return "removing player " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logPotentialMemoryLeak$lambda$0(String str, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logPotentialMemoryLeak$lambda$1(LogMessage logMessage) {
        logMessage.getClass();
        return "Potential memory leak: Removing control panel for " + logMessage.getStr1() + " from map without calling #onDestroy";
    }

    public final void logMediaLoaded(final String str, final boolean z) {
        str.getClass();
        LogBuffer.log$default(this.buffer, "MediaCarouselCtlrLog", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselControllerLogger$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaCarouselControllerLogger.logMediaLoaded$lambda$2(str, z, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselControllerLogger$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaCarouselControllerLogger.logMediaLoaded$lambda$3((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logMediaRemoved(final String str) {
        str.getClass();
        LogBuffer.log$default(this.buffer, "MediaCarouselCtlrLog", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselControllerLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaCarouselControllerLogger.logMediaRemoved$lambda$4(str, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselControllerLogger$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaCarouselControllerLogger.logMediaRemoved$lambda$5((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logPotentialMemoryLeak(final String str) {
        str.getClass();
        LogBuffer.log$default(this.buffer, "MediaCarouselCtlrLog", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselControllerLogger$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaCarouselControllerLogger.logPotentialMemoryLeak$lambda$0(str, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselControllerLogger$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaCarouselControllerLogger.logPotentialMemoryLeak$lambda$1((LogMessage) obj);
            }
        }, null, 16, null);
    }
}
