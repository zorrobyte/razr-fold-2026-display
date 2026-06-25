package androidx.compose.ui.node;

/* JADX INFO: compiled from: DepthSortedSet.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DepthSortedSetsForDifferentPasses {
    private final DepthSortedSet lookaheadSet;
    private final DepthSortedSet set;

    public DepthSortedSetsForDifferentPasses(boolean z) {
        this.lookaheadSet = new DepthSortedSet(z);
        this.set = new DepthSortedSet(z);
    }

    public final void add(LayoutNode layoutNode, boolean z) {
        if (z) {
            this.lookaheadSet.add(layoutNode);
            this.set.add(layoutNode);
        } else {
            if (this.lookaheadSet.contains(layoutNode)) {
                return;
            }
            this.set.add(layoutNode);
        }
    }

    public final boolean contains(LayoutNode layoutNode) {
        return this.lookaheadSet.contains(layoutNode) || this.set.contains(layoutNode);
    }

    public final boolean contains(LayoutNode layoutNode, boolean z) {
        boolean zContains = this.lookaheadSet.contains(layoutNode);
        return z ? zContains : zContains || this.set.contains(layoutNode);
    }

    public final boolean isEmpty() {
        return this.set.isEmpty() && this.lookaheadSet.isEmpty();
    }

    public final boolean isEmpty(boolean z) {
        return (z ? this.lookaheadSet : this.set).isEmpty();
    }

    public final boolean isNotEmpty() {
        return !isEmpty();
    }

    public final boolean remove(LayoutNode layoutNode) {
        return this.set.remove(layoutNode) || this.lookaheadSet.remove(layoutNode);
    }
}
