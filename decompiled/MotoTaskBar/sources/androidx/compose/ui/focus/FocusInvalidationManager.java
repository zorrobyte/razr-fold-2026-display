package androidx.compose.ui.focus;

import androidx.collection.MutableScatterSet;
import androidx.collection.ScatterSetKt;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.NodeKind;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* JADX INFO: compiled from: FocusInvalidationManager.kt */
/* JADX INFO: loaded from: classes.dex */
public final class FocusInvalidationManager {
    private final Function0 activeFocusTargetNodeFetcher;
    private final Function0 invalidateOwnerFocusState;
    private boolean isInvalidationScheduled;
    private final Function1 onRequestApplyChangesListener;
    private final Function0 rootFocusStateFetcher;
    private final MutableScatterSet focusTargetNodes = ScatterSetKt.mutableScatterSetOf();
    private final MutableScatterSet focusEventNodes = ScatterSetKt.mutableScatterSetOf();
    private final List focusTargetNodesLegacy = new ArrayList();
    private final List focusEventNodesLegacy = new ArrayList();
    private final List focusPropertiesNodesLegacy = new ArrayList();
    private final List focusTargetsWithInvalidatedFocusEventsLegacy = new ArrayList();

    /* JADX INFO: renamed from: androidx.compose.ui.focus.FocusInvalidationManager$scheduleInvalidationLegacy$1, reason: invalid class name */
    /* JADX INFO: compiled from: FocusInvalidationManager.kt */
    final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function0 {
        AnonymousClass1(Object obj) {
            super(0, obj, FocusInvalidationManager.class, "invalidateNodes", "invalidateNodes()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        /* JADX INFO: renamed from: invoke */
        public /* bridge */ /* synthetic */ Object mo2224invoke() {
            m133invoke();
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
        public final void m133invoke() {
            ((FocusInvalidationManager) this.receiver).invalidateNodes();
        }
    }

    /* JADX INFO: renamed from: androidx.compose.ui.focus.FocusInvalidationManager$setUpOnRequestApplyChangesListener$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FocusInvalidationManager.kt */
    final /* synthetic */ class C00591 extends FunctionReferenceImpl implements Function0 {
        C00591(Object obj) {
            super(0, obj, FocusInvalidationManager.class, "invalidateNodes", "invalidateNodes()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        /* JADX INFO: renamed from: invoke */
        public /* bridge */ /* synthetic */ Object mo2224invoke() {
            m134invoke();
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
        public final void m134invoke() {
            ((FocusInvalidationManager) this.receiver).invalidateNodes();
        }
    }

    public FocusInvalidationManager(Function1 function1, Function0 function0, Function0 function02, Function0 function03) {
        this.onRequestApplyChangesListener = function1;
        this.invalidateOwnerFocusState = function0;
        this.rootFocusStateFetcher = function02;
        this.activeFocusTargetNodeFetcher = function03;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void invalidateNodes() {
        if (ComposeUiFlags.isTrackFocusEnabled) {
            invalidateNodesOptimized();
        } else {
            invalidateNodesLegacy();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v0 */
    /* JADX WARN: Type inference failed for: r14v1 */
    /* JADX WARN: Type inference failed for: r14v10 */
    /* JADX WARN: Type inference failed for: r14v11 */
    /* JADX WARN: Type inference failed for: r14v12 */
    /* JADX WARN: Type inference failed for: r14v13 */
    /* JADX WARN: Type inference failed for: r14v2 */
    /* JADX WARN: Type inference failed for: r14v3 */
    /* JADX WARN: Type inference failed for: r14v4 */
    /* JADX WARN: Type inference failed for: r14v5 */
    /* JADX WARN: Type inference failed for: r14v6 */
    /* JADX WARN: Type inference failed for: r14v7 */
    /* JADX WARN: Type inference failed for: r14v8 */
    /* JADX WARN: Type inference failed for: r14v9 */
    /* JADX WARN: Type inference failed for: r15v0 */
    /* JADX WARN: Type inference failed for: r15v1 */
    /* JADX WARN: Type inference failed for: r15v10 */
    /* JADX WARN: Type inference failed for: r15v11 */
    /* JADX WARN: Type inference failed for: r15v12 */
    /* JADX WARN: Type inference failed for: r15v13 */
    /* JADX WARN: Type inference failed for: r15v2 */
    /* JADX WARN: Type inference failed for: r15v3 */
    /* JADX WARN: Type inference failed for: r15v4 */
    /* JADX WARN: Type inference failed for: r15v5 */
    /* JADX WARN: Type inference failed for: r15v6 */
    /* JADX WARN: Type inference failed for: r15v7 */
    /* JADX WARN: Type inference failed for: r15v8 */
    /* JADX WARN: Type inference failed for: r15v9 */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v22 */
    /* JADX WARN: Type inference failed for: r2v3, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r2v9 */
    private final void invalidateNodesLegacy() {
        int i;
        FocusState focusState;
        ?? r2 = 0;
        if (!((FocusState) this.rootFocusStateFetcher.mo2224invoke()).getHasFocus()) {
            List list = this.focusEventNodesLegacy;
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                ((FocusEventModifierNode) list.get(i2)).onFocusEvent(FocusStateImpl.Inactive);
            }
            List list2 = this.focusTargetNodesLegacy;
            int size2 = list2.size();
            for (int i3 = 0; i3 < size2; i3++) {
                FocusTargetNode focusTargetNode = (FocusTargetNode) list2.get(i3);
                if (focusTargetNode.isAttached() && !focusTargetNode.isInitialized$ui_release()) {
                    focusTargetNode.initializeFocusState$ui_release(FocusStateImpl.Inactive);
                }
            }
            this.focusTargetNodesLegacy.clear();
            this.focusEventNodesLegacy.clear();
            this.focusPropertiesNodesLegacy.clear();
            this.focusTargetsWithInvalidatedFocusEventsLegacy.clear();
            this.invalidateOwnerFocusState.mo2224invoke();
            return;
        }
        List list3 = this.focusPropertiesNodesLegacy;
        int size3 = list3.size();
        int i4 = 0;
        while (true) {
            i = 1024;
            if (i4 >= size3) {
                break;
            }
            FocusPropertiesModifierNode focusPropertiesModifierNode = (FocusPropertiesModifierNode) list3.get(i4);
            if (focusPropertiesModifierNode.getNode().isAttached()) {
                int iM658constructorimpl = NodeKind.m658constructorimpl(1024);
                for (Modifier.Node node = focusPropertiesModifierNode.getNode(); node != null; node = DelegatableNodeKt.pop(null)) {
                    if (node instanceof FocusTargetNode) {
                        this.focusTargetNodesLegacy.add((FocusTargetNode) node);
                    } else {
                        node.getKindSet$ui_release();
                    }
                }
                if (!focusPropertiesModifierNode.getNode().isAttached()) {
                    InlineClassHelperKt.throwIllegalStateException("visitChildren called on an unattached node");
                }
                MutableVector mutableVector = new MutableVector(new Modifier.Node[16], 0);
                Modifier.Node child$ui_release = focusPropertiesModifierNode.getNode().getChild$ui_release();
                if (child$ui_release == null) {
                    DelegatableNodeKt.addLayoutNodeChildren(mutableVector, focusPropertiesModifierNode.getNode(), false);
                } else {
                    mutableVector.add(child$ui_release);
                }
                while (mutableVector.getSize() != 0) {
                    Modifier.Node nodePop = (Modifier.Node) mutableVector.removeAt(mutableVector.getSize() - 1);
                    if ((nodePop.getAggregateChildKindSet$ui_release() & iM658constructorimpl) == 0) {
                        DelegatableNodeKt.addLayoutNodeChildren(mutableVector, nodePop, false);
                    } else {
                        while (true) {
                            if (nodePop == null) {
                                break;
                            }
                            if ((nodePop.getKindSet$ui_release() & iM658constructorimpl) != 0) {
                                while (nodePop != null) {
                                    if (nodePop instanceof FocusTargetNode) {
                                        this.focusTargetNodesLegacy.add((FocusTargetNode) nodePop);
                                    } else {
                                        nodePop.getKindSet$ui_release();
                                    }
                                    nodePop = DelegatableNodeKt.pop(null);
                                }
                            } else {
                                nodePop = nodePop.getChild$ui_release();
                            }
                        }
                    }
                }
            }
            i4++;
        }
        this.focusPropertiesNodesLegacy.clear();
        List list4 = this.focusEventNodesLegacy;
        int size4 = list4.size();
        int i5 = 0;
        while (i5 < size4) {
            FocusEventModifierNode focusEventModifierNode = (FocusEventModifierNode) list4.get(i5);
            if (focusEventModifierNode.getNode().isAttached()) {
                int iM658constructorimpl2 = NodeKind.m658constructorimpl(i);
                Modifier.Node node2 = focusEventModifierNode.getNode();
                ?? r15 = r2;
                FocusTargetNode focusTargetNode2 = null;
                ?? r14 = 1;
                while (node2 != null) {
                    if (node2 instanceof FocusTargetNode) {
                        FocusTargetNode focusTargetNode3 = (FocusTargetNode) node2;
                        r15 = r15;
                        if (focusTargetNode2 != null) {
                            r15 = 1;
                        }
                        r14 = r14;
                        if (this.focusTargetNodesLegacy.contains(focusTargetNode3)) {
                            this.focusTargetsWithInvalidatedFocusEventsLegacy.add(focusTargetNode3);
                            r14 = r2;
                        }
                        focusTargetNode2 = focusTargetNode3;
                    } else {
                        node2.getKindSet$ui_release();
                    }
                    node2 = DelegatableNodeKt.pop(null);
                    r14 = r14;
                    r15 = r15;
                }
                if (!focusEventModifierNode.getNode().isAttached()) {
                    InlineClassHelperKt.throwIllegalStateException("visitChildren called on an unattached node");
                }
                MutableVector mutableVector2 = new MutableVector(new Modifier.Node[16], r2);
                Modifier.Node child$ui_release2 = focusEventModifierNode.getNode().getChild$ui_release();
                if (child$ui_release2 == null) {
                    DelegatableNodeKt.addLayoutNodeChildren(mutableVector2, focusEventModifierNode.getNode(), r2);
                } else {
                    mutableVector2.add(child$ui_release2);
                }
                while (mutableVector2.getSize() != 0) {
                    Modifier.Node nodePop2 = (Modifier.Node) mutableVector2.removeAt(mutableVector2.getSize() - 1);
                    r2 = r2;
                    if ((nodePop2.getAggregateChildKindSet$ui_release() & iM658constructorimpl2) == 0) {
                        DelegatableNodeKt.addLayoutNodeChildren(mutableVector2, nodePop2, r2);
                    } else {
                        while (nodePop2 != null) {
                            if ((nodePop2.getKindSet$ui_release() & iM658constructorimpl2) != 0) {
                                while (nodePop2 != null) {
                                    if (nodePop2 instanceof FocusTargetNode) {
                                        FocusTargetNode focusTargetNode4 = (FocusTargetNode) nodePop2;
                                        r15 = r15;
                                        if (focusTargetNode2 != null) {
                                            r15 = 1;
                                        }
                                        r14 = r14;
                                        if (this.focusTargetNodesLegacy.contains(focusTargetNode4)) {
                                            this.focusTargetsWithInvalidatedFocusEventsLegacy.add(focusTargetNode4);
                                            r14 = 0;
                                        }
                                        focusTargetNode2 = focusTargetNode4;
                                    } else {
                                        nodePop2.getKindSet$ui_release();
                                    }
                                    nodePop2 = DelegatableNodeKt.pop(null);
                                    r2 = 0;
                                }
                            } else {
                                nodePop2 = nodePop2.getChild$ui_release();
                                r2 = 0;
                            }
                        }
                    }
                    r2 = 0;
                }
                if (r14 != 0) {
                    if (r15 != 0) {
                        focusState = FocusEventModifierNodeKt.getFocusState(focusEventModifierNode);
                    } else if (focusTargetNode2 == null || (focusState = focusTargetNode2.getFocusState()) == null) {
                        focusState = FocusStateImpl.Inactive;
                    }
                    focusEventModifierNode.onFocusEvent(focusState);
                }
            } else {
                focusEventModifierNode.onFocusEvent(FocusStateImpl.Inactive);
            }
            i5++;
            r2 = 0;
            i = 1024;
        }
        this.focusEventNodesLegacy.clear();
        List list5 = this.focusTargetNodesLegacy;
        int size5 = list5.size();
        for (int i6 = 0; i6 < size5; i6++) {
            FocusTargetNode focusTargetNode5 = (FocusTargetNode) list5.get(i6);
            if (focusTargetNode5.isAttached()) {
                FocusStateImpl focusState2 = focusTargetNode5.getFocusState();
                focusTargetNode5.invalidateFocus$ui_release();
                if (focusState2 != focusTargetNode5.getFocusState() || this.focusTargetsWithInvalidatedFocusEventsLegacy.contains(focusTargetNode5)) {
                    focusTargetNode5.dispatchFocusCallbacks$ui_release();
                }
            }
        }
        this.focusTargetNodesLegacy.clear();
        this.focusTargetsWithInvalidatedFocusEventsLegacy.clear();
        this.invalidateOwnerFocusState.mo2224invoke();
        if (!this.focusPropertiesNodesLegacy.isEmpty()) {
            InlineClassHelperKt.throwIllegalStateException("Unprocessed FocusProperties nodes");
        }
        if (!this.focusEventNodesLegacy.isEmpty()) {
            InlineClassHelperKt.throwIllegalStateException("Unprocessed FocusEvent nodes");
        }
        if (this.focusTargetNodesLegacy.isEmpty()) {
            return;
        }
        InlineClassHelperKt.throwIllegalStateException("Unprocessed FocusTarget nodes");
    }

    /* JADX WARN: Removed duplicated region for block: B:69:0x0139  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void invalidateNodesOptimized() {
        /*
            Method dump skipped, instruction units count: 336
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.focus.FocusInvalidationManager.invalidateNodesOptimized():void");
    }

    private final void scheduleInvalidation(MutableScatterSet mutableScatterSet, Object obj) {
        if (mutableScatterSet.add(obj)) {
            setUpOnRequestApplyChangesListener();
        }
    }

    private final void scheduleInvalidationLegacy(List list, Object obj) {
        if (list.add(obj) && this.focusTargetNodesLegacy.size() + this.focusEventNodesLegacy.size() + this.focusPropertiesNodesLegacy.size() == 1) {
            this.onRequestApplyChangesListener.invoke(new AnonymousClass1(this));
        }
    }

    private final void setUpOnRequestApplyChangesListener() {
        if (this.isInvalidationScheduled) {
            return;
        }
        this.onRequestApplyChangesListener.invoke(new C00591(this));
        this.isInvalidationScheduled = true;
    }

    public final boolean hasPendingInvalidation() {
        return ComposeUiFlags.isTrackFocusEnabled ? this.isInvalidationScheduled : (this.focusTargetNodesLegacy.isEmpty() && this.focusPropertiesNodesLegacy.isEmpty() && this.focusEventNodesLegacy.isEmpty()) ? false : true;
    }

    public final void scheduleInvalidation(FocusEventModifierNode focusEventModifierNode) {
        if (ComposeUiFlags.isTrackFocusEnabled) {
            scheduleInvalidation(this.focusEventNodes, focusEventModifierNode);
        } else {
            scheduleInvalidationLegacy(this.focusEventNodesLegacy, focusEventModifierNode);
        }
    }

    public final void scheduleInvalidation(FocusPropertiesModifierNode focusPropertiesModifierNode) {
        scheduleInvalidationLegacy(this.focusPropertiesNodesLegacy, focusPropertiesModifierNode);
    }

    public final void scheduleInvalidation(FocusTargetNode focusTargetNode) {
        if (ComposeUiFlags.isTrackFocusEnabled) {
            scheduleInvalidation(this.focusTargetNodes, focusTargetNode);
        } else {
            scheduleInvalidationLegacy(this.focusTargetNodesLegacy, focusTargetNode);
        }
    }

    public final void scheduleInvalidationForOwner() {
        setUpOnRequestApplyChangesListener();
    }
}
