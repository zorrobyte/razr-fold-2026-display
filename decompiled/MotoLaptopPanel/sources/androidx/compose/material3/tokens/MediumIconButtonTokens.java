package androidx.compose.material3.tokens;

import androidx.compose.ui.unit.Dp;

/* JADX INFO: compiled from: MediumIconButtonTokens.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediumIconButtonTokens {
    private static final ShapeKeyTokens ContainerShapeRound;
    private static final ShapeKeyTokens ContainerShapeSquare;
    private static final float DefaultLeadingSpace;
    private static final float DefaultTrailingSpace;
    private static final float IconSize;
    private static final float NarrowLeadingSpace;
    private static final float NarrowTrailingSpace;
    private static final float OutlinedOutlineWidth;
    private static final ShapeKeyTokens PressedContainerShape;
    private static final ShapeKeyTokens SelectedContainerShapeRound;
    private static final ShapeKeyTokens SelectedContainerShapeSquare;
    private static final float WideLeadingSpace;
    private static final float WideTrailingSpace;
    public static final MediumIconButtonTokens INSTANCE = new MediumIconButtonTokens();
    private static final float ContainerHeight = Dp.m1877constructorimpl((float) 56.0d);

    static {
        ShapeKeyTokens shapeKeyTokens = ShapeKeyTokens.CornerFull;
        ContainerShapeRound = shapeKeyTokens;
        ShapeKeyTokens shapeKeyTokens2 = ShapeKeyTokens.CornerLarge;
        ContainerShapeSquare = shapeKeyTokens2;
        float f = (float) 16.0d;
        DefaultLeadingSpace = Dp.m1877constructorimpl(f);
        DefaultTrailingSpace = Dp.m1877constructorimpl(f);
        float f2 = (float) 24.0d;
        IconSize = Dp.m1877constructorimpl(f2);
        float f3 = (float) 12.0d;
        NarrowLeadingSpace = Dp.m1877constructorimpl(f3);
        NarrowTrailingSpace = Dp.m1877constructorimpl(f3);
        OutlinedOutlineWidth = Dp.m1877constructorimpl((float) 1.0d);
        PressedContainerShape = ShapeKeyTokens.CornerMedium;
        SelectedContainerShapeRound = shapeKeyTokens2;
        SelectedContainerShapeSquare = shapeKeyTokens;
        WideLeadingSpace = Dp.m1877constructorimpl(f2);
        WideTrailingSpace = Dp.m1877constructorimpl(f2);
    }

    private MediumIconButtonTokens() {
    }

    /* JADX INFO: renamed from: getIconSize-D9Ej5fM, reason: not valid java name */
    public final float m421getIconSizeD9Ej5fM() {
        return IconSize;
    }
}
