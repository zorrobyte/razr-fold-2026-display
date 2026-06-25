package androidx.slidingpanelayout.widget;

import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: compiled from: SlidingPaneLayout.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SlidingPaneLayoutKt {
    private static final boolean edgeSizeUsingSystemGestureInsets = true;

    /* JADX INFO: Access modifiers changed from: private */
    public static final int getChildHeightMeasureSpec(View view, boolean z, int i, int i2) {
        return z ? ViewGroup.getChildMeasureSpec(i, i2, view.getLayoutParams().height) : View.MeasureSpec.makeMeasureSpec(view.getMeasuredHeight(), 1073741824);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Void layoutParamsError(View view, ViewGroup.LayoutParams layoutParams) {
        throw new IllegalStateException(("SlidingPaneLayout child " + view + " had unexpected LayoutParams " + layoutParams).toString());
    }
}
