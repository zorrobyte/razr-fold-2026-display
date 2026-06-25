package androidx.compose.ui.graphics;

import android.graphics.ColorSpace;
import androidx.compose.ui.graphics.colorspace.ColorSpaces;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AndroidColorSpace.android.kt */
/* JADX INFO: loaded from: classes.dex */
final class ColorSpaceVerificationHelperV34 {
    public static final ColorSpaceVerificationHelperV34 INSTANCE = new ColorSpaceVerificationHelperV34();

    private ColorSpaceVerificationHelperV34() {
    }

    public static final ColorSpace obtainAndroidColorSpace(androidx.compose.ui.graphics.colorspace.ColorSpace colorSpace) {
        ColorSpaces colorSpaces = ColorSpaces.INSTANCE;
        if (Intrinsics.areEqual(colorSpace, colorSpaces.getBt2020Hlg())) {
            return ColorSpace.get(ColorSpace.Named.BT2020_HLG);
        }
        if (Intrinsics.areEqual(colorSpace, colorSpaces.getBt2020Pq())) {
            return ColorSpace.get(ColorSpace.Named.BT2020_PQ);
        }
        return null;
    }
}
