package androidx.compose.ui.node;

import androidx.compose.ui.layout.LayoutCoordinates;

/* JADX INFO: compiled from: PointerInputModifierNode.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class PointerInputModifierNodeKt {
    public static final LayoutCoordinates getLayoutCoordinates(PointerInputModifierNode pointerInputModifierNode) {
        return DelegatableNodeKt.m562requireCoordinator64DMado(pointerInputModifierNode, NodeKind.m658constructorimpl(16));
    }
}
