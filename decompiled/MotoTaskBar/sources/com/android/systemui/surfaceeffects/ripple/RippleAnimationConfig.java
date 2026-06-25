package com.android.systemui.surfaceeffects.ripple;

import com.android.systemui.surfaceeffects.ripple.RippleShader;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RippleAnimationConfig.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RippleAnimationConfig {
    private final RippleShader.FadeParams baseRingFadeParams;
    private final RippleShader.FadeParams centerFillFadeParams;
    private final float centerX;
    private final float centerY;
    private int color;
    private final long duration;
    private final float maxHeight;
    private final float maxWidth;
    private final int opacity;
    private final float pixelDensity;
    private final RippleShader.RippleShape rippleShape;
    private final boolean shouldDistort;
    private final RippleShader.FadeParams sparkleRingFadeParams;
    private final float sparkleStrength;

    public RippleAnimationConfig(RippleShader.RippleShape rippleShape, long j, float f, float f2, float f3, float f4, float f5, int i, int i2, float f6, RippleShader.FadeParams fadeParams, RippleShader.FadeParams fadeParams2, RippleShader.FadeParams fadeParams3, boolean z) {
        rippleShape.getClass();
        this.rippleShape = rippleShape;
        this.duration = j;
        this.centerX = f;
        this.centerY = f2;
        this.maxWidth = f3;
        this.maxHeight = f4;
        this.pixelDensity = f5;
        this.color = i;
        this.opacity = i2;
        this.sparkleStrength = f6;
        this.baseRingFadeParams = fadeParams;
        this.sparkleRingFadeParams = fadeParams2;
        this.centerFillFadeParams = fadeParams3;
        this.shouldDistort = z;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RippleAnimationConfig)) {
            return false;
        }
        RippleAnimationConfig rippleAnimationConfig = (RippleAnimationConfig) obj;
        return this.rippleShape == rippleAnimationConfig.rippleShape && this.duration == rippleAnimationConfig.duration && Float.compare(this.centerX, rippleAnimationConfig.centerX) == 0 && Float.compare(this.centerY, rippleAnimationConfig.centerY) == 0 && Float.compare(this.maxWidth, rippleAnimationConfig.maxWidth) == 0 && Float.compare(this.maxHeight, rippleAnimationConfig.maxHeight) == 0 && Float.compare(this.pixelDensity, rippleAnimationConfig.pixelDensity) == 0 && this.color == rippleAnimationConfig.color && this.opacity == rippleAnimationConfig.opacity && Float.compare(this.sparkleStrength, rippleAnimationConfig.sparkleStrength) == 0 && Intrinsics.areEqual(this.baseRingFadeParams, rippleAnimationConfig.baseRingFadeParams) && Intrinsics.areEqual(this.sparkleRingFadeParams, rippleAnimationConfig.sparkleRingFadeParams) && Intrinsics.areEqual(this.centerFillFadeParams, rippleAnimationConfig.centerFillFadeParams) && this.shouldDistort == rippleAnimationConfig.shouldDistort;
    }

    public final RippleShader.FadeParams getBaseRingFadeParams() {
        return this.baseRingFadeParams;
    }

    public final RippleShader.FadeParams getCenterFillFadeParams() {
        return this.centerFillFadeParams;
    }

    public final float getCenterX() {
        return this.centerX;
    }

    public final float getCenterY() {
        return this.centerY;
    }

    public final int getColor() {
        return this.color;
    }

    public final long getDuration() {
        return this.duration;
    }

    public final float getMaxHeight() {
        return this.maxHeight;
    }

    public final float getMaxWidth() {
        return this.maxWidth;
    }

    public final int getOpacity() {
        return this.opacity;
    }

    public final float getPixelDensity() {
        return this.pixelDensity;
    }

    public final RippleShader.RippleShape getRippleShape() {
        return this.rippleShape;
    }

    public final boolean getShouldDistort() {
        return this.shouldDistort;
    }

    public final RippleShader.FadeParams getSparkleRingFadeParams() {
        return this.sparkleRingFadeParams;
    }

    public final float getSparkleStrength() {
        return this.sparkleStrength;
    }

    public int hashCode() {
        int iHashCode = ((((((((((((((((((this.rippleShape.hashCode() * 31) + Long.hashCode(this.duration)) * 31) + Float.hashCode(this.centerX)) * 31) + Float.hashCode(this.centerY)) * 31) + Float.hashCode(this.maxWidth)) * 31) + Float.hashCode(this.maxHeight)) * 31) + Float.hashCode(this.pixelDensity)) * 31) + Integer.hashCode(this.color)) * 31) + Integer.hashCode(this.opacity)) * 31) + Float.hashCode(this.sparkleStrength)) * 31;
        RippleShader.FadeParams fadeParams = this.baseRingFadeParams;
        int iHashCode2 = (iHashCode + (fadeParams == null ? 0 : fadeParams.hashCode())) * 31;
        RippleShader.FadeParams fadeParams2 = this.sparkleRingFadeParams;
        int iHashCode3 = (iHashCode2 + (fadeParams2 == null ? 0 : fadeParams2.hashCode())) * 31;
        RippleShader.FadeParams fadeParams3 = this.centerFillFadeParams;
        return ((iHashCode3 + (fadeParams3 != null ? fadeParams3.hashCode() : 0)) * 31) + Boolean.hashCode(this.shouldDistort);
    }

    public final void setColor(int i) {
        this.color = i;
    }

    public String toString() {
        return "RippleAnimationConfig(rippleShape=" + this.rippleShape + ", duration=" + this.duration + ", centerX=" + this.centerX + ", centerY=" + this.centerY + ", maxWidth=" + this.maxWidth + ", maxHeight=" + this.maxHeight + ", pixelDensity=" + this.pixelDensity + ", color=" + this.color + ", opacity=" + this.opacity + ", sparkleStrength=" + this.sparkleStrength + ", baseRingFadeParams=" + this.baseRingFadeParams + ", sparkleRingFadeParams=" + this.sparkleRingFadeParams + ", centerFillFadeParams=" + this.centerFillFadeParams + ", shouldDistort=" + this.shouldDistort + ")";
    }
}
