package androidx.compose.ui.focus;

import androidx.collection.MutableScatterSet;
import androidx.collection.ScatterSetKt;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;
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
        public /* bridge */ /* synthetic */ Object invoke() {
            m700invoke();
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
        public final void m700invoke() {
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
        public /* bridge */ /* synthetic */ Object invoke() {
            m701invoke();
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
        public final void m701invoke() {
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
    /* JADX WARN: Type inference failed for: r15v0 */
    /* JADX WARN: Type inference failed for: r15v1 */
    /* JADX WARN: Type inference failed for: r15v10 */
    /* JADX WARN: Type inference failed for: r15v11 */
    /* JADX WARN: Type inference failed for: r15v12 */
    /* JADX WARN: Type inference failed for: r15v13 */
    /* JADX WARN: Type inference failed for: r15v17 */
    /* JADX WARN: Type inference failed for: r15v18 */
    /* JADX WARN: Type inference failed for: r15v19 */
    /* JADX WARN: Type inference failed for: r15v2 */
    /* JADX WARN: Type inference failed for: r15v20 */
    /* JADX WARN: Type inference failed for: r15v21 */
    /* JADX WARN: Type inference failed for: r15v22 */
    /* JADX WARN: Type inference failed for: r15v23 */
    /* JADX WARN: Type inference failed for: r15v24 */
    /* JADX WARN: Type inference failed for: r15v3 */
    /* JADX WARN: Type inference failed for: r15v4 */
    /* JADX WARN: Type inference failed for: r15v5 */
    /* JADX WARN: Type inference failed for: r15v6 */
    /* JADX WARN: Type inference failed for: r15v7 */
    /* JADX WARN: Type inference failed for: r15v8 */
    /* JADX WARN: Type inference failed for: r15v9 */
    /* JADX WARN: Type inference failed for: r16v0 */
    /* JADX WARN: Type inference failed for: r16v1 */
    /* JADX WARN: Type inference failed for: r16v10 */
    /* JADX WARN: Type inference failed for: r16v11 */
    /* JADX WARN: Type inference failed for: r16v12 */
    /* JADX WARN: Type inference failed for: r16v13 */
    /* JADX WARN: Type inference failed for: r16v14 */
    /* JADX WARN: Type inference failed for: r16v15 */
    /* JADX WARN: Type inference failed for: r16v16 */
    /* JADX WARN: Type inference failed for: r16v17 */
    /* JADX WARN: Type inference failed for: r16v18 */
    /* JADX WARN: Type inference failed for: r16v19 */
    /* JADX WARN: Type inference failed for: r16v2 */
    /* JADX WARN: Type inference failed for: r16v20 */
    /* JADX WARN: Type inference failed for: r16v21 */
    /* JADX WARN: Type inference failed for: r16v3 */
    /* JADX WARN: Type inference failed for: r16v4 */
    /* JADX WARN: Type inference failed for: r16v5 */
    /* JADX WARN: Type inference failed for: r16v6 */
    /* JADX WARN: Type inference failed for: r16v7 */
    /* JADX WARN: Type inference failed for: r16v8 */
    /* JADX WARN: Type inference failed for: r16v9 */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.util.Collection, java.util.List] */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v3, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v33 */
    /* JADX WARN: Type inference failed for: r2v34 */
    /* JADX WARN: Type inference failed for: r2v35 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v16 */
    /* JADX WARN: Type inference failed for: r7v17 */
    /* JADX WARN: Type inference failed for: r7v18 */
    /* JADX WARN: Type inference failed for: r7v19 */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v20 */
    /* JADX WARN: Type inference failed for: r7v22 */
    /* JADX WARN: Type inference failed for: r7v28 */
    /* JADX WARN: Type inference failed for: r7v29 */
    /* JADX WARN: Type inference failed for: r7v3, types: [int] */
    /* JADX WARN: Type inference failed for: r7v31 */
    /* JADX WARN: Type inference failed for: r7v34 */
    /* JADX WARN: Type inference failed for: r7v35 */
    /* JADX WARN: Type inference failed for: r7v36 */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r8v20 */
    /* JADX WARN: Type inference failed for: r8v21, types: [int] */
    /* JADX WARN: Type inference failed for: r8v22 */
    /* JADX WARN: Type inference failed for: r8v23, types: [int] */
    /* JADX WARN: Type inference failed for: r8v24 */
    private final void invalidateNodesLegacy() {
        int i;
        int i2;
        boolean z;
        ?? r7;
        boolean z2;
        FocusState focusState;
        boolean z3;
        ?? r72;
        ?? r73;
        ?? r16;
        ?? r15;
        ?? r2 = 0;
        if (!((FocusState) this.rootFocusStateFetcher.invoke()).getHasFocus()) {
            List list = this.focusEventNodesLegacy;
            int size = list.size();
            for (int i3 = 0; i3 < size; i3++) {
                ((FocusEventModifierNode) list.get(i3)).onFocusEvent(FocusStateImpl.Inactive);
            }
            List list2 = this.focusTargetNodesLegacy;
            int size2 = list2.size();
            for (int i4 = 0; i4 < size2; i4++) {
                FocusTargetNode focusTargetNode = (FocusTargetNode) list2.get(i4);
                if (focusTargetNode.isAttached() && !focusTargetNode.isInitialized$ui_release()) {
                    focusTargetNode.initializeFocusState$ui_release(FocusStateImpl.Inactive);
                }
            }
            this.focusTargetNodesLegacy.clear();
            this.focusEventNodesLegacy.clear();
            this.focusPropertiesNodesLegacy.clear();
            this.focusTargetsWithInvalidatedFocusEventsLegacy.clear();
            this.invalidateOwnerFocusState.invoke();
            return;
        }
        List list3 = this.focusPropertiesNodesLegacy;
        int size3 = list3.size();
        int i5 = 0;
        while (true) {
            i = 1024;
            i2 = 16;
            z = true;
            if (i5 >= size3) {
                break;
            }
            FocusPropertiesModifierNode focusPropertiesModifierNode = (FocusPropertiesModifierNode) list3.get(i5);
            if (focusPropertiesModifierNode.getNode().isAttached()) {
                int iM1404constructorimpl = NodeKind.m1404constructorimpl(1024);
                Modifier.Node node = focusPropertiesModifierNode.getNode();
                MutableVector mutableVector = null;
                while (node != null) {
                    if (node instanceof FocusTargetNode) {
                        this.focusTargetNodesLegacy.add((FocusTargetNode) node);
                    } else if ((node.getKindSet$ui_release() & iM1404constructorimpl) != 0 && (node instanceof DelegatingNode)) {
                        int i6 = 0;
                        for (Modifier.Node delegate$ui_release = ((DelegatingNode) node).getDelegate$ui_release(); delegate$ui_release != null; delegate$ui_release = delegate$ui_release.getChild$ui_release()) {
                            if ((delegate$ui_release.getKindSet$ui_release() & iM1404constructorimpl) != 0) {
                                i6++;
                                if (i6 == 1) {
                                    node = delegate$ui_release;
                                } else {
                                    if (mutableVector == null) {
                                        mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                    }
                                    if (node != null) {
                                        mutableVector.add(node);
                                        node = null;
                                    }
                                    mutableVector.add(delegate$ui_release);
                                }
                            }
                        }
                        if (i6 == 1) {
                        }
                    }
                    node = DelegatableNodeKt.pop(mutableVector);
                }
                if (!focusPropertiesModifierNode.getNode().isAttached()) {
                    InlineClassHelperKt.throwIllegalStateException("visitChildren called on an unattached node");
                }
                MutableVector mutableVector2 = new MutableVector(new Modifier.Node[16], 0);
                Modifier.Node child$ui_release = focusPropertiesModifierNode.getNode().getChild$ui_release();
                if (child$ui_release == null) {
                    DelegatableNodeKt.addLayoutNodeChildren(mutableVector2, focusPropertiesModifierNode.getNode(), false);
                } else {
                    mutableVector2.add(child$ui_release);
                }
                while (mutableVector2.getSize() != 0) {
                    Modifier.Node nodePop = (Modifier.Node) mutableVector2.removeAt(mutableVector2.getSize() - 1);
                    if ((nodePop.getAggregateChildKindSet$ui_release() & iM1404constructorimpl) == 0) {
                        DelegatableNodeKt.addLayoutNodeChildren(mutableVector2, nodePop, false);
                    } else {
                        while (true) {
                            if (nodePop == null) {
                                break;
                            }
                            if ((nodePop.getKindSet$ui_release() & iM1404constructorimpl) != 0) {
                                MutableVector mutableVector3 = null;
                                while (nodePop != null) {
                                    if (nodePop instanceof FocusTargetNode) {
                                        this.focusTargetNodesLegacy.add((FocusTargetNode) nodePop);
                                    } else if ((nodePop.getKindSet$ui_release() & iM1404constructorimpl) != 0 && (nodePop instanceof DelegatingNode)) {
                                        int i7 = 0;
                                        for (Modifier.Node delegate$ui_release2 = ((DelegatingNode) nodePop).getDelegate$ui_release(); delegate$ui_release2 != null; delegate$ui_release2 = delegate$ui_release2.getChild$ui_release()) {
                                            if ((delegate$ui_release2.getKindSet$ui_release() & iM1404constructorimpl) != 0) {
                                                i7++;
                                                if (i7 == 1) {
                                                    nodePop = delegate$ui_release2;
                                                } else {
                                                    if (mutableVector3 == null) {
                                                        mutableVector3 = new MutableVector(new Modifier.Node[16], 0);
                                                    }
                                                    if (nodePop != null) {
                                                        mutableVector3.add(nodePop);
                                                        nodePop = null;
                                                    }
                                                    mutableVector3.add(delegate$ui_release2);
                                                }
                                            }
                                        }
                                        if (i7 == 1) {
                                        }
                                    }
                                    nodePop = DelegatableNodeKt.pop(mutableVector3);
                                }
                            } else {
                                nodePop = nodePop.getChild$ui_release();
                            }
                        }
                    }
                }
            }
            i5++;
        }
        this.focusPropertiesNodesLegacy.clear();
        List list4 = this.focusEventNodesLegacy;
        int size4 = list4.size();
        int i8 = 0;
        while (i8 < size4) {
            FocusEventModifierNode focusEventModifierNode = (FocusEventModifierNode) list4.get(i8);
            if (focusEventModifierNode.getNode().isAttached()) {
                int iM1404constructorimpl2 = NodeKind.m1404constructorimpl(i);
                Modifier.Node node2 = focusEventModifierNode.getNode();
                ?? r162 = r2;
                ?? r152 = z;
                FocusTargetNode focusTargetNode2 = null;
                MutableVector mutableVector4 = null;
                while (node2 != null) {
                    if (node2 instanceof FocusTargetNode) {
                        FocusTargetNode focusTargetNode3 = (FocusTargetNode) node2;
                        r162 = r162;
                        if (focusTargetNode2 != null) {
                            r162 = z;
                        }
                        r152 = r152;
                        if (this.focusTargetNodesLegacy.contains(focusTargetNode3)) {
                            this.focusTargetsWithInvalidatedFocusEventsLegacy.add(focusTargetNode3);
                            r152 = r2;
                        }
                        focusTargetNode2 = focusTargetNode3;
                    } else if ((node2.getKindSet$ui_release() & iM1404constructorimpl2) != 0 && (node2 instanceof DelegatingNode)) {
                        Modifier.Node delegate$ui_release3 = ((DelegatingNode) node2).getDelegate$ui_release();
                        ?? r8 = r2;
                        while (delegate$ui_release3 != null) {
                            if ((delegate$ui_release3.getKindSet$ui_release() & iM1404constructorimpl2) != 0) {
                                r8++;
                                if (r8 == z) {
                                    node2 = delegate$ui_release3;
                                } else {
                                    if (mutableVector4 == null) {
                                        mutableVector4 = new MutableVector(new Modifier.Node[i2], r2);
                                    }
                                    if (node2 != null) {
                                        mutableVector4.add(node2);
                                        node2 = null;
                                    }
                                    mutableVector4.add(delegate$ui_release3);
                                }
                            }
                            delegate$ui_release3 = delegate$ui_release3.getChild$ui_release();
                            z = true;
                            r8 = r8;
                        }
                        boolean z4 = z;
                        if (r8 == z4) {
                            z = z4;
                        }
                    }
                    node2 = DelegatableNodeKt.pop(mutableVector4);
                    z = true;
                }
                if (!focusEventModifierNode.getNode().isAttached()) {
                    InlineClassHelperKt.throwIllegalStateException("visitChildren called on an unattached node");
                }
                MutableVector mutableVector5 = new MutableVector(new Modifier.Node[i2], r2);
                Modifier.Node child$ui_release2 = focusEventModifierNode.getNode().getChild$ui_release();
                if (child$ui_release2 == null) {
                    DelegatableNodeKt.addLayoutNodeChildren(mutableVector5, focusEventModifierNode.getNode(), r2);
                } else {
                    mutableVector5.add(child$ui_release2);
                }
                while (mutableVector5.getSize() != 0) {
                    Modifier.Node nodePop2 = (Modifier.Node) mutableVector5.removeAt(mutableVector5.getSize() - 1);
                    r2 = r2;
                    if ((nodePop2.getAggregateChildKindSet$ui_release() & iM1404constructorimpl2) == 0) {
                        DelegatableNodeKt.addLayoutNodeChildren(mutableVector5, nodePop2, r2);
                    } else {
                        while (nodePop2 != null) {
                            if ((nodePop2.getKindSet$ui_release() & iM1404constructorimpl2) != 0) {
                                MutableVector mutableVector6 = null;
                                r2 = r2;
                                r152 = r152;
                                r162 = r162;
                                while (nodePop2 != null) {
                                    if (nodePop2 instanceof FocusTargetNode) {
                                        FocusTargetNode focusTargetNode4 = (FocusTargetNode) nodePop2;
                                        ?? r163 = r162;
                                        if (focusTargetNode2 != null) {
                                            r163 = 1;
                                        }
                                        ?? r153 = r152;
                                        if (this.focusTargetNodesLegacy.contains(focusTargetNode4)) {
                                            this.focusTargetsWithInvalidatedFocusEventsLegacy.add(focusTargetNode4);
                                            r153 = r2 == true ? 1 : 0;
                                        }
                                        z3 = r2 == true ? 1 : 0;
                                        focusTargetNode2 = focusTargetNode4;
                                        r15 = r153;
                                        r16 = r163;
                                    } else if ((nodePop2.getKindSet$ui_release() & iM1404constructorimpl2) == 0 || !(nodePop2 instanceof DelegatingNode)) {
                                        z3 = r2 == true ? 1 : 0;
                                        r15 = r152;
                                        r16 = r162;
                                    } else {
                                        Modifier.Node delegate$ui_release4 = ((DelegatingNode) nodePop2).getDelegate$ui_release();
                                        int i9 = r2 == true ? 1 : 0;
                                        ?? r22 = r2;
                                        while (delegate$ui_release4 != null) {
                                            if ((delegate$ui_release4.getKindSet$ui_release() & iM1404constructorimpl2) != 0) {
                                                i9++;
                                                if (i9 == 1) {
                                                    nodePop2 = delegate$ui_release4;
                                                    r73 = 0;
                                                } else {
                                                    if (mutableVector6 == null) {
                                                        Modifier.Node[] nodeArr = new Modifier.Node[i2];
                                                        r73 = 0;
                                                        mutableVector6 = new MutableVector(nodeArr, 0);
                                                    } else {
                                                        r73 = 0;
                                                    }
                                                    if (nodePop2 != null) {
                                                        mutableVector6.add(nodePop2);
                                                        nodePop2 = null;
                                                    }
                                                    mutableVector6.add(delegate$ui_release4);
                                                }
                                            } else {
                                                r73 = r22;
                                            }
                                            delegate$ui_release4 = delegate$ui_release4.getChild$ui_release();
                                            r22 = r73;
                                            i2 = 16;
                                        }
                                        r72 = r22;
                                        r72 = r72;
                                        r152 = r152;
                                        r162 = r162;
                                        if (i9 != 1) {
                                            nodePop2 = DelegatableNodeKt.pop(mutableVector6);
                                        }
                                        r2 = r72;
                                        i2 = 16;
                                        r152 = r152;
                                        r162 = r162;
                                    }
                                    r72 = z3;
                                    r152 = r15;
                                    r162 = r16;
                                    nodePop2 = DelegatableNodeKt.pop(mutableVector6);
                                    r2 = r72;
                                    i2 = 16;
                                    r152 = r152;
                                    r162 = r162;
                                }
                                boolean z5 = r2 == true ? 1 : 0;
                                i2 = 16;
                            } else {
                                boolean z6 = r2 == true ? 1 : 0;
                                nodePop2 = nodePop2.getChild$ui_release();
                                r2 = z6 ? 1 : 0;
                                i2 = 16;
                            }
                        }
                    }
                    r2 = r2 == true ? 1 : 0;
                    i2 = 16;
                }
                r7 = r2 == true ? 1 : 0;
                z2 = true;
                if (r152 != 0) {
                    if (r162 != 0) {
                        focusState = FocusEventModifierNodeKt.getFocusState(focusEventModifierNode);
                    } else if (focusTargetNode2 == null || (focusState = focusTargetNode2.getFocusState()) == null) {
                        focusState = FocusStateImpl.Inactive;
                    }
                    focusEventModifierNode.onFocusEvent(focusState);
                }
            } else {
                focusEventModifierNode.onFocusEvent(FocusStateImpl.Inactive);
                r7 = r2;
                z2 = z;
            }
            i8++;
            z = z2;
            r2 = r7;
            i = 1024;
            i2 = 16;
        }
        this.focusEventNodesLegacy.clear();
        ?? r1 = this.focusTargetNodesLegacy;
        int size5 = r1.size();
        for (?? r74 = r2; r74 < size5; r74++) {
            FocusTargetNode focusTargetNode5 = (FocusTargetNode) r1.get(r74);
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
        this.invalidateOwnerFocusState.invoke();
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
