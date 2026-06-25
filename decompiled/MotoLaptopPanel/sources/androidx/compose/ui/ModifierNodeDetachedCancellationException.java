package androidx.compose.ui;

import androidx.compose.ui.internal.PlatformOptimizedCancellationException;

/* JADX INFO: compiled from: Modifier.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ModifierNodeDetachedCancellationException extends PlatformOptimizedCancellationException {
    public ModifierNodeDetachedCancellationException() {
        super("The Modifier.Node was detached");
    }
}
