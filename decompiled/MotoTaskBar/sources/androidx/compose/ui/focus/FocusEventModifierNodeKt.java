package androidx.compose.ui.focus;

import androidx.compose.ui.node.DelegatableNodeKt;

/* JADX INFO: compiled from: FocusEventModifierNode.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class FocusEventModifierNodeKt {

    /* JADX INFO: compiled from: FocusEventModifierNode.kt */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FocusStateImpl.values().length];
            try {
                iArr[FocusStateImpl.Active.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FocusStateImpl.ActiveParent.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[FocusStateImpl.Captured.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[FocusStateImpl.Inactive.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:58:0x0060, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final androidx.compose.ui.focus.FocusState getFocusState(androidx.compose.ui.focus.FocusEventModifierNode r9) {
        /*
            r0 = 1024(0x400, float:1.435E-42)
            int r0 = androidx.compose.ui.node.NodeKind.m658constructorimpl(r0)
            androidx.compose.ui.Modifier$Node r1 = r9.getNode()
        La:
            r2 = 3
            r3 = 2
            r4 = 1
            r5 = 0
            if (r1 == 0) goto L32
            boolean r6 = r1 instanceof androidx.compose.ui.focus.FocusTargetNode
            if (r6 == 0) goto L2a
            androidx.compose.ui.focus.FocusTargetNode r1 = (androidx.compose.ui.focus.FocusTargetNode) r1
            androidx.compose.ui.focus.FocusStateImpl r1 = r1.getFocusState()
            int[] r6 = androidx.compose.ui.focus.FocusEventModifierNodeKt.WhenMappings.$EnumSwitchMapping$0
            int r7 = r1.ordinal()
            r6 = r6[r7]
            if (r6 == r4) goto L29
            if (r6 == r3) goto L29
            if (r6 == r2) goto L29
            goto L2d
        L29:
            return r1
        L2a:
            r1.getKindSet$ui_release()
        L2d:
            androidx.compose.ui.Modifier$Node r1 = androidx.compose.ui.node.DelegatableNodeKt.access$pop(r5)
            goto La
        L32:
            androidx.compose.ui.Modifier$Node r1 = r9.getNode()
            boolean r1 = r1.isAttached()
            if (r1 != 0) goto L41
            java.lang.String r1 = "visitChildren called on an unattached node"
            androidx.compose.ui.internal.InlineClassHelperKt.throwIllegalStateException(r1)
        L41:
            androidx.compose.runtime.collection.MutableVector r1 = new androidx.compose.runtime.collection.MutableVector
            r6 = 16
            androidx.compose.ui.Modifier$Node[] r6 = new androidx.compose.ui.Modifier.Node[r6]
            r7 = 0
            r1.<init>(r6, r7)
            androidx.compose.ui.Modifier$Node r6 = r9.getNode()
            androidx.compose.ui.Modifier$Node r6 = r6.getChild$ui_release()
            if (r6 != 0) goto L5d
            androidx.compose.ui.Modifier$Node r9 = r9.getNode()
            androidx.compose.ui.node.DelegatableNodeKt.access$addLayoutNodeChildren(r1, r9, r7)
            goto L60
        L5d:
            r1.add(r6)
        L60:
            int r9 = r1.getSize()
            if (r9 == 0) goto Lae
            int r9 = r1.getSize()
            int r9 = r9 - r4
            java.lang.Object r9 = r1.removeAt(r9)
            androidx.compose.ui.Modifier$Node r9 = (androidx.compose.ui.Modifier.Node) r9
            int r6 = r9.getAggregateChildKindSet$ui_release()
            r6 = r6 & r0
            if (r6 != 0) goto L7c
            androidx.compose.ui.node.DelegatableNodeKt.access$addLayoutNodeChildren(r1, r9, r7)
            goto L60
        L7c:
            if (r9 == 0) goto L60
            int r6 = r9.getKindSet$ui_release()
            r6 = r6 & r0
            if (r6 == 0) goto La9
        L85:
            if (r9 == 0) goto L60
            boolean r6 = r9 instanceof androidx.compose.ui.focus.FocusTargetNode
            if (r6 == 0) goto La1
            androidx.compose.ui.focus.FocusTargetNode r9 = (androidx.compose.ui.focus.FocusTargetNode) r9
            androidx.compose.ui.focus.FocusStateImpl r9 = r9.getFocusState()
            int[] r6 = androidx.compose.ui.focus.FocusEventModifierNodeKt.WhenMappings.$EnumSwitchMapping$0
            int r8 = r9.ordinal()
            r6 = r6[r8]
            if (r6 == r4) goto La0
            if (r6 == r3) goto La0
            if (r6 == r2) goto La0
            goto La4
        La0:
            return r9
        La1:
            r9.getKindSet$ui_release()
        La4:
            androidx.compose.ui.Modifier$Node r9 = androidx.compose.ui.node.DelegatableNodeKt.access$pop(r5)
            goto L85
        La9:
            androidx.compose.ui.Modifier$Node r9 = r9.getChild$ui_release()
            goto L7c
        Lae:
            androidx.compose.ui.focus.FocusStateImpl r9 = androidx.compose.ui.focus.FocusStateImpl.Inactive
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.focus.FocusEventModifierNodeKt.getFocusState(androidx.compose.ui.focus.FocusEventModifierNode):androidx.compose.ui.focus.FocusState");
    }

    public static final void invalidateFocusEvent(FocusEventModifierNode focusEventModifierNode) {
        DelegatableNodeKt.requireOwner(focusEventModifierNode).getFocusOwner().scheduleInvalidation(focusEventModifierNode);
    }
}
