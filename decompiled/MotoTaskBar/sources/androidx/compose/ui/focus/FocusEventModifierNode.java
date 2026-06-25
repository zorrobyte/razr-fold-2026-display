package androidx.compose.ui.focus;

import androidx.compose.ui.node.DelegatableNode;

/* JADX INFO: compiled from: FocusEventModifierNode.kt */
/* JADX INFO: loaded from: classes.dex */
public interface FocusEventModifierNode extends DelegatableNode {
    void onFocusEvent(FocusState focusState);
}
