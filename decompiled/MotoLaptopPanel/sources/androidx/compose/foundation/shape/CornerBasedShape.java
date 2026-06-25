package androidx.compose.foundation.shape;

import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;

/* JADX INFO: compiled from: CornerBasedShape.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class CornerBasedShape implements Shape {
    private final CornerSize bottomEnd;
    private final CornerSize bottomStart;
    private final CornerSize topEnd;
    private final CornerSize topStart;

    public CornerBasedShape(CornerSize cornerSize, CornerSize cornerSize2, CornerSize cornerSize3, CornerSize cornerSize4) {
        this.topStart = cornerSize;
        this.topEnd = cornerSize2;
        this.bottomEnd = cornerSize3;
        this.bottomStart = cornerSize4;
    }

    public static /* synthetic */ CornerBasedShape copy$default(CornerBasedShape cornerBasedShape, CornerSize cornerSize, CornerSize cornerSize2, CornerSize cornerSize3, CornerSize cornerSize4, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: copy");
        }
        if ((i & 1) != 0) {
            cornerSize = cornerBasedShape.topStart;
        }
        if ((i & 2) != 0) {
            cornerSize2 = cornerBasedShape.topEnd;
        }
        if ((i & 4) != 0) {
            cornerSize3 = cornerBasedShape.bottomEnd;
        }
        if ((i & 8) != 0) {
            cornerSize4 = cornerBasedShape.bottomStart;
        }
        return cornerBasedShape.copy(cornerSize, cornerSize2, cornerSize3, cornerSize4);
    }

    public abstract CornerBasedShape copy(CornerSize cornerSize, CornerSize cornerSize2, CornerSize cornerSize3, CornerSize cornerSize4);

    /* JADX INFO: renamed from: createOutline-LjSzlW0, reason: not valid java name */
    public abstract Outline mo188createOutlineLjSzlW0(long j, float f, float f2, float f3, float f4, LayoutDirection layoutDirection);

    @Override // androidx.compose.ui.graphics.Shape
    /* JADX INFO: renamed from: createOutline-Pq9zytI, reason: not valid java name */
    public final Outline mo189createOutlinePq9zytI(long j, LayoutDirection layoutDirection, Density density) {
        float fMo190toPxTmRCtEA = this.topStart.mo190toPxTmRCtEA(j, density);
        float fMo190toPxTmRCtEA2 = this.topEnd.mo190toPxTmRCtEA(j, density);
        float fMo190toPxTmRCtEA3 = this.bottomEnd.mo190toPxTmRCtEA(j, density);
        float fMo190toPxTmRCtEA4 = this.bottomStart.mo190toPxTmRCtEA(j, density);
        float fM787getMinDimensionimpl = Size.m787getMinDimensionimpl(j);
        float f = fMo190toPxTmRCtEA + fMo190toPxTmRCtEA4;
        if (f > fM787getMinDimensionimpl) {
            float f2 = fM787getMinDimensionimpl / f;
            fMo190toPxTmRCtEA *= f2;
            fMo190toPxTmRCtEA4 *= f2;
        }
        float f3 = fMo190toPxTmRCtEA2 + fMo190toPxTmRCtEA3;
        if (f3 > fM787getMinDimensionimpl) {
            float f4 = fM787getMinDimensionimpl / f3;
            fMo190toPxTmRCtEA2 *= f4;
            fMo190toPxTmRCtEA3 *= f4;
        }
        if (!(fMo190toPxTmRCtEA >= 0.0f && fMo190toPxTmRCtEA2 >= 0.0f && fMo190toPxTmRCtEA3 >= 0.0f && fMo190toPxTmRCtEA4 >= 0.0f)) {
            InlineClassHelperKt.throwIllegalArgumentException("Corner size in Px can't be negative(topStart = " + fMo190toPxTmRCtEA + ", topEnd = " + fMo190toPxTmRCtEA2 + ", bottomEnd = " + fMo190toPxTmRCtEA3 + ", bottomStart = " + fMo190toPxTmRCtEA4 + ")!");
        }
        return mo188createOutlineLjSzlW0(j, fMo190toPxTmRCtEA, fMo190toPxTmRCtEA2, fMo190toPxTmRCtEA3, fMo190toPxTmRCtEA4, layoutDirection);
    }

    public final CornerSize getBottomEnd() {
        return this.bottomEnd;
    }

    public final CornerSize getBottomStart() {
        return this.bottomStart;
    }

    public final CornerSize getTopEnd() {
        return this.topEnd;
    }

    public final CornerSize getTopStart() {
        return this.topStart;
    }
}
