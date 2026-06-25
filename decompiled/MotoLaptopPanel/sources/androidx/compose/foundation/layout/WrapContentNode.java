package androidx.compose.foundation.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutModifierNode;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: Size.kt */
/* JADX INFO: loaded from: classes.dex */
final class WrapContentNode extends Modifier.Node implements LayoutModifierNode {
    private Function2 alignmentCallback;
    private Direction direction;
    private boolean unbounded;

    public WrapContentNode(Direction direction, boolean z, Function2 function2) {
        this.direction = direction;
        this.unbounded = z;
        this.alignmentCallback = function2;
    }

    public final Function2 getAlignmentCallback() {
        return this.alignmentCallback;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* JADX INFO: renamed from: measure-3p2s80s */
    public MeasureResult mo34measure3p2s80s(final MeasureScope measureScope, Measurable measurable, long j) {
        Direction direction = this.direction;
        Direction direction2 = Direction.Vertical;
        int iM1862getMinWidthimpl = direction != direction2 ? 0 : Constraints.m1862getMinWidthimpl(j);
        Direction direction3 = this.direction;
        Direction direction4 = Direction.Horizontal;
        final Placeable placeableMo1284measureBRTryo0 = measurable.mo1284measureBRTryo0(ConstraintsKt.Constraints(iM1862getMinWidthimpl, (this.direction == direction2 || !this.unbounded) ? Constraints.m1860getMaxWidthimpl(j) : Integer.MAX_VALUE, direction3 == direction4 ? Constraints.m1861getMinHeightimpl(j) : 0, (this.direction == direction4 || !this.unbounded) ? Constraints.m1859getMaxHeightimpl(j) : Integer.MAX_VALUE));
        final int iCoerceIn = RangesKt.coerceIn(placeableMo1284measureBRTryo0.getWidth(), Constraints.m1862getMinWidthimpl(j), Constraints.m1860getMaxWidthimpl(j));
        final int iCoerceIn2 = RangesKt.coerceIn(placeableMo1284measureBRTryo0.getHeight(), Constraints.m1861getMinHeightimpl(j), Constraints.m1859getMaxHeightimpl(j));
        return MeasureScope.layout$default(measureScope, iCoerceIn, iCoerceIn2, null, new Function1() { // from class: androidx.compose.foundation.layout.WrapContentNode$measure$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((Placeable.PlacementScope) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(Placeable.PlacementScope placementScope) {
                Function2 alignmentCallback = this.this$0.getAlignmentCallback();
                int width = iCoerceIn - placeableMo1284measureBRTryo0.getWidth();
                long j2 = ((long) width) << 32;
                Placeable.PlacementScope.m1295place70tqf50$default(placementScope, placeableMo1284measureBRTryo0, ((IntOffset) alignmentCallback.invoke(IntSize.m1918boximpl(IntSize.m1919constructorimpl((((long) (iCoerceIn2 - placeableMo1284measureBRTryo0.getHeight())) & 4294967295L) | j2)), measureScope.getLayoutDirection())).m1911unboximpl(), 0.0f, 2, null);
            }
        }, 4, null);
    }

    public final void setAlignmentCallback(Function2 function2) {
        this.alignmentCallback = function2;
    }

    public final void setDirection(Direction direction) {
        this.direction = direction;
    }

    public final void setUnbounded(boolean z) {
        this.unbounded = z;
    }
}
