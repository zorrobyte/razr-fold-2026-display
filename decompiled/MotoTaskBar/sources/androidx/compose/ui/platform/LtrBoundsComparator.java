package androidx.compose.ui.platform;

import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.semantics.SemanticsNode;
import java.util.Comparator;

/* JADX INFO: compiled from: AndroidComposeViewAccessibilityDelegateCompat.android.kt */
/* JADX INFO: loaded from: classes.dex */
final class LtrBoundsComparator implements Comparator {
    public static final LtrBoundsComparator INSTANCE = new LtrBoundsComparator();

    private LtrBoundsComparator() {
    }

    @Override // java.util.Comparator
    public int compare(SemanticsNode semanticsNode, SemanticsNode semanticsNode2) {
        Rect boundsInWindow = semanticsNode.getBoundsInWindow();
        Rect boundsInWindow2 = semanticsNode2.getBoundsInWindow();
        int iCompare = Float.compare(boundsInWindow.getLeft(), boundsInWindow2.getLeft());
        if (iCompare != 0) {
            return iCompare;
        }
        int iCompare2 = Float.compare(boundsInWindow.getTop(), boundsInWindow2.getTop());
        if (iCompare2 != 0) {
            return iCompare2;
        }
        int iCompare3 = Float.compare(boundsInWindow.getBottom(), boundsInWindow2.getBottom());
        return iCompare3 != 0 ? iCompare3 : Float.compare(boundsInWindow.getRight(), boundsInWindow2.getRight());
    }
}
