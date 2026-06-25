package com.motorola.taskbar.settings.wallpaper.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import com.motorola.taskbar.guide.R$dimen;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: TileSizeCalculator.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class TileSizeCalculator {
    public static final Companion Companion = new Companion(null);

    /* JADX INFO: compiled from: TileSizeCalculator.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final int getActivityWindowWidthPx(Activity activity) {
            Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
            Point point = new Point();
            defaultDisplay.getSize(point);
            return point.x;
        }

        private final int getNumColumns(Context context, int i, int i2, int i3) {
            return RangesKt.coerceAtLeast(i / i2, 1);
        }

        public final int getNumIndividualColumns(Activity activity, View view) {
            activity.getClass();
            view.getClass();
            int activityWindowWidthPx = getActivityWindowWidthPx(activity);
            Resources resources = activity.getResources();
            int dimensionPixelOffset = resources.getDimensionPixelOffset(R$dimen.wallpaper_item_basic_width);
            int dimensionPixelOffset2 = resources.getDimensionPixelOffset(R$dimen.wallpaper_item_basic_height);
            int dimensionPixelOffset3 = resources.getDimensionPixelOffset(R$dimen.wallpaper_item_padding_end);
            return getNumColumns(activity, ((int) (((double) activityWindowWidthPx) * 0.7d)) - (resources.getDimensionPixelOffset(R$dimen.wallpaper_container_padding) * 2), dimensionPixelOffset + dimensionPixelOffset3, dimensionPixelOffset2 + dimensionPixelOffset3);
        }
    }
}
