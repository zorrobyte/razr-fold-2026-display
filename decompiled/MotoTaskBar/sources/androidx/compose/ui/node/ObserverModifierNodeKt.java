package androidx.compose.ui.node;

import androidx.compose.ui.Modifier;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: ObserverModifierNode.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ObserverModifierNodeKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static final void observeReads(Modifier.Node node, Function0 function0) {
        ObserverNodeOwnerScope ownerScope$ui_release = node.getOwnerScope$ui_release();
        if (ownerScope$ui_release == null) {
            ownerScope$ui_release = new ObserverNodeOwnerScope((ObserverModifierNode) node);
            node.setOwnerScope$ui_release(ownerScope$ui_release);
        }
        DelegatableNodeKt.requireOwner(node).getSnapshotObserver().observeReads$ui_release(ownerScope$ui_release, ObserverNodeOwnerScope.Companion.getOnObserveReadsChanged$ui_release(), function0);
    }
}
