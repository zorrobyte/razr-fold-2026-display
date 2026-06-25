package androidx.compose.ui.graphics.vector;

import androidx.compose.ui.graphics.BlendMode;
import androidx.compose.ui.graphics.BlendModeColorFilter;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.PathFillType;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import java.util.List;
import kotlin.collections.CollectionsKt;

/* JADX INFO: compiled from: Vector.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class VectorKt {
    private static final List EmptyPath = CollectionsKt.emptyList();
    private static final int DefaultStrokeLineCap = StrokeCap.Companion.m998getButtKaPHkGw();
    private static final int DefaultStrokeLineJoin = StrokeJoin.Companion.m1006getMiterLxFBmk8();
    private static final int DefaultTintBlendMode = BlendMode.Companion.m860getSrcIn0nO6VwU();
    private static final long DefaultTintColor = Color.Companion.m894getTransparent0d7_KjU();
    private static final int DefaultFillType = PathFillType.Companion.m968getNonZeroRgk1Os();

    public static final int getDefaultFillType() {
        return DefaultFillType;
    }

    public static final int getDefaultStrokeLineCap() {
        return DefaultStrokeLineCap;
    }

    public static final int getDefaultStrokeLineJoin() {
        return DefaultStrokeLineJoin;
    }

    public static final List getEmptyPath() {
        return EmptyPath;
    }

    /* JADX INFO: renamed from: rgbEqual--OWjLjI, reason: not valid java name */
    public static final boolean m1139rgbEqualOWjLjI(long j, long j2) {
        return Color.m887getRedimpl(j) == Color.m887getRedimpl(j2) && Color.m886getGreenimpl(j) == Color.m886getGreenimpl(j2) && Color.m884getBlueimpl(j) == Color.m884getBlueimpl(j2);
    }

    public static final boolean tintableWithAlphaMask(ColorFilter colorFilter) {
        if (!(colorFilter instanceof BlendModeColorFilter)) {
            return colorFilter == null;
        }
        BlendModeColorFilter blendModeColorFilter = (BlendModeColorFilter) colorFilter;
        int iM864getBlendMode0nO6VwU = blendModeColorFilter.m864getBlendMode0nO6VwU();
        BlendMode.Companion companion = BlendMode.Companion;
        return BlendMode.m832equalsimpl0(iM864getBlendMode0nO6VwU, companion.m860getSrcIn0nO6VwU()) || BlendMode.m832equalsimpl0(blendModeColorFilter.m864getBlendMode0nO6VwU(), companion.m862getSrcOver0nO6VwU());
    }
}
