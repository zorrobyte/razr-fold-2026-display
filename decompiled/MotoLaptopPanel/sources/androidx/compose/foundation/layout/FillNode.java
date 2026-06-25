package androidx.compose.foundation.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutModifierNode;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: Size.kt */
/* JADX INFO: loaded from: classes.dex */
final class FillNode extends Modifier.Node implements LayoutModifierNode {
    private Direction direction;
    private float fraction;

    public FillNode(Direction direction, float f) {
        this.direction = direction;
        this.fraction = f;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* JADX INFO: renamed from: measure-3p2s80s */
    public MeasureResult mo34measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        int iM1862getMinWidthimpl;
        int iM1860getMaxWidthimpl;
        int iM1859getMaxHeightimpl;
        int i;
        if (!Constraints.m1856getHasBoundedWidthimpl(j) || this.direction == Direction.Vertical) {
            iM1862getMinWidthimpl = Constraints.m1862getMinWidthimpl(j);
            iM1860getMaxWidthimpl = Constraints.m1860getMaxWidthimpl(j);
        } else {
            int iRound = Math.round(Constraints.m1860getMaxWidthimpl(j) * this.fraction);
            int iM1862getMinWidthimpl2 = Constraints.m1862getMinWidthimpl(j);
            iM1862getMinWidthimpl = Constraints.m1860getMaxWidthimpl(j);
            if (iRound < iM1862getMinWidthimpl2) {
                iRound = iM1862getMinWidthimpl2;
            }
            if (iRound <= iM1862getMinWidthimpl) {
                iM1862getMinWidthimpl = iRound;
            }
            iM1860getMaxWidthimpl = iM1862getMinWidthimpl;
        }
        if (!Constraints.m1855getHasBoundedHeightimpl(j) || this.direction == Direction.Horizontal) {
            int iM1861getMinHeightimpl = Constraints.m1861getMinHeightimpl(j);
            int iM1859getMaxHeightimpl2 = Constraints.m1859getMaxHeightimpl(j);
            iM1859getMaxHeightimpl = iM1861getMinHeightimpl;
            i = iM1859getMaxHeightimpl2;
        } else {
            int iRound2 = Math.round(Constraints.m1859getMaxHeightimpl(j) * this.fraction);
            int iM1861getMinHeightimpl2 = Constraints.m1861getMinHeightimpl(j);
            iM1859getMaxHeightimpl = Constraints.m1859getMaxHeightimpl(j);
            if (iRound2 < iM1861getMinHeightimpl2) {
                iRound2 = iM1861getMinHeightimpl2;
            }
            if (iRound2 <= iM1859getMaxHeightimpl) {
                iM1859getMaxHeightimpl = iRound2;
            }
            i = iM1859getMaxHeightimpl;
        }
        final Placeable placeableMo1284measureBRTryo0 = measurable.mo1284measureBRTryo0(ConstraintsKt.Constraints(iM1862getMinWidthimpl, iM1860getMaxWidthimpl, iM1859getMaxHeightimpl, i));
        return MeasureScope.layout$default(measureScope, placeableMo1284measureBRTryo0.getWidth(), placeableMo1284measureBRTryo0.getHeight(), null, new Function1() { // from class: androidx.compose.foundation.layout.FillNode$measure$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((Placeable.PlacementScope) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(Placeable.PlacementScope placementScope) {
                Placeable.PlacementScope.placeRelative$default(placementScope, placeableMo1284measureBRTryo0, 0, 0, 0.0f, 4, null);
            }
        }, 4, null);
    }

    public final void setDirection(Direction direction) {
        this.direction = direction;
    }

    public final void setFraction(float f) {
        this.fraction = f;
    }
}
