package com.android.systemui.surfaceeffects.turbulencenoise;

import java.util.Random;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: TurbulenceNoiseAnimationConfig.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TurbulenceNoiseAnimationConfig {
    public static final Companion Companion = new Companion(null);
    private static final Random random = new Random();
    private final int color;
    private final float easeInDuration;
    private final float easeOutDuration;
    private final float gridCount;
    private final float height;
    private final float lumaMatteBlendFactor;
    private final float lumaMatteOverallBrightness;
    private final float luminosityMultiplier;
    private final float maxDuration;
    private final float noiseMoveSpeedX;
    private final float noiseMoveSpeedY;
    private final float noiseMoveSpeedZ;
    private final float noiseOffsetX;
    private final float noiseOffsetY;
    private final float noiseOffsetZ;
    private final float pixelDensity;
    private final int screenColor;
    private final boolean shouldInverseNoiseLuminosity;
    private final float width;

    /* JADX INFO: compiled from: TurbulenceNoiseAnimationConfig.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public TurbulenceNoiseAnimationConfig(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, int i, int i2, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16, boolean z) {
        this.gridCount = f;
        this.luminosityMultiplier = f2;
        this.noiseOffsetX = f3;
        this.noiseOffsetY = f4;
        this.noiseOffsetZ = f5;
        this.noiseMoveSpeedX = f6;
        this.noiseMoveSpeedY = f7;
        this.noiseMoveSpeedZ = f8;
        this.color = i;
        this.screenColor = i2;
        this.width = f9;
        this.height = f10;
        this.maxDuration = f11;
        this.easeInDuration = f12;
        this.easeOutDuration = f13;
        this.pixelDensity = f14;
        this.lumaMatteBlendFactor = f15;
        this.lumaMatteOverallBrightness = f16;
        this.shouldInverseNoiseLuminosity = z;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TurbulenceNoiseAnimationConfig)) {
            return false;
        }
        TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig = (TurbulenceNoiseAnimationConfig) obj;
        return Float.compare(this.gridCount, turbulenceNoiseAnimationConfig.gridCount) == 0 && Float.compare(this.luminosityMultiplier, turbulenceNoiseAnimationConfig.luminosityMultiplier) == 0 && Float.compare(this.noiseOffsetX, turbulenceNoiseAnimationConfig.noiseOffsetX) == 0 && Float.compare(this.noiseOffsetY, turbulenceNoiseAnimationConfig.noiseOffsetY) == 0 && Float.compare(this.noiseOffsetZ, turbulenceNoiseAnimationConfig.noiseOffsetZ) == 0 && Float.compare(this.noiseMoveSpeedX, turbulenceNoiseAnimationConfig.noiseMoveSpeedX) == 0 && Float.compare(this.noiseMoveSpeedY, turbulenceNoiseAnimationConfig.noiseMoveSpeedY) == 0 && Float.compare(this.noiseMoveSpeedZ, turbulenceNoiseAnimationConfig.noiseMoveSpeedZ) == 0 && this.color == turbulenceNoiseAnimationConfig.color && this.screenColor == turbulenceNoiseAnimationConfig.screenColor && Float.compare(this.width, turbulenceNoiseAnimationConfig.width) == 0 && Float.compare(this.height, turbulenceNoiseAnimationConfig.height) == 0 && Float.compare(this.maxDuration, turbulenceNoiseAnimationConfig.maxDuration) == 0 && Float.compare(this.easeInDuration, turbulenceNoiseAnimationConfig.easeInDuration) == 0 && Float.compare(this.easeOutDuration, turbulenceNoiseAnimationConfig.easeOutDuration) == 0 && Float.compare(this.pixelDensity, turbulenceNoiseAnimationConfig.pixelDensity) == 0 && Float.compare(this.lumaMatteBlendFactor, turbulenceNoiseAnimationConfig.lumaMatteBlendFactor) == 0 && Float.compare(this.lumaMatteOverallBrightness, turbulenceNoiseAnimationConfig.lumaMatteOverallBrightness) == 0 && this.shouldInverseNoiseLuminosity == turbulenceNoiseAnimationConfig.shouldInverseNoiseLuminosity;
    }

    public final int getColor() {
        return this.color;
    }

    public final float getEaseInDuration() {
        return this.easeInDuration;
    }

    public final float getEaseOutDuration() {
        return this.easeOutDuration;
    }

    public final float getGridCount() {
        return this.gridCount;
    }

    public final float getHeight() {
        return this.height;
    }

    public final float getLumaMatteBlendFactor() {
        return this.lumaMatteBlendFactor;
    }

    public final float getLumaMatteOverallBrightness() {
        return this.lumaMatteOverallBrightness;
    }

    public final float getLuminosityMultiplier() {
        return this.luminosityMultiplier;
    }

    public final float getMaxDuration() {
        return this.maxDuration;
    }

    public final float getNoiseMoveSpeedX() {
        return this.noiseMoveSpeedX;
    }

    public final float getNoiseMoveSpeedY() {
        return this.noiseMoveSpeedY;
    }

    public final float getNoiseMoveSpeedZ() {
        return this.noiseMoveSpeedZ;
    }

    public final float getNoiseOffsetX() {
        return this.noiseOffsetX;
    }

    public final float getNoiseOffsetY() {
        return this.noiseOffsetY;
    }

    public final float getNoiseOffsetZ() {
        return this.noiseOffsetZ;
    }

    public final float getPixelDensity() {
        return this.pixelDensity;
    }

    public final int getScreenColor() {
        return this.screenColor;
    }

    public final boolean getShouldInverseNoiseLuminosity() {
        return this.shouldInverseNoiseLuminosity;
    }

    public final float getWidth() {
        return this.width;
    }

    public int hashCode() {
        return (((((((((((((((((((((((((((((((((((Float.hashCode(this.gridCount) * 31) + Float.hashCode(this.luminosityMultiplier)) * 31) + Float.hashCode(this.noiseOffsetX)) * 31) + Float.hashCode(this.noiseOffsetY)) * 31) + Float.hashCode(this.noiseOffsetZ)) * 31) + Float.hashCode(this.noiseMoveSpeedX)) * 31) + Float.hashCode(this.noiseMoveSpeedY)) * 31) + Float.hashCode(this.noiseMoveSpeedZ)) * 31) + Integer.hashCode(this.color)) * 31) + Integer.hashCode(this.screenColor)) * 31) + Float.hashCode(this.width)) * 31) + Float.hashCode(this.height)) * 31) + Float.hashCode(this.maxDuration)) * 31) + Float.hashCode(this.easeInDuration)) * 31) + Float.hashCode(this.easeOutDuration)) * 31) + Float.hashCode(this.pixelDensity)) * 31) + Float.hashCode(this.lumaMatteBlendFactor)) * 31) + Float.hashCode(this.lumaMatteOverallBrightness)) * 31) + Boolean.hashCode(this.shouldInverseNoiseLuminosity);
    }

    public String toString() {
        return "TurbulenceNoiseAnimationConfig(gridCount=" + this.gridCount + ", luminosityMultiplier=" + this.luminosityMultiplier + ", noiseOffsetX=" + this.noiseOffsetX + ", noiseOffsetY=" + this.noiseOffsetY + ", noiseOffsetZ=" + this.noiseOffsetZ + ", noiseMoveSpeedX=" + this.noiseMoveSpeedX + ", noiseMoveSpeedY=" + this.noiseMoveSpeedY + ", noiseMoveSpeedZ=" + this.noiseMoveSpeedZ + ", color=" + this.color + ", screenColor=" + this.screenColor + ", width=" + this.width + ", height=" + this.height + ", maxDuration=" + this.maxDuration + ", easeInDuration=" + this.easeInDuration + ", easeOutDuration=" + this.easeOutDuration + ", pixelDensity=" + this.pixelDensity + ", lumaMatteBlendFactor=" + this.lumaMatteBlendFactor + ", lumaMatteOverallBrightness=" + this.lumaMatteOverallBrightness + ", shouldInverseNoiseLuminosity=" + this.shouldInverseNoiseLuminosity + ")";
    }
}
