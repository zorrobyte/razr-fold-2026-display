package com.android.systemui.media.controls.ui.animation;

import com.android.systemui.monet.ColorScheme;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* JADX INFO: compiled from: ColorSchemeTransition.kt */
/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class ColorSchemeTransition$accentPrimary$1 extends FunctionReferenceImpl implements Function1 {
    public static final ColorSchemeTransition$accentPrimary$1 INSTANCE = new ColorSchemeTransition$accentPrimary$1();

    ColorSchemeTransition$accentPrimary$1() {
        super(1, MediaColorSchemesKt.class, "accentPrimaryFromScheme", "accentPrimaryFromScheme(Lcom/android/systemui/monet/ColorScheme;)I", 1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Integer invoke(ColorScheme colorScheme) {
        colorScheme.getClass();
        return Integer.valueOf(MediaColorSchemesKt.accentPrimaryFromScheme(colorScheme));
    }
}
