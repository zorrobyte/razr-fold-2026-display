package androidx.compose.ui.spatial;

import android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.collection.IntObjectMapKt;
import androidx.collection.MutableIntObjectMap;
import androidx.compose.ui.unit.IntOffset;

/* JADX INFO: compiled from: ThrottledCallbacks.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ThrottledCallbacks {
    private long screenOffset;
    private float[] viewToWindowMatrix;
    private long windowOffset;
    private final MutableIntObjectMap rectChangedMap = IntObjectMapKt.mutableIntObjectMapOf();
    private long minDebounceDeadline = -1;

    public ThrottledCallbacks() {
        IntOffset.Companion companion = IntOffset.Companion;
        this.windowOffset = companion.m1913getZeronOccac();
        this.screenOffset = companion.m1913getZeronOccac();
    }

    public final void fireGlobalChangeEntries(long j) {
    }

    public final void fireOnRectChangedEntries(long j) {
        MutableIntObjectMap mutableIntObjectMap = this.rectChangedMap;
        Object[] objArr = mutableIntObjectMap.values;
        long[] jArr = mutableIntObjectMap.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return;
        }
        int i = 0;
        while (true) {
            long j2 = jArr[i];
            if ((((~j2) << 7) & j2 & (-9187201950435737472L)) != -9187201950435737472L) {
                int i2 = 8 - ((~(i - length)) >>> 31);
                for (int i3 = 0; i3 < i2; i3++) {
                    if ((255 & j2) < 128) {
                        MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(objArr[(i << 3) + i3]);
                    }
                    j2 >>= 8;
                }
                if (i2 != 8) {
                    return;
                }
            }
            if (i == length) {
                return;
            } else {
                i++;
            }
        }
    }

    public final void fireOnUpdatedRect(int i, long j, long j2, long j3) {
        MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(this.rectChangedMap.get(i));
    }

    public final long getMinDebounceDeadline() {
        return this.minDebounceDeadline;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0047  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void triggerDebounced(long r13) {
        /*
            r12 = this;
            long r0 = r12.minDebounceDeadline
            int r13 = (r0 > r13 ? 1 : (r0 == r13 ? 0 : -1))
            if (r13 <= 0) goto L7
            return
        L7:
            androidx.collection.MutableIntObjectMap r13 = r12.rectChangedMap
            java.lang.Object[] r14 = r13.values
            long[] r13 = r13.metadata
            int r0 = r13.length
            int r0 = r0 + (-2)
            if (r0 < 0) goto L4c
            r1 = 0
            r2 = r1
        L14:
            r3 = r13[r2]
            long r5 = ~r3
            r7 = 7
            long r5 = r5 << r7
            long r5 = r5 & r3
            r7 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r5 = r5 & r7
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 == 0) goto L47
            int r5 = r2 - r0
            int r5 = ~r5
            int r5 = r5 >>> 31
            r6 = 8
            int r5 = 8 - r5
            r7 = r1
        L2e:
            if (r7 >= r5) goto L45
            r8 = 255(0xff, double:1.26E-321)
            long r8 = r8 & r3
            r10 = 128(0x80, double:6.32E-322)
            int r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r8 >= 0) goto L41
            int r8 = r2 << 3
            int r8 = r8 + r7
            r8 = r14[r8]
            android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(r8)
        L41:
            long r3 = r3 >> r6
            int r7 = r7 + 1
            goto L2e
        L45:
            if (r5 != r6) goto L4c
        L47:
            if (r2 == r0) goto L4c
            int r2 = r2 + 1
            goto L14
        L4c:
            r13 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            int r0 = (r13 > r13 ? 1 : (r13 == r13 ? 0 : -1))
            if (r0 != 0) goto L57
            r13 = -1
        L57:
            r12.minDebounceDeadline = r13
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.spatial.ThrottledCallbacks.triggerDebounced(long):void");
    }

    /* JADX INFO: renamed from: updateOffsets-bT0EZQs, reason: not valid java name */
    public final boolean m1513updateOffsetsbT0EZQs(long j, long j2, float[] fArr) {
        boolean z;
        if (IntOffset.m1904equalsimpl0(j2, this.windowOffset)) {
            z = false;
        } else {
            this.windowOffset = j2;
            z = true;
        }
        if (!IntOffset.m1904equalsimpl0(j, this.screenOffset)) {
            this.screenOffset = j;
            z = true;
        }
        if (fArr == null) {
            return z;
        }
        this.viewToWindowMatrix = fArr;
        return true;
    }
}
