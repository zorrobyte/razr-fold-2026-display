package androidx.compose.animation.core;

/* JADX INFO: compiled from: SpringSimulation.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SpringSimulation {
    private float finalPosition;
    private double naturalFreq = Math.sqrt(50.0d);
    private float dampingRatio = 1.0f;

    public SpringSimulation(float f) {
        this.finalPosition = f;
    }

    public final float getDampingRatio() {
        return this.dampingRatio;
    }

    public final float getStiffness() {
        double d = this.naturalFreq;
        return (float) (d * d);
    }

    public final void setDampingRatio(float f) {
        if (f < 0.0f) {
            PreconditionsKt.throwIllegalArgumentException("Damping ratio must be non-negative");
        }
        this.dampingRatio = f;
    }

    public final void setFinalPosition(float f) {
        this.finalPosition = f;
    }

    public final void setStiffness(float f) {
        if (getStiffness() <= 0.0f) {
            PreconditionsKt.throwIllegalArgumentException("Spring stiffness constant must be positive.");
        }
        this.naturalFreq = Math.sqrt(f);
    }

    /* JADX INFO: renamed from: updateValues-IJZedt4$animation_core_release, reason: not valid java name */
    public final long m48updateValuesIJZedt4$animation_core_release(float f, float f2, long j) {
        double dExp;
        double dExp2;
        float f3 = f - this.finalPosition;
        double d = j / 1000.0d;
        float f4 = this.dampingRatio;
        double d2 = ((double) f4) * ((double) f4);
        double d3 = this.naturalFreq;
        double d4 = ((double) (-f4)) * d3;
        if (f4 > 1.0f) {
            double dSqrt = d3 * Math.sqrt(d2 - ((double) 1));
            double d5 = d4 + dSqrt;
            double d6 = d4 - dSqrt;
            double d7 = f3;
            double d8 = ((d6 * d7) - ((double) f2)) / (d6 - d5);
            double d9 = d7 - d8;
            double d10 = d6 * d;
            double d11 = d * d5;
            dExp2 = (Math.exp(d10) * d9) + (Math.exp(d11) * d8);
            dExp = (d9 * d6 * Math.exp(d10)) + (d8 * d5 * Math.exp(d11));
        } else if (f4 == 1.0f) {
            double d12 = f3;
            double d13 = ((double) f2) + (d3 * d12);
            double d14 = (-d3) * d;
            double d15 = d12 + (d * d13);
            dExp2 = d15 * Math.exp(d14);
            dExp = (d15 * Math.exp(d14) * (-this.naturalFreq)) + (d13 * Math.exp(d14));
        } else {
            double d16 = 1;
            double dSqrt2 = d3 * Math.sqrt(d16 - d2);
            double d17 = f3;
            double d18 = (d16 / dSqrt2) * (((-d4) * d17) + ((double) f2));
            double d19 = dSqrt2 * d;
            double d20 = d * d4;
            double dExp3 = Math.exp(d20) * ((Math.cos(d19) * d17) + (Math.sin(d19) * d18));
            dExp = (d4 * dExp3) + (Math.exp(d20) * (((-dSqrt2) * d17 * Math.sin(d19)) + (dSqrt2 * d18 * Math.cos(d19))));
            dExp2 = dExp3;
        }
        return Motion.m47constructorimpl((((long) Float.floatToRawIntBits((float) dExp)) & 4294967295L) | (Float.floatToRawIntBits((float) (dExp2 + ((double) this.finalPosition))) << 32));
    }
}
