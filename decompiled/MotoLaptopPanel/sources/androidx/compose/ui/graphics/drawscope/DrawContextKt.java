package androidx.compose.ui.graphics.drawscope;

import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.DensityKt;

/* JADX INFO: compiled from: DrawContext.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class DrawContextKt {
    private static final Density DefaultDensity = DensityKt.Density(1.0f, 1.0f);

    public static final Density getDefaultDensity() {
        return DefaultDensity;
    }
}
