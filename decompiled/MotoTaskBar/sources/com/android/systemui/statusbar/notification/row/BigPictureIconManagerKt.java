package com.android.systemui.statusbar.notification.row;

import android.graphics.drawable.Drawable;
import android.util.Size;

/* JADX INFO: compiled from: BigPictureIconManager.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class BigPictureIconManagerKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final int component1(Size size) {
        size.getClass();
        return size.getWidth();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int component2(Size size) {
        size.getClass();
        return size.getHeight();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Size getIntrinsicSize(Drawable drawable) {
        return new Size(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Size resizeToMax(Size size, int i, int i2) {
        if (size.getWidth() > i || size.getHeight() > i2) {
            float fMin = Math.min(i <= 0 ? 1.0f : i / size.getWidth(), i2 <= 0 ? 1.0f : i2 / size.getHeight());
            if (fMin < 1.0f) {
                return new Size((int) (size.getWidth() * fMin), (int) (size.getHeight() * fMin));
            }
        }
        return size;
    }
}
