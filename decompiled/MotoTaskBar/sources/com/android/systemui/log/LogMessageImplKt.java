package com.android.systemui.log;

import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: LogMessageImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class LogMessageImplKt {
    private static final Function1 DEFAULT_PRINTER = new Function1() { // from class: com.android.systemui.log.LogMessageImplKt$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return LogMessageImplKt.DEFAULT_PRINTER$lambda$0((LogMessage) obj);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public static final String DEFAULT_PRINTER$lambda$0(LogMessage logMessage) {
        logMessage.getClass();
        return "Unknown message: " + logMessage;
    }
}
