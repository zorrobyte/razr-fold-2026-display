package com.android.systemui.media.controls.util;

import android.os.Bundle;
import android.util.Pair;

/* JADX INFO: loaded from: classes.dex */
public abstract class MediaDataUtils {
    public static Double getDescriptionProgress(Bundle bundle) {
        if (bundle == null || !bundle.containsKey("android.media.extra.PLAYBACK_STATUS")) {
            return null;
        }
        int i = bundle.getInt("android.media.extra.PLAYBACK_STATUS");
        if (i == 0) {
            return Double.valueOf(0.0d);
        }
        if (i == 1) {
            return Double.valueOf(0.5d);
        }
        if (i != 2) {
            return null;
        }
        return Double.valueOf(1.0d);
    }

    public static float getScaleFactor(Pair pair, Pair pair2) {
        float fIntValue = ((Integer) pair.first).intValue();
        float fIntValue2 = ((Integer) pair.second).intValue();
        float fIntValue3 = ((Integer) pair2.first).intValue();
        float fIntValue4 = ((Integer) pair2.second).intValue();
        if (fIntValue == 0.0f || fIntValue2 == 0.0f || fIntValue3 == 0.0f || fIntValue4 == 0.0f) {
            return 0.0f;
        }
        return fIntValue / fIntValue2 > fIntValue3 / fIntValue4 ? fIntValue4 / fIntValue2 : fIntValue3 / fIntValue;
    }
}
