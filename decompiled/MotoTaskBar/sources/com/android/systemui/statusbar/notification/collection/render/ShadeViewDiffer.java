package com.android.systemui.statusbar.notification.collection.render;

import android.os.Trace;
import android.view.View;
import com.android.app.tracing.TraceUtilsKt;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: ShadeViewDiffer.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ShadeViewDiffer {
    private final ShadeViewDifferLogger logger;
    private final Map nodes;
    private final ShadeNode rootNode;

    public ShadeViewDiffer(NodeController nodeController, ShadeViewDifferLogger shadeViewDifferLogger) {
        nodeController.getClass();
        shadeViewDifferLogger.getClass();
        this.logger = shadeViewDifferLogger;
        ShadeNode shadeNode = new ShadeNode(nodeController);
        this.rootNode = shadeNode;
        this.nodes = MapsKt.mutableMapOf(TuplesKt.to(nodeController, shadeNode));
    }

    private final void attachChildren(ShadeNode shadeNode, Map map) {
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("attachChildren");
        }
        try {
            Object obj = map.get(shadeNode.getController());
            if (obj == null) {
                throw new IllegalStateException("Required value was null.");
            }
            int i = 0;
            for (NodeSpec nodeSpec : ((NodeSpec) obj).getChildren()) {
                int i2 = i + 1;
                View childAt = shadeNode.getChildAt(i);
                ShadeNode node = getNode(nodeSpec);
                if (!Intrinsics.areEqual(node.getView(), childAt)) {
                    if (node.removeFromParentIfKeptForAnimation()) {
                        this.logger.logDetachingChild(node.getLabel(), false, true, null, null);
                    }
                    ShadeNode parent = node.getParent();
                    if (parent == null) {
                        this.logger.logAttachingChild(node.getLabel(), shadeNode.getLabel(), i);
                        shadeNode.addChildAt(node, i);
                        node.setParent(shadeNode);
                    } else {
                        if (!Intrinsics.areEqual(parent, shadeNode)) {
                            String label = node.getLabel();
                            String label2 = shadeNode.getLabel();
                            ShadeNode parent2 = node.getParent();
                            throw new IllegalStateException("Child " + label + " should have parent " + label2 + " but is actually " + (parent2 != null ? parent2.getLabel() : null));
                        }
                        this.logger.logMovingChild(node.getLabel(), shadeNode.getLabel(), i);
                        shadeNode.moveChildTo(node, i);
                    }
                }
                node.resetKeepInParentForAnimation();
                if (!nodeSpec.getChildren().isEmpty()) {
                    attachChildren(node, map);
                }
                i = i2;
            }
            Unit unit = Unit.INSTANCE;
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        } finally {
        }
    }

    private final void detachChildren(ShadeNode shadeNode, Map map) {
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("detachChildren");
        }
        try {
            Collection collectionValues = this.nodes.values();
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(collectionValues, 10)), 16));
            for (Object obj : collectionValues) {
                linkedHashMap.put(((ShadeNode) obj).getView(), obj);
            }
            detachChildren$lambda$4$detachRecursively(linkedHashMap, this, shadeNode, map);
            Unit unit = Unit.INSTANCE;
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    private static final void detachChildren$lambda$4$detachRecursively(Map map, ShadeViewDiffer shadeViewDiffer, ShadeNode shadeNode, Map map2) {
        NodeSpec nodeSpec = (NodeSpec) map2.get(shadeNode.getController());
        int childCount = shadeNode.getChildCount();
        while (true) {
            childCount--;
            if (-1 >= childCount) {
                return;
            }
            ShadeNode shadeNode2 = (ShadeNode) map.get(shadeNode.getChildAt(childCount));
            if (shadeNode2 != null) {
                shadeViewDiffer.maybeDetachChild(shadeNode, nodeSpec, shadeNode2, (NodeSpec) map2.get(shadeNode2.getController()));
                if (shadeNode2.getController().getChildCount() > 0) {
                    detachChildren$lambda$4$detachRecursively(map, shadeViewDiffer, shadeNode2, map2);
                }
            }
        }
    }

    private final ShadeNode getNode(NodeSpec nodeSpec) {
        ShadeNode shadeNode = (ShadeNode) this.nodes.get(nodeSpec.getController());
        if (shadeNode != null) {
            return shadeNode;
        }
        ShadeNode shadeNode2 = new ShadeNode(nodeSpec.getController());
        this.nodes.put(shadeNode2.getController(), shadeNode2);
        return shadeNode2;
    }

    private final void maybeDetachChild(ShadeNode shadeNode, NodeSpec nodeSpec, ShadeNode shadeNode2, NodeSpec nodeSpec2) {
        NodeSpec parent;
        ShadeNode node = (nodeSpec2 == null || (parent = nodeSpec2.getParent()) == null) ? null : getNode(parent);
        if (Intrinsics.areEqual(node, shadeNode)) {
            return;
        }
        boolean z = node == null;
        if (z) {
            this.nodes.remove(shadeNode2.getController());
        }
        if (z && nodeSpec == null && shadeNode2.offerToKeepInParentForAnimation()) {
            this.logger.logSkipDetachingChild(shadeNode2.getLabel(), shadeNode.getLabel(), !z, true);
            return;
        }
        this.logger.logDetachingChild(shadeNode2.getLabel(), !z, nodeSpec == null, shadeNode.getLabel(), node != null ? node.getLabel() : null);
        shadeNode.removeChild(shadeNode2, !z);
        shadeNode2.setParent(null);
    }

    private final void registerNodes(NodeSpec nodeSpec, Map map) {
        if (map.containsKey(nodeSpec.getController())) {
            throw new DuplicateNodeException("Node " + nodeSpec.getController().getNodeLabel() + " appears more than once");
        }
        map.put(nodeSpec.getController(), nodeSpec);
        if (nodeSpec.getChildren().isEmpty()) {
            return;
        }
        Iterator it = nodeSpec.getChildren().iterator();
        while (it.hasNext()) {
            registerNodes((NodeSpec) it.next(), map);
        }
    }

    private final Map treeToMap(NodeSpec nodeSpec) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        try {
            registerNodes(nodeSpec, linkedHashMap);
            return linkedHashMap;
        } catch (DuplicateNodeException e) {
            this.logger.logDuplicateNodeInTree(nodeSpec, e);
            throw e;
        }
    }

    public final void applySpec(NodeSpec nodeSpec) {
        nodeSpec.getClass();
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("ShadeViewDiffer.applySpec");
        }
        try {
            Map mapTreeToMap = treeToMap(nodeSpec);
            if (Intrinsics.areEqual(nodeSpec.getController(), this.rootNode.getController())) {
                detachChildren(this.rootNode, mapTreeToMap);
                attachChildren(this.rootNode, mapTreeToMap);
                Unit unit = Unit.INSTANCE;
                if (zIsEnabled) {
                    return;
                } else {
                    return;
                }
            }
            throw new IllegalArgumentException("Tree root " + nodeSpec.getController().getNodeLabel() + " does not match own root at " + this.rootNode.getLabel());
        } finally {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }
}
