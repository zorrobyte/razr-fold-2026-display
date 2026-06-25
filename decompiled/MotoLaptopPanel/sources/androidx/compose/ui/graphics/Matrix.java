package androidx.compose.ui.graphics;

import androidx.compose.ui.geometry.MutableRect;
import androidx.compose.ui.geometry.Offset;
import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: Matrix.kt */
/* JADX INFO: loaded from: classes.dex */
public final class Matrix {
    public static final Companion Companion = new Companion(null);
    private final float[] values;

    /* JADX INFO: compiled from: Matrix.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private /* synthetic */ Matrix(float[] fArr) {
        this.values = fArr;
    }

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ Matrix m939boximpl(float[] fArr) {
        return new Matrix(fArr);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static float[] m940constructorimpl(float[] fArr) {
        return fArr;
    }

    /* JADX INFO: renamed from: constructor-impl$default, reason: not valid java name */
    public static /* synthetic */ float[] m941constructorimpl$default(float[] fArr, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            fArr = new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
        }
        return m940constructorimpl(fArr);
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m942equalsimpl(float[] fArr, Object obj) {
        return (obj instanceof Matrix) && Intrinsics.areEqual(fArr, ((Matrix) obj).m954unboximpl());
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m943hashCodeimpl(float[] fArr) {
        return Arrays.hashCode(fArr);
    }

    /* JADX INFO: renamed from: map-MK-Hz9U, reason: not valid java name */
    public static final long m944mapMKHz9U(float[] fArr, long j) {
        if (fArr.length < 16) {
            return j;
        }
        float f = fArr[0];
        float f2 = fArr[1];
        float f3 = fArr[3];
        float f4 = fArr[4];
        float f5 = fArr[5];
        float f6 = fArr[7];
        float f7 = fArr[12];
        float f8 = fArr[13];
        float f9 = fArr[15];
        float fIntBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L));
        float f10 = 1 / (((f3 * fIntBitsToFloat) + (f6 * fIntBitsToFloat2)) + f9);
        if ((Float.floatToRawIntBits(f10) & Integer.MAX_VALUE) >= 2139095040) {
            f10 = 0.0f;
        }
        return Offset.m752constructorimpl((((long) Float.floatToRawIntBits((((f * fIntBitsToFloat) + (f4 * fIntBitsToFloat2)) + f7) * f10)) << 32) | (((long) Float.floatToRawIntBits(f10 * ((f2 * fIntBitsToFloat) + (f5 * fIntBitsToFloat2) + f8))) & 4294967295L));
    }

    /* JADX INFO: renamed from: map-impl, reason: not valid java name */
    public static final void m945mapimpl(float[] fArr, MutableRect mutableRect) {
        if (fArr.length < 16) {
            return;
        }
        float f = fArr[0];
        float f2 = fArr[1];
        float f3 = fArr[3];
        float f4 = fArr[4];
        float f5 = fArr[5];
        float f6 = fArr[7];
        float f7 = fArr[12];
        float f8 = fArr[13];
        float f9 = fArr[15];
        float left = mutableRect.getLeft();
        float top = mutableRect.getTop();
        float right = mutableRect.getRight();
        float bottom = mutableRect.getBottom();
        float f10 = f3 * left;
        float f11 = f6 * top;
        float f12 = 1.0f / ((f10 + f11) + f9);
        if ((Float.floatToRawIntBits(f12) & Integer.MAX_VALUE) >= 2139095040) {
            f12 = 0.0f;
        }
        float f13 = f * left;
        float f14 = f4 * top;
        float f15 = f12 * (f13 + f14 + f7);
        float f16 = left * f2;
        float f17 = top * f5;
        float f18 = f12 * (f16 + f17 + f8);
        float f19 = f6 * bottom;
        float f20 = 1.0f / ((f10 + f19) + f9);
        if ((Float.floatToRawIntBits(f20) & Integer.MAX_VALUE) >= 2139095040) {
            f20 = 0.0f;
        }
        float f21 = f4 * bottom;
        float f22 = (f13 + f21 + f7) * f20;
        float f23 = f5 * bottom;
        float f24 = f20 * (f16 + f23 + f8);
        float f25 = f3 * right;
        float f26 = 1.0f / ((f11 + f25) + f9);
        if ((Float.floatToRawIntBits(f26) & Integer.MAX_VALUE) >= 2139095040) {
            f26 = 0.0f;
        }
        float f27 = f * right;
        float f28 = f26 * (f27 + f14 + f7);
        float f29 = right * f2;
        float f30 = f26 * (f17 + f29 + f8);
        float f31 = 1.0f / ((f25 + f19) + f9);
        float f32 = (Float.floatToRawIntBits(f31) & Integer.MAX_VALUE) < 2139095040 ? f31 : 0.0f;
        float f33 = (f27 + f21 + f7) * f32;
        float f34 = f32 * (f29 + f23 + f8);
        mutableRect.setLeft(Math.min(f15, Math.min(f22, Math.min(f28, f33))));
        mutableRect.setTop(Math.min(f18, Math.min(f24, Math.min(f30, f34))));
        mutableRect.setRight(Math.max(f15, Math.max(f22, Math.max(f28, f33))));
        mutableRect.setBottom(Math.max(f18, Math.max(f24, Math.max(f30, f34))));
    }

    /* JADX INFO: renamed from: reset-impl, reason: not valid java name */
    public static final void m946resetimpl(float[] fArr) {
        if (fArr.length < 16) {
            return;
        }
        fArr[0] = 1.0f;
        fArr[1] = 0.0f;
        fArr[2] = 0.0f;
        fArr[3] = 0.0f;
        fArr[4] = 0.0f;
        fArr[5] = 1.0f;
        fArr[6] = 0.0f;
        fArr[7] = 0.0f;
        fArr[8] = 0.0f;
        fArr[9] = 0.0f;
        fArr[10] = 1.0f;
        fArr[11] = 0.0f;
        fArr[12] = 0.0f;
        fArr[13] = 0.0f;
        fArr[14] = 0.0f;
        fArr[15] = 1.0f;
    }

    /* JADX INFO: renamed from: resetToPivotedTransform-impl, reason: not valid java name */
    public static final void m947resetToPivotedTransformimpl(float[] fArr, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11) {
        double d = ((double) f6) * 0.017453292519943295d;
        float fSin = (float) Math.sin(d);
        float fCos = (float) Math.cos(d);
        float f12 = -fSin;
        float f13 = (f4 * fCos) - (f5 * fSin);
        float f14 = (f4 * fSin) + (f5 * fCos);
        double d2 = ((double) f7) * 0.017453292519943295d;
        float fSin2 = (float) Math.sin(d2);
        float fCos2 = (float) Math.cos(d2);
        float f15 = -fSin2;
        float f16 = fSin * fSin2;
        float f17 = fSin * fCos2;
        float f18 = fCos * fSin2;
        float f19 = fCos * fCos2;
        float f20 = (f3 * fCos2) + (f14 * fSin2);
        float f21 = ((-f3) * fSin2) + (f14 * fCos2);
        double d3 = ((double) f8) * 0.017453292519943295d;
        float fSin3 = (float) Math.sin(d3);
        float fCos3 = (float) Math.cos(d3);
        float f22 = -fSin3;
        float f23 = (f22 * fCos2) + (fCos3 * f16);
        float f24 = fCos * fCos3;
        float f25 = (f22 * f15) + (fCos3 * f17);
        float f26 = ((fCos2 * fCos3) + (f16 * fSin3)) * f9;
        float f27 = fSin3 * fCos * f9;
        float f28 = ((fCos3 * f15) + (fSin3 * f17)) * f9;
        float f29 = f23 * f10;
        float f30 = f24 * f10;
        float f31 = f25 * f10;
        float f32 = f18 * f11;
        float f33 = f12 * f11;
        float f34 = f19 * f11;
        if (fArr.length < 16) {
            return;
        }
        fArr[0] = f26;
        fArr[1] = f27;
        fArr[2] = f28;
        fArr[3] = 0.0f;
        fArr[4] = f29;
        fArr[5] = f30;
        fArr[6] = f31;
        fArr[7] = 0.0f;
        fArr[8] = f32;
        fArr[9] = f33;
        fArr[10] = f34;
        fArr[11] = 0.0f;
        float f35 = -f;
        fArr[12] = ((f26 * f35) - (f29 * f2)) + f20 + f;
        fArr[13] = ((f27 * f35) - (f30 * f2)) + f13 + f2;
        fArr[14] = ((f35 * f28) - (f2 * f31)) + f21;
        fArr[15] = 1.0f;
    }

    /* JADX INFO: renamed from: rotateZ-impl, reason: not valid java name */
    public static final void m948rotateZimpl(float[] fArr, float f) {
        if (fArr.length < 16) {
            return;
        }
        double d = ((double) f) * 0.017453292519943295d;
        float fSin = (float) Math.sin(d);
        float fCos = (float) Math.cos(d);
        float f2 = fArr[0];
        float f3 = fArr[4];
        float f4 = (fCos * f2) + (fSin * f3);
        float f5 = -fSin;
        float f6 = fArr[1];
        float f7 = fArr[5];
        float f8 = (fCos * f6) + (fSin * f7);
        float f9 = fArr[2];
        float f10 = fArr[6];
        float f11 = (fCos * f9) + (fSin * f10);
        float f12 = fArr[3];
        float f13 = fArr[7];
        fArr[0] = f4;
        fArr[1] = f8;
        fArr[2] = f11;
        fArr[3] = (fCos * f12) + (fSin * f13);
        fArr[4] = (f2 * f5) + (f3 * fCos);
        fArr[5] = (f6 * f5) + (f7 * fCos);
        fArr[6] = (f9 * f5) + (f10 * fCos);
        fArr[7] = (f5 * f12) + (fCos * f13);
    }

    /* JADX INFO: renamed from: scale-impl, reason: not valid java name */
    public static final void m949scaleimpl(float[] fArr, float f, float f2, float f3) {
        if (fArr.length < 16) {
            return;
        }
        fArr[0] = fArr[0] * f;
        fArr[1] = fArr[1] * f;
        fArr[2] = fArr[2] * f;
        fArr[3] = fArr[3] * f;
        fArr[4] = fArr[4] * f2;
        fArr[5] = fArr[5] * f2;
        fArr[6] = fArr[6] * f2;
        fArr[7] = fArr[7] * f2;
        fArr[8] = fArr[8] * f3;
        fArr[9] = fArr[9] * f3;
        fArr[10] = fArr[10] * f3;
        fArr[11] = fArr[11] * f3;
    }

    /* JADX INFO: renamed from: timesAssign-58bKbWc, reason: not valid java name */
    public static final void m950timesAssign58bKbWc(float[] fArr, float[] fArr2) {
        if (fArr.length >= 16 && fArr2.length >= 16) {
            float f = fArr[0];
            float f2 = fArr2[0];
            float f3 = fArr[1];
            float f4 = fArr2[4];
            float f5 = fArr[2];
            float f6 = fArr2[8];
            float f7 = fArr[3];
            float f8 = fArr2[12];
            float f9 = (f * f2) + (f3 * f4) + (f5 * f6) + (f7 * f8);
            float f10 = fArr2[1];
            float f11 = fArr2[5];
            float f12 = fArr2[9];
            float f13 = fArr2[13];
            float f14 = (f * f10) + (f3 * f11) + (f5 * f12) + (f7 * f13);
            float f15 = fArr2[2];
            float f16 = fArr2[6];
            float f17 = fArr2[10];
            float f18 = fArr2[14];
            float f19 = (f * f15) + (f3 * f16) + (f5 * f17) + (f7 * f18);
            float f20 = fArr2[3];
            float f21 = fArr2[7];
            float f22 = fArr2[11];
            float f23 = fArr2[15];
            float f24 = (f * f20) + (f3 * f21) + (f5 * f22) + (f7 * f23);
            float f25 = fArr[4];
            float f26 = fArr[5];
            float f27 = fArr[6];
            float f28 = fArr[7];
            float f29 = (f25 * f2) + (f26 * f4) + (f27 * f6) + (f28 * f8);
            float f30 = (f25 * f10) + (f26 * f11) + (f27 * f12) + (f28 * f13);
            float f31 = (f25 * f15) + (f26 * f16) + (f27 * f17) + (f28 * f18);
            float f32 = (f25 * f20) + (f26 * f21) + (f27 * f22) + (f28 * f23);
            float f33 = fArr[8];
            float f34 = fArr[9];
            float f35 = fArr[10];
            float f36 = fArr[11];
            float f37 = (f33 * f2) + (f34 * f4) + (f35 * f6) + (f36 * f8);
            float f38 = (f33 * f10) + (f34 * f11) + (f35 * f12) + (f36 * f13);
            float f39 = (f33 * f15) + (f34 * f16) + (f35 * f17) + (f36 * f18);
            float f40 = (f33 * f20) + (f34 * f21) + (f35 * f22) + (f36 * f23);
            float f41 = fArr[12];
            float f42 = fArr[13];
            float f43 = (f2 * f41) + (f4 * f42);
            float f44 = fArr[14];
            float f45 = f43 + (f6 * f44);
            float f46 = fArr[15];
            fArr[0] = f9;
            fArr[1] = f14;
            fArr[2] = f19;
            fArr[3] = f24;
            fArr[4] = f29;
            fArr[5] = f30;
            fArr[6] = f31;
            fArr[7] = f32;
            fArr[8] = f37;
            fArr[9] = f38;
            fArr[10] = f39;
            fArr[11] = f40;
            fArr[12] = f45 + (f8 * f46);
            fArr[13] = (f10 * f41) + (f11 * f42) + (f12 * f44) + (f13 * f46);
            fArr[14] = (f15 * f41) + (f16 * f42) + (f17 * f44) + (f18 * f46);
            fArr[15] = (f41 * f20) + (f42 * f21) + (f44 * f22) + (f46 * f23);
        }
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m951toStringimpl(float[] fArr) {
        return StringsKt.trimIndent("\n            |" + fArr[0] + ' ' + fArr[1] + ' ' + fArr[2] + ' ' + fArr[3] + "|\n            |" + fArr[4] + ' ' + fArr[5] + ' ' + fArr[6] + ' ' + fArr[7] + "|\n            |" + fArr[8] + ' ' + fArr[9] + ' ' + fArr[10] + ' ' + fArr[11] + "|\n            |" + fArr[12] + ' ' + fArr[13] + ' ' + fArr[14] + ' ' + fArr[15] + "|\n        ");
    }

    /* JADX INFO: renamed from: translate-impl, reason: not valid java name */
    public static final void m952translateimpl(float[] fArr, float f, float f2, float f3) {
        if (fArr.length < 16) {
            return;
        }
        float f4 = (fArr[0] * f) + (fArr[4] * f2) + (fArr[8] * f3) + fArr[12];
        float f5 = (fArr[1] * f) + (fArr[5] * f2) + (fArr[9] * f3) + fArr[13];
        float f6 = (fArr[2] * f) + (fArr[6] * f2) + (fArr[10] * f3) + fArr[14];
        float f7 = (fArr[3] * f) + (fArr[7] * f2) + (fArr[11] * f3) + fArr[15];
        fArr[12] = f4;
        fArr[13] = f5;
        fArr[14] = f6;
        fArr[15] = f7;
    }

    /* JADX INFO: renamed from: translate-impl$default, reason: not valid java name */
    public static /* synthetic */ void m953translateimpl$default(float[] fArr, float f, float f2, float f3, int i, Object obj) {
        if ((i & 1) != 0) {
            f = 0.0f;
        }
        if ((i & 2) != 0) {
            f2 = 0.0f;
        }
        if ((i & 4) != 0) {
            f3 = 0.0f;
        }
        m952translateimpl(fArr, f, f2, f3);
    }

    public boolean equals(Object obj) {
        return m942equalsimpl(this.values, obj);
    }

    public int hashCode() {
        return m943hashCodeimpl(this.values);
    }

    public String toString() {
        return m951toStringimpl(this.values);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ float[] m954unboximpl() {
        return this.values;
    }
}
