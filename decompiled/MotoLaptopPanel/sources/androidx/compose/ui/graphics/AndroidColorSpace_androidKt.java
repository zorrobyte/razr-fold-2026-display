package androidx.compose.ui.graphics;

import android.graphics.ColorSpace;

/* JADX INFO: compiled from: AndroidColorSpace.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class AndroidColorSpace_androidKt {
    public static final ColorSpace toAndroidColorSpace(androidx.compose.ui.graphics.colorspace.ColorSpace colorSpace) {
        return ColorSpaceVerificationHelper.androidColorSpace(colorSpace);
    }
}
