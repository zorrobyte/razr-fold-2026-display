package androidx.compose.ui.node;

import androidx.collection.MutableObjectIntMap;
import androidx.collection.ObjectIntMapKt;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Actual_jvmKt;
import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.DrawModifier;
import androidx.compose.ui.focus.FocusEventModifierNode;
import androidx.compose.ui.focus.FocusEventModifierNodeKt;
import androidx.compose.ui.focus.FocusPropertiesModifierNode;
import androidx.compose.ui.focus.FocusPropertiesModifierNodeKt;
import androidx.compose.ui.focus.FocusTargetNode;
import androidx.compose.ui.focus.FocusTargetNodeKt;
import androidx.compose.ui.input.key.KeyInputModifierNode;
import androidx.compose.ui.input.pointer.PointerInputModifier;
import androidx.compose.ui.input.rotary.RotaryInputModifierNode;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.modifier.ModifierLocalModifierNode;
import androidx.compose.ui.relocation.BringIntoViewModifierNode;
import androidx.compose.ui.semantics.SemanticsModifier;

/* JADX INFO: compiled from: NodeKind.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class NodeKindKt {
    private static final MutableObjectIntMap classToKindSetMap = ObjectIntMapKt.mutableObjectIntMapOf();

    public static final void autoInvalidateInsertedNode(Modifier.Node node) {
        if (!node.isAttached()) {
            InlineClassHelperKt.throwIllegalStateException("autoInvalidateInsertedNode called on unattached node");
        }
        autoInvalidateNodeIncludingDelegates(node, -1, 1);
    }

    public static final void autoInvalidateNodeIncludingDelegates(Modifier.Node node, int i, int i2) {
        if (!(node instanceof DelegatingNode)) {
            autoInvalidateNodeSelf(node, i & node.getKindSet$ui_release(), i2);
            return;
        }
        DelegatingNode delegatingNode = (DelegatingNode) node;
        autoInvalidateNodeSelf(node, delegatingNode.getSelfKindSet$ui_release() & i, i2);
        int i3 = (~delegatingNode.getSelfKindSet$ui_release()) & i;
        for (Modifier.Node delegate$ui_release = delegatingNode.getDelegate$ui_release(); delegate$ui_release != null; delegate$ui_release = delegate$ui_release.getChild$ui_release()) {
            autoInvalidateNodeIncludingDelegates(delegate$ui_release, i3, i2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final void autoInvalidateNodeSelf(Modifier.Node node, int i, int i2) {
        if (i2 != 0 || node.getShouldAutoInvalidate()) {
            if ((NodeKind.m1404constructorimpl(2) & i) != 0 && (node instanceof LayoutModifierNode)) {
                LayoutModifierNodeKt.invalidateMeasurement((LayoutModifierNode) node);
                if (i2 == 2) {
                    DelegatableNodeKt.m1308requireCoordinator64DMado(node, NodeKind.m1404constructorimpl(2)).onRelease();
                }
            }
            if ((NodeKind.m1404constructorimpl(128) & i) != 0 && (node instanceof LayoutAwareModifierNode) && i2 != 2) {
                DelegatableNodeKt.requireLayoutNode(node).invalidateMeasurements$ui_release();
            }
            if ((NodeKind.m1404constructorimpl(256) & i) != 0 && (node instanceof GlobalPositionAwareModifierNode) && i2 != 2) {
                DelegatableNodeKt.requireLayoutNode(node).invalidateOnPositioned$ui_release();
            }
            if ((NodeKind.m1404constructorimpl(4) & i) != 0 && (node instanceof DrawModifierNode)) {
                DrawModifierNodeKt.invalidateDraw((DrawModifierNode) node);
            }
            if ((NodeKind.m1404constructorimpl(8) & i) != 0 && (node instanceof SemanticsModifierNode)) {
                DelegatableNodeKt.requireLayoutNode(node).setSemanticsInvalidated$ui_release(true);
            }
            if ((NodeKind.m1404constructorimpl(64) & i) != 0 && (node instanceof ParentDataModifierNode)) {
                ParentDataModifierNodeKt.invalidateParentData((ParentDataModifierNode) node);
            }
            if ((NodeKind.m1404constructorimpl(2048) & i) != 0 && (node instanceof FocusPropertiesModifierNode)) {
                FocusPropertiesModifierNode focusPropertiesModifierNode = (FocusPropertiesModifierNode) node;
                if (specifiesCanFocusProperty(focusPropertiesModifierNode)) {
                    if (ComposeUiFlags.isTrackFocusEnabled || i2 == 2) {
                        scheduleInvalidationOfAssociatedFocusTargets(focusPropertiesModifierNode);
                    } else {
                        FocusPropertiesModifierNodeKt.invalidateFocusProperties(focusPropertiesModifierNode);
                    }
                }
            }
            if ((i & NodeKind.m1404constructorimpl(4096)) == 0 || !(node instanceof FocusEventModifierNode)) {
                return;
            }
            FocusEventModifierNodeKt.invalidateFocusEvent((FocusEventModifierNode) node);
        }
    }

    public static final void autoInvalidateRemovedNode(Modifier.Node node) {
        if (!node.isAttached()) {
            InlineClassHelperKt.throwIllegalStateException("autoInvalidateRemovedNode called on unattached node");
        }
        autoInvalidateNodeIncludingDelegates(node, -1, 2);
    }

    public static final void autoInvalidateUpdatedNode(Modifier.Node node) {
        if (!node.isAttached()) {
            InlineClassHelperKt.throwIllegalStateException("autoInvalidateUpdatedNode called on unattached node");
        }
        autoInvalidateNodeIncludingDelegates(node, -1, 0);
    }

    public static final int calculateNodeKindSetFrom(Modifier.Element element) {
        int iM1404constructorimpl = NodeKind.m1404constructorimpl(1);
        if (element instanceof DrawModifier) {
            iM1404constructorimpl |= NodeKind.m1404constructorimpl(4);
        }
        if (element instanceof SemanticsModifier) {
            iM1404constructorimpl |= NodeKind.m1404constructorimpl(8);
        }
        if (element instanceof PointerInputModifier) {
            iM1404constructorimpl |= NodeKind.m1404constructorimpl(16);
        }
        return element instanceof BringIntoViewModifierNode ? NodeKind.m1404constructorimpl(524288) | iM1404constructorimpl : iM1404constructorimpl;
    }

    public static final int calculateNodeKindSetFrom(Modifier.Node node) {
        if (node.getKindSet$ui_release() != 0) {
            return node.getKindSet$ui_release();
        }
        MutableObjectIntMap mutableObjectIntMap = classToKindSetMap;
        Object objClassKeyForObject = Actual_jvmKt.classKeyForObject(node);
        int iFindKeyIndex = mutableObjectIntMap.findKeyIndex(objClassKeyForObject);
        if (iFindKeyIndex >= 0) {
            return mutableObjectIntMap.values[iFindKeyIndex];
        }
        int iM1404constructorimpl = NodeKind.m1404constructorimpl(1);
        if (node instanceof LayoutModifierNode) {
            iM1404constructorimpl |= NodeKind.m1404constructorimpl(2);
        }
        if (node instanceof DrawModifierNode) {
            iM1404constructorimpl |= NodeKind.m1404constructorimpl(4);
        }
        if (node instanceof SemanticsModifierNode) {
            iM1404constructorimpl |= NodeKind.m1404constructorimpl(8);
        }
        if (node instanceof PointerInputModifierNode) {
            iM1404constructorimpl |= NodeKind.m1404constructorimpl(16);
        }
        if (node instanceof ModifierLocalModifierNode) {
            iM1404constructorimpl |= NodeKind.m1404constructorimpl(32);
        }
        if (node instanceof ParentDataModifierNode) {
            iM1404constructorimpl |= NodeKind.m1404constructorimpl(64);
        }
        if (node instanceof LayoutAwareModifierNode) {
            iM1404constructorimpl |= NodeKind.m1404constructorimpl(128);
        }
        if (node instanceof GlobalPositionAwareModifierNode) {
            iM1404constructorimpl |= NodeKind.m1404constructorimpl(256);
        }
        if (node instanceof FocusTargetNode) {
            iM1404constructorimpl |= NodeKind.m1404constructorimpl(1024);
        }
        if (node instanceof FocusPropertiesModifierNode) {
            iM1404constructorimpl |= NodeKind.m1404constructorimpl(2048);
        }
        if (node instanceof FocusEventModifierNode) {
            iM1404constructorimpl |= NodeKind.m1404constructorimpl(4096);
        }
        if (node instanceof KeyInputModifierNode) {
            iM1404constructorimpl |= NodeKind.m1404constructorimpl(8192);
        }
        if (node instanceof RotaryInputModifierNode) {
            iM1404constructorimpl |= NodeKind.m1404constructorimpl(16384);
        }
        if (node instanceof CompositionLocalConsumerModifierNode) {
            iM1404constructorimpl |= NodeKind.m1404constructorimpl(32768);
        }
        if (node instanceof TraversableNode) {
            iM1404constructorimpl |= NodeKind.m1404constructorimpl(262144);
        }
        if (node instanceof BringIntoViewModifierNode) {
            iM1404constructorimpl |= NodeKind.m1404constructorimpl(524288);
        }
        mutableObjectIntMap.set(objClassKeyForObject, iM1404constructorimpl);
        return iM1404constructorimpl;
    }

    public static final int calculateNodeKindSetFromIncludingDelegates(Modifier.Node node) {
        if (!(node instanceof DelegatingNode)) {
            return calculateNodeKindSetFrom(node);
        }
        DelegatingNode delegatingNode = (DelegatingNode) node;
        int selfKindSet$ui_release = delegatingNode.getSelfKindSet$ui_release();
        for (Modifier.Node delegate$ui_release = delegatingNode.getDelegate$ui_release(); delegate$ui_release != null; delegate$ui_release = delegate$ui_release.getChild$ui_release()) {
            selfKindSet$ui_release |= calculateNodeKindSetFromIncludingDelegates(delegate$ui_release);
        }
        return selfKindSet$ui_release;
    }

    /* JADX INFO: renamed from: getIncludeSelfInTraversal-H91voCI, reason: not valid java name */
    public static final boolean m1405getIncludeSelfInTraversalH91voCI(int i) {
        return (i & NodeKind.m1404constructorimpl(128)) != 0;
    }

    private static final void scheduleInvalidationOfAssociatedFocusTargets(FocusPropertiesModifierNode focusPropertiesModifierNode) {
        int iM1404constructorimpl = NodeKind.m1404constructorimpl(1024);
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
            if ((nodePop.getAggregateChildKindSet$ui_release() & iM1404constructorimpl) == 0) {
                DelegatableNodeKt.addLayoutNodeChildren(mutableVector, nodePop, false);
            } else {
                while (true) {
                    if (nodePop == null) {
                        break;
                    }
                    if ((nodePop.getKindSet$ui_release() & iM1404constructorimpl) != 0) {
                        MutableVector mutableVector2 = null;
                        while (nodePop != null) {
                            if (nodePop instanceof FocusTargetNode) {
                                FocusTargetNodeKt.invalidateFocusTarget((FocusTargetNode) nodePop);
                            } else if ((nodePop.getKindSet$ui_release() & iM1404constructorimpl) != 0 && (nodePop instanceof DelegatingNode)) {
                                int i = 0;
                                for (Modifier.Node delegate$ui_release = ((DelegatingNode) nodePop).getDelegate$ui_release(); delegate$ui_release != null; delegate$ui_release = delegate$ui_release.getChild$ui_release()) {
                                    if ((delegate$ui_release.getKindSet$ui_release() & iM1404constructorimpl) != 0) {
                                        i++;
                                        if (i == 1) {
                                            nodePop = delegate$ui_release;
                                        } else {
                                            if (mutableVector2 == null) {
                                                mutableVector2 = new MutableVector(new Modifier.Node[16], 0);
                                            }
                                            if (nodePop != null) {
                                                mutableVector2.add(nodePop);
                                                nodePop = null;
                                            }
                                            mutableVector2.add(delegate$ui_release);
                                        }
                                    }
                                }
                                if (i == 1) {
                                }
                            }
                            nodePop = DelegatableNodeKt.pop(mutableVector2);
                        }
                    } else {
                        nodePop = nodePop.getChild$ui_release();
                    }
                }
            }
        }
    }

    private static final boolean specifiesCanFocusProperty(FocusPropertiesModifierNode focusPropertiesModifierNode) {
        CanFocusChecker canFocusChecker = CanFocusChecker.INSTANCE;
        canFocusChecker.reset();
        focusPropertiesModifierNode.applyFocusProperties(canFocusChecker);
        return canFocusChecker.isCanFocusSet();
    }
}
