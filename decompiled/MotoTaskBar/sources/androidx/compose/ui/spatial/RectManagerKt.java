package androidx.compose.ui.spatial;

import androidx.compose.ui.unit.IntOffset;

/* JADX INFO: compiled from: RectManager.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class RectManagerKt {
    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: analyzeComponents-58bKbWc, reason: not valid java name */
    public static final int m762analyzeComponents58bKbWc(float[] fArr) {
        int i = 0;
        if (fArr.length < 16) {
            return 0;
        }
        int i2 = (fArr[0] == 1.0f && fArr[1] == 0.0f && fArr[2] == 0.0f && fArr[4] == 0.0f && fArr[5] == 1.0f && fArr[6] == 0.0f && fArr[8] == 0.0f && fArr[9] == 0.0f && fArr[10] == 1.0f) ? 1 : 0;
        if (fArr[12] == 0.0f && fArr[13] == 0.0f && fArr[14] == 0.0f && fArr[15] == 1.0f) {
            i = 1;
        }
        return (i2 << 1) | i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: isSet--gyyYBs, reason: not valid java name */
    public static final boolean m763isSetgyyYBs(long j) {
        return !IntOffset.m996equalsimpl0(j, IntOffset.Companion.m1001getMaxnOccac());
    }
}
