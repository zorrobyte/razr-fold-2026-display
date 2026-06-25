package androidx.compose.foundation.layout;

import androidx.compose.ui.Alignment;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$IntRef;

/* JADX INFO: compiled from: Box.kt */
/* JADX INFO: loaded from: classes.dex */
final class BoxMeasurePolicy implements MeasurePolicy {
    private final Alignment alignment;
    private final boolean propagateMinConstraints;

    public BoxMeasurePolicy(Alignment alignment, boolean z) {
        this.alignment = alignment;
        this.propagateMinConstraints = z;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BoxMeasurePolicy)) {
            return false;
        }
        BoxMeasurePolicy boxMeasurePolicy = (BoxMeasurePolicy) obj;
        return Intrinsics.areEqual(this.alignment, boxMeasurePolicy.alignment) && this.propagateMinConstraints == boxMeasurePolicy.propagateMinConstraints;
    }

    public int hashCode() {
        return (this.alignment.hashCode() * 31) + Boolean.hashCode(this.propagateMinConstraints);
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    /* JADX INFO: renamed from: measure-3p2s80s */
    public MeasureResult mo19measure3p2s80s(final MeasureScope measureScope, final List list, long j) {
        int iM1861getMinHeightimpl;
        final int i;
        final Placeable placeableMo1284measureBRTryo0;
        if (list.isEmpty()) {
            return MeasureScope.layout$default(measureScope, Constraints.m1862getMinWidthimpl(j), Constraints.m1861getMinHeightimpl(j), null, new Function1() { // from class: androidx.compose.foundation.layout.BoxMeasurePolicy$measure$1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    invoke((Placeable.PlacementScope) obj);
                    return Unit.INSTANCE;
                }

                public final void invoke(Placeable.PlacementScope placementScope) {
                }
            }, 4, null);
        }
        long jM1850constructorimpl = this.propagateMinConstraints ? j : Constraints.m1850constructorimpl((-8589934589L) & j);
        if (list.size() == 1) {
            final Measurable measurable = (Measurable) list.get(0);
            if (BoxKt.getMatchesParentSize(measurable)) {
                int iM1862getMinWidthimpl = Constraints.m1862getMinWidthimpl(j);
                iM1861getMinHeightimpl = Constraints.m1861getMinHeightimpl(j);
                i = iM1862getMinWidthimpl;
                placeableMo1284measureBRTryo0 = measurable.mo1284measureBRTryo0(Constraints.Companion.m1868fixedJhjzzOo(Constraints.m1862getMinWidthimpl(j), Constraints.m1861getMinHeightimpl(j)));
            } else {
                Placeable placeableMo1284measureBRTryo02 = measurable.mo1284measureBRTryo0(jM1850constructorimpl);
                int iMax = Math.max(Constraints.m1862getMinWidthimpl(j), placeableMo1284measureBRTryo02.getWidth());
                iM1861getMinHeightimpl = Math.max(Constraints.m1861getMinHeightimpl(j), placeableMo1284measureBRTryo02.getHeight());
                i = iMax;
                placeableMo1284measureBRTryo0 = placeableMo1284measureBRTryo02;
            }
            final int i2 = iM1861getMinHeightimpl;
            return MeasureScope.layout$default(measureScope, i, i2, null, new Function1() { // from class: androidx.compose.foundation.layout.BoxMeasurePolicy$measure$2
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
                    BoxKt.placeInBox(placementScope, placeableMo1284measureBRTryo0, measurable, measureScope.getLayoutDirection(), i, i2, this.alignment);
                }
            }, 4, null);
        }
        final Placeable[] placeableArr = new Placeable[list.size()];
        final Ref$IntRef ref$IntRef = new Ref$IntRef();
        ref$IntRef.element = Constraints.m1862getMinWidthimpl(j);
        final Ref$IntRef ref$IntRef2 = new Ref$IntRef();
        ref$IntRef2.element = Constraints.m1861getMinHeightimpl(j);
        int size = list.size();
        boolean z = false;
        for (int i3 = 0; i3 < size; i3++) {
            Measurable measurable2 = (Measurable) list.get(i3);
            if (BoxKt.getMatchesParentSize(measurable2)) {
                z = true;
            } else {
                Placeable placeableMo1284measureBRTryo03 = measurable2.mo1284measureBRTryo0(jM1850constructorimpl);
                placeableArr[i3] = placeableMo1284measureBRTryo03;
                ref$IntRef.element = Math.max(ref$IntRef.element, placeableMo1284measureBRTryo03.getWidth());
                ref$IntRef2.element = Math.max(ref$IntRef2.element, placeableMo1284measureBRTryo03.getHeight());
            }
        }
        if (z) {
            int i4 = ref$IntRef.element;
            int i5 = i4 != Integer.MAX_VALUE ? i4 : 0;
            int i6 = ref$IntRef2.element;
            long jConstraints = ConstraintsKt.Constraints(i5, i4, i6 != Integer.MAX_VALUE ? i6 : 0, i6);
            int size2 = list.size();
            for (int i7 = 0; i7 < size2; i7++) {
                Measurable measurable3 = (Measurable) list.get(i7);
                if (BoxKt.getMatchesParentSize(measurable3)) {
                    placeableArr[i7] = measurable3.mo1284measureBRTryo0(jConstraints);
                }
            }
        }
        return MeasureScope.layout$default(measureScope, ref$IntRef.element, ref$IntRef2.element, null, new Function1() { // from class: androidx.compose.foundation.layout.BoxMeasurePolicy$measure$5
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
                Placeable[] placeableArr2 = placeableArr;
                List list2 = list;
                MeasureScope measureScope2 = measureScope;
                Ref$IntRef ref$IntRef3 = ref$IntRef;
                Ref$IntRef ref$IntRef4 = ref$IntRef2;
                BoxMeasurePolicy boxMeasurePolicy = this;
                int length = placeableArr2.length;
                int i8 = 0;
                int i9 = 0;
                while (i8 < length) {
                    Placeable placeable = placeableArr2[i8];
                    placeable.getClass();
                    BoxKt.placeInBox(placementScope, placeable, (Measurable) list2.get(i9), measureScope2.getLayoutDirection(), ref$IntRef3.element, ref$IntRef4.element, boxMeasurePolicy.alignment);
                    i8++;
                    i9++;
                }
            }
        }, 4, null);
    }

    public String toString() {
        return "BoxMeasurePolicy(alignment=" + this.alignment + ", propagateMinConstraints=" + this.propagateMinConstraints + ')';
    }
}
