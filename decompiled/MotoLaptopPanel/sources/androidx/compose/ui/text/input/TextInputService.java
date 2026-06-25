package androidx.compose.ui.text.input;

import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: compiled from: TextInputService.kt */
/* JADX INFO: loaded from: classes.dex */
public class TextInputService {
    private final AtomicReference _currentInputSession = new AtomicReference(null);
    private final PlatformTextInputService platformTextInputService;

    public TextInputService(PlatformTextInputService platformTextInputService) {
        this.platformTextInputService = platformTextInputService;
    }
}
