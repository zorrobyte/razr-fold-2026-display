package androidx.compose.ui.autofill;

import android.view.View;

/* JADX INFO: compiled from: AutofillUtils.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AutofillApi27Helper {
    public static final AutofillApi27Helper INSTANCE = new AutofillApi27Helper();

    private AutofillApi27Helper() {
    }

    public final void notifyViewVisibilityChanged(View view, android.view.autofill.AutofillManager autofillManager, int i, boolean z) {
        autofillManager.notifyViewVisibilityChanged(view, i, z);
    }
}
