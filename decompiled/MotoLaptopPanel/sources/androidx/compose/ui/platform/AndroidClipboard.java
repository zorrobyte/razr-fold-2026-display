package androidx.compose.ui.platform;

/* JADX INFO: compiled from: AndroidClipboard.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AndroidClipboard implements Clipboard {
    private final AndroidClipboardManager androidClipboardManager;

    public AndroidClipboard(AndroidClipboardManager androidClipboardManager) {
        this.androidClipboardManager = androidClipboardManager;
    }
}
