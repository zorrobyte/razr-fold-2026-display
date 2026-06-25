package com.android.systemui.statusbar.notification.collection.render;

import java.util.Iterator;

/* JADX INFO: compiled from: NodeController.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class NodeControllerKt {
    public static final String treeSpecToStr(NodeSpec nodeSpec) {
        nodeSpec.getClass();
        StringBuilder sb = new StringBuilder();
        treeSpecToStrHelper(nodeSpec, sb, "");
        String string = sb.toString();
        string.getClass();
        return string;
    }

    private static final void treeSpecToStrHelper(NodeSpec nodeSpec, StringBuilder sb, String str) {
        sb.append(str + "{" + nodeSpec.getController().getNodeLabel() + "}\n");
        if (nodeSpec.getChildren().isEmpty()) {
            return;
        }
        String str2 = str + "  ";
        Iterator it = nodeSpec.getChildren().iterator();
        while (it.hasNext()) {
            treeSpecToStrHelper((NodeSpec) it.next(), sb, str2);
        }
    }
}
