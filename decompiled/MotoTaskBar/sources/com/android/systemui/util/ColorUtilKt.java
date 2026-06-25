package com.android.systemui.util;

import android.graphics.Color;
import java.util.Arrays;
import kotlin.jvm.internal.StringCompanionObject;

/* JADX INFO: compiled from: ColorUtil.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ColorUtilKt {
    public static final int getColorWithAlpha(int i, float f) {
        return Color.argb((int) (f * 255), Color.red(i), Color.green(i), Color.blue(i));
    }

    public static final String hexColorString(Integer num) {
        if (num == null) {
            return "null";
        }
        int iIntValue = num.intValue();
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        return String.format("#%08x", Arrays.copyOf(new Object[]{Integer.valueOf(iIntValue)}, 1));
    }
}
