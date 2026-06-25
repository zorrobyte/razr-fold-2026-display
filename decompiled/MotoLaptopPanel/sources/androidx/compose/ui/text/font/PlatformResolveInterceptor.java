package androidx.compose.ui.text.font;

/* JADX INFO: compiled from: FontFamilyResolver.kt */
/* JADX INFO: loaded from: classes.dex */
public interface PlatformResolveInterceptor {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: FontFamilyResolver.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final PlatformResolveInterceptor Default = new PlatformResolveInterceptor() { // from class: androidx.compose.ui.text.font.PlatformResolveInterceptor$Companion$Default$1
        };

        private Companion() {
        }

        public final PlatformResolveInterceptor getDefault$ui_text_release() {
            return Default;
        }
    }

    default FontFamily interceptFontFamily(FontFamily fontFamily) {
        return fontFamily;
    }

    /* JADX INFO: renamed from: interceptFontStyle-T2F_aPo, reason: not valid java name */
    default int m1643interceptFontStyleT2F_aPo(int i) {
        return i;
    }

    /* JADX INFO: renamed from: interceptFontSynthesis-Mscr08Y, reason: not valid java name */
    default int m1644interceptFontSynthesisMscr08Y(int i) {
        return i;
    }

    default FontWeight interceptFontWeight(FontWeight fontWeight) {
        return fontWeight;
    }
}
