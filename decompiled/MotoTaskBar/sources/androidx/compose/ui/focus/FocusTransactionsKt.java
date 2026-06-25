package androidx.compose.ui.focus;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.focus.FocusRequester;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.NodeChain;
import androidx.compose.ui.node.NodeKind;
import androidx.compose.ui.node.ObserverModifierNodeKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FocusTransactions.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class FocusTransactionsKt {

    /* JADX INFO: compiled from: FocusTransactions.kt */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FocusStateImpl.values().length];
            try {
                iArr[FocusStateImpl.Active.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FocusStateImpl.Captured.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[FocusStateImpl.ActiveParent.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[FocusStateImpl.Inactive.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private static final boolean clearChildFocus(FocusTargetNode focusTargetNode, boolean z, boolean z2) {
        FocusTargetNode activeChild = FocusTraversalKt.getActiveChild(focusTargetNode);
        if (activeChild != null) {
            return clearFocus(activeChild, z, z2);
        }
        return true;
    }

    static /* synthetic */ boolean clearChildFocus$default(FocusTargetNode focusTargetNode, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        if ((i & 2) != 0) {
            z2 = true;
        }
        return clearChildFocus(focusTargetNode, z, z2);
    }

    public static final boolean clearFocus(FocusTargetNode focusTargetNode, boolean z, boolean z2) {
        int i = WhenMappings.$EnumSwitchMapping$0[focusTargetNode.getFocusState().ordinal()];
        if (i == 1) {
            if (ComposeUiFlags.isTrackFocusEnabled) {
                DelegatableNodeKt.requireOwner(focusTargetNode).getFocusOwner().setActiveFocusTargetNode(null);
                if (z2) {
                    focusTargetNode.dispatchFocusCallbacks$ui_release(FocusStateImpl.Active, FocusStateImpl.Inactive);
                }
            } else {
                focusTargetNode.setFocusState(FocusStateImpl.Inactive);
                if (z2) {
                    focusTargetNode.dispatchFocusCallbacks$ui_release();
                }
            }
            return true;
        }
        if (i == 2) {
            if (z) {
                if (ComposeUiFlags.isTrackFocusEnabled) {
                    DelegatableNodeKt.requireOwner(focusTargetNode).getFocusOwner().setActiveFocusTargetNode(null);
                    if (z2) {
                        focusTargetNode.dispatchFocusCallbacks$ui_release(FocusStateImpl.Captured, FocusStateImpl.Inactive);
                        return z;
                    }
                } else {
                    focusTargetNode.setFocusState(FocusStateImpl.Inactive);
                    if (z2) {
                        focusTargetNode.dispatchFocusCallbacks$ui_release();
                    }
                }
            }
            return z;
        }
        if (i != 3) {
            if (i == 4) {
                return true;
            }
            throw new NoWhenBranchMatchedException();
        }
        if (!clearChildFocus(focusTargetNode, z, z2)) {
            return false;
        }
        if (!ComposeUiFlags.isTrackFocusEnabled) {
            focusTargetNode.setFocusState(FocusStateImpl.Inactive);
            if (z2) {
                focusTargetNode.dispatchFocusCallbacks$ui_release();
            }
        } else if (z2) {
            focusTargetNode.dispatchFocusCallbacks$ui_release(FocusStateImpl.ActiveParent, FocusStateImpl.Inactive);
        }
        return true;
    }

    public static /* synthetic */ boolean clearFocus$default(FocusTargetNode focusTargetNode, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return clearFocus(focusTargetNode, z, z2);
    }

    private static final boolean grantFocus(final FocusTargetNode focusTargetNode) {
        ObserverModifierNodeKt.observeReads(focusTargetNode, new Function0() { // from class: androidx.compose.ui.focus.FocusTransactionsKt.grantFocus.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public /* bridge */ /* synthetic */ Object mo2224invoke() {
                m155invoke();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
            public final void m155invoke() {
                focusTargetNode.fetchFocusProperties$ui_release();
            }
        });
        int i = WhenMappings.$EnumSwitchMapping$0[focusTargetNode.getFocusState().ordinal()];
        if (i != 3 && i != 4) {
            return true;
        }
        if (ComposeUiFlags.isTrackFocusEnabled) {
            DelegatableNodeKt.requireOwner(focusTargetNode).getFocusOwner().setActiveFocusTargetNode(focusTargetNode);
            return true;
        }
        focusTargetNode.setFocusState(FocusStateImpl.Active);
        return true;
    }

    /* JADX INFO: renamed from: performCustomClearFocus-Mxy_nc0, reason: not valid java name */
    public static final CustomDestinationResult m151performCustomClearFocusMxy_nc0(FocusTargetNode focusTargetNode, int i) {
        int i2 = WhenMappings.$EnumSwitchMapping$0[focusTargetNode.getFocusState().ordinal()];
        if (i2 != 1) {
            if (i2 == 2) {
                return CustomDestinationResult.Cancelled;
            }
            if (i2 == 3) {
                CustomDestinationResult customDestinationResultM151performCustomClearFocusMxy_nc0 = m151performCustomClearFocusMxy_nc0(requireActiveChild(focusTargetNode), i);
                if (customDestinationResultM151performCustomClearFocusMxy_nc0 == CustomDestinationResult.None) {
                    customDestinationResultM151performCustomClearFocusMxy_nc0 = null;
                }
                return customDestinationResultM151performCustomClearFocusMxy_nc0 == null ? m153performCustomExitMxy_nc0(focusTargetNode, i) : customDestinationResultM151performCustomClearFocusMxy_nc0;
            }
            if (i2 != 4) {
                throw new NoWhenBranchMatchedException();
            }
        }
        return CustomDestinationResult.None;
    }

    /* JADX INFO: renamed from: performCustomEnter-Mxy_nc0, reason: not valid java name */
    private static final CustomDestinationResult m152performCustomEnterMxy_nc0(FocusTargetNode focusTargetNode, int i) {
        if (!focusTargetNode.isProcessingCustomEnter) {
            focusTargetNode.isProcessingCustomEnter = true;
            try {
                FocusProperties focusPropertiesFetchFocusProperties$ui_release = focusTargetNode.fetchFocusProperties$ui_release();
                CancelIndicatingFocusBoundaryScope cancelIndicatingFocusBoundaryScope = new CancelIndicatingFocusBoundaryScope(i, null);
                FocusTransactionManager focusTransactionManager = FocusTargetNodeKt.getFocusTransactionManager(focusTargetNode);
                int generation = focusTransactionManager != null ? focusTransactionManager.getGeneration() : 0;
                FocusOwner focusOwner = DelegatableNodeKt.requireOwner(focusTargetNode).getFocusOwner();
                FocusTargetNode activeFocusTargetNode = focusOwner.getActiveFocusTargetNode();
                focusPropertiesFetchFocusProperties$ui_release.getOnEnter().invoke(cancelIndicatingFocusBoundaryScope);
                int generation2 = focusTransactionManager != null ? focusTransactionManager.getGeneration() : 0;
                FocusTargetNode activeFocusTargetNode2 = focusOwner.getActiveFocusTargetNode();
                if (cancelIndicatingFocusBoundaryScope.isCanceled()) {
                    FocusRequester.Companion companion = FocusRequester.Companion;
                    FocusRequester cancel = companion.getCancel();
                    if (cancel == companion.getCancel()) {
                        CustomDestinationResult customDestinationResult = CustomDestinationResult.Cancelled;
                        focusTargetNode.isProcessingCustomEnter = false;
                        return customDestinationResult;
                    }
                    if (cancel == companion.getRedirect$ui_release()) {
                        CustomDestinationResult customDestinationResult2 = CustomDestinationResult.Redirected;
                        focusTargetNode.isProcessingCustomEnter = false;
                        return customDestinationResult2;
                    }
                    CustomDestinationResult customDestinationResult3 = FocusRequester.m145requestFocus3ESFkO8$default(cancel, 0, 1, null) ? CustomDestinationResult.Redirected : CustomDestinationResult.RedirectCancelled;
                    focusTargetNode.isProcessingCustomEnter = false;
                    return customDestinationResult3;
                }
                if (generation != generation2 || (ComposeUiFlags.isTrackFocusEnabled && activeFocusTargetNode != activeFocusTargetNode2 && activeFocusTargetNode2 != null)) {
                    FocusRequester.Companion companion2 = FocusRequester.Companion;
                    FocusRequester redirect$ui_release = companion2.getRedirect$ui_release();
                    if (redirect$ui_release == companion2.getCancel()) {
                        CustomDestinationResult customDestinationResult4 = CustomDestinationResult.Cancelled;
                        focusTargetNode.isProcessingCustomEnter = false;
                        return customDestinationResult4;
                    }
                    if (redirect$ui_release == companion2.getRedirect$ui_release()) {
                        CustomDestinationResult customDestinationResult5 = CustomDestinationResult.Redirected;
                        focusTargetNode.isProcessingCustomEnter = false;
                        return customDestinationResult5;
                    }
                    CustomDestinationResult customDestinationResult6 = FocusRequester.m145requestFocus3ESFkO8$default(redirect$ui_release, 0, 1, null) ? CustomDestinationResult.Redirected : CustomDestinationResult.RedirectCancelled;
                    focusTargetNode.isProcessingCustomEnter = false;
                    return customDestinationResult6;
                }
                focusTargetNode.isProcessingCustomEnter = false;
            } catch (Throwable th) {
                focusTargetNode.isProcessingCustomEnter = false;
                throw th;
            }
        }
        return CustomDestinationResult.None;
    }

    /* JADX INFO: renamed from: performCustomExit-Mxy_nc0, reason: not valid java name */
    private static final CustomDestinationResult m153performCustomExitMxy_nc0(FocusTargetNode focusTargetNode, int i) {
        if (!focusTargetNode.isProcessingCustomExit) {
            focusTargetNode.isProcessingCustomExit = true;
            try {
                FocusProperties focusPropertiesFetchFocusProperties$ui_release = focusTargetNode.fetchFocusProperties$ui_release();
                CancelIndicatingFocusBoundaryScope cancelIndicatingFocusBoundaryScope = new CancelIndicatingFocusBoundaryScope(i, null);
                FocusTransactionManager focusTransactionManager = FocusTargetNodeKt.getFocusTransactionManager(focusTargetNode);
                int generation = focusTransactionManager != null ? focusTransactionManager.getGeneration() : 0;
                FocusOwner focusOwner = DelegatableNodeKt.requireOwner(focusTargetNode).getFocusOwner();
                FocusTargetNode activeFocusTargetNode = focusOwner.getActiveFocusTargetNode();
                focusPropertiesFetchFocusProperties$ui_release.getOnExit().invoke(cancelIndicatingFocusBoundaryScope);
                int generation2 = focusTransactionManager != null ? focusTransactionManager.getGeneration() : 0;
                FocusTargetNode activeFocusTargetNode2 = focusOwner.getActiveFocusTargetNode();
                if (cancelIndicatingFocusBoundaryScope.isCanceled()) {
                    FocusRequester.Companion companion = FocusRequester.Companion;
                    FocusRequester cancel = companion.getCancel();
                    if (cancel == companion.getCancel()) {
                        CustomDestinationResult customDestinationResult = CustomDestinationResult.Cancelled;
                        focusTargetNode.isProcessingCustomExit = false;
                        return customDestinationResult;
                    }
                    if (cancel == companion.getRedirect$ui_release()) {
                        CustomDestinationResult customDestinationResult2 = CustomDestinationResult.Redirected;
                        focusTargetNode.isProcessingCustomExit = false;
                        return customDestinationResult2;
                    }
                    CustomDestinationResult customDestinationResult3 = FocusRequester.m145requestFocus3ESFkO8$default(cancel, 0, 1, null) ? CustomDestinationResult.Redirected : CustomDestinationResult.RedirectCancelled;
                    focusTargetNode.isProcessingCustomExit = false;
                    return customDestinationResult3;
                }
                if (generation != generation2 || (ComposeUiFlags.isTrackFocusEnabled && activeFocusTargetNode != activeFocusTargetNode2 && activeFocusTargetNode2 != null)) {
                    FocusRequester.Companion companion2 = FocusRequester.Companion;
                    FocusRequester redirect$ui_release = companion2.getRedirect$ui_release();
                    if (redirect$ui_release == companion2.getCancel()) {
                        CustomDestinationResult customDestinationResult4 = CustomDestinationResult.Cancelled;
                        focusTargetNode.isProcessingCustomExit = false;
                        return customDestinationResult4;
                    }
                    if (redirect$ui_release == companion2.getRedirect$ui_release()) {
                        CustomDestinationResult customDestinationResult5 = CustomDestinationResult.Redirected;
                        focusTargetNode.isProcessingCustomExit = false;
                        return customDestinationResult5;
                    }
                    CustomDestinationResult customDestinationResult6 = FocusRequester.m145requestFocus3ESFkO8$default(redirect$ui_release, 0, 1, null) ? CustomDestinationResult.Redirected : CustomDestinationResult.RedirectCancelled;
                    focusTargetNode.isProcessingCustomExit = false;
                    return customDestinationResult6;
                }
                focusTargetNode.isProcessingCustomExit = false;
            } catch (Throwable th) {
                focusTargetNode.isProcessingCustomExit = false;
                throw th;
            }
        }
        return CustomDestinationResult.None;
    }

    /* JADX INFO: renamed from: performCustomRequestFocus-Mxy_nc0, reason: not valid java name */
    public static final CustomDestinationResult m154performCustomRequestFocusMxy_nc0(FocusTargetNode focusTargetNode, int i) {
        Modifier.Node nodePop;
        NodeChain nodes$ui_release;
        int i2 = WhenMappings.$EnumSwitchMapping$0[focusTargetNode.getFocusState().ordinal()];
        if (i2 == 1 || i2 == 2) {
            return CustomDestinationResult.None;
        }
        if (i2 == 3) {
            return m151performCustomClearFocusMxy_nc0(requireActiveChild(focusTargetNode), i);
        }
        if (i2 != 4) {
            throw new NoWhenBranchMatchedException();
        }
        int iM658constructorimpl = NodeKind.m658constructorimpl(1024);
        if (!focusTargetNode.getNode().isAttached()) {
            InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
        }
        Modifier.Node parent$ui_release = focusTargetNode.getNode().getParent$ui_release();
        LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(focusTargetNode);
        loop0: while (true) {
            if (layoutNodeRequireLayoutNode == null) {
                nodePop = null;
                break;
            }
            if ((layoutNodeRequireLayoutNode.getNodes$ui_release().getHead$ui_release().getAggregateChildKindSet$ui_release() & iM658constructorimpl) != 0) {
                while (parent$ui_release != null) {
                    if ((parent$ui_release.getKindSet$ui_release() & iM658constructorimpl) != 0) {
                        nodePop = parent$ui_release;
                        while (nodePop != null) {
                            if (nodePop instanceof FocusTargetNode) {
                                break loop0;
                            }
                            nodePop.getKindSet$ui_release();
                            nodePop = DelegatableNodeKt.pop(null);
                        }
                    }
                    parent$ui_release = parent$ui_release.getParent$ui_release();
                }
            }
            layoutNodeRequireLayoutNode = layoutNodeRequireLayoutNode.getParent$ui_release();
            parent$ui_release = (layoutNodeRequireLayoutNode == null || (nodes$ui_release = layoutNodeRequireLayoutNode.getNodes$ui_release()) == null) ? null : nodes$ui_release.getTail$ui_release();
        }
        FocusTargetNode focusTargetNode2 = (FocusTargetNode) nodePop;
        if (focusTargetNode2 == null) {
            return CustomDestinationResult.None;
        }
        int i3 = WhenMappings.$EnumSwitchMapping$0[focusTargetNode2.getFocusState().ordinal()];
        if (i3 == 1) {
            return m152performCustomEnterMxy_nc0(focusTargetNode2, i);
        }
        if (i3 == 2) {
            return CustomDestinationResult.Cancelled;
        }
        if (i3 == 3) {
            return m154performCustomRequestFocusMxy_nc0(focusTargetNode2, i);
        }
        if (i3 != 4) {
            throw new NoWhenBranchMatchedException();
        }
        CustomDestinationResult customDestinationResultM154performCustomRequestFocusMxy_nc0 = m154performCustomRequestFocusMxy_nc0(focusTargetNode2, i);
        CustomDestinationResult customDestinationResult = customDestinationResultM154performCustomRequestFocusMxy_nc0 != CustomDestinationResult.None ? customDestinationResultM154performCustomRequestFocusMxy_nc0 : null;
        return customDestinationResult == null ? m152performCustomEnterMxy_nc0(focusTargetNode2, i) : customDestinationResult;
    }

    public static final boolean performRequestFocus(FocusTargetNode focusTargetNode) {
        return ComposeUiFlags.isTrackFocusEnabled ? performRequestFocusOptimized(focusTargetNode) : performRequestFocusLegacy(focusTargetNode);
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x00a3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static final boolean performRequestFocusLegacy(androidx.compose.ui.focus.FocusTargetNode r8) {
        /*
            Method dump skipped, instruction units count: 224
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.focus.FocusTransactionsKt.performRequestFocusLegacy(androidx.compose.ui.focus.FocusTargetNode):boolean");
    }

    private static final boolean performRequestFocusOptimized(FocusTargetNode focusTargetNode) {
        MutableVector mutableVector;
        NodeChain nodes$ui_release;
        NodeChain nodes$ui_release2;
        FocusOwner focusOwner = DelegatableNodeKt.requireOwner(focusTargetNode).getFocusOwner();
        FocusTargetNode activeFocusTargetNode = focusOwner.getActiveFocusTargetNode();
        FocusStateImpl focusState = focusTargetNode.getFocusState();
        if (activeFocusTargetNode == focusTargetNode) {
            focusTargetNode.dispatchFocusCallbacks$ui_release(focusState, focusState);
            return true;
        }
        if (activeFocusTargetNode != null && !clearFocus$default(activeFocusTargetNode, false, true, 1, null)) {
            return false;
        }
        if (activeFocusTargetNode == null && !requestFocusForOwner(focusTargetNode)) {
            return false;
        }
        grantFocus(focusTargetNode);
        if (activeFocusTargetNode != null) {
            mutableVector = new MutableVector(new FocusTargetNode[16], 0);
            int iM658constructorimpl = NodeKind.m658constructorimpl(1024);
            if (!activeFocusTargetNode.getNode().isAttached()) {
                InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
            }
            Modifier.Node parent$ui_release = activeFocusTargetNode.getNode().getParent$ui_release();
            LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(activeFocusTargetNode);
            while (layoutNodeRequireLayoutNode != null) {
                if ((layoutNodeRequireLayoutNode.getNodes$ui_release().getHead$ui_release().getAggregateChildKindSet$ui_release() & iM658constructorimpl) != 0) {
                    while (parent$ui_release != null) {
                        if ((parent$ui_release.getKindSet$ui_release() & iM658constructorimpl) != 0) {
                            for (Modifier.Node nodePop = parent$ui_release; nodePop != null; nodePop = DelegatableNodeKt.pop(null)) {
                                if (nodePop instanceof FocusTargetNode) {
                                    mutableVector.add((FocusTargetNode) nodePop);
                                } else {
                                    nodePop.getKindSet$ui_release();
                                }
                            }
                        }
                        parent$ui_release = parent$ui_release.getParent$ui_release();
                    }
                }
                layoutNodeRequireLayoutNode = layoutNodeRequireLayoutNode.getParent$ui_release();
                parent$ui_release = (layoutNodeRequireLayoutNode == null || (nodes$ui_release2 = layoutNodeRequireLayoutNode.getNodes$ui_release()) == null) ? null : nodes$ui_release2.getTail$ui_release();
            }
        } else {
            mutableVector = null;
        }
        MutableVector mutableVector2 = new MutableVector(new FocusTargetNode[16], 0);
        int iM658constructorimpl2 = NodeKind.m658constructorimpl(1024);
        if (!focusTargetNode.getNode().isAttached()) {
            InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
        }
        Modifier.Node parent$ui_release2 = focusTargetNode.getNode().getParent$ui_release();
        LayoutNode layoutNodeRequireLayoutNode2 = DelegatableNodeKt.requireLayoutNode(focusTargetNode);
        while (layoutNodeRequireLayoutNode2 != null) {
            if ((layoutNodeRequireLayoutNode2.getNodes$ui_release().getHead$ui_release().getAggregateChildKindSet$ui_release() & iM658constructorimpl2) != 0) {
                while (parent$ui_release2 != null) {
                    if ((parent$ui_release2.getKindSet$ui_release() & iM658constructorimpl2) != 0) {
                        for (Modifier.Node nodePop2 = parent$ui_release2; nodePop2 != null; nodePop2 = DelegatableNodeKt.pop(null)) {
                            if (nodePop2 instanceof FocusTargetNode) {
                                FocusTargetNode focusTargetNode2 = (FocusTargetNode) nodePop2;
                                Boolean boolValueOf = mutableVector != null ? Boolean.valueOf(mutableVector.remove(focusTargetNode2)) : null;
                                if (boolValueOf == null || !boolValueOf.booleanValue()) {
                                    mutableVector2.add(focusTargetNode2);
                                }
                            } else {
                                nodePop2.getKindSet$ui_release();
                            }
                        }
                    }
                    parent$ui_release2 = parent$ui_release2.getParent$ui_release();
                }
            }
            layoutNodeRequireLayoutNode2 = layoutNodeRequireLayoutNode2.getParent$ui_release();
            parent$ui_release2 = (layoutNodeRequireLayoutNode2 == null || (nodes$ui_release = layoutNodeRequireLayoutNode2.getNodes$ui_release()) == null) ? null : nodes$ui_release.getTail$ui_release();
        }
        if (mutableVector != null) {
            int size = mutableVector.getSize() - 1;
            Object[] objArr = mutableVector.content;
            if (size < objArr.length) {
                while (size >= 0) {
                    FocusTargetNode focusTargetNode3 = (FocusTargetNode) objArr[size];
                    if (focusOwner.getActiveFocusTargetNode() != focusTargetNode) {
                        return false;
                    }
                    focusTargetNode3.dispatchFocusCallbacks$ui_release(FocusStateImpl.ActiveParent, FocusStateImpl.Inactive);
                    size--;
                }
            }
        }
        int size2 = mutableVector2.getSize() - 1;
        Object[] objArr2 = mutableVector2.content;
        if (size2 < objArr2.length) {
            while (size2 >= 0) {
                FocusTargetNode focusTargetNode4 = (FocusTargetNode) objArr2[size2];
                if (focusOwner.getActiveFocusTargetNode() != focusTargetNode) {
                    return false;
                }
                focusTargetNode4.dispatchFocusCallbacks$ui_release(FocusStateImpl.Inactive, FocusStateImpl.ActiveParent);
                size2--;
            }
        }
        if (focusOwner.getActiveFocusTargetNode() != focusTargetNode) {
            return false;
        }
        focusTargetNode.dispatchFocusCallbacks$ui_release(focusState, FocusStateImpl.Active);
        if (focusOwner.getActiveFocusTargetNode() != focusTargetNode) {
            return false;
        }
        if (ComposeUiFlags.isViewFocusFixEnabled && DelegatableNodeKt.requireLayoutNode(focusTargetNode).getInteropView() == null) {
            DelegatableNodeKt.requireOwner(focusTargetNode).getFocusOwner().mo140requestFocusForOwner7o62pno(FocusDirection.m117boximpl(FocusDirection.Companion.m128getNextdhqQ8s()), null);
        }
        return true;
    }

    private static final boolean requestFocusForChild(FocusTargetNode focusTargetNode, FocusTargetNode focusTargetNode2) {
        Modifier.Node node;
        Modifier.Node nodePop;
        NodeChain nodes$ui_release;
        NodeChain nodes$ui_release2;
        int iM658constructorimpl = NodeKind.m658constructorimpl(1024);
        if (!focusTargetNode2.getNode().isAttached()) {
            InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
        }
        Modifier.Node parent$ui_release = focusTargetNode2.getNode().getParent$ui_release();
        LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(focusTargetNode2);
        loop0: while (true) {
            node = null;
            if (layoutNodeRequireLayoutNode == null) {
                nodePop = null;
                break;
            }
            if ((layoutNodeRequireLayoutNode.getNodes$ui_release().getHead$ui_release().getAggregateChildKindSet$ui_release() & iM658constructorimpl) != 0) {
                while (parent$ui_release != null) {
                    if ((parent$ui_release.getKindSet$ui_release() & iM658constructorimpl) != 0) {
                        nodePop = parent$ui_release;
                        while (nodePop != null) {
                            if (nodePop instanceof FocusTargetNode) {
                                break loop0;
                            }
                            nodePop.getKindSet$ui_release();
                            nodePop = DelegatableNodeKt.pop(null);
                        }
                    }
                    parent$ui_release = parent$ui_release.getParent$ui_release();
                }
            }
            layoutNodeRequireLayoutNode = layoutNodeRequireLayoutNode.getParent$ui_release();
            parent$ui_release = (layoutNodeRequireLayoutNode == null || (nodes$ui_release2 = layoutNodeRequireLayoutNode.getNodes$ui_release()) == null) ? null : nodes$ui_release2.getTail$ui_release();
        }
        if (!Intrinsics.areEqual(nodePop, focusTargetNode)) {
            throw new IllegalStateException("Non child node cannot request focus.");
        }
        int i = WhenMappings.$EnumSwitchMapping$0[focusTargetNode.getFocusState().ordinal()];
        if (i == 1) {
            boolean zGrantFocus = grantFocus(focusTargetNode2);
            if (zGrantFocus) {
                focusTargetNode.setFocusState(FocusStateImpl.ActiveParent);
            }
            return zGrantFocus;
        }
        if (i != 2) {
            if (i != 3) {
                if (i != 4) {
                    throw new NoWhenBranchMatchedException();
                }
                int iM658constructorimpl2 = NodeKind.m658constructorimpl(1024);
                if (!focusTargetNode.getNode().isAttached()) {
                    InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
                }
                Modifier.Node parent$ui_release2 = focusTargetNode.getNode().getParent$ui_release();
                LayoutNode layoutNodeRequireLayoutNode2 = DelegatableNodeKt.requireLayoutNode(focusTargetNode);
                loop3: while (true) {
                    if (layoutNodeRequireLayoutNode2 == null) {
                        break;
                    }
                    if ((layoutNodeRequireLayoutNode2.getNodes$ui_release().getHead$ui_release().getAggregateChildKindSet$ui_release() & iM658constructorimpl2) != 0) {
                        while (parent$ui_release2 != null) {
                            if ((parent$ui_release2.getKindSet$ui_release() & iM658constructorimpl2) != 0) {
                                for (Modifier.Node nodePop2 = parent$ui_release2; nodePop2 != null; nodePop2 = DelegatableNodeKt.pop(null)) {
                                    if (nodePop2 instanceof FocusTargetNode) {
                                        node = nodePop2;
                                        break loop3;
                                    }
                                    nodePop2.getKindSet$ui_release();
                                }
                            }
                            parent$ui_release2 = parent$ui_release2.getParent$ui_release();
                        }
                    }
                    layoutNodeRequireLayoutNode2 = layoutNodeRequireLayoutNode2.getParent$ui_release();
                    parent$ui_release2 = (layoutNodeRequireLayoutNode2 == null || (nodes$ui_release = layoutNodeRequireLayoutNode2.getNodes$ui_release()) == null) ? null : nodes$ui_release.getTail$ui_release();
                }
                FocusTargetNode focusTargetNode3 = (FocusTargetNode) node;
                if (focusTargetNode3 == null && requestFocusForOwner(focusTargetNode)) {
                    boolean zGrantFocus2 = grantFocus(focusTargetNode2);
                    if (zGrantFocus2) {
                        focusTargetNode.setFocusState(FocusStateImpl.ActiveParent);
                    }
                    return zGrantFocus2;
                }
                if (focusTargetNode3 == null || !requestFocusForChild(focusTargetNode3, focusTargetNode)) {
                    return false;
                }
                boolean zRequestFocusForChild = requestFocusForChild(focusTargetNode, focusTargetNode2);
                if (focusTargetNode.getFocusState() != FocusStateImpl.ActiveParent) {
                    throw new IllegalStateException("Deactivated node is focused");
                }
                if (zRequestFocusForChild) {
                    focusTargetNode3.dispatchFocusCallbacks$ui_release();
                }
                return zRequestFocusForChild;
            }
            requireActiveChild(focusTargetNode);
            if (clearChildFocus$default(focusTargetNode, false, false, 3, null) && grantFocus(focusTargetNode2)) {
                return true;
            }
        }
        return false;
    }

    private static final boolean requestFocusForOwner(FocusTargetNode focusTargetNode) {
        return DelegatableNodeKt.requireOwner(focusTargetNode).getFocusOwner().mo140requestFocusForOwner7o62pno(null, null);
    }

    private static final FocusTargetNode requireActiveChild(FocusTargetNode focusTargetNode) {
        FocusTargetNode activeChild = FocusTraversalKt.getActiveChild(focusTargetNode);
        if (activeChild != null) {
            return activeChild;
        }
        throw new IllegalArgumentException("ActiveParent with no focused child");
    }
}
