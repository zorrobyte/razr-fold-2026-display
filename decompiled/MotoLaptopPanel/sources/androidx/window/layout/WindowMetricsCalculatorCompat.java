package androidx.window.layout;

import android.content.Context;
import androidx.core.view.WindowInsetsCompat;
import androidx.window.layout.util.DensityCompatHelper;
import androidx.window.layout.util.WindowMetricsCompatHelper;
import java.util.ArrayList;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: WindowMetricsCalculatorCompat.kt */
/* JADX INFO: loaded from: classes.dex */
public final class WindowMetricsCalculatorCompat implements WindowMetricsCalculator {
    private final DensityCompatHelper densityCompatHelper;
    private final ArrayList insetsTypeMasks;

    public WindowMetricsCalculatorCompat(DensityCompatHelper densityCompatHelper) {
        densityCompatHelper.getClass();
        this.densityCompatHelper = densityCompatHelper;
        this.insetsTypeMasks = CollectionsKt.arrayListOf(Integer.valueOf(WindowInsetsCompat.Type.statusBars()), Integer.valueOf(WindowInsetsCompat.Type.navigationBars()), Integer.valueOf(WindowInsetsCompat.Type.captionBar()), Integer.valueOf(WindowInsetsCompat.Type.ime()), Integer.valueOf(WindowInsetsCompat.Type.systemGestures()), Integer.valueOf(WindowInsetsCompat.Type.mandatorySystemGestures()), Integer.valueOf(WindowInsetsCompat.Type.tappableElement()), Integer.valueOf(WindowInsetsCompat.Type.displayCutout()));
    }

    public /* synthetic */ WindowMetricsCalculatorCompat(DensityCompatHelper densityCompatHelper, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? DensityCompatHelper.Companion.getInstance() : densityCompatHelper);
    }

    @Override // androidx.window.layout.WindowMetricsCalculator
    public WindowMetrics computeMaximumWindowMetrics(Context context) {
        context.getClass();
        return WindowMetricsCompatHelper.Companion.getInstance().maximumWindowMetrics(context, this.densityCompatHelper);
    }
}
