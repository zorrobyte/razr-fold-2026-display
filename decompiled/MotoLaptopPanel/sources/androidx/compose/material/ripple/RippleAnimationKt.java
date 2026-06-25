package androidx.compose.material.ripple;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.OffsetKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;

/* JADX INFO: compiled from: RippleAnimation.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class RippleAnimationKt {
    private static final float BoundedRippleExtraRadius = Dp.m1877constructorimpl(10);

    /* JADX INFO: renamed from: getRippleEndRadius-cSwnlzA, reason: not valid java name */
    public static final float m230getRippleEndRadiuscSwnlzA(Density density, boolean z, long j) {
        float fM758getDistanceimpl = Offset.m758getDistanceimpl(OffsetKt.Offset(Size.m788getWidthimpl(j), Size.m786getHeightimpl(j))) / 2.0f;
        return z ? fM758getDistanceimpl + density.mo146toPx0680j_4(BoundedRippleExtraRadius) : fM758getDistanceimpl;
    }

    /* JADX INFO: renamed from: getRippleStartRadius-uvyYCjk, reason: not valid java name */
    public static final float m231getRippleStartRadiusuvyYCjk(long j) {
        return Math.max(Size.m788getWidthimpl(j), Size.m786getHeightimpl(j)) * 0.3f;
    }
}
