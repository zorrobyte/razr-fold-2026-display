package androidx.slidingpanelayout.widget;

import android.graphics.Rect;
import android.view.View;
import androidx.window.layout.FoldingFeature;

/* JADX INFO: compiled from: SlidingPaneLayout.kt */
/* JADX INFO: loaded from: classes.dex */
final class FoldBoundsCalculator {
    private final int[] tmpIntArray = new int[2];
    private final Rect splitViewPositionsTmpRect = new Rect();
    private final Rect getFoldBoundsInViewTmpRect = new Rect();

    private final boolean getFoldBoundsInView(FoldingFeature foldingFeature, View view, Rect rect) {
        int[] iArr = this.tmpIntArray;
        view.getLocationInWindow(iArr);
        int i = iArr[0];
        int i2 = iArr[1];
        Rect rect2 = this.getFoldBoundsInViewTmpRect;
        rect2.set(i, i2, view.getWidth() + i, view.getWidth() + i2);
        rect.set(foldingFeature.getBounds());
        boolean zIntersect = rect.intersect(rect2);
        if ((rect.width() == 0 && rect.height() == 0) || !zIntersect) {
            return false;
        }
        rect.offset(-i, -i2);
        return true;
    }

    public final boolean splitViewPositions(FoldingFeature foldingFeature, View view, Rect rect, Rect rect2) {
        view.getClass();
        rect.getClass();
        rect2.getClass();
        if (foldingFeature == null || !foldingFeature.isSeparating() || foldingFeature.getBounds().left == 0) {
            return false;
        }
        Rect rect3 = this.splitViewPositionsTmpRect;
        if (foldingFeature.getBounds().top != 0 || !getFoldBoundsInView(foldingFeature, view, rect3)) {
            return false;
        }
        rect.set(view.getPaddingLeft(), view.getPaddingTop(), Math.max(view.getPaddingLeft(), rect3.left), view.getHeight() - view.getPaddingBottom());
        int width = view.getWidth() - view.getPaddingRight();
        rect2.set(Math.min(width, rect3.right), view.getPaddingTop(), width, view.getHeight() - view.getPaddingBottom());
        return true;
    }
}
