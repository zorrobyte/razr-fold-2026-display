package androidx.compose.material3.internal;

import android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.SemanticsModifierNode;
import androidx.compose.ui.node.TraversableNode;
import androidx.compose.ui.node.TraversableNodeKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: ChildParentSemantics.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ChildSemanticsNode extends Modifier.Node implements SemanticsModifierNode {
    private Function1 properties;

    public ChildSemanticsNode(Function1 function1) {
        this.properties = function1;
    }

    @Override // androidx.compose.ui.node.SemanticsModifierNode
    public void applySemantics(final SemanticsPropertyReceiver semanticsPropertyReceiver) {
        TraversableNodeKt.traverseAncestors(this, ParentSemanticsNodeKey.INSTANCE, new Function1() { // from class: androidx.compose.material3.internal.ChildSemanticsNode.applySemantics.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(TraversableNode traversableNode) {
                traversableNode.getClass();
                MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(traversableNode);
                throw null;
            }
        });
        this.properties.invoke(semanticsPropertyReceiver);
    }

    @Override // androidx.compose.ui.Modifier.Node
    public void onDetach() {
        super.onDetach();
        TraversableNodeKt.traverseAncestors(this, ParentSemanticsNodeKey.INSTANCE, new Function1() { // from class: androidx.compose.material3.internal.ChildSemanticsNode.onDetach.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(TraversableNode traversableNode) {
                traversableNode.getClass();
                MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(traversableNode);
                MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(traversableNode);
                throw null;
            }
        });
    }

    public final void setProperties(Function1 function1) {
        this.properties = function1;
    }
}
