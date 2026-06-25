package androidx.compose.ui.relocation;

import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.node.DelegatableNode;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: BringIntoViewModifierNode.kt */
/* JADX INFO: loaded from: classes.dex */
public interface BringIntoViewModifierNode extends DelegatableNode {
    Object bringIntoView(LayoutCoordinates layoutCoordinates, Function0 function0, Continuation continuation);
}
