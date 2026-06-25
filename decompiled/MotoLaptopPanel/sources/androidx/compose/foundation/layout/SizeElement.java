package androidx.compose.foundation.layout;

import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: Size.kt */
/* JADX INFO: loaded from: classes.dex */
final class SizeElement extends ModifierNodeElement {
    private final boolean enforceIncoming;
    private final Function1 inspectorInfo;
    private final float maxHeight;
    private final float maxWidth;
    private final float minHeight;
    private final float minWidth;

    private SizeElement(float f, float f2, float f3, float f4, boolean z, Function1 function1) {
        this.minWidth = f;
        this.minHeight = f2;
        this.maxWidth = f3;
        this.maxHeight = f4;
        this.enforceIncoming = z;
        this.inspectorInfo = function1;
    }

    public /* synthetic */ SizeElement(float f, float f2, float f3, float f4, boolean z, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? Dp.Companion.m1885getUnspecifiedD9Ej5fM() : f, (i & 2) != 0 ? Dp.Companion.m1885getUnspecifiedD9Ej5fM() : f2, (i & 4) != 0 ? Dp.Companion.m1885getUnspecifiedD9Ej5fM() : f3, (i & 8) != 0 ? Dp.Companion.m1885getUnspecifiedD9Ej5fM() : f4, z, function1, null);
    }

    public /* synthetic */ SizeElement(float f, float f2, float f3, float f4, boolean z, Function1 function1, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2, f3, f4, z, function1);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public SizeNode create() {
        return new SizeNode(this.minWidth, this.minHeight, this.maxWidth, this.maxHeight, this.enforceIncoming, null);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SizeElement)) {
            return false;
        }
        SizeElement sizeElement = (SizeElement) obj;
        return Dp.m1879equalsimpl0(this.minWidth, sizeElement.minWidth) && Dp.m1879equalsimpl0(this.minHeight, sizeElement.minHeight) && Dp.m1879equalsimpl0(this.maxWidth, sizeElement.maxWidth) && Dp.m1879equalsimpl0(this.maxHeight, sizeElement.maxHeight) && this.enforceIncoming == sizeElement.enforceIncoming;
    }

    public int hashCode() {
        return (((((((Dp.m1880hashCodeimpl(this.minWidth) * 31) + Dp.m1880hashCodeimpl(this.minHeight)) * 31) + Dp.m1880hashCodeimpl(this.maxWidth)) * 31) + Dp.m1880hashCodeimpl(this.maxHeight)) * 31) + Boolean.hashCode(this.enforceIncoming);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public void update(SizeNode sizeNode) {
        sizeNode.m184setMinWidth0680j_4(this.minWidth);
        sizeNode.m183setMinHeight0680j_4(this.minHeight);
        sizeNode.m182setMaxWidth0680j_4(this.maxWidth);
        sizeNode.m181setMaxHeight0680j_4(this.maxHeight);
        sizeNode.setEnforceIncoming(this.enforceIncoming);
    }
}
