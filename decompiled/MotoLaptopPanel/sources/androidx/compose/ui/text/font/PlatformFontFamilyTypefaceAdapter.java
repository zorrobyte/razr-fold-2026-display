package androidx.compose.ui.text.font;

import android.graphics.Typeface;
import androidx.compose.ui.text.font.TypefaceResult;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: PlatformFontFamilyTypefaceAdapter.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PlatformFontFamilyTypefaceAdapter {
    private final PlatformTypefaces platformTypefaceResolver = PlatformTypefaces_androidKt.PlatformTypefaces();

    public TypefaceResult resolve(TypefaceRequest typefaceRequest, PlatformFontLoader platformFontLoader, Function1 function1, Function1 function12) {
        Typeface typefaceMo1646createNamedRetOiIg;
        FontFamily fontFamily = typefaceRequest.getFontFamily();
        if (fontFamily == null ? true : fontFamily instanceof DefaultFontFamily) {
            typefaceMo1646createNamedRetOiIg = this.platformTypefaceResolver.mo1645createDefaultFO1MlWM(typefaceRequest.getFontWeight(), typefaceRequest.m1650getFontStyle_LCdwA());
        } else {
            if (!(fontFamily instanceof GenericFontFamily)) {
                return null;
            }
            typefaceMo1646createNamedRetOiIg = this.platformTypefaceResolver.mo1646createNamedRetOiIg((GenericFontFamily) typefaceRequest.getFontFamily(), typefaceRequest.getFontWeight(), typefaceRequest.m1650getFontStyle_LCdwA());
        }
        return new TypefaceResult.Immutable(typefaceMo1646createNamedRetOiIg, false, 2, null);
    }
}
