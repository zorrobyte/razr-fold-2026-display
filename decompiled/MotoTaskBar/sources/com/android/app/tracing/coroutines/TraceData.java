package com.android.app.tracing.coroutines;

import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import java.util.ArrayDeque;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TraceData.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TraceData {
    private final int currentId;
    public ArrayDeque slices;
    private final boolean strictMode;

    public TraceData(int i, boolean z) {
        this.currentId = i;
        this.strictMode = z;
    }

    private final void strictModeCheck() {
        if (this.strictMode) {
            TraceStorage traceStorage = (TraceStorage) TraceContextElementKt.getTraceThreadLocal().get();
            if ((traceStorage != null ? traceStorage.getData$frameworks__libs__systemui__tracinglib__core__android_common__tracinglib_platform() : null) != this) {
                throw new ConcurrentModificationException("TraceData should only be accessed using the ThreadLocal: CURRENT_TRACE.get(). Accessing TraceData by other means, such as through the TraceContextElement's property may lead to concurrent modification.");
            }
        }
    }

    public final byte beginAllOnThread$frameworks__libs__systemui__tracinglib__core__android_common__tracinglib_platform() {
        byte b = 0;
        if (Trace.isTagEnabled(4096L)) {
            strictModeCheck();
            if (this.slices != null) {
                Iterator itDescendingIterator = getSlices$frameworks__libs__systemui__tracinglib__core__android_common__tracinglib_platform().descendingIterator();
                itDescendingIterator.getClass();
                while (itDescendingIterator.hasNext()) {
                    String str = (String) itDescendingIterator.next();
                    str.getClass();
                    TraceUtilsKt.beginSlice(str);
                    b = (byte) (b + 1);
                }
            }
        }
        return b;
    }

    public final ArrayDeque getSlices$frameworks__libs__systemui__tracinglib__core__android_common__tracinglib_platform() {
        ArrayDeque arrayDeque = this.slices;
        if (arrayDeque != null) {
            return arrayDeque;
        }
        Intrinsics.throwUninitializedPropertyAccessException("slices");
        return null;
    }

    public String toString() {
        return super.toString();
    }
}
