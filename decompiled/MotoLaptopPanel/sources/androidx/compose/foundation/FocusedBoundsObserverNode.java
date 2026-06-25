package androidx.compose.foundation;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.TraversableNode;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: FocusedBounds.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class FocusedBoundsObserverNode extends Modifier.Node implements TraversableNode {
    public static final TraverseKey TraverseKey = new TraverseKey(null);
    public static final int $stable = 8;

    /* JADX INFO: compiled from: FocusedBounds.kt */
    public final class TraverseKey {
        private TraverseKey() {
        }

        public /* synthetic */ TraverseKey(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}
