package androidx.compose.ui.autofill;

import android.graphics.Rect;
import android.view.View;
import android.view.autofill.AutofillValue;

/* JADX INFO: compiled from: PlatformAutofillManager.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PlatformAutofillManagerImpl implements PlatformAutofillManager {
    private final android.view.autofill.AutofillManager platformAndroidManager;

    public PlatformAutofillManagerImpl(android.view.autofill.AutofillManager autofillManager) {
        this.platformAndroidManager = autofillManager;
    }

    @Override // androidx.compose.ui.autofill.PlatformAutofillManager
    public void commit() {
        this.platformAndroidManager.commit();
    }

    @Override // androidx.compose.ui.autofill.PlatformAutofillManager
    public void notifyValueChanged(View view, int i, AutofillValue autofillValue) {
        this.platformAndroidManager.notifyValueChanged(view, i, autofillValue);
    }

    @Override // androidx.compose.ui.autofill.PlatformAutofillManager
    public void notifyViewEntered(View view, int i, Rect rect) {
        this.platformAndroidManager.notifyViewEntered(view, i, rect);
    }

    @Override // androidx.compose.ui.autofill.PlatformAutofillManager
    public void notifyViewExited(View view, int i) {
        this.platformAndroidManager.notifyViewExited(view, i);
    }

    @Override // androidx.compose.ui.autofill.PlatformAutofillManager
    public void notifyViewVisibilityChanged(View view, int i, boolean z) {
        AutofillApi27Helper.INSTANCE.notifyViewVisibilityChanged(view, this.platformAndroidManager, i, z);
    }
}
