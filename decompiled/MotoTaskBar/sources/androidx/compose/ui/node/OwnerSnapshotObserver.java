package androidx.compose.ui.node;

import androidx.compose.runtime.snapshots.SnapshotStateObserver;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: OwnerSnapshotObserver.kt */
/* JADX INFO: loaded from: classes.dex */
public final class OwnerSnapshotObserver {
    public static final int $stable = SnapshotStateObserver.$stable;
    private final SnapshotStateObserver observer;
    private final Function1 onCommitAffectingLookaheadMeasure = new Function1() { // from class: androidx.compose.ui.node.OwnerSnapshotObserver$onCommitAffectingLookaheadMeasure$1
        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((LayoutNode) obj);
            return Unit.INSTANCE;
        }

        public final void invoke(LayoutNode layoutNode) {
            if (layoutNode.isValidOwnerScope()) {
                LayoutNode.requestLookaheadRemeasure$ui_release$default(layoutNode, false, false, false, 7, null);
            }
        }
    };
    private final Function1 onCommitAffectingMeasure = new Function1() { // from class: androidx.compose.ui.node.OwnerSnapshotObserver$onCommitAffectingMeasure$1
        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((LayoutNode) obj);
            return Unit.INSTANCE;
        }

        public final void invoke(LayoutNode layoutNode) {
            if (layoutNode.isValidOwnerScope()) {
                LayoutNode.requestRemeasure$ui_release$default(layoutNode, false, false, false, 7, null);
            }
        }
    };
    private final Function1 onCommitAffectingSemantics = new Function1() { // from class: androidx.compose.ui.node.OwnerSnapshotObserver$onCommitAffectingSemantics$1
        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((LayoutNode) obj);
            return Unit.INSTANCE;
        }

        public final void invoke(LayoutNode layoutNode) {
            if (layoutNode.isValidOwnerScope()) {
                layoutNode.invalidateSemantics$ui_release();
            }
        }
    };
    private final Function1 onCommitAffectingLayout = new Function1() { // from class: androidx.compose.ui.node.OwnerSnapshotObserver$onCommitAffectingLayout$1
        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((LayoutNode) obj);
            return Unit.INSTANCE;
        }

        public final void invoke(LayoutNode layoutNode) {
            if (layoutNode.isValidOwnerScope()) {
                LayoutNode.requestRelayout$ui_release$default(layoutNode, false, 1, null);
            }
        }
    };
    private final Function1 onCommitAffectingLayoutModifier = new Function1() { // from class: androidx.compose.ui.node.OwnerSnapshotObserver$onCommitAffectingLayoutModifier$1
        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((LayoutNode) obj);
            return Unit.INSTANCE;
        }

        public final void invoke(LayoutNode layoutNode) {
            if (layoutNode.isValidOwnerScope()) {
                LayoutNode.requestRelayout$ui_release$default(layoutNode, false, 1, null);
            }
        }
    };
    private final Function1 onCommitAffectingLayoutModifierInLookahead = new Function1() { // from class: androidx.compose.ui.node.OwnerSnapshotObserver$onCommitAffectingLayoutModifierInLookahead$1
        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((LayoutNode) obj);
            return Unit.INSTANCE;
        }

        public final void invoke(LayoutNode layoutNode) {
            if (layoutNode.isValidOwnerScope()) {
                LayoutNode.requestLookaheadRelayout$ui_release$default(layoutNode, false, 1, null);
            }
        }
    };
    private final Function1 onCommitAffectingLookahead = new Function1() { // from class: androidx.compose.ui.node.OwnerSnapshotObserver$onCommitAffectingLookahead$1
        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((LayoutNode) obj);
            return Unit.INSTANCE;
        }

        public final void invoke(LayoutNode layoutNode) {
            if (layoutNode.isValidOwnerScope()) {
                LayoutNode.requestLookaheadRelayout$ui_release$default(layoutNode, false, 1, null);
            }
        }
    };

    public OwnerSnapshotObserver(Function1 function1) {
        this.observer = new SnapshotStateObserver(function1);
    }

    public static /* synthetic */ void observeLayoutModifierSnapshotReads$ui_release$default(OwnerSnapshotObserver ownerSnapshotObserver, LayoutNode layoutNode, boolean z, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        ownerSnapshotObserver.observeLayoutModifierSnapshotReads$ui_release(layoutNode, z, function0);
    }

    public static /* synthetic */ void observeLayoutSnapshotReads$ui_release$default(OwnerSnapshotObserver ownerSnapshotObserver, LayoutNode layoutNode, boolean z, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        ownerSnapshotObserver.observeLayoutSnapshotReads$ui_release(layoutNode, z, function0);
    }

    public static /* synthetic */ void observeMeasureSnapshotReads$ui_release$default(OwnerSnapshotObserver ownerSnapshotObserver, LayoutNode layoutNode, boolean z, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        ownerSnapshotObserver.observeMeasureSnapshotReads$ui_release(layoutNode, z, function0);
    }

    public final void clearInvalidObservations$ui_release() {
        this.observer.clearIf(new Function1() { // from class: androidx.compose.ui.node.OwnerSnapshotObserver$clearInvalidObservations$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                obj.getClass();
                return Boolean.valueOf(!((OwnerScope) obj).isValidOwnerScope());
            }
        });
    }

    public final void observeLayoutModifierSnapshotReads$ui_release(LayoutNode layoutNode, boolean z, Function0 function0) {
        if (!z || layoutNode.getLookaheadRoot$ui_release() == null) {
            observeReads$ui_release(layoutNode, this.onCommitAffectingLayoutModifier, function0);
        } else {
            observeReads$ui_release(layoutNode, this.onCommitAffectingLayoutModifierInLookahead, function0);
        }
    }

    public final void observeLayoutSnapshotReads$ui_release(LayoutNode layoutNode, boolean z, Function0 function0) {
        if (!z || layoutNode.getLookaheadRoot$ui_release() == null) {
            observeReads$ui_release(layoutNode, this.onCommitAffectingLayout, function0);
        } else {
            observeReads$ui_release(layoutNode, this.onCommitAffectingLookahead, function0);
        }
    }

    public final void observeMeasureSnapshotReads$ui_release(LayoutNode layoutNode, boolean z, Function0 function0) {
        if (!z || layoutNode.getLookaheadRoot$ui_release() == null) {
            observeReads$ui_release(layoutNode, this.onCommitAffectingMeasure, function0);
        } else {
            observeReads$ui_release(layoutNode, this.onCommitAffectingLookaheadMeasure, function0);
        }
    }

    public final void observeReads$ui_release(OwnerScope ownerScope, Function1 function1, Function0 function0) {
        this.observer.observeReads(ownerScope, function1, function0);
    }

    public final void observeSemanticsReads$ui_release(LayoutNode layoutNode, Function0 function0) {
        observeReads$ui_release(layoutNode, this.onCommitAffectingSemantics, function0);
    }

    public final void startObserving$ui_release() {
        this.observer.start();
    }

    public final void stopObserving$ui_release() {
        this.observer.stop();
        this.observer.clear();
    }
}
