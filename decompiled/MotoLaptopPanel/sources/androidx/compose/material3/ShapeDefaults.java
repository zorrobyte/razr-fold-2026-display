package androidx.compose.material3;

import androidx.compose.foundation.shape.CornerBasedShape;
import androidx.compose.foundation.shape.CornerSize;
import androidx.compose.foundation.shape.CornerSizeKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.tokens.ShapeTokens;
import androidx.compose.ui.unit.Dp;

/* JADX INFO: compiled from: Shapes.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ShapeDefaults {
    private static final CornerSize CornerExtraExtraLarge;
    private static final CornerSize CornerExtraLarge;
    private static final CornerSize CornerExtraLargeIncreased;
    private static final CornerSize CornerExtraSmall;
    private static final CornerSize CornerFull;
    private static final CornerSize CornerLarge;
    private static final CornerSize CornerLargeIncreased;
    private static final CornerSize CornerMedium;
    private static final CornerSize CornerNone;
    private static final CornerSize CornerSmall;
    private static final CornerBasedShape ExtraExtraLarge;
    private static final CornerBasedShape ExtraLarge;
    private static final CornerBasedShape ExtraLargeIncreased;
    private static final CornerBasedShape ExtraSmall;
    public static final ShapeDefaults INSTANCE = new ShapeDefaults();
    private static final CornerBasedShape Large;
    private static final CornerBasedShape LargeIncreased;
    private static final CornerBasedShape Medium;
    private static final CornerBasedShape Small;

    static {
        ShapeTokens shapeTokens = ShapeTokens.INSTANCE;
        ExtraSmall = shapeTokens.getCornerExtraSmall();
        Small = shapeTokens.getCornerSmall();
        Medium = shapeTokens.getCornerMedium();
        Large = shapeTokens.getCornerLarge();
        float f = 20;
        LargeIncreased = RoundedCornerShapeKt.m192RoundedCornerShape0680j_4(Dp.m1877constructorimpl(f));
        ExtraLarge = shapeTokens.getCornerExtraLarge();
        float f2 = 32;
        ExtraLargeIncreased = RoundedCornerShapeKt.m192RoundedCornerShape0680j_4(Dp.m1877constructorimpl(f2));
        float f3 = 48;
        ExtraExtraLarge = RoundedCornerShapeKt.m192RoundedCornerShape0680j_4(Dp.m1877constructorimpl(f3));
        CornerNone = CornerSizeKt.m191CornerSize0680j_4(Dp.m1877constructorimpl(0));
        CornerExtraSmall = CornerSizeKt.m191CornerSize0680j_4(Dp.m1877constructorimpl(4));
        CornerSmall = CornerSizeKt.m191CornerSize0680j_4(Dp.m1877constructorimpl(8));
        CornerMedium = CornerSizeKt.m191CornerSize0680j_4(Dp.m1877constructorimpl(12));
        CornerLarge = CornerSizeKt.m191CornerSize0680j_4(Dp.m1877constructorimpl(16));
        CornerLargeIncreased = CornerSizeKt.m191CornerSize0680j_4(Dp.m1877constructorimpl(f));
        CornerExtraLarge = CornerSizeKt.m191CornerSize0680j_4(Dp.m1877constructorimpl(28));
        CornerExtraLargeIncreased = CornerSizeKt.m191CornerSize0680j_4(Dp.m1877constructorimpl(f2));
        CornerExtraExtraLarge = CornerSizeKt.m191CornerSize0680j_4(Dp.m1877constructorimpl(f3));
        CornerFull = CornerSizeKt.CornerSize(100);
    }

    private ShapeDefaults() {
    }

    public final CornerSize getCornerNone$material3_release() {
        return CornerNone;
    }

    public final CornerBasedShape getExtraExtraLarge() {
        return ExtraExtraLarge;
    }

    public final CornerBasedShape getExtraLarge() {
        return ExtraLarge;
    }

    public final CornerBasedShape getExtraLargeIncreased() {
        return ExtraLargeIncreased;
    }

    public final CornerBasedShape getExtraSmall() {
        return ExtraSmall;
    }

    public final CornerBasedShape getLarge() {
        return Large;
    }

    public final CornerBasedShape getLargeIncreased() {
        return LargeIncreased;
    }

    public final CornerBasedShape getMedium() {
        return Medium;
    }

    public final CornerBasedShape getSmall() {
        return Small;
    }
}
