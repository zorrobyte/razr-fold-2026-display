package androidx.compose.ui.node;

import androidx.compose.runtime.CompositionLocal;
import androidx.compose.ui.internal.InlineClassHelperKt;

/* JADX INFO: compiled from: CompositionLocalConsumerModifierNode.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class CompositionLocalConsumerModifierNodeKt {
    public static final Object currentValueOf(CompositionLocalConsumerModifierNode compositionLocalConsumerModifierNode, CompositionLocal compositionLocal) {
        if (!compositionLocalConsumerModifierNode.getNode().isAttached()) {
            InlineClassHelperKt.throwIllegalStateException("Cannot read CompositionLocal because the Modifier node is not currently attached.");
        }
        return DelegatableNodeKt.requireLayoutNode(compositionLocalConsumerModifierNode).getCompositionLocalMap().get(compositionLocal);
    }
}
