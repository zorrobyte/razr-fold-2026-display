package androidx.compose.ui.modifier;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.node.BackwardsCompatNode;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.Owner;

/* JADX INFO: compiled from: ModifierLocalManager.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ModifierLocalManager {
    private final Owner owner;
    private final MutableVector inserted = new MutableVector(new BackwardsCompatNode[16], 0);
    private final MutableVector insertedLocal = new MutableVector(new ModifierLocal[16], 0);
    private final MutableVector removed = new MutableVector(new LayoutNode[16], 0);
    private final MutableVector removedLocal = new MutableVector(new ModifierLocal[16], 0);

    public ModifierLocalManager(Owner owner) {
        this.owner = owner;
    }
}
