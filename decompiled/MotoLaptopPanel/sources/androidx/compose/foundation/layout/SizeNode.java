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
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: Size.kt */
/* JADX INFO: loaded from: classes.dex */
final class SizeNode extends Modifier.Node implements LayoutModifierNode {
    private boolean enforceIncoming;
    private float maxHeight;
    private float maxWidth;
    private float minHeight;
    private float minWidth;

    private SizeNode(float f, float f2, float f3, float f4, boolean z) {
        this.minWidth = f;
        this.minHeight = f2;
        this.maxWidth = f3;
        this.maxHeight = f4;
        this.enforceIncoming = z;
    }

    public /* synthetic */ SizeNode(float f, float f2, float f3, float f4, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2, f3, f4, z);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0041  */
    /* JADX INFO: renamed from: getTargetConstraints-OenEA2s, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final long m180getTargetConstraintsOenEA2s(androidx.compose.ui.unit.Density r7) {
        /*
            r6 = this;
            float r0 = r6.maxWidth
            boolean r0 = java.lang.Float.isNaN(r0)
            r1 = 2147483647(0x7fffffff, float:NaN)
            r2 = 0
            if (r0 != 0) goto L16
            float r0 = r6.maxWidth
            int r0 = r7.mo141roundToPx0680j_4(r0)
            if (r0 >= 0) goto L17
            r0 = r2
            goto L17
        L16:
            r0 = r1
        L17:
            float r3 = r6.maxHeight
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 != 0) goto L29
            float r3 = r6.maxHeight
            int r3 = r7.mo141roundToPx0680j_4(r3)
            if (r3 >= 0) goto L2a
            r3 = r2
            goto L2a
        L29:
            r3 = r1
        L2a:
            float r4 = r6.minWidth
            boolean r4 = java.lang.Float.isNaN(r4)
            if (r4 != 0) goto L41
            float r4 = r6.minWidth
            int r4 = r7.mo141roundToPx0680j_4(r4)
            if (r4 >= 0) goto L3b
            r4 = r2
        L3b:
            if (r4 <= r0) goto L3e
            r4 = r0
        L3e:
            if (r4 == r1) goto L41
            goto L42
        L41:
            r4 = r2
        L42:
            float r5 = r6.minHeight
            boolean r5 = java.lang.Float.isNaN(r5)
            if (r5 != 0) goto L59
            float r6 = r6.minHeight
            int r6 = r7.mo141roundToPx0680j_4(r6)
            if (r6 >= 0) goto L53
            r6 = r2
        L53:
            if (r6 <= r3) goto L56
            r6 = r3
        L56:
            if (r6 == r1) goto L59
            r2 = r6
        L59:
            long r6 = androidx.compose.ui.unit.ConstraintsKt.Constraints(r4, r0, r2, r3)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.layout.SizeNode.m180getTargetConstraintsOenEA2s(androidx.compose.ui.unit.Density):long");
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* JADX INFO: renamed from: measure-3p2s80s */
    public MeasureResult mo34measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        int iM1862getMinWidthimpl;
        int iM1860getMaxWidthimpl;
        int iM1861getMinHeightimpl;
        int iM1859getMaxHeightimpl;
        long jConstraints;
        long jM180getTargetConstraintsOenEA2s = m180getTargetConstraintsOenEA2s(measureScope);
        if (this.enforceIncoming) {
            jConstraints = ConstraintsKt.m1870constrainN9IONVI(j, jM180getTargetConstraintsOenEA2s);
        } else {
            if (Float.isNaN(this.minWidth)) {
                iM1862getMinWidthimpl = Constraints.m1862getMinWidthimpl(j);
                int iM1860getMaxWidthimpl2 = Constraints.m1860getMaxWidthimpl(jM180getTargetConstraintsOenEA2s);
                if (iM1862getMinWidthimpl > iM1860getMaxWidthimpl2) {
                    iM1862getMinWidthimpl = iM1860getMaxWidthimpl2;
                }
            } else {
                iM1862getMinWidthimpl = Constraints.m1862getMinWidthimpl(jM180getTargetConstraintsOenEA2s);
            }
            if (Float.isNaN(this.maxWidth)) {
                iM1860getMaxWidthimpl = Constraints.m1860getMaxWidthimpl(j);
                int iM1862getMinWidthimpl2 = Constraints.m1862getMinWidthimpl(jM180getTargetConstraintsOenEA2s);
                if (iM1860getMaxWidthimpl < iM1862getMinWidthimpl2) {
                    iM1860getMaxWidthimpl = iM1862getMinWidthimpl2;
                }
            } else {
                iM1860getMaxWidthimpl = Constraints.m1860getMaxWidthimpl(jM180getTargetConstraintsOenEA2s);
            }
            if (Float.isNaN(this.minHeight)) {
                iM1861getMinHeightimpl = Constraints.m1861getMinHeightimpl(j);
                int iM1859getMaxHeightimpl2 = Constraints.m1859getMaxHeightimpl(jM180getTargetConstraintsOenEA2s);
                if (iM1861getMinHeightimpl > iM1859getMaxHeightimpl2) {
                    iM1861getMinHeightimpl = iM1859getMaxHeightimpl2;
                }
            } else {
                iM1861getMinHeightimpl = Constraints.m1861getMinHeightimpl(jM180getTargetConstraintsOenEA2s);
            }
            if (Float.isNaN(this.maxHeight)) {
                iM1859getMaxHeightimpl = Constraints.m1859getMaxHeightimpl(j);
                int iM1861getMinHeightimpl2 = Constraints.m1861getMinHeightimpl(jM180getTargetConstraintsOenEA2s);
                if (iM1859getMaxHeightimpl < iM1861getMinHeightimpl2) {
                    iM1859getMaxHeightimpl = iM1861getMinHeightimpl2;
                }
            } else {
                iM1859getMaxHeightimpl = Constraints.m1859getMaxHeightimpl(jM180getTargetConstraintsOenEA2s);
            }
            jConstraints = ConstraintsKt.Constraints(iM1862getMinWidthimpl, iM1860getMaxWidthimpl, iM1861getMinHeightimpl, iM1859getMaxHeightimpl);
        }
        final Placeable placeableMo1284measureBRTryo0 = measurable.mo1284measureBRTryo0(jConstraints);
        return MeasureScope.layout$default(measureScope, placeableMo1284measureBRTryo0.getWidth(), placeableMo1284measureBRTryo0.getHeight(), null, new Function1() { // from class: androidx.compose.foundation.layout.SizeNode$measure$1
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

    public final void setEnforceIncoming(boolean z) {
        this.enforceIncoming = z;
    }

    /* JADX INFO: renamed from: setMaxHeight-0680j_4, reason: not valid java name */
    public final void m181setMaxHeight0680j_4(float f) {
        this.maxHeight = f;
    }

    /* JADX INFO: renamed from: setMaxWidth-0680j_4, reason: not valid java name */
    public final void m182setMaxWidth0680j_4(float f) {
        this.maxWidth = f;
    }

    /* JADX INFO: renamed from: setMinHeight-0680j_4, reason: not valid java name */
    public final void m183setMinHeight0680j_4(float f) {
        this.minHeight = f;
    }

    /* JADX INFO: renamed from: setMinWidth-0680j_4, reason: not valid java name */
    public final void m184setMinWidth0680j_4(float f) {
        this.minWidth = f;
    }
}
