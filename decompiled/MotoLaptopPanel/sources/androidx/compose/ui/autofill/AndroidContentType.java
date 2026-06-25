package androidx.compose.ui.autofill;

import java.util.Set;

/* JADX INFO: compiled from: ContentType.android.kt */
/* JADX INFO: loaded from: classes.dex */
final class AndroidContentType implements ContentType {
    private final Set androidAutofillHints;

    public AndroidContentType(Set set) {
        this.androidAutofillHints = set;
    }

    public final Set getAndroidAutofillHints() {
        return this.androidAutofillHints;
    }
}
