package androidx.compose.ui.text.font;

/* JADX INFO: compiled from: FontFamily.kt */
/* JADX INFO: loaded from: classes.dex */
public final class GenericFontFamily extends SystemFontFamily {
    private final String fontFamilyName;
    private final String name;

    public GenericFontFamily(String str, String str2) {
        super(null);
        this.name = str;
        this.fontFamilyName = str2;
    }

    public final String getName() {
        return this.name;
    }

    public String toString() {
        return this.fontFamilyName;
    }
}
