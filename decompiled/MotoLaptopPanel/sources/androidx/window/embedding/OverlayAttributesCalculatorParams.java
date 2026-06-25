package androidx.window.embedding;

import android.content.res.Configuration;
import androidx.window.layout.WindowLayoutInfo;
import androidx.window.layout.WindowMetrics;

/* JADX INFO: compiled from: OverlayAttributesCalculatorParams.kt */
/* JADX INFO: loaded from: classes.dex */
public final class OverlayAttributesCalculatorParams {
    private final OverlayAttributes defaultOverlayAttributes;
    private final String overlayTag;
    private final Configuration parentConfiguration;
    private final WindowLayoutInfo parentWindowLayoutInfo;
    private final WindowMetrics parentWindowMetrics;

    public OverlayAttributesCalculatorParams(WindowMetrics windowMetrics, Configuration configuration, WindowLayoutInfo windowLayoutInfo, String str, OverlayAttributes overlayAttributes) {
        windowMetrics.getClass();
        configuration.getClass();
        windowLayoutInfo.getClass();
        str.getClass();
        overlayAttributes.getClass();
        this.parentWindowMetrics = windowMetrics;
        this.parentConfiguration = configuration;
        this.parentWindowLayoutInfo = windowLayoutInfo;
        this.overlayTag = str;
        this.defaultOverlayAttributes = overlayAttributes;
    }

    public String toString() {
        return OverlayAttributesCalculatorParams.class + ":{parentWindowMetrics=" + this.parentWindowMetrics + "parentConfiguration=" + this.parentConfiguration + "parentWindowLayoutInfo=" + this.parentWindowLayoutInfo + "overlayTag=" + this.overlayTag + "defaultOverlayAttributes=" + this.defaultOverlayAttributes;
    }
}
