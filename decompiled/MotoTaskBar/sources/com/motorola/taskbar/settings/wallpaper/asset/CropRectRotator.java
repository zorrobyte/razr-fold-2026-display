package com.motorola.taskbar.settings.wallpaper.asset;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: CropRectRotator.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class CropRectRotator {
    public static final Companion Companion = new Companion(null);

    /* JADX INFO: compiled from: CropRectRotator.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Rect rotateCropRectForExifOrientation(Point point, Rect rect, int i) {
            point.getClass();
            rect.getClass();
            if (i == 1) {
                return new Rect(rect);
            }
            if (i == 3) {
                int i2 = point.x;
                int i3 = i2 - rect.right;
                int i4 = point.y;
                return new Rect(i3, i4 - rect.bottom, i2 - rect.left, i4 - rect.top);
            }
            if (i == 6) {
                int i5 = rect.top;
                int i6 = point.x;
                return new Rect(i5, i6 - rect.right, rect.bottom, i6 - rect.left);
            }
            if (i == 8) {
                int i7 = point.y;
                return new Rect(i7 - rect.bottom, rect.left, i7 - rect.top, rect.right);
            }
            Log.w("CropRectRotator", "Unsupported EXIF orientation " + i);
            return new Rect(rect);
        }
    }
}
