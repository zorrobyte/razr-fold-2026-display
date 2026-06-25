package androidx.compose.material3.tokens;

import androidx.compose.ui.unit.Dp;

/* JADX INFO: compiled from: LargeIconButtonTokens.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LargeIconButtonTokens {
    private static final ShapeKeyTokens ContainerShapeRound;
    private static final ShapeKeyTokens ContainerShapeSquare;
    private static final float IconSize;
    private static final float NarrowLeadingSpace;
    private static final float NarrowTrailingSpace;
    private static final float OutlinedOutlineWidth;
    private static final ShapeKeyTokens PressedContainerShape;
    private static final ShapeKeyTokens SelectedContainerShapeRound;
    private static final ShapeKeyTokens SelectedContainerShapeSquare;
    private static final float UniformLeadingSpace;
    private static final float UniformTrailingSpace;
    private static final float WideLeadingSpace;
    private static final float WideTrailingSpace;
    public static final LargeIconButtonTokens INSTANCE = new LargeIconButtonTokens();
    private static final float ContainerHeight = Dp.m1877constructorimpl((float) 96.0d);

    static {
        ShapeKeyTokens shapeKeyTokens = ShapeKeyTokens.CornerFull;
        ContainerShapeRound = shapeKeyTokens;
        ShapeKeyTokens shapeKeyTokens2 = ShapeKeyTokens.CornerExtraLarge;
        ContainerShapeSquare = shapeKeyTokens2;
        float f = (float) 32.0d;
        IconSize = Dp.m1877constructorimpl(f);
        float f2 = (float) 16.0d;
        NarrowLeadingSpace = Dp.m1877constructorimpl(f2);
        NarrowTrailingSpace = Dp.m1877constructorimpl(f2);
        OutlinedOutlineWidth = Dp.m1877constructorimpl((float) 2.0d);
        PressedContainerShape = ShapeKeyTokens.CornerLarge;
        SelectedContainerShapeRound = shapeKeyTokens2;
        SelectedContainerShapeSquare = shapeKeyTokens;
        UniformLeadingSpace = Dp.m1877constructorimpl(f);
        UniformTrailingSpace = Dp.m1877constructorimpl(f);
        float f3 = (float) 48.0d;
        WideLeadingSpace = Dp.m1877constructorimpl(f3);
        WideTrailingSpace = Dp.m1877constructorimpl(f3);
    }

    private LargeIconButtonTokens() {
    }

    /* JADX INFO: renamed from: getIconSize-D9Ej5fM, reason: not valid java name */
    public final float m420getIconSizeD9Ej5fM() {
        return IconSize;
    }
}
