package androidx.compose.material3.tokens;

import androidx.compose.ui.unit.Dp;

/* JADX INFO: compiled from: XSmallIconButtonTokens.kt */
/* JADX INFO: loaded from: classes.dex */
public final class XSmallIconButtonTokens {
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
    public static final XSmallIconButtonTokens INSTANCE = new XSmallIconButtonTokens();
    private static final float ContainerHeight = Dp.m1877constructorimpl((float) 32.0d);

    static {
        ShapeKeyTokens shapeKeyTokens = ShapeKeyTokens.CornerFull;
        ContainerShapeRound = shapeKeyTokens;
        ShapeKeyTokens shapeKeyTokens2 = ShapeKeyTokens.CornerMedium;
        ContainerShapeSquare = shapeKeyTokens2;
        float f = (float) 6.0d;
        DefaultLeadingSpace = Dp.m1877constructorimpl(f);
        DefaultTrailingSpace = Dp.m1877constructorimpl(f);
        IconSize = Dp.m1877constructorimpl((float) 20.0d);
        float f2 = (float) 4.0d;
        NarrowLeadingSpace = Dp.m1877constructorimpl(f2);
        NarrowTrailingSpace = Dp.m1877constructorimpl(f2);
        OutlinedOutlineWidth = Dp.m1877constructorimpl((float) 1.0d);
        PressedContainerShape = ShapeKeyTokens.CornerSmall;
        SelectedContainerShapeRound = shapeKeyTokens2;
        SelectedContainerShapeSquare = shapeKeyTokens;
        float f3 = (float) 10.0d;
        WideLeadingSpace = Dp.m1877constructorimpl(f3);
        WideTrailingSpace = Dp.m1877constructorimpl(f3);
    }

    private XSmallIconButtonTokens() {
    }

    /* JADX INFO: renamed from: getIconSize-D9Ej5fM, reason: not valid java name */
    public final float m575getIconSizeD9Ej5fM() {
        return IconSize;
    }
}
