package androidx.compose.foundation.layout;

import androidx.compose.foundation.layout.internal.InlineClassHelperKt;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: Padding.kt */
/* JADX INFO: loaded from: classes.dex */
final class PaddingElement extends ModifierNodeElement {
    private float bottom;
    private float end;
    private final Function1 inspectorInfo;
    private boolean rtlAware;
    private float start;
    private float top;

    private PaddingElement(float f, float f2, float f3, float f4, boolean z, Function1 function1) {
        this.start = f;
        this.top = f2;
        this.end = f3;
        this.bottom = f4;
        this.rtlAware = z;
        this.inspectorInfo = function1;
        boolean z2 = true;
        boolean z3 = f >= 0.0f || Float.isNaN(f);
        float f5 = this.top;
        boolean z4 = z3 & (f5 >= 0.0f || Float.isNaN(f5));
        float f6 = this.end;
        boolean z5 = z4 & (f6 >= 0.0f || Float.isNaN(f6));
        float f7 = this.bottom;
        if (f7 < 0.0f && !Float.isNaN(f7)) {
            z2 = false;
        }
        if (!z5 || !z2) {
            InlineClassHelperKt.throwIllegalArgumentException("Padding must be non-negative");
        }
    }

    public /* synthetic */ PaddingElement(float f, float f2, float f3, float f4, boolean z, Function1 function1, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2, f3, f4, z, function1);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public PaddingNode create() {
        return new PaddingNode(this.start, this.top, this.end, this.bottom, this.rtlAware, null);
    }

    public boolean equals(Object obj) {
        PaddingElement paddingElement = obj instanceof PaddingElement ? (PaddingElement) obj : null;
        return paddingElement != null && Dp.m1879equalsimpl0(this.start, paddingElement.start) && Dp.m1879equalsimpl0(this.top, paddingElement.top) && Dp.m1879equalsimpl0(this.end, paddingElement.end) && Dp.m1879equalsimpl0(this.bottom, paddingElement.bottom) && this.rtlAware == paddingElement.rtlAware;
    }

    public int hashCode() {
        return (((((((Dp.m1880hashCodeimpl(this.start) * 31) + Dp.m1880hashCodeimpl(this.top)) * 31) + Dp.m1880hashCodeimpl(this.end)) * 31) + Dp.m1880hashCodeimpl(this.bottom)) * 31) + Boolean.hashCode(this.rtlAware);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public void update(PaddingNode paddingNode) {
        paddingNode.m169setStart0680j_4(this.start);
        paddingNode.m170setTop0680j_4(this.top);
        paddingNode.m168setEnd0680j_4(this.end);
        paddingNode.m167setBottom0680j_4(this.bottom);
        paddingNode.setRtlAware(this.rtlAware);
    }
}
