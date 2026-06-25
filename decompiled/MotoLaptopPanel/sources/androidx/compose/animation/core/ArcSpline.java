package androidx.compose.animation.core;

import kotlin.collections.ArraysKt;

/* JADX INFO: compiled from: ArcSpline.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ArcSpline {
    private final Arc[][] arcs;
    private final boolean isExtrapolate = true;

    /* JADX INFO: compiled from: ArcSpline.kt */
    public final class Arc {
        private float arcDistance;
        private final float arcVelocity;
        public final float ellipseA;
        public final float ellipseB;
        public final float ellipseCenterX;
        public final float ellipseCenterY;
        public final boolean isLinear;
        private final float[] lut;
        private final float oneOverDeltaTime;
        private final float time1;
        private final float time2;
        private final float vertical;
        private final float x1;
        private final float x2;
        private final float y1;
        private final float y2;

        public Arc(int i, float f, float f2, float f3, float f4, float f5, float f6) {
            this.time1 = f;
            this.time2 = f2;
            this.x1 = f3;
            this.y1 = f4;
            this.x2 = f5;
            this.y2 = f6;
            float f7 = f5 - f3;
            float f8 = f6 - f4;
            boolean z = true;
            boolean z2 = i == 1 || (i == 4 ? f8 > 0.0f : !(i != 5 || f8 >= 0.0f));
            float f9 = z2 ? -1.0f : 1.0f;
            this.vertical = f9;
            float f10 = 1 / (f2 - f);
            this.oneOverDeltaTime = f10;
            this.lut = new float[101];
            boolean z3 = i == 3;
            if (z3 || Math.abs(f7) < 0.001f || Math.abs(f8) < 0.001f) {
                float fHypot = (float) Math.hypot(f8, f7);
                this.arcDistance = fHypot;
                this.arcVelocity = fHypot * f10;
                this.ellipseCenterX = f7 * f10;
                this.ellipseCenterY = f8 * f10;
                this.ellipseA = Float.NaN;
                this.ellipseB = Float.NaN;
            } else {
                this.ellipseA = f7 * f9;
                this.ellipseB = f8 * (-f9);
                this.ellipseCenterX = z2 ? f5 : f3;
                this.ellipseCenterY = z2 ? f4 : f6;
                buildTable$animation_core_release(f3, f4, f5, f6);
                this.arcVelocity = this.arcDistance * f10;
                z = z3;
            }
            this.isLinear = z;
        }

        public final void buildTable$animation_core_release(float f, float f2, float f3, float f4) {
            float f5;
            float f6;
            float fHypot;
            float f7 = f3 - f;
            float f8 = f2 - f4;
            float[] fArr = ArcSplineKt.OurPercentCache;
            int length = fArr.length - 1;
            float f9 = length;
            float[] fArr2 = this.lut;
            if (1 <= length) {
                float f10 = f8;
                int i = 1;
                fHypot = 0.0f;
                float f11 = 0.0f;
                while (true) {
                    f6 = 0.0f;
                    double radians = (float) Math.toRadians((((double) i) * 90.0d) / ((double) length));
                    float fSin = ((float) Math.sin(radians)) * f7;
                    float fCos = ((float) Math.cos(radians)) * f8;
                    f5 = f9;
                    fHypot += (float) Math.hypot(fSin - f11, fCos - f10);
                    fArr[i] = fHypot;
                    if (i == length) {
                        break;
                    }
                    i++;
                    f10 = fCos;
                    f9 = f5;
                    f11 = fSin;
                }
            } else {
                f5 = f9;
                f6 = 0.0f;
                fHypot = 0.0f;
            }
            this.arcDistance = fHypot;
            if (1 <= length) {
                int i2 = 1;
                while (true) {
                    fArr[i2] = fArr[i2] / fHypot;
                    if (i2 == length) {
                        break;
                    } else {
                        i2++;
                    }
                }
            }
            int length2 = fArr2.length;
            for (int i3 = 0; i3 < length2; i3++) {
                float f12 = i3 / 100.0f;
                int iBinarySearch$default = ArraysKt.binarySearch$default(fArr, f12, 0, 0, 6, null);
                if (iBinarySearch$default >= 0) {
                    fArr2[i3] = iBinarySearch$default / f5;
                } else if (iBinarySearch$default == -1) {
                    fArr2[i3] = f6;
                } else {
                    int i4 = -iBinarySearch$default;
                    int i5 = i4 - 2;
                    float f13 = i5;
                    float f14 = fArr[i5];
                    fArr2[i3] = (f13 + ((f12 - f14) / (fArr[i4 - 1] - f14))) / f5;
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0028 A[PHI: r10
      0x0028: PHI (r10v1 int) = (r10v0 int), (r10v3 int), (r10v4 int) binds: [B:5:0x0018, B:10:0x0021, B:12:0x0024] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0031  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public ArcSpline(int[] r22, float[] r23, float[][] r24) {
        /*
            r21 = this;
            r0 = r21
            r1 = r23
            r0.<init>()
            r2 = 1
            r0.isExtrapolate = r2
            int r3 = r1.length
            int r3 = r3 - r2
            androidx.compose.animation.core.ArcSpline$Arc[][] r4 = new androidx.compose.animation.core.ArcSpline.Arc[r3][]
            r5 = 0
            r7 = r2
            r8 = r7
            r6 = r5
        L12:
            if (r6 >= r3) goto L69
            r9 = r22[r6]
            r10 = 3
            r11 = 2
            if (r9 == 0) goto L28
            if (r9 == r2) goto L31
            if (r9 == r11) goto L2f
            if (r9 == r10) goto L2a
            r10 = 4
            if (r9 == r10) goto L28
            r10 = 5
            if (r9 == r10) goto L28
            r13 = r8
            goto L33
        L28:
            r13 = r10
            goto L33
        L2a:
            if (r7 != r2) goto L31
            goto L2f
        L2d:
            r13 = r7
            goto L33
        L2f:
            r7 = r11
            goto L2d
        L31:
            r7 = r2
            goto L2d
        L33:
            r8 = r24[r6]
            int r9 = r6 + 1
            r10 = r24[r9]
            r14 = r1[r6]
            r15 = r1[r9]
            int r12 = r8.length
            int r12 = r12 / r11
            int r2 = r8.length
            int r2 = r2 % r11
            int r2 = r2 + r12
            androidx.compose.animation.core.ArcSpline$Arc[] r11 = new androidx.compose.animation.core.ArcSpline.Arc[r2]
            r12 = r5
        L45:
            if (r12 >= r2) goto L63
            int r16 = r12 * 2
            r17 = r12
            androidx.compose.animation.core.ArcSpline$Arc r12 = new androidx.compose.animation.core.ArcSpline$Arc
            r18 = r16
            r16 = r8[r18]
            int r19 = r18 + 1
            r20 = r17
            r17 = r8[r19]
            r18 = r10[r18]
            r19 = r10[r19]
            r12.<init>(r13, r14, r15, r16, r17, r18, r19)
            r11[r20] = r12
            int r12 = r20 + 1
            goto L45
        L63:
            r4[r6] = r11
            r6 = r9
            r8 = r13
            r2 = 1
            goto L12
        L69:
            r0.arcs = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.ArcSpline.<init>(int[], float[], float[][]):void");
    }
}
