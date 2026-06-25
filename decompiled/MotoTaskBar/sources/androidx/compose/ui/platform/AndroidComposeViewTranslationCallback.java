package androidx.compose.ui.platform;

import android.view.View;
import android.view.translation.ViewTranslationCallback;

/* JADX INFO: compiled from: AndroidComposeView.android.kt */
/* JADX INFO: loaded from: classes.dex */
final class AndroidComposeViewTranslationCallback implements ViewTranslationCallback {
    public static final AndroidComposeViewTranslationCallback INSTANCE = new AndroidComposeViewTranslationCallback();

    private AndroidComposeViewTranslationCallback() {
    }

    @Override // android.view.translation.ViewTranslationCallback
    public boolean onClearTranslation(View view) {
        view.getClass();
        ((AndroidComposeView) view).getContentCaptureManager$ui_release().onClearTranslation$ui_release();
        return true;
    }

    @Override // android.view.translation.ViewTranslationCallback
    public boolean onHideTranslation(View view) {
        view.getClass();
        ((AndroidComposeView) view).getContentCaptureManager$ui_release().onHideTranslation$ui_release();
        return true;
    }

    @Override // android.view.translation.ViewTranslationCallback
    public boolean onShowTranslation(View view) {
        view.getClass();
        ((AndroidComposeView) view).getContentCaptureManager$ui_release().onShowTranslation$ui_release();
        return true;
    }
}
