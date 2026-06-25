package androidx.compose.ui.platform;

import androidx.compose.ui.geometry.Rect;
import java.util.Comparator;
import kotlin.Pair;

/* JADX INFO: compiled from: AndroidComposeViewAccessibilityDelegateCompat.android.kt */
/* JADX INFO: loaded from: classes.dex */
final class TopBottomBoundsComparator implements Comparator {
    public static final TopBottomBoundsComparator INSTANCE = new TopBottomBoundsComparator();

    private TopBottomBoundsComparator() {
    }

    @Override // java.util.Comparator
    public int compare(Pair pair, Pair pair2) {
        int iCompare = Float.compare(((Rect) pair.getFirst()).getTop(), ((Rect) pair2.getFirst()).getTop());
        return iCompare != 0 ? iCompare : Float.compare(((Rect) pair.getFirst()).getBottom(), ((Rect) pair2.getFirst()).getBottom());
    }
}
