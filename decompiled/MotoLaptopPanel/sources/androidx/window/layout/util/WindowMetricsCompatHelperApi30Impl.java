package androidx.window.layout.util;

import android.content.Context;
import android.graphics.Rect;
import androidx.window.core.Bounds;
import androidx.window.layout.WindowMetrics;

/* JADX INFO: compiled from: WindowMetricsCompatHelper.kt */
/* JADX INFO: loaded from: classes.dex */
public final class WindowMetricsCompatHelperApi30Impl implements WindowMetricsCompatHelper {
    public static final WindowMetricsCompatHelperApi30Impl INSTANCE = new WindowMetricsCompatHelperApi30Impl();

    private WindowMetricsCompatHelperApi30Impl() {
    }

    @Override // androidx.window.layout.util.WindowMetricsCompatHelper
    public WindowMetrics maximumWindowMetrics(Context context, DensityCompatHelper densityCompatHelper) {
        context.getClass();
        densityCompatHelper.getClass();
        return new WindowMetrics(new Bounds(BoundsHelper.Companion.getInstance().maximumWindowBounds(context)), densityCompatHelper.density(context));
    }

    @Override // androidx.window.layout.util.WindowMetricsCompatHelper
    public WindowMetrics translateWindowMetrics(android.view.WindowMetrics windowMetrics, float f) {
        windowMetrics.getClass();
        Rect bounds = windowMetrics.getBounds();
        bounds.getClass();
        return new WindowMetrics(bounds, f);
    }
}
