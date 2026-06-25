package androidx.compose.ui.autofill;

import kotlin.collections.SetsKt;

/* JADX INFO: compiled from: ContentType.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ContentType_androidKt {
    public static final ContentType ContentType(String str) {
        return new AndroidContentType(SetsKt.setOf(str));
    }

    public static final String[] getContentHints(ContentType contentType) {
        contentType.getClass();
        return (String[]) ((AndroidContentType) contentType).getAndroidAutofillHints().toArray(new String[0]);
    }
}
