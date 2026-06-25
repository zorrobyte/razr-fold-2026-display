package androidx.compose.ui.autofill;

/* JADX INFO: compiled from: ContentDataType.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ContentDataType_androidKt {
    public static final ContentDataType ContentDataType(int i) {
        return AndroidContentDataType.m106boximpl(AndroidContentDataType.m107constructorimpl(i));
    }

    public static final int getDataType(ContentDataType contentDataType) {
        contentDataType.getClass();
        return ((AndroidContentDataType) contentDataType).m111unboximpl();
    }
}
