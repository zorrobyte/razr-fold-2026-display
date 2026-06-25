package com.android.systemui.plugins.clocks;

/* JADX INFO: compiled from: ClockFaceLayout.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AodClockBurnInModel {
    public static final int $stable = 0;
    private final float scale;
    private final float translationX;
    private final float translationY;

    public AodClockBurnInModel(float f, float f2, float f3) {
        this.scale = f;
        this.translationX = f2;
        this.translationY = f3;
    }

    public static /* synthetic */ AodClockBurnInModel copy$default(AodClockBurnInModel aodClockBurnInModel, float f, float f2, float f3, int i, Object obj) {
        if ((i & 1) != 0) {
            f = aodClockBurnInModel.scale;
        }
        if ((i & 2) != 0) {
            f2 = aodClockBurnInModel.translationX;
        }
        if ((i & 4) != 0) {
            f3 = aodClockBurnInModel.translationY;
        }
        return aodClockBurnInModel.copy(f, f2, f3);
    }

    public final float component1() {
        return this.scale;
    }

    public final float component2() {
        return this.translationX;
    }

    public final float component3() {
        return this.translationY;
    }

    public final AodClockBurnInModel copy(float f, float f2, float f3) {
        return new AodClockBurnInModel(f, f2, f3);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AodClockBurnInModel)) {
            return false;
        }
        AodClockBurnInModel aodClockBurnInModel = (AodClockBurnInModel) obj;
        return Float.compare(this.scale, aodClockBurnInModel.scale) == 0 && Float.compare(this.translationX, aodClockBurnInModel.translationX) == 0 && Float.compare(this.translationY, aodClockBurnInModel.translationY) == 0;
    }

    public final float getScale() {
        return this.scale;
    }

    public final float getTranslationX() {
        return this.translationX;
    }

    public final float getTranslationY() {
        return this.translationY;
    }

    public int hashCode() {
        return (((Float.hashCode(this.scale) * 31) + Float.hashCode(this.translationX)) * 31) + Float.hashCode(this.translationY);
    }

    public String toString() {
        return "AodClockBurnInModel(scale=" + this.scale + ", translationX=" + this.translationX + ", translationY=" + this.translationY + ")";
    }
}
