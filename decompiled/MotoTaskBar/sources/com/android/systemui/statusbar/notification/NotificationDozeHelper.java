package com.android.systemui.statusbar.notification;

import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.widget.ImageView;
import com.android.systemui.res.R$id;

/* JADX INFO: loaded from: classes.dex */
public class NotificationDozeHelper {
    private static final int DOZE_ANIMATOR_TAG = R$id.doze_intensity_tag;
    private final ColorMatrix mGrayscaleColorMatrix = new ColorMatrix();

    private void updateGrayscaleMatrix(float f) {
        this.mGrayscaleColorMatrix.setSaturation(1.0f - f);
    }

    public void updateGrayscale(ImageView imageView, float f) {
        if (f <= 0.0f) {
            imageView.setColorFilter((ColorFilter) null);
        } else {
            updateGrayscaleMatrix(f);
            imageView.setColorFilter(new ColorMatrixColorFilter(this.mGrayscaleColorMatrix));
        }
    }
}
