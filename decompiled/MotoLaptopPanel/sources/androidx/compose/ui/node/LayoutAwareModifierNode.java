package androidx.compose.ui.node;

import androidx.compose.ui.layout.LayoutCoordinates;

/* JADX INFO: compiled from: LayoutAwareModifierNode.kt */
/* JADX INFO: loaded from: classes.dex */
public interface LayoutAwareModifierNode extends DelegatableNode {
    default void onPlaced(LayoutCoordinates layoutCoordinates) {
    }

    /* JADX INFO: renamed from: onRemeasured-ozmzZPI */
    void mo673onRemeasuredozmzZPI(long j);
}
