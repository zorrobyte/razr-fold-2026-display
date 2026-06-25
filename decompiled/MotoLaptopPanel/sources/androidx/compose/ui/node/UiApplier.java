package androidx.compose.ui.node;

import androidx.compose.runtime.AbstractApplier;

/* JADX INFO: compiled from: UiApplier.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class UiApplier extends AbstractApplier {
    public UiApplier(LayoutNode layoutNode) {
        super(layoutNode);
    }

    @Override // androidx.compose.runtime.Applier
    public void insertBottomUp(int i, LayoutNode layoutNode) {
        ((LayoutNode) getCurrent()).insertAt$ui_release(i, layoutNode);
    }

    @Override // androidx.compose.runtime.Applier
    public void insertTopDown(int i, LayoutNode layoutNode) {
    }

    @Override // androidx.compose.runtime.Applier
    public void move(int i, int i2, int i3) {
        ((LayoutNode) getCurrent()).move$ui_release(i, i2, i3);
    }

    @Override // androidx.compose.runtime.AbstractApplier
    protected void onClear() {
        ((LayoutNode) getRoot()).removeAll$ui_release();
    }

    @Override // androidx.compose.runtime.Applier
    public void onEndChanges() {
        super.onEndChanges();
        Owner owner$ui_release = ((LayoutNode) getRoot()).getOwner$ui_release();
        if (owner$ui_release != null) {
            owner$ui_release.onEndApplyChanges();
        }
    }

    @Override // androidx.compose.runtime.Applier
    public void remove(int i, int i2) {
        ((LayoutNode) getCurrent()).removeAt$ui_release(i, i2);
    }

    @Override // androidx.compose.runtime.Applier
    public void reuse() {
        ((LayoutNode) getCurrent()).onReuse();
    }
}
