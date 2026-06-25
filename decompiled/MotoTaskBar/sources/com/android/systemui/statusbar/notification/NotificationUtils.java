package com.android.systemui.statusbar.notification;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import com.android.internal.util.ContrastColorUtil;
import com.android.systemui.res.R$id;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.util.Compile;

/* JADX INFO: loaded from: classes.dex */
public abstract class NotificationUtils {
    private static final int[] sLocationBase = new int[2];
    private static final int[] sLocationOffset = new int[2];

    public static int getFontScaledHeight(Context context, int i) {
        return (int) (context.getResources().getDimensionPixelSize(i) * Math.max(1.0f, context.getResources().getDisplayMetrics().scaledDensity / context.getResources().getDisplayMetrics().density));
    }

    public static float getRelativeYOffset(View view, View view2) {
        view2.getLocationOnScreen(sLocationBase);
        view.getLocationOnScreen(sLocationOffset);
        return r2[1] - r0[1];
    }

    public static float interpolate(float f, float f2, float f3) {
        return (f * (1.0f - f3)) + (f2 * f3);
    }

    public static int interpolateColors(int i, int i2, float f) {
        return Color.argb((int) interpolate(Color.alpha(i), Color.alpha(i2), f), (int) interpolate(Color.red(i), Color.red(i2), f), (int) interpolate(Color.green(i), Color.green(i2), f), (int) interpolate(Color.blue(i), Color.blue(i2), f));
    }

    public static boolean isGrayscale(ImageView imageView, ContrastColorUtil contrastColorUtil) {
        int i = R$id.icon_is_grayscale;
        Object tag = imageView.getTag(i);
        if (tag != null) {
            return Boolean.TRUE.equals(tag);
        }
        boolean zIsGrayscaleIcon = contrastColorUtil.isGrayscaleIcon(imageView.getDrawable());
        imageView.setTag(i, Boolean.valueOf(zIsGrayscaleIcon));
        return zIsGrayscaleIcon;
    }

    public static String logKey(ListEntry listEntry) {
        if (listEntry == null) {
            return "null";
        }
        boolean z = Compile.IS_DEBUG;
        return logKey(listEntry.getKey());
    }

    public static String logKey(String str) {
        return str == null ? "null" : str.replace("\n", "");
    }
}
