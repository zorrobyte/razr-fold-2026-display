package androidx.compose.ui.platform;

import android.content.ClipDescription;
import android.content.Context;

/* JADX INFO: compiled from: AndroidClipboardManager.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AndroidClipboardManager implements ClipboardManager {
    private final android.content.ClipboardManager clipboardManager;

    public AndroidClipboardManager(android.content.ClipboardManager clipboardManager) {
        this.clipboardManager = clipboardManager;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public AndroidClipboardManager(Context context) {
        Object systemService = context.getSystemService("clipboard");
        systemService.getClass();
        this((android.content.ClipboardManager) systemService);
    }

    public boolean hasText() {
        ClipDescription primaryClipDescription = this.clipboardManager.getPrimaryClipDescription();
        if (primaryClipDescription != null) {
            return primaryClipDescription.hasMimeType("text/*");
        }
        return false;
    }
}
