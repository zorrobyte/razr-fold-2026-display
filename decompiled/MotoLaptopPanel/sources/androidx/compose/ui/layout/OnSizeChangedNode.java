package androidx.compose.ui.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.LayoutAwareModifierNode;
import androidx.compose.ui.unit.IntSize;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: OnRemeasuredModifier.kt */
/* JADX INFO: loaded from: classes.dex */
final class OnSizeChangedNode extends Modifier.Node implements LayoutAwareModifierNode {
    private Function1 onSizeChanged;
    private long previousSize;
    private final boolean shouldAutoInvalidate = true;

    public OnSizeChangedNode(Function1 function1) {
        this.onSizeChanged = function1;
        long j = Integer.MIN_VALUE;
        this.previousSize = IntSize.m1919constructorimpl((j & 4294967295L) | (j << 32));
    }

    @Override // androidx.compose.ui.Modifier.Node
    public boolean getShouldAutoInvalidate() {
        return this.shouldAutoInvalidate;
    }

    @Override // androidx.compose.ui.node.LayoutAwareModifierNode
    /* JADX INFO: renamed from: onRemeasured-ozmzZPI */
    public void mo673onRemeasuredozmzZPI(long j) {
        if (IntSize.m1921equalsimpl0(this.previousSize, j)) {
            return;
        }
        this.onSizeChanged.invoke(IntSize.m1918boximpl(j));
        this.previousSize = j;
    }

    public final void update(Function1 function1) {
        this.onSizeChanged = function1;
        long j = Integer.MIN_VALUE;
        this.previousSize = IntSize.m1919constructorimpl((j & 4294967295L) | (j << 32));
    }
}
