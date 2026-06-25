package androidx.compose.ui.draw;

import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.drawscope.ContentDrawScope;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.layout.ScaleFactorKt;
import androidx.compose.ui.node.DrawModifierNode;
import androidx.compose.ui.node.LayoutModifierNode;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: PainterModifier.kt */
/* JADX INFO: loaded from: classes.dex */
final class PainterNode extends Modifier.Node implements LayoutModifierNode, DrawModifierNode {
    private Alignment alignment;
    private float alpha;
    private ColorFilter colorFilter;
    private ContentScale contentScale;
    private Painter painter;
    private boolean sizeToIntrinsics;

    public PainterNode(Painter painter, boolean z, Alignment alignment, ContentScale contentScale, float f, ColorFilter colorFilter) {
        this.painter = painter;
        this.sizeToIntrinsics = z;
        this.alignment = alignment;
        this.contentScale = contentScale;
        this.alpha = f;
        this.colorFilter = colorFilter;
    }

    /* JADX INFO: renamed from: calculateScaledSize-E7KxVPU, reason: not valid java name */
    private final long m679calculateScaledSizeE7KxVPU(long j) {
        if (!getUseIntrinsicSize()) {
            return j;
        }
        long jM783constructorimpl = Size.m783constructorimpl((((long) Float.floatToRawIntBits(!m681hasSpecifiedAndFiniteWidthuvyYCjk(this.painter.mo1122getIntrinsicSizeNHjbRc()) ? Float.intBitsToFloat((int) (j >> 32)) : Float.intBitsToFloat((int) (this.painter.mo1122getIntrinsicSizeNHjbRc() >> 32)))) << 32) | (((long) Float.floatToRawIntBits(!m680hasSpecifiedAndFiniteHeightuvyYCjk(this.painter.mo1122getIntrinsicSizeNHjbRc()) ? Float.intBitsToFloat((int) (j & 4294967295L)) : Float.intBitsToFloat((int) (this.painter.mo1122getIntrinsicSizeNHjbRc() & 4294967295L)))) & 4294967295L));
        return (Float.intBitsToFloat((int) (j >> 32)) == 0.0f || Float.intBitsToFloat((int) (j & 4294967295L)) == 0.0f) ? Size.Companion.m794getZeroNHjbRc() : ScaleFactorKt.m1300timesUQTWf7w(jM783constructorimpl, this.contentScale.mo1273computeScaleFactorH7hwNQA(jM783constructorimpl, j));
    }

    private final boolean getUseIntrinsicSize() {
        return this.sizeToIntrinsics && this.painter.mo1122getIntrinsicSizeNHjbRc() != 9205357640488583168L;
    }

    /* JADX INFO: renamed from: hasSpecifiedAndFiniteHeight-uvyYCjk, reason: not valid java name */
    private final boolean m680hasSpecifiedAndFiniteHeightuvyYCjk(long j) {
        return !Size.m785equalsimpl0(j, Size.Companion.m793getUnspecifiedNHjbRc()) && (Float.floatToRawIntBits(Float.intBitsToFloat((int) (j & 4294967295L))) & Integer.MAX_VALUE) < 2139095040;
    }

    /* JADX INFO: renamed from: hasSpecifiedAndFiniteWidth-uvyYCjk, reason: not valid java name */
    private final boolean m681hasSpecifiedAndFiniteWidthuvyYCjk(long j) {
        return !Size.m785equalsimpl0(j, Size.Companion.m793getUnspecifiedNHjbRc()) && (Float.floatToRawIntBits(Float.intBitsToFloat((int) (j >> 32))) & Integer.MAX_VALUE) < 2139095040;
    }

    /* JADX INFO: renamed from: modifyConstraints-ZezNO4M, reason: not valid java name */
    private final long m682modifyConstraintsZezNO4M(long j) {
        boolean z = false;
        boolean z2 = Constraints.m1856getHasBoundedWidthimpl(j) && Constraints.m1855getHasBoundedHeightimpl(j);
        if (Constraints.m1858getHasFixedWidthimpl(j) && Constraints.m1857getHasFixedHeightimpl(j)) {
            z = true;
        }
        if ((!getUseIntrinsicSize() && z2) || z) {
            return Constraints.m1852copyZbe2FdA$default(j, Constraints.m1860getMaxWidthimpl(j), 0, Constraints.m1859getMaxHeightimpl(j), 0, 10, null);
        }
        long jMo1122getIntrinsicSizeNHjbRc = this.painter.mo1122getIntrinsicSizeNHjbRc();
        int iRound = m681hasSpecifiedAndFiniteWidthuvyYCjk(jMo1122getIntrinsicSizeNHjbRc) ? Math.round(Float.intBitsToFloat((int) (jMo1122getIntrinsicSizeNHjbRc >> 32))) : Constraints.m1862getMinWidthimpl(j);
        int iRound2 = m680hasSpecifiedAndFiniteHeightuvyYCjk(jMo1122getIntrinsicSizeNHjbRc) ? Math.round(Float.intBitsToFloat((int) (jMo1122getIntrinsicSizeNHjbRc & 4294967295L))) : Constraints.m1861getMinHeightimpl(j);
        long jM679calculateScaledSizeE7KxVPU = m679calculateScaledSizeE7KxVPU(Size.m783constructorimpl((((long) Float.floatToRawIntBits(ConstraintsKt.m1871constrainHeightK40F9xA(j, iRound2))) & 4294967295L) | (((long) Float.floatToRawIntBits(ConstraintsKt.m1872constrainWidthK40F9xA(j, iRound))) << 32)));
        return Constraints.m1852copyZbe2FdA$default(j, ConstraintsKt.m1872constrainWidthK40F9xA(j, Math.round(Float.intBitsToFloat((int) (jM679calculateScaledSizeE7KxVPU >> 32)))), 0, ConstraintsKt.m1871constrainHeightK40F9xA(j, Math.round(Float.intBitsToFloat((int) (jM679calculateScaledSizeE7KxVPU & 4294967295L)))), 0, 10, null);
    }

    @Override // androidx.compose.ui.node.DrawModifierNode
    public void draw(ContentDrawScope contentDrawScope) {
        long jMo1122getIntrinsicSizeNHjbRc = this.painter.mo1122getIntrinsicSizeNHjbRc();
        long jM783constructorimpl = Size.m783constructorimpl((((long) Float.floatToRawIntBits(m681hasSpecifiedAndFiniteWidthuvyYCjk(jMo1122getIntrinsicSizeNHjbRc) ? Float.intBitsToFloat((int) (jMo1122getIntrinsicSizeNHjbRc >> 32)) : Float.intBitsToFloat((int) (contentDrawScope.mo1082getSizeNHjbRc() >> 32)))) << 32) | (((long) Float.floatToRawIntBits(m680hasSpecifiedAndFiniteHeightuvyYCjk(jMo1122getIntrinsicSizeNHjbRc) ? Float.intBitsToFloat((int) (jMo1122getIntrinsicSizeNHjbRc & 4294967295L)) : Float.intBitsToFloat((int) (contentDrawScope.mo1082getSizeNHjbRc() & 4294967295L)))) & 4294967295L));
        long jM794getZeroNHjbRc = (Float.intBitsToFloat((int) (contentDrawScope.mo1082getSizeNHjbRc() >> 32)) == 0.0f || Float.intBitsToFloat((int) (contentDrawScope.mo1082getSizeNHjbRc() & 4294967295L)) == 0.0f) ? Size.Companion.m794getZeroNHjbRc() : ScaleFactorKt.m1300timesUQTWf7w(jM783constructorimpl, this.contentScale.mo1273computeScaleFactorH7hwNQA(jM783constructorimpl, contentDrawScope.mo1082getSizeNHjbRc()));
        long jMo662alignKFBX0sM = this.alignment.mo662alignKFBX0sM(IntSize.m1919constructorimpl((((long) Math.round(Float.intBitsToFloat((int) (jM794getZeroNHjbRc & 4294967295L)))) & 4294967295L) | (((long) Math.round(Float.intBitsToFloat((int) (jM794getZeroNHjbRc >> 32)))) << 32)), IntSize.m1919constructorimpl((((long) Math.round(Float.intBitsToFloat((int) (contentDrawScope.mo1082getSizeNHjbRc() & 4294967295L)))) & 4294967295L) | (((long) Math.round(Float.intBitsToFloat((int) (contentDrawScope.mo1082getSizeNHjbRc() >> 32)))) << 32)), contentDrawScope.getLayoutDirection());
        float fM1905getXimpl = IntOffset.m1905getXimpl(jMo662alignKFBX0sM);
        float fM1906getYimpl = IntOffset.m1906getYimpl(jMo662alignKFBX0sM);
        contentDrawScope.getDrawContext().getTransform().translate(fM1905getXimpl, fM1906getYimpl);
        try {
            this.painter.m1123drawx_KDEd0(contentDrawScope, jM794getZeroNHjbRc, this.alpha, this.colorFilter);
            contentDrawScope.getDrawContext().getTransform().translate(-fM1905getXimpl, -fM1906getYimpl);
            contentDrawScope.drawContent();
        } catch (Throwable th) {
            contentDrawScope.getDrawContext().getTransform().translate(-fM1905getXimpl, -fM1906getYimpl);
            throw th;
        }
    }

    public final Painter getPainter() {
        return this.painter;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public boolean getShouldAutoInvalidate() {
        return false;
    }

    public final boolean getSizeToIntrinsics() {
        return this.sizeToIntrinsics;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* JADX INFO: renamed from: measure-3p2s80s */
    public MeasureResult mo34measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        final Placeable placeableMo1284measureBRTryo0 = measurable.mo1284measureBRTryo0(m682modifyConstraintsZezNO4M(j));
        return MeasureScope.layout$default(measureScope, placeableMo1284measureBRTryo0.getWidth(), placeableMo1284measureBRTryo0.getHeight(), null, new Function1() { // from class: androidx.compose.ui.draw.PainterNode$measure$1
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

    public final void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public final void setAlpha(float f) {
        this.alpha = f;
    }

    public final void setColorFilter(ColorFilter colorFilter) {
        this.colorFilter = colorFilter;
    }

    public final void setContentScale(ContentScale contentScale) {
        this.contentScale = contentScale;
    }

    public final void setPainter(Painter painter) {
        this.painter = painter;
    }

    public final void setSizeToIntrinsics(boolean z) {
        this.sizeToIntrinsics = z;
    }

    public String toString() {
        return "PainterModifier(painter=" + this.painter + ", sizeToIntrinsics=" + this.sizeToIntrinsics + ", alignment=" + this.alignment + ", alpha=" + this.alpha + ", colorFilter=" + this.colorFilter + ')';
    }
}
