package androidx.compose.ui.unit;

import androidx.compose.ui.geometry.Size;

/* JADX INFO: compiled from: Density.kt */
/* JADX INFO: loaded from: classes.dex */
public interface Density extends FontScaling {
    float getDensity();

    /* JADX INFO: renamed from: toPx--R2X_6o */
    default float mo528toPxR2X_6o(long j) {
        if (!TextUnitType.m1032equalsimpl0(TextUnit.m1022getTypeUIouoOA(j), TextUnitType.Companion.m1037getSpUIouoOA())) {
            InlineClassHelperKt.throwIllegalStateException("Only Sp can convert to Px");
        }
        return mo529toPx0680j_4(mo527toDpGaN1DYA(j));
    }

    /* JADX INFO: renamed from: toPx-0680j_4 */
    default float mo529toPx0680j_4(float f) {
        return f * getDensity();
    }

    /* JADX INFO: renamed from: toSize-XkaWNTQ */
    default long mo530toSizeXkaWNTQ(long j) {
        if (j == 9205357640488583168L) {
            return Size.Companion.m210getUnspecifiedNHjbRc();
        }
        float fMo529toPx0680j_4 = mo529toPx0680j_4(DpSize.m993getWidthD9Ej5fM(j));
        float fMo529toPx0680j_42 = mo529toPx0680j_4(DpSize.m992getHeightD9Ej5fM(j));
        return Size.m206constructorimpl((((long) Float.floatToRawIntBits(fMo529toPx0680j_4)) << 32) | (((long) Float.floatToRawIntBits(fMo529toPx0680j_42)) & 4294967295L));
    }
}
