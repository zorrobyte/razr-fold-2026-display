package androidx.window.layout;

import android.content.Context;
import androidx.window.layout.util.WindowMetricsCompatHelper;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: WindowMetricsCalculator.kt */
/* JADX INFO: loaded from: classes.dex */
public interface WindowMetricsCalculator {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: WindowMetricsCalculator.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static Function1 decorator = new Function1() { // from class: androidx.window.layout.WindowMetricsCalculator$Companion$decorator$1
            @Override // kotlin.jvm.functions.Function1
            public final WindowMetricsCalculator invoke(WindowMetricsCalculator windowMetricsCalculator) {
                windowMetricsCalculator.getClass();
                return windowMetricsCalculator;
            }
        };
        private static final WindowMetricsCalculatorCompat windowMetricsCalculatorCompat = new WindowMetricsCalculatorCompat(null, 1, null);

        private Companion() {
        }

        public final WindowMetricsCalculator getOrCreate() {
            return (WindowMetricsCalculator) decorator.invoke(windowMetricsCalculatorCompat);
        }

        public final WindowMetrics translateWindowMetrics$window_release(android.view.WindowMetrics windowMetrics, float f) {
            windowMetrics.getClass();
            return WindowMetricsCompatHelper.Companion.getInstance().translateWindowMetrics(windowMetrics, f);
        }
    }

    WindowMetrics computeMaximumWindowMetrics(Context context);
}
