package androidx.compose.animation.core;

import androidx.compose.animation.core.internal.PlatformOptimizedCancellationException;

/* JADX INFO: compiled from: InternalMutatorMutex.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MutationInterruptedException extends PlatformOptimizedCancellationException {
    public MutationInterruptedException() {
        super("Mutation interrupted");
    }
}
