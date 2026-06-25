package androidx.compose.ui.semantics;

/* JADX INFO: compiled from: SemanticsNode.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SemanticsNodeKt {
    /* JADX WARN: Removed duplicated region for block: B:16:0x0037 A[LOOP:0: B:5:0x0016->B:16:0x0037, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x003c A[EDGE_INSN: B:24:0x003c->B:17:0x003c BREAK  A[LOOP:0: B:5:0x0016->B:16:0x0037], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final androidx.compose.ui.semantics.SemanticsNode SemanticsNode(androidx.compose.ui.node.LayoutNode r5, boolean r6) {
        /*
            androidx.compose.ui.node.NodeChain r0 = r5.getNodes$ui_release()
            r1 = 8
            int r1 = androidx.compose.ui.node.NodeKind.m658constructorimpl(r1)
            int r2 = androidx.compose.ui.node.NodeChain.access$getAggregateChildKindSet(r0)
            r2 = r2 & r1
            r3 = 0
            if (r2 == 0) goto L3c
            androidx.compose.ui.Modifier$Node r0 = r0.getHead$ui_release()
        L16:
            if (r0 == 0) goto L3c
            int r2 = r0.getKindSet$ui_release()
            r2 = r2 & r1
            if (r2 == 0) goto L30
            r2 = r0
        L20:
            if (r2 == 0) goto L30
            boolean r4 = r2 instanceof androidx.compose.ui.node.SemanticsModifierNode
            if (r4 == 0) goto L28
            r3 = r2
            goto L3c
        L28:
            r2.getKindSet$ui_release()
            androidx.compose.ui.Modifier$Node r2 = androidx.compose.ui.node.DelegatableNodeKt.access$pop(r3)
            goto L20
        L30:
            int r2 = r0.getAggregateChildKindSet$ui_release()
            r2 = r2 & r1
            if (r2 == 0) goto L3c
            androidx.compose.ui.Modifier$Node r0 = r0.getChild$ui_release()
            goto L16
        L3c:
            r3.getClass()
            androidx.compose.ui.node.SemanticsModifierNode r3 = (androidx.compose.ui.node.SemanticsModifierNode) r3
            androidx.compose.ui.Modifier$Node r0 = r3.getNode()
            androidx.compose.ui.semantics.SemanticsConfiguration r1 = r5.getSemanticsConfiguration()
            if (r1 != 0) goto L50
            androidx.compose.ui.semantics.SemanticsConfiguration r1 = new androidx.compose.ui.semantics.SemanticsConfiguration
            r1.<init>()
        L50:
            androidx.compose.ui.semantics.SemanticsNode r2 = new androidx.compose.ui.semantics.SemanticsNode
            r2.<init>(r0, r6, r5, r1)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.semantics.SemanticsNodeKt.SemanticsNode(androidx.compose.ui.node.LayoutNode, boolean):androidx.compose.ui.semantics.SemanticsNode");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int contentDescriptionFakeNodeId(SemanticsNode semanticsNode) {
        return semanticsNode.getId() + 2000000000;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0040 A[LOOP:0: B:5:0x0016->B:19:0x0040, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0045 A[EDGE_INSN: B:24:0x0045->B:20:0x0045 BREAK  A[LOOP:0: B:5:0x0016->B:19:0x0040], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final androidx.compose.ui.node.SemanticsModifierNode getOuterMergingSemantics(androidx.compose.ui.node.LayoutNode r4) {
        /*
            androidx.compose.ui.node.NodeChain r4 = r4.getNodes$ui_release()
            r0 = 8
            int r0 = androidx.compose.ui.node.NodeKind.m658constructorimpl(r0)
            int r1 = androidx.compose.ui.node.NodeChain.access$getAggregateChildKindSet(r4)
            r1 = r1 & r0
            r2 = 0
            if (r1 == 0) goto L45
            androidx.compose.ui.Modifier$Node r4 = r4.getHead$ui_release()
        L16:
            if (r4 == 0) goto L45
            int r1 = r4.getKindSet$ui_release()
            r1 = r1 & r0
            if (r1 == 0) goto L39
            r1 = r4
        L20:
            if (r1 == 0) goto L39
            boolean r3 = r1 instanceof androidx.compose.ui.node.SemanticsModifierNode
            if (r3 == 0) goto L31
            r3 = r1
            androidx.compose.ui.node.SemanticsModifierNode r3 = (androidx.compose.ui.node.SemanticsModifierNode) r3
            boolean r3 = r3.getShouldMergeDescendantSemantics()
            if (r3 == 0) goto L34
            r2 = r1
            goto L45
        L31:
            r1.getKindSet$ui_release()
        L34:
            androidx.compose.ui.Modifier$Node r1 = androidx.compose.ui.node.DelegatableNodeKt.access$pop(r2)
            goto L20
        L39:
            int r1 = r4.getAggregateChildKindSet$ui_release()
            r1 = r1 & r0
            if (r1 == 0) goto L45
            androidx.compose.ui.Modifier$Node r4 = r4.getChild$ui_release()
            goto L16
        L45:
            androidx.compose.ui.node.SemanticsModifierNode r2 = (androidx.compose.ui.node.SemanticsModifierNode) r2
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.semantics.SemanticsNodeKt.getOuterMergingSemantics(androidx.compose.ui.node.LayoutNode):androidx.compose.ui.node.SemanticsModifierNode");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Role getRole(SemanticsNode semanticsNode) {
        return (Role) SemanticsConfigurationKt.getOrNull(semanticsNode.getUnmergedConfig$ui_release(), SemanticsProperties.INSTANCE.getRole());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int roleFakeNodeId(SemanticsNode semanticsNode) {
        return semanticsNode.getId() + 1000000000;
    }
}
