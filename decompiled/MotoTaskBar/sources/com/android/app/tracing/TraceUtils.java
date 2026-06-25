package com.android.app.tracing;

import android.os.Trace;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;

/* JADX INFO: compiled from: TraceUtils.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TraceUtils {
    public static final TraceUtils INSTANCE = new TraceUtils();

    private TraceUtils() {
    }

    public static final Object trace(String str, Function0 function0) {
        str.getClass();
        function0.getClass();
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice(str);
        }
        try {
            return function0.mo2224invoke();
        } finally {
            InlineMarker.finallyStart(1);
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            InlineMarker.finallyEnd(1);
        }
    }
}
