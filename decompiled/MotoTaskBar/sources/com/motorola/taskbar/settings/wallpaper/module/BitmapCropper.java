package com.motorola.taskbar.settings.wallpaper.module;

import android.graphics.Bitmap;
import android.graphics.Rect;
import com.motorola.taskbar.settings.wallpaper.asset.IAsset;

/* JADX INFO: compiled from: BitmapCropper.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface BitmapCropper {

    /* JADX INFO: compiled from: BitmapCropper.kt */
    public interface Callback {
        void onBitmapCropped(Bitmap bitmap);

        void onError(Throwable th);
    }

    void cropAndScaleBitmap(IAsset iAsset, float f, Rect rect, Callback callback);
}
