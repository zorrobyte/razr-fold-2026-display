package androidx.compose.material3;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNodeKt;
import androidx.compose.ui.node.LayoutModifierNode;
import androidx.compose.ui.unit.Dp;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: InteractiveComponentSize.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MinimumInteractiveModifierNode extends Modifier.Node implements CompositionLocalConsumerModifierNode, LayoutModifierNode {
    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* JADX INFO: renamed from: measure-3p2s80s */
    public MeasureResult mo34measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        float f = 0;
        float fM1877constructorimpl = Dp.m1877constructorimpl(RangesKt.coerceAtLeast(((Dp) CompositionLocalConsumerModifierNodeKt.currentValueOf(this, InteractiveComponentSizeKt.getLocalMinimumInteractiveComponentSize())).m1883unboximpl(), Dp.m1877constructorimpl(f)));
        final Placeable placeableMo1284measureBRTryo0 = measurable.mo1284measureBRTryo0(j);
        boolean z = isAttached() && !Float.isNaN(fM1877constructorimpl) && Dp.m1876compareTo0680j_4(fM1877constructorimpl, Dp.m1877constructorimpl(f)) > 0;
        int iMo141roundToPx0680j_4 = Float.isNaN(fM1877constructorimpl) ? 0 : measureScope.mo141roundToPx0680j_4(fM1877constructorimpl);
        final int iMax = z ? Math.max(placeableMo1284measureBRTryo0.getWidth(), iMo141roundToPx0680j_4) : placeableMo1284measureBRTryo0.getWidth();
        final int iMax2 = z ? Math.max(placeableMo1284measureBRTryo0.getHeight(), iMo141roundToPx0680j_4) : placeableMo1284measureBRTryo0.getHeight();
        return MeasureScope.layout$default(measureScope, iMax, iMax2, null, new Function1() { // from class: androidx.compose.material3.MinimumInteractiveModifierNode$measure$1
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
                Placeable.PlacementScope.place$default(placementScope, placeableMo1284measureBRTryo0, MathKt.roundToInt((iMax - placeableMo1284measureBRTryo0.getWidth()) / 2.0f), MathKt.roundToInt((iMax2 - placeableMo1284measureBRTryo0.getHeight()) / 2.0f), 0.0f, 4, null);
            }
        }, 4, null);
    }
}
