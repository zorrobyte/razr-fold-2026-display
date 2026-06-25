package androidx.compose.material3;

import androidx.compose.material.ripple.RippleAlpha;

/* JADX INFO: compiled from: Ripple.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RippleDefaults {
    public static final RippleDefaults INSTANCE = new RippleDefaults();
    private static final RippleAlpha RippleAlpha = new RippleAlpha(0.16f, 0.1f, 0.08f, 0.1f);

    private RippleDefaults() {
    }

    public final RippleAlpha getRippleAlpha() {
        return RippleAlpha;
    }
}
