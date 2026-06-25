package com.android.settingslib.display;

import android.hardware.display.BrightnessInfo;
import android.util.MathUtils;
import android.util.Slog;
import android.view.SurfaceControl;

/* JADX INFO: loaded from: classes.dex */
public abstract class BrightnessUtils {
    public static final float convertGammaToLinearFloat(int i, float f, float f2) {
        float fNorm = MathUtils.norm(0.0f, 65535.0f, i);
        return MathUtils.lerp(f, f2, MathUtils.constrain(fNorm <= 0.5f ? MathUtils.sq(fNorm / 0.5f) : MathUtils.exp((fNorm - 0.5599107f) / 0.17883277f) + 0.28466892f, 0.0f, 12.0f) / 12.0f);
    }

    public static final float convertGammaToLinearFloat(int i, float f, float f2, int i2) {
        float fLerp;
        float f3 = SurfaceControl.MOTO_GAMMA_R;
        float fPow = 0.0f;
        if (f3 <= 0.0f) {
            f3 = 1.5f;
        }
        float fNorm = MathUtils.norm(0.0f, 65535.0f, i);
        if (SurfaceControl.USE_GAMMA_CONVERTING) {
            fLerp = convertGammaToLinearFloat(i, f, f2);
        } else if (i2 == 2) {
            fPow = MathUtils.pow(fNorm, f3);
            fLerp = MathUtils.lerp(f, f2, fPow);
        } else {
            fLerp = MathUtils.lerp(f, f2, fNorm);
        }
        if (SurfaceControl.IS_BACKLIGHT_DEBUGGABLE) {
            Slog.d("BrightnessUtils", "convertGammaToLinearFloat: r=" + f3 + " val=" + i + " min=" + f + " max=" + f2 + " normalizedVal=" + fNorm + " normalizedRet=" + fPow + " hbmPolicy=" + BrightnessInfo.hbmPolicyToString(i2) + " result=" + fLerp);
        }
        return fLerp;
    }

    public static final int convertLinearToGammaFloat(float f, float f2, float f3) {
        float fNorm = MathUtils.norm(f2, f3, f) * 12.0f;
        return Math.round(MathUtils.lerp(0, 65535, fNorm <= 1.0f ? MathUtils.sqrt(fNorm) * 0.5f : (MathUtils.log(fNorm - 0.28466892f) * 0.17883277f) + 0.5599107f));
    }

    public static final int convertLinearToGammaFloat(float f, float f2, float f3, int i) {
        int iRound;
        float f4 = SurfaceControl.MOTO_GAMMA_R;
        float fPow = 0.0f;
        if (f4 <= 0.0f) {
            f4 = 1.5f;
        }
        float fNorm = MathUtils.norm(f2, f3, f);
        if (SurfaceControl.USE_GAMMA_CONVERTING) {
            iRound = convertLinearToGammaFloat(f, f2, f3);
        } else if (i == 2) {
            fPow = MathUtils.pow(fNorm, 1.0f / f4);
            iRound = Math.round(MathUtils.lerp(0, 65535, fPow));
        } else {
            iRound = Math.round(MathUtils.lerp(0, 65535, fNorm));
        }
        if (SurfaceControl.IS_BACKLIGHT_DEBUGGABLE) {
            Slog.d("BrightnessUtils", "convertLinearToGammaFloat: r=" + f4 + " val=" + f + " min=" + f2 + " max=" + f3 + " normalizedVal=" + fNorm + " normalizedRet=" + fPow + " hbmPolicy=" + BrightnessInfo.hbmPolicyToString(i) + " result=" + iRound);
        }
        return iRound;
    }
}
