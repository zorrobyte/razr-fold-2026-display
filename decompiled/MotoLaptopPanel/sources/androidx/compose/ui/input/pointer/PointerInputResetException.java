package androidx.compose.ui.input.pointer;

import androidx.compose.ui.internal.PlatformOptimizedCancellationException;

/* JADX INFO: compiled from: SuspendingPointerInputFilter.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PointerInputResetException extends PlatformOptimizedCancellationException {
    public PointerInputResetException() {
        super("Pointer input was reset");
    }
}
