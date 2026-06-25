package androidx.window.layout.util;

import android.content.Context;
import android.graphics.Rect;
import androidx.window.layout.WindowMetrics;

/* JADX INFO: compiled from: WindowMetricsCompatHelper.kt */
/* JADX INFO: loaded from: classes.dex */
public final class WindowMetricsCompatHelperApi34Impl implements WindowMetricsCompatHelper {
    public static final WindowMetricsCompatHelperApi34Impl INSTANCE = new WindowMetricsCompatHelperApi34Impl();

    private WindowMetricsCompatHelperApi34Impl() {
    }

    @Override // androidx.window.layout.util.WindowMetricsCompatHelper
    public WindowMetrics maximumWindowMetrics(Context context, DensityCompatHelper densityCompatHelper) {
        context.getClass();
        densityCompatHelper.getClass();
        return WindowMetricsCompatHelperApi30Impl.INSTANCE.maximumWindowMetrics(context, densityCompatHelper);
    }

    @Override // androidx.window.layout.util.WindowMetricsCompatHelper
    public WindowMetrics translateWindowMetrics(android.view.WindowMetrics windowMetrics, float f) {
        windowMetrics.getClass();
        Rect bounds = windowMetrics.getBounds();
        bounds.getClass();
        return new WindowMetrics(bounds, windowMetrics.getDensity());
    }
}
