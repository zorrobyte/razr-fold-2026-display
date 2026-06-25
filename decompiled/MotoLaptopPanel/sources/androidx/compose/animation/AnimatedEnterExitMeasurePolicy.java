package androidx.compose.animation;

import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.IntSize;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: AnimatedVisibility.kt */
/* JADX INFO: loaded from: classes.dex */
final class AnimatedEnterExitMeasurePolicy implements MeasurePolicy {
    private boolean hasLookaheadOccurred;
    private final AnimatedVisibilityScopeImpl scope;

    public AnimatedEnterExitMeasurePolicy(AnimatedVisibilityScopeImpl animatedVisibilityScopeImpl) {
        this.scope = animatedVisibilityScopeImpl;
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    /* JADX INFO: renamed from: measure-3p2s80s, reason: not valid java name */
    public MeasureResult mo19measure3p2s80s(MeasureScope measureScope, List list, long j) {
        final ArrayList arrayList = new ArrayList(list.size());
        int size = list.size();
        int iMax = 0;
        int iMax2 = 0;
        for (int i = 0; i < size; i++) {
            Placeable placeableMo1284measureBRTryo0 = ((Measurable) list.get(i)).mo1284measureBRTryo0(j);
            iMax = Math.max(iMax, placeableMo1284measureBRTryo0.getWidth());
            iMax2 = Math.max(iMax2, placeableMo1284measureBRTryo0.getHeight());
            arrayList.add(placeableMo1284measureBRTryo0);
        }
        if (measureScope.isLookingAhead()) {
            this.hasLookaheadOccurred = true;
            this.scope.getTargetSize$animation().setValue(IntSize.m1918boximpl(IntSize.m1919constructorimpl((((long) iMax2) & 4294967295L) | (((long) iMax) << 32))));
        } else if (!this.hasLookaheadOccurred) {
            this.scope.getTargetSize$animation().setValue(IntSize.m1918boximpl(IntSize.m1919constructorimpl((((long) iMax2) & 4294967295L) | (((long) iMax) << 32))));
        }
        return MeasureScope.layout$default(measureScope, iMax, iMax2, null, new Function1() { // from class: androidx.compose.animation.AnimatedEnterExitMeasurePolicy$measure$1
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
                int size2 = list2.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    Placeable.PlacementScope.place$default(placementScope, (Placeable) list2.get(i2), 0, 0, 0.0f, 4, null);
                }
            }
        }, 4, null);
    }
}
