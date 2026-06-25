package androidx.compose.material3.tokens;

import androidx.compose.ui.unit.Dp;

/* JADX INFO: compiled from: SmallIconButtonTokens.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SmallIconButtonTokens {
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
    public static final SmallIconButtonTokens INSTANCE = new SmallIconButtonTokens();
    private static final float ContainerHeight = Dp.m1877constructorimpl((float) 40.0d);

    static {
        ShapeKeyTokens shapeKeyTokens = ShapeKeyTokens.CornerFull;
        ContainerShapeRound = shapeKeyTokens;
        ShapeKeyTokens shapeKeyTokens2 = ShapeKeyTokens.CornerMedium;
        ContainerShapeSquare = shapeKeyTokens2;
        float f = (float) 8.0d;
        DefaultLeadingSpace = Dp.m1877constructorimpl(f);
        DefaultTrailingSpace = Dp.m1877constructorimpl(f);
        IconSize = Dp.m1877constructorimpl((float) 24.0d);
        float f2 = (float) 4.0d;
        NarrowLeadingSpace = Dp.m1877constructorimpl(f2);
        NarrowTrailingSpace = Dp.m1877constructorimpl(f2);
        OutlinedOutlineWidth = Dp.m1877constructorimpl((float) 1.0d);
        PressedContainerShape = ShapeKeyTokens.CornerSmall;
        SelectedContainerShapeRound = shapeKeyTokens2;
        SelectedContainerShapeSquare = shapeKeyTokens;
        float f3 = (float) 14.0d;
        WideLeadingSpace = Dp.m1877constructorimpl(f3);
        WideTrailingSpace = Dp.m1877constructorimpl(f3);
    }

    private SmallIconButtonTokens() {
    }

    /* JADX INFO: renamed from: getContainerHeight-D9Ej5fM, reason: not valid java name */
    public final float m477getContainerHeightD9Ej5fM() {
        return ContainerHeight;
    }

    public final ShapeKeyTokens getContainerShapeRound() {
        return ContainerShapeRound;
    }

    /* JADX INFO: renamed from: getDefaultLeadingSpace-D9Ej5fM, reason: not valid java name */
    public final float m478getDefaultLeadingSpaceD9Ej5fM() {
        return DefaultLeadingSpace;
    }

    /* JADX INFO: renamed from: getIconSize-D9Ej5fM, reason: not valid java name */
    public final float m479getIconSizeD9Ej5fM() {
        return IconSize;
    }

    /* JADX INFO: renamed from: getNarrowLeadingSpace-D9Ej5fM, reason: not valid java name */
    public final float m480getNarrowLeadingSpaceD9Ej5fM() {
        return NarrowLeadingSpace;
    }

    /* JADX INFO: renamed from: getNarrowTrailingSpace-D9Ej5fM, reason: not valid java name */
    public final float m481getNarrowTrailingSpaceD9Ej5fM() {
        return NarrowTrailingSpace;
    }

    /* JADX INFO: renamed from: getWideLeadingSpace-D9Ej5fM, reason: not valid java name */
    public final float m482getWideLeadingSpaceD9Ej5fM() {
        return WideLeadingSpace;
    }

    /* JADX INFO: renamed from: getWideTrailingSpace-D9Ej5fM, reason: not valid java name */
    public final float m483getWideTrailingSpaceD9Ej5fM() {
        return WideTrailingSpace;
    }
}
