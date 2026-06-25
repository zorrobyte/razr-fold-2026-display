package com.android.launcher3.icons;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;

/* JADX INFO: loaded from: classes.dex */
public interface BitmapRenderer {
    public static final boolean USE_HARDWARE_BITMAP = true;

    static Bitmap createHardwareBitmap(int i, int i2, BitmapRenderer bitmapRenderer) {
        if (!USE_HARDWARE_BITMAP) {
            return createSoftwareBitmap(i, i2, bitmapRenderer);
        }
        GraphicsUtils.noteNewBitmapCreated();
        Picture picture = new Picture();
        bitmapRenderer.draw(picture.beginRecording(i, i2));
        picture.endRecording();
        return Bitmap.createBitmap(picture);
    }

    static Bitmap createSoftwareBitmap(int i, int i2, BitmapRenderer bitmapRenderer) {
        GraphicsUtils.noteNewBitmapCreated();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        bitmapRenderer.draw(new Canvas(bitmapCreateBitmap));
        return bitmapCreateBitmap;
    }

    void draw(Canvas canvas);
}
