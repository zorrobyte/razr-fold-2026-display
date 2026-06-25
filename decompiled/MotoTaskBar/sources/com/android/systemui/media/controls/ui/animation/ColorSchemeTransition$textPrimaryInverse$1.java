package com.android.systemui.media.controls.ui.animation;

import com.android.systemui.monet.ColorScheme;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* JADX INFO: compiled from: ColorSchemeTransition.kt */
/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class ColorSchemeTransition$textPrimaryInverse$1 extends FunctionReferenceImpl implements Function1 {
    public static final ColorSchemeTransition$textPrimaryInverse$1 INSTANCE = new ColorSchemeTransition$textPrimaryInverse$1();

    ColorSchemeTransition$textPrimaryInverse$1() {
        super(1, MediaColorSchemesKt.class, "textPrimaryInverseFromScheme", "textPrimaryInverseFromScheme(Lcom/android/systemui/monet/ColorScheme;)I", 1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Integer invoke(ColorScheme colorScheme) {
        colorScheme.getClass();
        return Integer.valueOf(MediaColorSchemesKt.textPrimaryInverseFromScheme(colorScheme));
    }
}
