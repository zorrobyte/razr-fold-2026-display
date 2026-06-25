package androidx.compose.ui.autofill;

import android.graphics.Rect;
import android.view.View;
import android.view.autofill.AutofillValue;

/* JADX INFO: compiled from: PlatformAutofillManager.android.kt */
/* JADX INFO: loaded from: classes.dex */
public interface PlatformAutofillManager {
    void commit();

    void notifyValueChanged(View view, int i, AutofillValue autofillValue);

    void notifyViewEntered(View view, int i, Rect rect);

    void notifyViewExited(View view, int i);

    void notifyViewVisibilityChanged(View view, int i, boolean z);
}
