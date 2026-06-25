package androidx.compose.foundation.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutModifierNode;
import androidx.compose.ui.unit.ConstraintsKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: Padding.kt */
/* JADX INFO: loaded from: classes.dex */
final class PaddingNode extends Modifier.Node implements LayoutModifierNode {
    private float bottom;
    private float end;
    private boolean rtlAware;
    private float start;
    private float top;

    private PaddingNode(float f, float f2, float f3, float f4, boolean z) {
        this.start = f;
        this.top = f2;
        this.end = f3;
        this.bottom = f4;
        this.rtlAware = z;
    }

    public /* synthetic */ PaddingNode(float f, float f2, float f3, float f4, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2, f3, f4, z);
    }

    public final boolean getRtlAware() {
        return this.rtlAware;
    }

    /* JADX INFO: renamed from: getStart-D9Ej5fM, reason: not valid java name */
    public final float m165getStartD9Ej5fM() {
        return this.start;
    }

    /* JADX INFO: renamed from: getTop-D9Ej5fM, reason: not valid java name */
    public final float m166getTopD9Ej5fM() {
        return this.top;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* JADX INFO: renamed from: measure-3p2s80s */
    public MeasureResult mo34measure3p2s80s(final MeasureScope measureScope, Measurable measurable, long j) {
        int iMo141roundToPx0680j_4 = measureScope.mo141roundToPx0680j_4(this.start) + measureScope.mo141roundToPx0680j_4(this.end);
        int iMo141roundToPx0680j_42 = measureScope.mo141roundToPx0680j_4(this.top) + measureScope.mo141roundToPx0680j_4(this.bottom);
        final Placeable placeableMo1284measureBRTryo0 = measurable.mo1284measureBRTryo0(ConstraintsKt.m1873offsetNN6EwU(j, -iMo141roundToPx0680j_4, -iMo141roundToPx0680j_42));
        return MeasureScope.layout$default(measureScope, ConstraintsKt.m1872constrainWidthK40F9xA(j, placeableMo1284measureBRTryo0.getWidth() + iMo141roundToPx0680j_4), ConstraintsKt.m1871constrainHeightK40F9xA(j, placeableMo1284measureBRTryo0.getHeight() + iMo141roundToPx0680j_42), null, new Function1() { // from class: androidx.compose.foundation.layout.PaddingNode$measure$1
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
                if (this.this$0.getRtlAware()) {
                    Placeable.PlacementScope.placeRelative$default(placementScope, placeableMo1284measureBRTryo0, measureScope.mo141roundToPx0680j_4(this.this$0.m165getStartD9Ej5fM()), measureScope.mo141roundToPx0680j_4(this.this$0.m166getTopD9Ej5fM()), 0.0f, 4, null);
                } else {
                    Placeable.PlacementScope.place$default(placementScope, placeableMo1284measureBRTryo0, measureScope.mo141roundToPx0680j_4(this.this$0.m165getStartD9Ej5fM()), measureScope.mo141roundToPx0680j_4(this.this$0.m166getTopD9Ej5fM()), 0.0f, 4, null);
                }
            }
        }, 4, null);
    }

    /* JADX INFO: renamed from: setBottom-0680j_4, reason: not valid java name */
    public final void m167setBottom0680j_4(float f) {
        this.bottom = f;
    }

    /* JADX INFO: renamed from: setEnd-0680j_4, reason: not valid java name */
    public final void m168setEnd0680j_4(float f) {
        this.end = f;
    }

    public final void setRtlAware(boolean z) {
        this.rtlAware = z;
    }

    /* JADX INFO: renamed from: setStart-0680j_4, reason: not valid java name */
    public final void m169setStart0680j_4(float f) {
        this.start = f;
    }

    /* JADX INFO: renamed from: setTop-0680j_4, reason: not valid java name */
    public final void m170setTop0680j_4(float f) {
        this.top = f;
    }
}
