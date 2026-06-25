package androidx.compose.ui.text.font;

/* JADX INFO: compiled from: FontFamilyResolver.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class TypefaceRequest {
    private final int fontStyle;
    private final int fontSynthesis;
    private final FontWeight fontWeight;
    private final Object resourceLoaderCacheKey;

    /* JADX INFO: renamed from: copy-e1PVR60$default, reason: not valid java name */
    public static /* synthetic */ TypefaceRequest m829copye1PVR60$default(TypefaceRequest typefaceRequest, FontFamily fontFamily, FontWeight fontWeight, int i, int i2, Object obj, int i3, Object obj2) {
        if ((i3 & 1) != 0) {
            typefaceRequest.getClass();
            fontFamily = null;
        }
        if ((i3 & 2) != 0) {
            fontWeight = typefaceRequest.fontWeight;
        }
        if ((i3 & 4) != 0) {
            i = typefaceRequest.fontStyle;
        }
        if ((i3 & 8) != 0) {
            i2 = typefaceRequest.fontSynthesis;
        }
        if ((i3 & 16) != 0) {
            obj = typefaceRequest.resourceLoaderCacheKey;
        }
        Object obj3 = obj;
        int i4 = i;
        return typefaceRequest.m830copye1PVR60(fontFamily, fontWeight, i4, i2, obj3);
    }

    /* JADX INFO: renamed from: copy-e1PVR60, reason: not valid java name */
    public abstract TypefaceRequest m830copye1PVR60(FontFamily fontFamily, FontWeight fontWeight, int i, int i2, Object obj);

    public abstract FontFamily getFontFamily();

    /* JADX INFO: renamed from: getFontStyle-_-LCdwA, reason: not valid java name */
    public abstract int m831getFontStyle_LCdwA();

    public abstract FontWeight getFontWeight();
}
