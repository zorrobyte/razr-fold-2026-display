package com.motorola.taskbar.settings.wallpaper.utils;

import android.graphics.Point;
import android.graphics.Rect;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: BitmapUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class BitmapUtils {
    public static final Companion Companion = new Companion(null);

    /* JADX INFO: compiled from: BitmapUtils.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final float calculateHorizontalAlignment(Point point, Rect rect) {
            point.getClass();
            rect.getClass();
            int i = rect.left;
            int i2 = point.x - rect.right;
            if (i + i2 == 0) {
                return 0.5f;
            }
            float f = i;
            return f / (i2 + f);
        }

        public final int calculateInSampleSize(int i, int i2, int i3, int i4) {
            int i5 = i2 / 2;
            int i6 = i / 2;
            int i7 = 0;
            while ((i5 >> i7) >= i4 && (i6 >> i7) >= i3) {
                i7++;
            }
            return 1 << i7;
        }

        public final float calculateVerticalAlignment(Point point, Rect rect) {
            point.getClass();
            rect.getClass();
            int i = rect.top;
            int i2 = point.y - rect.bottom;
            if (i + i2 == 0) {
                return 0.5f;
            }
            float f = i;
            return f / (i2 + f);
        }
    }
}
