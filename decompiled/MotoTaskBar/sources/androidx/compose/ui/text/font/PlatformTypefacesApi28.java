package androidx.compose.ui.text.font;

import android.graphics.Typeface;
import androidx.compose.ui.text.font.FontStyle;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PlatformTypefaces.android.kt */
/* JADX INFO: loaded from: classes.dex */
final class PlatformTypefacesApi28 implements PlatformTypefaces {
    /* JADX INFO: renamed from: createAndroidTypefaceApi28-RetOiIg, reason: not valid java name */
    private final Typeface m828createAndroidTypefaceApi28RetOiIg(String str, FontWeight fontWeight, int i) {
        FontStyle.Companion companion = FontStyle.Companion;
        if (FontStyle.m824equalsimpl0(i, companion.m826getNormal_LCdwA()) && Intrinsics.areEqual(fontWeight, FontWeight.Companion.getNormal()) && (str == null || str.length() == 0)) {
            return Typeface.DEFAULT;
        }
        return Typeface.create(str == null ? Typeface.DEFAULT : Typeface.create(str, 0), fontWeight.getWeight(), FontStyle.m824equalsimpl0(i, companion.m825getItalic_LCdwA()));
    }

    @Override // androidx.compose.ui.text.font.PlatformTypefaces
    /* JADX INFO: renamed from: createDefault-FO1MlWM */
    public Typeface mo827createDefaultFO1MlWM(FontWeight fontWeight, int i) {
        return m828createAndroidTypefaceApi28RetOiIg(null, fontWeight, i);
    }
}
