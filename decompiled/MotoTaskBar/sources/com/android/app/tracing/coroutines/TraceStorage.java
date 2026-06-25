package com.android.app.tracing.coroutines;

import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import kotlin.collections.ArraysKt;

/* JADX INFO: compiled from: TraceData.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TraceStorage {
    private TraceData data;
    private int contIndex = -1;
    private byte[] openSliceCount = new byte[4];
    private int[] continuationIds = null;
    private final String debugCounterTrack = null;

    public TraceStorage(TraceData traceData) {
        this.data = traceData;
    }

    public final TraceData getData$frameworks__libs__systemui__tracinglib__core__android_common__tracinglib_platform() {
        return this.data;
    }

    public final int restoreDataForSuspension(TraceData traceData) {
        this.data = traceData;
        int i = this.contIndex;
        this.contIndex = i - 1;
        if (i >= 0 && this.openSliceCount.length > i) {
            if (Trace.isTagEnabled(4096L)) {
                byte b = this.openSliceCount[i];
                for (int i2 = 0; i2 < b; i2++) {
                    TraceUtilsKt.endSlice();
                }
            }
            int[] iArr = this.continuationIds;
            if (iArr != null) {
                Integer numValueOf = i < iArr.length ? Integer.valueOf(iArr[i]) : null;
                if (numValueOf != null) {
                    return numValueOf.intValue();
                }
            }
        }
        return 0;
    }

    public final void updateDataForContinuation(TraceData traceData, int i) {
        int[] iArr;
        this.data = traceData;
        int i2 = this.contIndex + 1;
        this.contIndex = i2;
        if (i2 < 0 || 512 <= i2) {
            return;
        }
        int length = this.openSliceCount.length;
        if (i2 >= length) {
            int iMax = Math.max(length * 2, 512);
            this.openSliceCount = ArraysKt.copyInto$default(this.openSliceCount, new byte[iMax], 0, 0, 0, 14, (Object) null);
            int[] iArr2 = this.continuationIds;
            this.continuationIds = iArr2 != null ? ArraysKt.copyInto$default(iArr2, new int[iMax], 0, 0, 0, 14, (Object) null) : null;
        }
        byte[] bArr = this.openSliceCount;
        TraceData traceData2 = this.data;
        bArr[i2] = traceData2 != null ? traceData2.beginAllOnThread$frameworks__libs__systemui__tracinglib__core__android_common__tracinglib_platform() : (byte) 0;
        if (i <= 0 || (iArr = this.continuationIds) == null) {
            return;
        }
        iArr[i2] = i;
    }
}
