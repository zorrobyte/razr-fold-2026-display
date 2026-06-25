package androidx.compose.ui.focus;

import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.focus.FocusDirection;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.NodeChain;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.node.NodeKind;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FocusTraversal.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class FocusTraversalKt {

    /* JADX INFO: compiled from: FocusTraversal.kt */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[LayoutDirection.values().length];
            try {
                iArr[LayoutDirection.Ltr.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LayoutDirection.Rtl.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[FocusStateImpl.values().length];
            try {
                iArr2[FocusStateImpl.Active.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[FocusStateImpl.ActiveParent.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[FocusStateImpl.Captured.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[FocusStateImpl.Inactive.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    /* JADX INFO: renamed from: customFocusSearch--OM-vw8, reason: not valid java name */
    public static final FocusRequester m156customFocusSearchOMvw8(FocusTargetNode focusTargetNode, int i, LayoutDirection layoutDirection) {
        FocusRequester end;
        FocusRequester focusRequester;
        FocusRequester start;
        FocusProperties focusPropertiesFetchFocusProperties$ui_release = focusTargetNode.fetchFocusProperties$ui_release();
        FocusDirection.Companion companion = FocusDirection.Companion;
        if (FocusDirection.m120equalsimpl0(i, companion.m128getNextdhqQ8s())) {
            return focusPropertiesFetchFocusProperties$ui_release.getNext();
        }
        if (FocusDirection.m120equalsimpl0(i, companion.m129getPreviousdhqQ8s())) {
            return focusPropertiesFetchFocusProperties$ui_release.getPrevious();
        }
        if (FocusDirection.m120equalsimpl0(i, companion.m131getUpdhqQ8s())) {
            return focusPropertiesFetchFocusProperties$ui_release.getUp();
        }
        if (FocusDirection.m120equalsimpl0(i, companion.m124getDowndhqQ8s())) {
            return focusPropertiesFetchFocusProperties$ui_release.getDown();
        }
        if (FocusDirection.m120equalsimpl0(i, companion.m127getLeftdhqQ8s())) {
            int i2 = WhenMappings.$EnumSwitchMapping$0[layoutDirection.ordinal()];
            if (i2 == 1) {
                start = focusPropertiesFetchFocusProperties$ui_release.getStart();
            } else {
                if (i2 != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                start = focusPropertiesFetchFocusProperties$ui_release.getEnd();
            }
            focusRequester = start != FocusRequester.Companion.getDefault() ? start : null;
            return focusRequester == null ? focusPropertiesFetchFocusProperties$ui_release.getLeft() : focusRequester;
        }
        if (FocusDirection.m120equalsimpl0(i, companion.m130getRightdhqQ8s())) {
            int i3 = WhenMappings.$EnumSwitchMapping$0[layoutDirection.ordinal()];
            if (i3 == 1) {
                end = focusPropertiesFetchFocusProperties$ui_release.getEnd();
            } else {
                if (i3 != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                end = focusPropertiesFetchFocusProperties$ui_release.getStart();
            }
            focusRequester = end != FocusRequester.Companion.getDefault() ? end : null;
            return focusRequester == null ? focusPropertiesFetchFocusProperties$ui_release.getRight() : focusRequester;
        }
        if (!(FocusDirection.m120equalsimpl0(i, companion.m125getEnterdhqQ8s()) ? true : FocusDirection.m120equalsimpl0(i, companion.m126getExitdhqQ8s()))) {
            throw new IllegalStateException("invalid FocusDirection");
        }
        CancelIndicatingFocusBoundaryScope cancelIndicatingFocusBoundaryScope = new CancelIndicatingFocusBoundaryScope(i, null);
        FocusTransactionManager focusTransactionManager = FocusTargetNodeKt.getFocusTransactionManager(focusTargetNode);
        int generation = focusTransactionManager != null ? focusTransactionManager.getGeneration() : 0;
        FocusOwner focusOwner = DelegatableNodeKt.requireOwner(focusTargetNode).getFocusOwner();
        FocusTargetNode activeFocusTargetNode = focusOwner.getActiveFocusTargetNode();
        if (FocusDirection.m120equalsimpl0(i, companion.m125getEnterdhqQ8s())) {
            focusPropertiesFetchFocusProperties$ui_release.getOnEnter().invoke(cancelIndicatingFocusBoundaryScope);
        } else {
            focusPropertiesFetchFocusProperties$ui_release.getOnExit().invoke(cancelIndicatingFocusBoundaryScope);
        }
        return cancelIndicatingFocusBoundaryScope.isCanceled() ? FocusRequester.Companion.getCancel() : (generation != (focusTransactionManager != null ? focusTransactionManager.getGeneration() : 0) || (ComposeUiFlags.isTrackFocusEnabled && activeFocusTargetNode != focusOwner.getActiveFocusTargetNode())) ? FocusRequester.Companion.getRedirect$ui_release() : FocusRequester.Companion.getDefault();
    }

    /* JADX WARN: Code restructure failed: missing block: B:53:0x006e, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final androidx.compose.ui.focus.FocusTargetNode findActiveFocusNode(androidx.compose.ui.focus.FocusTargetNode r6) {
        /*
            boolean r0 = androidx.compose.ui.ComposeUiFlags.isTrackFocusEnabled
            r1 = 0
            if (r0 == 0) goto L1b
            androidx.compose.ui.node.Owner r6 = androidx.compose.ui.node.DelegatableNodeKt.requireOwner(r6)
            androidx.compose.ui.focus.FocusOwner r6 = r6.getFocusOwner()
            androidx.compose.ui.focus.FocusTargetNode r6 = r6.getActiveFocusTargetNode()
            if (r6 == 0) goto L1a
            boolean r0 = r6.isAttached()
            if (r0 == 0) goto L1a
            return r6
        L1a:
            return r1
        L1b:
            androidx.compose.ui.focus.FocusStateImpl r0 = r6.getFocusState()
            int[] r2 = androidx.compose.ui.focus.FocusTraversalKt.WhenMappings.$EnumSwitchMapping$1
            int r0 = r0.ordinal()
            r0 = r2[r0]
            r2 = 1
            if (r0 == r2) goto Lb0
            r3 = 2
            if (r0 == r3) goto L3a
            r2 = 3
            if (r0 == r2) goto Lb0
            r6 = 4
            if (r0 != r6) goto L34
            return r1
        L34:
            kotlin.NoWhenBranchMatchedException r6 = new kotlin.NoWhenBranchMatchedException
            r6.<init>()
            throw r6
        L3a:
            r0 = 1024(0x400, float:1.435E-42)
            int r0 = androidx.compose.ui.node.NodeKind.m658constructorimpl(r0)
            androidx.compose.ui.Modifier$Node r3 = r6.getNode()
            boolean r3 = r3.isAttached()
            if (r3 != 0) goto L4f
            java.lang.String r3 = "visitChildren called on an unattached node"
            androidx.compose.ui.internal.InlineClassHelperKt.throwIllegalStateException(r3)
        L4f:
            androidx.compose.runtime.collection.MutableVector r3 = new androidx.compose.runtime.collection.MutableVector
            r4 = 16
            androidx.compose.ui.Modifier$Node[] r4 = new androidx.compose.ui.Modifier.Node[r4]
            r5 = 0
            r3.<init>(r4, r5)
            androidx.compose.ui.Modifier$Node r4 = r6.getNode()
            androidx.compose.ui.Modifier$Node r4 = r4.getChild$ui_release()
            if (r4 != 0) goto L6b
            androidx.compose.ui.Modifier$Node r6 = r6.getNode()
            androidx.compose.ui.node.DelegatableNodeKt.access$addLayoutNodeChildren(r3, r6, r5)
            goto L6e
        L6b:
            r3.add(r4)
        L6e:
            int r6 = r3.getSize()
            if (r6 == 0) goto Laf
            int r6 = r3.getSize()
            int r6 = r6 - r2
            java.lang.Object r6 = r3.removeAt(r6)
            androidx.compose.ui.Modifier$Node r6 = (androidx.compose.ui.Modifier.Node) r6
            int r4 = r6.getAggregateChildKindSet$ui_release()
            r4 = r4 & r0
            if (r4 != 0) goto L8a
            androidx.compose.ui.node.DelegatableNodeKt.access$addLayoutNodeChildren(r3, r6, r5)
            goto L6e
        L8a:
            if (r6 == 0) goto L6e
            int r4 = r6.getKindSet$ui_release()
            r4 = r4 & r0
            if (r4 == 0) goto Laa
        L93:
            if (r6 == 0) goto L6e
            boolean r4 = r6 instanceof androidx.compose.ui.focus.FocusTargetNode
            if (r4 == 0) goto La2
            androidx.compose.ui.focus.FocusTargetNode r6 = (androidx.compose.ui.focus.FocusTargetNode) r6
            androidx.compose.ui.focus.FocusTargetNode r6 = findActiveFocusNode(r6)
            if (r6 == 0) goto La5
            return r6
        La2:
            r6.getKindSet$ui_release()
        La5:
            androidx.compose.ui.Modifier$Node r6 = androidx.compose.ui.node.DelegatableNodeKt.access$pop(r1)
            goto L93
        Laa:
            androidx.compose.ui.Modifier$Node r6 = r6.getChild$ui_release()
            goto L8a
        Laf:
            return r1
        Lb0:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.focus.FocusTraversalKt.findActiveFocusNode(androidx.compose.ui.focus.FocusTargetNode):androidx.compose.ui.focus.FocusTargetNode");
    }

    private static final FocusTargetNode findNonDeactivatedParent(FocusTargetNode focusTargetNode) {
        NodeChain nodes$ui_release;
        int iM658constructorimpl = NodeKind.m658constructorimpl(1024);
        if (!focusTargetNode.getNode().isAttached()) {
            InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
        }
        Modifier.Node parent$ui_release = focusTargetNode.getNode().getParent$ui_release();
        LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(focusTargetNode);
        while (layoutNodeRequireLayoutNode != null) {
            if ((layoutNodeRequireLayoutNode.getNodes$ui_release().getHead$ui_release().getAggregateChildKindSet$ui_release() & iM658constructorimpl) != 0) {
                while (parent$ui_release != null) {
                    if ((parent$ui_release.getKindSet$ui_release() & iM658constructorimpl) != 0) {
                        for (Modifier.Node nodePop = parent$ui_release; nodePop != null; nodePop = DelegatableNodeKt.pop(null)) {
                            if (nodePop instanceof FocusTargetNode) {
                                FocusTargetNode focusTargetNode2 = (FocusTargetNode) nodePop;
                                if (focusTargetNode2.fetchFocusProperties$ui_release().getCanFocus()) {
                                    return focusTargetNode2;
                                }
                            } else {
                                nodePop.getKindSet$ui_release();
                            }
                        }
                    }
                    parent$ui_release = parent$ui_release.getParent$ui_release();
                }
            }
            layoutNodeRequireLayoutNode = layoutNodeRequireLayoutNode.getParent$ui_release();
            parent$ui_release = (layoutNodeRequireLayoutNode == null || (nodes$ui_release = layoutNodeRequireLayoutNode.getNodes$ui_release()) == null) ? null : nodes$ui_release.getTail$ui_release();
        }
        return null;
    }

    public static final Rect focusRect(FocusTargetNode focusTargetNode) {
        Rect rectLocalBoundingBoxOf;
        NodeCoordinator coordinator$ui_release = focusTargetNode.getCoordinator$ui_release();
        return (coordinator$ui_release == null || (rectLocalBoundingBoxOf = LayoutCoordinatesKt.findRootCoordinates(coordinator$ui_release).localBoundingBoxOf(coordinator$ui_release, false)) == null) ? Rect.Companion.getZero() : rectLocalBoundingBoxOf;
    }

    /* JADX INFO: renamed from: focusSearch-0X8WOeE, reason: not valid java name */
    public static final Boolean m157focusSearch0X8WOeE(FocusTargetNode focusTargetNode, int i, LayoutDirection layoutDirection, Rect rect, Function1 function1) {
        int iM130getRightdhqQ8s;
        FocusDirection.Companion companion = FocusDirection.Companion;
        if (FocusDirection.m120equalsimpl0(i, companion.m128getNextdhqQ8s()) ? true : FocusDirection.m120equalsimpl0(i, companion.m129getPreviousdhqQ8s())) {
            return Boolean.valueOf(OneDimensionalFocusSearchKt.m165oneDimensionalFocusSearchOMvw8(focusTargetNode, i, function1));
        }
        if (FocusDirection.m120equalsimpl0(i, companion.m127getLeftdhqQ8s()) ? true : FocusDirection.m120equalsimpl0(i, companion.m130getRightdhqQ8s()) ? true : FocusDirection.m120equalsimpl0(i, companion.m131getUpdhqQ8s()) ? true : FocusDirection.m120equalsimpl0(i, companion.m124getDowndhqQ8s())) {
            return TwoDimensionalFocusSearchKt.m174twoDimensionalFocusSearchsMXa3k8(focusTargetNode, i, rect, function1);
        }
        if (!FocusDirection.m120equalsimpl0(i, companion.m125getEnterdhqQ8s())) {
            if (FocusDirection.m120equalsimpl0(i, companion.m126getExitdhqQ8s())) {
                FocusTargetNode focusTargetNodeFindActiveFocusNode = findActiveFocusNode(focusTargetNode);
                FocusTargetNode focusTargetNodeFindNonDeactivatedParent = focusTargetNodeFindActiveFocusNode != null ? findNonDeactivatedParent(focusTargetNodeFindActiveFocusNode) : null;
                return Boolean.valueOf((focusTargetNodeFindNonDeactivatedParent == null || Intrinsics.areEqual(focusTargetNodeFindNonDeactivatedParent, focusTargetNode)) ? false : ((Boolean) function1.invoke(focusTargetNodeFindNonDeactivatedParent)).booleanValue());
            }
            throw new IllegalStateException(("Focus search invoked with invalid FocusDirection " + ((Object) FocusDirection.m122toStringimpl(i))).toString());
        }
        int i2 = WhenMappings.$EnumSwitchMapping$0[layoutDirection.ordinal()];
        if (i2 == 1) {
            iM130getRightdhqQ8s = companion.m130getRightdhqQ8s();
        } else {
            if (i2 != 2) {
                throw new NoWhenBranchMatchedException();
            }
            iM130getRightdhqQ8s = companion.m127getLeftdhqQ8s();
        }
        FocusTargetNode focusTargetNodeFindActiveFocusNode2 = findActiveFocusNode(focusTargetNode);
        if (focusTargetNodeFindActiveFocusNode2 != null) {
            return TwoDimensionalFocusSearchKt.m174twoDimensionalFocusSearchsMXa3k8(focusTargetNodeFindActiveFocusNode2, iM130getRightdhqQ8s, rect, function1);
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x0040, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final androidx.compose.ui.focus.FocusTargetNode getActiveChild(androidx.compose.ui.focus.FocusTargetNode r7) {
        /*
            androidx.compose.ui.Modifier$Node r0 = r7.getNode()
            boolean r0 = r0.isAttached()
            r1 = 0
            if (r0 != 0) goto Lc
            return r1
        Lc:
            r0 = 1024(0x400, float:1.435E-42)
            int r0 = androidx.compose.ui.node.NodeKind.m658constructorimpl(r0)
            androidx.compose.ui.Modifier$Node r2 = r7.getNode()
            boolean r2 = r2.isAttached()
            if (r2 != 0) goto L21
            java.lang.String r2 = "visitChildren called on an unattached node"
            androidx.compose.ui.internal.InlineClassHelperKt.throwIllegalStateException(r2)
        L21:
            androidx.compose.runtime.collection.MutableVector r2 = new androidx.compose.runtime.collection.MutableVector
            r3 = 16
            androidx.compose.ui.Modifier$Node[] r3 = new androidx.compose.ui.Modifier.Node[r3]
            r4 = 0
            r2.<init>(r3, r4)
            androidx.compose.ui.Modifier$Node r3 = r7.getNode()
            androidx.compose.ui.Modifier$Node r3 = r3.getChild$ui_release()
            if (r3 != 0) goto L3d
            androidx.compose.ui.Modifier$Node r7 = r7.getNode()
            androidx.compose.ui.node.DelegatableNodeKt.access$addLayoutNodeChildren(r2, r7, r4)
            goto L40
        L3d:
            r2.add(r3)
        L40:
            int r7 = r2.getSize()
            if (r7 == 0) goto L9b
            int r7 = r2.getSize()
            r3 = 1
            int r7 = r7 - r3
            java.lang.Object r7 = r2.removeAt(r7)
            androidx.compose.ui.Modifier$Node r7 = (androidx.compose.ui.Modifier.Node) r7
            int r5 = r7.getAggregateChildKindSet$ui_release()
            r5 = r5 & r0
            if (r5 != 0) goto L5d
            androidx.compose.ui.node.DelegatableNodeKt.access$addLayoutNodeChildren(r2, r7, r4)
            goto L40
        L5d:
            if (r7 == 0) goto L40
            int r5 = r7.getKindSet$ui_release()
            r5 = r5 & r0
            if (r5 == 0) goto L96
        L66:
            if (r7 == 0) goto L40
            boolean r5 = r7 instanceof androidx.compose.ui.focus.FocusTargetNode
            if (r5 == 0) goto L8e
            androidx.compose.ui.focus.FocusTargetNode r7 = (androidx.compose.ui.focus.FocusTargetNode) r7
            androidx.compose.ui.Modifier$Node r5 = r7.getNode()
            boolean r5 = r5.isAttached()
            if (r5 == 0) goto L91
            androidx.compose.ui.focus.FocusStateImpl r5 = r7.getFocusState()
            int[] r6 = androidx.compose.ui.focus.FocusTraversalKt.WhenMappings.$EnumSwitchMapping$1
            int r5 = r5.ordinal()
            r5 = r6[r5]
            if (r5 == r3) goto L8d
            r6 = 2
            if (r5 == r6) goto L8d
            r6 = 3
            if (r5 == r6) goto L8d
            goto L91
        L8d:
            return r7
        L8e:
            r7.getKindSet$ui_release()
        L91:
            androidx.compose.ui.Modifier$Node r7 = androidx.compose.ui.node.DelegatableNodeKt.access$pop(r1)
            goto L66
        L96:
            androidx.compose.ui.Modifier$Node r7 = r7.getChild$ui_release()
            goto L5d
        L9b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.focus.FocusTraversalKt.getActiveChild(androidx.compose.ui.focus.FocusTargetNode):androidx.compose.ui.focus.FocusTargetNode");
    }

    public static final boolean isEligibleForFocusSearch(FocusTargetNode focusTargetNode) {
        LayoutNode layoutNode;
        NodeCoordinator coordinator$ui_release;
        LayoutNode layoutNode2;
        NodeCoordinator coordinator$ui_release2 = focusTargetNode.getCoordinator$ui_release();
        return (coordinator$ui_release2 == null || (layoutNode = coordinator$ui_release2.getLayoutNode()) == null || !layoutNode.isPlaced() || (coordinator$ui_release = focusTargetNode.getCoordinator$ui_release()) == null || (layoutNode2 = coordinator$ui_release.getLayoutNode()) == null || !layoutNode2.isAttached()) ? false : true;
    }
}
