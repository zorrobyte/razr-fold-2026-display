package androidx.compose.ui.text.font;

import androidx.compose.ui.text.font.TypefaceResult;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: PlatformFontFamilyTypefaceAdapter.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PlatformFontFamilyTypefaceAdapter {
    private final PlatformTypefaces platformTypefaceResolver = PlatformTypefaces_androidKt.PlatformTypefaces();

    public TypefaceResult resolve(TypefaceRequest typefaceRequest, PlatformFontLoader platformFontLoader, Function1 function1, Function1 function12) {
        typefaceRequest.getFontFamily();
        return new TypefaceResult.Immutable(this.platformTypefaceResolver.mo827createDefaultFO1MlWM(typefaceRequest.getFontWeight(), typefaceRequest.m831getFontStyle_LCdwA()), false, 2, null);
    }
}
