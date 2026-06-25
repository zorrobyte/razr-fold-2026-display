package androidx.compose.ui.unit;

import androidx.compose.ui.geometry.Size;

/* JADX INFO: compiled from: Density.kt */
/* JADX INFO: loaded from: classes.dex */
public interface Density extends FontScaling {
    float getDensity();

    /* JADX INFO: renamed from: roundToPx-0680j_4 */
    default int mo141roundToPx0680j_4(float f) {
        float fMo146toPx0680j_4 = mo146toPx0680j_4(f);
        if (Float.isInfinite(fMo146toPx0680j_4)) {
            return Integer.MAX_VALUE;
        }
        return Math.round(fMo146toPx0680j_4);
    }

    /* JADX INFO: renamed from: toDp-u2uoSUM */
    default float mo143toDpu2uoSUM(float f) {
        return Dp.m1877constructorimpl(f / getDensity());
    }

    /* JADX INFO: renamed from: toDp-u2uoSUM */
    default float mo144toDpu2uoSUM(int i) {
        return Dp.m1877constructorimpl(i / getDensity());
    }

    /* JADX INFO: renamed from: toPx--R2X_6o */
    default float mo145toPxR2X_6o(long j) {
        if (!TextUnitType.m1947equalsimpl0(TextUnit.m1936getTypeUIouoOA(j), TextUnitType.Companion.m1952getSpUIouoOA())) {
            InlineClassHelperKt.throwIllegalStateException("Only Sp can convert to Px");
        }
        return mo146toPx0680j_4(mo142toDpGaN1DYA(j));
    }

    /* JADX INFO: renamed from: toPx-0680j_4 */
    default float mo146toPx0680j_4(float f) {
        return f * getDensity();
    }

    /* JADX INFO: renamed from: toSize-XkaWNTQ */
    default long mo147toSizeXkaWNTQ(long j) {
        if (j == 9205357640488583168L) {
            return Size.Companion.m793getUnspecifiedNHjbRc();
        }
        float fMo146toPx0680j_4 = mo146toPx0680j_4(DpSize.m1899getWidthD9Ej5fM(j));
        float fMo146toPx0680j_42 = mo146toPx0680j_4(DpSize.m1898getHeightD9Ej5fM(j));
        return Size.m783constructorimpl((((long) Float.floatToRawIntBits(fMo146toPx0680j_4)) << 32) | (((long) Float.floatToRawIntBits(fMo146toPx0680j_42)) & 4294967295L));
    }

    /* JADX INFO: renamed from: toSp-kPz2Gy4 */
    default long mo149toSpkPz2Gy4(float f) {
        return mo148toSp0xMU5do(mo143toDpu2uoSUM(f));
    }
}
