package androidx.window.layout.util;

import android.content.Context;
import androidx.window.layout.WindowMetrics;

/* JADX INFO: compiled from: WindowMetricsCompatHelper.kt */
/* JADX INFO: loaded from: classes.dex */
public interface WindowMetricsCompatHelper {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: WindowMetricsCompatHelper.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final WindowMetricsCompatHelper getInstance() {
            return WindowMetricsCompatHelperApi34Impl.INSTANCE;
        }
    }

    WindowMetrics currentWindowMetrics(Context context, DensityCompatHelper densityCompatHelper);
}
