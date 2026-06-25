package com.android.systemui.statusbar.notification.collection.render;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: NodeController.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NodeSpecImpl implements NodeSpec {
    private final List children;
    private final NodeController controller;
    private final NodeSpec parent;

    public NodeSpecImpl(NodeSpec nodeSpec, NodeController nodeController) {
        nodeController.getClass();
        this.parent = nodeSpec;
        this.controller = nodeController;
        this.children = new ArrayList();
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeSpec
    public List getChildren() {
        return this.children;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeSpec
    public NodeController getController() {
        return this.controller;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeSpec
    public NodeSpec getParent() {
        return this.parent;
    }
}
