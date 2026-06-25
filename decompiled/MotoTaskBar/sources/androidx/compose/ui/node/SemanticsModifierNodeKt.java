package androidx.compose.ui.node;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.semantics.SemanticsActions;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsConfigurationKt;

/* JADX INFO: compiled from: SemanticsModifierNode.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SemanticsModifierNodeKt {
    public static final boolean getUseMinimumTouchTarget(SemanticsConfiguration semanticsConfiguration) {
        return SemanticsConfigurationKt.getOrNull(semanticsConfiguration, SemanticsActions.INSTANCE.getOnClick()) != null;
    }

    public static final Rect touchBoundsInRoot(Modifier.Node node, boolean z) {
        return !node.getNode().isAttached() ? Rect.Companion.getZero() : !z ? LayoutCoordinatesKt.boundsInRoot(DelegatableNodeKt.m562requireCoordinator64DMado(node, NodeKind.m658constructorimpl(8))) : DelegatableNodeKt.m562requireCoordinator64DMado(node, NodeKind.m658constructorimpl(8)).touchBoundsInRoot();
    }
}
