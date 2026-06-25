package com.android.systemui.media.controls.ui.animation;

import com.android.systemui.monet.ColorScheme;

/* JADX INFO: compiled from: MediaColorSchemes.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class MediaColorSchemesKt {
    public static final int accentPrimaryFromScheme(ColorScheme colorScheme) {
        colorScheme.getClass();
        return colorScheme.getAccent1().getS100();
    }

    public static final int accentSecondaryFromScheme(ColorScheme colorScheme) {
        colorScheme.getClass();
        return colorScheme.getAccent1().getS200();
    }

    public static final int backgroundEndFromScheme(ColorScheme colorScheme) {
        colorScheme.getClass();
        return colorScheme.getAccent1().getS700();
    }

    public static final int backgroundStartFromScheme(ColorScheme colorScheme) {
        colorScheme.getClass();
        return colorScheme.getAccent2().getS700();
    }

    public static final int surfaceFromScheme(ColorScheme colorScheme) {
        colorScheme.getClass();
        return colorScheme.getAccent2().getS800();
    }

    public static final int textPrimaryFromScheme(ColorScheme colorScheme) {
        colorScheme.getClass();
        return colorScheme.getNeutral1().getS50();
    }

    public static final int textPrimaryInverseFromScheme(ColorScheme colorScheme) {
        colorScheme.getClass();
        return colorScheme.getNeutral1().getS900();
    }

    public static final int textSecondaryFromScheme(ColorScheme colorScheme) {
        colorScheme.getClass();
        return colorScheme.getNeutral2().getS200();
    }

    public static final int textTertiaryFromScheme(ColorScheme colorScheme) {
        colorScheme.getClass();
        return colorScheme.getNeutral2().getS400();
    }
}
