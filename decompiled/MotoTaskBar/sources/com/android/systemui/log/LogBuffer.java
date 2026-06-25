package com.android.systemui.log;

import android.util.Log;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: LogBuffer.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LogBuffer {
    public static /* synthetic */ void log$default(LogBuffer logBuffer, String str, LogLevel logLevel, Function1 function1, Function1 function12, Throwable th, int i, Object obj) {
        if ((i & 16) != 0) {
            th = null;
        }
        logBuffer.log(str, logLevel, function1, function12, th);
    }

    private final LogMessage obtain(String str, LogLevel logLevel, Function1 function1, Throwable th) {
        LogMessageImpl logMessageImplCreate = LogMessageImpl.Factory.create();
        logMessageImplCreate.reset(str, logLevel, function1, th);
        return logMessageImplCreate;
    }

    public final void log(String str, LogLevel logLevel, Function1 function1, Function1 function12, Throwable th) {
        str.getClass();
        logLevel.getClass();
        function1.getClass();
        function12.getClass();
        LogMessage logMessageObtain = obtain(str, logLevel, function12, th);
        function1.invoke(logMessageObtain);
        Log.d(str, (String) function12.invoke(logMessageObtain));
    }
}
