package androidx.compose.ui.layout;

import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: RootMeasurePolicy.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RootMeasurePolicy extends LayoutNode.NoIntrinsicsMeasurePolicy {
    public static final RootMeasurePolicy INSTANCE = new RootMeasurePolicy();

    private RootMeasurePolicy() {
        super("Undefined intrinsics block and it is required");
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    /* JADX INFO: renamed from: measure-3p2s80s */
    public MeasureResult mo538measure3p2s80s(MeasureScope measureScope, List list, long j) {
        int size = list.size();
        if (size == 0) {
            return MeasureScope.layout$default(measureScope, Constraints.m982getMinWidthimpl(j), Constraints.m981getMinHeightimpl(j), null, new Function1() { // from class: androidx.compose.ui.layout.RootMeasurePolicy$measure$1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    invoke((Placeable.PlacementScope) obj);
                    return Unit.INSTANCE;
                }

                public final void invoke(Placeable.PlacementScope placementScope) {
                }
            }, 4, null);
        }
        if (size == 1) {
            final Placeable placeableMo537measureBRTryo0 = ((Measurable) list.get(0)).mo537measureBRTryo0(j);
            return MeasureScope.layout$default(measureScope, ConstraintsKt.m988constrainWidthK40F9xA(j, placeableMo537measureBRTryo0.getWidth()), ConstraintsKt.m987constrainHeightK40F9xA(j, placeableMo537measureBRTryo0.getHeight()), null, new Function1() { // from class: androidx.compose.ui.layout.RootMeasurePolicy$measure$2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    invoke((Placeable.PlacementScope) obj);
                    return Unit.INSTANCE;
                }

                public final void invoke(Placeable.PlacementScope placementScope) {
                    Placeable.PlacementScope.placeRelativeWithLayer$default(placementScope, placeableMo537measureBRTryo0, 0, 0, 0.0f, null, 12, null);
                }
            }, 4, null);
        }
        final ArrayList arrayList = new ArrayList(list.size());
        int size2 = list.size();
        int iMax = 0;
        int iMax2 = 0;
        for (int i = 0; i < size2; i++) {
            Placeable placeableMo537measureBRTryo02 = ((Measurable) list.get(i)).mo537measureBRTryo0(j);
            iMax = Math.max(placeableMo537measureBRTryo02.getWidth(), iMax);
            iMax2 = Math.max(placeableMo537measureBRTryo02.getHeight(), iMax2);
            arrayList.add(placeableMo537measureBRTryo02);
        }
        return MeasureScope.layout$default(measureScope, ConstraintsKt.m988constrainWidthK40F9xA(j, iMax), ConstraintsKt.m987constrainHeightK40F9xA(j, iMax2), null, new Function1() { // from class: androidx.compose.ui.layout.RootMeasurePolicy$measure$3
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
                List list2 = arrayList;
                int size3 = list2.size();
                for (int i2 = 0; i2 < size3; i2++) {
                    Placeable.PlacementScope.placeRelativeWithLayer$default(placementScope, (Placeable) list2.get(i2), 0, 0, 0.0f, null, 12, null);
                }
            }
        }, 4, null);
    }
}
